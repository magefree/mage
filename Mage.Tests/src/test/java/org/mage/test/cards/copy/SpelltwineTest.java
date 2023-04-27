package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SpelltwineTest extends CardTestPlayerBase {

    /**
     * Spelltwine Sorcery, 5U (6) Exile target instant or sorcery card from your
     * graveyard and target instant or sorcery card from an opponent's
     * graveyard. Copy those cards. Cast the copies if able without paying their
     * mana costs. Exile Spelltwine.
     *
     */
    @Test
    public void testCopyCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Spelltwine");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerB, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spelltwine");
        // addTarget(playerA, "Lightning Bolt"); Autochosen, only target
        // addTarget(playerA, "Shock"); Autochosen, only target

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Spelltwine", 1);
        assertExileCount("Lightning Bolt", 1);
        assertExileCount("Shock", 1);
        assertLife(playerB, 15);

    }

    /**
     * In a game of Commander, I cast Spelltwine, targeting Impulse and
     * Blasphemous Act. This triggered my Mirari, which I paid the 3 and copied
     * the Spelltwine. I chose new targets for the copy, naming Path to Exile
     * and Shape Anew. Somehow, the original Spelltwine was completely lost
     * after this, failing to be in the stack box or resolve all.
     */
    @Test
    public void testCopyCardsMirari() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        // Exile target instant or sorcery card from your graveyard and target instant or sorcery card from an opponent's graveyard.
        // Copy those cards. Cast the copies if able without paying their mana costs. Exile Spelltwine.
        addCard(Zone.HAND, playerA, "Spelltwine"); // {5}{U}
        // Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        addCard(Zone.GRAVEYARD, playerA, "Impulse");
        // You draw two cards and you lose 2 life.
        addCard(Zone.GRAVEYARD, playerA, "Night's Whisper");
        // Blasphemous Act costs {1} less to cast for each creature on the battlefield.
        // Blasphemous Act deals 13 damage to each creature.
        addCard(Zone.GRAVEYARD, playerB, "Blasphemous Act");
        // Draw two cards.
        addCard(Zone.GRAVEYARD, playerB, "Divination");

        // Whenever you cast an instant or sorcery spell, you may pay {3}. If you do, copy that spell. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Mirari", 1);

        // cast spellwin
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spelltwine");
        addTarget(playerA, "Impulse"); // target 1 to exile
        addTarget(playerA, "Blasphemous Act"); // target 2 to exile

        setChoice(playerA, true); //  pay {3} and copy spell
        setChoice(playerA, true); // Change targets
        addTarget(playerA, "Night's Whisper");
        addTarget(playerA, "Divination");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Impulse", 1);
        assertExileCount("Blasphemous Act", 1);
        assertExileCount("Spelltwine", 1);
        assertExileCount("Night's Whisper", 1);
        assertExileCount("Divination", 1);

        assertHandCount(playerA, 5);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }
}
