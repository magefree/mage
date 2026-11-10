package org.mage.test.utils;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.CommanderDuel;
import mage.game.Game;
import mage.game.GameException;
import mage.game.events.TableEvent;
import mage.game.mulligan.MulliganType;
import mage.game.permanent.Permanent;
import mage.util.GameLog;
import mage.view.GameView;
import mage.view.PlayerView;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Player names inside html texts of the game (game logs, card info, dialogs).
 * <p>
 * A player name is user input: on an anonymous server any symbols are allowed in it (only the length is checked),
 * so a name must be shown as is, without breaking the html text around it. Texts with html must show the name
 * literally, texts without html (window titles, tables, plain labels) must keep the name unchanged.
 * <p>
 * Real names from prod: "_Xyou_Du*&" (client crash #12939, #12954, #13132, #13170 by commander damage info),
 * "I<3Dragons", "M'zuka", "Artemis{G/P}".
 *
 * @author JayDi85
 */
@Ignore // TODO: rework html usage/protection and enable
public class GameLogPlayerNamesTest extends CardTestPlayerAPIImpl {

    // real prod name, ends with an ampersand
    private static final String NAME_A = "_Xyou_Du*&";
    // html tags, html entity, quotes, mana symbol, non-ascii and emoji
    private static final String NAME_B = "<b>Bold</b> I<3 'q' \"q\" a&amp;b {G/P} bøvs 🦍";

    private static final List<String> NAMES = Arrays.asList(
            NAME_A,
            NAME_B,
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
            "Dan The Man",
            "bøvsprutbøvs",
            "🦍🦍🦍"
    );

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new CommanderDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 40, 7);

        playerA = createPlayer(game, NAME_A, "CommanderDuel.dck");
        playerB = createPlayer(game, NAME_B, "CommanderDuel.dck");
        return game;
    }

    /**
     * Text of html as the client's game log shows it (Swing html render)
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

    private static void checkVisibleName(List<String> errors, String info, String html, String name) {
        String text = getVisibleText(html);
        if (!text.contains(name)) {
            errors.add(info + ": the name must be shown as is" + "\n name: " + name + "\n text: " + text + "\n html: " + html);
        }
    }

    private static void assertVisibleName(String info, String html, String name) {
        List<String> errors = new ArrayList<>();
        checkVisibleName(errors, info, html, name);
        Assert.assertTrue(String.join("\n", errors), errors.isEmpty());
    }

    @Test
    public void test_GameLog_ColoredPlayerName() {
        List<String> errors = new ArrayList<>();
        for (String name : NAMES) {
            checkVisibleName(errors, "player name", GameLog.getColoredPlayerName(name), name);
        }
        Assert.assertTrue("found broken names (" + errors.size() + "):\n" + String.join("\n", errors), errors.isEmpty());
    }

    @Test
    public void test_GameLog_PlayerLogName() {
        assertVisibleName("player A log name", playerA.getLogName(), NAME_A);
        assertVisibleName("player B log name", playerB.getLogName(), NAME_B);
    }

    @Test
    public void test_GameLog_CastSpellTargetingPlayer() {
        List<String> logs = new ArrayList<>();
        currentGame.addTableEventListener(event -> {
            if (event.getEventType() == TableEvent.EventType.INFO) {
                logs.add(event.getMessage());
            }
        });

        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 40 - 3);

        // the cast line has both names: the caster and the target player
        String castLog = logs.stream()
                .filter(log -> log.contains(" casts ") && log.contains("Lightning Bolt"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull("cast log must exist, logs:\n" + String.join("\n", logs), castLog);
        assertVisibleName("cast log, caster", castLog, NAME_A);
        assertVisibleName("cast log, target player", castLog, NAME_B);
    }

    @Test
    public void test_CardInfo_CommanderDamage() {
        // commander damage info is shown in the commander's card text, prod client crash #12939 by the name NAME_A
        addCard(Zone.COMMAND, playerA, "Isamaru, Hound of Konda"); // {W}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isamaru, Hound of Konda");

        attack(3, playerA, "Isamaru, Hound of Konda");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 40 - 2);

        Permanent commander = getPermanent("Isamaru, Hound of Konda", playerA);
        String damageInfo = commander.getRules(currentGame).stream()
                .filter(rule -> rule.contains("combat damage to player"))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull("commander damage info must exist, rules:\n"
                + String.join("\n", commander.getRules(currentGame)), damageInfo);
        assertVisibleName("commander damage info", damageInfo, NAME_B);
    }

    @Test
    public void test_PlainName_GameView() {
        // plain texts (window titles, labels, tables) get the name as is - html escaping is for html texts only
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        GameView gameView = new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
        List<String> names = gameView.getPlayers().stream().map(PlayerView::getName).collect(Collectors.toList());
        Assert.assertTrue("player A name must be kept, found: " + names, names.contains(NAME_A));
        Assert.assertTrue("player B name must be kept, found: " + names, names.contains(NAME_B));
    }
}
