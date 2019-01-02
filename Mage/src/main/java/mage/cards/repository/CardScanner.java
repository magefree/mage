
package mage.cards.repository;

import java.util.ArrayList;
import java.util.List;
import mage.cards.*;
import org.apache.log4j.Logger;

/**
 *
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
        int setsUpdatedCount = 0;
        int setsAddedCount = 0;

        for (ExpansionSet set : Sets.getInstance().values()) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(set.getCode());
            if (expansionInfo == null) {
                setsAddedCount += 1;
                ExpansionRepository.instance.add(new ExpansionInfo(set));
            } else if (!expansionInfo.name.equals(set.getName())
                    || !expansionInfo.code.equals(set.getCode())
                    || (expansionInfo.blockName == null ? set.getBlockName() != null : !expansionInfo.blockName.equals(set.getBlockName()))
                    || !expansionInfo.releaseDate.equals(set.getReleaseDate())
                    || expansionInfo.type != set.getSetType()
                    || expansionInfo.boosters != set.hasBoosters()
                    || expansionInfo.basicLands != set.hasBasicLands()) {
                setsUpdatedCount += 1;
                ExpansionRepository.instance.update(expansionInfo);
            }
        }
        ExpansionRepository.instance.setContentVersion(ExpansionRepository.instance.getContentVersionConstant());

        if (setsAddedCount > 0) {
            logger.info("DB: need to add " + setsAddedCount + " new sets");
        }
        if (setsUpdatedCount > 0) {
            logger.info("DB: need to update " + setsUpdatedCount + " sets");
        }

        for (ExpansionSet set : Sets.getInstance().values()) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                if (CardRepository.instance.findCard(set.getCode(), setInfo.getCardNumber()) == null) {
                    Card card = CardImpl.createCard(
                            setInfo.getCardClass(),
                            new CardSetInfo(setInfo.getName(), set.getCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                            errorsList);
                    if (card != null) {
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

        if (!cardsToAdd.isEmpty()) {
            logger.info("DB: need to add " + cardsToAdd.size() + " new cards");
            CardRepository.instance.addCards(cardsToAdd);
        }
        CardRepository.instance.setContentVersion(CardRepository.instance.getContentVersionConstant());
    }
}
