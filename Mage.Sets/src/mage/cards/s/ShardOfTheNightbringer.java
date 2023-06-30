package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShardOfTheNightbringer extends CardImpl {

    public ShardOfTheNightbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");

        this.subtype.add(SubType.CTAN);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Drain Life -- When Shard of the Nightbringer enters the battlefield, if you cast it, target opponent loses half their life, rounded up. You gain life equal to the life lost this way.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ShardOfTheNightbringerEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, target opponent loses half their life, rounded up. " +
                "You gain life equal to the life lost this way."
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.withFlavorWord("Drain Life"));
    }

    private ShardOfTheNightbringer(final ShardOfTheNightbringer card) {
        super(card);
    }

    @Override
    public ShardOfTheNightbringer copy() {
        return new ShardOfTheNightbringer(this);
    }
}

class ShardOfTheNightbringerEffect extends OneShotEffect {

    ShardOfTheNightbringerEffect() {
        super(Outcome.Benefit);
    }

    private ShardOfTheNightbringerEffect(final ShardOfTheNightbringerEffect effect) {
        super(effect);
    }

    @Override
    public ShardOfTheNightbringerEffect copy() {
        return new ShardOfTheNightbringerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        int lifeLost = opponent.loseLife(
                opponent.getLife() / 2 + opponent.getLife() % 2, game, source, false
        );
        if (lifeLost < 1) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        return controller == null || controller.gainLife(lifeLost, game, source) > 0;
    }
}
