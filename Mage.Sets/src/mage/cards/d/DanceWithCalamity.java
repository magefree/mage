package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DanceWithCalamity extends CardImpl {

    public DanceWithCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{R}");

        // Shuffle your library. As many times as you choose, you may exile the top card of your library. If the total mana value of the cards exiled this way is 13 or less, you may cast any number of spells from among those cards without paying their mana costs.
        this.getSpellAbility().addEffect(new DanceWithCalamityEffect());
    }

    private DanceWithCalamity(final DanceWithCalamity card) {
        super(card);
    }

    @Override
    public DanceWithCalamity copy() {
        return new DanceWithCalamity(this);
    }
}

class DanceWithCalamityEffect extends OneShotEffect {

    DanceWithCalamityEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle your library. As many times as you choose, you may exile the top card of your library. " +
                "If the total mana value of the cards exiled this way is 13 or less, you may cast any number " +
                "of spells from among those cards without paying their mana costs";
    }

    private DanceWithCalamityEffect(final DanceWithCalamityEffect effect) {
        super(effect);
    }

    @Override
    public DanceWithCalamityEffect copy() {
        return new DanceWithCalamityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        while (player.getLibrary().hasCards()) {
            int totalMV = cards
                    .getCards(game)
                    .stream()
                    .mapToInt(MageObject::getManaValue)
                    .sum();
            if (!player.chooseUse(
                    outcome, "Exile the top card of your library?",
                    "Current total mana value is " + totalMV,
                    "Yes", "No", source, game
            )) {
                break;
            }
            Card card = player.getLibrary().getFromTop(game);
            player.moveCards(card, Zone.EXILED, source, game);
            cards.add(card);
        }
        if (cards
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum() <= 13) {
            CardUtil.castMultipleWithAttributeForFree(player, source, game, cards, StaticFilters.FILTER_CARD);
        }
        return true;
    }
}
