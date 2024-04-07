package org.mage.test.cards.single.otj;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class VraskaTheSilencerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.VraskaTheSilencer Vraska, the Silencer} {1}{B}{G}
     * Legendary Creature — Gorgon Assassin
     * Deathtouch
     * Whenever a nontoken creature an opponent controls dies, you may pay {1}. If you do, return that card to the battlefield tapped under your control. It’s a Treasure artifact with “{T}, Sacrifice this artifact: Add one mana of any color,” and it loses all other card types.
     * 3/3
     */
    private static final String vraska = "Vraska, the Silencer";

    @Test
    public void test_CorrectTypes() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, vraska);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerB, "Red Herring"); // Creature Artifact -- Fish Clue
        // Whenever another creature enters the battlefield, you gain 1 life.
        // Should not trigger. Just to check the continuous effect is done before the permanent entering modified.
        addCard(Zone.BATTLEFIELD, playerA, "Soul Warden");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Red Herring");
        setChoice(playerA, true); // yes to "You may pay"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Red Herring", 1);
        assertTapped("Red Herring", true);
        assertType("Red Herring", CardType.CREATURE, false);
        assertType("Red Herring", CardType.ARTIFACT, true);
        assertNotSubtype("Red Herring", SubType.FISH);
        assertSubtype("Red Herring", SubType.CLUE);
        assertSubtype("Red Herring", SubType.TREASURE);
        assertLife(playerA, 20); // no Soul Warden trigger
    }
}
