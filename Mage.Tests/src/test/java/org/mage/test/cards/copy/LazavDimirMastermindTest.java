package org.mage.test.cards.copy;

import mage.Constants;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class LazavDimirMastermindTest extends CardTestPlayerBase {

    /**
     * Tests copy simple creature
     */
    @Test
    public void testCopySimpleCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Constants.Zone.LIBRARY, playerB, "Assault Griffin",5);
        skipInitShuffling();
        
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player puts the top card of his or her library into his or her graveyard.", playerB);

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 3, 2);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.getSubtype().contains("Griffin"));
        Assert.assertTrue("Lazav, Dimir Mastermind must have flying",lazav.getAbilities().contains(FlyingAbility.getInstance()));
    }

    /**
     * Tests copy simple creature
     */
    @Test
    public void testRatsHaveDeathtouch() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Gutter Skulk", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        // Whenever another nontoken creature dies, you may put a 1/1 black Rat creature token onto the battlefield.
        // Rats you control have deathtouch.
        addCard(Constants.Zone.LIBRARY, playerB, "Ogre Slumlord",5);
        skipInitShuffling();

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player puts the top card of his or her library into his or her graveyard.", playerB);

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 3, 3);
        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.getSubtype().contains("Ogre"));
        Assert.assertTrue(lazav.getSubtype().contains("Rogue"));

        Permanent gutterSkulk = getPermanent("Gutter Skulk", playerA.getId());
        Assert.assertTrue("Gutter Skulk should have deathtouch but hasn't", gutterSkulk.getAbilities().contains(DeathtouchAbility.getInstance()));

    }
}
