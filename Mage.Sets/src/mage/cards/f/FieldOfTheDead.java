package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieldOfTheDead extends CardImpl {

    public FieldOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Field of the Dead enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Whenever Field of the Dead or another land enters the battlefield under your control, if you control seven or more lands with different names, create a 2/2 black Zombie creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldThisOrAnotherTriggeredAbility(
                        new CreateTokenEffect(new ZombieToken()), StaticFilters.FILTER_LAND, false, true
                ), FieldOfTheDeadCondition.instance, "Whenever {this} or another land " +
                "enters the battlefield under your control, if you control seven or more lands with different names, " +
                "create a 2/2 black Zombie creature token."
        ));
    }

    private FieldOfTheDead(final FieldOfTheDead card) {
        super(card);
    }

    @Override
    public FieldOfTheDead copy() {
        return new FieldOfTheDead(this);
    }
}

enum FieldOfTheDeadCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return game
                .getBattlefield()
                .getAllActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)
                .stream()
                .map(permanent -> permanent.getName())
                .filter(s -> s.length() > 0)
                .distinct()
                .count() > 6;
    }
}