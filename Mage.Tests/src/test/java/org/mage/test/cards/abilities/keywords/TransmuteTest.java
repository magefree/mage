
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * 702.52. Transmute
 *
 * 702.52a Transmute is an activated ability that functions only while the card
 * with transmute is in a player's hand. “Transmute [cost]” means “[Cost],
 * Discard this card: Search your library for a card with the same converted
 * mana cost as the discarded card, reveal that card, and put it into your hand.
 * Then shuffle your library. Play this ability only any time you could play a
 * sorcery.”
 *
 * 702.52b Although the transmute ability is playable only if the card is in a
 * player's hand, it continues to exist while the object is in play and in all
 * other zones. Therefore objects with transmute will be affected by effects
 * that depend on objects having one or more activated abilities.
 *
 * @author LevelX2
 */
public class TransmuteTest extends CardTestPlayerBase {

    @Test
    public void searchSimpleOneManaCmcSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Target creature gets -3/-0 until end of turn.
        // Transmute {1}{U}{U}
        addCard(Zone.HAND, playerA, "Dizzy Spell");

        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{U}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dizzy Spell", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void searchSplittedCardOneManaCmcSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Target creature gets -3/-0 until end of turn.
        // Transmute {1}{U}{U} ({1}{U}{U}, Discard this card: Search your library for a card with the same
        //                      converted mana cost as this card, reveal it, and put it into your hand.
        //                      Then shuffle your library.
        //                      Transmute only as a sorcery.)
        addCard(Zone.HAND, playerA, "Dizzy Spell"); // Instant {U}

        // Wear {1}{R}
        // Destroy target artifact.
        // Tear {W}
        // Destroy target enchantment.
        addCard(Zone.LIBRARY, playerA, "Wear // Tear");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{U}");
        // let the game choose targets

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dizzy Spell", 1);
        assertHandCount(playerA, "Wear // Tear", 0);
    }

    @Test
    public void searchSplittedCardThreeManaCmcSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Counter target spell unless its controller discards their hand.
        // Transmute {1}{U}{B}
        addCard(Zone.HAND, playerA, "Perplex"); // Instant {1}{U}{B}

        // Wear {1}{R}
        // Destroy target artifact.
        // Tear {W}
        // Destroy target enchantment.
        addCard(Zone.LIBRARY, playerA, "Wear // Tear");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{B}");
        addTarget(playerA, "Wear // Tear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Perplex", 1);
        assertHandCount(playerA, "Wear // Tear", 1);
    }
}
