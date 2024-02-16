package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class UndyingTest extends CardTestPlayerBase {

    /**
     * Tests boost weren't be applied second time when creature back to
     * battlefield
     */
    @Test
    public void testWithBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Geralf's Messenger");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Last Gasp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Geralf's Messenger");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Geralf's Messenger", 1);
        assertPowerToughness(playerA, "Geralf's Messenger", 4, 3);
    }

    /**
     * Tests boost weren't be applied second time when creature back to
     * battlefield
     */
    @Test
    public void testWithMassBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Strangleroot Geist");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Cower in Fear");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cower in Fear");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Strangleroot Geist", 1);
        // dies then returned with +1/+1 counter (and boost doesn't work anymore)
        assertPowerToughness(playerA, "Strangleroot Geist", 3, 2);
    }

    /**
     * Tests "Target creature gains undying until end of turn"
     */
    @Test
    public void testUndyingEvil() {
        // Elite Vanguard
        // Creature — Human Soldier 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Last Gasp
        // Instant, 1B
        // Target creature gets -3/-3 until end of turn.
        addCard(Zone.HAND, playerA, "Last Gasp");
        // Undying Evil
        // Target creature gains undying until end of turn.
        // When it dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Undying Evil");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Elite Vanguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Undying Evil", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertCounterCount(playerA, "Elite Vanguard", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }

    /**
     * Tests "Threads of Disloyalty enchanting Strangleroot Geist: after geist
     * died it returns to the bf under opponent's control."
     */
    @Test
    public void testUndyingControlledReturnsToOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // Strangleroot Geist  2/1
        // Haste
        // Undying
        // (When it dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Strangleroot Geist");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // Threads of Disloyalty {1}{U}{U}
        // Enchant creature with converted mana cost 2 or less
        // You control enchanted creature.
        addCard(Zone.HAND, playerB, "Threads of Disloyalty");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Strangleroot Geist");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Threads of Disloyalty", "Strangleroot Geist");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Strangleroot Geist");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Threads of Disloyalty", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Strangleroot Geist", 0);
        assertPermanentCount(playerA, "Strangleroot Geist", 1);
        assertCounterCount(playerA, "Strangleroot Geist", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Strangleroot Geist", 3, 2);
    }

    /**
     * Tests "Target creature with Undying will be exiled by Anafenza before it
     * returns to battlefield
     *
     * Anafenza the foremost doesn't exile an undying creature when dying at the
     * same time as that undying one. The undying comes back to the field when
     * he shouldn't.
     */
    @Test
    public void testReplacementEffectPreventsReturnOfUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Butcher Ghoul
        // Creature - Zombie, 1/1  {1}{B}
        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Butcher Ghoul");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        // Anafenza, the Foremost
        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Anafenza, the Foremost");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Butcher Ghoul");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Butcher Ghoul");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Anafenza, the Foremost", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertPermanentCount(playerA, "Butcher Ghoul", 0);
        assertExileCount("Butcher Ghoul", 1);
    }

    /**
     * Tests "Target creature with Undying will be exiled by Anafenza before it
     * returns to battlefield if both leave the battlefield at the same time
     *
     * Anafenza the foremost doesn't exile an undying creature when dying at the
     * same time as that undying one. The undying comes back to the field when
     * he shouldn't.
     */
    @Test
    public void testReplacementEffectPreventsReturnOfUndyingWrath() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Butcher Ghoul
        // Creature - Zombie, 1/1  {1}{B}
        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Butcher Ghoul");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerB, "Wrath of God");

        // Anafenza, the Foremost
        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Anafenza, the Foremost");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Butcher Ghoul");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wrath of God");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Anafenza, the Foremost", 1);
        assertGraveyardCount(playerB, "Wrath of God", 1);

        assertPermanentCount(playerA, "Butcher Ghoul", 0);
        assertExileCount("Butcher Ghoul", 1);
    }

    /**
     * Tests that the undying granted by Mikaeus, the Unhallowed works
     *
     */
    @Test
    public void testUndyingMikaeusTheUnhallowed() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Other non-Human creatures you control get +1/+1 and have undying.
        addCard(Zone.BATTLEFIELD, playerA, "Mikaeus, the Unhallowed", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Mikaeus, the Unhallowed", 1);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);

    }

    /**
     * Tatterkite is getting counters on it, I have him in a edh deck with
     * Mikaeus, the Lunarch and when Tatterkite dies it triggers the undying and
     * he gets the +1/+1 counters
     */
    @Test
    public void testUndyingMikaeusAndTatterkite() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a Human deals damage to you, destroy it.
        // Other non-Human creatures you control get +1/+1 and have undying.
        addCard(Zone.BATTLEFIELD, playerA, "Mikaeus, the Unhallowed", 1);

        // Flying
        // Tatterkite can't have counters placed on it.
        addCard(Zone.BATTLEFIELD, playerA, "Tatterkite", 1); // Artifact Creature - Scarecrow  2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Tatterkite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertPermanentCount(playerA, "Tatterkite", 1);
        assertPermanentCount(playerA, "Mikaeus, the Unhallowed", 1);
        assertCounterCount("Tatterkite", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Tatterkite", 3, 2);

    }

    @Test
    public void testUndyingMikaeusAndTatterkiteSacrifice() {
        // Sacrifice a creature: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);
        // Whenever a Human deals damage to you, destroy it.
        // Other non-Human creatures you control get +1/+1 and have undying
        // (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.).
        addCard(Zone.BATTLEFIELD, playerA, "Mikaeus, the Unhallowed", 1);

        // Flying
        // Tatterkite can't have counters placed on it.
        addCard(Zone.BATTLEFIELD, playerA, "Tatterkite", 1); // Artifact Creature - Scarecrow  2/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        setChoice(playerA, "Tatterkite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tatterkite", 1);
        assertPermanentCount(playerA, "Mikaeus, the Unhallowed", 1);
        assertCounterCount("Tatterkite", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Tatterkite", 3, 2);

    }

    /**
     * I stole my opponents Vorapede using Simic Manipulator and shortly after
     * someone played Wrath of God. Instead of returning to my opponent's board,
     * Vorapede came back under my control. The rules text for Undying states
     * that it should return under its owner's control, not its controller's.
     */
    @Test
    public void testUndyingCreatureReturnsUnderOwnersControl() {

        // Creature — Insect
        // Vigilance, trample
        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerB, "Vorapede"); // {2}{G}{G}{G} 5/4
        addCard(Zone.HAND, playerB, "Doom Blade"); // {1}{B} destroy target non-black creature
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // {2}{R} sorcery - Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // playerA takes control of Vorapede from playerB
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Vorapede");
        attack(1, playerA, "Vorapede");
        // playerB kills Vorapede under the control of playerA now
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Doom Blade", "Vorapede");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertGraveyardCount(playerA, "Act of Treason", 1);
        assertGraveyardCount(playerB, "Doom Blade", 1);

        // Vorapede should return under control of playerA, not playerB
        assertPermanentCount(playerB, "Vorapede", 1);
        assertPermanentCount(playerA, "Vorapede", 0);
        assertCounterCount(playerB, "Vorapede", CounterType.P1P1, 1);
        assertPowerToughness(playerB, "Vorapede", 6, 5);
    }

}
