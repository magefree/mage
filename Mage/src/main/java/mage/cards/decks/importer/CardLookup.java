package mage.cards.decks.importer;

import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

import java.util.List;
import java.util.Optional;

public class CardLookup {

    public static final CardLookup instance = new CardLookup();

    public Optional<CardInfo> lookupCardInfo(String name) {
        return Optional.ofNullable(CardRepository.instance.findPreferredCoreExpansionCard(name));
    }

    public List<CardInfo> lookupCardInfo(CardCriteria criteria) {
        return CardRepository.instance.findCards(criteria);
    }

    public Optional<CardInfo> lookupCardInfo(String name, String set) {
        Optional<CardInfo> result = lookupCardInfo(new CardCriteria().name(name).setCodes(set))
                .stream()
                .findAny();
        if (result.isPresent()) {
            return result;
        }

        return lookupCardInfo(name);
    }

    public Optional<CardInfo> lookupCardInfo(String name, String set, String cardNumber) {
        Optional<CardInfo> result;
        try {
            int intCardNumber = Integer.parseInt(cardNumber);
            result = lookupCardInfo(
                    new CardCriteria()
                            .name(name)
                            .setCodes(set)
                            .minCardNumber(intCardNumber)
                            .maxCardNumber(intCardNumber))
                    .stream()
                    .findAny();
            if (result.isPresent()) {
                return result;
            }
        } catch (NumberFormatException ignored) {
            /* ignored */
        }

        return lookupCardInfo(name, set);
    }

}
