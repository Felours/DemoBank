package com.demo.web;

import com.demo.service.IBanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BanqueController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)  // Le mapping depuis l'URL et la page index representee par l'action index. Une vue est basee teamleaf dans les resources/templates
    public String index(){

        return "index";

    }

}
