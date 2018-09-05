/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.web.jsf.impl.facesmodel;

import java.util.Collections;
import org.netbeans.modules.web.jsf.api.facesmodel.ContractMapping;
import org.netbeans.modules.web.jsf.api.facesmodel.JSFConfigVisitor;
import org.netbeans.modules.web.jsf.api.facesmodel.UrlPattern;
import org.w3c.dom.Element;

/**
 *
 * @author Martin Fousek <marfous@netbeans.org>
 */
public class ContractMappingImpl extends IdentifiableDescriptionGroupImpl implements ContractMapping {

    public ContractMappingImpl(JSFConfigModelImpl model, Element element) {
        super(model, element);
    }

    public ContractMappingImpl(JSFConfigModelImpl model) {
        super(model, createElementNS(model, JSFConfigQNames.CONTRACT_MAPPING));
    }

    @Override
    public void accept(JSFConfigVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public UrlPattern getUrlPattern() {
        return getChild(UrlPattern.class);
    }

    @Override
    public void setUrlPattern(UrlPattern urlPatternType) {
        setChild(UrlPattern.class, URL_PATTERN, urlPatternType, Collections.EMPTY_LIST);
    }

    @Override
    public String getContracts() {
        return getChildElementText(JSFConfigQNames.CONTRACTS.getQName(getNamespaceURI()));
    }

    @Override
    public void setContracts(String contracts) {
        setChildElementText(CONTRACTS, contracts, JSFConfigQNames.CONTRACTS.getQName(getNamespaceURI()));
    }

}
