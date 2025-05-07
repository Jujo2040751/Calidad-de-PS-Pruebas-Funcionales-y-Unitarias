package pageobjects;

import com.microsoft.playwright.Page;

public class PoblarBaseDeDatos {
    private final Page page;
    private final String url = "http://localhost/dolibarr/custom/poblar.php";

    public PoblarBaseDeDatos(Page page) {
        this.page = page;
    }

    public void poblarDatos() {
        page.navigate(url);

        // Espera hasta que se vea el mensaje final de éxito
        page.waitForSelector("text=Todo completado", new Page.WaitForSelectorOptions().setTimeout(60_000));

        System.out.println("Población de datos completada.");
    }
}