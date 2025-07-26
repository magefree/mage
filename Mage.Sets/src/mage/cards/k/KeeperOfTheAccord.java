package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeeperOfTheAccord extends CardImpl {

    public KeeperOfTheAccord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of each opponent's end step, if that player controls more creatures than you, create a 1/1 white Soldier creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.OPPONENT, new CreateTokenEffect(new SoldierToken()), false
        ).withInterveningIf(KeeperOfTheAccordCondition.CREATURES));

        // At the beginning of each opponent's end step, if that player controls more lands than you, you may search your library for a basic Plains card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.OPPONENT, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true), true
        ).withInterveningIf(KeeperOfTheAccordCondition.LANDS));
    }

    private KeeperOfTheAccord(final KeeperOfTheAccord card) {
        super(card);
    }

    @Override
    public KeeperOfTheAccord copy() {
        return new KeeperOfTheAccord(this);
    }
}

enum KeeperOfTheAccordCondition implements Condition {

    CREATURES(StaticFilters.FILTER_PERMANENT_CREATURES),
    LANDS(StaticFilters.FILTER_LANDS);

    private final FilterPermanent filter;

    KeeperOfTheAccordCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().countAll(filter, source.getControllerId(), game)
                < game.getBattlefield().countAll(filter, game.getActivePlayerId(), game);
    }

    @Override
    public String toString() {
        return "that player controls more " + filter.getMessage() + " than you";
    }
}
