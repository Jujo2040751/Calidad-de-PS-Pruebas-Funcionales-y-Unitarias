package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class Login {

    private final Page page;
    private final Locator searchLoginUsername;
    private final Locator searchLoginPassword;
    private final Locator searchLoginButton;

    public Login(Page page){
        this.page = page;
        this.searchLoginUsername = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Login"));
        this.searchLoginPassword =  page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Contraseña"));
        this.searchLoginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Conexión"));
    }

    public void navigate(){
        page.navigate("http://localhost/dolibarr/");
    }

    public void search(String username, String password){
    this.searchLoginUsername.fill(username);
    this.searchLoginPassword.fill(password);
    this.searchLoginButton.click();
    }
}
