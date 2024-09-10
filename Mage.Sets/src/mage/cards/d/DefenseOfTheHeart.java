package mage.cards.d;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class DefenseOfTheHeart extends CardImpl {

    public DefenseOfTheHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice Defense of the Heart, search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_CREATURE), false));
        DefenseOfTheHeartCondition contition = new DefenseOfTheHeartCondition();
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, contition, "At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice {this}, search your library for up to two creature cards, put those cards onto the battlefield, then shuffle"));

    }

    private DefenseOfTheHeart(final DefenseOfTheHeart card) {
        super(card);
    }

    @Override
    public DefenseOfTheHeart copy() {
        return new DefenseOfTheHeart(this);
    }

    static class DefenseOfTheHeartCondition implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for (UUID uuid : opponents) {
                if (game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, uuid, game) >= 3) {
                    return true;
                }
            }
            return false;
        }
    }
}
