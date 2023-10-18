package com.example.suwanboard.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExampleController {

  @GetMapping("/thymeleaf/example")
  public String thymeleafExample(Model model) { // 뷰로 데이터를 넘겨주는 모델 객체
    System.out.println("와이 요청 안와?");
    Person person = new Person();
    person.setId(90L);
    person.setName("이수안");
    person.setAge(29);
    person.setHobbies(new ArrayList<>(List.of("jiujitsu", "soccer")));

    model.addAttribute("person", person);
    model.addAttribute("today", LocalDate.now());
    return "example";
  }

}

@Getter
@Setter
class Person {
  private Long id;
  private String name;
  private int age;
  private List<String> hobbies;
}