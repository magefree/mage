/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.control;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GainControlTargetEffectTest extends CardTestPlayerBase {

    /**
     * Checks if control has changed and the controlled creature has Haste
     */
    @Test
    public void testPermanentControlEffect() {
        // When Smelt-Ward Gatekeepers enters the battlefield, if you control two or more Gates, gain control of target creature an opponent controls until end of turn. Untap that creature. That creature gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Smelt-Ward Gatekeepers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Boros Guildgate", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Smelt-Ward Gatekeepers");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // under control
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
    }

    /**
     * I gained control of my opponent's Glen Elendra Archmage with Vedalken
     * Shackles. After I sacrificed it to counter a spell, it Persisted back to
     * my battlefield, but it should return under its owner's control. Maybe a
     * Persist problem, but I am thinking Vedalken Shackles doesn't realize that
     * it is a different object when it returns from the graveyard instead.
     */
    @Test
    public void testGainControlOfCreatureWithPersistEffect() {
        // {2},{T}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Shackles", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Lightning Strike deals 3 damage to any target.
        addCard(Zone.HAND, playerB, "Lightning Strike", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        // Flying
        // {U}, Sacrifice Glen Elendra Archmage: Counter target noncreature spell.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerB, "Glen Elendra Archmage");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as {this} remains tapped.", "Glen Elendra Archmage");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Strike", playerA);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{U}, Sacrifice {this}: Counter target noncreature spell.", "Lightning Strike");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Strike", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // under control of the owner after persist triggered
        assertPermanentCount(playerA, "Glen Elendra Archmage", 0);
        assertPermanentCount(playerB, "Glen Elendra Archmage", 1);

    }

    /**
     * The shackles can maintain control of Mutavault indefinitely, even when
     * it's not a creature.
     */
    @Test
    public void testKeepControlOfMutavault() {
        // {2},{T}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Shackles", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // {T}: Add {C}.
        // {1}: Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.
        addCard(Zone.BATTLEFIELD, playerB, "Mutavault", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}:");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}: Gain control", "Mutavault");

        setChoice(playerA, false); // Don't untap the Shackles
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // under control of Shackles even if it's no longer a creature
        assertPermanentCount(playerB, "Mutavault", 0);
        assertPermanentCount(playerA, "Mutavault", 1);
    }
    
    /**
     * Steel Golem, once donated to another player does not disable their ability to play creature cards.
     */
    @Test
    public void testDonateSteelGolem() {
         // You can't cast creature spells.
        addCard(Zone.HAND, playerA, "Steel Golem", 1); // Creature 3/4 {3}
        // Target player gains control of target permanent you control.
        addCard(Zone.HAND, playerA, "Donate", 1); // Sorcery {2}{U}         
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steel Golem");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Donate", playerB);
        addTarget(playerA, "Steel Golem");

        checkPlayableAbility("Steel Golem stops casting", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Silvercoat", false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Donate", 1);

        assertPermanentCount(playerA, "Steel Golem", 0);
        assertPermanentCount(playerB, "Steel Golem", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1);
    }
    
    /**
     * Reported bug:
     * Skyfire Kirin was allowed to steal a creature with a different CMC than the card cast for it.
     * Played a 5 CMC creature and stole a 3 CMC creature.
    */
    @Test
    public void testSkyfireKirinStealCreatureDifferentCMC() {
        /*
        Skyfire Kirin {2}{R}{R}
        Legendary Creature - Kirin Spirit 3/3
        Flying
        Whenever you cast a Spirit or Arcane spell, you may gain control of target creature with that spell's converted mana cost until end of turn.
        */
        String sKirin = "Skyfire Kirin";
        
        /*
        Ore Gorger {3}{R}{R}
        Creature — Spirit 3/1
        Whenever you cast a Spirit or Arcane spell, you may destroy target nonbasic land.
        */
        String oGorger = "Ore Gorger"; 
        
        /*
        Leovold, Emissary of Trest {B}{G}{U}
         Legendary Creature — Elf Advisor 3/3
        Each opponent can't draw more than one card each turn.
        Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        */
        String leovold = "Leovold, Emissary of Trest";

        addCard(Zone.HAND, playerA, oGorger);
        addCard(Zone.BATTLEFIELD, playerA, sKirin);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        addCard(Zone.BATTLEFIELD, playerB, leovold);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, oGorger);
        // Option to gain control is not even given since the Gorger is 5 mana but the only possible target (Leovold) is 3.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, oGorger, 1);
        assertPermanentCount(playerA, leovold, 0); // should not have gained control
        assertPermanentCount(playerB, leovold, 1); // still under playerB control
        assertHandCount(playerB, 0); // leovold ability should not have triggered due to not targetted, so no extra cards
    }

    /**
     * Skyfire Kirin should steal be able to steal creatures with same CMC.
     */
    @Test
    public void testSkyfireKirinStealCreatureSameCMC() {
        /*
        Skyfire Kirin {2}{R}{R}
        Legendary Creature - Kirin Spirit 3/3
        Flying
        Whenever you cast a Spirit or Arcane spell, you may gain control of target creature with that spell's converted mana cost until end of turn.
        */
        String sKirin = "Skyfire Kirin";
        
        /*
        Ore Gorger {3}{R}{R}
        Creature — Spirit 3/1
        Whenever you cast a Spirit or Arcane spell, you may destroy target nonbasic land.
        */
        String oGorger = "Ore Gorger"; 
        
        /*
         Angel of Light {4}{W}
        Creature — Angel (3/3)
         Flying, vigilance
        */
        String aLight = "Angel of Light"; // 5 cmc creature, so valid to steal with Ore Gorger
        
        addCard(Zone.BATTLEFIELD, playerA, sKirin);
        addCard(Zone.HAND, playerA, oGorger);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, aLight);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, oGorger);
        setChoice(playerA, true); // opt to use Kirin's ability
        addTarget(playerA, aLight); // target Angel of Light with Kirin's take control ability
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, oGorger, 1);
        assertPermanentCount(playerA, aLight, 1); // should have gained control of Angel
        assertPermanentCount(playerB, aLight, 0); // Angel no longer under opponent's control
    }
}
