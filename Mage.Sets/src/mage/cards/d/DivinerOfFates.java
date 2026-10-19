package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SharesCardTypePredicate;
import mage.game.Game;
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

public final class DivinerOfFates extends CardImpl {

    public DivinerOfFates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect()));
        this.addAbility(new DivinerOfFatesTriggeredAbility().setTriggersLimitEachTurn(1));
    }

    private DivinerOfFates(final DivinerOfFates card) {
        super(card);
    }

    @Override
    public DivinerOfFates copy() {
        return new DivinerOfFates(this);
    }
}

class DivinerOfFatesTriggeredAbility extends TriggeredAbilityImpl {

    DivinerOfFatesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SeekCardEffect(new FilterCard(
                "card that shares a card type with one of the discarded cards")));
        setTriggerPhrase("Whenever you discard one or more cards, ");
    }

    private DivinerOfFatesTriggeredAbility(final DivinerOfFatesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DivinerOfFatesTriggeredAbility copy() {
        return new DivinerOfFatesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || event.getAmount() == 0) {
            return false;
        }
        Set<CardType> types = EnumSet.noneOf(CardType.class);
        for (Card card : ((DiscardedCardsEvent) event).getDiscardedCards().getCards(game)) {
            types.addAll(card.getCardType(game));
        }
        FilterCard filter = new FilterCard("card that shares a card type with one of the discarded cards");
        filter.add(new SharesCardTypePredicate(types));
        getEffects().clear();
        addEffect(new SeekCardEffect(filter));
        return true;
    }
}
