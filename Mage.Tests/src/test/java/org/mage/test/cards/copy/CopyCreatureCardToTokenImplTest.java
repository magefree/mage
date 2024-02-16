package org.mage.test.cards.copy;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CopyCreatureCardToTokenImplTest extends CardTestPlayerBase {

    /**
     * Unesh, Criosphinx Sovereign did not have his ETB effect trigger when he
     * had a token copy of himself made through casting Hour of Eternity. I
     * think there was another creature too that didn't get the ETB effect
     * either.
     */
    @Test
    @Ignore
    public void testTokenTriggeresETBEffect() {
        // Flying
        // Sphinx spells you cast cost {2} less to cast.
        // Whenever Unesh, Criosphinx Sovereign or another Sphinx enters the battlefield
        // under your control, reveal the top four cards of your library. An opponent seperates
        // those cards into two piles. Put one pile into your hand and the other into your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Unesh, Criosphinx Sovereign", 1); // Sphinx 4/4

        // Exile X target creature cards from your graveyard. For each card exiled this way,
        // create a token that's a copy of that card, except it's a 4/4 black Zombie.
        addCard(Zone.HAND, playerA, "Hour of Eternity"); // Sorcery {X}{X}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hour of Eternity", "Unesh, Criosphinx Sovereign");
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Hour of Eternity", 1);
        assertPermanentCount(playerA, "Unesh, Criosphinx Sovereign", 1);
        assertGraveyardCount(playerA, "Unesh, Criosphinx Sovereign", 0);
        assertExileCount(playerA, "Unesh, Criosphinx Sovereign", 1);

        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, 2);
    }

    /* https://github.com/magefree/mage/issues/5904
    I had a Faerie Artisans on the battlefield. My opponent played a Thrashing Brontodon.
    In response to the Faerie Artisans ability, my opponent sacrificed the Brontodon to destroy an artifact.
    When the Faerie Artisans trigger resolved, no token was created. */
    @Test
    public void testFaerieArtisans() {
        // Flying
        // Whenever a nontoken creature enters the battlefield under an opponent's control,
        // create a token that's a copy of that creature except it's an artifact in addition to its other types.
        // Then exile all other tokens created with Faerie Artisans.
        addCard(Zone.BATTLEFIELD, playerA, "Faerie Artisans", 1); // Creature {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 1); // Artifact creature 2/1

        // {1}, Sacrifice Thrashing Brontodon: Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerB, "Thrashing Brontodon"); // Creature {1}{G}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Thrashing Brontodon", true);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}, Sacrifice", "Alpha Myr");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Thrashing Brontodon", 1);
        assertGraveyardCount(playerA, "Alpha Myr", 1);
        assertPermanentCount(playerA, "Thrashing Brontodon", 1);
        assertType("Thrashing Brontodon", CardType.ARTIFACT, true);
    }
}
