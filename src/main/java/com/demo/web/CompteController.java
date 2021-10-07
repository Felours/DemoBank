package com.demo.web;

import com.demo.entities.Compte;
import com.demo.service.IBanqueService;
import groovy.util.logging.Log4j;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
//@Log4j
public class CompteController {

    @Autowired
    private IBanqueService banqueService;

    @RequestMapping(value = "/comptes", method = RequestMethod.GET)
    public String comptes(Model model){

        if(model.getAttribute("compte") != null) {

            //Compte compte = (Compte) unwrapProxy(model.getAttribute("compte"));   // Only to use on the wrapped element got by getById() and done by [T]Repository.
            Compte compte = (Compte) model.getAttribute("compte");
            model.addAttribute("compte", compte);

        }

        return "comptes";

    }

    /**
     * To unwrap the hibrnate proxy. Must find a better way..
     * @param element
     * @return Object
     */
    private Object unwrapProxy(Object element){

        Object result = null;
        if (element instanceof HibernateProxy) {

            HibernateProxy hibernateProxy = (HibernateProxy) element;
            LazyInitializer lazyInitializer = hibernateProxy.getHibernateLazyInitializer();
            result = lazyInitializer.getImplementation();

        }

        return result;

    }

    /**
     *
     * @param redirectAttributes: Used to store flash elements of the model to be transfered to the redirected action. Lost afterward! The model in the redirected action has it.
     * @param model
     * @param codeCompte
     * @return String
     */
    @RequestMapping(value = "comptes/find", method = RequestMethod.GET)
    public ModelAndView find(RedirectAttributes redirectAttributes, Model model, String codeCompte){

        ModelAndView mv = new ModelAndView("redirect:/comptes");

        try {

            if(codeCompte != null && !codeCompte.isEmpty()) {

                Compte compte = banqueService.consulter(codeCompte);
                //model.addAttribute("compte", compte); // Not necessary because it's a redirection. Use redirectAttributes.addFlashAttribute instead.
                redirectAttributes.addFlashAttribute("compte", compte);
                mv.addObject("compte", compte); // Necessary to avoid LazyInitialisationException - no session. Need to find a better alternative.

            }

        }
        catch(Exception e){

            redirectAttributes.addFlashAttribute("error", e);

        }

        //return "redirect:/comptes";
        return mv;

    }

}
