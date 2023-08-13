
package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HeedTheMists extends CardImpl {

    public HeedTheMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");
        this.subtype.add(SubType.ARCANE);

        // Put the top card of your library into your graveyard, then draw cards equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new HeedTheMistsEffect());
    }

    private HeedTheMists(final HeedTheMists card) {
        super(card);
    }

    @Override
    public HeedTheMists copy() {
        return new HeedTheMists(this);
    }

    private static class HeedTheMistsEffect extends OneShotEffect {

        public HeedTheMistsEffect() {
            super(Outcome.DrawCard);
            staticText = "Mill a card, then draw cards equal to the milled card's mana value";
        }

        public HeedTheMistsEffect(HeedTheMistsEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            int totalCMC = controller
                    .millCards(1, source, game)
                    .getCards(game)
                    .stream()
                    .mapToInt(MageObject::getManaValue)
                    .sum();
            controller.drawCards(totalCMC, source, game);
            return true;
        }

        @Override
        public HeedTheMistsEffect copy() {
            return new HeedTheMistsEffect(this);
        }
    }
}
