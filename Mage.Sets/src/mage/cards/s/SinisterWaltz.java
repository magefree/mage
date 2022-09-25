package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SinisterWaltz extends CardImpl {

    public SinisterWaltz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Choose three target creature cards in your graveyard. Return two of them at random to the battlefield and put the other on the bottom of your library.
        this.getSpellAbility().addEffect(new SinisterWaltzEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(3, StaticFilters.FILTER_CARD_CREATURES));
    }

    private SinisterWaltz(final SinisterWaltz card) {
        super(card);
    }

    @Override
    public SinisterWaltz copy() {
        return new SinisterWaltz(this);
    }
}

class SinisterWaltzEffect extends OneShotEffect {

    SinisterWaltzEffect() {
        super(Outcome.Benefit);
        staticText = "choose three target creature cards in your graveyard. Return two of them " +
                "at random to the battlefield and put the other on the bottom of your library";
    }

    private SinisterWaltzEffect(final SinisterWaltzEffect effect) {
        super(effect);
    }

    @Override
    public SinisterWaltzEffect copy() {
        return new SinisterWaltzEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        Card card;
        if (cards.size() > 2) {
            card = cards.getRandom(game);
            cards.remove(card);
        } else {
            card = null;
        }
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        if (card != null) {
            player.putCardsOnBottomOfLibrary(card, game, source, false);
        }
        return true;
    }
}
