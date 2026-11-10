package org.mage.card.arcane;

import mage.util.GameLog;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Player names inside texts from the server as the client renders them: game log and chat lines (html with mana
 * symbols), card info lines in the card text box (own rules text parser).
 * <p>
 * A player name is user input: on an anonymous server any symbols are allowed in it (only the length is checked),
 * so a name must be shown as is: no html tags, entities or mana symbols made from it, no parser errors.
 * <p>
 * Real names from prod: "_Xyou_Du*&" (client crash #12939, #12954, #13132, #13170 by commander damage info),
 * "I<3Dragons", "M'zuka", "Artemis{G/P}".
 *
 * @author JayDi85
 */
@Ignore // TODO: rework html usage/protection and enable
public class PlayerNamesRenderTest {

    private static final List<String> NAMES = Arrays.asList(
            "_Xyou_Du*&",
            "<b>Bold</b> I<3 'q' \"q\" a&amp;b {G/P} bøvs 🦍",
            "I<3Dragons",
            "<b>Bold</b>",
            "<font color='red'>Red</font>",
            "M'zuka",
            "a \"quoted\" b",
            "x &amp; y",
            "&mdash;dash",
            "&lt;tag&gt;",
            "Artemis{G/P}",
            "{T}",
            "{this}",
            "Dan The Man",
            "bøvsprutbøvs",
            "🦍🦍🦍"
    );

    /**
     * Text of html as the client's chat / game log panel shows it (Swing html render)
     */
    private static String getVisibleText(String html) {
        HTMLEditorKit kit = new HTMLEditorKit();
        Document doc = kit.createDefaultDocument();
        try {
            kit.read(new StringReader("<html><body>" + html + "</body></html>"), doc, 0);
            return doc.getText(0, doc.getLength()).trim();
        } catch (IOException | BadLocationException e) {
            throw new IllegalStateException("Can't parse html: " + html, e);
        }
    }

    @Test
    public void test_GameLog_NameWithManaSymbols() {
        // the game log panel replaces mana symbols in the whole line (ChatPanelBasic.receiveMessage)
        List<String> errors = new ArrayList<>();
        for (String name : NAMES) {
            String serverLine = GameLog.getColoredPlayerName(name) + " casts Lightning Bolt";
            String clientLine = ManaSymbols.replaceSymbolsWithHTML(serverLine, ManaSymbols.Type.CHAT);
            String text = getVisibleText(clientLine);
            if (!text.contains(name)) {
                errors.add("game log line must show the name as is"
                        + "\n name: " + name + "\n text: " + text + "\n html: " + clientLine);
            }
        }
        Assert.assertTrue("found broken names (" + errors.size() + "):\n" + String.join("\n", errors), errors.isEmpty());
    }

    @Test
    public void test_CardInfo_CommanderDamage() {
        // the same info line as CommanderInfoWatcher adds to a commander's card text, parsed by the card text box
        List<String> errors = new ArrayList<>();
        for (String name : NAMES) {
            String rule = "<b>Commander</b> did 2 combat damage to player " + GameLog.getColoredPlayerName(name) + '.';
            TextboxRule textboxRule;
            try {
                textboxRule = TextboxRuleParser.parse(null, rule, "Isamaru, Hound of Konda");
            } catch (Exception e) {
                errors.add("card text parser must not fail on the name: " + name + "\n error: " + e);
                continue;
            }
            if (!textboxRule.text.contains(name)) {
                errors.add("card text must show the name as is"
                        + "\n name: " + name + "\n text: " + textboxRule.text + "\n rule: " + rule);
            }
        }
        Assert.assertTrue("found broken names (" + errors.size() + "):\n" + String.join("\n", errors), errors.isEmpty());
    }
}
