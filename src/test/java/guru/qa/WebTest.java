package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import guru.qa.data.Locale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class WebTest {
    @BeforeAll
    public static void setUp() {
        Configuration.holdBrowserOpen = true;
    }
    @ValueSource(strings = {"MacBook Pro", "MacBook Air"})
    @ParameterizedTest(name = "Check macbook in store {0}")
    void appleSearchCollaborativeTest(String testData) {
        open("https://www.apple.com/");
        $("#ac-gn-link-search").click();
        $("#ac-gn-searchform-input").setValue(testData);
        $("#ac-gn-searchform-input").setValue(testData).pressEnter();
        $("#exploreCurated")
                .shouldHave(text(testData));

    }

    @CsvSource( {
            "MacBook Pro, Our most powerful notebooks supercharged by M1 and M2 chips",
            "MacBook Air, Our most portable laptops, supercharged by Apple silicon"
    })

    @ParameterizedTest(name = "Check macbook in store {0}")
    void appleSearchTestWithDescription(String SearchQuery, String Description ) {
        open("https://www.apple.com/");
        $("#ac-gn-link-search").click();
        $("#ac-gn-searchform-input").setValue(SearchQuery);
        $("#ac-gn-searchform-input").setValue(SearchQuery).pressEnter();
        $("#exploreCurated")
                .shouldHave(text(Description));

    }

    static Stream<Arguments> SelenideButtonsText() {
        return Stream.of(
                Arguments.of(Locale.EN, List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes")),
                Arguments.of(Locale.RU, List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы"))
        );
    }
    @MethodSource
    @ParameterizedTest(name = "check to see locale buttons: {0}")
    void SelenideButtonsText(Locale locale, List<String> buttonsTexts) {
        open("https://selenide.org/");
        $$("#languages a").find(text(locale.name())).click();
        $$(".main-menu-pages a").filter(visible)
                .shouldHave(CollectionCondition.texts(buttonsTexts));

    }

    @EnumSource(Locale.class)
    @ParameterizedTest
    void checkLocaleTest(Locale locale) {
        open("https://selenide.org/");
        $$("#languages a").find(text(locale.name())).shouldBe(visible);

    }
}
