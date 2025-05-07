package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class EtiquetasCategorias {
    private final Page page;
    private final Locator searchEtiquetasCategoriasButton;


    public EtiquetasCategorias(Page page){
        this.page = page;
        this.searchEtiquetasCategoriasButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Etiquetas/Categorías")).nth(1);
    }



    public void clickOnEtiquetasCategoriasButton(){
        this.searchEtiquetasCategoriasButton.click();
    }
}