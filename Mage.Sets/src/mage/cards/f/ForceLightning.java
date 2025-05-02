
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class ForceLightning extends CardImpl {

    public ForceLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Force Lightning deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Scry X.
        this.getSpellAbility().addEffect(new ForceLightningEffect());

    }

    private ForceLightning(final ForceLightning card) {
        super(card);
    }

    @Override
    public ForceLightning copy() {
        return new ForceLightning(this);
    }
}

class ForceLightningEffect extends OneShotEffect {

    ForceLightningEffect() {
        super(Outcome.Benefit);
        this.staticText = "Scry X";
    }

    private ForceLightningEffect(final ForceLightningEffect effect) {
        super(effect);
    }

    @Override
    public ForceLightningEffect copy() {
        return new ForceLightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int x = CardUtil.getSourceCostsTag(game, source, "X", 0);
            if (x > 0) {
                return controller.scry(x, source, game);
            }
            return true;
        }
        return false;
    }
}
