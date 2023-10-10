package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LightningStorm extends CardImpl {

    public LightningStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Lightning Storm deals X damage to any target, where X is 3 plus the number of charge counters on it.
        Effect effect = new DamageTargetEffect(new LightningStormCountCondition(CounterType.CHARGE));
        effect.setText("{this} deals X damage to any target, where X is 3 plus the number of charge counters on it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Discard a land card: Put two charge counters on Lightning Storm. You may choose a new target for it. Any player may activate this ability but only if Lightning Storm is on the stack.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.STACK,
                new LightningStormAddCounterEffect(),
                new DiscardTargetCost(new TargetCardInHand(new FilterLandCard("a land card"))));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect(" Any player may activate this ability but only if {this} is on the stack"));
        this.addAbility(ability);
    }

    private LightningStorm(final LightningStorm card) {
        super(card);
    }

    @Override
    public LightningStorm copy() {
        return new LightningStorm(this);
    }
}

class LightningStormCountCondition implements DynamicValue {

    private final CounterType counter;

    LightningStormCountCondition(CounterType counter) {
        this.counter = counter;
    }

    private LightningStormCountCondition(final LightningStormCountCondition countersCount) {
        this.counter = countersCount.counter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = game.getStack().getSpell(sourceAbility.getSourceId());
        if (spell != null) {
            return spell.getCounters(game).getCount(counter) + 3;
        }
        return 0;
    }

    @Override
    public LightningStormCountCondition copy() {
        return new LightningStormCountCondition(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "3 plus the number of charge counters on it";
    }
}

class LightningStormAddCounterEffect extends OneShotEffect {

    public LightningStormAddCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put two charge counters on {this}. You may choose a new target for it.";
    }

    private LightningStormAddCounterEffect(final LightningStormAddCounterEffect effect) {
        super(effect);
    }

    @Override
    public LightningStormAddCounterEffect copy() {
        return new LightningStormAddCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell != null) {
            spell.addCounters(CounterType.CHARGE.createInstance(2), source.getControllerId(), source, game);
            spell.chooseNewTargets(game, ((ActivatedAbilityImpl) source).getActivatorId(), false, false, null);
            return true;
        }
        return false;
    }
}
