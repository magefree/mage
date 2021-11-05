package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TappingAtTheWindow extends CardImpl {

    public TappingAtTheWindow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Look at the top three cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new TappingAtTheWindowEffect());

        // Flashback {2}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{G}")));
    }

    private TappingAtTheWindow(final TappingAtTheWindow card) {
        super(card);
    }

    @Override
    public TappingAtTheWindow copy() {
        return new TappingAtTheWindow(this);
    }
}

class TappingAtTheWindowEffect extends OneShotEffect {

    TappingAtTheWindowEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top three cards of your library. You may reveal a creature card " +
                "from among them and put it into your hand. Put the rest into your graveyard";
    }

    private TappingAtTheWindowEffect(final TappingAtTheWindowEffect effect) {
        super(effect);
    }

    @Override
    public TappingAtTheWindowEffect copy() {
        return new TappingAtTheWindowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        TargetCard target = new TargetCardInLibrary(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        );
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(card, Zone.GRAVEYARD, source, game);
        return true;
    }
}
