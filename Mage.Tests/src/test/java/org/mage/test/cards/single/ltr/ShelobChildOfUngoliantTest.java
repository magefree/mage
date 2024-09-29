package org.mage.test.cards.single.ltr;

import mage.abilities.keyword.BandingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ShelobChildOfUngoliantTest extends CardTestPlayerBase {

    /**
     * Shelob, Child of Ungoliant
     * {4}{B}{G} <br>
     * Legendary Creature — Spider Demon <br>
     *
     * Deathtouch, ward {2} <br>
     * Other Spiders you control have deathtouch and ward {2}. <br> <br>
     *
     * Whenever another creature dealt damage this turn by a Spider you controlled dies,
     * create a token that’s a copy of that creature, except it’s a Food artifact with
     * "{2}, {T}, Sacrifice this artifact: You gain 3 life,” and it loses all other card types.
     */
    private static final String shelob = "Shelob, Child of Ungoliant";

    /**
     * find a permanent controlled by a plyaer with the provided name.
     * assert that it is a food artifact token.
     */
    private void assertIsFoodArtifactToken(Player player, String cardName) {
        assertPermanentCount(player, cardName, 1);
        assertTokenCount(player, cardName, 1);
        assertType(cardName, CardType.ARTIFACT, true);
        assertSubtype(cardName, SubType.FOOD);
    }

    @Test
    public void test_Shelob_NoSpiderDamage() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Destroy all creatures
        addCard(Zone.HAND, playerA, "Day of Judgment"); // {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // Grizzly Bears {1}{G}
        // Creature — Bear
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day of Judgment");
        // No Token copy of anything.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_Shelob_DieTogether() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Prey Upon {G} Sorcery
        // Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Prey Upon"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Feral Abomination {5}{B}
        // Creature — Thrull
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Feral Abomination");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prey Upon", shelob + "^Feral Abomination");
        // Both Shelob and Abomination die, a token of Abomination is created

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertIsFoodArtifactToken(playerA, "Feral Abomination");
        assertNotType("Feral Abomination", CardType.CREATURE);
        assertNotSubtype("Feral Abomination", SubType.THRULL);
    }

    @Test
    public void test_Shelob_TokenKeepsArtifactSubtypes() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Prey Upon {G} Sorcery
        // Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Prey Upon"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Artifact Creature — Equipment Lizard
        addCard(Zone.BATTLEFIELD, playerB, "Lizard Blades");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prey Upon", shelob + "^Lizard Blades");
        // Lizard die, a token of Lizard is created

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertIsFoodArtifactToken(playerA, "Lizard Blades");
        assertNotType("Lizard Blades", CardType.CREATURE);
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
        assertSubtype("Lizard Blades", SubType.EQUIPMENT);
    }

    @Test
    public void test_Shelob_TokenIsCopySoHasText() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Prey Upon {G} Sorcery
        // Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Prey Upon"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // When Angel of Mercy enters the battlefield, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerB, "Angel of Mercy");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prey Upon", shelob + "^Angel of Mercy");
        // Angel die, a token of Angel is created

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertIsFoodArtifactToken(playerA, "Angel of Mercy");
        assertLife(playerA, 23);
    }

    @Test
    public void test_Shelob_GristDoesNotDieAsCreature() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Target creature you control deals damage equal to
        // its power to target creature or planeswalker you don’t control.
        addCard(Zone.HAND, playerA, "Bite Down"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Grist, the Hunger Tide
        // Legendary Planeswalker — Grist
        // As long as Grist, the Hunger Tide isn't on the battlefield,
        // it’s a 1/1 Insect creature in addition to its other types.
        addCard(Zone.BATTLEFIELD, playerB, "Grist, the Hunger Tide");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bite Down", shelob + "^Grist, the Hunger Tide");
        // Grist die, no token is created as it is a creature right after

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Grist, the Hunger Tide", 0);
        assertPermanentCount(playerB, "Grist, the Hunger Tide", 0);
    }

    @Test
    public void test_Shelob_AnimatedPermamentIsCopied() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Target creature you control deals damage equal to
        // its power to target creature or planeswalker you don’t control.
        addCard(Zone.HAND, playerA, "Bite Down"); // {1}{G}
        // Ensoul Artifact
        // Enchantment — Aura
        // Enchant artifact
        // Enchanted artifact is a creature with base power and toughness
        // 5/5 in addition to its other types.
        addCard(Zone.HAND, playerA, "Ensoul Artifact"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);

        // Aradara Express
        // Artifact — Vehicle
        addCard(Zone.BATTLEFIELD, playerB, "Aradara Express");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ensoul Artifact", "Aradara Express");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bite Down", shelob + "^Aradara Express");
        // Aradara Express die as a creature, shelob makes a copy.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertIsFoodArtifactToken(playerA, "Aradara Express");
        assertSubtype("Aradara Express", SubType.VEHICLE);
    }

    @Test
    public void test_Shelob_TokenIsCopied() {
        addCard(Zone.BATTLEFIELD, playerA, shelob, 1);
        // Target creature you control deals damage equal to
        // its power to target creature or planeswalker you don’t control.
        addCard(Zone.HAND, playerA, "Bite Down"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // Errand of Duty {1}{W}
        // Instant
        // Create a 1/1 white Knight creature token with banding.
        addCard(Zone.HAND, playerB, "Errand of Duty");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Errand of Duty");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bite Down", shelob);
        addTarget(playerA,"Knight Token");
        // Knight Token die as a creature, shelob makes a copy.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertIsFoodArtifactToken(playerA, "Knight Token");
        assertAbility(playerA, "Knight Token", BandingAbility.getInstance(), true);
    }

    @Test
    public void test_Shelob_EarlySpiderDamageCounts() {
        addCard(Zone.HAND, playerA, shelob, 1);
        // 2/4 Spider
        addCard(Zone.BATTLEFIELD, playerA, "Giant Spider", 1);
        // Target creature you control deals damage equal to
        // its power to target creature or planeswalker you don’t control.
        addCard(Zone.HAND, playerA, "Bite Down"); // {1}{G}
        // Destroy target non-black creature.
        addCard(Zone.HAND, playerA, "Doom Blade"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 10);

        // Creature 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Colossapede", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bite Down", "Giant Spider^Colossapede");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Dealing spider damage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shelob);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Colossapede");
        // Colossapede die as a creature, shelob makes a copy.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertIsFoodArtifactToken(playerA, "Colossapede");
    }
}
