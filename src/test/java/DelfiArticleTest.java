import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DelfiArticleTest {

    private final By HOME_PAGE_TITLE = By.xpath(".//h1[contains(@class, 'text-size-22')]");
    private final By HOME_PAGE_COMMENTS = By.xpath(".//a[@class = 'comment-count text-red-ribbon']");
    private final By ARTICLE_PAGE_TITLE = By.xpath(".//h1[contains(@class,'text-size-md-30')]");
    private final By ARTICLE_PAGE_COMMENTS = By.xpath(".//a[contains(@class,'text-size-md-28')]");
    private final By COMMENT_PAGE_TITLE = By.xpath(".//h1[@class='article-title']/a");
    private final By ARTICLE = By.xpath(".//span[@class = 'text-size-22 d-block']");
    private final By COMMENTS_ANON = By.xpath(".//li[@class = 'as-link show-anon']/span/span");
    private final By COMMENTS_REG = By.xpath(".//li[@class = 'as-link is-active show-reg']/span/span");
    private final By TOTAL_ARTICLE_COMMENTS = By.xpath(".//a[@class = 'text-size-19 text-size-md-28 text-red-ribbon d-print-none']");
    private final Logger LOGGGER = LogManager.getLogger(DelfiArticleTest.class);

    int totalArticleCommentsParsed;
    int commentAnonToParse;
    int commentRegToParse;
    int totalCommentCount;

    String a = "commentsToCompareAnon";
    String b = "commentsToCompareReg";

    @Test
    public void titleAndCommentsTest() {
        LOGGGER.info("Set driver path");

        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        LOGGGER.info("Open Delfi Home Page");
        driver.get("http://rus.delfi.lv");

        LOGGGER.info("Find 1st article");
        WebElement article = driver.findElements(ARTICLE).get(1);

        LOGGGER.info("Find 1st article title");
        WebElement homePageTitle = article.findElement(HOME_PAGE_TITLE);

        LOGGGER.info("Save to string");
        String titleToCompare = homePageTitle.getText().trim(); //replace("  ", "");
        titleToCompare = titleToCompare.substring(0, titleToCompare.length() - 0);

        Integer commentsToCompare = 0;

        if (!article.findElements(HOME_PAGE_COMMENTS).isEmpty()) {

            WebElement homePageComments = article.findElement(HOME_PAGE_COMMENTS);

            String commentsToParse = homePageComments.getText();
            commentsToParse = commentsToParse.substring(1, commentsToParse.length() - 1);
            commentsToCompare = Integer.valueOf(commentsToParse);
        }

        homePageTitle.click();

        String apTitle = driver.findElement(ARTICLE_PAGE_TITLE).getText().trim();

        Assertions.assertEquals(titleToCompare, apTitle, "Wrong title on article page!");

        Integer apComments = Integer.valueOf(driver.findElement(ARTICLE_PAGE_COMMENTS).getText()
                .substring(1, driver.findElement(ARTICLE_PAGE_COMMENTS).getText().length() - 1));

        Assertions.assertEquals(commentsToCompare, apComments, "Comments count is not same as on Home Page!");

        String totalArticleComments = driver.findElement(TOTAL_ARTICLE_COMMENTS).getText();
        totalArticleCommentsParsed = Integer.parseInt(totalArticleComments.substring(1, totalArticleComments.length() -1));

        driver.findElement(ARTICLE_PAGE_COMMENTS).click();

        String cpTitle = driver.findElement(COMMENT_PAGE_TITLE).getText().trim();

        Assertions.assertEquals(titleToCompare, cpTitle, "cpTitle not compare!");


        if (!driver.findElements(COMMENTS_ANON).isEmpty()) {
            String commentCountAnon = driver.findElement(COMMENTS_ANON).getText();
            commentAnonToParse = Integer.parseInt(commentCountAnon.substring(1, commentCountAnon.length() - 1));
        }

        if (!driver.findElements(COMMENTS_REG).isEmpty()) {
            String commentCountReg = driver.findElement(COMMENTS_REG).getText();
            commentRegToParse = Integer.parseInt(commentCountReg.substring(1, commentCountReg.length() - 1));
        }

        totalCommentCount = commentAnonToParse + commentRegToParse;

        Assertions.assertEquals(totalArticleCommentsParsed, totalCommentCount, "Error");

        driver.quit();

    }
}