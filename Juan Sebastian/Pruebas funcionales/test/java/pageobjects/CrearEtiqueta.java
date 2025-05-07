package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Objects;

public class CrearEtiqueta {
    private final Page page;
    private final Locator searchCrearEtiquetaButton;
    private final Locator searchRefField;
    private final Locator searchDescripcionField;
    private final Locator searchColorField;
    private final Locator searchValidateColorCode;
    private final Locator searchPosicionField;
    private final Locator searchAñadirEnField;
    private final Locator searchChangeAñadirEnOption;
    private final Locator searchCrearButton;



    public CrearEtiqueta(Page page){
        this.page = page;
        this.searchCrearEtiquetaButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(""));
        this.searchRefField = page.locator("#label");
        this.searchDescripcionField =  page.locator("iframe[title=\"Editor de Texto Enriquecido\\, description\"]").contentFrame()
                .getByLabel("Editor de Texto Enriquecido,");
        this.searchColorField = page.getByTitle("Click To Open Color Picker");
        this.searchValidateColorCode = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Validar"));
        this.searchPosicionField = page.locator("#position");
        this.searchAñadirEnField = page.getByTitle(" ");
        this.searchChangeAñadirEnOption = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(" AutoEtiqueta 1").setExact(true));
        this.searchCrearButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Crear esta etiqueta/categoría"));
    }



    public void clickOnCrearEtiquetaButton(){
        this.searchCrearEtiquetaButton.click();
    }

    public void fillForm(String ref, String Descripcion, String Color, String Posicion,Boolean En){
        this.searchRefField.fill(ref);
        this.searchDescripcionField.fill(Descripcion);
        if(!Objects.equals(Color, "")){
            this.searchColorField.click();
            page.getByTitle(Color).click();
            this.searchValidateColorCode.click();
        }
        this.searchPosicionField.fill(Posicion);
        if(En) {
            this.searchAñadirEnField.click();
            this.searchChangeAñadirEnOption.click();
        }
        this.searchCrearButton.click();
    }
}