package pageobjects;

import com.microsoft.playwright.*;

public class DespoblarBaseDeDatos {
    private final Page page;
    private final String url = "http://localhost/dolibarr/custom/despoblar.php";

    public DespoblarBaseDeDatos(Page page) {
        this.page = page;
    }

    public void despoblarDatos() {
        page.navigate(url);

        // Espera hasta que se vea el mensaje de finalización
        page.waitForSelector("text=Tablas despobladas correctamente", new Page.WaitForSelectorOptions().setTimeout(60_000));

        System.out.println("Despoblación de datos completada.");
    }
}