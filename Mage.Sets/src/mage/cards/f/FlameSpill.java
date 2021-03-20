package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
public final class FlameSpill extends CardImpl {

    public FlameSpill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Flame Spill deals 4 damage to target creature. Excess damage is dealt to that creature's controller instead.
        this.getSpellAbility().addEffect(new FlameSpillEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FlameSpill(final FlameSpill card) {
        super(card);
    }

    @Override
    public FlameSpill copy() {
        return new FlameSpill(this);
    }
}

class FlameSpillEffect extends OneShotEffect {

    FlameSpillEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target creature. " +
                "Excess damage is dealt to that creature's controller instead.";
    }

    private FlameSpillEffect(final FlameSpillEffect effect) {
        super(effect);
    }

    @Override
    public FlameSpillEffect copy() {
        return new FlameSpillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent == null || sourceObject == null) {
            return false;
        }
        int lethal = permanent.getLethalDamage(source.getSourceId(), game);
        lethal = Math.min(lethal, 4);
        permanent.damage(lethal, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null && lethal < 4) {
            player.damage(4 - lethal, source.getSourceId(), source, game);
        }
        return true;
    }
}