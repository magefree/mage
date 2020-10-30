package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pori
 */
public class MagicOriginsPromos extends ExpansionSet {

    private static final MagicOriginsPromos instance = new MagicOriginsPromos();

    public static MagicOriginsPromos getInstance() {
        return instance;
    }

    private MagicOriginsPromos() {
        super("Magic Origins Promos", "PORI", ExpansionSet.buildDate(2015, 7, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abbot of Keral Keep", "127s", Rarity.RARE, mage.cards.a.AbbotOfKeralKeep.class));
        cards.add(new SetCardInfo("Alhammarret, High Arbiter", 43, Rarity.RARE, mage.cards.a.AlhammarretHighArbiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Alhammarret, High Arbiter", "43s", Rarity.RARE, mage.cards.a.AlhammarretHighArbiter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Ignition", "137s", Rarity.RARE, mage.cards.c.ChandrasIgnition.class));
        cards.add(new SetCardInfo("Chandra, Fire of Kaladesh", "135s", Rarity.MYTHIC, mage.cards.c.ChandraFireOfKaladesh.class));
        cards.add(new SetCardInfo("Chandra, Roaring Flame", "135s", Rarity.MYTHIC, mage.cards.c.ChandraRoaringFlame.class));
        cards.add(new SetCardInfo("Conclave Naturalists", 171, Rarity.UNCOMMON, mage.cards.c.ConclaveNaturalists.class));
        cards.add(new SetCardInfo("Dark Petition", "90s", Rarity.RARE, mage.cards.d.DarkPetition.class));
        cards.add(new SetCardInfo("Despoiler of Souls", "93s", Rarity.RARE, mage.cards.d.DespoilerOfSouls.class));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", 172, Rarity.RARE, mage.cards.d.DwynenGiltLeafDaen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", "172s", Rarity.RARE, mage.cards.d.DwynenGiltLeafDaen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embermaw Hellion", "141s", Rarity.RARE, mage.cards.e.EmbermawHellion.class));
        cards.add(new SetCardInfo("Evolutionary Leap", "176s", Rarity.RARE, mage.cards.e.EvolutionaryLeap.class));
        cards.add(new SetCardInfo("Exquisite Firecraft", "143s", Rarity.RARE, mage.cards.e.ExquisiteFirecraft.class));
        cards.add(new SetCardInfo("Gaea's Revenge", "177s", Rarity.RARE, mage.cards.g.GaeasRevenge.class));
        cards.add(new SetCardInfo("Gideon's Phalanx", "14s", Rarity.RARE, mage.cards.g.GideonsPhalanx.class));
        cards.add(new SetCardInfo("Gideon, Battle-Forged", "23s", Rarity.MYTHIC, mage.cards.g.GideonBattleForged.class));
        cards.add(new SetCardInfo("Gilt-Leaf Winnower", "99s", Rarity.RARE, mage.cards.g.GiltLeafWinnower.class));
        cards.add(new SetCardInfo("Goblin Piledriver", "151s", Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Graveblade Marauder", "101s", Rarity.RARE, mage.cards.g.GravebladeMarauder.class));
        cards.add(new SetCardInfo("Harbinger of the Tides", "58s", Rarity.RARE, mage.cards.h.HarbingerOfTheTides.class));
        cards.add(new SetCardInfo("Hixus, Prison Warden", 19, Rarity.RARE, mage.cards.h.HixusPrisonWarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hixus, Prison Warden", "19s", Rarity.RARE, mage.cards.h.HixusPrisonWarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Honored Hierarch", "182s", Rarity.RARE, mage.cards.h.HonoredHierarch.class));
        cards.add(new SetCardInfo("Jace, Telepath Unbound", "60s", Rarity.MYTHIC, mage.cards.j.JaceTelepathUnbound.class));
        cards.add(new SetCardInfo("Jace, Vryn's Prodigy", "60s", Rarity.MYTHIC, mage.cards.j.JaceVrynsProdigy.class));
        cards.add(new SetCardInfo("Knight of the White Orchid", "21s", Rarity.RARE, mage.cards.k.KnightOfTheWhiteOrchid.class));
        cards.add(new SetCardInfo("Kothophed, Soul Hoarder", 104, Rarity.RARE, mage.cards.k.KothophedSoulHoarder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kothophed, Soul Hoarder", "104s", Rarity.RARE, mage.cards.k.KothophedSoulHoarder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kytheon's Irregulars", "24s", Rarity.RARE, mage.cards.k.KytheonsIrregulars.class));
        cards.add(new SetCardInfo("Kytheon, Hero of Akros", "23s", Rarity.MYTHIC, mage.cards.k.KytheonHeroOfAkros.class));
        cards.add(new SetCardInfo("Languish", 105, Rarity.RARE, mage.cards.l.Languish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Languish", "105s", Rarity.RARE, mage.cards.l.Languish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana, Defiant Necromancer", "106s", Rarity.MYTHIC, mage.cards.l.LilianaDefiantNecromancer.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", "106s", Rarity.MYTHIC, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Managorger Hydra", "186s", Rarity.RARE, mage.cards.m.ManagorgerHydra.class));
        cards.add(new SetCardInfo("Mizzium Meddler", 64, Rarity.RARE, mage.cards.m.MizziumMeddler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mizzium Meddler", "64s", Rarity.RARE, mage.cards.m.MizziumMeddler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa's Revelation", "191s", Rarity.RARE, mage.cards.n.NissasRevelation.class));
        cards.add(new SetCardInfo("Nissa, Sage Animist", "189s", Rarity.MYTHIC, mage.cards.n.NissaSageAnimist.class));
        cards.add(new SetCardInfo("Nissa, Vastwood Seer", "189s", Rarity.MYTHIC, mage.cards.n.NissaVastwoodSeer.class));
        cards.add(new SetCardInfo("Outland Colossus", "193s", Rarity.RARE, mage.cards.o.OutlandColossus.class));
        cards.add(new SetCardInfo("Pia and Kiran Nalaar", 157, Rarity.RARE, mage.cards.p.PiaAndKiranNalaar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pia and Kiran Nalaar", "157s", Rarity.RARE, mage.cards.p.PiaAndKiranNalaar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Priest of the Blood Rite", "112s", Rarity.RARE, mage.cards.p.PriestOfTheBloodRite.class));
        cards.add(new SetCardInfo("Relic Seeker", 29, Rarity.RARE, mage.cards.r.RelicSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relic Seeker", "29s", Rarity.RARE, mage.cards.r.RelicSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scab-Clan Berserker", "160s", Rarity.RARE, mage.cards.s.ScabClanBerserker.class));
        cards.add(new SetCardInfo("Soulblade Djinn", "75s", Rarity.RARE, mage.cards.s.SoulbladeDjinn.class));
        cards.add(new SetCardInfo("Talent of the Telepath", "78s", Rarity.RARE, mage.cards.t.TalentOfTheTelepath.class));
        cards.add(new SetCardInfo("Thopter Spy Network", "79s", Rarity.RARE, mage.cards.t.ThopterSpyNetwork.class));
        cards.add(new SetCardInfo("Tragic Arrogance", "38s", Rarity.RARE, mage.cards.t.TragicArrogance.class));
        cards.add(new SetCardInfo("Vryn Wingmare", "40s", Rarity.RARE, mage.cards.v.VrynWingmare.class));
        cards.add(new SetCardInfo("Willbreaker", "84s", Rarity.RARE, mage.cards.w.Willbreaker.class));
     }
}
