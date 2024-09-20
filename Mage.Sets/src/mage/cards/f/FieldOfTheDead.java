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
import mage.util.CardUtil;

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

        // Whenever Field of the Dead or another land you control enters, if you control seven or more lands with different names, create a 2/2 black Zombie creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldThisOrAnotherTriggeredAbility(
                        new CreateTokenEffect(new ZombieToken()), StaticFilters.FILTER_LAND, false, true
                ), FieldOfTheDeadCondition.instance, "Whenever {this} or another land " +
                "you control enters, if you control seven or more lands with different names, " +
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
        return CardUtil.differentlyNamedAmongCollection(
                game.getBattlefield().getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                        source.getControllerId(), source, game
                ), game
        ) >= 7;
    }
}
