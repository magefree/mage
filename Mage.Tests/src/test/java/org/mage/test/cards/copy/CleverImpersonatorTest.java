package org.mage.test.cards.copy;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class CleverImpersonatorTest extends CardTestPlayerBase {

    /**
     * Clever Impersonator Copy Gilded Drake. Gilded drake does not trigger to
     * exchange creature.
     */
    @Test
    public void testCopyGildedDrake() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Flying
        // When Gilded Drake enters the battlefield, exchange control of Gilded Drake and up to one target creature an opponent controls. If you don't make an exchange, sacrifice Gilded Drake. This ability can't be countered except by spells and abilities.
        addCard(Zone.HAND, playerA, "Gilded Drake", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gilded Drake");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");
        setChoice(playerB, "Gilded Drake"); // copy the drake
        addTarget(playerB, "Pillarfield Ox"); // exchange control with Ox

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gilded Drake", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertPermanentCount(playerB, "Gilded Drake", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
    }

    /**
     * Copy a planeswalker on the battlefield
     */
    @Test
    public void testCopyPlaneswalker() {
        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerA, "Clever Impersonator", 1); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // +2: Each player discards a card.
        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        // -8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step.";
        addCard(Zone.BATTLEFIELD, playerB, "Liliana, Defiant Necromancer", 1);
        //
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerB, "Balduvian Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clever Impersonator", true);
        setChoice(playerA, true); // make copy
        setChoice(playerA, "Liliana, Defiant Necromancer"); // copy target
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Each player discards a card");
        addTarget(playerA, "Balduvian Bears"); // discard
        addTarget(playerB, "Balduvian Bears"); // discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Clever Impersonator", 0);
        assertCounterCount(playerB, "Liliana, Defiant Necromancer", CounterType.LOYALTY, 3);  // 3
        assertPermanentCount(playerB, "Liliana, Defiant Necromancer", 1);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertCounterCount(playerA, "Liliana, Defiant Necromancer", CounterType.LOYALTY, 5);  // 3 + 2
    }

    /**
     * I had an Alesha, Who Smiles at Death returning a Clever Impersonator who
     * was supposed to copy a flipped Liliana, Defiant Necromancer, but it
     * entered the battlefield with 0 loyalty and died immediately. If I am not
     * mistaken it should have entered with 3 loyalty (see Gatherer entry).
     */
    @Test
    public void testCopyPlaneswalkerFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // First strike
        // Whenever Alesha, Who Smiles at Death attacks, you may pay {W/B}{W/B}. If you do, return target creature card with power 2 or less from your graveyard to the battlefield tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerA, "Alesha, Who Smiles at Death", 1); // {2}{R} - 3/2

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Clever Impersonator", 1); // {2}{U}{U}

        // +2: Each player discards a card.
        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        // -8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step.";
        addCard(Zone.BATTLEFIELD, playerB, "Liliana, Defiant Necromancer", 1);

        attack(1, playerA, "Alesha, Who Smiles at Death");
        // addTarget(playerA, "Clever Impersonator"); (Autochosen, only target)
        setChoice(playerA, "Liliana, Defiant Necromancer");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "+2: Each player discards a card");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Alesha, Who Smiles at Death", true);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Clever Impersonator", 0);

        assertCounterCount(playerB, "Liliana, Defiant Necromancer", CounterType.LOYALTY, 3);  // 3
        assertPermanentCount(playerB, "Liliana, Defiant Necromancer", 1);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertCounterCount(playerA, "Liliana, Defiant Necromancer", CounterType.LOYALTY, 5);  // 3 + 2
    }

    /**
     * So I copied Jace, Vryns Prodigy with Clever Impersonator (it was tapped
     * and I needed a blocker for a token...), and Jace got to survive until the
     * next turn. When I looted, he flipped, and I got an error message I
     * couldn't get rid of, forcing me to concede. I'm not sure what the correct
     * outcome is rules-wise.
     */
    @Test
    public void testCopyCreatureOfFlipPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard, exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Vryn's Prodigy", 1); // {2}{R} - 3/2

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerA, "Clever Impersonator", 1); // {2}{U}{U}
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clever Impersonator");
        setChoice(playerA, "Jace, Vryn's Prodigy");
        setChoice(playerA, "Jace, Vryn's Prodigy[only copy]"); // keep the copied Jace

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card");
        setChoice(playerA, "Pillarfield Ox");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jace, Vryn's Prodigy", 1);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);

    }

    /**
     * Reported bug:
     *      Could not use Clever Impersonator to copy Dawn's Reflection
     */
    @Test
    public void dawnsReflectionCopiedByImpersonator() {
        String impersonator = "Clever Impersonator";
        String dReflection = "Dawn's Reflection";

        /*
        {3}{G} Dawn's Reflection
        Enchantment - Aura, Enchant Land
        Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors (in addition to the mana the land produces).
         */
        addCard(Zone.HAND, playerA, dReflection);

        /*
        {2}{U}{U} Creature - Shapeshifter 0/0
        You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
         */
        addCard(Zone.HAND, playerA, impersonator);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dReflection, "Forest"); // enchant a forest
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, impersonator);
        setChoice(playerA, dReflection); // have Impersonator enter as copy of Dawn's Reflection

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, dReflection, 0);
        assertHandCount(playerA, impersonator, 0);
        assertPermanentCount(playerA, dReflection, 2);
        assertPermanentCount(playerA, impersonator, 0);
        assertType(dReflection, CardType.ENCHANTMENT, true);
    }

    @Test
    public void testKindredDiscovery() {
        addCard(Zone.HAND, playerA, "Kindred Discovery");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.HAND, playerB, "Clever Impersonator");
        addCard(Zone.HAND, playerB, "Ornithopter", 2);
        addCard(Zone.HAND, playerB, "Memnite");
        // Skip your draw step.
        addCard(Zone.BATTLEFIELD, playerB, "Dragon Appeasement");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kindred Discovery"); // Construct token auto-chosen
        setChoice(playerA, "Thopter");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");
        setChoice(playerB, "Yes");
        setChoice(playerB, "Kindred Discovery");
        setChoice(playerB, "Thopter");

        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ornithopter");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ornithopter");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memnite");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerB, 2);
    }
}
