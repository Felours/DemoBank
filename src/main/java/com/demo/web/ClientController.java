package com.demo.web;

import com.demo.entities.Client;
import com.demo.service.IBanqueService;
import com.demo.utilities.pagination.ModelNaming;
import com.demo.utilities.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {



    @Autowired
    private IBanqueService banqueService;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String clients(Model model,
                          @RequestParam(value = "page") Optional<Integer> page,
                          @RequestParam(value = "size") Optional<Integer> size){

        int currentPage = Pagination.getNumberPage(page);
        int pageSize = Pagination.getSize(size);

        Page pageClients = banqueService.clients(currentPage, pageSize);    // Represents the pagination for the operations
        ModelNaming modelNaming = new ModelNaming("clients", "clientPage", "pageNumbers");
        Pagination pagination = new Pagination<Client>(pageClients, modelNaming);

        model.addAllAttributes(pagination.computePaginationModel());

        return "clients";

    }

}
