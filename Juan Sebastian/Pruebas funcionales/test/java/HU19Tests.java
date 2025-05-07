import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import pageobjects.*;

import static org.junit.jupiter.api.Assertions.*;


@UsePlaywright(HU19Tests.CustomOptions.class)
public class HU19Tests {

    public static class CustomOptions implements OptionsFactory{

        @Override
        public Options getOptions(){
            return new Options().setHeadless(false);
        }
    }
    //PRUEBAS HU 19
    @Test
    void ValidClassTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        crearEtiqueta.fillForm("AutoEtiqueta nueva","Soy la nueva etiqueta", "#ff0000ff", "0", Boolean.FALSE);

        Locator newLabelCell = page.locator("td").filter(new Locator.FilterOptions().setHasText("AutoEtiqueta nueva"));

        assertTrue(newLabelCell.first().isVisible(),"La etiqueta no fue creada correctamente");

        despoblarBaseDeDatos.despoblarDatos();

    }


    @Test
    void invalidRefLengthTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        crearEtiqueta.fillForm("9999999999999999999999999999999","Soy la nueva etiqueta", "#ff0000ff", "0", Boolean.FALSE);

        boolean lengthError = page.locator("text=La longitud de la referencia ingresada es demasiado larga, ingrese menos de 30 caracteres").isVisible();

        assertTrue(lengthError,"El sistema tenia que tirar error de maximo de caracteres superado, pero no lo hizo y lo dejo pasar");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void invalidVoidRefTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();


        crearEtiqueta.fillForm("","Soy la nueva etiqueta", "#ff0000ff", "0", Boolean.FALSE);

        boolean voidRefError = page.locator("text=El campo 'Ref.' es obligatorio").isVisible();

        assertTrue(voidRefError,"El sistema tenia que tirar error de que el campo Ref es obligatorio, pero no lo hizo");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void ExistingRefTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();


        crearEtiqueta.fillForm("AutoEtiqueta 1","Soy la nueva etiqueta", "#ff0000ff", "0", Boolean.FALSE);

        boolean existingRefError = page.locator("text=Imposible añadir la categoría AutoEtiqueta 1 : Esta categoría ya existe para esta referencia").isVisible();

        assertTrue(existingRefError,"El sistema tenia que tirar error de que el campo Ref ya existe pero no lo hizo y dejo crearlo igual");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void voidDescriptionTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        crearEtiqueta.fillForm("AutoEtiqueta nueva","", "#ff0000ff", "0", Boolean.FALSE);

        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        Locator newLabelCell = page.locator("td").filter(new Locator.FilterOptions().setHasText("AutoEtiqueta nueva"));

        assertTrue(newLabelCell.first().isVisible(),"La etiqueta no fue creada correctamente");

        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void sqlInjectionTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();


        crearEtiqueta.fillForm("AutoEtiqueta nueva","SELECT * FROM llx_user WHERE rowid = '$id' AND '1'='1′", "#ff0000ff", "0", Boolean.FALSE);

        boolean descriptionError = page.locator("text=La descripcion ingresada no es valida").isVisible();

        assertTrue(descriptionError,"El sistema dejo ingresar una inyeccion SQL al sistema (indiferente de si funciona o no)");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void voidColorTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();


        crearEtiqueta.fillForm("AutoEtiqueta nueva","Soy la nueva etiqueta", "", "0", Boolean.FALSE);

        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        Locator newLabelCell = page.locator("td").filter(new Locator.FilterOptions().setHasText("AutoEtiqueta nueva"));

        assertTrue(newLabelCell.first().isVisible(),"La etiqueta no fue creada correctamente");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void invalidPosicionTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();



        try {
            crearEtiqueta.fillForm("AutoEtiqueta nueva","Soy la nueva etiqueta", "#ff0000ff", "cero", Boolean.FALSE);
        } catch (PlaywrightException e) {
            // Validar que el mensaje de error sea el esperado
            assertTrue(e.getMessage().contains("Cannot type text into input[type=number]"),
                    "El error no fue el esperado: " + e.getMessage());

            // O simplemente considerar la prueba como exitosa aquí
            System.out.println("Se lanzó correctamente un error al intentar ingresar letras en un campo numérico.");
        }


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void voidPosicionTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();


        crearEtiqueta.fillForm("AutoEtiqueta nueva","Soy la nueva etiqueta", "#ff0000ff", "", Boolean.FALSE);

        boolean posicionError = page.locator("text=El campo 'Posicion' es obligatorio \n" +
                " en el campo de posicion").isVisible();

        assertTrue(posicionError,"El sistema tenia que tirar error de que el campo Posicion es obligatorio, pero no lo hizo");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void changeAñadirEnTest(Page page){

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

        CrearEtiqueta crearEtiqueta = new CrearEtiqueta(page);
        crearEtiqueta.clickOnCrearEtiquetaButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        crearEtiqueta.fillForm("AutoEtiqueta nueva","Soy la nueva etiqueta", "#ff0000ff", "0", Boolean.TRUE);

        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        Locator newLabelCell = page.locator("td").filter(new Locator.FilterOptions().setHasText("AutoEtiqueta nueva"));

        assertTrue(newLabelCell.first().isVisible(),"La etiqueta no fue creada correctamente");

        despoblarBaseDeDatos.despoblarDatos();

    }
}
