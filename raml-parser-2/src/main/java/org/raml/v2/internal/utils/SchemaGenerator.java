/*
 * Copyright 2013 (c) MuleSoft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.raml.v2.internal.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.raml.v2.api.loader.ResourceLoader;
import org.raml.v2.internal.impl.commons.nodes.ExternalSchemaTypeExpressionNode;
import org.raml.v2.internal.impl.commons.nodes.TypeDeclarationNode;
import org.raml.v2.internal.impl.commons.nodes.TypeExpressionNode;
import org.raml.v2.internal.impl.commons.type.JsonSchemaExternalType;
import org.raml.v2.internal.impl.commons.type.ResolvedType;
import org.raml.v2.internal.impl.commons.type.XmlSchemaExternalType;
import org.raml.v2.internal.impl.v10.nodes.NamedTypeExpressionNode;
import org.raml.v2.internal.utils.xml.XMLLocalConstants;
import org.raml.v2.internal.utils.xml.XsdResourceResolver;
import org.raml.yagi.framework.util.NodeUtils;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class SchemaGenerator {
  private static String DEFINITIONS = "/definitions/";

  private static final LoadingCache<JsonSchemaExternalType, JsonSchema> jsonSchemaCache = CacheBuilder.newBuilder()
    .maximumSize(Integer.parseInt(System.getProperty("yagi.json_cache_size", "200")))
    .build(new CacheLoader<>() {
      @Override
      public JsonSchema load(JsonSchemaExternalType jsonTypeDefinition) throws IOException,
        ProcessingException {
        return loadJsonSchema(jsonTypeDefinition);
      }
    });

  public static Schema generateXmlSchema(ResourceLoader resourceLoader, XmlSchemaExternalType xmlTypeDefinition) throws SAXException {
    SchemaFactory factory = SchemaFactory.newInstance(XMLLocalConstants.XML_SCHEMA_VERSION);
    factory.setResourceResolver(new XsdResourceResolver(resourceLoader, xmlTypeDefinition.getSchemaPath()));
    String includedResourceUri = resolveResourceUriIfIncluded(xmlTypeDefinition);
    return factory.newSchema(new StreamSource(new StringReader(xmlTypeDefinition.getSchemaValue()), includedResourceUri));
  }

  private static JsonSchema loadJsonSchema(JsonSchemaExternalType jsonTypeDefinition) throws IOException, ProcessingException {
    final JsonSchema result;
    String includedResourceUri = resolveResourceUriIfIncluded(jsonTypeDefinition);

    JsonNode jsonSchema = JsonLoader.fromString(jsonTypeDefinition.getSchemaValue());
    JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    if (jsonTypeDefinition.getInternalFragment() != null) {
      if (includedResourceUri != null) {
        result = factory.getJsonSchema(includedResourceUri + "#" + DEFINITIONS + jsonTypeDefinition.getInternalFragment());
      } else {
        result = factory.getJsonSchema(jsonSchema, DEFINITIONS + jsonTypeDefinition.getInternalFragment());
      }
    } else {
      if (includedResourceUri != null) {
        result = factory.getJsonSchema(includedResourceUri);
      } else {
        result = factory.getJsonSchema(jsonSchema);
      }
    }
    return result;
  }

  public static JsonSchema generateJsonSchema(JsonSchemaExternalType jsonTypeDefinition) throws IOException, ProcessingException {
    try {
      return jsonSchemaCache.get(jsonTypeDefinition);
    } catch (ExecutionException e) {
      if (e.getCause() instanceof JsonParseException)
        throw (JsonParseException) e.getCause();
      else if (e.getCause() instanceof IOException) {
        throw (IOException) e.getCause();
      } else
        throw new ProcessingException(e.getMessage(), e.getCause());
    }
  }

  @Nullable
  private static String resolveResourceUriIfIncluded(ResolvedType typeDefinition) {
    // Getting the type holding the schema
    TypeExpressionNode typeExpressionNode = typeDefinition.getTypeExpressionNode();

    if (typeExpressionNode instanceof ExternalSchemaTypeExpressionNode) {
      final ExternalSchemaTypeExpressionNode schema = (ExternalSchemaTypeExpressionNode) typeExpressionNode;

      return getIncludedResourceUri(schema);
    } else {
      // Inside the type declaration, we find the node containing the schema itself
      List<ExternalSchemaTypeExpressionNode> schemas = typeExpressionNode.findDescendantsWith(ExternalSchemaTypeExpressionNode.class);
      if (!schemas.isEmpty()) {
        return getIncludedResourceUri(schemas.get(0));
      } else {
        // If the array is empty, then it must be a reference to a previously defined type
        List<NamedTypeExpressionNode> refNode = typeExpressionNode.findDescendantsWith(NamedTypeExpressionNode.class);

        if (!refNode.isEmpty()) {
          // If refNodes is not empty, then we obtain that type
          typeExpressionNode = refNode.get(0).resolveReference();
          if (typeExpressionNode != null) {
            schemas = typeExpressionNode.findDescendantsWith(ExternalSchemaTypeExpressionNode.class);
            if (!schemas.isEmpty()) {
              return getIncludedResourceUri(schemas.get(0));
            }
          }
        }
      }
    }

    return null;
  }

  private static String getIncludedResourceUri(ExternalSchemaTypeExpressionNode schemaNode) {
    final String includedResourceUri = schemaNode.getStartPosition().getIncludedResourceUri();

    if (includedResourceUri == null) {
      final TypeDeclarationNode parentTypeDeclaration = NodeUtils.getAncestor(schemaNode, TypeDeclarationNode.class);
      if (parentTypeDeclaration != null)
        return parentTypeDeclaration.getStartPosition().getIncludedResourceUri();
    }

    return includedResourceUri;
  }


  public static boolean isJsonSchema(String schema) {
    return schema.trim().startsWith("{");
  }


  public static boolean isXmlSchema(String schema) {
    return schema.trim().startsWith("<");
  }

}
