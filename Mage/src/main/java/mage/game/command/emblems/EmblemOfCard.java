package mage.game.command.emblems;

import mage.MageObject;
import mage.abilities.AbilityImpl;
import mage.cards.Card;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.importer.CardLookup;
import mage.cards.mock.MockCard;
import mage.constants.Zone;
import mage.game.command.Emblem;
import org.apache.log4j.Logger;

import java.util.stream.Collectors;

public final class EmblemOfCard extends Emblem {
    private static final Logger logger = Logger.getLogger(EmblemOfCard.class);
    public static Card cardFromDeckInfo(DeckCardInfo info) {
        return CardLookup.instance.lookupCardInfo(info.getCardName(), info.getSetCode(), info.getCardNum())
                .orElseThrow(() -> new IllegalArgumentException("No real card for DeckCardInfo" + info.getCardName()))
                .getCard();
    }
    public EmblemOfCard(Card card, Zone zone) {
        super(card.getName());
        if (card instanceof MockCard) {
            MockCard mock = (MockCard)card;
            card = CardLookup.instance.lookupCardInfo(card.getName(), card.getExpansionSetCode(), card.getCardNumber())
                    .orElseThrow(() -> new IllegalArgumentException("No real card for mock card " + mock.getFullName(true)))
                    .getCard();
        }
        this.getAbilities().addAll(card.getAbilities().stream().filter(
                ability -> zone.match(ability.getZone())
        ).map(ability -> {
            if (ability instanceof AbilityImpl && ability.getZone() == zone) {
                return ((AbilityImpl)ability).copyWithZone(Zone.COMMAND);
            }
            return ability;
        }).collect(Collectors.toList()));
        this.getAbilities().setSourceId(this.getId());
        this.setExpansionSetCode(card.getExpansionSetCode());
        this.setCardNumber(card.getCardNumber());
        this.setImageNumber(card.getImageNumber());
    }
    public EmblemOfCard(Card card) {
        this(card, Zone.BATTLEFIELD);
    }

    private EmblemOfCard(EmblemOfCard eoc) {
        super(eoc);
    }
    @Override
    public Emblem copy() {
        return new EmblemOfCard(this);
    }

    @Override
    public void setSourceObject(MageObject sourceObject) {
        try {
            super.setSourceObject(sourceObject);
        }
        catch (IllegalArgumentException e) {
            // happens because this isn't a real emblem, but the source object gets set before throwing so it's fine
        }
    }
}

