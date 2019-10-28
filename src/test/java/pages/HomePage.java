package pages;

import org.openqa.selenium.By;

public class HomePage {
    private BaseFunc baseFunc;

    private final By TITLE = By.xpath(".//h1[contains(@class, 'headline__title')]");

    public HomePage(BaseFunc baseFunc) { //konstruktor
        this.baseFunc = baseFunc;
    }

    public void openFirstArticle() {
        //Find WebElement
        baseFunc.getElement(TITLE).click();

        //click
    }
}
