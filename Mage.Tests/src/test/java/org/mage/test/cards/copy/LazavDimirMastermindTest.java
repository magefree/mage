package org.mage.test.cards.copy;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/*
    Lazav, Dimir Mastermind
    Legendary Creature — Shapeshifter 3/3, UUBB
    Hexproof Whenever a creature card is put into an opponent's graveyard from anywhere, you may have
    Lazav, Dimir Mastermind become a copy of that card except its name is still Lazav, Dimir Mastermind,
    it's legendary in addition to its other types, and it gains hexproof and this ability.

    @author LevelX2
 */
public class LazavDimirMastermindTest extends CardTestPlayerBase {

    /**
     * Tests copy simple creature
     */
    @Test
    public void testCopySimpleCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        // Codex Shredder - Artifact
        // {T}: Target player mills a card.
        // {5}, {T}, Sacrifice Codex Shredder: Return target card from your graveyard to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        // Flying 3/2
        addCard(Zone.LIBRARY, playerB, "Assault Griffin", 5);
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 3, 2);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.hasSubtype(SubType.GRIFFIN, currentGame));
        Assert.assertTrue("Lazav, Dimir Mastermind must have flying", lazav.getAbilities().contains(FlyingAbility.getInstance()));
    }

    /**
     * Tests copy card with static abilitiy gaining ability to other permanents
     */
    @Test
    public void testRatsHaveDeathtouch() {
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gutter Skulk", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        // Whenever another nontoken creature dies, you may put a 1/1 black Rat creature token onto the battlefield.
        // Rats you control have deathtouch.
        addCard(Zone.LIBRARY, playerB, "Ogre Slumlord", 5);
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 3, 3);
        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.hasSubtype(SubType.OGRE, currentGame));
        Assert.assertTrue(lazav.hasSubtype(SubType.ROGUE, currentGame));

        Permanent gutterSkulk = getPermanent("Gutter Skulk", playerA.getId());
        Assert.assertTrue("Gutter Skulk should have deathtouch but hasn't", gutterSkulk.getAbilities().contains(DeathtouchAbility.getInstance()));

    }

    /*
     * Tests copy Nightveil Specter
     *
     * Nightveil Specter Creature — Specter 2/3, {U/B}{U/B}{U/B}
     * Flying
     * Whenever Nightveil Specter deals combat damage to a player, that player exiles the
     * top card of their library. You may play cards exiled with Nightveil Specter.
     */
    @Test
    public void testCopyNightveilSpecter() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        addCard(Zone.LIBRARY, playerB, "Nightveil Specter", 1);
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);

        attack(3, playerA, "Lazav, Dimir Mastermind");

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 2, 3);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.getAbilities().contains(FlyingAbility.getInstance()));
        Assert.assertTrue(lazav.hasSubtype(SubType.SPECTER, currentGame));
        Assert.assertTrue(lazav.isLegendary());

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);

    }

    @Test
    public void testCopyMultipleTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        addCard(Zone.LIBRARY, playerB, "Nightveil Specter", 1);
        skipInitShuffling();

        // Lazav becomes a Nightveil Specter
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);

        // Lazav becomes a Silvercoat Lion
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 2, 2);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.hasSubtype(SubType.CAT, currentGame));
        Assert.assertTrue(lazav.isLegendary());

    }

    /**
     * Tests old copy is discarded after reanmiation of Lazav
     */
    @Test
    public void testCopyAfterReanimation() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        // Codex Shredder - Artifact
        // {T}: Target player mills a card.
        // {5}, {T}, Sacrifice Codex Shredder: Return target card from your graveyard to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        // Flying 3/2
        addCard(Zone.LIBRARY, playerB, "Assault Griffin", 1);
        // Target opponent sacrifices a creature. You gain life equal to that creature's toughness.
        addCard(Zone.HAND, playerB, "Tribute to Hunger");

        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Tribute to Hunger");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Reanimate", "Lazav, Dimir Mastermind");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Tribute to Hunger", 1);
        assertGraveyardCount(playerA, "Reanimate", 1);

        assertLife(playerA, 16); // -4 from Reanmiate
        assertLife(playerB, 22); // +3 from Tribute to Hunger because Lazav is 3/2

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 3, 3);
        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertFalse(lazav.hasSubtype(SubType.GRIFFIN, currentGame)); // no Griffin type
        Assert.assertFalse("Lazav, Dimir Mastermind must have flying", lazav.getAbilities().contains(FlyingAbility.getInstance()));


    }

    /**
     * Tests if Lazav remains a copy of the creature after it is exiled
     */
    @Test
    public void testCopyCreatureExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind", 1);
        // Codex Shredder - Artifact
        // {T}: Target player mills a card.
        // {5}, {T}, Sacrifice Codex Shredder: Return target card from your graveyard to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Codex Shredder", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Rest in Peace", 1);

        // Flying 3/2
        addCard(Zone.LIBRARY, playerB, "Assault Griffin", 5);
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player mills a card.", playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Rest in Peace");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lazav, Dimir Mastermind", 1);
        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 3, 2);

        Permanent lazav = getPermanent("Lazav, Dimir Mastermind", playerA.getId());
        Assert.assertTrue(lazav.hasSubtype(SubType.GRIFFIN, currentGame));
        Assert.assertTrue("Lazav, Dimir Mastermind must have flying", lazav.getAbilities().contains(FlyingAbility.getInstance()));
    }
}
