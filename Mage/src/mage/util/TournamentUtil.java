/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
public class TournamentUtil {
    
    /**
     * Tries to calculate the most appropiate sets to add basic lands for cards of a deck
     * 
     * @param setCodesDeck
     * @return setCode for lands
     */
            
    public static Set<String> getLandSetCodeForDeckSets(Set<String> setCodesDeck) {
        
        Set<String> setCodesland = new HashSet<>();
        // decide from which sets basic lands are taken from
        for (String setCode :setCodesDeck) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
            if (expansionInfo.hasBasicLands()) {
                setCodesland.add(expansionInfo.getCode());
            }
        }

        // if sets have no basic land, take land from block
        if (setCodesland.isEmpty()) {
            for (String setCode :setCodesDeck) {
                ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(setCode);
                List<ExpansionInfo> blockSets = ExpansionRepository.instance.getSetsFromBlock(expansionInfo.getBlockName());
                for (ExpansionInfo blockSet: blockSets) {
                    if (blockSet.hasBasicLands()) {
                        setCodesland.add(blockSet.getCode());
                    }
                }
            }
        }
        // if still no set with lands found, take one by random
        if (setCodesland.isEmpty()) {
            // if sets have no basic lands and also it has no parent or parent has no lands get last set with lands
            // select a set with basic lands by random
            Random generator = new Random();
            List<ExpansionInfo> basicLandSets = ExpansionRepository.instance.getSetsWithBasicLandsByReleaseDate();
            if (basicLandSets.size() > 0) {
                setCodesland.add(basicLandSets.get(generator.nextInt(basicLandSets.size())).getCode());
            }
        }

        if (setCodesland.isEmpty()) {
            throw new IllegalArgumentException("No set with basic land was found");
        }
        return setCodesland;
    }
    
    public static List<Card> getLands(String landName, int number, Set<String> landSets) {
        Random random = new Random();
        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        }
        criteria.rarities(Rarity.LAND).name(landName);
        List<CardInfo> lands = CardRepository.instance.findCards(criteria);
        List<Card> cards = new ArrayList<>();
        if (!lands.isEmpty()) {
            for (int i = 0; i < number; i++) {
                Card land = lands.get(random.nextInt(lands.size())).getCard();
                cards.add(land);
            }            
        }        
        return cards;
    }
    
}
