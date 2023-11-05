package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AbuelosAwakening extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("artifact or non-Aura enchantment card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                Predicates.and(
                        Predicates.not(SubType.AURA.getPredicate()),
                        CardType.ENCHANTMENT.getPredicate()
                )
        ));
    }

    public AbuelosAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{3}{W}");

        // Return target artifact or non-Aura enchantment card from your graveyard to the battlefield with X additional +1/+1 counters on it. It's a 1/1 Spirit creature with flying in addition to its other types.
        this.getSpellAbility().addEffect(new AbuelosAwakeningEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private AbuelosAwakening(final AbuelosAwakening card) {
        super(card);
    }

    @Override
    public AbuelosAwakening copy() {
        return new AbuelosAwakening(this);
    }
}

class AbuelosAwakeningEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {

    AbuelosAwakeningEffect() {
        super(false, false, false);
        staticText = "return target artifact or non-Aura enchantment card from your graveyard to the battlefield "
                + "with X additional +1/+1 counters on it. "
                + "It's a 1/1 Spirit creature with flying in addition to its other types";
    }

    private AbuelosAwakeningEffect(final AbuelosAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public AbuelosAwakeningEffect copy() {
        return new AbuelosAwakeningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int counterAmount = ManacostVariableValue.REGULAR.calculate(game, source, this);
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            AbuelosAwakeningContinuousEffect continuousEffect = new AbuelosAwakeningContinuousEffect();
            continuousEffect.setTargetPointer(new FixedTarget(targetId, game));
            game.addEffect(continuousEffect, source);
            if (counterAmount > 0) {
                game.setEnterWithCounters(targetId, new Counters().addCounter(CounterType.P1P1.createInstance(counterAmount)));
            }
        }
        return super.apply(game, source);
    }
}

class AbuelosAwakeningContinuousEffect extends ContinuousEffectImpl {

    public AbuelosAwakeningContinuousEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "It's a 1/1 Spirit creature with flying in addition to its other types";
    }

    private AbuelosAwakeningContinuousEffect(final AbuelosAwakeningContinuousEffect effect) {
        super(effect);
    }

    @Override
    public AbuelosAwakeningContinuousEffect copy() {
        return new AbuelosAwakeningContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature;
        if (source.getTargets().getFirstTarget() == null) {
            creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature == null) {
                creature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        if (creature == null) {
            this.used = true;
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                creature.addCardType(game, CardType.CREATURE);
                creature.addSubType(game, SubType.SPIRIT);
                break;
            case AbilityAddingRemovingEffects_6:
                creature.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    creature.getPower().setModifiedBaseValue(1);
                    creature.getToughness().setModifiedBaseValue(1);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 ||
                layer == Layer.AbilityAddingRemovingEffects_6 ||
                layer == Layer.PTChangingEffects_7;
    }
}
