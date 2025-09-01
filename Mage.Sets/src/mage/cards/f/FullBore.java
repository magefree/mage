package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FullBore extends CardImpl {

    public FullBore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature you control gets +3/+2 until end of turn. If that creature was cast for its warp cost, it also gains trample and haste until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 2));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new FullBoreEffect());
    }

    private FullBore(final FullBore card) {
        super(card);
    }

    @Override
    public FullBore copy() {
        return new FullBore(this);
    }
}

class FullBoreEffect extends OneShotEffect {

    FullBoreEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature was cast for its warp cost, it also gains trample and haste until end of turn";
    }

    private FullBoreEffect(final FullBoreEffect effect) {
        super(effect);
    }

    @Override
    public FullBoreEffect copy() {
        return new FullBoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !WarpAbility.checkIfPermanentWarped(permanent, game)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
