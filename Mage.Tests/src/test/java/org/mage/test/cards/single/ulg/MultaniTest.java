package org.mage.test.cards.single.ulg;

import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 * Created by escplan9
 */
public class MultaniTest extends CardTestPlayerBase {

    @Test
    public void pathbreakerTrampleShouldOnlyLastUntilEOT() {
        /*
        Multani, Maro-Sorcerer {4}{G}{G}
        Legendary Creature — Elemental * / *
        Shroud
        Multani, Maro-Sorcerer's power and toughness are each equal to the total number of cards in all players' hands.
        */
        String multani = "Multani, Maro-Sorcerer";

        /*
         Hall of the Bandit Lord
        Legendary Land
        Hall of the Bandit Lord enters the battlefield tapped.
        {T}, Pay 3 life: Add {1}. If that mana is spent on a creature spell, it gains haste.
        */
        String hBandit = "Hall of the Bandit Lord";

        /*
         Pathbreaker Ibex {4}{G}{G}
        Creature — Goat 3/3
        Whenever Pathbreaker Ibex attacks, creatures you control gain trample and get +X/+X until end of turn, where X is the greatest power among creatures you control.
        */
        String pIbex = "Pathbreaker Ibex";

        addCard(Zone.HAND, playerA, multani);
        addCard(Zone.HAND, playerA, pIbex);
        addCard(Zone.BATTLEFIELD, playerA, hBandit);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, multani); // 5 forests and 1 colorless from Hall pay for it, granting it haste and losing 3 life
        attack(1, playerA, multani);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, pIbex); // 5 forests and 1 colorless from Hall pay for it, granting it haste and losing 3 life
        attack(3, playerA, multani);
        attack(3, playerA, pIbex);

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN); // make sure trample has not carried over
        execute();

        assertLife(playerA, 14); // hall of bandit activated twice
        assertTapped(hBandit, true);
        assertTapped(multani, true);
        assertTapped(pIbex, true);
        assertPermanentCount(playerA, pIbex, 1);
        assertPermanentCount(playerA, multani, 1);
        assertAbility(playerA, pIbex, HasteAbility.getInstance(), true);
        assertAbility(playerA, multani, HasteAbility.getInstance(), true);
        assertAbility(playerA, multani, ShroudAbility.getInstance(), true);
        assertAbility(playerA, multani, TrampleAbility.getInstance(), false);
        assertAbility(playerA, pIbex, TrampleAbility.getInstance(), false);
    }
}
