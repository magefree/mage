package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SonicAssault extends CardImpl {

    public SonicAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Tap target creature. Sonic Assault deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new SonicAssaultEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private SonicAssault(final SonicAssault card) {
        super(card);
    }

    @Override
    public SonicAssault copy() {
        return new SonicAssault(this);
    }
}

class SonicAssaultEffect extends OneShotEffect {

    public SonicAssaultEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap target creature. "
                + "{this} deals 2 damage to that creature's controller.";
    }

    private SonicAssaultEffect(final SonicAssaultEffect effect) {
        super(effect);
    }

    @Override
    public SonicAssaultEffect copy() {
        return new SonicAssaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        creature.tap(source, game);
        Player player = game.getPlayer(creature.getControllerId());
        if (player == null) {
            return false;
        }
        player.damage(2, source.getSourceId(), source, game);
        return true;
    }
}
