package org.mage.test.cards.single.mom;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class InvasionOfAlaraTest extends CardTestPlayerBase {

    private static final String invasion = "Invasion of Alara";
    // {W}{U}{B}{R}{G}
    // Battle — Siege
    // When Invasion of Alara enters the battlefield, exile cards from the top of your library until you exile two nonland cards with mana value 4 or less.
    // You may cast one of those two cards without paying its mana cost. Put one of them into your hand.
    // Then put the other cards exiled this way on the bottom of your library in a random order.
    // Sorcery
    // Awaken the Maelstrom is all colors.
    // Target player draws two cards. You may put an artifact card from your hand onto the battlefield.
    // Create a token that's a copy of a permanent you control. Distribute three +1/+1 counters among one, two, or three creatures you control.
    // Destroy target permanent an opponent controls.

    private static final String hexmage = "Vampire Hexmage"; // (2/1) exiled with mana value 4 or less, then cast, then sacrificed to transform Siege
    private static final String wurm = "Craw Wurm"; // mana value 6, so remains exiled
    private static final String divination = "Divination"; // second exiled card with mana value 4 or less
    private static final String meteor = "Meteor Golem"; // card drawn (3/3) ETB destroy target nonland permanent an opponent controls
    private static final String puma = "Stonework Puma"; // another card drawn
    private static final String anthem = "Glorious Anthem"; // nonland permanent to destroy
    private static final String missionary = "Lone Missionary"; // ETB gain 4 life (2/1)
    private static final String behemoth = "Kalonian Behemoth"; // shroud (9/9)
    private static final String baloth = "Baloth Pup"; // trample with +1/+1 counter (3/1)
    private static final String golems = "Bottle Golems"; // dies: gain life equal to its power (3/3)

    @Test
    public void testSiegeAndSorcery() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, invasion);
        addCard(Zone.BATTLEFIELD, playerA, "Composite Golem"); // Sac for WUBRG
        addCard(Zone.BATTLEFIELD, playerB, golems);
        addCard(Zone.BATTLEFIELD, playerA, baloth);
        addCard(Zone.BATTLEFIELD, playerA, behemoth);
        addCard(Zone.BATTLEFIELD, playerA, missionary);
        addCard(Zone.BATTLEFIELD, playerB, anthem);
        addCard(Zone.LIBRARY, playerA, puma); // fifth from top of library
        addCard(Zone.LIBRARY, playerA, meteor);
        addCard(Zone.LIBRARY, playerA, divination);
        addCard(Zone.LIBRARY, playerA, wurm);
        addCard(Zone.LIBRARY, playerA, hexmage); // top of library

        checkPT("Anthem boost", 1, PhaseStep.UPKEEP, playerB, golems, 4, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice"); // add WUBRG
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, invasion);
        setChoice(playerA, hexmage); // yes to cast hexmage
        setChoice(playerA, true); // yes to cast a card

        // divination to hand, no choice needed
        checkGraveyardCount("Composite Golem sacrificed", 1, PhaseStep.BEGIN_COMBAT, playerA, "Composite Golem", 1);
        checkPermanentCount("Battle", 1, PhaseStep.BEGIN_COMBAT, playerA, invasion, 1);
        checkPT("Hexmage on battlefield", 1, PhaseStep.BEGIN_COMBAT, playerA, hexmage, 2, 1);
        checkHandCardCount("Divination in hand", 1, PhaseStep.BEGIN_COMBAT, playerA, divination, 1);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", invasion); // remove all counters
        setChoice(playerA, true); // yes to cast
        addTarget(playerA, playerA); // to draw 2 cards (meteor and puma)
        addTarget(playerA, golems); // to destroy (last effect)
        // now resolve
        setChoice(playerA, true); // yes to put artifact card onto battlefield
        setChoice(playerA, meteor); // artifact to put onto battlefield (will ETB trigger after effect resolve, need to choose target)
        setChoice(playerA, missionary); // to create token copy (will ETB trigger after effect resolve, gain 4 life)
        addTargetAmount(playerA, meteor, 1);
        addTargetAmount(playerA, baloth, 1);
        addTargetAmount(playerA, behemoth, 1);
        // 3 chosen targets (max)
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 1);
        setChoice(playerA, "When {this} enters the battlefield, you gain"); // order triggers
        addTarget(playerA, anthem); // to destroy

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 24); // gained from Lone Missionary
        assertLife(playerB, 24); // gained from Bottle Golems (anthem not destroyed at time of trigger)
        assertAbility(playerA, baloth, TrampleAbility.getInstance(), true); // counter placed
        assertPowerToughness(playerA, baloth, 4, 2);
        assertPowerToughness(playerA, behemoth, 10, 10);
        assertPowerToughness(playerA, meteor, 4, 4);
        assertPermanentCount(playerA, missionary, 2);
        assertGraveyardCount(playerA, hexmage, 1);
        assertGraveyardCount(playerA, invasion, 1);
        assertGraveyardCount(playerB, anthem, 1);
        assertGraveyardCount(playerB, golems, 1);
        assertHandCount(playerA, puma, 1);

    }
}
