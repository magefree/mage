package mage.client.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChineseUiTest {

    private static final String LANGUAGE_PROPERTY = "xmage.ui.lang";

    private String originalLanguage;

    @Before
    public void rememberLanguage() {
        originalLanguage = System.getProperty(LANGUAGE_PROPERTY);
    }

    @After
    public void restoreLanguage() {
        if (originalLanguage == null) {
            System.clearProperty(LANGUAGE_PROPERTY);
        } else {
            System.setProperty(LANGUAGE_PROPERTY, originalLanguage);
        }
    }

    @Test
    public void keepsEnglishUiUnchangedByDefault() {
        System.clearProperty(LANGUAGE_PROPERTY);

        assertFalse(ChineseUi.isEnabled());
        assertEquals("Deck Editor", ChineseUi.tr("Deck Editor"));
    }

    @Test
    public void recognizesSupportedChineseLanguageCodes() {
        System.setProperty(LANGUAGE_PROPERTY, "zh_CN");
        assertTrue(ChineseUi.isEnabled());

        System.setProperty(LANGUAGE_PROPERTY, "zh-CN");
        assertTrue(ChineseUi.isEnabled());

        System.setProperty(LANGUAGE_PROPERTY, "zh");
        assertTrue(ChineseUi.isEnabled());
    }

    @Test
    public void translatesKnownAndDynamicUiText() {
        System.setProperty(LANGUAGE_PROPERTY, "zh_CN");

        assertEquals("套牌编辑器", ChineseUi.tr("Deck Editor"));
        assertEquals("玩家 2（你）", ChineseUi.tr("Player 2 (You)"));
        assertEquals("内存使用： 42%", ChineseUi.tr("Memory used: 42%"));
    }

    @Test
    public void doesNotTranslateCardNamesOrUnknownServerText() {
        System.setProperty(LANGUAGE_PROPERTY, "zh_CN");

        assertEquals("Lightning Bolt", ChineseUi.tr("Lightning Bolt"));
        assertEquals("Doctor Doom, Unrivaled", ChineseUi.tr("Doctor Doom, Unrivaled"));
    }
}
