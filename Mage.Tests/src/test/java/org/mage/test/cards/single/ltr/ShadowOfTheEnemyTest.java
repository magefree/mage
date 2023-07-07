package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ShadowOfTheEnemyTest extends CardTestPlayerBase {

    @Test
    public void test_ShadowOfTheEnemy() {
        // Exile all creature cards from target player’s graveyard.
        // You may cast spells from among those cards for as long as they remain exiled,
        // and mana of any type can be spent to cast them.
        addCard(Zone.HAND, playerA, "Shadow of the Enemy"); // {3}{B}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 15); // enough to cast anything but worl

        // Akoum Warrior - creature
        // Akoum Teeth - land
        // Testing: will not allow play of back side
        addCard(Zone.GRAVEYARD, playerB, "Akoum Warrior");
        // Plargg, Dean of Chaos - creature
        // Augusta, Dean of Order - creature
        // Testing: mdfc can be cast both sides.
        addCard(Zone.GRAVEYARD, playerB, "Plargg, Dean of Chaos");
        // Valki, God of Lies - creature
        // Tibalt, Cosmic Impostor - planeswalker
        // Testing: Creature check is only on exile. After that you can cast both sides.
        addCard(Zone.GRAVEYARD, playerB, "Valki, God of Lies");
        // Dryad Arbor - creature land
        // Testing: Play is not allowed, but will still be exiled.
        addCard(Zone.GRAVEYARD, playerB, "Dryad Arbor");
        // Bonecrusher Giant - creature
        // Stomp - instant
        // Testing: Adventures can be cast both faces
        addCard(Zone.GRAVEYARD, playerB, "Bonecrusher Giant");
        // Zoetic Cavern - land
        // Testing: Has morph! but will not be exiled
        addCard(Zone.GRAVEYARD, playerB, "Zoetic Cavern");
        // Thought-Knot Seer
        // Testing: "you may spend mana as any type"
        addCard(Zone.GRAVEYARD, playerB, "Thought-Knot Seer");
        // Emrakul, the Aeons Torn
        // Testing: Here to check that the cast is not free.
        addCard(Zone.GRAVEYARD, playerB, "Emrakul, the Aeons Torn");

        // exile all creature cards from player B's graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shadow of the Enemy", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkExileCount("after exile Akoum Warrior",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);
        checkExileCount("after exile Plargg, Dean of Chaos" ,
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plargg, Dean of Chaos", 1);
        checkExileCount("after exile Valki, God of Lies"    ,
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Valki, God of Lies", 1);
        checkExileCount("after exile Dryad Arbor",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Arbor", 1);
        checkExileCount("after exile Bonecrusher Giant",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bonecrusher Giant", 1);
        checkExileCount("after exile Zoetic Cavern",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern", 0);
        checkExileCount("after exile Though-Knot Seer",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought-Knot Seer", 1);
        checkExileCount("after exile Emrakul, the Aeons Torn",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Aeons Torn", 1);

        // checking what can be cast/played
        checkPlayableAbility("after exile - can cast Akoum Warrior",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("after exile - can't play Akoum Teeth",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", false);
        checkPlayableAbility("after exile - can cast Plargg, Dean of Chaos",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Plargg, Dean of Chaos", true);
        checkPlayableAbility("after exile - can cast Augusta, Dean of Order",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Augusta, Dean of Order", true);
        checkPlayableAbility("after exile - can cast Valki, God of Lies",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Valki, God of Lies", true);
        checkPlayableAbility("after exile - can cast Tibalt, Cosmic Impostor",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tibalt, Cosmic Impostor", true);
        checkPlayableAbility("after exile - can't play Dryad Arbor",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Dryad Arbor", false);
        checkPlayableAbility("after exile - can cast Bonecrusher Giant",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bonecrusher Giant", true);
        checkPlayableAbility("after exile - can cast Stomp",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Stomp", true);
        checkPlayableAbility("after exile - can cast Thought-Knot Seer",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Thought-Knot Seer", true);
        checkPlayableAbility("after exile - can't cast Emrakul, the Aeons Torn, not enough mana",
            1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Emrakul, the Aeons Torn", false);

        setStrictChooseMode(true);
        execute();
    }

}
