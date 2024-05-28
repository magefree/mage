package org.mage.test.cards.abilities.other;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class CastFromGraveyardOnceTest extends CardTestPlayerBase {

    private static final String danitha = "Danitha, New Benalia's Light"; // 2/2
    // Vigilance, trample, lifelink
    // Once during each of your turns, you may cast an Aura or Equipment spell from your graveyard.

    private static final String bonesplitter = "Bonesplitter"; // 1 mana equip 1 for +2/+0
    private static final String kitesail = "Kitesail"; // 2 mana equip 2 for +1/+0 and flying
    private static final String machete = "Trusty Machete"; // 1 mana equip 2 for +2/+1
    private static final String creature = "Field Creeper"; // 2 mana 2/1
    private static final String halvar = "Halvar, God of Battle"; // MDFC front side - creature 2WW
    private static final String sword = "Sword of the Realms"; // MDFC back side - equipment 1W
    private static final String auraMorph = "Gift of Doom"; // 4B enchant creature, or morph
    private static final String saguArcher = "Sagu Archer"; // 4G 2/5 reach, or morph
    private static final String unicorn = "Lonesome Unicorn"; // 4W 3/3 with adventure 2W 2/2 token
    private static final String rider = "Rider in Need";

    private static final String karador = "Karador, Ghost Chieftain";
    // Once during each of your turns, you may cast a creature spell from your graveyard.

    @Test
    public void testDanithaAllowsOneCast() {
        addCard(Zone.BATTLEFIELD, playerA, danitha);
        addCard(Zone.GRAVEYARD, playerA, bonesplitter);
        addCard(Zone.GRAVEYARD, playerA, kitesail);
        addCard(Zone.GRAVEYARD, playerB, machete);
        addCard(Zone.GRAVEYARD, playerA, creature);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Raff Capashen, Ship's Mage"); // historic spells have flash

        checkPlayableAbility("bonesplitter your turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bonesplitter, true);
        checkPlayableAbility("kitesail your turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + kitesail, true);
        checkPlayableAbility("only your graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + machete, false);
        checkPlayableAbility("creature not permitted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + creature, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kitesail);

        checkPermanentCount("kitesail on battlefield", 1, PhaseStep.BEGIN_COMBAT, playerA, kitesail, 1);
        checkPlayableAbility("no second cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bonesplitter, false);

        checkPlayableAbility("not during opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bonesplitter, false);

        checkPlayableAbility("available next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bonesplitter, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, bonesplitter);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bonesplitter, 1);
    }

    @Test
    public void testDanithaMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, danitha);
        addCard(Zone.GRAVEYARD, playerA, halvar);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        checkPlayableAbility("front not allowed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + halvar, false);
        checkPlayableAbility("back allowed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + sword, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sword);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sword, 1);
    }

    @Test
    public void testDanithaMorph() {
        addCard(Zone.BATTLEFIELD, playerA, danitha);
        addCard(Zone.GRAVEYARD, playerA, auraMorph);
        addCard(Zone.BATTLEFIELD, playerA, creature); // to enchant
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        checkPlayableAbility("morph not allowed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + auraMorph + " using Morph", false);
        checkPlayableAbility("aura allowed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + auraMorph, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, auraMorph, creature);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, auraMorph, 1);
        assertAbility(playerA, creature, IndestructibleAbility.getInstance(), true);
    }

    @Test
    public void testKaradorCastWithoutMorph() {
        addCard(Zone.BATTLEFIELD, playerA, karador);
        addCard(Zone.GRAVEYARD, playerA, saguArcher);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        checkPlayableAbility("with morph", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + saguArcher + " using Morph", true);
        checkPlayableAbility("without morph", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + saguArcher, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saguArcher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, saguArcher, 1);
    }

    @Test
    public void testKaradorCastWithMorph() {
        addCard(Zone.BATTLEFIELD, playerA, karador);
        addCard(Zone.GRAVEYARD, playerA, saguArcher);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        checkPlayableAbility("with morph", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + saguArcher + " using Morph", true);
        checkPlayableAbility("without morph", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + saguArcher, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saguArcher + " using Morph");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void testKaradorCastAdventure() {
        addCard(Zone.BATTLEFIELD, playerA, karador);
        addCard(Zone.GRAVEYARD, playerA, unicorn);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        checkPlayableAbility("creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + unicorn, true);
        checkPlayableAbility("sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + rider, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, unicorn);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, unicorn, 1);
    }

}
