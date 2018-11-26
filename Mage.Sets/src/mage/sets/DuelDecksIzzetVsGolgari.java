
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksIzzetVsGolgari extends ExpansionSet {

    private static final DuelDecksIzzetVsGolgari instance = new DuelDecksIzzetVsGolgari();

    public static DuelDecksIzzetVsGolgari getInstance() {
        return instance;
    }

    private DuelDecksIzzetVsGolgari() {
        super("Duel Decks: Izzet vs. Golgari", "DDJ", ExpansionSet.buildDate(2012, 9, 7), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Barren Moor", 78, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 51, Rarity.UNCOMMON, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Brainstorm", 13, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brain Weevil", 58, Rarity.COMMON, mage.cards.b.BrainWeevil.class));
        cards.add(new SetCardInfo("Call to Heel", 18, Rarity.COMMON, mage.cards.c.CallToHeel.class));
        cards.add(new SetCardInfo("Dakmor Salvage", 79, Rarity.UNCOMMON, mage.cards.d.DakmorSalvage.class));
        cards.add(new SetCardInfo("Dissipate", 25, Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Djinn Illuminatus", 12, Rarity.RARE, mage.cards.d.DjinnIlluminatus.class));
        cards.add(new SetCardInfo("Doomgape", 65, Rarity.RARE, mage.cards.d.Doomgape.class));
        cards.add(new SetCardInfo("Dreg Mangler", 56, Rarity.UNCOMMON, mage.cards.d.DregMangler.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 47, Rarity.COMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Eternal Witness", 55, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Feast or Famine", 72, Rarity.COMMON, mage.cards.f.FeastOrFamine.class));
        cards.add(new SetCardInfo("Fire // Ice", 32, Rarity.UNCOMMON, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Force Spike", 14, Rarity.COMMON, mage.cards.f.ForceSpike.class));
        cards.add(new SetCardInfo("Forest", 87, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 89, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 90, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Cave", 33, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Galvanoth", 10, Rarity.RARE, mage.cards.g.Galvanoth.class));
        cards.add(new SetCardInfo("Gelectrode", 5, Rarity.UNCOMMON, mage.cards.g.Gelectrode.class));
        cards.add(new SetCardInfo("Ghoul's Feast", 67, Rarity.UNCOMMON, mage.cards.g.GhoulsFeast.class));
        cards.add(new SetCardInfo("Gleancrawler", 64, Rarity.RARE, mage.cards.g.Gleancrawler.class));
        cards.add(new SetCardInfo("Goblin Electromancer", 3, Rarity.COMMON, mage.cards.g.GoblinElectromancer.class));
        cards.add(new SetCardInfo("Golgari Germination", 70, Rarity.UNCOMMON, mage.cards.g.GolgariGermination.class));
        cards.add(new SetCardInfo("Golgari Grave-Troll", 60, Rarity.RARE, mage.cards.g.GolgariGraveTroll.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 80, Rarity.COMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Golgari Rotwurm", 63, Rarity.COMMON, mage.cards.g.GolgariRotwurm.class));
        cards.add(new SetCardInfo("Golgari Signet", 66, Rarity.COMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Golgari Thug", 48, Rarity.UNCOMMON, mage.cards.g.GolgariThug.class));
        cards.add(new SetCardInfo("Greater Mossdog", 59, Rarity.COMMON, mage.cards.g.GreaterMossdog.class));
        cards.add(new SetCardInfo("Grim Flowering", 75, Rarity.UNCOMMON, mage.cards.g.GrimFlowering.class));
        cards.add(new SetCardInfo("Invoke the Firemind", 31, Rarity.RARE, mage.cards.i.InvokeTheFiremind.class));
        cards.add(new SetCardInfo("Island", 37, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 38, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 39, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 40, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isochron Scepter", 16, Rarity.UNCOMMON, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Izzet Boilerworks", 34, Rarity.COMMON, mage.cards.i.IzzetBoilerworks.class));
        cards.add(new SetCardInfo("Izzet Charm", 21, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Izzet Chronarch", 11, Rarity.COMMON, mage.cards.i.IzzetChronarch.class));
        cards.add(new SetCardInfo("Izzet Guildmage", 4, Rarity.UNCOMMON, mage.cards.i.IzzetGuildmage.class));
        cards.add(new SetCardInfo("Izzet Signet", 17, Rarity.COMMON, mage.cards.i.IzzetSignet.class));
        cards.add(new SetCardInfo("Jarad, Golgari Lich Lord", 45, Rarity.MYTHIC, mage.cards.j.JaradGolgariLichLord.class));
        cards.add(new SetCardInfo("Kiln Fiend", 2, Rarity.COMMON, mage.cards.k.KilnFiend.class));
        cards.add(new SetCardInfo("Korozda Guildmage", 52, Rarity.UNCOMMON, mage.cards.k.KorozdaGuildmage.class));
        cards.add(new SetCardInfo("Life // Death", 77, Rarity.UNCOMMON, mage.cards.l.LifeDeath.class));
        cards.add(new SetCardInfo("Life from the Loam", 69, Rarity.RARE, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 35, Rarity.COMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Magma Spray", 15, Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Mountain", 41, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 42, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 43, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 44, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nightmare Void", 73, Rarity.UNCOMMON, mage.cards.n.NightmareVoid.class));
        cards.add(new SetCardInfo("Nivix, Aerie of the Firemind", 36, Rarity.UNCOMMON, mage.cards.n.NivixAerieOfTheFiremind.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 1, Rarity.MYTHIC, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Ogre Savant", 9, Rarity.COMMON, mage.cards.o.OgreSavant.class));
        cards.add(new SetCardInfo("Overwhelming Intellect", 28, Rarity.UNCOMMON, mage.cards.o.OverwhelmingIntellect.class));
        cards.add(new SetCardInfo("Plagued Rusalka", 46, Rarity.UNCOMMON, mage.cards.p.PlaguedRusalka.class));
        cards.add(new SetCardInfo("Prophetic Bolt", 27, Rarity.RARE, mage.cards.p.PropheticBolt.class));
        cards.add(new SetCardInfo("Putrefy", 71, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Putrid Leech", 53, Rarity.COMMON, mage.cards.p.PutridLeech.class));
        cards.add(new SetCardInfo("Pyromatics", 20, Rarity.COMMON, mage.cards.p.Pyromatics.class));
        cards.add(new SetCardInfo("Quicksilver Dagger", 26, Rarity.COMMON, mage.cards.q.QuicksilverDagger.class));
        cards.add(new SetCardInfo("Ravenous Rats", 49, Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 50, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reminisce", 22, Rarity.UNCOMMON, mage.cards.r.Reminisce.class));
        cards.add(new SetCardInfo("Sadistic Hypnotist", 62, Rarity.UNCOMMON, mage.cards.s.SadisticHypnotist.class));
        cards.add(new SetCardInfo("Shambling Shell", 57, Rarity.COMMON, mage.cards.s.ShamblingShell.class));
        cards.add(new SetCardInfo("Shrewd Hatchling", 8, Rarity.UNCOMMON, mage.cards.s.ShrewdHatchling.class));
        cards.add(new SetCardInfo("Sphinx-Bone Wand", 29, Rarity.RARE, mage.cards.s.SphinxBoneWand.class));
        cards.add(new SetCardInfo("Steamcore Weird", 7, Rarity.COMMON, mage.cards.s.SteamcoreWeird.class));
        cards.add(new SetCardInfo("Stingerfling Spider", 61, Rarity.UNCOMMON, mage.cards.s.StingerflingSpider.class));
        cards.add(new SetCardInfo("Stinkweed Imp", 54, Rarity.COMMON, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Street Spasm", 30, Rarity.UNCOMMON, mage.cards.s.StreetSpasm.class));
        cards.add(new SetCardInfo("Svogthos, the Restless Tomb", 81, Rarity.UNCOMMON, mage.cards.s.SvogthosTheRestlessTomb.class));
        cards.add(new SetCardInfo("Swamp", 83, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 84, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 85, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 86, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thunderheads", 23, Rarity.UNCOMMON, mage.cards.t.Thunderheads.class));
        cards.add(new SetCardInfo("Train of Thought", 19, Rarity.COMMON, mage.cards.t.TrainOfThought.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 82, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Twilight's Call", 76, Rarity.RARE, mage.cards.t.TwilightsCall.class));
        cards.add(new SetCardInfo("Vacuumelt", 24, Rarity.UNCOMMON, mage.cards.v.Vacuumelt.class));
        cards.add(new SetCardInfo("Vigor Mortis", 74, Rarity.UNCOMMON, mage.cards.v.VigorMortis.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 6, Rarity.COMMON, mage.cards.w.WeeDragonauts.class));
        cards.add(new SetCardInfo("Yoke of the Damned", 68, Rarity.COMMON, mage.cards.y.YokeOfTheDamned.class));
    }
}
