package mage.cards.i;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;
import mage.MageObject;
import mage.cards.Card;

/**
 * @author LevelX2
 */
public final class InducedAmnesia extends CardImpl {

    public InducedAmnesia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Induced Amnesia enters the battlefield, target player exiles all the cards in their hand face down, then draws that many cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InducedAmnesiaExileEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Induced Amnesia is put into a graveyard from the battlefield, return the exiled cards to their owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new InducedAmnesiaReturnEffect()));
    }

    private InducedAmnesia(final InducedAmnesia card) {
        super(card);
    }

    @Override
    public InducedAmnesia copy() {
        return new InducedAmnesia(this);
    }
}

class InducedAmnesiaExileEffect extends OneShotEffect {

    InducedAmnesiaExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player exiles all cards from their hand face down, then draws that many cards";
    }

    private InducedAmnesiaExileEffect(final InducedAmnesiaExileEffect effect) {
        super(effect);
    }

    @Override
    public InducedAmnesiaExileEffect copy() {
        return new InducedAmnesiaExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        int numberOfCards = targetPlayer.getHand().size();
        if (numberOfCards < 1) {
            return false;
        }
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPlayer.getHand());
        targetPlayer.moveCardsToExile(
                cards.getCards(game), source, game, false,
                CardUtil.getExileZoneId(game, mageObject.getId(), game.getState().getZoneChangeCounter(mageObject.getId())), CardUtil.getSourceName(game, source)
        );
        cards.getCards(game)
                .stream()
                .filter(card -> game.getState().getZone(card.getId()) == Zone.EXILED)
                .forEach(card -> card.setFaceDown(true, game));
        targetPlayer.drawCards(numberOfCards, source, game);
        return true;
    }
}

class InducedAmnesiaReturnEffect extends OneShotEffect {

    InducedAmnesiaReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return the exiled cards to their owner's hand";
    }

    private InducedAmnesiaReturnEffect(final InducedAmnesiaReturnEffect effect) {
        super(effect);
    }

    @Override
    public InducedAmnesiaReturnEffect copy() {
        return new InducedAmnesiaReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, mageObject.getId(), game.getState().getZoneChangeCounter(mageObject.getId()) - 1));
        if (exileZone == null
                || exileZone.isEmpty()) {
            return false;
        }
        Set<Card> cards = exileZone.getCards(game);
        Map<UUID, Cards> cardsByOwner = new HashMap<>();

        // Group exiled cards by owners (ie: Strionic Resonator)
        for (Card card : cards) {
            cardsByOwner.computeIfAbsent(card.getOwnerId(), k -> new CardsImpl()).add(card);
        }

        // Move cards to their respective owner's hands
        for (Map.Entry<UUID, Cards> entry : cardsByOwner.entrySet()) {
            Player owner = game.getPlayer(entry.getKey());
            if (owner != null) {
                owner.moveCards(entry.getValue(), Zone.HAND, source, game);
            }
        }
        return true;
    }
}
