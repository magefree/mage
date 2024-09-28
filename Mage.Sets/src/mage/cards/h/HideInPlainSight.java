package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author notgreat
 */
public final class HideInPlainSight extends CardImpl {

    public HideInPlainSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Look at the top five cards of your library, cloak two of them, and put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new HideInPlainSightEffect());

    }

    private HideInPlainSight(final HideInPlainSight card) {
        super(card);
    }

    @Override
    public HideInPlainSight copy() {
        return new HideInPlainSight(this);
    }
}

class HideInPlainSightEffect extends LookLibraryAndPickControllerEffect {
    HideInPlainSightEffect() {
        super(5, 2, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM);
    }

    private HideInPlainSightEffect(final HideInPlainSightEffect effect) {
        super(effect);
    }

    @Override
    public HideInPlainSightEffect copy() {
        return new HideInPlainSightEffect(this);
    }

    @Override
    public boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        Set<Card> cards =  pickedCards.stream().map(game::getCard).filter(Objects::nonNull).collect(Collectors.toSet());
        boolean result = !(ManifestEffect.doManifestCards(game, source, player, cards, true)).isEmpty();
        result |= putLookedCards.moveCards(player, otherCards, source, game);
        return result;
    }

    @Override
    public String getText(Mode mode) {
        return "Look at the top five cards of your library, cloak two of them, and put the rest on the bottom of your library in a random order";
    }
}
