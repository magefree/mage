package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreativeTechnique extends CardImpl {

    public CreativeTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Shuffle your library, then reveal cards from the top of it until you reveal a nonland card. Exile that card and put the rest on the bottom of your library in a random order. You may cast the exiled card without paying its mana cost.
        this.getSpellAbility().addEffect(new CreativeTechniqueEffect());
    }

    private CreativeTechnique(final CreativeTechnique card) {
        super(card);
    }

    @Override
    public CreativeTechnique copy() {
        return new CreativeTechnique(this);
    }
}

class CreativeTechniqueEffect extends OneShotEffect {

    CreativeTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle your library, then reveal cards from the top of it until you reveal a nonland card. " +
                "Exile that card and put the rest on the bottom of your library in a random order. " +
                "You may cast the exiled card without paying its mana cost";
    }

    private CreativeTechniqueEffect(final CreativeTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public CreativeTechniqueEffect copy() {
        return new CreativeTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.shuffleLibrary(source, game);
        Cards cards = new CardsImpl();
        Card toCast = null;
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (!card.isLand(game)) {
                toCast = card;
                break;
            }
        }
        player.revealCards(source, cards, game);
        cards.remove(toCast);
        if (toCast != null) {
            player.moveCards(toCast, Zone.EXILED, source, game);
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        CardUtil.castSpellWithAttributesForFree(player, source, game, toCast);
        return true;
    }
}
