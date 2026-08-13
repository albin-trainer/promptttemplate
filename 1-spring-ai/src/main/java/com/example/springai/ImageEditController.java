package com.example.springai;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageEditController {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @PostMapping(value = "/cricket",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> cricket(
            @RequestParam("image") MultipartFile image)
            throws IOException {

        RestClient client = RestClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        ByteArrayResource resource =
                new ByteArrayResource(image.getBytes()) {
                    @Override
                    public String getFilename() {
                        return image.getOriginalFilename();
                    }
                };

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("model", "gpt-image-1");
        body.add("prompt",
                "Create a realistic image of this person  Teaching Java ");
        body.add("image", resource);

        String response = client.post()
                .uri("/v1/images/edits")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(String.class);

        System.out.println(response);

        return ResponseEntity.ok().
                contentType(MediaType.APPLICATION_JSON)
                .body(response.getBytes());
    }
}