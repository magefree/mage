package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonBasicLandPermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class LavaBlister extends CardImpl {

    public LavaBlister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Destroy target nonbasic land unless its controller has Lava Blister deal 6 damage to them.
        this.getSpellAbility().addTarget(new TargetNonBasicLandPermanent());
        this.getSpellAbility().addEffect(new LavaBlisterEffect());
    }

    private LavaBlister(final LavaBlister card) {
        super(card);
    }

    @Override
    public LavaBlister copy() {
        return new LavaBlister(this);
    }
}

class LavaBlisterEffect extends OneShotEffect {

    public LavaBlisterEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy target nonbasic land unless its controller has {this} deal 6 damage to them";
    }

    private LavaBlisterEffect(final LavaBlisterEffect effect) {
        super(effect);
    }

    @Override
    public LavaBlisterEffect copy() {
        return new LavaBlisterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                String message = "Have Lava Blister do 6 damage to you?";
                if (player.chooseUse(Outcome.Damage, message, source, game)) {
                    player.damage(6, source.getSourceId(), source, game);
                } else {
                    permanent.destroy(source, game, false);
                }
                return true;
            }
        }
        return false;
    }
}
