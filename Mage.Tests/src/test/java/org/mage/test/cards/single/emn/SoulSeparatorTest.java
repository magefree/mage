package org.mage.test.cards.single.emn;

import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SoulSeparatorTest extends CardTestPlayerBase {

    @Test
    public void testBasicExileCreature() {
        // Soul Separator {3} Artifact
        // {5}, {T}, Sacrifice Soul Separator: Exile target creature card from your graveyard.
        // Create a token that's a copy of that card except it's 1/1, it's a Spirit in addition to its other types, and it has flying.
        // Create a black Zombie creature token onto the battlefield with power equal to that card's power and toughness equal that card's toughness.
        addCard(Zone.BATTLEFIELD, playerA, "Soul Separator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerA, "Sylvan Advocate"); // 2/3 vigilance

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}");
        addTarget(playerA, "Sylvan Advocate");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Soul Separator", 1);
        assertExileCount("Sylvan Advocate", 1);
        assertPermanentCount(playerA, "Sylvan Advocate", 1);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertPowerToughness(playerA, "Zombie Token", 2, 3);

        Permanent saToken = getPermanent("Sylvan Advocate", playerA);
        Assert.assertTrue(saToken.getAbilities().contains(FlyingAbility.getInstance()));
        Assert.assertTrue(saToken.hasSubtype(SubType.SPIRIT, currentGame));
        Assert.assertTrue(saToken.getAbilities().contains(VigilanceAbility.getInstance()));
        assertPowerToughness(playerA, "Sylvan Advocate", 1, 1);
    }

    // Reported bug: Exiled Tree of Perdition with Soul Separator
    // The token copy when activated reduced the opponent's life total to 13 (tree toughness) instead of 1 (1/1 token)
    @Test
    public void testExileTreeOfPerdition() {
        // Soul Separator {3} Artifact
        // {5}, {T}, Sacrifice Soul Separator: Exile target creature card from your graveyard.
        // Create a token that's a copy of that card except it's 1/1, it's a Spirit in addition to its other types, and it has flying.
        // Create a black Zombie creature token onto the battlefield with power equal to that card's power and toughness equal that card's toughness.
        addCard(Zone.BATTLEFIELD, playerA, "Soul Separator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // Tree of Perdition {3}{B} Creature - Defender (0/13)
        // {tap}: Exchange target opponent's life total with Tree of Perdition's toughness.
        addCard(Zone.GRAVEYARD, playerA, "Tree of Perdition");

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}");
        addTarget(playerA, "Tree of Perdition");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exchange");
        addTarget(playerA, playerB);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Soul Separator", 1);
        assertExileCount("Tree of Perdition", 1);
        assertPermanentCount(playerA, "Tree of Perdition", 1);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertPowerToughness(playerA, "Zombie Token", 0, 13);

        Permanent treeToken = getPermanent("Tree of Perdition", playerA);
        Assert.assertTrue(treeToken.getAbilities().contains(FlyingAbility.getInstance()));
        Assert.assertTrue(treeToken.hasSubtype(SubType.SPIRIT, currentGame));
        Assert.assertTrue(treeToken.getAbilities().contains(DefenderAbility.getInstance()));

        assertLife(playerA, 20);
        assertLife(playerB, 1);
        assertPowerToughness(playerA, "Tree of Perdition", 1, 20);
    }
}
