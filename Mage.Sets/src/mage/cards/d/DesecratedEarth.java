
package mage.cards.d;

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
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class DesecratedEarth extends CardImpl {

    public DesecratedEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Destroy target land. Its controller discards a card.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DesecratedEarthEffect());

    }

    private DesecratedEarth(final DesecratedEarth card) {
        super(card);
    }

    @Override
    public DesecratedEarth copy() {
        return new DesecratedEarth(this);
    }
}

class DesecratedEarthEffect extends OneShotEffect {

    public DesecratedEarthEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Its controller discards a card";
    }

    private DesecratedEarthEffect(final DesecratedEarthEffect effect) {
        super(effect);
    }

    @Override
    public DesecratedEarthEffect copy() {
        return new DesecratedEarthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.discard(1, false, false, source, game);
                return true;
            }
        }
        return false;
    }
}
