package com.demo.web;

import com.demo.entities.Client;
import com.demo.exception.EnumException;
import com.demo.exception.IsNullException;
import com.demo.service.IBanqueService;
import com.demo.utilities.TypeOperation;
import com.demo.utilities.pagination.ModelNaming;
import com.demo.utilities.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Optional;

@Controller
public class OperationController {

    @Autowired
    private IBanqueService banqueService;

    @RequestMapping(value = "/operations", method = RequestMethod.GET)
    public String operations(Model model,
                             @RequestParam(value = "codeCompte") Optional<String> codeCompte,
                             @RequestParam(value = "page") Optional<Integer> page,
                             @RequestParam(value = "size") Optional<Integer> size){

        try {

            int currentPage = Pagination.getNumberPage(page);
            int pageSize = Pagination.getSize(size);

            Page pageOperations = null;
            if (codeCompte.isPresent() && !codeCompte.get().isEmpty()) {

                String codeCompteValue = codeCompte.get();
                pageOperations = banqueService.operations(codeCompteValue, currentPage, pageSize);
                model.addAttribute("codeCompte", codeCompteValue);

            }
            else{
                pageOperations = banqueService.operations(currentPage, pageSize);
            }

            ModelNaming modelNaming = new ModelNaming("operations", "operationPage", "pageNumbers");
            Pagination pagination = new Pagination<Client>(pageOperations, modelNaming);

            Map paginationContent = pagination.computePaginationModel();
            model.addAllAttributes(paginationContent);

        }
        catch(Exception e){
            model.addAttribute("error", e);
        }

        return "operations";

    }

    @RequestMapping(value = "operations/save", method = RequestMethod.GET)
    public ModelAndView save(RedirectAttributes redirectAttributes, Model model,
                             String codeCompte, String codeCompteVirement, Double montant, String operationType){

        ModelAndView mv = new ModelAndView("redirect:/operations");
        try {

            if(codeCompte == null || montant == null || operationType == null){

                redirectAttributes.addFlashAttribute("error", new IsNullException("Le code du compte ou le montant ou le type d'operation n'a pas été fourni"));
                return mv;

            }

            TypeOperation convertedTypeOperation = convertOperationType(operationType);
            if(convertedTypeOperation == null){

                redirectAttributes.addFlashAttribute("error", new EnumException("L'operation fournie est incorrecte"));
                return mv;

            }

            switch (convertedTypeOperation){

                case VERSEMET:
                    banqueService.verser(codeCompte, montant);
                    break;
                case RETRAIT:
                    banqueService.retirer(codeCompte, montant);
                    break;
                case VIREMENT:

                    if(codeCompteVirement == null){

                        redirectAttributes.addFlashAttribute("error", new IsNullException("L'operation virement require le code compte visé"));
                        return mv;

                    }

                    banqueService.virement(codeCompte, codeCompteVirement, montant);
                    break;

            }

        }
        catch(Exception e){

            redirectAttributes.addFlashAttribute("error", e);

        }

        return mv;

    }

    private TypeOperation convertOperationType(String operationType){

        return TypeOperation.valueOf(operationType);

    }

}
