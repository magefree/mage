
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author cbt33, Ad Nauseum (North), Izzet Staticaster (LevelX2), Bane Alley Broker (LevelX2)
 */
public final class TaintedPact extends CardImpl {

    public TaintedPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        // Exile the top card of your library. You may put that card into your hand unless it has the same name as another card exiled this way. Repeat this process until you put a card into your hand or you exile two cards with the same name, whichever comes first.
        this.getSpellAbility().addEffect(new TaintedPactEffect());
    }

    public TaintedPact(final TaintedPact card) {
        super(card);
    }

    @Override
    public TaintedPact copy() {
        return new TaintedPact(this);
    }
}

class TaintedPactEffect extends OneShotEffect{
    
        public TaintedPactEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Exile the top card of your library. You may put that card into your hand unless it has the same name as another card exiled this way. Repeat this process until you put a card into your hand or you exile two cards with the same name, whichever comes first";
    }

    public TaintedPactEffect(final TaintedPactEffect effect) {
        super(effect);
    }

    @Override
    public TaintedPactEffect copy() {
        return new TaintedPactEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) { 
        Card sourceCard = game.getCard(source.getSourceId()); 
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || sourceCard == null) {
            return false;
        }
        Set<String> names = new HashSet<>();
        while (player.canRespond() && player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {

                card.moveToExile(null, null, source.getSourceId(), game);
                // Checks if there was already exiled a card with the same name
                if (names.contains(card.getName())) {
                    break;
                }
                names.add(card.getName());
                if (player.chooseUse(outcome, "Put " + card.getName() + "into your hand?", source, game)) {
                     //Adds the current card to hand if it is chosen.
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                    break;
                }
            }
        }
        return true;
    }
}
