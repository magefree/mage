/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class Ixalan extends ExpansionSet {

    private static final Ixalan instance = new Ixalan();

    public static Ixalan getInstance() {
        return instance;
    }

    private Ixalan() {
        super("Ixalan", "XLN", ExpansionSet.buildDate(2017, 9, 29), SetType.EXPANSION);
        this.blockName = "Ixalan";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Angrath's Marauders", 132, Rarity.RARE, mage.cards.a.AngrathsMarauders.class));
        cards.add(new SetCardInfo("Bloodcrazed Paladin", 93, Rarity.RARE, mage.cards.b.BloodcrazedPaladin.class));
        cards.add(new SetCardInfo("Burning Sun's Avatar", 135, Rarity.RARE, mage.cards.b.BurningSunsAvatar.class));
        cards.add(new SetCardInfo("Captain Lannery Storm", 136, Rarity.RARE, mage.cards.c.CaptainLanneryStorm.class));
        cards.add(new SetCardInfo("Carnage Tyrant", 179, Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class));
        cards.add(new SetCardInfo("Daring Saboteur", 49, Rarity.RARE, mage.cards.d.DaringSaboteur.class));
        cards.add(new SetCardInfo("Deadeye Tormentor", 98, Rarity.COMMON, mage.cards.d.DeadeyeTormentor.class));
        cards.add(new SetCardInfo("Deadeye Tracker", 99, Rarity.RARE, mage.cards.d.DeadeyeTracker.class));
        cards.add(new SetCardInfo("Deeproot Champion", 185, Rarity.RARE, mage.cards.d.DeeprootChampion.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 252, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Dreamcaller Siren", 54, Rarity.RARE, mage.cards.d.DreamcallerSiren.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 253, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Emperor's Vanguard", 189, Rarity.RARE, mage.cards.e.EmperorsVanguard.class));
        cards.add(new SetCardInfo("Entrancing Melody", 55, Rarity.RARE, mage.cards.e.EntrancingMelody.class));
        cards.add(new SetCardInfo("Gishath, Sun's Avatar", 222, Rarity.MYTHIC, mage.cards.g.GishathSunsAvatar.class));
        cards.add(new SetCardInfo("Glacial Fortress", 255, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Herald of Secret Streams", 59, Rarity.RARE, mage.cards.h.HeraldOfSecretStreams.class));
        cards.add(new SetCardInfo("Jace, Cunning Castaway", 60, Rarity.MYTHIC, mage.cards.j.JaceCunningCastaway.class));
        cards.add(new SetCardInfo("Kumena's Omenspeaker", 196, Rarity.UNCOMMON, mage.cards.k.KumenasOmenspeaker.class));
        cards.add(new SetCardInfo("Old-Growth Dryads", 199, Rarity.RARE, mage.cards.o.OldGrowthDryads.class));
        cards.add(new SetCardInfo("Prosperous Pirates", 69, Rarity.COMMON, mage.cards.p.ProsperousPirates.class));
        cards.add(new SetCardInfo("Queen's Bay Soldier", 115, Rarity.COMMON, mage.cards.q.QueensBaySoldier.class));
        cards.add(new SetCardInfo("Revel in Riches", 117, Rarity.RARE, mage.cards.r.RevelInRiches.class));
        cards.add(new SetCardInfo("Ripjaw Raptor", 203, Rarity.RARE, mage.cards.r.RipjawRaptor.class));
        cards.add(new SetCardInfo("River's Rebuke", 71, Rarity.RARE, mage.cards.r.RiversRebuke.class));
        cards.add(new SetCardInfo("Rootbound Crag", 256, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Rowdy Crew", 159, Rarity.MYTHIC, mage.cards.r.RowdyCrew.class));
        cards.add(new SetCardInfo("Ruin Raider", 118, Rarity.RARE, mage.cards.r.RuinRaider.class));
        cards.add(new SetCardInfo("Shapers' Sanctuary", 206, Rarity.RARE, mage.cards.s.ShapersSanctuary.class));
        cards.add(new SetCardInfo("Sleek Schooner", 247, Rarity.UNCOMMON, mage.cards.s.SleekSchooner.class));
        cards.add(new SetCardInfo("Star of Extinction", 161, Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class));
        cards.add(new SetCardInfo("Sun-Crowned Hunters", 164, Rarity.COMMON, mage.cards.s.SunCrownedHunters.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 257, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Tishana's Wayfinder", 211, Rarity.COMMON, mage.cards.t.TishanasWayfinder.class));
        cards.add(new SetCardInfo("Tishana, Voice of Thunder", 230, Rarity.MYTHIC, mage.cards.t.TishanaVoiceOfThunder.class));
        cards.add(new SetCardInfo("Tocatli Honor Guard", 42, Rarity.RARE, mage.cards.t.TocatliHonorGuard.class));
        cards.add(new SetCardInfo("Treasure Cove", 1250, Rarity.RARE, mage.cards.t.TreasureCove.class));
        cards.add(new SetCardInfo("Treasure Map", 250, Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 258, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Unfriendly Fire", 172, Rarity.COMMON, mage.cards.u.UnfriendlyFire.class));
        cards.add(new SetCardInfo("Vanquisher's Banner", 251, Rarity.RARE, mage.cards.v.VanquishersBanner.class));
        cards.add(new SetCardInfo("Verdant Sun's Avatar", 213, Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class));
        cards.add(new SetCardInfo("Vraska's Contempt", 129, Rarity.RARE, mage.cards.v.VraskasContempt.class));
        cards.add(new SetCardInfo("Waker of the Wilds", 215, Rarity.RARE, mage.cards.w.WakerOfTheWilds.class));
        cards.add(new SetCardInfo("Walk the Plank", 130, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));      
    }
}
