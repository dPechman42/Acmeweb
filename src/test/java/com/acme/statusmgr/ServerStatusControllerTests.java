/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.statusmgr;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServerStatusControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/server/status")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDesc").value("Server is up"));
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/server/status").param("name", "RebYid"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.contentHeader").value("Server Status requested by RebYid"));
    }

    // Professors example unit test
    @Test
    public void withParamShouldReturnTailored_Name_Basic_Mem_Op() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?details=memory,operations&name=billy"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.contentHeader").value("Server Status requested by billy"))
                .andExpect(jsonPath("$.statusDesc").value("Server is up, and its memory is running low, " +
                        "and is operating normally"));
    }

    @Test
    public void displayDetailParamThatComesFirst() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?details=operations,memory&name=billy"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.contentHeader").value("Server Status requested by billy"))
                .andExpect(jsonPath("$.statusDesc").value("Server is up, and is operating normally, " +
                        "and its memory is running low"));
    }

    @Test
    public void withOnlyExtensionDetails() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?details=extensions"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDesc").value("Server is up, " +
                        "and is using these extensions - [Hypervisor, Kubernetes, RAID-6]"));
    }

    @Test
    public void withOnlyOperationsDetails() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?details=operations"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDesc").value("Server is up, " +
                        "and is operating normally"));
    }

    @Test
    public void withOnlyMemoryDetails() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?details=memory"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDesc").value("Server is up, " +
                        "and its memory is running low"));
    }

    @Test
    public void NameParamBeforeDetailParam() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?name=bill&details=operations"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.contentHeader").value("Server Status requested by bill"))
                .andExpect(jsonPath("$.statusDesc").value("Server is up, " +
                        "and is operating normally"));
    }

    @Test
    public void allDetailsAndNameParam() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed?details=extensions,memory,operations&name=Cthulhu"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.contentHeader").value("Server Status requested by Cthulhu"))
                .andExpect(jsonPath("$.statusDesc").value("Server is up, " +
                        "and is using these extensions - [Hypervisor, Kubernetes, RAID-6], " +
                        "and its memory is running low, " +
                        "and is operating normally"));
    }

    @Test
    public void missingDetailsShouldReturnBadStatus() throws Exception {

        this.mockMvc.perform(get("/server/status/detailed"))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void invalidDetailPassed() throws Exception {
        this.mockMvc.perform(get("/server/status/detailed?details=memory,operations,junkERROR"))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(status().reason(is("Invalid details option: junkERROR")));
    }

}
