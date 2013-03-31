package org.mage.test.cards.copy;

import mage.Constants;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * Lazav, Dimir Mastermind
 *
 * Legendary Creature — Shapeshifter 3/3, UUBB
 * Hexproof
 * Whenever a creature card is put into an opponent's graveyard from anywhere, you may have
 * Lazav, Dimir Mastermind become a copy of that card except its name is still
 * Lazav, Dimir Mastermind, it's legendary in addition to its other types, and
 * it gains hexproof and this ability.
 *
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
     * Tests copy card with static abilitiy gaining ability to other permanents
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

    /**
     * Tests copy Nightveil Specter
     *
     * Nightveil Specter
     * Creature — Specter 2/3, {U/B}{U/B}{U/B}
     * Flying
     * Whenever Nightveil Specter deals combat damage to a player, that player exiles the top card of his or her library.
     * You may play cards exiled with Nightveil Specter.
     *
     */
    @Test
    public void testCopyNightveilSpecter() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Constants.Zone.LIBRARY, playerB, "Silvercoat Lion",2);
        addCard(Constants.Zone.LIBRARY, playerB, "Nightveil Specter",1);
        skipInitShuffling();

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player puts the top card of his or her library into his or her graveyard.", playerB);

        attack(3, playerA, "Lazav, Dimir Mastermind");

        castSpell(3, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 2, 3);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.getAbilities().contains(FlyingAbility.getInstance()));
        Assert.assertTrue(lazav.getSubtype().contains("Specter"));
        Assert.assertTrue(lazav.getSupertype().contains("Legendary"));

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);

    }

    @Test
    public void testCopyMultipleTimes() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Constants.Zone.LIBRARY, playerB, "Silvercoat Lion",2);
        addCard(Constants.Zone.LIBRARY, playerB, "Nightveil Specter",1);
        skipInitShuffling();

        // Lazav becomes a Nightveil Specter
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player puts the top card of his or her library into his or her graveyard.", playerB);

        // Lazav becomes a Silvercoat Lion
        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player puts the top card of his or her library into his or her graveyard.", playerB);

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 2, 2);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.getSubtype().contains("Cat"));
        Assert.assertTrue(lazav.getSupertype().contains("Legendary"));

    }
}
