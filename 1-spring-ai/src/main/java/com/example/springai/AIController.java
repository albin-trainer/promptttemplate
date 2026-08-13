package com.example.springai;

import com.example.config.AIConfig;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
	private final AIConfig AIConfig;
	@Autowired
	private ChatClient chatClient;
	
	@Value("classpath:prompts/simpleprompt.st")
	private Resource  simplePrompt;
	@Value("classpath:prompts/primary_sports.st")
	private Resource primarySports;
	@Value("classpath:prompts/system-message.st")
	private Resource systemMsg;
	@GetMapping("/prompt")

public String prompt(@RequestParam("msg")  String msg) {
	return chatClient.prompt(msg).call().content();
	
	
}
	@GetMapping("/sports")
public String sports(@RequestParam  String sports) throws IOException {
		System.out.println(sports+"-----");
	/*String msg="""
			List 5 popular personalities in {sports} along with their carrier
			achievements.
			Show the details in the proper readable format
			""";
			*/
		//String msg=simplePrompt.;
	
	
		PromptTemplate promptTemplate= new PromptTemplate(simplePrompt);
	Prompt prompt= promptTemplate.create( Map.of("sports",sports) );
	return chatClient.prompt(prompt).call().content();
}
	

	@GetMapping("/sports/primary")
	public String sportsPrimary(@RequestParam String sports) throws IOException {

	    String msg = StreamUtils.copyToString(
	            primarySports.getInputStream(),
	            StandardCharsets.UTF_8
	    );
	    System.out.println(msg);
	    System.out.println(sports);
	    PromptTemplate promptTemplate = new PromptTemplate(msg);
	    
	    String userPrompt = promptTemplate
	            .create(Map.of("sports", sports))
	            .getContents();
	    System.out.println(  "user prompt :"+ userPrompt);
	    
	    var systemMsg = new SystemMessage(
	            "Your primary function is to share information about sports. "
	            + "If someone asks something else, say: "
	            + "I will share information only about sports."
	    );

	    var userMsg = new UserMessage(userPrompt);

	    Prompt prompt = new Prompt(List.of(systemMsg, userMsg));

	    return chatClient.prompt(prompt)
	            .call()
	            .content();
	}
	
	@GetMapping("/listoutout")
	public List<String> listoutout(@RequestParam  String sports) {
		ListOutputConverter converter=
				new ListOutputConverter(new DefaultConversionService()  );
		String msg="""
				List 5 popular personalities in {sports}  
				{format}
				""";
		
		PromptTemplate promptTemplate= new PromptTemplate(msg);
		Prompt prompt= promptTemplate.create( Map.of("sports",sports,"format",converter.getFormat()) );
		ChatResponse chatResp=  chatClient.prompt(prompt).call().chatResponse();
		return  converter.convert(chatResp.getResult().getOutput().getText());
	}

	@GetMapping("/beanconverter")
	public Player beanoutputconvertor(@RequestParam  String sports) {
		BeanOutputConverter converter=
				new BeanOutputConverter(Player.class );
		String msg="""
				Generate a list of career achievements for 1 popular personalities in {sports}  
				include the player as the key and the achievements as List of value 
				{format}
				""";
		
		PromptTemplate promptTemplate= new PromptTemplate(msg);
		Prompt prompt= promptTemplate.create( Map.of("sports",sports,"format",converter.getFormat()) );
		ChatResponse chatResp=  chatClient.prompt(prompt).call().chatResponse();
		String output = chatResp.getResult().getOutput().getText();
		System.out.println(output);
		return  (Player) converter.convert(chatResp.getResult().getOutput().getText());
	}		
	@Autowired
    private  OpenAiImageModel imageModel;
	AIController(AIConfig AIConfig) {
		this.AIConfig = AIConfig;
	}
	@GetMapping("/generate-image")
    public String generateImage(@RequestParam String prompt) throws IOException {

		/*
		 * ImageResponse response = imageModel.call( new ImagePrompt(prompt)); String
		 * url= response.getResult() .getOutput() .getUrl();; System.out.println(
		 * "URL :"+url); return url;
		 */
				  ImageResponse response =
		            imageModel.call(new ImagePrompt(prompt));
				  
				  
				  var image = response.getResult().getOutput();

		    byte[] imageBytes =
		            Base64.getDecoder().decode(image.getB64Json());

		    Path path = Path.of("generated-image.png");

		    Files.write(path, imageBytes);

		    return "Saved to: " + path.toAbsolutePath();
    }
	
	/*
	 * @Autowired private OpenAiImageApi imageApi; public void test() {
	 * imageApi.createImage(null) }
	 */
}
	