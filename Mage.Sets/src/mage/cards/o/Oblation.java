
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author emerald000
 */
public final class Oblation extends CardImpl {

    public Oblation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // The owner of target nonland permanent shuffles it into their library, then draws two cards.
        this.getSpellAbility().addEffect(new OblationEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private Oblation(final Oblation card) {
        super(card);
    }

    @Override
    public Oblation copy() {
        return new Oblation(this);
    }
}

class OblationEffect extends OneShotEffect {

    OblationEffect() {
        super(Outcome.Removal);
        this.staticText = "The owner of target nonland permanent shuffles it into their library, then draws two cards";
    }

    private OblationEffect(final OblationEffect effect) {
        super(effect);
    }

    @Override
    public OblationEffect copy() {
        return new OblationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player != null) {
                player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, true);
                player.shuffleLibrary(source, game);

                game.getState().processAction(game); // so effects from creatures that were on the battlefield won't trigger from draw 

                player.drawCards(2, source, game);
                return true;
            }
        }
        return false;
    }
}
