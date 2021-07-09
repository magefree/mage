package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProvokeTheTrolls extends CardImpl {

    public ProvokeTheTrolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Provoke the Trolls deals 3 damage to any target. If a creature is dealt damage this way, it gets +5/+0 until end of turn.
        this.getSpellAbility().addEffect(new ProvokeTheTrollsEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ProvokeTheTrolls(final ProvokeTheTrolls card) {
        super(card);
    }

    @Override
    public ProvokeTheTrolls copy() {
        return new ProvokeTheTrolls(this);
    }
}

class ProvokeTheTrollsEffect extends OneShotEffect {

    ProvokeTheTrollsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to any target. " +
                "If a creature is dealt damage this way, it gets +5/+0 until end of turn";
    }

    private ProvokeTheTrollsEffect(final ProvokeTheTrollsEffect effect) {
        super(effect);
    }

    @Override
    public ProvokeTheTrollsEffect copy() {
        return new ProvokeTheTrollsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            return player.damage(3, source.getSourceId(), source, game) > 0;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || permanent.damage(3, source.getSourceId(), source, game) < 1) {
            return false;
        }
        if (permanent.isCreature(game)) {
            game.addEffect(new BoostTargetEffect(3, 0), source);
        }
        return true;
    }
}
