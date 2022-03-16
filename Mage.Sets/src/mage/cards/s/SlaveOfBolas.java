package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SlaveOfBolas extends CardImpl {

    public SlaveOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U/R}{B}");

        // Gain control of target creature. Untap that creature. It gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));
        this.getSpellAbility().addEffect(new SlaveOfBolasEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SlaveOfBolas(final SlaveOfBolas card) {
        super(card);
    }

    @Override
    public SlaveOfBolas copy() {
        return new SlaveOfBolas(this);
    }
}

class SlaveOfBolasEffect extends OneShotEffect {

    public SlaveOfBolasEffect() {
        super(Outcome.Sacrifice);
        staticText = "Sacrifice it at the beginning of the next end step";
    }

    public SlaveOfBolasEffect(final SlaveOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public SlaveOfBolasEffect copy() {
        return new SlaveOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice this", source.getControllerId())
                        .setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
