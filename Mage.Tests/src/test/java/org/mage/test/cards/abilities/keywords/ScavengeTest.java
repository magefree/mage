package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ScavengeTest extends CardTestPlayerBase {

//
//    702.95. Scavenge
//
//    702.95a Scavenge is an activated ability that functions only while the card
//    with scavenge is in a graveyard. "Scavenge [cost]" means "[Cost], Exile this
//    card from your graveyard: Put a number of +1/+1 counters equal to this card's
//    power on target creature. Activate this ability only any time you could cast
//    a sorcery."
//
    @Test
    public void SimpleScavenge() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        // Scavenge {4}{G}{G} ({4}{G}{G} , Exile this card from your graveyard: Put a number of +1/+1 counter's equal to this card's power on target creature. Scavenge only as a sorcery.)
        addCard(Zone.GRAVEYARD, playerA, "Deadbridge Goliath"); // 5/5

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scavenge", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Deadbridge Goliath", 0);
        assertExileCount(playerA, "Deadbridge Goliath", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 7, 7);
    }

    @Test
    public void SimpleScavengeWithCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        // Scavenge {4}{G}{G} ({4}{G}{G} , Exile this card from your graveyard: Put a number of +1/+1 counter's equal to this card's power on target creature. Scavenge only as a sorcery.)
        addCard(Zone.GRAVEYARD, playerA, "Deadbridge Goliath"); // 5/5

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Activated abilities of creature cards in your graveyard cost {1} less to activate.
        // Tap an untapped Zombie you control: Target player puts the top card of their library into their graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Embalmer's Tools", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scavenge", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Deadbridge Goliath", 0);
        assertExileCount(playerA, "Deadbridge Goliath", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 7, 7);
        assertTappedCount("Forest", true, 5);
        assertTappedCount("Forest", false, 1);
    }

    @Test
    public void ScavengeWithVaroz() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.GRAVEYARD, playerA, "Pillarfield Ox"); // 2/4

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost.
        // Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
        addCard(Zone.BATTLEFIELD, playerA, "Varolz, the Scar-Striped", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scavenge", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pillarfield Ox", 0);
        assertExileCount(playerA, "Pillarfield Ox", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void ScavengeWithVarozAndCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.GRAVEYARD, playerA, "Pillarfield Ox"); // 2/4

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost.
        // Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
        addCard(Zone.BATTLEFIELD, playerA, "Varolz, the Scar-Striped", 1);

        // Activated abilities of creature cards in your graveyard cost {1} less to activate.
        // Tap an untapped Zombie you control: Target player puts the top card of their library into their graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Embalmer's Tools", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scavenge", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pillarfield Ox", 0);
        assertExileCount(playerA, "Pillarfield Ox", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertTappedCount("Plains", true, 3);
        assertTappedCount("Plains", false, 1);
    }
}
