package mage.cards.d;

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
 * @author Susucr
 */
public final class Depressurize extends CardImpl {

    public Depressurize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -3/-0 until end of turn. Then if that creature's power is 0 or less, destroy it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DepressurizeTargetEffect());
    }

    private Depressurize(final Depressurize card) {
        super(card);
    }

    @Override
    public Depressurize copy() {
        return new Depressurize(this);
    }
}

class DepressurizeTargetEffect extends OneShotEffect {

    DepressurizeTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Then if that creature's power is 0 or less, destroy it";
    }

    private DepressurizeTargetEffect(final DepressurizeTargetEffect effect) {
        super(effect);
    }

    @Override
    public DepressurizeTargetEffect copy() {
        return new DepressurizeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.getPower().getValue() > 0) {
            return false;
        }

        permanent.destroy(source, game);
        return true;
    }
}