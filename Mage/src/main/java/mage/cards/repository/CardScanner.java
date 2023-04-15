package mage.cards.repository;

import mage.cards.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author North
 */
public final class CardScanner {

    public static boolean scanned = false;

    private static final Logger logger = Logger.getLogger(CardScanner.class);

    public static void scan() {
        scan(null);
    }

    public static void scan(List<String> errorsList) {
        if (scanned) {
            return;
        }
        scanned = true;

        List<CardInfo> cardsToAdd = new ArrayList<>();
        List<ExpansionInfo> setsToAdd = new ArrayList<>();
        List<ExpansionInfo> setsToUpdate = new ArrayList<>();

        // check sets
        for (ExpansionSet set : Sets.getInstance().values()) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(set.getCode());
            if (expansionInfo == null) {
                // need add
                setsToAdd.add(new ExpansionInfo(set));
            } else if (!expansionInfo.name.equals(set.getName())
                    || !expansionInfo.code.equals(set.getCode())
                    || !(Objects.equals(expansionInfo.blockName, set.getBlockName()))
                    || !expansionInfo.releaseDate.equals(set.getReleaseDate())
                    || expansionInfo.type != set.getSetType()
                    || expansionInfo.boosters != set.hasBoosters()
                    || expansionInfo.basicLands != set.hasBasicLands()) {
                // need update
                setsToUpdate.add(expansionInfo);
            }
        }
        ExpansionRepository.instance.saveSets(setsToAdd, setsToUpdate, ExpansionRepository.instance.getContentVersionConstant());

        // check cards (only add mode, without updates)
        for (ExpansionSet set : Sets.getInstance().values()) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                if (CardRepository.instance.findCard(set.getCode(), setInfo.getCardNumber(), false) == null) {
                    // found new card
                    Card card = CardImpl.createCard(
                            setInfo.getCardClass(),
                            new CardSetInfo(setInfo.getName(), set.getCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                            errorsList);
                    if (card != null) {
                        // Adds only main card, except night cards.

                        // TODO: remove night cards from sets and db someday
                        // Possible reasons for night cards in sets:
                        // - direct put night card to battlefield by name in tests;
                        // - images download;
                        // - in old days xmage client was able to works without card classes, e.g.
                        //   downloads unknown cards from the server as texts (images, hints and all other works fine with it)

                        cardsToAdd.add(new CardInfo(card));
                        if (card instanceof SplitCard) {
                            SplitCard splitCard = (SplitCard) card;
                            cardsToAdd.add(new CardInfo(splitCard.getLeftHalfCard()));
                            cardsToAdd.add(new CardInfo(splitCard.getRightHalfCard()));
                        }
                    }
                }
            }
        }
        CardRepository.instance.saveCards(cardsToAdd, CardRepository.instance.getContentVersionConstant());
    }

    public static List<Card> getAllCards() {
        return getAllCards(true);
    }

    public static List<Card> getAllCards(boolean ignoreCustomSets) {
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        List<Card> cards = new ArrayList<>();
        for (ExpansionSet set : sets) {
            if (ignoreCustomSets && set.getSetType().isCustomSet()) {
                continue;
            }
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                cards.add(CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                        setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo())));
            }
        }
        return cards;
    }
}
