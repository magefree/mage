package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.GainLifeAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MathasFiendSeeker extends CardImpl {

    public MathasFiendSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, put a bounty counter on target creature an opponent controls. For as long as that creature has a bounty counter on it, it has "When this creature dies, each opponent draws a card and gains 2 life."
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.BOUNTY.createInstance())
        );
        ability.addEffect(new MathasFiendSeekerEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private MathasFiendSeeker(final MathasFiendSeeker card) {
        super(card);
    }

    @Override
    public MathasFiendSeeker copy() {
        return new MathasFiendSeeker(this);
    }
}

class MathasFiendSeekerEffect extends ContinuousEffectImpl {

    private final Ability ability;

    MathasFiendSeekerEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "For as long as that creature has a bounty counter on it, " +
                "it has \"When this creature dies, each opponent draws a card and gains 2 life.\"";
        this.ability = new DiesSourceTriggeredAbility(new DrawCardAllEffect(1, TargetController.OPPONENT));
        this.ability.addEffect(new GainLifeAllEffect(2, TargetController.OPPONENT).setText("and gains 2 life"));
    }

    private MathasFiendSeekerEffect(final MathasFiendSeekerEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public MathasFiendSeekerEffect copy() {
        return new MathasFiendSeekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature == null || !creature.getCounters(game).containsKey(CounterType.BOUNTY)) {
            discard();
            return false;
        }
        creature.addAbility(ability, source.getSourceId(), game);
        return true;
    }
}
