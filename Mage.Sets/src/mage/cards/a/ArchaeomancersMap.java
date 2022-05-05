package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchaeomancersMap extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic Plains cards");
    private static final FilterPermanent filter2 = new FilterLandPermanent();

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter.add(SuperType.BASIC.getPredicate());
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ArchaeomancersMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // When Archaeomancer's Map enters the battlefield, search your library for up to two basic Plains cards, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(2, filter), true)
        ));

        // Whenever a land enters the battlefield under an opponent's control, if that player controls more lands than you, you may put a land card from your hand onto the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldAllTriggeredAbility(
                        new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A), filter2
                ), ArchaeomancersMapCondition.instance, "Whenever a land enters the battlefield " +
                "under an opponent's control, if that player controls more lands than you, " +
                "you may put a land card from your hand onto the battlefield."
        ));
    }

    private ArchaeomancersMap(final ArchaeomancersMap card) {
        super(card);
    }

    @Override
    public ArchaeomancersMap copy() {
        return new ArchaeomancersMap(this);
    }
}

enum ArchaeomancersMapCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) source.getEffects().get(0).getValue("permanentEnteringControllerId");
        return playerId != null && game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                playerId, source, game
        ) > game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        );
    }
}
