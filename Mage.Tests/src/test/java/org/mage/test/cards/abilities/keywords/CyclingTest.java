
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CyclingTest extends CardTestPlayerBase {

    /**
     * 702.28. Cycling 702.28a Cycling is an activated ability that functions
     * only while the card with cycling is in a player's hand. “Cycling [cost]”
     * means “[Cost], Discard this card: Draw a card.” 702.28b Although the
     * cycling ability is playable only if the card is in a player's hand, it
     * continues to exist while the object is in play and in all other zones.
     * Therefore objects with cycling will be affected by effects that depend on
     * objects having one or more activated abilities. 702.28c Some cards with
     * cycling have abilities that trigger when they're cycled. “When you cycle
     * [this card]” means “When you discard [this card] to pay a cycling cost.”
     * These abilities trigger from whatever zone the card winds up in after
     * it's cycled. 702.28d Typecycling is a variant of the cycling ability.
     * “[Type]cycling [cost]” means “[Cost], Discard this card: Search your
     * library for a [type] card, reveal it, and put it into your hand. Then
     * shuffle your library.” This type is usually a subtype (as in
     * “mountaincycling”) but can be any card type, subtype, supertype, or
     * combination thereof (as in “basic landcycling”). 702.28e Typecycling
     * abilities are cycling abilities, and typecycling costs are cycling costs.
     * Any cards that trigger when a player cycles a card will trigger when a
     * card is discarded to pay a typecycling cost. Any effect that stops
     * players from cycling cards will stop players from activating cards'
     * typecycling abilities. Any effect that increases or reduces a cycling
     * cost will increase or reduce a typecycling cost.
     */
    @Test
    public void cycleAndTriggerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way.
        // Cycling {3}{B}{B}
        // When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
        addCard(Zone.HAND, playerA, "Decree of Pain");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {3}{B}{B}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);

        assertGraveyardCount(playerA, "Decree of Pain", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);

        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);
    }

    /**
     * Cycle from graveyard or battlefield should not work.
     */
    @Test
    public void cycleFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way.
        // Cycling {3}{B}{B}
        // When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
        addCard(Zone.GRAVEYARD, playerA, "Decree of Pain");

        // Protection from black
        // Cycling {2} ({2}, Discard this card: Draw a card.)
        addCard(Zone.BATTLEFIELD, playerB, "Disciple of Grace");

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);

        assertGraveyardCount(playerA, "Decree of Pain", 1);
        assertPermanentCount(playerB, "Disciple of Grace", 1);

    }

    /**
     * Type cycling for sliver
     */
    @Test
    public void cycleFromHomingSliver() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Each Sliver card in each player's hand has slivercycling {3}.
        addCard(Zone.BATTLEFIELD, playerA, "Homing Sliver");
        // All Sliver creatures have flying.
        addCard(Zone.HAND, playerA, "Winged Sliver");

        addCard(Zone.LIBRARY, playerA, "Horned Sliver");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 10);
        skipInitShuffling();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Slivercycling {3}");
        addTarget(playerA, "Horned Sliver");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);

        assertGraveyardCount(playerA, "Winged Sliver", 1);

        assertHandCount(playerA, "Horned Sliver", 1); // searched by slivercyclibng
    }

    @Test
    public void cycleWithNewPerspectives() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // When New Perspectives enters the battlefield, draw three cards.
        // As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs.
        addCard(Zone.HAND, playerA, "New Perspectives"); // Enchantment {5}{U}
        // Destroy all artifacts, creatures, and enchantments.
        // Cycling ({3}, Discard this card: Draw a card.)
        addCard(Zone.HAND, playerA, "Akroma's Vengeance");
        addCard(Zone.HAND, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "New Perspectives");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Akroma's Vengeance", 1);
        assertHandCount(playerA, 7);
    }


    @Test
    public void cycleShadowOfTheGrave(){
        addCard(Zone.HAND, playerA, "Darkwatch Elves", 1 );
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 20);
        addCard(Zone.HAND, playerA, "Shadow of the Grave", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shadow of the Grave");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerA, "Darkwatch Elves", 0);
        assertHandCount(playerA, "Darkwatch Elves", 1);
    }
}
