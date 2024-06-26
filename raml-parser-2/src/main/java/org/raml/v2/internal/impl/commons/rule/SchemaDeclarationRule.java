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
package org.raml.v2.internal.impl.commons.rule;

import org.raml.v2.internal.utils.SchemaGenerator;
import org.raml.yagi.framework.grammar.rule.ErrorNodeFactory;
import org.raml.yagi.framework.grammar.rule.Rule;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.StringNode;
import org.raml.yagi.framework.suggester.ParsingContext;
import org.raml.yagi.framework.suggester.Suggestion;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class SchemaDeclarationRule extends Rule
{
    @Override
    public boolean matches(@Nonnull Node node)
    {
        if (node instanceof StringNode)
        {
            final String value = ((StringNode) node).getValue();
            return SchemaGenerator.isJsonSchema(value) || SchemaGenerator.isXmlSchema(value);
        }
        else
        {
            return false;
        }
    }

    @Nonnull
    @Override
    public Node apply(@Nonnull Node node)
    {
        if (matches(node))
        {
            return createNodeUsingFactory(node, ((StringNode) node).getValue());
        }
        else
        {
            return ErrorNodeFactory.createInvalidNode(node);
        }
    }

    @Override
    public String getDescription()
    {
        return "External schema.";
    }

    @Nonnull
    @Override
    public List<Suggestion> getSuggestions(Node node, ParsingContext context)
    {
        return Collections.emptyList();
    }
}
