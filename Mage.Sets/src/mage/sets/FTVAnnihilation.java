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

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FTVAnnihilation extends ExpansionSet {

    private static final FTVAnnihilation fINSTANCE = new FTVAnnihilation();

    public static FTVAnnihilation getInstance() {
        return fINSTANCE;
    }

    private FTVAnnihilation() {
        super("From the Vault: Annihilation", "V14", "mage.sets.ftvannihilation", ExpansionSet.buildDate(2014, 8, 22), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Armageddon", 1, Rarity.MYTHIC, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Burning of Xinye", 2, Rarity.MYTHIC, mage.cards.b.BurningOfXinye.class));
        cards.add(new SetCardInfo("Cataclysm", 3, Rarity.MYTHIC, mage.cards.c.Cataclysm.class));
        cards.add(new SetCardInfo("Child of Alara", 4, Rarity.MYTHIC, mage.cards.c.ChildOfAlara.class));
        cards.add(new SetCardInfo("Decree of Annihilation", 5, Rarity.MYTHIC, mage.cards.d.DecreeOfAnnihilation.class));
        cards.add(new SetCardInfo("Firespout", 6, Rarity.MYTHIC, mage.cards.f.Firespout.class));
        cards.add(new SetCardInfo("Fracturing Gust", 7, Rarity.MYTHIC, mage.cards.f.FracturingGust.class));
        cards.add(new SetCardInfo("Living Death", 8, Rarity.MYTHIC, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Martial Coup", 9, Rarity.MYTHIC, mage.cards.m.MartialCoup.class));
        cards.add(new SetCardInfo("Rolling Earthquake", 10, Rarity.MYTHIC, mage.cards.r.RollingEarthquake.class));
        cards.add(new SetCardInfo("Smokestack", 11, Rarity.MYTHIC, mage.cards.s.Smokestack.class));
        cards.add(new SetCardInfo("Terminus", 12, Rarity.MYTHIC, mage.cards.t.Terminus.class));
        cards.add(new SetCardInfo("Upheaval", 13, Rarity.MYTHIC, mage.cards.u.Upheaval.class));
        cards.add(new SetCardInfo("Virtue's Ruin", 14, Rarity.MYTHIC, mage.cards.v.VirtuesRuin.class));
        cards.add(new SetCardInfo("Wrath of God", 15, Rarity.MYTHIC, mage.cards.w.WrathOfGod.class));
    }
}
