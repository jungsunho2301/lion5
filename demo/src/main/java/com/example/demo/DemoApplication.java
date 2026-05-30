package com.example.demo;

import com.example.demo.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);

		// 스프링 빈 컨테이너에 정상 등록 및 주입되었는지 콘솔창 로그로 확인
		MemberService memberService = applicationContext.getBean(MemberService.class);
		System.out.println("=========================================");
		System.out.println("컨테이너에서 확인된 memberService 빈: " + memberService);
		System.out.println("=========================================");
	}
}