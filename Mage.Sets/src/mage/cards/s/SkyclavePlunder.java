package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.PartyCountHint;
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
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclavePlunder extends CardImpl {

    public SkyclavePlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Look at the top X cards of your library, where X is three plus the number of creatures in your party. Put three of those cards into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new SkyclavePlunderEffect());
        this.getSpellAbility().addHint(PartyCountHint.instance);
    }

    private SkyclavePlunder(final SkyclavePlunder card) {
        super(card);
    }

    @Override
    public SkyclavePlunder copy() {
        return new SkyclavePlunder(this);
    }
}

class SkyclavePlunderEffect extends OneShotEffect {

    SkyclavePlunderEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top X cards of your library, " +
                "where X is three plus the number of creatures in your party. " +
                "Put three of those cards into your hand and the rest " +
                "on the bottom of your library in a random order. " + PartyCount.getReminder();
    }

    private SkyclavePlunderEffect(final SkyclavePlunderEffect effect) {
        super(effect);
    }

    @Override
    public SkyclavePlunderEffect copy() {
        return new SkyclavePlunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = 3 + PartyCount.instance.calculate(game, source, this);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        int toTake = Math.min(cards.size(), 3);
        TargetCard target = new TargetCardInLibrary(toTake, toTake, StaticFilters.FILTER_CARD);
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl(target.getTargets());
        cards.removeIf(target.getTargets()::contains);
        player.moveCards(toHand, Zone.HAND, source, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
