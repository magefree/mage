
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author BetaSteward
 */
public final class EndlessRanksOfTheDead extends CardImpl {

    public EndlessRanksOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // At the beginning of your upkeep, create X 2/2 black Zombie creature tokens,
        // where X is half the number of Zombies you control, rounded down.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep",
                new CreateTokenEffect(new ZombieToken(), new HalfZombiesCount())));

    }

    private EndlessRanksOfTheDead(final EndlessRanksOfTheDead card) {
        super(card);
    }

    @Override
    public EndlessRanksOfTheDead copy() {
        return new EndlessRanksOfTheDead(this);
    }
}

class HalfZombiesCount implements DynamicValue {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) / 2;
        return amount;
    }

    @Override
    public HalfZombiesCount copy() {
        return new HalfZombiesCount();
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
