
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SagesReverieTest extends CardTestPlayerBase {

    /**
     * 21:11: MarioPineda casts Sage's Reverie [26d] targeting face down creature
     * 21:11: Ability triggers: Sage's Reverie [26d] - When Sage's Reverie [26d] enters the battlefield, draw a card for each aura you control that's attached to a creature.
     * 21:11: mbvash casts Crackling Doom [b78]
     * 21:11: MarioPineda loses 2 life
     * 21:11: MarioPineda sacrificed face down creature
     * 21:11: mbvash puts Crackling Doom [b78] from stack into their graveyard
     * 21:11: Cloudform [9cd] is put into graveyard from battlefield
     * 21:11: Sage's Reverie [26d] is put into graveyard from battlefield
     * 21:11: MarioPineda draws two cards
     *
     * There were two other Auras on the battlefield, and Sage's Reverie made me draw two cards even though the creature it was going to enchant left the battlefield.
     *
     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/604851-sages-reverie-question
     */
    @Test
    public void testNoCardDrawIfTargetIllegal() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Enchant creature
        // When Sage's Reverie enters the battlefield, draw a card for each aura you control that's attached to a creature.
        addCard(Zone.HAND, playerA, "Sage's Reverie", 1); // {3}{W}
        // Enchant creature
        // Enchanted creature has lifelink
        addCard(Zone.HAND, playerA, "Lifelink", 1); // {W}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerB, "Hero's Downfall"); // {1}{B}{B}

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lifelink", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sage's Reverie", "Pillarfield Ox");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Hero's Downfall", "Pillarfield Ox", "Sage's Reverie");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lifelink", 1);
        assertGraveyardCount(playerB, "Hero's Downfall", 1);
        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerA, "Sage's Reverie", 1);

        assertHandCount(playerA, 0);
    }

}
