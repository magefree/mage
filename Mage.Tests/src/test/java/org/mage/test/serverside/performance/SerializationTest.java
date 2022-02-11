package org.mage.test.serverside.performance;

import mage.abilities.keyword.InfectAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.mulligan.LondonMulligan;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.remote.traffic.ZippedObjectImpl;
import mage.util.CardUtil;
import mage.utils.CompressUtil;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class SerializationTest extends CardTestPlayerBase {

    @Test
    public void test_PermanentImpl_Simple() {
        CardInfo cardInfo = CardRepository.instance.findCard("Balduvian Bears");
        Card newCard = cardInfo.getCard();
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
        Card newCard = cardInfo.getCard();
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
        Card newCard = cardInfo.getCard();
        CardUtil.getObjectPartsAsObjects(newCard).stream()
                .map(Card.class::cast)
                .forEach(card -> {
                    Card testCard = CardUtil.getDefaultCardSideForBattlefield(currentGame, newCard);
                    Card testPermanent = null;
                    if (!testCard.isInstantOrSorcery()) {
                        testPermanent = new PermanentCard(testCard, playerA.getId(), currentGame);
                    }

                    // card
                    {
                        Object compressed = CompressUtil.compress(testCard);
                        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
                        Card uncompressed = (Card) CompressUtil.decompress(compressed);
                        Assert.assertEquals("Must be same", testCard.getName(), uncompressed.getName());
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
}
