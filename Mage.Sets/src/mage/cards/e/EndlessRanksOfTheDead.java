package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class EndlessRanksOfTheDead extends CardImpl {

    public EndlessRanksOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of your upkeep, create X 2/2 black Zombie creature tokens,
        // where X is half the number of Zombies you control, rounded down.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new ZombieToken(), HalfZombiesCount.instance)
        ));

    }

    private EndlessRanksOfTheDead(final EndlessRanksOfTheDead card) {
        super(card);
    }

    @Override
    public EndlessRanksOfTheDead copy() {
        return new EndlessRanksOfTheDead(this);
    }
}

enum HalfZombiesCount implements DynamicValue {
    instance;

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game) / 2;
    }

    @Override
    public HalfZombiesCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "half the number of Zombies you control, rounded down";
    }
}
