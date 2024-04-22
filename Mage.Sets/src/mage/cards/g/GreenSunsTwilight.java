package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreenSunsTwilight extends CardImpl {

    public GreenSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Reveal the top X plus one cards of your library. Choose a creature card and/or a land card from among them. Put those cards into your hand and the rest on the bottom of your library in a random order. If X is 5 or more, instead put the chosen cards onto the battlefield or into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new GreenSunsTwilightEffect());
    }

    private GreenSunsTwilight(final GreenSunsTwilight card) {
        super(card);
    }

    @Override
    public GreenSunsTwilight copy() {
        return new GreenSunsTwilight(this);
    }
}

class GreenSunsTwilightEffect extends OneShotEffect {

    GreenSunsTwilightEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top X plus one cards of your library. Choose a creature card and/or a land card " +
                "from among them. Put those cards into your hand and the rest on the bottom of your library " +
                "in a random order. If X is 5 or more, instead put the chosen cards onto the battlefield " +
                "or into your hand and the rest on the bottom of your library in a random order";
    }

    private GreenSunsTwilightEffect(final GreenSunsTwilightEffect effect) {
        super(effect);
    }

    @Override
    public GreenSunsTwilightEffect copy() {
        return new GreenSunsTwilightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue + 1));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardAndOrCardInLibrary(CardType.CREATURE, CardType.LAND);
        player.choose(outcome, cards, target, source, game);
        Cards toMove = new CardsImpl(target.getTargets());
        if (!toMove.isEmpty()) {
            Zone zone = xValue >= 5 && player.chooseUse(
                    Outcome.PutCardInPlay, "Put the chosen cards onto the battlefield or into your hand?",
                    null, "Battlefield", "Hand", source, game
            ) ? Zone.BATTLEFIELD : Zone.HAND;
            player.moveCards(toMove, zone, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
