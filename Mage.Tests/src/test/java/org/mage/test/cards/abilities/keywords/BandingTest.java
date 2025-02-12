package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class BandingTest extends CardTestPlayerBase {

    @Test
    public void BandingAttackSimple() {
        addCard(Zone.BATTLEFIELD, playerA, "Squire"); // 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Benalish Infantry"); // Banding 1/3
        addCard(Zone.BATTLEFIELD, playerA, "Eager Cadet"); // 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Naga Eternal"); // 3/2

        attack(1, playerA, "Squire");
        attack(1, playerA, "Benalish Infantry");
        attack(1, playerA, "Eager Cadet");
        setChoice(playerA, true);
        setChoice(playerA, "Squire");
        block(1, playerB, "Naga Eternal", "Squire");
        setChoiceAmount(playerA, 1, 2); //1 to Squire, 2 to Infantry, attacking player chooses

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 0);
        assertDamageReceived(playerA, "Squire", 1);
        assertDamageReceived(playerA, "Benalish Infantry", 2);
        assertGraveyardCount(playerB, 1);
        assertLife(playerB, 19); // Only Eager Cadet gets through
    }

    @Test
    public void BandingBlockSimple() {
        addCard(Zone.BATTLEFIELD, playerA, "Alpine Grizzly"); // 4/2
        addCard(Zone.BATTLEFIELD, playerB, "Squire"); // 1/2
        addCard(Zone.BATTLEFIELD, playerB, "Sanctuary Cat"); // 1/2
        addCard(Zone.BATTLEFIELD, playerB, "Benalish Infantry"); // Banding 1/3

        attack(1, playerA, "Alpine Grizzly");
        block(1, playerB, "Squire", "Alpine Grizzly");
        block(1, playerB, "Sanctuary Cat", "Alpine Grizzly");
        block(1, playerB, "Benalish Infantry", "Alpine Grizzly");
        setChoiceAmount(playerB, 1, 1, 2); //1 to Squire, 1 to Cat, 2 to Infantry, defending player chooses

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 0);
        assertDamageReceived(playerB, "Squire", 1);
        assertDamageReceived(playerB, "Sanctuary Cat", 1);
        assertDamageReceived(playerB, "Benalish Infantry", 2);
        assertLife(playerB, 20);

    }

    @Test
    public void DoubleBanding() {
        addCard(Zone.BATTLEFIELD, playerA, "Benalish Infantry"); // Banding 1/3
        addCard(Zone.BATTLEFIELD, playerA, "Fortress Crab"); // 1/6
        addCard(Zone.BATTLEFIELD, playerA, "Eager Cadet"); // 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Catacomb Slug"); // 2/6
        addCard(Zone.BATTLEFIELD, playerB, "War Elephant"); // Banding 2/2 Trample

        attack(1, playerA, "Benalish Infantry");
        attack(1, playerA, "Fortress Crab");
        attack(1, playerA, "Eager Cadet");
        setChoice(playerA, true);
        setChoice(playerA, "Fortress Crab");

        block(1, playerB, "Catacomb Slug", "Benalish Infantry");
        block(1, playerB, "War Elephant", "Benalish Infantry");

        setChoiceAmount(playerB, 0, 1); // Damage from Benalish Infantry
        setChoiceAmount(playerB, 0, 1); // Damage from Fortress Crab

        setChoiceAmount(playerA, 1, 1); // Damage from War Elephant
        setChoiceAmount(playerA, 0, 2); // Damage from Catacomb Slug

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 0);
        assertDamageReceived(playerA, "Benalish Infantry", 1);
        assertDamageReceived(playerA, "Fortress Crab", 3);

        assertGraveyardCount(playerB, "War Elephant", 1);
        assertDamageReceived(playerB, "Catacomb Slug", 0);

        assertLife(playerB, 19); // Only Eager Cadet gets through
    }

}
