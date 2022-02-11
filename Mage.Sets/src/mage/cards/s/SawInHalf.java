package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SawInHalf extends CardImpl {

    public SawInHalf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Destroy target creature. If that creature dies this way, its controller creates two tokens that are copies of that creature, except their base power is half that creature's power and their base toughness is half that creature's toughness. Round up each time.
        this.getSpellAbility().addEffect(new SawInHalfEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SawInHalf(final SawInHalf card) {
        super(card);
    }

    @Override
    public SawInHalf copy() {
        return new SawInHalf(this);
    }
}

class SawInHalfEffect extends OneShotEffect {

    SawInHalfEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target creature. If that creature dies this way, its controller creates " +
                "two tokens that are copies of that creature, except their base power is half that creature's " +
                "power and their base toughness is half that creature's toughness. Round up each time";
    }

    private SawInHalfEffect(final SawInHalfEffect effect) {
        super(effect);
    }

    @Override
    public SawInHalfEffect copy() {
        return new SawInHalfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null
                || !permanent.destroy(source, game)
                || game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        return new CreateTokenCopyTargetEffect(
                permanent.getControllerId(), null, false, 2, false, false, null,
                divide(permanent.getPower()), divide(permanent.getToughness()), false
        ).setSavedPermanent(permanent).apply(game, source);
    }

    private static final int divide(MageInt mageInt) {
        return Math.floorDiv(mageInt.getValue(), 2) + mageInt.getValue() % 2;
    }
}
