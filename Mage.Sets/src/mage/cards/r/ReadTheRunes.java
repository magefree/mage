
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class ReadTheRunes extends CardImpl {

    public ReadTheRunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");


        // Draw X cards. For each card drawn this way, discard a card unless you sacrifice a permanent.
        this.getSpellAbility().addEffect(new ReadTheRunesEffect());
    }

    private ReadTheRunes(final ReadTheRunes card) {
        super(card);
    }

    @Override
    public ReadTheRunes copy() {
        return new ReadTheRunes(this);
    }
}

class ReadTheRunesEffect extends OneShotEffect {
    
    ReadTheRunesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw X cards. For each card drawn this way, discard a card unless you sacrifice a permanent.";
    }
    
    private ReadTheRunesEffect(final ReadTheRunesEffect effect) {
        super(effect);
    }
    
    @Override
    public ReadTheRunesEffect copy() {
        return new ReadTheRunesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int drawnCards = controller.drawCards(source.getManaCostsToPay().getX(), source, game);
            Target target = new TargetControlledPermanent(0, drawnCards, new FilterControlledPermanent(), true);
            controller.chooseTarget(Outcome.Sacrifice, target, source, game);
            int sacrificedPermanents = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.sacrifice(source, game)) {
                        sacrificedPermanents++;
                    }
                }
            }
            controller.discard(drawnCards - sacrificedPermanents, false, false, source, game);
            return true;
        }
        return false;
    }
}
