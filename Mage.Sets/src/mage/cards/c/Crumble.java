package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author fireshoes
 */
public final class Crumble extends CardImpl {

    public Crumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Destroy target artifact. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));

        // That artifact's controller gains life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new CrumbleEffect());
    }

    private Crumble(final Crumble card) {
        super(card);
    }

    @Override
    public Crumble copy() {
        return new Crumble(this);
    }
}

class CrumbleEffect extends OneShotEffect {

    public CrumbleEffect() {
        super(Outcome.GainLife);
        staticText = "That artifact's controller gains life equal to its mana value";
    }

    private CrumbleEffect(final CrumbleEffect effect) {
        super(effect);
    }

    @Override
    public CrumbleEffect copy() {
        return new CrumbleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source); // must use LKI
        if (permanent != null) {
            int cost = permanent.getManaValue();
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.gainLife(cost, game, source);
            }
            return true;
        }
        return false;
    }
}
