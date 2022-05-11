
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class LifecraftAwakening extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LifecraftAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Put X +1/+1 counters on target artifact you control. If it isn't a creature or Vehicle, it becomes a 0/0 Construct artifact creature.
        getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), ManacostVariableValue.REGULAR
        ).setText("put X +1/+1 counters on target artifact you control"));
        getSpellAbility().addTarget(new TargetArtifactPermanent(filter));
        getSpellAbility().addEffect(new LifecraftAwakeningEffect());
    }

    private LifecraftAwakening(final LifecraftAwakening card) {
        super(card);
    }

    @Override
    public LifecraftAwakening copy() {
        return new LifecraftAwakening(this);
    }
}

class LifecraftAwakeningEffect extends OneShotEffect {

    public LifecraftAwakeningEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "If it isn't a creature or Vehicle, it becomes a 0/0 Construct artifact creature";
    }

    public LifecraftAwakeningEffect(final LifecraftAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public LifecraftAwakeningEffect copy() {
        return new LifecraftAwakeningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (!permanent.isCreature(game) && !permanent.hasSubtype(SubType.VEHICLE, game)) {
            ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new LifecraftAwakeningToken(), false, true, Duration.Custom);
            continuousEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(continuousEffect, source);
            return true;
        }
        return false;
    }
}

class LifecraftAwakeningToken extends TokenImpl {

    LifecraftAwakeningToken() {
        super("", "0/0 Construct artifact creature");
        this.cardType.add(CardType.ARTIFACT);
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
    }
    public LifecraftAwakeningToken(final LifecraftAwakeningToken token) {
        super(token);
    }

    public LifecraftAwakeningToken copy() {
        return new LifecraftAwakeningToken(this);
    }
}
