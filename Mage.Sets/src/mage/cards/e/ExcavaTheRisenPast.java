package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;

/**
 *
 * @author muz
 */
public final class ExcavaTheRisenPast extends CardImpl {

    private static final FilterCard filter =
        new FilterCard("artifact, creature, or non-Aura enchantment card with mana value 3 or less from your graveyard");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate(),
            Predicates.and(
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.not(SubType.AURA.getPredicate())
            )
        ));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public ExcavaTheRisenPast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Excava attacks, return up to one target artifact, creature, or non-Aura enchantment card with mana value 3 or less from your graveyard to the battlefield with a finality counter on it.
        // It's a 1/1 Spirit creature with flying in addition to its other types.
        Ability ability = new AttacksTriggeredAbility(new ExcavaTheRisenPastEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private ExcavaTheRisenPast(final ExcavaTheRisenPast card) {
        super(card);
    }

    @Override
    public ExcavaTheRisenPast copy() {
        return new ExcavaTheRisenPast(this);
    }
}
class ExcavaTheRisenPastEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {

    ExcavaTheRisenPastEffect() {
        super();
        staticText = "return target artifact, creature, or non-Aura enchantment card "
                + "with mana value 3 or less from your graveyard to the battlefield "
                + "with a finality counter on it. "
                + "It's a 1/1 Spirit creature with flying in addition to its other types";
    }

    private ExcavaTheRisenPastEffect(final ExcavaTheRisenPastEffect effect) {
        super(effect);
    }

    @Override
    public ExcavaTheRisenPastEffect copy() {
        return new ExcavaTheRisenPastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            ExcavaTheRisenPastContinuousEffect continuousEffect = new ExcavaTheRisenPastContinuousEffect();
            continuousEffect.setTargetPointer(new FixedTarget(targetId, game));
            game.addEffect(continuousEffect, source);
            game.setEnterWithCounters(targetId, new Counters().addCounter(CounterType.FINALITY.createInstance(1)));
        }
        return super.apply(game, source);
    }
}

class ExcavaTheRisenPastContinuousEffect extends ContinuousEffectImpl {

    ExcavaTheRisenPastContinuousEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "It's a 1/1 Spirit creature with flying in addition to its other types";
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    private ExcavaTheRisenPastContinuousEffect(final ExcavaTheRisenPastContinuousEffect effect) {
        super(effect);
    }

    @Override
    public ExcavaTheRisenPastContinuousEffect copy() {
        return new ExcavaTheRisenPastContinuousEffect(this);
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
