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
package mage.sets;

import java.util.ArrayList;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class HourOfDevastation extends ExpansionSet {

    private static final HourOfDevastation instance = new HourOfDevastation();

    public static HourOfDevastation getInstance() {
        return instance;
    }

    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private HourOfDevastation() {
        super("Hour of Devastation", "HOU", ExpansionSet.buildDate(2017, 7, 14), SetType.EXPANSION);
        this.blockName = "Amonkhet";
        this.parentSet = Amonkhet.getInstance();
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialLand = 144;

        cards.add(new SetCardInfo("Nicol Bolas, God-Pharoh", 140, Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharoh.class));
        cards.add(new SetCardInfo("Samut, the Tested", 144, Rarity.MYTHIC, mage.cards.s.SamutTheTested.class));

    }

    @Override
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("MPS-AKH");
            criteria.minCardNumber(31);
            criteria.maxCardNumber(54);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
