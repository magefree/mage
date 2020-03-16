package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/phou
 */
public class HourOfDevastationPromos extends ExpansionSet {

    private static final HourOfDevastationPromos instance = new HourOfDevastationPromos();

    public static HourOfDevastationPromos getInstance() {
        return instance;
    }

    private HourOfDevastationPromos() {
        super("Hour of Devastation Promos", "PHOU", ExpansionSet.buildDate(2017, 7, 14), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abandoned Sarcophagus", "158s", Rarity.RARE, mage.cards.a.AbandonedSarcophagus.class));
        cards.add(new SetCardInfo("Abrade", 83, Rarity.UNCOMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Adorned Pouncer", 2, Rarity.RARE, mage.cards.a.AdornedPouncer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adorned Pouncer", "2s", Rarity.RARE, mage.cards.a.AdornedPouncer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ammit Eternal", "57s", Rarity.RARE, mage.cards.a.AmmitEternal.class));
        cards.add(new SetCardInfo("Angel of Condemnation", "3s", Rarity.RARE, mage.cards.a.AngelOfCondemnation.class));
        cards.add(new SetCardInfo("Apocalypse Demon", "58s", Rarity.RARE, mage.cards.a.ApocalypseDemon.class));
        cards.add(new SetCardInfo("Bontu's Last Reckoning", "60s", Rarity.RARE, mage.cards.b.BontusLastReckoning.class));
        cards.add(new SetCardInfo("Champion of Wits", "31s", Rarity.RARE, mage.cards.c.ChampionOfWits.class));
        cards.add(new SetCardInfo("Chaos Maw", "87s", Rarity.RARE, mage.cards.c.ChaosMaw.class));
        cards.add(new SetCardInfo("Crested Sunmare", "6s", Rarity.MYTHIC, mage.cards.c.CrestedSunmare.class));
        cards.add(new SetCardInfo("Djeru, With Eyes Open", "10s", Rarity.RARE, mage.cards.d.DjeruWithEyesOpen.class));
        cards.add(new SetCardInfo("Dreamstealer", "63s", Rarity.RARE, mage.cards.d.Dreamstealer.class));
        cards.add(new SetCardInfo("Driven // Despair", "157s", Rarity.RARE, mage.cards.d.DrivenDespair.class));
        cards.add(new SetCardInfo("Earthshaker Khenra", "90s", Rarity.RARE, mage.cards.e.EarthshakerKhenra.class));
        cards.add(new SetCardInfo("Endless Sands", "176s", Rarity.RARE, mage.cards.e.EndlessSands.class));
        cards.add(new SetCardInfo("Fraying Sanity", "35s", Rarity.RARE, mage.cards.f.FrayingSanity.class));
        cards.add(new SetCardInfo("God-Pharaoh's Gift", "161s", Rarity.RARE, mage.cards.g.GodPharaohsGift.class));
        cards.add(new SetCardInfo("Grind // Dust", "155s", Rarity.RARE, mage.cards.g.GrindDust.class));
        cards.add(new SetCardInfo("Hazoret's Undying Fury", "96s", Rarity.RARE, mage.cards.h.HazoretsUndyingFury.class));
        cards.add(new SetCardInfo("Hollow One", "163s", Rarity.RARE, mage.cards.h.HollowOne.class));
        cards.add(new SetCardInfo("Hostile Desert", "178s", Rarity.RARE, mage.cards.h.HostileDesert.class));
        cards.add(new SetCardInfo("Hour of Devastation", "97s", Rarity.RARE, mage.cards.h.HourOfDevastation.class));
        cards.add(new SetCardInfo("Hour of Eternity", "36s", Rarity.RARE, mage.cards.h.HourOfEternity.class));
        cards.add(new SetCardInfo("Hour of Glory", "65s", Rarity.RARE, mage.cards.h.HourOfGlory.class));
        cards.add(new SetCardInfo("Hour of Promise", "120s", Rarity.RARE, mage.cards.h.HourOfPromise.class));
        cards.add(new SetCardInfo("Hour of Revelation", "15s", Rarity.RARE, mage.cards.h.HourOfRevelation.class));
        cards.add(new SetCardInfo("Imminent Doom", "98s", Rarity.RARE, mage.cards.i.ImminentDoom.class));
        cards.add(new SetCardInfo("Kefnet's Last Word", "39s", Rarity.RARE, mage.cards.k.KefnetsLastWord.class));
        cards.add(new SetCardInfo("Leave // Chance", "153s", Rarity.RARE, mage.cards.l.LeaveChance.class));
        cards.add(new SetCardInfo("Majestic Myriarch", "122s", Rarity.MYTHIC, mage.cards.m.MajesticMyriarch.class));
        cards.add(new SetCardInfo("Mirage Mirror", "165p", Rarity.RARE, mage.cards.m.MirageMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirage Mirror", "165s", Rarity.RARE, mage.cards.m.MirageMirror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neheb, the Eternal", "104s", Rarity.MYTHIC, mage.cards.n.NehebTheEternal.class));
        cards.add(new SetCardInfo("Nicol Bolas, God-Pharaoh", "140s", Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharaoh.class));
        cards.add(new SetCardInfo("Nimble Obstructionist", "40s", Rarity.RARE, mage.cards.n.NimbleObstructionist.class));
        cards.add(new SetCardInfo("Oketra's Last Mercy", "18s", Rarity.RARE, mage.cards.o.OketrasLastMercy.class));
        cards.add(new SetCardInfo("Overwhelming Splendor", "19s", Rarity.MYTHIC, mage.cards.o.OverwhelmingSplendor.class));
        cards.add(new SetCardInfo("Pride Sovereign", "126s", Rarity.RARE, mage.cards.p.PrideSovereign.class));
        cards.add(new SetCardInfo("Ramunap Excavator", 129, Rarity.RARE, mage.cards.r.RamunapExcavator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ramunap Excavator", "129s", Rarity.RARE, mage.cards.r.RamunapExcavator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ramunap Hydra", "130s", Rarity.RARE, mage.cards.r.RamunapHydra.class));
        cards.add(new SetCardInfo("Razaketh, the Foulblooded", "73s", Rarity.MYTHIC, mage.cards.r.RazakethTheFoulblooded.class));
        cards.add(new SetCardInfo("Reason // Believe", "154s", Rarity.RARE, mage.cards.r.ReasonBelieve.class));
        cards.add(new SetCardInfo("Refuse // Cooperate", "156s", Rarity.RARE, mage.cards.r.RefuseCooperate.class));
        cards.add(new SetCardInfo("Resilient Khenra", "131s", Rarity.RARE, mage.cards.r.ResilientKhenra.class));
        cards.add(new SetCardInfo("Rhonas's Last Stand", "132s", Rarity.RARE, mage.cards.r.RhonassLastStand.class));
        cards.add(new SetCardInfo("Samut, the Tested", "144s", Rarity.MYTHIC, mage.cards.s.SamutTheTested.class));
        cards.add(new SetCardInfo("Scavenger Grounds", "182s", Rarity.RARE, mage.cards.s.ScavengerGrounds.class));
        cards.add(new SetCardInfo("Solemnity", "22s", Rarity.RARE, mage.cards.s.Solemnity.class));
        cards.add(new SetCardInfo("Swarm Intelligence", "50s", Rarity.RARE, mage.cards.s.SwarmIntelligence.class));
        cards.add(new SetCardInfo("The Locust God", "139s", Rarity.MYTHIC, mage.cards.t.TheLocustGod.class));
        cards.add(new SetCardInfo("The Scarab God", "145s", Rarity.MYTHIC, mage.cards.t.TheScarabGod.class));
        cards.add(new SetCardInfo("The Scorpion God", "146s", Rarity.MYTHIC, mage.cards.t.TheScorpionGod.class));
        cards.add(new SetCardInfo("Torment of Hailfire", "77s", Rarity.RARE, mage.cards.t.TormentOfHailfire.class));
        cards.add(new SetCardInfo("Uncage the Menagerie", "137s", Rarity.MYTHIC, mage.cards.u.UncageTheMenagerie.class));
        cards.add(new SetCardInfo("Unesh, Criosphinx Sovereign", "52s", Rarity.MYTHIC, mage.cards.u.UneshCriosphinxSovereign.class));
        cards.add(new SetCardInfo("Wildfire Eternal", 109, Rarity.RARE, mage.cards.w.WildfireEternal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildfire Eternal", "109s", Rarity.RARE, mage.cards.w.WildfireEternal.class, NON_FULL_USE_VARIOUS));
     }
}
