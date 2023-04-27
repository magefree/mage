
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class FatalFrenzy extends CardImpl {

    public FatalFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Until end of turn, target creature you control gains trample and gets +X/+0, where X is its power. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("Until end of turn, target creature you control gains trample")
        );
        this.getSpellAbility().addEffect(new BoostTargetEffect(TargetPermanentPowerCount.instance, StaticValue.get(0), Duration.EndOfTurn)
                .setText("and gets +X/+0, where X is its power")
        );
        this.getSpellAbility().addEffect(new FatalFrenzyEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

    }

    private FatalFrenzy(final FatalFrenzy card) {
        super(card);
    }

    @Override
    public FatalFrenzy copy() {
        return new FatalFrenzy(this);
    }
}

class FatalFrenzyEffect extends OneShotEffect {

    public FatalFrenzyEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice it at the beginning of the next end step";
    }

    public FatalFrenzyEffect(final FatalFrenzyEffect effect) {
        super(effect);
    }

    @Override
    public FatalFrenzyEffect copy() {
        return new FatalFrenzyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice this", source.getControllerId());
            sacrificeEffect.setTargetPointer(new FixedTarget(targetCreature, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }
}
