package mage.cards.m;

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
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Mise extends CardImpl {

    public Mise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose a nonland card name, then reveal the top card of your library. If that card has the chosen name, you draw three cards.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME));
        this.getSpellAbility().addEffect(new MiseEffect());
    }

    private Mise(final Mise card) {
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

    private MiseEffect(final MiseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (player != null && cardName != null) {
            Card card = player.getLibrary().getFromTop(game);
            CardsImpl cards = new CardsImpl(card);
            if (card != null) {
                player.revealCards("Mise", cards, game, true);
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    player.drawCards(3, source, game);
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
