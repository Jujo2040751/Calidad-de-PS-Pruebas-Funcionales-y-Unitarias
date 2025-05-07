import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import pageobjects.*;

import static org.junit.jupiter.api.Assertions.*;


@UsePlaywright(HU18Tests.CustomOptions.class)
public class HU18Tests {

    public static class CustomOptions implements OptionsFactory{

        @Override
        public Options getOptions(){
            return new Options().setHeadless(false);
        }
    }
    //PRUEBAS HU 18
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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        modificarEtiqueta.fillForm("AutoEtiqueta 1","Soy la primer etiqueta generada", "#ff0000ff", "0", Boolean.FALSE);

        boolean isLabelChanged = page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("ff0000")).isVisible();

        assertTrue(isLabelChanged,"La etiqueta no fue modificada correctamente");


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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        modificarEtiqueta.fillForm("9999999999999999999999999999999","Soy la primer etiqueta generada", "#ff0000ff", "0", Boolean.FALSE);

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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();


        modificarEtiqueta.fillForm("","Soy la primer etiqueta generada", "#ff0000ff", "0", Boolean.FALSE);

        boolean voidRefError = page.locator("text=El campo 'Etiqueta' es obligatorio").isVisible();

        assertTrue(voidRefError,"El sistema tenia que tirar error de que el campo Ref es obligatorio, pero no lo hizo");


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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        modificarEtiqueta.fillForm("AutoEtiqueta 1","", "#ff0000ff", "0", Boolean.FALSE);

        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        boolean isLabelChanged = page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("ff0000")).isVisible();

        assertTrue(isLabelChanged,"La etiqueta no fue modificada correctamente");

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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();


        modificarEtiqueta.fillForm("AutoEtiqueta 1","SELECT * FROM llx_user WHERE rowid = '$id' AND '1'='1′", "#ff0000ff", "0", Boolean.FALSE);

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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();
        //page.pause();

        modificarEtiqueta.fillForm("AutoEtiqueta 1","Soy la primer etiqueta generada", "", "0", Boolean.FALSE);

        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        boolean isLabelChanged = page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Soy la primer etiqueta generada")).isVisible();

        assertTrue(isLabelChanged,"La etiqueta no fue modificada correctamente");


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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();


        modificarEtiqueta.fillForm("AutoEtiqueta 1","Soy la primer etiqueta generada", "#ff0000ff", "cero", Boolean.FALSE);

        boolean posicionError = page.locator("text=Debe ingresar un numero valido \n" +
                " en el campo de posicion").isVisible();

        assertTrue(posicionError,"El sistema dejo ingresar algo diferente a un numero en el campo posicion)");


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

        InformacionEtiqueta1 informacionEtiqueta1 = new InformacionEtiqueta1(page);
        informacionEtiqueta1.clickOnAutoEtiqueta1();



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();


        modificarEtiqueta.fillForm("AutoEtiqueta 1","Soy la primer etiqueta generada", "#ff0000ff", "", Boolean.FALSE);

        boolean posicionError = page.locator("text=El campo 'Posicion' es obligatorio \n" +
                " en el campo de posicion").isVisible();

        assertTrue(posicionError,"El sistema tenia que tirar error de que el campo Posicion es obligatorio, pero no lo hizo");


        despoblarBaseDeDatos.despoblarDatos();

    }

    @Test
    void changeEnTest(Page page){

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



        ModificarEtiqueta modificarEtiqueta = new ModificarEtiqueta(page);
        modificarEtiqueta.clickOnModificarButton();
        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        modificarEtiqueta.fillForm("AutoEtiqueta 1","Soy la primer etiqueta generada", "#ff0000ff", "0", Boolean.TRUE);

        //codigo diferente de prueba: #007f00ff
        //codigo rojo de prueba: #ff0000ff

        boolean isLabelChanged = page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("ff0000")).isVisible();

        assertTrue(isLabelChanged,"La etiqueta no fue modificada correctamente");

        despoblarBaseDeDatos.despoblarDatos();

    }
}
