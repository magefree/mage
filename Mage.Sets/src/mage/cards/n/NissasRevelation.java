
package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class NissasRevelation extends CardImpl {

    public NissasRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{G}{G}");

        // Scry 5, then reveal the top card of your library. If it's a creature card, you draw cards equal to its power and you gain life equal to its toughness.
        this.getSpellAbility().addEffect(new ScryEffect(5, false));
        this.getSpellAbility().addEffect(new NissasRevelationEffect());

    }

    private NissasRevelation(final NissasRevelation card) {
        super(card);
    }

    @Override
    public NissasRevelation copy() {
        return new NissasRevelation(this);
    }
}

class NissasRevelationEffect extends OneShotEffect {

    public NissasRevelationEffect() {
        super(Outcome.DrawCard);
        this.staticText = ", then reveal the top card of your library. If it's a creature card, you draw cards equal to its power and you gain life equal to its toughness";
    }

    private NissasRevelationEffect(final NissasRevelationEffect effect) {
        super(effect);
    }

    @Override
    public NissasRevelationEffect copy() {
        return new NissasRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);

            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.isCreature(game)) {
                    controller.drawCards(card.getPower().getValue(), source, game);
                    controller.gainLife(card.getToughness().getValue(), game, source);
                }
            }
            return true;
        }
        return false;
    }
}
