package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StandUnited extends CardImpl {

    public StandUnited(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G/W}");

        // Target creature gets +2/+2 until end of turn. If it's an Ally, scry 2.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new StandUnitedEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StandUnited(final StandUnited card) {
        super(card);
    }

    @Override
    public StandUnited copy() {
        return new StandUnited(this);
    }
}

class StandUnitedEffect extends OneShotEffect {

    StandUnitedEffect() {
        super(Outcome.Benefit);
        staticText = "If it's an Ally, scry 2";
    }

    private StandUnitedEffect(final StandUnitedEffect effect) {
        super(effect);
    }

    @Override
    public StandUnitedEffect copy() {
        return new StandUnitedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null
                && permanent.hasSubtype(SubType.ALLY, game)
                && player.scry(2, source, game);
    }
}
