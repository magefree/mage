package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author North
 */
public final class FalkenrathAristocrat extends CardImpl {

    public FalkenrathAristocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // Sacrifice a creature: Falkenrath Aristocrat is indestructible this turn.
        // If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Aristocrat.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addEffect(new FalkenrathAristocratEffect());
        this.addAbility(ability);
    }

    private FalkenrathAristocrat(final FalkenrathAristocrat card) {
        super(card);
    }

    @Override
    public FalkenrathAristocrat copy() {
        return new FalkenrathAristocrat(this);
    }
}

class FalkenrathAristocratEffect extends OneShotEffect {

    public FalkenrathAristocratEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If the sacrificed creature was a Human, put a +1/+1 counter on {this}";
    }

    public FalkenrathAristocratEffect(final FalkenrathAristocratEffect effect) {
        super(effect);
    }

    @Override
    public FalkenrathAristocratEffect copy() {
        return new FalkenrathAristocratEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                Permanent sacrificedCreature = ((SacrificeTargetCost) cost).getPermanents().get(0);
                Permanent sourceCreature = game.getPermanent(source.getSourceId());
                if (sacrificedCreature.hasSubtype(SubType.HUMAN, game) && sourceCreature != null) {
                    sourceCreature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    break;
                }
            }
        }
        return true;
    }
}
