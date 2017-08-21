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

        cards.add(new SetCardInfo("Arahbo, Roar of the World", 35, Rarity.MYTHIC, mage.cards.a.ArahboRoarOfTheWorld.class));
        cards.add(new SetCardInfo("Balan, Wandering Knight", 2, Rarity.RARE, mage.cards.b.BalanWanderingKnight.class));
        cards.add(new SetCardInfo("Bloodforged War Axe", 50, Rarity.RARE, mage.cards.b.BloodforgedWarAxe.class));
        cards.add(new SetCardInfo("Bloodline Necromancer", 14, Rarity.UNCOMMON, mage.cards.b.BloodlineNecromancer.class));
        cards.add(new SetCardInfo("Bloodsworn Steward", 22, Rarity.RARE, mage.cards.b.BloodswornSteward.class));
        cards.add(new SetCardInfo("Boneyard Scourge", 15, Rarity.RARE, mage.cards.b.BoneyardScourge.class));
        cards.add(new SetCardInfo("Crimson Honor Guard", 23, Rarity.RARE, mage.cards.c.CrimsonHonorGuard.class));
        cards.add(new SetCardInfo("Curse of Bounty", 30, Rarity.UNCOMMON, mage.cards.c.CurseOfBounty.class));
        cards.add(new SetCardInfo("Curse of Disturbance", 16, Rarity.UNCOMMON, mage.cards.c.CurseOfDisturbance.class));
        cards.add(new SetCardInfo("Curse of Opulence", 24, Rarity.UNCOMMON, mage.cards.c.CurseOfOpulence.class));
        cards.add(new SetCardInfo("Curse of Verbosity", 9, Rarity.UNCOMMON, mage.cards.c.CurseOfVerbosity.class));
        cards.add(new SetCardInfo("Curse of Vitality", 3, Rarity.UNCOMMON, mage.cards.c.CurseOfVitality.class));
        cards.add(new SetCardInfo("Disrupt Decorum", 25, Rarity.RARE, mage.cards.d.DisruptDecorum.class));
        cards.add(new SetCardInfo("Edgar Markov", 36, Rarity.MYTHIC, mage.cards.e.EdgarMarkov.class));
        cards.add(new SetCardInfo("Fortunate Few", 4, Rarity.RARE, mage.cards.f.FortunateFew.class));
        cards.add(new SetCardInfo("Fractured Identity", 37, Rarity.RARE, mage.cards.f.FracturedIdentity.class));
        cards.add(new SetCardInfo("Galecaster Colossus", 10, Rarity.RARE, mage.cards.g.GalecasterColossus.class));
        cards.add(new SetCardInfo("Hammer of Nazahn", 51, Rarity.RARE, mage.cards.h.HammerOfNazahn.class));
        cards.add(new SetCardInfo("Herald's Horn", 53, Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Heirloom Blade", 52, Rarity.UNCOMMON, mage.cards.h.HeirloomBlade.class));
        cards.add(new SetCardInfo("Hungry Lynx", 31, Rarity.RARE, mage.cards.h.HungryLynx.class));
        cards.add(new SetCardInfo("Inalla, Archmage Ritualist", 38, Rarity.MYTHIC, mage.cards.i.InallaArchmageRitualist.class));
        cards.add(new SetCardInfo("Izzet Chemister", 26, Rarity.RARE, mage.cards.i.IzzetChemister.class));
        cards.add(new SetCardInfo("Kess, Dissident Mage", 39, Rarity.MYTHIC, mage.cards.k.KessDissidentMage.class));
        cards.add(new SetCardInfo("Kheru Mind-Eater", 17, Rarity.RARE, mage.cards.k.KheruMindEater.class));
        cards.add(new SetCardInfo("Kindred Boon", 5, Rarity.RARE, mage.cards.k.KindredBoon.class));
        cards.add(new SetCardInfo("Kindred Charge", 27, Rarity.RARE, mage.cards.k.KindredCharge.class));
        cards.add(new SetCardInfo("Kindred Discovery", 11, Rarity.RARE, mage.cards.k.KindredDiscovery.class));
        cards.add(new SetCardInfo("Kindred Dominance", 18, Rarity.RARE, mage.cards.k.KindredDominance.class));
        cards.add(new SetCardInfo("Kindred Summons", 32, Rarity.RARE, mage.cards.k.KindredSummons.class));
        cards.add(new SetCardInfo("Licia, Sanguine Tribune", 40, Rarity.MYTHIC, mage.cards.l.LiciaSanguineTribune.class));
        cards.add(new SetCardInfo("Magus of the Mind", 12, Rarity.RARE, mage.cards.m.MagusOfTheMind.class));
        cards.add(new SetCardInfo("Mairsil, the Pretender", 41, Rarity.MYTHIC, mage.cards.m.MairsilThePretender.class));
        cards.add(new SetCardInfo("Mathas, Fiend Seeker", 42, Rarity.MYTHIC, mage.cards.m.MathasFiendSeeker.class));
        cards.add(new SetCardInfo("Mirri, Weatherlight Duelist", 43, Rarity.MYTHIC, mage.cards.m.MirriWeatherlightDuelist.class));
        cards.add(new SetCardInfo("Mirror of the Forebears", 54, Rarity.UNCOMMON, mage.cards.m.MirrorOfTheForebears.class));
        cards.add(new SetCardInfo("Nazahn, Revered Bladesmith", 44, Rarity.MYTHIC, mage.cards.n.NazahnReveredBladesmith.class));
        cards.add(new SetCardInfo("O-Kagachi, Vengeful Kami", 45, Rarity.MYTHIC, mage.cards.o.OKagachiVengefulKami.class));
        cards.add(new SetCardInfo("Path of Ancestry", 56, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Patron of the Vein", 20, Rarity.RARE, mage.cards.p.PatronOfTheVein.class));
        cards.add(new SetCardInfo("Qasali Slingers", 33, Rarity.RARE, mage.cards.q.QasaliSlingers.class));
        cards.add(new SetCardInfo("Ramos, Dragon Engine", 55, Rarity.MYTHIC, mage.cards.r.RamosDragonEngine.class));
        cards.add(new SetCardInfo("Scalelord Reckoner", 6, Rarity.RARE, mage.cards.s.ScalelordReckoner.class));
        cards.add(new SetCardInfo("Shifting Shadow", 28, Rarity.RARE, mage.cards.s.ShiftingShadow.class));
        cards.add(new SetCardInfo("Taigam, Ojutai Master", 46, Rarity.MYTHIC, mage.cards.t.TaigamOjutaiMaster.class));
        cards.add(new SetCardInfo("Taigam, Sidisi's Hand", 47, Rarity.RARE, mage.cards.t.TaigamSidisisHand.class));
        cards.add(new SetCardInfo("Teferi's Protection", 8, Rarity.RARE, mage.cards.t.TeferisProtection.class));
        cards.add(new SetCardInfo("Territorial Hellkite", 29, Rarity.RARE, mage.cards.t.TerritorialHellkite.class));
        cards.add(new SetCardInfo("The Ur-Dragon", 48, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class));
        cards.add(new SetCardInfo("Traverse the Outlands", 34, Rarity.RARE, mage.cards.t.TraverseTheOutlands.class));
        cards.add(new SetCardInfo("Wasitora, Nekoru Queen", 49, Rarity.MYTHIC, mage.cards.w.WasitoraNekoruQueen.class));
    }
}
