package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * New SOI mechanics change requires the night-side CMC to be equal to the
 * day-side CMC
 *
 * @author escplan9
 */
public class NightSideCMCTest extends CardTestPlayerBase {
    
    // Delver of Secrets {U} 1/1
    // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
    private final String delver = "Delver of Secrets";
    // Night-side of Delver, 3/2 Flying should have CMC 1 as well
    private final String insect = "Insectile Aberration";

    /**
     * Repeal for X = 1 on Delver flipped "Insectile Aberration"
     */
    @Test
    public void insectileAbberationRepealXis1Test() {
            
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, delver);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // to transform Delver of Secrets
        // Instant - {X}{U}
        // Return target nonland permanent with converted mana cost X to its owner's hand. Draw a card.
        addCard(Zone.HAND, playerB, "Repeal");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Repeal");
        setChoice(playerB, "X=1");
        // Insectile Aberration is auto-chosen since only target

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Repeal", 1);
        assertPermanentCount(playerA, insect, 0);
        assertHandCount(playerA, delver, 1); // day-side of Insectile Abberation returned to hand
    }
    
    /**
     * Engineered Explosives for Sunburst = 1 on Delver flipped "Insectile Aberration"
     */
    @Test
    public void insectileAbberationEngeeredExplosivesSunburstIs1Test() {
        
        /*
        Engineered Explosives {X}
       Artifact
       Sunburst (This enters the battlefield with a charge counter on it for each color of mana spent to cast it.)
        {2}, Sacrifice Engineered Explosives: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives.
        */
        String explosives = "Engineered Explosives";
        
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, delver);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // to transform Delver of Secrets
        
        addCard(Zone.HAND, playerB, explosives);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, explosives);
        setChoice(playerB, "X=1");
        activateAbility(2, PhaseStep.BEGIN_COMBAT, playerB, "{2}"); // activate explosives

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, explosives, 1);
        assertPermanentCount(playerA, insect, 0);
        assertGraveyardCount(playerA, delver, 1);
    }
}
