package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author TheElk801
 */
public class ConjureCardEffect extends OneShotEffect {

    private final String cardName;
    private final Zone zone;
    private final int amount;

    public ConjureCardEffect(String cardName) {
        this(cardName, Zone.HAND, 1);
    }

    public ConjureCardEffect(String cardName, Zone zone, int amount) {
        super(Outcome.Benefit);
        this.cardName = cardName;
        this.zone = zone;
        this.amount = amount;
    }

    private ConjureCardEffect(final ConjureCardEffect effect) {
        super(effect);
        this.cardName = effect.cardName;
        this.zone = effect.zone;
        this.amount = effect.amount;
    }

    @Override
    public ConjureCardEffect copy() {
        return new ConjureCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CardInfo cardInfo = CardRepository
                .instance
                .findCards(new CardCriteria().name(cardName))
                .stream()
                .findFirst()
                .orElse(null);
        if (cardInfo == null) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            Card card = cardInfo.getCard();
            cards.add(card);
        }
        game.loadCards(cards, source.getControllerId());
        return player.moveCards(cards, zone, source, game);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("conjure ");
        sb.append(CardUtil.numberToText(amount, "a"));
        sb.append(' ');
        sb.append("card");
        sb.append(amount > 1 ? "s " : " ");
        sb.append("named ");
        sb.append(cardName);
        sb.append(' ');
        switch (zone) {
            case HAND:
            case GRAVEYARD:
                sb.append("into your");
                break;
            case BATTLEFIELD:
                sb.append("onto the");
        }
        sb.append(' ');
        sb.append(zone.toString().toLowerCase());
        return sb.toString();
    }
}
