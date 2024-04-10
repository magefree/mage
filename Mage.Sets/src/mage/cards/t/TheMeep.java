package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMeep extends CardImpl {

    public TheMeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Ward--Pay 3 life.
        this.addAbility(new WardAbility(new PayLifeCost(3), false));

        // Whenever The Meep attacks, you may sacrifice another creature. If you do, creatures you control have base power and toughness X/X until end of turn, where X is the sacrificed creature's mana value.
        this.addAbility(new AttacksTriggeredAbility(new TheMeepEffect()));
    }

    private TheMeep(final TheMeep card) {
        super(card);
    }

    @Override
    public TheMeep copy() {
        return new TheMeep(this);
    }
}

class TheMeepEffect extends OneShotEffect {

    TheMeepEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. If you do, creatures you control have " +
                "base power and toughness X/X until end of turn, where X is the sacrificed creature's mana value";
    }

    private TheMeepEffect(final TheMeepEffect effect) {
        super(effect);
    }

    @Override
    public TheMeepEffect copy() {
        return new TheMeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !player.chooseUse(outcome, "Sacrifice another creature?", source, game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost
                .getPermanents()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        game.addEffect(new SetBasePowerToughnessAllEffect(
                xValue, xValue, Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), source);
        return true;
    }
}
