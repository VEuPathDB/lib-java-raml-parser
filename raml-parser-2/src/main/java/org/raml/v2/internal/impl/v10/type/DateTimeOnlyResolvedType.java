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
package org.raml.v2.internal.impl.v10.type;

import org.raml.v2.internal.impl.commons.nodes.TypeDeclarationNode;
import org.raml.v2.internal.impl.commons.nodes.TypeExpressionNode;
import org.raml.v2.internal.impl.commons.type.ResolvedCustomFacets;
import org.raml.v2.internal.impl.commons.type.ResolvedType;
import org.raml.v2.internal.impl.v10.rules.TypesUtils;
import org.raml.yagi.framework.grammar.rule.AnyOfRule;

import javax.annotation.Nullable;

public class DateTimeOnlyResolvedType extends XmlFacetsCapableType
{
    public DateTimeOnlyResolvedType(String typeName, TypeExpressionNode declarationNode, XmlFacets xmlFacets, ResolvedCustomFacets customFacets)
    {
        super(typeName, declarationNode, xmlFacets, customFacets);
    }

    public DateTimeOnlyResolvedType(TypeExpressionNode from)
    {
        super(getTypeName(from, TypeId.DATE_TIME_ONLY.getType()), from, new ResolvedCustomFacets());
    }

    protected DateTimeOnlyResolvedType copy()
    {
        return new DateTimeOnlyResolvedType(getTypeName(), getTypeExpressionNode(), getXmlFacets().copy(), customFacets.copy());
    }

    @Override
    public void validateCanOverwriteWith(TypeDeclarationNode from)
    {
        customFacets.validate(from);
        final AnyOfRule facetRule = new AnyOfRule().addAll(customFacets.getRules());
        TypesUtils.validateAllWith(facetRule, from.getFacets());
    }

    @Override
    public ResolvedType overwriteFacets(TypeDeclarationNode from)
    {
        final DateTimeOnlyResolvedType copy = copy();
        copy.customFacets = copy.customFacets.overwriteFacets(from);
        return overwriteFacets(copy, from);
    }

    @Override
    public ResolvedType mergeFacets(ResolvedType with)
    {
        final DateTimeOnlyResolvedType copy = copy();
        copy.customFacets = copy.customFacets.mergeWith(with.customFacets());
        return mergeFacets(copy, with);
    }

    @Override
    public <T> T visit(TypeVisitor<T> visitor)
    {
        return visitor.visitDateTimeOnly(this);
    }

    @Nullable
    @Override
    public String getBuiltinTypeName()
    {
        return TypeId.DATE_TIME_ONLY.getType();
    }
}
