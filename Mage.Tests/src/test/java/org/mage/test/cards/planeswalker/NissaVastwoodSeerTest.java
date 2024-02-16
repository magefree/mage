package org.mage.test.cards.planeswalker;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 *
 * @author LevelX2
 */
public class NissaVastwoodSeerTest extends CardTestPlayerBase {

// https://github.com/magefree/mage/issues/5677
/*
EDH again, I'm playing Nissa, Vastwood Seer as my commander.
I got her to 7 loyalty, used her -7 to untap lands and make them into creatures. So far so good.
Nissa died, went back to command zone, lands stayed as creatures (as they should).
Next turn I replay Nissa, but the moment I try to cast another spell (or activate an ability, 
maybe it was directly on replaying her, can't remember exactly), all my 6 creature lands are back to being regular lands.
A few turns later, my opponent uses overloaded cyclonic rift, which also bounces Nissa to my hand.
Not entirely sure, but I believe it again took at least until priority got passed for my lands to reappear, 
maybe even until I tried to activate an ability or cast something.
This was not a 1 time thing either.
After replaying Nissa (again) and casting another card, my creature lands went back to being regular lands again.
Later I used her -2 to kill her, which (after putting another spell on the stack) got me my creature lands back again.
    */
    @Test
    public void NissaVastwoodSeerAnimationTest() {
        setStrictChooseMode(true);
        
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        
        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, 
        // reveal it, put it into your hand, then shuffle your library.
        // Whenever a land enters the battlefield under your control, if you control seven or more lands, 
        // exile Nissa, then return her to the battlefield transformed under her owner's control.
        // Nissa, Sage Animist
        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        // -2: Create a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World.
        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.        
        addCard(Zone.HAND, playerA, "Nissa, Vastwood Seer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nissa, Vastwood Seer", true);
        setChoice(playerA, true);
        addTarget(playerA, "Forest");
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1: Reveal");
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1: Reveal");
        activateAbility(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1: Reveal");
        activateAbility(7, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1: Reveal");
        activateAbility(9, PhaseStep.POSTCOMBAT_MAIN, playerA, "-7: Untap up to six target");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest");
        setStopAt(9, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nissa, Vastwood Seer", 0);
        assertPermanentCount(playerA, "Nissa, Sage Animist", 0);
        assertGraveyardCount(playerA, "Nissa, Vastwood Seer", 1);

        assertPermanentCount(playerA, "Swamp", 1);

        assertType("Forest", CardType.CREATURE, SubType.ELEMENTAL);        
        assertPermanentCount(playerA, "Forest", 6);        
        assertPowerToughness(playerA, "Forest", 6, 6, Filter.ComparisonScope.All);
        
        
        
        
    }
    
}
