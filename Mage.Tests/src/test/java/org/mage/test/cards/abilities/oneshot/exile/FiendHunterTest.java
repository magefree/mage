package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FiendHunterTest extends CardTestPlayerBase {

    /**
     *
     *
     * Hi i rencently play against an opponent that when i did the bounce
     * ability with Restoration Angel to Fiend hunter to Exile Primeval Titan
     * the first primeval titan that i exiled didnt came back into play.
     *
     * Just to be sure i exile primeval titan the first time that fiend hunter
     * came into play and i resto angel fiend hunter to exile another primeval
     * titan Fiend hunter was on the battlefield for 4-6 rounds. When Fiend
     * hunter is remove from the battlefield the exile ability is suppose lose
     * his effect and the first Titan is suppose to come back.
     */
    @Test
    public void testExileWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // When Fiend Hunter enters the battlefield, you may exile another target creature.
        // When Fiend Hunter leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Fiend Hunter"); // Creature - Human Cleric 1/3  {1}{W}{W}

        // At the beginning of your upkeep, you lose 1 life and put a 1/1 black Faerie Rogue creature token with flying onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Primeval Titan");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiend Hunter");
        // Target autochosen since only one option

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount("Primeval Titan", 1);
        assertPermanentCount(playerA, "Fiend Hunter", 1);

    }

    @Test
    public void testExileAndReturnWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // When Fiend Hunter enters the battlefield, you may exile another target creature.
        // When Fiend Hunter leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Fiend Hunter"); // Creature - Human Cleric 1/3  {1}{W}{W}
        // When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control, then return that card to the battlefield under your control
        addCard(Zone.HAND, playerA, "Restoration Angel"); // Creature - Angel 3/4  {3}{W}

        // At the beginning of your upkeep, you lose 1 life and put a 1/1 black Faerie Rogue creature token with flying onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Primeval Titan");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiend Hunter");
        // addTarget(playerA, "Primeval Titan"); Autochosen, only option

        // When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control, then return that card to the battlefield under your control
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Restoration Angel");
        // addTarget(playerA, "Fiend Hunter"); Autochosen, only option

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Fiend Hunter", 1);
        assertPermanentCount(playerA, "Restoration Angel", 1);
        assertPermanentCount(playerB, "Primeval Titan", 1);

    }

}
