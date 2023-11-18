package org.mage.test.utils;

import mage.MageObject;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CommanderCardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.GameLog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class CardHintsTest extends CardTestCommanderDuelBase {

    // html logs/names used all around xmage (game logs, chats, choose dialogs, etc)
    // usage steps:
    // * server side: find game object for logs;
    // * server side: prepare html compatible log (name [id] + color + additional info)
    // * client side: inject additional elements for popup support (e.g. "a" with "href")
    // * client side: process mouse move over a href and show object data like a card popup

    private void assertObjectHtmlLog(String originalLog, String needVisibleColorPart, String needVisibleNormalPart, String needId) {
        String needVisibleFull = needVisibleColorPart;
        if (!needVisibleNormalPart.isEmpty()) {
            needVisibleFull += !needVisibleFull.isEmpty() ? " " : "";
            needVisibleFull += needVisibleNormalPart;
        }
        String mesPrefix = needVisibleFull + ": ";
        String mesPostfix = " in " + originalLog;

        // simple check
        Assert.assertTrue(mesPrefix + "can't find color part" + mesPostfix, originalLog.contains(needVisibleColorPart));
        Assert.assertTrue(mesPrefix + "can't find normal part" + mesPostfix, originalLog.contains(needVisibleNormalPart));
        Assert.assertTrue(mesPrefix + "can't find id" + mesPostfix, originalLog.contains(needId));

        // html check
        Element html = Jsoup.parse(originalLog);
        Assert.assertEquals(mesPrefix + "can't find full text" + mesPostfix, needVisibleFull, html.text());
        Element htmlFont = html.getElementsByTag("font").stream().findFirst().orElse(null);
        Assert.assertNotNull(mesPrefix + "can't find tag [font]" + mesPostfix, htmlFont);
        Assert.assertNotEquals(mesPrefix + "can't find attribute [color]" + mesPostfix, "", htmlFont.attr("color"));
        Assert.assertEquals(mesPrefix + "can't find attribute [object_id]" + mesPostfix, needId, htmlFont.attr("object_id"));

        // improved log from client (with href and popup support)
        String popupLog = GameLog.injectPopupSupport(originalLog);
        html = Jsoup.parse(popupLog);
        Assert.assertEquals(mesPrefix + "injected, can't find full text" + mesPostfix, needVisibleFull, html.text());
        // href
        Element htmlA = html.getElementsByTag("a").stream().findFirst().orElse(null);
        Assert.assertNotNull(mesPrefix + "injected, can't find tag [a]" + mesPostfix, htmlA);
        Assert.assertTrue(mesPrefix + "injected, can't find attribute [href]" + mesPostfix, htmlA.attr("href").startsWith("#"));
        Assert.assertEquals(mesPrefix + "injected, popup tag [a] must contains colored part only" + mesPostfix, needVisibleColorPart, htmlA.text());
        // object
        htmlFont = html.getElementsByTag("font").stream().findFirst().orElse(null);
        Assert.assertNotNull(mesPrefix + "injected, can't find tag [font]" + mesPostfix, htmlFont);
        Assert.assertNotEquals(mesPrefix + "can't find attribute [color]" + mesPostfix, "", htmlFont.attr("color"));
        Assert.assertEquals(mesPrefix + "can't find attribute [object_id]" + mesPostfix, needId, htmlFont.attr("object_id"));
    }

    private void assertObjectSupport(MageObject object) {
        String shortId = String.format("[%s]", object.getId().toString().substring(0, 3));

        // original mode with both color and normal parts (name + id)
        String log = GameLog.getColoredObjectIdName(object, null);
        assertObjectHtmlLog(
                log,
                object.getName(),
                shortId,
                object.getId().toString()
        );

        // custom mode with both color and normal parts
        String customName = "custom" + shortId + "name";
        String customPart = "xxx";
        log = GameLog.getColoredObjectIdName(object.getColor(currentGame), object.getId(), customName, customPart, null);
        assertObjectHtmlLog(log, customName, customPart, object.getId().toString());

        // custom mode with colored part only
        customName = "custom" + shortId + "name";
        customPart = "";
        log = GameLog.getColoredObjectIdName(object.getColor(currentGame), object.getId(), customName, customPart, null);
        assertObjectHtmlLog(log, customName, customPart, object.getId().toString());

        // custom mode with normal part only (popup will not work in GUI due empty a-href part)
        customName = "";
        customPart = "xxx";
        log = GameLog.getColoredObjectIdName(object.getColor(currentGame), object.getId(), customName, customPart, null);
        assertObjectHtmlLog(log, customName, customPart, object.getId().toString());
    }

    @Test
    public void test_ObjectNamesInHtml() {
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Grizzly Bears"); // card
        addCard(Zone.BATTLEFIELD, playerA, "Mountain"); // permanent
        addCard(Zone.COMMAND, playerA, "Soldier of Fortune"); // commander
        // diff names
        addCustomCardWithAbility("name normal", playerA, FlyingAbility.getInstance());
        addCustomCardWithAbility("123", playerA, FlyingAbility.getInstance());
        addCustomCardWithAbility("", playerA, FlyingAbility.getInstance());
        addCustomCardWithAbility("special \" name 1", playerA, FlyingAbility.getInstance());
        addCustomCardWithAbility("\"special name 2", playerA, FlyingAbility.getInstance());
        addCustomCardWithAbility("special name 3\"", playerA, FlyingAbility.getInstance());
        addCustomCardWithAbility("\"special name 4\"", playerA, FlyingAbility.getInstance());

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        // collect all possible objects and test logs with it
        List<MageObject> sampleObjects = new ArrayList<>();
        sampleObjects.addAll(currentGame.getBattlefield().getAllPermanents());
        sampleObjects.addAll(playerA.getHand().getCards(currentGame));
        sampleObjects.addAll(currentGame.getCommandersIds(playerA, CommanderCardType.ANY, false)
                .stream()
                .map(c -> currentGame.getObject(c))
                .collect(Collectors.toList()));
        Assert.assertEquals(3 + 7 + 1, sampleObjects.size()); // defaul commander game already contains +1 commander

        sampleObjects.forEach(this::assertObjectSupport);
    }
}
