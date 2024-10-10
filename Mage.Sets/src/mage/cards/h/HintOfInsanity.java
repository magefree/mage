package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class HintOfInsanity extends CardImpl {

    public HintOfInsanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player reveals their hand. That player discards all nonland cards with the same name as another card in their hand.
        this.getSpellAbility().addEffect(new HintOfInsanityEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HintOfInsanity(final HintOfInsanity card) {
        super(card);
    }

    @Override
    public HintOfInsanity copy() {
        return new HintOfInsanity(this);
    }
}

class HintOfInsanityEffect extends OneShotEffect {

    HintOfInsanityEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand. " +
                "That player discards all nonland cards with the same name as another card in their hand";
    }

    private HintOfInsanityEffect(final HintOfInsanityEffect effect) {
        super(effect);
    }

    @Override
    public HintOfInsanityEffect copy() {
        return new HintOfInsanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Set<Card> cards = CardUtil.streamAllPairwiseMatches(
                player.getHand().getCards(game),
                (p1, p2) -> p1.sharesName(p2, game)
        ).collect(Collectors.toSet());
        return !cards.isEmpty() && !player.discard(new CardsImpl(cards), false, source, game).isEmpty();
    }
}
