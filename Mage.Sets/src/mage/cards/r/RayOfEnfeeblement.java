package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RayOfEnfeeblement extends CardImpl {

    public RayOfEnfeeblement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -4/-1 until end of turn. If that creature is white, it gets -4/-4 until end of turn instead.
        this.getSpellAbility().addEffect(new RayOfEnfeeblementEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RayOfEnfeeblement(final RayOfEnfeeblement card) {
        super(card);
    }

    @Override
    public RayOfEnfeeblement copy() {
        return new RayOfEnfeeblement(this);
    }
}

class RayOfEnfeeblementEffect extends OneShotEffect {

    RayOfEnfeeblementEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gets -4/-1 until end of turn. " +
                "If that creature is white, it gets -4/-4 until end of turn instead";
    }

    private RayOfEnfeeblementEffect(final RayOfEnfeeblementEffect effect) {
        super(effect);
    }

    @Override
    public RayOfEnfeeblementEffect copy() {
        return new RayOfEnfeeblementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                -4, permanent.getColor(game).isWhite() ? -4 : -1, Duration.EndOfTurn
        ), source);
        return true;
    }
}
