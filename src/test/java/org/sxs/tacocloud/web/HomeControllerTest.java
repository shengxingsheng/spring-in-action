package org.sxs.tacocloud.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest()//针对HomeController的Web测试
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; //注入MockMvc

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))//发起对“/”的GET请求
                .andExpect(status().isOk())   //期望得到HTTP 200
                .andExpect(view().name("home"))   //期望得到home视图
                .andExpect(content().string(    //期望包含“Welcome to...”
                        containsString("Welcome to...")));
    }


}