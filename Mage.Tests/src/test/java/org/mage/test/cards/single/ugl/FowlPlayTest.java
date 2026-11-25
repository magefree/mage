package org.mage.test.cards.single.ugl;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Fowl Play {2}{U} Enchantment — Aura
 * Enchant creature
 * Enchanted creature is a Bird creature with base power and toughness 1/1 and loses all abilities.
 * <p>
 * The effect sets the card type, so an artifact creature stops being an artifact (CR 205.1a)
 * and loses its other subtypes with it.
 */
public class FowlPlayTest extends CardTestPlayerBase {

    @Test
    public void test_ArtifactCreatureLosesArtifactType() {
        addCard(Zone.HAND, playerA, "Fowl Play");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // Artifact Creature — Thopter 0/2, flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fowl Play", "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Ornithopter", 1);
        assertType("Ornithopter", CardType.CREATURE, SubType.BIRD);
        assertNotType("Ornithopter", CardType.ARTIFACT);
        assertNotSubtype("Ornithopter", SubType.THOPTER);
        assertPowerToughness(playerB, "Ornithopter", 1, 1);
        assertAbility(playerB, "Ornithopter", FlyingAbility.getInstance(), false);
    }
}
