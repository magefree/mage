package mage.game.command.emblems;

import mage.MageObject;
import mage.abilities.AbilityImpl;
import mage.cards.Card;
import mage.cards.decks.DeckCardInfo;
import mage.cards.mock.MockCard;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.util.CardUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author artemiswkearney
 * Emblem with all the abilities of an existing card.
 * Can be used for custom gamemodes like Omniscience Draft (as seen on Arena),
 * mana burn with Yurlok of Scorch Thrash, and anything else players might think of.
 */
public final class EmblemOfCard extends Emblem {
    
    public static Card lookupCard(
            String cardName,
            String cardNumber,
            String setCode,
            String infoTypeForError
    ) {
        int cardNumberInt = CardUtil.parseCardNumberAsInt(cardNumber);
        List<CardInfo> found = CardRepository.instance.findCards(new CardCriteria()
                .name(cardName)
                .minCardNumber(cardNumberInt)
                .maxCardNumber(cardNumberInt)
                .setCodes(setCode));
        return found.stream()
                .filter(ci -> ci.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElseGet(() -> found.stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No real card for " + infoTypeForError + " " + cardName)))
                .createCard();
    }
    
    public static Card cardFromDeckInfo(DeckCardInfo info) {
        return lookupCard(
                info.getCardName(),
                info.getCardNumber(),
                info.getSetCode(),
                "DeckCardInfo"
        );
    }
    
    public EmblemOfCard(Card card, Zone zone) {
        super(card.getName());
        if (card instanceof MockCard) {
            card = lookupCard(
                    card.getName(),
                    card.getCardNumber(),
                    card.getExpansionSetCode(),
                    "MockCard"
            );
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
        this.setUsesVariousArt(card.getUsesVariousArt());
        this.setCardNumber(card.getCardNumber());
        this.setImageFileName(card.getImageFileName());
        this.setImageNumber(card.getImageNumber());
    }
    
    public EmblemOfCard(Card card) {
        this(card, Zone.BATTLEFIELD);
    }

    private EmblemOfCard(final EmblemOfCard eoc) {
        super(eoc);
    }

    @Override
    public EmblemOfCard copy() {
        return new EmblemOfCard(this);
    }

    @Override
    public void setSourceObject(MageObject sourceObject) {
        this.sourceObject = sourceObject;
        // super method would try and fail to find the emblem image here
        // (not sure why that would be setSoureObject's job; we get our image during construction)
    }
}

