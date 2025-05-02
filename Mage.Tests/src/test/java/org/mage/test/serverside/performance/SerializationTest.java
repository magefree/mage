package org.mage.test.serverside.performance;

import mage.abilities.Ability;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.mulligan.LondonMulligan;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.remote.traffic.ZippedObjectImpl;
import mage.util.CardUtil;
import mage.utils.CompressUtil;
import mage.util.ThreadUtils;
import mage.view.GameView;
import org.jboss.remoting.serialization.impl.java.ClearableObjectOutputStream;
import org.jboss.serial.io.JBossObjectInputStream;
import org.jboss.serial.io.JBossObjectOutputStream;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class SerializationTest extends CardTestPlayerBase {

    @Test
    public void test_PermanentImpl_Simple() {
        CardInfo cardInfo = CardRepository.instance.findCard("Balduvian Bears");
        Card newCard = cardInfo.createCard();
        Card permCard = CardUtil.getDefaultCardSideForBattlefield(currentGame, newCard);
        PermanentImpl permanent = new PermanentCard(permCard, playerA.getId(), currentGame);
        currentGame.addPermanent(permanent, 0);

        Object compressed = CompressUtil.compress(permanent);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        PermanentImpl uncompressed = (PermanentImpl) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", permanent.getName(), uncompressed.getName());
    }

    @Test
    public void test_PermanentImpl_MarkedDamageInfo() {
        CardInfo cardInfo = CardRepository.instance.findCard("Balduvian Bears");
        Card newCard = cardInfo.createCard();
        Card permCard = CardUtil.getDefaultCardSideForBattlefield(currentGame, newCard);
        PermanentImpl permanent = new PermanentCard(permCard, playerA.getId(), currentGame);
        currentGame.addPermanent(permanent, 0);

        // mark damage from infected ability
        permanent.addAbility(InfectAbility.getInstance(), null, currentGame);
        permanent.markDamage(1, permanent.getId(), null, currentGame, false, false);

        // test compress (it uses default java serialization)
        Object compressed = CompressUtil.compress(permanent);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        PermanentImpl uncompressed = (PermanentImpl) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", permanent.getName(), uncompressed.getName());

        // ensure that it was marked damage
        permanent.applyDamage(currentGame);
        Assert.assertEquals("Must get infected counter", 1, permanent.getCounters(currentGame).getCount(CounterType.M1M1));
    }

    private void processSingleCard(CardInfo cardInfo) {
        // compress each card's part
        Card newCard = cardInfo.createCard();
        CardUtil.getObjectPartsAsObjects(newCard).stream()
                .map(Card.class::cast)
                .forEach(card -> {
                    Card testPermanent = null;
                    if (!card.isInstantOrSorcery()) {
                        Card battlefieldCard = CardUtil.getDefaultCardSideForBattlefield(currentGame, card);
                        testPermanent = new PermanentCard(battlefieldCard, playerA.getId(), currentGame);
                    }

                    // card
                    {
                        Object compressed = CompressUtil.compress(card);
                        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
                        Card uncompressed = (Card) CompressUtil.decompress(compressed);
                        Assert.assertEquals("Must be same", card.getName(), uncompressed.getName());
                    }

                    // permanent
                    if (testPermanent != null) {
                        Object compressed = CompressUtil.compress(testPermanent);
                        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
                        Card uncompressed = (Card) CompressUtil.decompress(compressed);
                        Assert.assertEquals("Must be same", testPermanent.getName(), uncompressed.getName());
                    }
                });
    }

    private void processSingleCard(String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        processSingleCard(cardInfo);
    }

    @Ignore // WARNING, debug only, needs few minutes to execute, so run it manually
    @Test
    public void test_Single_AllCards() {
        // checking FULL cards list for serialization errors
        String filterSet = "";
        String filterCard = "";

        List<String> errorsList = new ArrayList<>();
        int checkedCount = 0;
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (!filterSet.isEmpty() && !set.getCode().equals(filterSet)) {
                continue;
            }
            checkedCount++;
            System.out.printf("Checking set %d of %d (%s)%n", checkedCount, Sets.getInstance().size(), set.getName());

            for (ExpansionSet.SetCardInfo info : set.getSetCardInfo()) {
                if (!filterCard.isEmpty() && !info.getName().equals(filterCard)) {
                    continue;
                }
                CardInfo cardInfo = CardRepository.instance.findCardsByClass(info.getCardClass().getCanonicalName()).stream().findFirst().orElse(null);
                try {
                    processSingleCard(cardInfo);
                } catch (Throwable e) {
                    System.out.println("Broken card: " + info.getName());
                    //e.printStackTrace(); // search exception errors in the logs
                    errorsList.add(info.getName());
                }
            }
        }

        if (!errorsList.isEmpty()) {
            Assert.fail("Found broken cards: " + errorsList.size() + "\n"
                    + errorsList.stream().sorted().collect(Collectors.joining("\n")));
        }
    }

    @Test
    public void test_Single_KentaroTheSmilingCat() {
        processSingleCard("Kentaro, the Smiling Cat");
    }

    @Test
    public void test_LondonMulligan() {
        LondonMulligan mulligan = new LondonMulligan(15);
        Object compressed = CompressUtil.compress(mulligan);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        LondonMulligan uncompressed = (LondonMulligan) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", mulligan.getFreeMulligans(), uncompressed.getFreeMulligans());
    }

    @Test
    public void test_Game() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        GameView gameView = getGameView(playerA);
        Object compressed = CompressUtil.compress(gameView);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        GameView uncompressed = (GameView) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", 1, uncompressed.getPlayers().get(0).getBattlefield().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_Choices_MustHaveProtectionFromKeySetUsage() {
        Map<String, Ability> abilityMap = new HashMap<>();
        abilityMap.put("Flying", FlyingAbility.getInstance());
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");

        choice.setChoices(abilityMap.keySet());
        Assert.fail("Can't be here");
    }

    @Test
    public void test_Choices_MustSupportSerialization() {
        // related issue: https://github.com/magefree/mage/issues/10115
        // copy-paste structure for the test (example from Atraxas Skitter)
        Map<String, Ability> abilityMap = new HashMap<>();
        abilityMap.put("Flying", FlyingAbility.getInstance());
        abilityMap.put("Vigilance", VigilanceAbility.getInstance());
        abilityMap.put("Deathtouch", DeathtouchAbility.getInstance());
        abilityMap.put("Lifelink", LifelinkAbility.getInstance());
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");

        choice.setChoices(new LinkedHashSet<>(abilityMap.keySet()));

        Object compressed = CompressUtil.compress(choice);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        Choice uncompressed = (Choice) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", choice.getChoices().size(), uncompressed.getChoices().size());
    }

    static class SerializationTestData {

        String name;
        GameView gameView;
        Class<? extends ObjectOutputStream> writerClass;
        Class<? extends ObjectInputStream> readerClass;

        void SerializationTest(
                String name,
                GameView gameView,
                Class<? extends ObjectOutputStream> writerClass,
                Class<? extends ObjectInputStream> readerClass
        ) {
            this.name = name;
            this.gameView = gameView;
            this.writerClass = writerClass;
            this.readerClass = readerClass;
        }
    }

    private void assertSerializationClass(
            String name,
            GameView gameView,
            Class<? extends ObjectOutputStream> writerClass,
            Class<? extends ObjectInputStream> readerClass
    ) {
        byte[] compressedGameView;
        GameView uncompressedGameView;

        // write
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Constructor<?> writerCon = writerClass.getConstructor(OutputStream.class);
            ObjectOutputStream writerStream = (ObjectOutputStream) writerCon.newInstance(outStream);
            writerStream.writeObject(gameView);
            compressedGameView = outStream.toByteArray();
            Assert.assertNotNull(compressedGameView);
            Assert.assertNotEquals(0, compressedGameView.length);
        } catch (Throwable t) {
            ThreadUtils.findRootException(t).printStackTrace(); // write full stack
            throw new IllegalStateException("Can't use [" + name + "] serialization on write: " + t.getMessage(), t);
        }

        // read
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedGameView);
            Constructor<?> readCon = readerClass.getConstructor(InputStream.class);
            ObjectInputStream readerStream = (ObjectInputStream) readCon.newInstance(inputStream);
            uncompressedGameView = (GameView) readerStream.readObject();
            Assert.assertNotNull(uncompressedGameView);
            Assert.assertEquals(1, uncompressedGameView.getMyHand().size());
            Assert.assertEquals("Grizzly Bears", uncompressedGameView.getMyHand().values().stream().findFirst().get().getName());
        } catch (Throwable t) {
            ThreadUtils.findRootException(t).printStackTrace(); // write full stack
            throw new IllegalStateException("Can't use [" + name + "] serialization on read: " + t.getMessage(), t);
        }
    }

    @Test
    @Ignore // TODO: enable to test migration to new java
    public void test_SerializationEngines_Java17Support() {
        // compatibility testing with new java version, see details at https://github.com/magefree/mage/issues/5862
        // original problem: jboss's inner stream classes (static code) used outdated access to private fields
        // must be run under java 9+ with --illegal-access=deny or under java 17+ without additional commands

        // WORKAROUND for migration (InaccessibleObjectException): if it's impossible to update library then use
        // additional java commands to open access to java modules: add-opens - https://stackoverflow.com/a/69160452/1276632

        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        GameView gameView = getGameView(playerA);
        Assert.assertEquals(1, gameView.getMyHand().size());
        Assert.assertEquals("Grizzly Bears", gameView.getMyHand().values().stream().findFirst().get().getName());

        // test diff serialization engines

        // java - current default serialization
        assertSerializationClass("java", gameView, ObjectOutputStream.class, ObjectInputStream.class);

        // jboss client/server - jboss inner code for real client/server app (original error from #5862)
        assertSerializationClass("jboss client/server", gameView, ClearableObjectOutputStream.class, ObjectInputStream.class);

        // jboss serial - jboss serilization (no longer used in the project)
        assertSerializationClass("jboss serial", gameView, JBossObjectOutputStream.class, JBossObjectInputStream.class);
    }
}
