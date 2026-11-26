package org.mage.test.cards.abilities.keywords;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class AftermathTest extends CardTestPlayerBase {

    @Test
    public void testCastFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, "Spring // Mind", 1);
        /*
        Aftermath (Cast this spell only from your graveyard. Then exile it.)
        Draw two cards.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Spring // Mind", 1);  // card is exiled after casting from graveyard
        assertHandCount(playerA, 2);  // two cards drawn
        
    }

    @Test
    public void test_CantCastFromHand() {
        // Spring {2}{G} - Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        // Mind {4}{U}{U} - Aftermath. Draw two cards.
        addCard(Zone.HAND, playerA, "Spring // Mind");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        checkPlayableAbility("first half", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Spring", true);
        checkPlayableAbility("aftermath half", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CantCastFromOpponentsGraveyard() {
        // Mind {4}{U}{U} - Aftermath. Draw two cards.
        addCard(Zone.GRAVEYARD, playerB, "Spring // Mind");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        // you may cast this half from your graveyard
        checkPlayableAbility("opponent's graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CastFromOpponentsGraveyard_WithPlayPermission() {
        // 702.127a ... "This half of this split card can't be cast from any zone other than a graveyard"

        // you may play cards from any graveyard
        addCustomCardWithAbility("play from any graveyard", playerA, new SimpleStaticAbility(
                new PlayFromNotOwnHandZoneAllEffect(
                        StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, TargetController.YOU, Duration.WhileOnBattlefield
                )
        ));
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        //
        // Mind {4}{U}{U} - Aftermath. Draw two cards.
        addCard(Zone.GRAVEYARD, playerB, "Spring // Mind");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertExileCount(playerB, "Spring // Mind", 1);
    }

    @Test
    public void test_CantCastFromExile_WithPlayPermission() {
        // you may play cards from exile
        addCustomCardWithAbility("play from exile", playerA, new SimpleStaticAbility(
                new PlayFromNotOwnHandZoneAllEffect(
                        StaticFilters.FILTER_CARD, Zone.EXILED, false, TargetController.YOU, Duration.WhileOnBattlefield
                )
        ));
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Spring {2}{G} - Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        // Mind {4}{U}{U} - Aftermath. Draw two cards.
        addCard(Zone.EXILED, playerA, "Spring // Mind");

        // can't be cast from any zone other than a graveyard
        checkPlayableAbility("first half", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Spring", true);
        checkPlayableAbility("aftermath half", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mind", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CastFirstHalfFromGraveyard_WithPlayPermission_NotExiled() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest");

        // you may play cards from any graveyard
        addCustomCardWithAbility("play from any graveyard", playerA, new SimpleStaticAbility(
                new PlayFromNotOwnHandZoneAllEffect(
                        StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, TargetController.YOU, Duration.WhileOnBattlefield
                )
        ));
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Spring {2}{G} - Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        // Mind {4}{U}{U} - Aftermath. Draw two cards.
        addCard(Zone.GRAVEYARD, playerA, "Spring // Mind");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spring");
        addTarget(playerA, "Forest"); // search basic land

        // only the aftermath half is exiled after a cast from a graveyard
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Forest", 3 + 1);
        assertGraveyardCount(playerA, "Spring // Mind", 1);
    }
}
