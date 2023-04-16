package phonebook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import phonebook.entity.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {
    private List<Contact> contacts;

    public ContactController() {
        this.contacts = new ArrayList<>();
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }

    @PostMapping("/")
    public String add(Contact contact) {
        this.contacts.add(contact);
        return "redirect:/";
    }

    @GetMapping("edit{name}/")
    public ModelAndView editForm(@PathVariable String name, ModelAndView modelAndView) {
        Optional<Contact> optionalContact = this.contacts.stream().filter(c -> c.getName().equals(name)).findFirst();
        Contact contact = optionalContact.orElseThrow(() -> new IllegalArgumentException("No contact found with name " + name));

        modelAndView.setViewName("edit");
        modelAndView.addObject("contact", contact);

        return modelAndView;
    }

    @PutMapping("/edit{name}")
    public String edit (@PathVariable String name, Contact contact) {
        Optional<Contact> optionalContact = this.contacts.stream().filter(c -> c.getName().equals(name)).findFirst();
        Contact contact1 = optionalContact.orElseThrow(() -> new IllegalArgumentException("No contact found with name " + name));
        contact1.setName(contact.getName());
        contact1.setNumber(contact.getNumber());
        return "redirect:/";
    }

    @DeleteMapping("/delete{name}")
    public String delete(@PathVariable String name) {
        this.contacts.removeIf(contact -> contact.getName().equals(name));

        return "redirect:/";
    }
}
