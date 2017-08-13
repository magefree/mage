/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class Commander2017 extends ExpansionSet {

    private static final Commander2017 instance = new Commander2017();

    public static Commander2017 getInstance() {
        return instance;
    }

    private Commander2017() {
        super("Commander 2017 Edition", "C17", ExpansionSet.buildDate(2017, 8, 25), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Balan, Wandering Knight", 2, Rarity.RARE, mage.cards.b.BalanWanderingKnight.class));
        cards.add(new SetCardInfo("Bloodsworn Steward", 22, Rarity.RARE, mage.cards.b.BloodswornSteward.class));
        cards.add(new SetCardInfo("Curse of Bounty", 30, Rarity.UNCOMMON, mage.cards.c.CurseOfBounty.class));
        cards.add(new SetCardInfo("Curse of Disturbance", 16, Rarity.UNCOMMON, mage.cards.c.CurseOfDisturbance.class));
        cards.add(new SetCardInfo("Curse of Opulence", 24, Rarity.UNCOMMON, mage.cards.c.CurseOfOpulence.class));
        cards.add(new SetCardInfo("Curse of Verbosity", 9, Rarity.UNCOMMON, mage.cards.c.CurseOfVerbosity.class));
        cards.add(new SetCardInfo("Curse of Vitality", 3, Rarity.UNCOMMON, mage.cards.c.CurseOfVitality.class));
        cards.add(new SetCardInfo("Herald's Horn", 53, Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Hungry Lynx", 31, Rarity.RARE, mage.cards.h.HungryLynx.class));
        cards.add(new SetCardInfo("Nazahn, Revered Bladesmith", 44, Rarity.MYTHIC, mage.cards.n.NazahnReveredBladesmith.class));
        cards.add(new SetCardInfo("O-Kagachi, Vengeful Kami", 45, Rarity.MYTHIC, mage.cards.o.OKagachiVengefulKami.class));
        cards.add(new SetCardInfo("Patron of the Vein", 20, Rarity.RARE, mage.cards.p.PatronOfTheVein.class));
        cards.add(new SetCardInfo("Qasali Slingers", 33, Rarity.RARE, mage.cards.q.QasaliSlingers.class));
        cards.add(new SetCardInfo("Ramos, Dragon Engine", 55, Rarity.MYTHIC, mage.cards.r.RamosDragonEngine.class));
        cards.add(new SetCardInfo("Scalelord Reckoner", 6, Rarity.RARE, mage.cards.s.ScalelordReckoner.class));
        cards.add(new SetCardInfo("Taigam, Ojutai Master", 46, Rarity.MYTHIC, mage.cards.t.TaigamOjutaiMaster.class));
        cards.add(new SetCardInfo("Taigam, Sidisi's Hand", 47, Rarity.RARE, mage.cards.t.TaigamSidisisHand.class));
        cards.add(new SetCardInfo("Teferi's Protection", 8, Rarity.RARE, mage.cards.t.TeferisProtection.class));
        cards.add(new SetCardInfo("Traverse the Outlands", 34, Rarity.RARE, mage.cards.t.TraverseTheOutlands.class));
        cards.add(new SetCardInfo("Wasitora, Nekoru Queen", 49, Rarity.MYTHIC, mage.cards.w.WasitoraNekoruQueen.class));
        
    }
}
