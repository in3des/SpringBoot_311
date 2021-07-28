package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.entity.Person;
import web.service.PeopleService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping("/")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/admin")
    public String showIndexPage(Model model, Principal principal) {
        System.out.println("simple - " + principal.getName());
        model.addAttribute("people", peopleService.index());
        return "people/index";
    }

//    @GetMapping("/user/{id}")
//    public String showOneUserPage(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("person", peopleService.show(id));
//        return "people/show";
//    }

//    @GetMapping("/user")
//    public String showOneUserPage(Model model, Principal principal) throws NoSuchFieldException {
//        System.out.println("principal.getName() ---> " + principal.getName());
//        System.out.println("Authentication  --> " + SecurityContextHolder.getContext().getAuthentication().getName());
//
////        try {
////            principal.getClass().getField("email").setAccessible(true);
//////            String email  = principal.getClass().getField("НазваниеПоля").toString();
////            String email  = principal.getClass().getField("email").toString();
////            System.out.println("clean --> " + principal.getClass().getField("email").toString());
////            principal.getClass().getField("email").setAccessible(true);
////            System.out.println("inside try");
////            System.out.println("gjgsnrf -->" + email);
////            System.out.println("clean --> " + principal.getClass().getField("email").toString());
////        } catch (NoSuchFieldException e) {
////            e.printStackTrace();
////        }
////
////        System.out.println("test test test");
////        System.out.println("clean --> " + principal.getClass().getField("email").toString());
//
//
//
////        System.out.println("full - " + peopleService.findPersonByEmail(principal.getName()));
//
//        model.addAttribute("person", peopleService.findPersonByName(principal.getName()));
//
//        return "people/show";
//    }

//    @GetMapping("/user")
//    public String showOneUserPage(Model model, Principal principal) throws NoSuchFieldException {
//        model.addAttribute("person", peopleService.findPersonByName(principal.getName()));
//
//        return "people/show";

    @GetMapping("/user")
    public String showOneUserPage(Model model, Authentication authentication) throws NoSuchFieldException {
//        Person person = (Person) authentication.getPrincipal();
//        model.addAttribute("person", peopleService.findPersonByEmail(person.getEmail()));
        model.addAttribute("person", peopleService.findPersonByEmail(((Person) authentication.getPrincipal()).getEmail()));
        return "people/show";
    }

//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public ModelAndView showOneUserPage(Principal principal) {
//        ModelAndView mav= new ModelAndView("/people/show");
//        mav.addObject("person", principal);
//        return mav;
//    }

//    @GetMapping("/user")
//    public String show1(String email, Model model) {
//        model.addAttribute("person", peopleService.findPersonByEmail(email));
//        return "people/show";
//    }

    @GetMapping("/admin/new")
    public String showNewPersonPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping("/admin")
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        peopleService.save(person);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String showEditPersonPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person", peopleService.show(id));
        return "people/edit";
    }

    @PatchMapping("admin/{id}")
    public String UpdatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") Long id) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(person, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        peopleService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}
