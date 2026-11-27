package org.mage.test.cards.single.mom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class IngaAndEsikaTest extends CardTestPlayerBase {

    @Test
    public void test_ManaForCreatureSpellsOnly() {
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        // Whenever you cast a creature spell, if three or more mana from creatures was spent to cast it, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        //
        addCard(Zone.HAND, playerA, "Centaur Courser"); // {2}{G}, creature
        addCard(Zone.HAND, playerA, "Divination"); // {2}{U}, sorcery

        checkPlayableAbility("creature mana can't cast a sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Divination", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Centaur Courser");
        setChoice(playerA, "Green", 3); // color of the creature mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Centaur Courser", 1);
        assertTappedCount("Grizzly Bears", true, 2);
        assertHandCount(playerA, 1 + 1); // Divination + Inga's draw (three mana from creatures)
    }

    @Test
    public void test_ManaForCreatureSpellsOnly_WithDualLands() {
        // lands with a choice of mana must not make the creature mana usable for other spells
        addCard(Zone.BATTLEFIELD, playerA, "Taiga"); // {R} or {G}
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Savannah"); // {G} or {W}
        //
        addCard(Zone.HAND, playerA, "Kodama's Reach"); // {2}{G}, sorcery
        addCard(Zone.HAND, playerA, "Hill Giant"); // {3}{R}, creature

        checkPlayableAbility("two lands can't cast a sorcery for three", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Kodama's Reach", false);
        checkPlayableAbility("lands and creatures can cast a creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hill Giant", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ManaWithOtherRestrictedMana() {
        // creature mana and multicolored mana can be spent together only on a multicolored creature spell
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);
        //
        addCard(Zone.HAND, playerA, "Cloudblazer"); // {3}{W}{U}, multicolored creature
        addCard(Zone.HAND, playerA, "Hill Giant"); // {3}{R}, red creature
        addCard(Zone.HAND, playerA, "Electrolyze"); // {1}{U}{R}, multicolored instant

        checkPlayableAbility("creature mana only for 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hill Giant", false);
        checkPlayableAbility("multicolored mana only for 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Electrolyze", false);
        checkPlayableAbility("all mana for a multicolored creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Cloudblazer", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ManyCreatures() {
        // a big board of creatures gives one mana each, all of it can go into one creature spell
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        // Whenever you cast a creature spell, if three or more mana from creatures was spent to cast it, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 29); // bear 2/2
        //
        addCard(Zone.HAND, playerA, "Darksteel Colossus"); // {11}, creature
        addCard(Zone.HAND, playerA, "Divination"); // {2}{U}, sorcery

        checkPlayableAbility("creature mana can't cast a sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Divination", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Colossus");
        setChoice(playerA, "Green", 11); // color of the creature mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Darksteel Colossus", 1);
        assertHandCount(playerA, 1 + 1); // Divination + Inga's draw (three or more mana from creatures)
    }

    @Test
    public void test_ManyCreatures_ManaInPool() {
        // the same, but the player taps all creatures for mana before casting the spell
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        // Whenever you cast a creature spell, if three or more mana from creatures was spent to cast it, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 29); // bear 2/2
        //
        addCard(Zone.HAND, playerA, "Darksteel Colossus"); // {11}, creature
        addCard(Zone.HAND, playerA, "Divination"); // {2}{U}, sorcery

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any color", 30);
        setChoice(playerA, "Green", 30); // color of the creature mana
        checkPlayableAbility("creature mana can't cast a sorcery", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Divination", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Colossus");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Darksteel Colossus", 1);
        assertHandCount(playerA, 1 + 1); // Divination + Inga's draw (three or more mana from creatures)
    }
}
