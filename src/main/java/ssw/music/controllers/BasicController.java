package ssw.music.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ssw.music.Person;

import java.util.ArrayList;

@Controller
public class BasicController {

    private List<Person> database;


    public BasicController() {
        System.out.println("BasicController 초기화");
        database = new ArrayList<>();
        database.add(new Person(1, "이순신", 56, "서울"));
        database.add(new Person(2, "임꺽정", 45, "인천"));
        database.add(new Person(3, "홍길동", 78, "서울"));
        database.add(new Person(4, "신사임당", 150, "조선"));
    }

    @RequestMapping("/count")
    @ResponseBody
    public String count() {
        return "현재 인원 " + database.size();
    }
    @RequestMapping("/person/{id}")
    @ResponseBody
    public String person(@PathVariable("id") int id) {
        Person p = database.stream().filter(person -> person.getId() == id).findFirst().get();
        return  "결과 : " + p;
    }

    @GetMapping("/people")
    public String people(Model model) {
        model.addAttribute("people", database);
        return  "people";
    }

    @RequestMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        Person person = database.stream().filter(p -> p.getId() == id).findFirst().get();
        database.remove(person);

        return "redirect:/people";
    }
}
