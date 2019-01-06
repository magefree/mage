
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class Mise extends CardImpl {

    public Mise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");
        
        // Choose a nonland card name, then reveal the top card of your library. If that card has the chosen name, you draw three cards.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME));
        this.getSpellAbility().addEffect(new MiseEffect());
    }

    public Mise(final Mise card) {
        super(card);
    }

    @Override
    public Mise copy() {
        return new Mise(this);
    }
}

class MiseEffect extends OneShotEffect {

    public MiseEffect() {
        super(Outcome.Detriment);
        staticText = "then reveal the top card of your library. If that card has the chosen name, you draw three cards";
    }

    public MiseEffect(final MiseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Object object = game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (player != null && object instanceof String) {
            Card card = player.getLibrary().getFromTop(game);
            String namedCard = (String) object;
            CardsImpl cards = new CardsImpl(card);
            if (card != null) {
                player.revealCards("Mise", cards, game, true);
                if (card.getName().equals(namedCard)) {
                    player.drawCards(3, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MiseEffect copy() {
        return new MiseEffect(this);
    }
}
