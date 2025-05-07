package pageobjects;

import com.microsoft.playwright.Page;

public class PoblarAsignarProductorAEtiquetas {
    private final Page page;
    private final String url = "http://localhost/dolibarr/custom/poblarAsignarProductosAEtiquetas.php";

    public PoblarAsignarProductorAEtiquetas(Page page) {
        this.page = page;
    }

    public void poblarDatosAsignarProductosAEtiquetas() {
        page.navigate(url);

        // Espera hasta que se vea el mensaje final de éxito
        page.waitForSelector("text=Proceso completado.", new Page.WaitForSelectorOptions().setTimeout(60_000));

        System.out.println("Población de asignacion de productos a etiquetas completado.");
    }
}
