package com.demo.utilities.pagination;

import com.demo.entities.Client;
import com.demo.exception.IsNullException;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination<T> {

    public static final int MAX_RESULTS = 10;
    public static final int MINIMAL_PAGE_NUMBER = 0;

    private Page page;
    private ModelNaming modelNaming;

    public Pagination(Page page, ModelNaming modelNaming){

        if(page == null){
            throw new IsNullException("page is null");
        }

        if(modelNaming == null){
            throw new IsNullException("modelNaming is null");
        }

        this.page = page;
        this.modelNaming = modelNaming;

    }

    public static int getNumberPage(Optional<Integer> numberPage){

        int intNumberPage = numberPage.orElse(MINIMAL_PAGE_NUMBER);
        intNumberPage = (intNumberPage >= 0) ? intNumberPage : MINIMAL_PAGE_NUMBER;
        return intNumberPage;

    }

    public static int getSize(Optional<Integer> size){

        int intSize = size.orElse(MAX_RESULTS);
        intSize = (intSize > 0) ? intSize : MAX_RESULTS;
        return intSize;

    }

    /**
     * Build the list of pages number to
     * @return List<Integer>
     */
    public List<Integer> buildPageNumbers(){

        int totalPages = page.getTotalPages();
        List<Integer> pageNumbers;
        if(totalPages > 0){
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }
        else{

            pageNumbers = new ArrayList<Integer>();
            pageNumbers.add(1);

        }

        return pageNumbers;

    }

    public Map computePaginationModel(){

        Map result = new HashMap();
        ModelNaming naming = this.modelNaming;

        List<T> content = this.page.getContent();
        result.put(naming.getContentName(), content);

        List<Integer> pageNumbers = this.buildPageNumbers();
        result.put(naming.getPageNumbersName(), pageNumbers);

        result.put(naming.getPageName(), this.page);

        return result;

    }

}
