package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by goesta on 12/02/2017.  Modified by jeffwadsworth
 */
public class SpellskiteTest extends CardTestPlayerBase {

    @Test
    public void testThatSpellSkiteCantBeTargetedTwiceOrMore() {
        /* According to rules, the same object can be a legal target only 
           once for each instances of the word “target” in the text 
           of a spell or ability.  In this case, the target can't be changed 
           due to Spellskite already being a target.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");
        addCard(Zone.BATTLEFIELD, playerB, "Scute Mob");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        addCard(Zone.HAND, playerA, "Fiery Justice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice");
        addTarget(playerA, "Scute Mob");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Spellskite");
        setChoice(playerA, "X=4");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{UP}: Change a target of target spell or ability to {this}.", "Fiery Justice", "Fiery Justice");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 2);
    }
    
    public void testThatSplitDamageCanGetRedirected() {
        /* Standard redirect test 
           The Spellskite should die from the 5 damage that was redirected to it
        */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");// 0/4 creature
        addCard(Zone.BATTLEFIELD, playerB, "Scute Mob"); // 1/1 creauture
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        addCard(Zone.HAND, playerA, "Fiery Justice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice"); // 5 damage distributed to any number of targets
        addTarget(playerA, "Scute Mob");
        setChoice(playerA, "X=5");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{UP}: Change a target of target spell or ability to {this}.", "Fiery Justice", "Fiery Justice");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 1);
        assertPermanentCount(playerB, "Scute Mob", 1);
    }
    
    public void testThatSplitDamageGetsRedirectedFromTheCorrectChoice() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Spellskite");// 0/4 creature
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1 creauture
        addCard(Zone.BATTLEFIELD, playerB, "Royal Assassin");
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit");
        addCard(Zone.BATTLEFIELD, playerB, "Pearled Unicorn");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        addCard(Zone.HAND, playerA, "Fiery Justice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Justice"); // 5 damage distributed to any number of targets
        addTarget(playerA, "Memnite");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Royal Assassin");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Blinking Spirit");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Pearled Unicorn");
        setChoice(playerA, "X=2");//the unicorn deserves it
        

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{UP}: Change a target of target spell or ability to {this}.", "Fiery Justice", "Fiery Justice");
        setChoice(playerA, "No");
        setChoice(playerA, "No");
        setChoice(playerA, "No");
        setChoice(playerA, "Yes"); //of course

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 3);
        assertPermanentCount(playerB, "Pearled Unicorn", 1);//it lives on
        assertPowerToughness(playerB, "Spellskite", 0, 2);
    }
}
