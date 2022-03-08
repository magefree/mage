
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class Scapeshift extends CardImpl {

    public Scapeshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");

        // Sacrifice any number of lands. Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ScapeshiftEffect());
    }

    private Scapeshift(final Scapeshift card) {
        super(card);
    }

    @Override
    public Scapeshift copy() {
        return new Scapeshift(this);
    }
}

class ScapeshiftEffect extends OneShotEffect {

    public ScapeshiftEffect() {
        super(Outcome.Neutral);
        staticText = "Sacrifice any number of lands. Search your library for up to that many land cards, put them onto the battlefield tapped, then shuffle";
    }

    public ScapeshiftEffect(final ScapeshiftEffect effect) {
        super(effect);
    }

    @Override
    public ScapeshiftEffect copy() {
        return new ScapeshiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int amount = 0;
        TargetControlledPermanent sacrificeLand = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterControlledLandPermanent("lands you control"), true);
        if (controller.chooseTarget(Outcome.Sacrifice, sacrificeLand, source, game)) {
            for (UUID uuid : sacrificeLand.getTargets()) {
                Permanent land = game.getPermanent(uuid);
                if (land != null) {
                    land.sacrifice(source, game);
                    amount++;
                }
            }
        }
        TargetCardInLibrary target = new TargetCardInLibrary(amount, new FilterLandCard("lands"));
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game, true, false, false, null);
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }

}
