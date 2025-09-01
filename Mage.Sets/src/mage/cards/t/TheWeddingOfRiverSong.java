package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWeddingOfRiverSong extends CardImpl {

    public TheWeddingOfRiverSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Draw two cards, then you may exile a nonland card from your hand with a number of time counters on it equal to its mana value. Then target opponent does the same. Cards exiled this way that don't have suspend gain suspend.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new TheWeddingOfRiverSongEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Time travel.
        this.getSpellAbility().addEffect(new TimeTravelEffect().concatBy("<br>"));
    }

    private TheWeddingOfRiverSong(final TheWeddingOfRiverSong card) {
        super(card);
    }

    @Override
    public TheWeddingOfRiverSong copy() {
        return new TheWeddingOfRiverSong(this);
    }
}

class TheWeddingOfRiverSongEffect extends OneShotEffect {

    TheWeddingOfRiverSongEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may exile a nonland card from your hand " +
                "with a number of time counters on it equal to its mana value. Then target opponent does the same. " +
                "Cards exiled this way that don't have suspend gain suspend";
    }

    private TheWeddingOfRiverSongEffect(final TheWeddingOfRiverSongEffect effect) {
        super(effect);
    }

    @Override
    public TheWeddingOfRiverSongEffect copy() {
        return new TheWeddingOfRiverSongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : Arrays.asList(
                game.getPlayer(source.getControllerId()),
                game.getPlayer(this.getTargetPointer().getFirst(game, source))
        )) {
            if (player == null
                    || player.getHand().count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1
                    || source.isControlledBy(player.getId())
                    && !player.chooseUse(outcome, "Suspend a nonland card from your hand?", source, game)) {
                continue;
            }
            TargetCard target = new TargetCardInHand(StaticFilters.FILTER_CARD_NON_LAND);
            player.choose(Outcome.PlayForFree, player.getHand(), target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                continue;
            }
            player.moveCards(card, Zone.EXILED, source, game);
            SuspendAbility.addTimeCountersAndSuspend(card, card.getManaValue(), source, game);
        }
        return true;
    }
}
