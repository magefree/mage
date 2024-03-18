package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author jimga150
 */
public class DonnaNobleTests extends CardTestCommander4Players {

    @Test
    public void PairedCreature2To1Test() {

        //Check that paired creature being dealt damage by 2 sources at the same time = 1 trigger with correct amount

        addCard(Zone.BATTLEFIELD, playerA, "Donna Noble", 1); // Legendary Creature — Human 2/4 {3}{R}

        addCard(Zone.HAND, playerA, "Impervious Greatwurm", 1); // Creature — Wurm 16/16 {7}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Impervious Greatwurm", true);

        //Yes, soul bond donna noble with IG
        setChoice(playerA, "Yes");

        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // Artifact Creature — Construct 1/1 {0}
        addCard(Zone.BATTLEFIELD, playerB, "Expedition Envoy", 1); // Creature — Human Scout Ally 2/1 {W}

        attack(5, playerA, "Impervious Greatwurm", playerB);
        block(5, playerB, "Memnite", "Impervious Greatwurm");
        block(5, playerB, "Expedition Envoy", "Impervious Greatwurm");

        //Assign this much damage to the first blocking creature
        setChoice(playerA, "X=1");

        //Assign this much damage to the second blocking creature
        setChoice(playerA, "X=1");

        //Target this player with Donna Noble
        addTarget(playerA, playerB);

        setStopAt(5, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, currentGame.getStartingLife());
        assertLife(playerB, currentGame.getStartingLife() - 3);

    }

    @Test
    public void DonnaAndPairedBothDamagedSingleSourceTest() {

        //Check that Donna and paired creature both damaged at the same time by one source = 2 triggers with correct amounts

        addCard(Zone.BATTLEFIELD, playerA, "Donna Noble", 1); // Legendary Creature — Human 2/4 {3}{R}

        addCard(Zone.HAND, playerA, "Impervious Greatwurm", 1); // Creature — Wurm 16/16 {7}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Impervious Greatwurm", true);

        //Yes, soul bond donna noble with IG
        setChoice(playerA, "Yes");

        // Kicker {R} (You may pay an additional {R} as you cast this spell.)
        // Cinderclasm deals 1 damage to each creature. If it was kicked, it deals 2 damage to each creature instead.
        addCard(Zone.HAND, playerA, "Cinderclasm", 1); // Instant {1}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinderclasm", true);

        //Yes, pay kicker for Cinderclasm
        setChoice(playerA, "Yes");

        //pick triggered ability starting with this string to enter the stack first
        setChoice(playerA, "Whenever");

        //Target this player with Donna Noble
        addTarget(playerA, playerB);

        //Target this player with Donna Noble (second trigger)
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, currentGame.getStartingLife());
        assertLife(playerB, currentGame.getStartingLife() - 4);

    }

    @Test
    public void DonnaAndPairedBothDamagedDiffSourceTest() {

        //Check that Donna and paired creature both damaged at the same time by different sources = 2 triggers with correct amounts

        addCard(Zone.BATTLEFIELD, playerA, "Donna Noble", 1); // Legendary Creature — Human 2/4 {3}{R}

        addCard(Zone.HAND, playerA, "Impervious Greatwurm", 1); // Creature — Wurm 16/16 {7}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Impervious Greatwurm", true);

        //Yes, soul bond donna noble with IG
        setChoice(playerA, "Yes");

        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // Artifact Creature — Construct 1/1 {0}
        addCard(Zone.BATTLEFIELD, playerB, "Expedition Envoy", 1); // Creature — Human Scout Ally 2/1 {W}

        attack(4, playerB, "Memnite", playerA);
        attack(4, playerB, "Expedition Envoy", playerA);
        block(4, playerA, "Impervious Greatwurm", "Memnite");
        block(4, playerA, "Donna Noble", "Expedition Envoy");

        //pick triggered ability starting with this string to enter the stack first
        setChoice(playerA, "Whenever");

        //Target this player with Donna Noble
        addTarget(playerA, playerB);

        //Target this player with Donna Noble (second trigger)
        addTarget(playerA, playerB);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, currentGame.getStartingLife());
        assertLife(playerB, currentGame.getStartingLife() - 3);

    }

}
