package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OccultEpiphany extends CardImpl {

    public OccultEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Draw X cards, then discard X cards. Create a 1/1 white Spirit creature token with flying for each card type among cards discarded this way.
        this.getSpellAbility().addEffect(new OccultEpiphanyEffect());
    }

    private OccultEpiphany(final OccultEpiphany card) {
        super(card);
    }

    @Override
    public OccultEpiphany copy() {
        return new OccultEpiphany(this);
    }
}

class OccultEpiphanyEffect extends OneShotEffect {

    OccultEpiphanyEffect() {
        super(Outcome.Benefit);
        staticText = "draw X cards, then discard X cards. Create a 1/1 white Spirit creature token " +
                "with flying for each card type among cards discarded this way";
    }

    private OccultEpiphanyEffect(final OccultEpiphanyEffect effect) {
        super(effect);
    }

    @Override
    public OccultEpiphanyEffect copy() {
        return new OccultEpiphanyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1) {
            return false;
        }
        player.drawCards(xValue, source, game);
        int cardTypes = player
                .discard(xValue, false, false, source, game)
                .getCards(game)
                .stream()
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
        if (cardTypes > 0) {
            new SpiritWhiteToken().putOntoBattlefield(cardTypes, game, source);
        }
        return true;
    }
}
