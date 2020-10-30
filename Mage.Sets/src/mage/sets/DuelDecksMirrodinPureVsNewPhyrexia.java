package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/td2
 */
public class DuelDecksMirrodinPureVsNewPhyrexia extends ExpansionSet {

    private static final DuelDecksMirrodinPureVsNewPhyrexia instance = new DuelDecksMirrodinPureVsNewPhyrexia();

    public static DuelDecksMirrodinPureVsNewPhyrexia getInstance() {
        return instance;
    }

    private DuelDecksMirrodinPureVsNewPhyrexia() {
        super("Duel Decks: Mirrodin Pure vs. New Phyrexia", "TD2", ExpansionSet.buildDate(2011, 5, 14), SetType.MAGIC_ONLINE);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Ancient Den", 36, Rarity.COMMON, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Ardent Recruit", 4, Rarity.COMMON, mage.cards.a.ArdentRecruit.class));
        cards.add(new SetCardInfo("Argent Sphinx", 13, Rarity.RARE, mage.cards.a.ArgentSphinx.class));
        cards.add(new SetCardInfo("Arrest", 32, Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Barter in Blood", 76, Rarity.UNCOMMON, mage.cards.b.BarterInBlood.class));
        cards.add(new SetCardInfo("Black Sun's Zenith", 81, Rarity.RARE, mage.cards.b.BlackSunsZenith.class));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 37, Rarity.RARE, mage.cards.b.BlinkmothNexus.class));
        cards.add(new SetCardInfo("Bonesplitter", 20, Rarity.COMMON, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Coastal Tower", 38, Rarity.UNCOMMON, mage.cards.c.CoastalTower.class));
        cards.add(new SetCardInfo("Condemn", 22, Rarity.UNCOMMON, mage.cards.c.Condemn.class));
        cards.add(new SetCardInfo("Contagion Clasp", 67, Rarity.UNCOMMON, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Darksteel Gargoyle", 19, Rarity.UNCOMMON, mage.cards.d.DarksteelGargoyle.class));
        cards.add(new SetCardInfo("Darksteel Sentinel", 16, Rarity.UNCOMMON, mage.cards.d.DarksteelSentinel.class));
        cards.add(new SetCardInfo("Desecration Elemental", 57, Rarity.RARE, mage.cards.d.DesecrationElemental.class));
        cards.add(new SetCardInfo("Diabolic Servitude", 75, Rarity.UNCOMMON, mage.cards.d.DiabolicServitude.class));
        cards.add(new SetCardInfo("Dispatch", 23, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Drooling Groodion", 64, Rarity.UNCOMMON, mage.cards.d.DroolingGroodion.class));
        cards.add(new SetCardInfo("Duplicant", 17, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Exhume", 70, Rarity.COMMON, mage.cards.e.Exhume.class));
        cards.add(new SetCardInfo("Flesh-Eater Imp", 58, Rarity.UNCOMMON, mage.cards.f.FleshEaterImp.class));
        cards.add(new SetCardInfo("Forbidding Watchtower", 39, Rarity.UNCOMMON, mage.cards.f.ForbiddingWatchtower.class));
        cards.add(new SetCardInfo("Forest", 86, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 87, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fume Spitter", 49, Rarity.COMMON, mage.cards.f.FumeSpitter.class));
        cards.add(new SetCardInfo("Glimmerpoint Stag", 12, Rarity.UNCOMMON, mage.cards.g.GlimmerpointStag.class));
        cards.add(new SetCardInfo("Gold Myr", 6, Rarity.COMMON, mage.cards.g.GoldMyr.class));
        cards.add(new SetCardInfo("Grafted Exoskeleton", 74, Rarity.UNCOMMON, mage.cards.g.GraftedExoskeleton.class));
        cards.add(new SetCardInfo("Grand Architect", 10, Rarity.RARE, mage.cards.g.GrandArchitect.class));
        cards.add(new SetCardInfo("Innocent Blood", 66, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Island", 45, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 46, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 47, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kuldotha Forgemaster", 14, Rarity.RARE, mage.cards.k.KuldothaForgemaster.class));
        cards.add(new SetCardInfo("Lumengrid Gargoyle", 18, Rarity.UNCOMMON, mage.cards.l.LumengridGargoyle.class));
        cards.add(new SetCardInfo("Memnite", 2, Rarity.UNCOMMON, mage.cards.m.Memnite.class));
        cards.add(new SetCardInfo("Mitotic Slime", 61, Rarity.RARE, mage.cards.m.MitoticSlime.class));
        cards.add(new SetCardInfo("Morbid Plunder", 72, Rarity.COMMON, mage.cards.m.MorbidPlunder.class));
        cards.add(new SetCardInfo("Mortarpod", 68, Rarity.UNCOMMON, mage.cards.m.Mortarpod.class));
        cards.add(new SetCardInfo("Myr Retriever", 7, Rarity.UNCOMMON, mage.cards.m.MyrRetriever.class));
        cards.add(new SetCardInfo("Myr Sire", 50, Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Necroskitter", 55, Rarity.RARE, mage.cards.n.Necroskitter.class));
        cards.add(new SetCardInfo("Neurok Stealthsuit", 25, Rarity.COMMON, mage.cards.n.NeurokStealthsuit.class));
        cards.add(new SetCardInfo("One Dozen Eyes", 80, Rarity.UNCOMMON, mage.cards.o.OneDozenEyes.class));
        cards.add(new SetCardInfo("Phyrexian Altar", 71, Rarity.RARE, mage.cards.p.PhyrexianAltar.class));
        cards.add(new SetCardInfo("Phyrexian Ghoul", 53, Rarity.COMMON, mage.cards.p.PhyrexianGhoul.class));
        cards.add(new SetCardInfo("Phyrexian Juggernaut", 63, Rarity.UNCOMMON, mage.cards.p.PhyrexianJuggernaut.class));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", 48, Rarity.MYTHIC, mage.cards.p.PhyrexianPlaguelord.class));
        cards.add(new SetCardInfo("Plague Myr", 51, Rarity.UNCOMMON, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Plaguemaw Beast", 62, Rarity.UNCOMMON, mage.cards.p.PlaguemawBeast.class));
        cards.add(new SetCardInfo("Plains", 42, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 43, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 44, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Platinum Angel", 1, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Protean Hulk", 65, Rarity.RARE, mage.cards.p.ProteanHulk.class));
        cards.add(new SetCardInfo("Purge", 28, Rarity.UNCOMMON, mage.cards.p.Purge.class));
        cards.add(new SetCardInfo("Putrefy", 73, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Reprocess", 77, Rarity.RARE, mage.cards.r.Reprocess.class));
        cards.add(new SetCardInfo("Rot Wolf", 54, Rarity.COMMON, mage.cards.r.RotWolf.class));
        cards.add(new SetCardInfo("Seat of the Synod", 40, Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class));
        cards.add(new SetCardInfo("Silent Arbiter", 11, Rarity.RARE, mage.cards.s.SilentArbiter.class));
        cards.add(new SetCardInfo("Silver Myr", 8, Rarity.COMMON, mage.cards.s.SilverMyr.class));
        cards.add(new SetCardInfo("Slagwurm Armor", 21, Rarity.COMMON, mage.cards.s.SlagwurmArmor.class));
        cards.add(new SetCardInfo("Soul Snuffers", 60, Rarity.UNCOMMON, mage.cards.s.SoulSnuffers.class));
        cards.add(new SetCardInfo("Spawning Pit", 69, Rarity.UNCOMMON, mage.cards.s.SpawningPit.class));
        cards.add(new SetCardInfo("Spire Serpent", 15, Rarity.COMMON, mage.cards.s.SpireSerpent.class));
        cards.add(new SetCardInfo("Spread the Sickness", 79, Rarity.COMMON, mage.cards.s.SpreadTheSickness.class));
        cards.add(new SetCardInfo("Steel Wall", 3, Rarity.COMMON, mage.cards.s.SteelWall.class));
        cards.add(new SetCardInfo("Steelshaper's Gift", 24, Rarity.UNCOMMON, mage.cards.s.SteelshapersGift.class));
        cards.add(new SetCardInfo("Stoic Rebuttal", 34, Rarity.COMMON, mage.cards.s.StoicRebuttal.class));
        cards.add(new SetCardInfo("Swamp", 83, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 84, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 85, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symbiotic Elf", 59, Rarity.COMMON, mage.cards.s.SymbioticElf.class));
        cards.add(new SetCardInfo("Tainted Wood", 82, Rarity.UNCOMMON, mage.cards.t.TaintedWood.class));
        cards.add(new SetCardInfo("Talisman of Progress", 26, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 41, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Test of Faith", 29, Rarity.UNCOMMON, mage.cards.t.TestOfFaith.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 33, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thunderstaff", 31, Rarity.UNCOMMON, mage.cards.t.Thunderstaff.class));
        cards.add(new SetCardInfo("Trinket Mage", 9, Rarity.UNCOMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Triumph of the Hordes", 78, Rarity.UNCOMMON, mage.cards.t.TriumphOfTheHordes.class));
        cards.add(new SetCardInfo("Turn the Tide", 30, Rarity.COMMON, mage.cards.t.TurnTheTide.class));
        cards.add(new SetCardInfo("Vedalken Certarch", 5, Rarity.COMMON, mage.cards.v.VedalkenCertarch.class));
        cards.add(new SetCardInfo("Viridian Claw", 27, Rarity.UNCOMMON, mage.cards.v.ViridianClaw.class));
        cards.add(new SetCardInfo("Viridian Corrupter", 56, Rarity.UNCOMMON, mage.cards.v.ViridianCorrupter.class));
        cards.add(new SetCardInfo("Viridian Emissary", 52, Rarity.COMMON, mage.cards.v.ViridianEmissary.class));
        cards.add(new SetCardInfo("White Sun's Zenith", 35, Rarity.RARE, mage.cards.w.WhiteSunsZenith.class));
     }
}
