package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Map<String, Integer> nameCounts = new HashMap<>();
        player.getHand()
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .forEach(s -> nameCounts.compute(s, CardUtil::setOrIncrementValue));
        Cards cards = new CardsImpl(
                player.getHand()
                        .getCards(game)
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(card -> !card.isLand(game))
                        .filter(card -> nameCounts.getOrDefault(card.getName(), 0) > 1)
                        .collect(Collectors.toSet())
        );
        player.discard(cards, false, source, game);
        return true;
    }
}
