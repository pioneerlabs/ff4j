package org.ff4j.web.resources.it;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/*
 * #%L
 * ff4j-web
 * %%
 * Copyright (C) 2013 - 2014 Ff4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Unit testing of resource 'Groups'
 * 
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
public class GroupsResource_get_TestIT extends AbstractWebResourceTestIT {

    /**
     * TDD.
     */
    @Test
    public void getGroups() {
        // Given
        Assert.assertEquals(2, ff4j.getStore().readAllGroups().size());
        // When
        WebResource wResff4j = resourceGroups();
        ClientResponse resHttp = wResff4j.get(ClientResponse.class);
        String resEntity = resHttp.getEntity(String.class);
        // Then, HTTPResponse
        Assert.assertEquals("Expected status is 200", Status.OK.getStatusCode(), resHttp.getStatus());
        // Then, Entity Object
        Assert.assertNotNull(resEntity);
        Set<String> groups = readGroupList(resEntity);
        Assert.assertEquals(2, groups.size());
        Assert.assertTrue(groups.containsAll(ff4j.getStore().readAllGroups()));
    }

    /**
     * Convert JsonOutput to group set.
     * 
     * @param jsonOutput
     * @return
     */
    private Set < String > readGroupList(String jsonOutput) {
        // Remove brackets
        String resEntity = jsonOutput.substring(2, jsonOutput.length() - 1);
        // Split
        String[] features = resEntity.split("\\,");
        // Map as SET, remove quotes
        Set<String> groups = new HashSet<String>();
        for (String string : features) {
            groups.add(string.substring(1, string.indexOf(":") - 1));
        }
        return groups;
    }

}
