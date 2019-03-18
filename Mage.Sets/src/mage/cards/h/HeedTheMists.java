
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HeedTheMists extends CardImpl {

    public HeedTheMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");
        this.subtype.add(SubType.ARCANE);

        // Put the top card of your library into your graveyard, then draw cards equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new HeedTheMistsEffect());
    }

    public HeedTheMists(final HeedTheMists card) {
        super(card);
    }

    @Override
    public HeedTheMists copy() {
        return new HeedTheMists(this);
    }

    private static class HeedTheMistsEffect extends OneShotEffect {

        public HeedTheMistsEffect() {
            super(Outcome.DrawCard);
            staticText = "Put the top card of your library into your graveyard, then draw cards equal to that card's converted mana cost";
        }

        public HeedTheMistsEffect(HeedTheMistsEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            boolean result = false;
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    int cmc = card.getConvertedManaCost();
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    controller.drawCards(cmc, game);
                }
            }
            return result;
        }

        @Override
        public HeedTheMistsEffect copy() {
            return new HeedTheMistsEffect(this);
        }
    }
}
