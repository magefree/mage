package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */

public class CommanderColorChangeTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // When a player casts a spell or a creature attacks, exile Norin the Wary. Return it to the battlefield under its owner's control at the beginning of the next end step.
        setDecknamePlayerA("CMDNorinTheWary.dck"); // Commander = Norin the Wary {R}
        return super.createNewGameAndPlayers();
    }
    
    @Test
    public void castCommanderWithAddedBlueColor() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant", 1); // Artifact Creature {2}
        
        // Whenever a player casts a blue spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Kraken's Eye", 1);
        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant", true);
        setChoice(playerA, "Blue");
        
        // When a player casts a spell or a creature attacks, exile Norin the Wary. 
        // Return it to the battlefield under its owner's control at the beginning of the next end step.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Norin the Wary", true);
        setChoice(playerA, true); //  Whenever a player casts a blue spell, you may gain 1 life. Choices: Yes - No
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Norin the Wary", 1);
        
        Permanent norin = getPermanent("Norin the Wary", playerA);
        Assert.assertTrue(norin.getColor(currentGame).isBlue());
        Assert.assertTrue(norin.getColor(currentGame).isRed());
        
        Permanent kraken = getPermanent("Kraken's Eye", playerA);
        Assert.assertTrue(kraken.getColor(currentGame).isBlue());
        
        assertLife(playerA, 41);
        assertLife(playerB, 40);

    }


    /**
     * I played a Painter's Servant, named black, but the other commanders get a extra colors
     * Later it got removed but the commanders and some cards still have the extra color
     * I played it again later, named green, and the previously affected cards get the extra color
     * so now they have 2 extra colors and the commander get and additional color on top of that
     * And finally I got the empty hand error #6738 on my turn for what I assume is the Painter's Servant + Grindstone combo I have,
     * but nonetheless manage to tie the game so it go into a second game and the issue carry over,
     * all the commanders have all the extra colors they gain from the first game
     */
    
    @Test
    public void castCommanderWithoutAddedBlueColor() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant", 1); // Artifact Creature {2}
        
        // Whenever a player casts a blue spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Kraken's Eye", 1);
        

        // Exile target artifact or enchantment.
        addCard(Zone.HAND, playerB, "Altar's Light", 1); // Instant {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant", true);
        setChoice(playerA, "Blue");
        
        // When a player casts a spell or a creature attacks, exile Norin the Wary. 
        // Return it to the battlefield under its owner's control at the beginning of the next end step.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Norin the Wary", true);
        setChoice(playerA, true); //  Whenever a player casts a blue spell, you may gain 1 life. Choices: Yes - No
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Altar's Light", "Painter's Servant", "Norin the Wary");
        setChoice(playerA, true); //  Whenever a player casts a blue spell, you may gain 1 life. Choices: Yes - No
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Norin the Wary", 1);

        Permanent norin = getPermanent("Norin the Wary", playerA);
        Assert.assertEquals(false, norin.getColor(currentGame).isBlue());
        Assert.assertEquals(true, norin.getColor(currentGame).isRed());

        Permanent kraken = getPermanent("Kraken's Eye", playerA);
        Assert.assertEquals(false, kraken.getColor(currentGame).isBlue());
        
        assertLife(playerA, 42);
        assertLife(playerB, 40);

    }    
}
