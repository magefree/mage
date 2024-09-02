package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
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
public final class UnwantedRemake extends CardImpl {

    public UnwantedRemake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Destroy target creature. Its controller manifests dread.
        this.getSpellAbility().addEffect(new UnwantedRemakeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnwantedRemake(final UnwantedRemake card) {
        super(card);
    }

    @Override
    public UnwantedRemake copy() {
        return new UnwantedRemake(this);
    }
}

class UnwantedRemakeEffect extends OneShotEffect {

    UnwantedRemakeEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target creature. Its controller manifests dread";
    }

    private UnwantedRemakeEffect(final UnwantedRemakeEffect effect) {
        super(effect);
    }

    @Override
    public UnwantedRemakeEffect copy() {
        return new UnwantedRemakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game);
        if (player != null) {
            ManifestDreadEffect.doManifestDread(player, source, game);
        }
        return true;
    }
}
