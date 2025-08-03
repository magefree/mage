package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Plopman
 */
public final class DefenseOfTheHeart extends CardImpl {

    public DefenseOfTheHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice Defense of the Heart, search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect())
                .withInterveningIf(DefenseOfTheHeartCondition.instance);
        ability.addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 2, StaticFilters.FILTER_CARD_CREATURES
        )).concatBy(","));
        this.addAbility(ability);
    }

    private DefenseOfTheHeart(final DefenseOfTheHeart card) {
        super(card);
    }

    @Override
    public DefenseOfTheHeart copy() {
        return new DefenseOfTheHeart(this);
    }
}

enum DefenseOfTheHeartCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum))
                .values()
                .stream()
                .anyMatch(x -> x >= 3);
    }

    @Override
    public String toString() {
        return "an opponent controls three or more creatures";
    }
}
