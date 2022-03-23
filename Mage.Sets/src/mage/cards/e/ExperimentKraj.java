
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ExperimentKraj extends CardImpl {

    public ExperimentKraj(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Experiment Kraj has all activated abilities of each other creature with a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExperimentKrajEffect()));

        // {tap}: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()),new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ExperimentKraj(final ExperimentKraj card) {
        super(card);
    }

    @Override
    public ExperimentKraj copy() {
        return new ExperimentKraj(this);
    }
}

class ExperimentKrajEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ExperimentKrajEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of each other creature with a +1/+1 counter on it";
    }

    public ExperimentKrajEffect(final ExperimentKrajEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            for (Permanent creature :game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)){
                for (Ability ability: creature.getAbilities()) {
                    if (ability instanceof ActivatedAbility) {
                        perm.addAbility(ability, source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ExperimentKrajEffect copy() {
        return new ExperimentKrajEffect(this);
    }

}
