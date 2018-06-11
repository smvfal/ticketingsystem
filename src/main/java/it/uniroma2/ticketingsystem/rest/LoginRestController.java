package it.uniroma2.ticketingsystem.rest;

import it.uniroma2.ticketingsystem.controller.UtenteController;
import it.uniroma2.ticketingsystem.dao.UtenteDao;
import it.uniroma2.ticketingsystem.entity.Ruolo;
import it.uniroma2.ticketingsystem.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
public class LoginRestController {

    @Autowired
    private UtenteController utenteController;

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/redirect_login")
    public ModelAndView redirect_login(){
        //ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utente user = utenteController.cercaPerUsername(auth.getName());
        List<Ruolo> list = user.getRuoli();
        if (list.get(0).getName().equals("ADMIN")){
            return new ModelAndView("redirect:dashboard/dash").addObject("userName",user.getUsername());
        }else if (list.get(0).getName().equals("USER")){
            return new ModelAndView("redirect:index").addObject("userName","USER");
        }
        //modelAndView.setViewName("dashboard/dash");
        //return modelAndView;
        return null;
    }

    @RequestMapping(value="/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /*@RequestMapping(value="/dashboard/dash")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Utente user = utenteController.cercaPerUsername(auth.getName());
        //modelAndView.addObject("userName", "Welcome " + user.getNome() + " " + user.getCognome() + " (" + user.getUsername() + ")");
        //modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard/dash");
        return modelAndView;
    }*/

}
