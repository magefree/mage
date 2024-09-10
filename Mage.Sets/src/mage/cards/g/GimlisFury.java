package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GimlisFury extends CardImpl {

    public GimlisFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+2 until end of turn. If it's legendary, it also gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 2));
        this.getSpellAbility().addEffect(new GimlisFuryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GimlisFury(final GimlisFury card) {
        super(card);
    }

    @Override
    public GimlisFury copy() {
        return new GimlisFury(this);
    }
}

class GimlisFuryEffect extends OneShotEffect {

    GimlisFuryEffect() {
        super(Outcome.Benefit);
        staticText = "If it's legendary, it also gains trample until end of turn";
    }

    private GimlisFuryEffect(final GimlisFuryEffect effect) {
        super(effect);
    }

    @Override
    public GimlisFuryEffect copy() {
        return new GimlisFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && permanent.isLegendary(game)) {
            game.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
            return true;
        }
        return false;
    }
}
