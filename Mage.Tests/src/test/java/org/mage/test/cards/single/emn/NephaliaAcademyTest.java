package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.n.NephaliaAcademy Nephalia Academy}
 * Land
 * If a spell or ability an opponent controls causes you to discard a card,
 * you may reveal that card and put it on top of your library instead of putting it anywhere else.
 * {T}: Add {C}.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class NephaliaAcademyTest extends CardTestPlayerBase {

    @Test
    public void testReplacementEffectBySpell() {
        // Sorcery {B}
        // Target opponent reveals their hand. You choose a noncreature, nonland card from it. That player discards that card.
        addCard(Zone.HAND, playerA, "Duress", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        
        addCard(Zone.HAND, playerB, "Giant Growth", 1); // discard fodder
        addCard(Zone.BATTLEFIELD, playerB, "Nephalia Academy", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duress");
        addTarget(playerA, playerB);
        setChoice(playerA, "Giant Growth"); // choose to discard Giant Growth
        setChoice(playerB, true); // replacement effect, choose to reveal the card and place on top of library
        execute();
                
        assertGraveyardCount(playerA, "Duress", 1);
        assertHandCount(playerB, "Giant Growth", 0);
        assertGraveyardCount(playerB, "Giant Growth", 0);
        assertLibraryCount(playerB, "Giant Growth", 1);
    }
    
    @Test
    public void testDeclineReplacementEffectBySpell() {
        // Sorcery {B}
        // Target opponent reveals their hand. You choose a noncreature, nonland card from it. That player discards that card.
        addCard(Zone.HAND, playerA, "Duress", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        
        addCard(Zone.HAND, playerB, "Giant Growth", 1); // discard fodder
        addCard(Zone.BATTLEFIELD, playerB, "Nephalia Academy", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Duress");
        addTarget(playerA, playerB);
        setChoice(playerA, "Giant Growth"); // choose to discard Giant Growth
        setChoice(playerB, false); // decline the replacement effect, allow the discard to happen
        execute();
                
        assertGraveyardCount(playerA, "Duress", 1);
        assertHandCount(playerB, "Giant Growth", 0);
        assertGraveyardCount(playerB, "Giant Growth", 1); // discarded to grave
    }
    
    @Test
    public void testShouldNotApplyToOwnDiscardSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Nephalia Academy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Memnite", 1); // discard fodder
        
        // Sift - Sorcery <3><U>
        // Draw three cards. Then discard a card.
        addCard(Zone.HAND, playerA, "Sift", 1);

        setStrictChooseMode(true);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sift");
        setChoice(playerA, "Memnite");
        // should not be given the option to use Nephalia Academy replacement effect
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Sift", 1);
        assertHandCount(playerA, "Memnite", 0);
        assertGraveyardCount(playerA, "Memnite", 1); // discarded to grave        
    }
    
    //TODO: Add tests for replacement effect by ability
    
}
