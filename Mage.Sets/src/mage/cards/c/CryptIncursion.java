package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CryptIncursion extends CardImpl {

    public CryptIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Exile all creature cards from target player's graveyard. You gain 3 life for each card exiled this way.
        this.getSpellAbility().addEffect(new CryptIncursionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private CryptIncursion(final CryptIncursion card) {
        super(card);
    }

    @Override
    public CryptIncursion copy() {
        return new CryptIncursion(this);
    }
}

class CryptIncursionEffect extends OneShotEffect {

    CryptIncursionEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from target player's graveyard. " +
                "You gain 3 life for each card exiled this way";
    }

    private CryptIncursionEffect(final CryptIncursionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Cards cards = new CardsImpl(targetPlayer.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        player.moveCards(cards, Zone.EXILED, source, game);
        int count = cards
                .stream()
                .map(game.getState()::getZone)
                .map(Zone.EXILED::equals)
                .mapToInt(x -> x ? 1 : 0)
                .sum();
        player.gainLife(3 * count, game, source);
        return true;
    }

    @Override
    public CryptIncursionEffect copy() {
        return new CryptIncursionEffect(this);
    }
}
