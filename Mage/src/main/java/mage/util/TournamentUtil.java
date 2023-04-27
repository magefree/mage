package mage.util;

import mage.cards.Card;
import mage.cards.repository.*;
import mage.constants.Rarity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class TournamentUtil {

    /**
     * Tries to calculate the most appropiate sets to add basic lands for cards of a deck
     *
     * @param setCodesDeck
     * @return setCode for lands
     */

    public static Set<String> getLandSetCodeForDeckSets(Collection<String> setCodesDeck) {

        Set<String> landSetCodes = new HashSet<>();
        // decide from which sets basic lands are taken from
        for (String setCode : setCodesDeck) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
            if (expansionInfo.hasBasicLands() && !CardRepository.haveSnowLands(setCode)) {
                landSetCodes.add(expansionInfo.getCode());
            }
        }

        // if sets have no basic land, take land from block
        if (landSetCodes.isEmpty()) {
            for (String setCode : setCodesDeck) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                List<ExpansionInfo> blockSets = ExpansionRepository.instance.getSetsFromBlock(expansionInfo.getBlockName());
                for (ExpansionInfo blockSet : blockSets) {
                    if (blockSet.hasBasicLands() && !CardRepository.haveSnowLands(blockSet.getCode())) {
                        landSetCodes.add(blockSet.getCode());
                    }
                }
            }
        }
        // if still no set with lands found, take one by random
        if (landSetCodes.isEmpty()) {
            // if sets have no basic lands and also it has no parent or parent has no lands get last set with lands
            // select a set with basic lands by random
            List<ExpansionInfo> basicLandSets = ExpansionRepository.instance.getSetsWithBasicLandsByReleaseDate()
                    .stream()
                    .filter(exp -> !CardRepository.haveSnowLands(exp.getCode()))
                    .collect(Collectors.toList());
            if (!basicLandSets.isEmpty()) {
                landSetCodes.add(basicLandSets.get(RandomUtil.nextInt(basicLandSets.size())).getCode());
            }
        }

        if (landSetCodes.isEmpty()) {
            throw new IllegalArgumentException("No set with basic land was found");
        }
        return landSetCodes;
    }

    public static List<Card> getLands(String landName, int number, Set<String> landSets) {
        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        } else {
            criteria.ignoreSetsWithSnowLands();
        }
        criteria.rarities(Rarity.LAND).nameExact(landName);
        List<CardInfo> lands = CardRepository.instance.findCards(criteria);
        List<Card> cards = new ArrayList<>();
        if (!lands.isEmpty()) {
            for (int i = 0; i < number; i++) {
                Card land = lands.get(RandomUtil.nextInt(lands.size())).getCard();
                cards.add(land);
            }
        }
        return cards;
    }

}
