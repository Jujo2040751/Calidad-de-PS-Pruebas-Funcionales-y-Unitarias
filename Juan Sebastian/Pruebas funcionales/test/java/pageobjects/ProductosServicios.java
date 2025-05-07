package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ProductosServicios {
    private final Page page;
    private final Locator searchProductosServiciosButton;


    public ProductosServicios(Page page){
        this.page = page;
        this.searchProductosServiciosButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Productos | Servicios"));
    }



    public void clickOnProductosServiciosButton(){
        this.searchProductosServiciosButton.click();
    }
}
