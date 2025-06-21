package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class TezzeretCruelCaptainEmblem extends Emblem {

    // -7: You get an emblem with "At the beginning of combat on your turn, put three +1/+1 counters on target artifact you control. If it's not a creature, it becomes a 0/0 Robot artifact creature."
    public TezzeretCruelCaptainEmblem() {
        super("Emblem Tezzeret");
        Ability ability = new BeginningOfCombatTriggeredAbility(
                Zone.COMMAND, TargetController.YOU,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)), false
        );
        ability.addEffect(new TezzeretCruelCaptainEmblemEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.getAbilities().add(ability);
    }

    private TezzeretCruelCaptainEmblem(final TezzeretCruelCaptainEmblem card) {
        super(card);
    }

    @Override
    public TezzeretCruelCaptainEmblem copy() {
        return new TezzeretCruelCaptainEmblem(this);
    }
}

class TezzeretCruelCaptainEmblemEffect extends OneShotEffect {

    TezzeretCruelCaptainEmblemEffect() {
        super(Outcome.Benefit);
        staticText = "If it's not a creature, it becomes a 0/0 Robot artifact creature";
    }

    private TezzeretCruelCaptainEmblemEffect(final TezzeretCruelCaptainEmblemEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretCruelCaptainEmblemEffect copy() {
        return new TezzeretCruelCaptainEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.isCreature(game)) {
            return false;
        }
        game.addEffect(new AddCardTypeTargetEffect(
                Duration.Custom, CardType.ARTIFACT, CardType.CREATURE
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new AddCardSubTypeTargetEffect(
                SubType.ROBOT, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new SetBasePowerToughnessTargetEffect(
                0, 0, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
