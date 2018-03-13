/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.Rarity;

/**
 *
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
        for (String setCode :setCodesDeck) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
            if (expansionInfo.hasBasicLands()) {
                landSetCodes.add(expansionInfo.getCode());
            }
        }

        // if sets have no basic land, take land from block
        if (landSetCodes.isEmpty()) {
            for (String setCode :setCodesDeck) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                List<ExpansionInfo> blockSets = ExpansionRepository.instance.getSetsFromBlock(expansionInfo.getBlockName());
                for (ExpansionInfo blockSet: blockSets) {
                    if (blockSet.hasBasicLands()) {
                        landSetCodes.add(blockSet.getCode());
                    }
                }
            }
        }
        // if still no set with lands found, take one by random
        if (landSetCodes.isEmpty()) {
            // if sets have no basic lands and also it has no parent or parent has no lands get last set with lands
            // select a set with basic lands by random
            List<ExpansionInfo> basicLandSets = ExpansionRepository.instance.getSetsWithBasicLandsByReleaseDate();
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
        }
        criteria.rarities(Rarity.LAND).name(landName);
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
