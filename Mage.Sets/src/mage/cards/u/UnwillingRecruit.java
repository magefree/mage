package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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

/**
 *
 * @author jeffwadsworth
 */
public final class UnwillingRecruit extends CardImpl {

    public UnwillingRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gets +X/+0 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new UnwillingRecruitEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private UnwillingRecruit(final UnwillingRecruit card) {
        super(card);
    }

    @Override
    public UnwillingRecruit copy() {
        return new UnwillingRecruit(this);
    }
}

class UnwillingRecruitEffect extends OneShotEffect {

    UnwillingRecruitEffect() {
        super(Outcome.Benefit);
        staticText = "Gain control of target creature until end of turn. Untap that creature. It gets +X/+0 and gains haste until end of turn";
    }

    UnwillingRecruitEffect(UnwillingRecruitEffect effect) {
        super(effect);
    }

    @Override
    public UnwillingRecruitEffect copy() {
        return new UnwillingRecruitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            source.getEffects().get(0).setTargetPointer(new FixedTarget(targetCreature.getId(), game));
            game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn), source);
            targetCreature.untap(game);
            game.addEffect(new BoostTargetEffect(source.getManaCostsToPay().getX(), 0, Duration.EndOfTurn), source);
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
