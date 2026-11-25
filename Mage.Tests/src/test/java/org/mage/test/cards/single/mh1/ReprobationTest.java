package org.mage.test.cards.single.mh1;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Reprobation {1}{W} Enchantment — Aura
 * Enchant creature
 * Enchanted creature loses all abilities and is a Coward creature with base power and toughness 0/1.
 * (It keeps all supertypes but loses all other types and creature types.)
 */
public class ReprobationTest extends CardTestPlayerBase {

    @Test
    public void test_ArtifactCreatureLosesArtifactType() {
        addCard(Zone.HAND, playerA, "Reprobation");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // Artifact Creature — Thopter 0/2, flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reprobation", "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertType("Ornithopter", CardType.CREATURE, SubType.COWARD);
        assertNotType("Ornithopter", CardType.ARTIFACT);
        assertNotSubtype("Ornithopter", SubType.THOPTER);
        assertPowerToughness(playerB, "Ornithopter", 0, 1);
        assertAbility(playerB, "Ornithopter", FlyingAbility.getInstance(), false);
    }
}
