
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SimicFluxmage extends CardImpl {

    public SimicFluxmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());

        // 1{U}, {T}: Move a +1/+1 counter from Simic Fluxmage onto target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveCounterFromSourceToTargetEffect(),new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private SimicFluxmage(final SimicFluxmage card) {
        super(card);
    }

    @Override
    public SimicFluxmage copy() {
        return new SimicFluxmage(this);
    }
}

class MoveCounterFromSourceToTargetEffect extends OneShotEffect {

    public MoveCounterFromSourceToTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Move a +1/+1 counter from {this} onto target creature";
    }

    public MoveCounterFromSourceToTargetEffect(final MoveCounterFromSourceToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromSourceToTargetEffect copy() {
        return new MoveCounterFromSourceToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (targetPermanent != null) {
                sourcePermanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
                targetPermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                return true;
            }
        }
        return false;
    }
}
