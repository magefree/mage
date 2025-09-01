package org.mage.test.cards.single.dft;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.ExileZone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jmlundeen
 */
public class GontiNightMinisterTest extends CardTestCommander4Players {

    /*
    Gonti, Night Minister
    {2}{B}{B}
    Legendary Creature - Aetherborn Rogue
    Whenever a player casts a spell they don't own, that player creates a Treasure token.
    Whenever a creature deals combat damage to one of your opponents, its controller looks at the top card of that opponent's library and exiles it face down. They may play that card for as long as it remains exiled. Mana of any type can be spent to cast a spell this way.
    3/4
    */
    private static final String gontiNightMinister = "Gonti, Night Minister";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Fugitive Wizard
    {U}
    Creature - Human Wizard
    1/1
    */
    private static final String fugitiveWizard = "Fugitive Wizard";


    @Test
    public void testGontiNightMinister() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gontiNightMinister);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerD, bearCub);
        addCard(Zone.BATTLEFIELD, playerD, "Mountain");
        addCard(Zone.LIBRARY, playerC, fugitiveWizard);
        addCard(Zone.LIBRARY, playerB, fugitiveWizard);

        attack(1, playerA, gontiNightMinister, playerC);
        runCode("only playerA can see fugitive wizard", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            for (ExileZone zone : game.getExile().getExileZones()) {
                for (Card card : zone.getCards(game)) {
                    if (card.isFaceDown(game)) {
                        assertTrue("player A can see the card", zone.isPlayerAllowedToSeeCard(playerA.getId(), card));
                        assertFalse("player B can not see the card", zone.isPlayerAllowedToSeeCard(playerB.getId(), card));
                        assertFalse("player C can not see the card", zone.isPlayerAllowedToSeeCard(playerC.getId(), card));
                        assertFalse("player D can not see the card", zone.isPlayerAllowedToSeeCard(playerD.getId(), card));
                    }
                }
            }
        });
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, fugitiveWizard);

        attack(2, playerD, bearCub, playerB);
        runCode("only playerD can see fugitive wizard", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            for (ExileZone zone : game.getExile().getExileZones()) {
                for (Card card : zone.getCards(game)) {
                    if (card.isFaceDown(game)) {
                        assertTrue("player D can see the card", zone.isPlayerAllowedToSeeCard(playerD.getId(), card));
                        assertFalse("player A can not see the card", zone.isPlayerAllowedToSeeCard(playerA.getId(), card));
                        assertFalse("player B can not see the card", zone.isPlayerAllowedToSeeCard(playerB.getId(), card));
                        assertFalse("player C can not see the card", zone.isPlayerAllowedToSeeCard(playerC.getId(), card));
                    }
                }
            }
        });
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerD, fugitiveWizard);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 1);
        assertPermanentCount(playerD, "Treasure Token", 1);
    }
}