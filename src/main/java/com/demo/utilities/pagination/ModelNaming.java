package com.demo.utilities.pagination;

/**
 * Represents a pagination model naming (page, pageNumbers and the content)
 */
public class ModelNaming {

    private String contentName;
    private String pageName;
    private String pageNumbersName;

    public ModelNaming(String contentName, String pageName, String pageNumbersName){

        this.contentName = contentName;
        this.pageName = pageName;
        this.pageNumbersName = pageNumbersName;

    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageNumbersName() {
        return pageNumbersName;
    }

    public void setPageNumbersName(String pageNumbersName) {
        this.pageNumbersName = pageNumbersName;
    }

}
