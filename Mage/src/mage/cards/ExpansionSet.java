/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.cards;

import mage.Constants.Rarity;
import mage.Constants.SetType;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

    private final static Logger logger = Logger.getLogger(ExpansionSet.class);

    protected static Random rnd = new Random();

    protected String name;
    protected String code;
    protected String symbolCode;
    protected Date releaseDate;
    protected ExpansionSet parentSet;
    protected List<Card> cards;
    protected SetType setType;
    protected Map<Rarity, List<Card>> rarities;

    protected String blockName;
    protected boolean hasBoosters = false;
    protected int numBoosterLands;
    protected int numBoosterCommon;
    protected int numBoosterUncommon;
    protected int numBoosterRare;
    protected int numBoosterDoubleFaced;
    protected int ratioBoosterMythic;

    protected String packageName;

    public ExpansionSet(String name, String code, String symbolCode, String packageName, Date releaseDate, SetType setType) {
        this.name = name;
        this.code = code;
        this.symbolCode = symbolCode;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public SetType getSetType() {
        return setType;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Card> createBooster() {
        List<Card> booster = new ArrayList<Card>();
        if (!hasBoosters) {
            return booster;
        }

        CardCriteria criteria = new CardCriteria();
        criteria.setCodes(parentSet != null ? parentSet.code : this.code).rarities(Rarity.LAND).doubleFaced(false);
        List<CardInfo> basicLand = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.COMMON).doubleFaced(false);
        List<CardInfo> common = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.UNCOMMON).doubleFaced(false);
        List<CardInfo> uncommon = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.RARE).doubleFaced(false);
        List<CardInfo> rare = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.MYTHIC).doubleFaced(false);
        List<CardInfo> mythic = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).doubleFaced(true);
        List<CardInfo> doubleFaced = CardRepository.instance.findCards(criteria);

        for (int i = 0; i < numBoosterLands; i++) {
            addToBooster(booster, basicLand);
        }
        for (int i = 0; i < numBoosterCommon; i++) {
            addToBooster(booster, common);
        }
        for (int i = 0; i < numBoosterUncommon; i++) {
            addToBooster(booster, uncommon);
        }
        for (int i = 0; i < numBoosterRare; i++) {
            if (ratioBoosterMythic > 0 && rnd.nextInt(ratioBoosterMythic) == 1) {
                addToBooster(booster, mythic);
            } else {
                addToBooster(booster, rare);
            }
        }
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            addToBooster(booster, doubleFaced);
        }

        return booster;
    }

    private void addToBooster(List<Card> booster, List<CardInfo> cards) {
        if (!cards.isEmpty()) {
            CardInfo cardInfo = cards.remove(rnd.nextInt(cards.size()));
            if (cardInfo != null) {
                Card card = cardInfo.getCard();
                if (card != null) {
                    booster.add(card);
                }
            }
        }
    }
    
    public boolean hasBoosters() {
        return hasBoosters;
    }
}
