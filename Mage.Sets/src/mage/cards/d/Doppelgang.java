package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Doppelgang extends CardImpl {

    public Doppelgang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{X}{G}{U}");

        // For each of X target permanents, create X tokens that are copies of that permanent.
        this.getSpellAbility().addEffect(new DoppelgangEffect());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Doppelgang(final Doppelgang card) {
        super(card);
    }

    @Override
    public Doppelgang copy() {
        return new Doppelgang(this);
    }
}

class DoppelgangEffect extends OneShotEffect {

    DoppelgangEffect() {
        super(Outcome.Benefit);
        staticText = "for each of X target permanents, create X tokens that are copies of that permanent";
    }

    private DoppelgangEffect(final DoppelgangEffect effect) {
        super(effect);
    }

    @Override
    public DoppelgangEffect copy() {
        return new DoppelgangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        if (xValue < 1) {
            return false;
        }
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                new CreateTokenCopyTargetEffect(
                        null, null, false, xValue
                ).setSavedPermanent(permanent).apply(game, source);
            }
        }
        return true;
    }
}
