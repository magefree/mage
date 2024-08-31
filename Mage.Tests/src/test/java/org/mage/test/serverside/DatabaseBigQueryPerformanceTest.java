package org.mage.test.serverside;

import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class DatabaseBigQueryPerformanceTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_GetLands_SQL() {
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("must load getNames", CardRepository.instance.getNames().size() > 1000);
        Assert.assertTrue("must load getNonLandNames", CardRepository.instance.getNonLandNames().size() > 1000);
        Assert.assertTrue("must load getArtifactNames", CardRepository.instance.getArtifactNames().size() > 1000);
        Assert.assertTrue("must load getCreatureNames", CardRepository.instance.getCreatureNames().size() > 1000);
        Assert.assertTrue("must load getNonArtifactAndNonLandNames", CardRepository.instance.getNonArtifactAndNonLandNames().size() > 1000);
        Assert.assertTrue("must load getNonLandAndNonCreatureNames", CardRepository.instance.getNonLandAndNonCreatureNames().size() > 1000);
    }

    @Test
    public void test_GetLands_RealGame_Manual() {
        // Name a nonland card. Target player reveals their hand. That player discards a card with that name.
        // If they can't, you draw a card.
        addCard(Zone.HAND, playerA, "Brain Pry", 1); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brain Pry", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, "Balduvian Bears"); // name to choose

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_GetLands_RealGame_AI() {
        // possible bug: game simulations can call big queries multiple times and overflow database cache to crash it
        // how-to fix: increase CACHE_SIZE in DatabaseUtils (require 150 000 kb on 2024)
        int cardsAmount = 5;

        // Name a nonland card. Target player reveals their hand. That player discards a card with that name.
        // If they can't, you draw a card.
        addCard(Zone.HAND, playerA, "Brain Pry", cardsAmount); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 * cardsAmount);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
