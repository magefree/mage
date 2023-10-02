package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Sigarda, Host of Herons:
 *   Spells and abilities your opponents control can't cause you to sacrifice permanents.
 *
 * @author noxx
 */
public class SigardaHostOfHeronsTest extends CardTestPlayerBase {

    /**
     * Tests that spells don't work for opponents but still work for controller
     */
    @Test
    public void testCard() {
        // Spells and abilities your opponents control can't cause you to sacrifice permanents.
        addCard(Zone.BATTLEFIELD, playerA, "Sigarda, Host of Herons");
        // {T}, Tap two untapped Humans you control: Exile target artifact or enchantment.
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        // {2}{B}, Sacrifice a creature: Target opponent reveals their hand. You choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        addCard(Zone.HAND, playerB, "Diabolic Edict");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // At the beginning of your upkeep, return target creature card from your graveyard to the battlefield.
        // At the beginning of each opponent's upkeep, that player sacrifices a creature.
        addCard(Zone.BATTLEFIELD, playerB, "Sheoldred, Whispering One");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Diabolic Edict", playerA); // sacrificing for player A prevented by Sigarda
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Diabolic Edict", playerB); // playerB has to sacrifice Sheldred

        //setStrictChooseMode(true); // TODO: test must be fixed with correct targets
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Sigarda, Host of Herons", 1);
        assertPermanentCount(playerA, "Devout Chaplain", 1);
        assertPermanentCount(playerA, "Corpse Traders", 1);
        assertGraveyardCount(playerA, "Diabolic Edict", 1);
        assertGraveyardCount(playerA, 1);

        assertPermanentCount(playerB, "Sheoldred, Whispering One", 0);
        assertHandCount(playerB, "Diabolic Edict", 0);
        assertGraveyardCount(playerB, "Sheoldred, Whispering One", 1);
        assertGraveyardCount(playerB, "Diabolic Edict", 1);
        assertGraveyardCount(playerB, 2);
    }

    /**
     * Tests that sacrifice cost works.
     */
    @Test
    public void testSacrificeCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Sigarda, Host of Herons");
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        //setStrictChooseMode(true); // TODO: test must be fixed with correct targets
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Devout Chaplain", 0);
        assertPermanentCount(playerA, "Corpse Traders", 1);
    }
    
    @Test
    public void testUseWithMercilessExecutioner() {
        /** I put a merciless executioner in play as part 
            of the resolution of Tempt With Immortality. An opponent who put 
            Sigarda, Host of Herons into play from Tempt With Immortality, 
            was forced to sacrifice her, even though her text says that that shouldn't happen.
        */
        
        // Spells and abilities your opponents control can't cause you to sacrifice permanents.
        addCard(Zone.GRAVEYARD, playerA, "Sigarda, Host of Herons");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        // When Merciless Executioner enters the battlefield, each player sacrifices a creature.
        addCard(Zone.GRAVEYARD, playerB, "Merciless Executioner");
        
        // Tempting offer — Return a creature card from your graveyard to the battlefield. 
        // Each opponent may return a creature card from their graveyard to the battlefield.
        // For each player who does, return a creature card from your graveyard to the battlefield.
        addCard(Zone.HAND, playerB, "Tempt with Immortality"); // sorcery {4}{B}
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Tempt with Immortality");
        setChoice(playerB, "Merciless Executioner");        
        setChoice(playerA, "Sigarda, Host of Herons");

        //setStrictChooseMode(true); // TODO: test must be fixed with correct targets
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerB, "Tempt with Immortality", 1);
        assertGraveyardCount(playerA, "Sigarda, Host of Herons", 0);
        assertGraveyardCount(playerB, "Merciless Executioner", 1);    
    }

}
