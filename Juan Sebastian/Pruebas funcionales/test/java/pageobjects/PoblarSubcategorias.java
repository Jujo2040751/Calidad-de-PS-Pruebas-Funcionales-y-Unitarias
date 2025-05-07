package pageobjects;

import com.microsoft.playwright.Page;

public class PoblarSubcategorias {
    private final Page page;
    private final String url = "http://localhost/dolibarr/custom/poblarSubcategoria.php";

    public PoblarSubcategorias(Page page) {
        this.page = page;
    }

    public void poblarDatosSubcategorias() {
        page.navigate(url);

        // Espera hasta que se vea el mensaje final de éxito
        page.waitForSelector("text=Proceso completado.", new Page.WaitForSelectorOptions().setTimeout(60_000));

        System.out.println("Población de subcategorias completada.");
    }
}