package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class InformacionEtiqueta1 {
    private final Page page;
    private final Locator searchAutoEtiqueta1;


    public InformacionEtiqueta1(Page page){
        this.page = page;
        this.searchAutoEtiqueta1 = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" AutoEtiqueta 1").setExact(true));
    }



    public void clickOnAutoEtiqueta1(){
        this.searchAutoEtiqueta1.click();
    }
}