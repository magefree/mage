package org.mage.test.serverside.performance;

import mage.abilities.keyword.InfectAbility;
import mage.cards.Card;
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
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SerializationTest extends CardTestPlayerBase {

    @Test
    public void test_PermanentImpl_Simple() {
        CardInfo cardInfo = CardRepository.instance.findCard("Balduvian Bears");
        Card newCard = cardInfo.getCard();
        Card permCard = CardUtil.getDefaultCardSideForBattlefield(newCard);
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
        Card permCard = CardUtil.getDefaultCardSideForBattlefield(newCard);
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
