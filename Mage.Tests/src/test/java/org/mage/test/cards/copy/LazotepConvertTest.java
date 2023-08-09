package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public class LazotepConvertTest extends CardTestPlayerBase {
    @Test
    public void testInvastionAmonkhetTransformed() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        addCard(Zone.GRAVEYARD, playerA, "Mutagen Connoisseur", 1);
        addCard(Zone.HAND, playerA, "Invasion of Amonkhet");
        addCard(Zone.HAND, playerA,"Char" );
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Invasion of Amonkhet");
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Char", "Invasion of Amonkhet");
        //setChoice(playerA,true);
        //addTarget(playerA,"Mutagen Connoisseur");
        //These are auto-chosen by the AI

        //Should make a 4/4 BGU Vedalken Mutant Zombie
        //And count as transformed, so it's a 5/4

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        currentGame.debugMessage("Graveyard: "+currentGame.getPlayer(playerA.getId()).getGraveyard().stream()
                .map(x -> currentGame.getObject(x).getClass().getSimpleName()).collect(Collectors.toList()));

        assertPermanentCount(playerA, "Mutagen Connoisseur", 1);
        assertGraveyardCount(playerA, "Mutagen Connoisseur", 1);
        assertSubtype("Mutagen Connoisseur", SubType.ZOMBIE);
        assertSubtype("Mutagen Connoisseur", SubType.VEDALKEN);
        assertColor(playerA,"Mutagen Connoisseur","BGU",true);
        assertPowerToughness(playerA,"Mutagen Connoisseur",5,4);
    }
}

