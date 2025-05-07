import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import pageobjects.*;

import static org.junit.jupiter.api.Assertions.*;


@UsePlaywright(HU16Tests.CustomOptions.class)
public class HU16Tests {

    public static class CustomOptions implements OptionsFactory{

        @Override
        public Options getOptions(){
            return new Options().setHeadless(false);
        }
    }
    //PRUEBAS HU 16
    @Test
    void allLabelsTest(Page page){

        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();
        //page.pause();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noLabelsMessageVisible = page.locator("text=No se ha creado ninguna").isVisible();

        // Verificar que hay al menos una etiqueta visible
        int visibleCount = page.locator("role=cell").count();

        // Si el mensaje de "No se ha creado ninguna" está visible, el test debe fallar
        assertFalse(noLabelsMessageVisible, "El mensaje 'No se ha creado ninguna' está visible cuando no debería.");

        // Verificar que haya al menos una celda visible
        assertTrue(visibleCount > 0, "No se encontró ninguna etiqueta visible en la tabla.");


        despoblarBaseDeDatos.despoblarDatos();

    }


    @Test
    void searchSpecificExistingLabel(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        //page.pause();
        // Realizar búsqueda
        page.locator("input[name=\"catname\"]").fill("AutoEtiqueta 1");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buscar")).click();

        // Apuntar directamente a la tabla nueva con los resultados
        Locator resultadoTabla = page.locator("table")
                .filter(new Locator.FilterOptions().setHasText("Encontradas etiquetas/categor"));

        // Buscar dentro de esa tabla la celda específica
        Locator resultadoEtiqueta = resultadoTabla
                .getByRole(AriaRole.CELL, new Locator.GetByRoleOptions()
                        .setName(" AutoEtiqueta 1")
                        .setExact(true));

        // Esperar a que aparezca y verificar visibilidad
        assertTrue(resultadoEtiqueta.isVisible(), "La etiqueta 'AutoEtiqueta 1' no se encontró en los resultados de búsqueda.");


        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void searchSpecificNonExistingLabel(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();


        // Realizar búsqueda
        page.locator("input[name=\"catname\"]").fill("Etiqueta no existente");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buscar")).click();

        // Esperar a que la tabla de resultados esté visible
        Locator tablaResultados = page.locator("table").filter(
                new Locator.FilterOptions().setHasText("Encontradas etiquetas/categor")
        );
        tablaResultados.waitFor();

        // Verificar si la tabla está vacía
        // Comprobar que no haya filas con datos
        Locator filasTabla = tablaResultados.locator("tr");
        int filas = filasTabla.count();


        //Asegurarse de que la nueva tabla existe y ademas de que esta vacia
        assertEquals(filas, 1, "La tabla de resultados no está vacía. Se encontraron resultados inesperados.");

        // Buscar el mensaje de confirmacion
        boolean noLabelsMessageVisible = page.locator("text=No hay etiquetas que coincidan con tu busqueda").isVisible();

        assertTrue(noLabelsMessageVisible, "No se encontro el mensaje de confirmacion de que no hay resultados para la busqueda ");
        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void nonExistingLabels(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();


        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noLabelsMessageVisible = page.locator("text=No se ha creado ninguna").isVisible();

        // Si el mensaje de "No se ha creado ninguna" está visible, el test debe fallar
        assertTrue(noLabelsMessageVisible, "Se encontraron etiquetas cuando no exise ninguna");



        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void tryDeleteLabel(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(" AutoEtiqueta 1   ").setExact(true)).getByRole(AriaRole.LINK).nth(3).click();

        // Verificar si el texto "¿Está seguro de querer" está presente
        boolean deleteConfirmationMessage = page.locator("text=¿Está seguro de querer").isVisible();

        // Si no esta mensaje  ¿Está seguro de querer eliminar esta etiqueta/categoría? el test falla
        assertTrue(deleteConfirmationMessage, "No hubo confirmacion al intentar eliminar una etiqueta");



        despoblarBaseDeDatos.despoblarDatos();
    }
}
