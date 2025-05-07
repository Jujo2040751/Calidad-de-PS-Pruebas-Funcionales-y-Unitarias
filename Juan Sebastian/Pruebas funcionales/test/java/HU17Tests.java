import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import pageobjects.*;

import static org.junit.jupiter.api.Assertions.*;


@UsePlaywright(HU17Tests.CustomOptions.class)
public class HU17Tests {

    public static class CustomOptions implements OptionsFactory{

        @Override
        public Options getOptions(){
            return new Options().setHeadless(false);
        }
    }
    //PRUEBAS HU 17
    @Test
    void allSubcategoriesTest(Page page){

        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarSubcategorias poblarSubcategorias = new PoblarSubcategorias(page);
        poblarSubcategorias.poblarDatosSubcategorias();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        // Si el mensaje de "No se ha creado ninguna" está visible, el test debe fallar
        assertTrue(noObjectLabel, "Se encontraron productos asignados a esta etiqueta cuando no deberian ");


        // Buscar la tabla de subcategorias
        Locator tablaSubcategorias = page.locator("table").filter(new Locator.FilterOptions().setHasText("Subcategorías Contraer todo"));

        // Seleccionar todas las filas
        Locator filasSubcategorias = tablaSubcategorias.locator("tbody tr");

        // Contar filas
        int cantidadFilas = filasSubcategorias.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(cantidadFilas > 2 && !noSubcategoriesLabel, "No se encontraron las subcategorias que tiene esta etiqueta");

        despoblarBaseDeDatos.despoblarDatos();

    }


    @Test
    void allSubcategoriesAndExpandTest(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarSubcategorias poblarSubcategorias = new PoblarSubcategorias(page);
        poblarSubcategorias.poblarDatosSubcategorias();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        // Si el mensaje de "No se ha creado ninguna" está visible, el test debe fallar
        assertTrue(noObjectLabel, "Se encontraron productos asignados a esta etiqueta cuando no deberian ");


        // Buscar la tabla de subcategorias
        Locator tablaSubcategorias = page.locator("table").filter(new Locator.FilterOptions().setHasText("Subcategorías Contraer todo"));

        // Seleccionar todas las filas
        Locator filasSubcategorias = tablaSubcategorias.locator("tbody tr");

        // Contar filas
        int cantidadFilas = filasSubcategorias.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(cantidadFilas > 2 && !noSubcategoriesLabel, "No se encontraron las subcategorias que tiene esta etiqueta");

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Contraer todo")).click();
        // Espera corta por si hay efecto visual asíncrono
        page.waitForTimeout(1000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Expandir todo")).click();
        // Espera corta por si hay efecto visual asíncrono
        page.waitForTimeout(1000);

        // Filas después de expandir

        int filasDespues = tablaSubcategorias.locator("tbody tr").count();
        assertTrue(filasDespues > cantidadFilas, "Expandir todo no muestra más subcategorías");
        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void allSubcategoriesAndContractTest(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarSubcategorias poblarSubcategorias = new PoblarSubcategorias(page);
        poblarSubcategorias.poblarDatosSubcategorias();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        // Si el mensaje de "No se ha creado ninguna" está visible, el test debe fallar
        assertTrue(noObjectLabel, "Se encontraron productos asignados a esta etiqueta cuando no deberian ");


        // Buscar la tabla de subcategorias
        Locator tablaSubcategorias = page.locator("table").filter(new Locator.FilterOptions().setHasText("Subcategorías Contraer todo"));

        // Seleccionar todas las filas
        Locator filasSubcategorias = tablaSubcategorias.locator("tbody tr");

        // Contar filas
        int cantidadFilas = filasSubcategorias.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(cantidadFilas > 2 && !noSubcategoriesLabel, "No se encontraron las subcategorias que tiene esta etiqueta");

        // Filas después de contraer
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Contraer todo")).click();
        // Espera corta por si hay efecto visual asíncrono
        page.waitForTimeout(1000);

        int filasDespues = tablaSubcategorias.locator("tbody tr").count();
        assertTrue(filasDespues < cantidadFilas, "Contraer todo no muestra menos subcategorías");
        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void allProductsAsignedToLabelTest(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarAsignarProductorAEtiquetas poblarAsignarProductorAEtiquetas = new PoblarAsignarProductorAEtiquetas(page);
        poblarAsignarProductorAEtiquetas.poblarDatosAsignarProductosAEtiquetas();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(noSubcategoriesLabel, "Se encontraron subcategorias asignados a esta etiqueta cuando no deberian ");

        //page.pause();
        // Buscar la tabla de productos asignados
        Locator tablaProductosAsignados = page.locator("table").filter(new Locator.FilterOptions().setHasText("Ref."));

        // Seleccionar todas las filas
        Locator filasProductosAsignados = tablaProductosAsignados.locator("tbody tr");

        // Contar filas
        int cantidadFilas = filasProductosAsignados.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        assertTrue(cantidadFilas > 2 && !noObjectLabel, "No se encontraron los objetos asignados que tiene esta etiqueta");

        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void allProductsAsignedToLabelAndChangePaginatedTest(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarAsignarProductorAEtiquetas poblarAsignarProductorAEtiquetas = new PoblarAsignarProductorAEtiquetas(page);
        poblarAsignarProductorAEtiquetas.poblarDatosAsignarProductosAEtiquetas();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(noSubcategoriesLabel, "Se encontraron subcategorias asignados a esta etiqueta cuando no deberian ");

        // Buscar la tabla de productos asignados
        Locator tablaProductosAsignados = page.locator("table").filter(new Locator.FilterOptions().setHasText("Ref."));

        // Seleccionar todas las filas
        Locator filasProductosAsignados = tablaProductosAsignados.locator("tbody tr");

        // Contar filas
        int cantidadFilas = filasProductosAsignados.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        assertTrue(cantidadFilas > 2 && !noObjectLabel, "No se encontraron los objetos asignados que tiene esta etiqueta");

        //page.pause();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("20")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("10").setExact(true)).click();
        // Espera corta por si hay efecto visual asíncrono
        page.waitForTimeout(1000);

        int filasDespues = tablaProductosAsignados.locator("tbody tr").count();
        assertTrue(filasDespues < cantidadFilas, "No se esta cambiando el paginado");


        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void allProductsAsignedToLabelAndDeleteOneTest(Page page){
        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarAsignarProductorAEtiquetas poblarAsignarProductorAEtiquetas = new PoblarAsignarProductorAEtiquetas(page);
        poblarAsignarProductorAEtiquetas.poblarDatosAsignarProductosAEtiquetas();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(noSubcategoriesLabel, "Se encontraron subcategorias asignados a esta etiqueta cuando no deberian ");

        // Buscar la tabla de productos asignados
        Locator tablaProductosAsignados = page.locator("table").filter(new Locator.FilterOptions().setHasText("Ref."));

        // Seleccionar todas las filas
        Locator filasProductosAsignados = tablaProductosAsignados.locator("tr");

        // Contar filas
        int cantidadFilas = filasProductosAsignados.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        assertTrue(cantidadFilas > 2 && !noObjectLabel, "No se encontraron los objetos asignados que tiene esta etiqueta");



        Locator segundaFila = filasProductosAsignados.nth(1);

        // Dentro de esa fila, se buscan los botones/enlaces de acción
        Locator enlacesEnFila = segundaFila.getByRole(AriaRole.LINK);
        // Se hace clic en el botón de eliminar
        enlacesEnFila.nth(1).click();
        // Espera corta por si hay efecto visual asíncrono
        page.waitForTimeout(1000);
        int filasDespues = tablaProductosAsignados.locator("tbody tr").count();
        assertEquals(filasDespues, cantidadFilas - 1, "No se elimino ningun producto asignado");

        despoblarBaseDeDatos.despoblarDatos();
    }

    @Test
    void allSubcategoriesAndAllProductsAsignedToLabelTest(Page page){

        Login login = new Login(page);
        login.navigate();
        login.search("admin", "univalle");

        DespoblarBaseDeDatos despoblarBaseDeDatos = new DespoblarBaseDeDatos(page);
        despoblarBaseDeDatos.despoblarDatos();

        PoblarBaseDeDatos poblarBaseDeDatos = new PoblarBaseDeDatos(page);
        poblarBaseDeDatos.poblarDatos();

        PoblarSubcategorias poblarSubcategorias = new PoblarSubcategorias(page);
        poblarSubcategorias.poblarDatosSubcategorias();

        PoblarAsignarProductorAEtiquetas poblarAsignarProductorAEtiquetas = new PoblarAsignarProductorAEtiquetas(page);
        poblarAsignarProductorAEtiquetas.poblarDatosAsignarProductosAEtiquetas();

        page.navigate("http://localhost/dolibarr/");

        ProductosServicios productosServicios = new ProductosServicios(page);
        productosServicios.clickOnProductosServiciosButton();

        EtiquetasCategorias etiquetasCategorias = new EtiquetasCategorias(page);
        etiquetasCategorias.clickOnEtiquetasCategoriasButton();

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();

        // Buscar la tabla de subcategorias
        Locator tablaSubcategorias = page.locator("table").filter(new Locator.FilterOptions().setHasText("Subcategorías Contraer todo"));

        // Seleccionar todas las filas
        Locator filasSubcategorias = tablaSubcategorias.locator("tbody tr");

        // Contar filas
        int cantidadFilasSubcategorias = filasSubcategorias.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(cantidadFilasSubcategorias > 2 && !noSubcategoriesLabel, "No se encontraron las subcategorias que tiene esta etiqueta");


        // Buscar la tabla de productos asignados
        Locator tablaProductosAsignados = page.locator("table").filter(new Locator.FilterOptions().setHasText("Ref."));

        // Seleccionar todas las filas
        Locator filasProductosAsignados = tablaProductosAsignados.locator("tr");

        // Contar filas
        int cantidadFilasProductosAsignados = filasProductosAsignados.count();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        assertTrue(cantidadFilasProductosAsignados > 2 && !noObjectLabel, "No se encontraron los objetos asignados que tiene esta etiqueta");

        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void doNothingTest(Page page){

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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noObjectLabel = page.locator("text=Esta categoría no contiene ningún objeto").isVisible();

        // Si el mensaje de "No se ha creado ninguna" está visible, el test debe fallar
        assertTrue(noObjectLabel, "Se encontraron productos asignados a esta etiqueta cuando no deberian ");

        // Verificar si el texto "No se ha creado ninguna" está presente
        boolean noSubcategoriesLabel = page.locator("text=Esta categoría no contiene ninguna subcategoría.").isVisible();

        assertTrue(noSubcategoriesLabel, "Se encontraron subcategorias asignados a esta etiqueta cuando no deberian ");



        despoblarBaseDeDatos.despoblarDatos();

    }
}
