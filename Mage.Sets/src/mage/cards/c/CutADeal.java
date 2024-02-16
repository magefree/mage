package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutADeal extends CardImpl {

    public CutADeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Each opponent draws a card, then you draw a card for each opponent who drew a card this way.
        this.getSpellAbility().addEffect(new CutADealEffect());
    }

    private CutADeal(final CutADeal card) {
        super(card);
    }

    @Override
    public CutADeal copy() {
        return new CutADeal(this);
    }
}

class CutADealEffect extends OneShotEffect {

    CutADealEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent draws a card, then you draw a card for each opponent who drew a card this way";
    }

    private CutADealEffect(final CutADealEffect effect) {
        super(effect);
    }

    @Override
    public CutADealEffect copy() {
        return new CutADealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(player -> player.drawCards(1, source, game))
                .sum();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && count > 0) {
            controller.drawCards(count, source, game);
            return true;
        }
        return false;
    }
}
