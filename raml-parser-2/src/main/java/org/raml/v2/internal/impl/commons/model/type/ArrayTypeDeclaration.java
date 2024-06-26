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
package org.raml.v2.internal.impl.commons.model.type;

import org.raml.v2.internal.impl.commons.model.factory.TypeDeclarationModelFactory;
import org.raml.v2.internal.impl.v10.type.ArrayResolvedType;
import org.raml.yagi.framework.nodes.KeyValueNode;

/**
 * Binding implementation of {@link org.raml.v2.api.model.v10.datamodel.ArrayTypeDeclaration}
 */
public class ArrayTypeDeclaration extends TypeDeclaration<ArrayResolvedType>
{

    public ArrayTypeDeclaration(KeyValueNode node, ArrayResolvedType resolvedType)
    {
        super(node, resolvedType);
    }


    public Boolean uniqueItems()
    {
        return getResolvedType().getUniqueItems();
    }


    public TypeDeclaration<?> items()
    {
        return new TypeDeclarationModelFactory().create(getResolvedType().getItems());
    }


    public Integer minItems()
    {
        return getResolvedType().getMinItems();
    }


    public Integer maxItems()
    {
        return getResolvedType().getMaxItems();
    }
}