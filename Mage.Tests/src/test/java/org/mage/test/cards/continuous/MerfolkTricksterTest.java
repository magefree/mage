package org.mage.test.cards.continuous;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.keyword.FlashAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author drmDev
 */
public class MerfolkTricksterTest extends CardTestPlayerBase {

    /*
     Merfolk Trickster (UU)
    Creature Merfolk Wizard
    Flash
    When Merfolk Trickster enters the battlefield, tap target creature an opponent controls. It loses all abilities until end of turn.
     */
    public final String mTrickster = "Merfolk Trickster";

    @Test
    public void test_TricksterAndFlyer_FlyingRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, "Flying Men"); // (U) 1/1 flyer
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, mTrickster);

        attack(1, playerA, "Flying Men");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, mTrickster);
        // addTarget(playerB, "Flying Men"); Autochosen, only option

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertTappedCount("Island", true, 2);
        assertTapped("Flying Men", true);

        Abilities<Ability> noAbilities = new AbilitiesImpl<>();
        assertAbilities(playerA, "Flying Men", noAbilities); // no abilities, empty list

        Abilities<Ability> flashAbility = new AbilitiesImpl<>();
        flashAbility.add(FlashAbility.getInstance());
        assertAbilities(playerB, mTrickster, flashAbility); // has flash

        assertAllCommandsUsed();
    }

    @Test
    public void test_TricksterAndFlyerBlocked_FlyingRemovedAndBlocked() {
        addCard(Zone.BATTLEFIELD, playerA, "Flying Men"); // (U) 1/1 flyer
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, mTrickster);

        attack(1, playerA, "Flying Men");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, mTrickster);
        // addTarget(playerB, "Flying Men"); Autochosen, only target
        block(1, playerB, mTrickster, "Flying Men");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertTappedCount("Island", true, 2);
        assertGraveyardCount(playerA, "Flying Men", 1);
        assertPermanentCount(playerB, mTrickster, 1);
        assertDamageReceived(playerB, mTrickster, 1);
        assertAllCommandsUsed();
    }

    @Test
    public void test_TricksterBlocksFootlightFiend_Survives() {
        addCard(Zone.BATTLEFIELD, playerA, "Footlight Fiend"); // (R/B) 1/1 on death pings any target for 1
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, mTrickster);

        attack(1, playerA, "Footlight Fiend");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, mTrickster);
        addTarget(playerB, "Footlight Fiend");
        block(1, playerB, mTrickster, "Footlight Fiend");
        addTarget(playerA, mTrickster);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertTappedCount("Island", true, 2);
        assertPermanentCount(playerB, mTrickster, 1);
        assertDamageReceived(playerB, mTrickster, 1);
        //assertAllCommandsUsed(); // uncommenting this will force a failure since PlayerA cannot do a command to target Trickster, as expected
    }

    @Test
    public void test_TricksterBlocksTibaltToken_Survives() {
        /*
        Tibalt, Rakish Instigator (2R)
        Legendary Planeswalker Tibalt
        Your opponents can't gain life.
        -2: Create a 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
         */
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, Rakish Instigator");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, mTrickster);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:");

        attack(3, playerA, "Devil Token");
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerB, mTrickster);
        addTarget(playerB, "Devil Token");
        block(3, playerB, mTrickster, "Devil Token");
        addTarget(playerA, mTrickster);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertCounterCount("Tibalt, Rakish Instigator", CounterType.LOYALTY, 3);
        assertTappedCount("Island", true, 2);
        assertPermanentCount(playerB, mTrickster, 1);
        assertDamageReceived(playerB, mTrickster, 1);
        // assertAllCommandsUsed(); // uncommenting this should force a failure since PlayerA cannot do a command to target Trickster, as expected
    }
}
