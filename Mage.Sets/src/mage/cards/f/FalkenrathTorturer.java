
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class FalkenrathTorturer extends CardImpl {

    public FalkenrathTorturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice a creature: Falkenrath Torturer gains flying until end of turn.
        // If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Torturer.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addEffect(new FalkenrathAristocratEffect());
        this.addAbility(ability);
    }

    private FalkenrathTorturer(final FalkenrathTorturer card) {
        super(card);
    }

    @Override
    public FalkenrathTorturer copy() {
        return new FalkenrathTorturer(this);
    }
}

class FalkenrathTorturerEffect extends OneShotEffect {

    public FalkenrathTorturerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "If the sacrificed creature was a Human, put a +1/+1 counter on {this}";
    }

    public FalkenrathTorturerEffect(final FalkenrathTorturerEffect effect) {
        super(effect);
    }

    @Override
    public FalkenrathTorturerEffect copy() {
        return new FalkenrathTorturerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                Permanent sacrificedCreature = ((SacrificeTargetCost) cost).getPermanents().get(0);
                Permanent sourceCreature = game.getPermanent(source.getSourceId());
                if (sacrificedCreature.hasSubtype(SubType.HUMAN, game) && sourceCreature != null) {
                    sourceCreature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
