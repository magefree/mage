package org.mage.test.serverside.performance;

import mage.abilities.keyword.InfectAbility;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.counters.CounterType;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.remote.traffic.ZippedObjectImpl;
import mage.utils.CompressUtil;
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
        PermanentImpl permanent = new PermanentCard(cardInfo.getCard(), playerA.getId(), currentGame);
        currentGame.addPermanent(permanent);

        Object compressed = CompressUtil.compress(permanent);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        PermanentImpl uncompressed = (PermanentImpl) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", permanent.getName(), uncompressed.getName());
    }

    @Test
    public void test_PermanentImpl_MarkedDamageInfo() {
        CardInfo cardInfo = CardRepository.instance.findCard("Balduvian Bears");
        PermanentImpl permanent = new PermanentCard(cardInfo.getCard(), playerA.getId(), currentGame);
        currentGame.addPermanent(permanent);

        // mark damage from infected ability
        permanent.addAbility(InfectAbility.getInstance(), currentGame);
        permanent.markDamage(1, permanent.getId(), currentGame, false, false);

        // test compress (it uses default java serialization)
        Object compressed = CompressUtil.compress(permanent);
        Assert.assertTrue("Must be zip", compressed instanceof ZippedObjectImpl);
        PermanentImpl uncompressed = (PermanentImpl) CompressUtil.decompress(compressed);
        Assert.assertEquals("Must be same", permanent.getName(), uncompressed.getName());

        // ensure that it was marked damage
        permanent.applyDamage(currentGame);
        Assert.assertEquals("Must get infected counter", 1, permanent.getCounters(currentGame).getCount(CounterType.M1M1));
    }

}
