package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeartwarmingRedemption extends CardImpl {

    public HeartwarmingRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // Discard all the cards in your hand, then draw that many cards plus one. You gain life equal to the number of cards in your hand.
        this.getSpellAbility().addEffect(new HeartwarmingRedemptionEffect());
    }

    private HeartwarmingRedemption(final HeartwarmingRedemption card) {
        super(card);
    }

    @Override
    public HeartwarmingRedemption copy() {
        return new HeartwarmingRedemption(this);
    }
}

class HeartwarmingRedemptionEffect extends OneShotEffect {

    HeartwarmingRedemptionEffect() {
        super(Outcome.Benefit);
        staticText = "Discard all the cards in your hand, then draw that many cards plus one. " +
                "You gain life equal to the number of cards in your hand.";
    }

    private HeartwarmingRedemptionEffect(final HeartwarmingRedemptionEffect effect) {
        super(effect);
    }

    @Override
    public HeartwarmingRedemptionEffect copy() {
        return new HeartwarmingRedemptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int discarded = player.discard(player.getHand().size(), false, false, source, game).size();
        player.drawCards(discarded + 1, source, game);
        player.gainLife(player.getHand().size(), game, source);
        return true;
    }
}
// Good night, sweet prince :(