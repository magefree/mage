package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pgrn
 */
public class GuildsOfRavnicaPromos extends ExpansionSet {

    private static final GuildsOfRavnicaPromos instance = new GuildsOfRavnicaPromos();

    public static GuildsOfRavnicaPromos getInstance() {
        return instance;
    }

    private GuildsOfRavnicaPromos() {
        super("Guilds of Ravnica Promos", "PGRN", ExpansionSet.buildDate(2018, 10, 5), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arclight Phoenix", "91p", Rarity.MYTHIC, mage.cards.a.ArclightPhoenix.class));
        cards.add(new SetCardInfo("Assassin's Trophy", "152p", Rarity.RARE, mage.cards.a.AssassinsTrophy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assassin's Trophy", "152s", Rarity.RARE, mage.cards.a.AssassinsTrophy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assure // Assemble", "221s", Rarity.RARE, mage.cards.a.AssureAssemble.class));
        cards.add(new SetCardInfo("Aurelia, Exemplar of Justice", "153p", Rarity.MYTHIC, mage.cards.a.AureliaExemplarOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aurelia, Exemplar of Justice", "153s", Rarity.MYTHIC, mage.cards.a.AureliaExemplarOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beast Whisperer", "123p", Rarity.RARE, mage.cards.b.BeastWhisperer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beast Whisperer", "123s", Rarity.RARE, mage.cards.b.BeastWhisperer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Operative", "63s", Rarity.RARE, mage.cards.b.BloodOperative.class));
        cards.add(new SetCardInfo("Boros Challenger", 156, Rarity.UNCOMMON, mage.cards.b.BorosChallenger.class));
        cards.add(new SetCardInfo("Bounty of Might", "124s", Rarity.RARE, mage.cards.b.BountyOfMight.class));
        cards.add(new SetCardInfo("Chromatic Lantern", "233p", Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Citywide Bust", "4s", Rarity.RARE, mage.cards.c.CitywideBust.class));
        cards.add(new SetCardInfo("Conclave Tribunal", 6, Rarity.UNCOMMON, mage.cards.c.ConclaveTribunal.class));
        cards.add(new SetCardInfo("Connive // Concoct", "222s", Rarity.RARE, mage.cards.c.ConniveConcoct.class));
        cards.add(new SetCardInfo("Dawn of Hope", "8s", Rarity.RARE, mage.cards.d.DawnOfHope.class));
        cards.add(new SetCardInfo("Deafening Clarion", "165s", Rarity.RARE, mage.cards.d.DeafeningClarion.class));
        cards.add(new SetCardInfo("Doom Whisperer", "69p", Rarity.MYTHIC, mage.cards.d.DoomWhisperer.class));
        cards.add(new SetCardInfo("Emmara, Soul of the Accord", 168, Rarity.RARE, mage.cards.e.EmmaraSoulOfTheAccord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emmara, Soul of the Accord", "168s", Rarity.RARE, mage.cards.e.EmmaraSoulOfTheAccord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Etrata, the Silencer", "170s", Rarity.RARE, mage.cards.e.EtrataTheSilencer.class));
        cards.add(new SetCardInfo("Expansion // Explosion", "224p", Rarity.RARE, mage.cards.e.ExpansionExplosion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expansion // Explosion", "224s", Rarity.RARE, mage.cards.e.ExpansionExplosion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Experimental Frenzy", "99p", Rarity.RARE, mage.cards.e.ExperimentalFrenzy.class));
        cards.add(new SetCardInfo("Find // Finality", "225s", Rarity.RARE, mage.cards.f.FindFinality.class));
        cards.add(new SetCardInfo("Firemind's Research", 171, Rarity.RARE, mage.cards.f.FiremindsResearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Firemind's Research", "171s", Rarity.RARE, mage.cards.f.FiremindsResearch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruesome Menagerie", "71s", Rarity.RARE, mage.cards.g.GruesomeMenagerie.class));
        cards.add(new SetCardInfo("Hatchery Spider", "132s", Rarity.RARE, mage.cards.h.HatcherySpider.class));
        cards.add(new SetCardInfo("Ionize", "179p", Rarity.RARE, mage.cards.i.Ionize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ionize", "179s", Rarity.RARE, mage.cards.i.Ionize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izoni, Thousand-Eyed", "180s", Rarity.RARE, mage.cards.i.IzoniThousandEyed.class));
        cards.add(new SetCardInfo("Knight of Autumn", "183p", Rarity.RARE, mage.cards.k.KnightOfAutumn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knight of Autumn", "183s", Rarity.RARE, mage.cards.k.KnightOfAutumn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lazav, the Multifarious", "184s", Rarity.MYTHIC, mage.cards.l.LazavTheMultifarious.class));
        cards.add(new SetCardInfo("Legion Warboss", "109s", Rarity.RARE, mage.cards.l.LegionWarboss.class));
        cards.add(new SetCardInfo("Light of the Legion", "19s", Rarity.RARE, mage.cards.l.LightOfTheLegion.class));
        cards.add(new SetCardInfo("Mausoleum Secrets", "75s", Rarity.RARE, mage.cards.m.MausoleumSecrets.class));
        cards.add(new SetCardInfo("Midnight Reaper", "77p", Rarity.RARE, mage.cards.m.MidnightReaper.class));
        cards.add(new SetCardInfo("Mission Briefing", "44s", Rarity.RARE, mage.cards.m.MissionBriefing.class));
        cards.add(new SetCardInfo("Narcomoeba", "47s", Rarity.RARE, mage.cards.n.Narcomoeba.class));
        cards.add(new SetCardInfo("Necrotic Wound", 79, Rarity.UNCOMMON, mage.cards.n.NecroticWound.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Parun", "192p", Rarity.RARE, mage.cards.n.NivMizzetParun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niv-Mizzet, Parun", "192s", Rarity.RARE, mage.cards.n.NivMizzetParun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", "253p", Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Quasiduplicate", "51s", Rarity.RARE, mage.cards.q.Quasiduplicate.class));
        cards.add(new SetCardInfo("Response // Resurgence", "229s", Rarity.RARE, mage.cards.r.ResponseResurgence.class));
        cards.add(new SetCardInfo("Risk Factor", "113p", Rarity.RARE, mage.cards.r.RiskFactor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Risk Factor", "113s", Rarity.RARE, mage.cards.r.RiskFactor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ritual of Soot", "84s", Rarity.RARE, mage.cards.r.RitualOfSoot.class));
        cards.add(new SetCardInfo("Runaway Steam-Kin", "115p", Rarity.RARE, mage.cards.r.RunawaySteamKin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runaway Steam-Kin", "115s", Rarity.RARE, mage.cards.r.RunawaySteamKin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", "254p", Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Sinister Sabotage", 54, Rarity.UNCOMMON, mage.cards.s.SinisterSabotage.class));
        cards.add(new SetCardInfo("Steam Vents", "257p", Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Swiftblade Vindicator", "203s", Rarity.RARE, mage.cards.s.SwiftbladeVindicator.class));
        cards.add(new SetCardInfo("Tajic, Legion's Edge", "204s", Rarity.RARE, mage.cards.t.TajicLegionsEdge.class));
        cards.add(new SetCardInfo("Temple Garden", "258p", Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Thief of Sanity", "205p", Rarity.RARE, mage.cards.t.ThiefOfSanity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thief of Sanity", "205s", Rarity.RARE, mage.cards.t.ThiefOfSanity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thought Erasure", 206, Rarity.UNCOMMON, mage.cards.t.ThoughtErasure.class));
        cards.add(new SetCardInfo("Thousand-Year Storm", "207s", Rarity.MYTHIC, mage.cards.t.ThousandYearStorm.class));
        cards.add(new SetCardInfo("Trostani Discordant", "208s", Rarity.MYTHIC, mage.cards.t.TrostaniDiscordant.class));
        cards.add(new SetCardInfo("Underrealm Lich", "211s", Rarity.MYTHIC, mage.cards.u.UnderrealmLich.class));
        cards.add(new SetCardInfo("Venerated Loxodon", "30s", Rarity.RARE, mage.cards.v.VeneratedLoxodon.class));
        cards.add(new SetCardInfo("Vivid Revival", "148s", Rarity.RARE, mage.cards.v.VividRevival.class));
        cards.add(new SetCardInfo("Watery Grave", "259p", Rarity.RARE, mage.cards.w.WateryGrave.class));
     }
}
