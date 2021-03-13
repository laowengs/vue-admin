package com.laowengs.vuedashboard.docker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.laowengs.vuedashboard.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/docker")
public class DockerController {
    private RestTemplate restTemplate = new RestTemplate();
    @RequestMapping(value = "/containers",method = RequestMethod.GET)
    public Result<JSONArray> containers(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://wengjp.local:12375/containers/json", String.class);
        String body = responseEntity.getBody();
        JSONArray jsonArray = JSON.parseArray(body);
        return Result.getInstance(0, "success", jsonArray);
    }

    @RequestMapping(value = "/images",method = RequestMethod.GET)
    public Result<JSONArray> images(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://wengjp.local:12375/images/json", String.class);
        String body = responseEntity.getBody();
        JSONArray jsonArray = JSON.parseArray(body);
        return Result.getInstance(0, "success", jsonArray);
    }
}
