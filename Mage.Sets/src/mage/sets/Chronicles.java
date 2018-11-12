package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public final class Chronicles extends ExpansionSet {

    private static final Chronicles instance = new Chronicles();

    public static Chronicles getInstance() {
        return instance;
    }

    private Chronicles() {
        super("Chronicles", "CHR", ExpansionSet.buildDate(1995, 6, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 2;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abu Ja'far", 57, Rarity.UNCOMMON, mage.cards.a.AbuJafar.class));
        cards.add(new SetCardInfo("Active Volcano", 43, Rarity.COMMON, mage.cards.a.ActiveVolcano.class));
        cards.add(new SetCardInfo("Akron Legionnaire", 58, Rarity.RARE, mage.cards.a.AkronLegionnaire.class));
        cards.add(new SetCardInfo("Aladdin", 44, Rarity.UNCOMMON, mage.cards.a.Aladdin.class));
        cards.add(new SetCardInfo("Angelic Voices", 59, Rarity.RARE, mage.cards.a.AngelicVoices.class));
        cards.add(new SetCardInfo("Arcades Sabboth", 106, Rarity.RARE, mage.cards.a.ArcadesSabboth.class));
        cards.add(new SetCardInfo("Arena of the Ancients", 71, Rarity.RARE, mage.cards.a.ArenaOfTheAncients.class));
        cards.add(new SetCardInfo("Argothian Pixies", 29, Rarity.COMMON, mage.cards.a.ArgothianPixies.class));
        cards.add(new SetCardInfo("Ashnod's Altar", 72, Rarity.COMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Ashnod's Transmogrant", 73, Rarity.COMMON, mage.cards.a.AshnodsTransmogrant.class));
        cards.add(new SetCardInfo("Axelrod Gunnarson", 107, Rarity.RARE, mage.cards.a.AxelrodGunnarson.class));
        cards.add(new SetCardInfo("Ayesha Tanaka", 108, Rarity.RARE, mage.cards.a.AyeshaTanaka.class));
        cards.add(new SetCardInfo("Azure Drake", 15, Rarity.UNCOMMON, mage.cards.a.AzureDrake.class));
        cards.add(new SetCardInfo("Banshee", 1, Rarity.UNCOMMON, mage.cards.b.Banshee.class));
        cards.add(new SetCardInfo("Barl's Cage", 74, Rarity.RARE, mage.cards.b.BarlsCage.class));
        cards.add(new SetCardInfo("Beasts of Bogardan", 45, Rarity.UNCOMMON, mage.cards.b.BeastsOfBogardan.class));
        cards.add(new SetCardInfo("Blood Moon", 46, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Blood of the Martyr", 60, Rarity.UNCOMMON, mage.cards.b.BloodOfTheMartyr.class));
        cards.add(new SetCardInfo("Bog Rats", 2, Rarity.COMMON, mage.cards.b.BogRats.class));
        cards.add(new SetCardInfo("Book of Rass", 75, Rarity.RARE, mage.cards.b.BookOfRass.class));
        cards.add(new SetCardInfo("Boomerang", 16, Rarity.COMMON, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Cat Warriors", 30, Rarity.COMMON, mage.cards.c.CatWarriors.class));
        cards.add(new SetCardInfo("Chromium", 109, Rarity.RARE, mage.cards.c.Chromium.class));
        cards.add(new SetCardInfo("City of Brass", 92, Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Concordant Crossroads", 32, Rarity.RARE, mage.cards.c.ConcordantCrossroads.class));
        cards.add(new SetCardInfo("Craw Giant", 33, Rarity.UNCOMMON, mage.cards.c.CrawGiant.class));
        cards.add(new SetCardInfo("Cuombajj Witches", 3, Rarity.COMMON, mage.cards.c.CuombajjWitches.class));
        cards.add(new SetCardInfo("Cyclone", 34, Rarity.RARE, mage.cards.c.Cyclone.class));
        cards.add(new SetCardInfo("Dakkon Blackblade", 110, Rarity.RARE, mage.cards.d.DakkonBlackblade.class));
        cards.add(new SetCardInfo("Dance of Many", 17, Rarity.RARE, mage.cards.d.DanceOfMany.class));
        cards.add(new SetCardInfo("Dandan", 18, Rarity.COMMON, mage.cards.d.Dandan.class));
        cards.add(new SetCardInfo("D'Avenant Archer", 61, Rarity.COMMON, mage.cards.d.DAvenantArcher.class));
        cards.add(new SetCardInfo("Divine Offering", 62, Rarity.COMMON, mage.cards.d.DivineOffering.class));
        cards.add(new SetCardInfo("Emerald Dragonfly", 35, Rarity.COMMON, mage.cards.e.EmeraldDragonfly.class));
        cards.add(new SetCardInfo("Enchantment Alteration", 19, Rarity.UNCOMMON, mage.cards.e.EnchantmentAlteration.class));
        cards.add(new SetCardInfo("Erhnam Djinn", 36, Rarity.UNCOMMON, mage.cards.e.ErhnamDjinn.class));
        cards.add(new SetCardInfo("Fallen Angel", 4, Rarity.UNCOMMON, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Feldon's Cane", 77, Rarity.COMMON, mage.cards.f.FeldonsCane.class));
        cards.add(new SetCardInfo("Fire Drake", 47, Rarity.UNCOMMON, mage.cards.f.FireDrake.class));
        cards.add(new SetCardInfo("Fishliver Oil", 20, Rarity.COMMON, mage.cards.f.FishliverOil.class));
        cards.add(new SetCardInfo("Flash Flood", 21, Rarity.COMMON, mage.cards.f.FlashFlood.class));
        cards.add(new SetCardInfo("Fountain of Youth", 78, Rarity.COMMON, mage.cards.f.FountainOfYouth.class));
        cards.add(new SetCardInfo("Gabriel Angelfire", 111, Rarity.RARE, mage.cards.g.GabrielAngelfire.class));
        cards.add(new SetCardInfo("Gauntlets of Chaos", 79, Rarity.RARE, mage.cards.g.GauntletsOfChaos.class));
        cards.add(new SetCardInfo("Ghazban Ogre", 37, Rarity.COMMON, mage.cards.g.GhazbanOgre.class));
        cards.add(new SetCardInfo("Goblin Artisans", 48, Rarity.UNCOMMON, mage.cards.g.GoblinArtisans.class));
        cards.add(new SetCardInfo("Goblin Digging Team", 49, Rarity.COMMON, mage.cards.g.GoblinDiggingTeam.class));
        cards.add(new SetCardInfo("Goblin Shrine", 50, Rarity.COMMON, mage.cards.g.GoblinShrine.class));
        cards.add(new SetCardInfo("Goblins of the Flarg", 51, Rarity.COMMON, mage.cards.g.GoblinsOfTheFlarg.class));
        cards.add(new SetCardInfo("Hasran Ogress", 6, Rarity.COMMON, mage.cards.h.HasranOgress.class));
        cards.add(new SetCardInfo("Hell's Caretaker", 7, Rarity.RARE, mage.cards.h.HellsCaretaker.class));
        cards.add(new SetCardInfo("Horn of Deafening", 80, Rarity.RARE, mage.cards.h.HornOfDeafening.class));
        cards.add(new SetCardInfo("Indestructible Aura", 63, Rarity.COMMON, mage.cards.i.IndestructibleAura.class));
        cards.add(new SetCardInfo("Ivory Guardians", 64, Rarity.UNCOMMON, mage.cards.i.IvoryGuardians.class));
        cards.add(new SetCardInfo("Jalum Tome", 81, Rarity.RARE, mage.cards.j.JalumTome.class));
        cards.add(new SetCardInfo("Johan", 112, Rarity.RARE, mage.cards.j.Johan.class));
        cards.add(new SetCardInfo("Juxtapose", 22, Rarity.RARE, mage.cards.j.Juxtapose.class));
        cards.add(new SetCardInfo("Keepers of the Faith", 65, Rarity.COMMON, mage.cards.k.KeepersOfTheFaith.class));
        cards.add(new SetCardInfo("Kei Takahashi", 113, Rarity.UNCOMMON, mage.cards.k.KeiTakahashi.class));
        cards.add(new SetCardInfo("Land's Edge", 52, Rarity.RARE, mage.cards.l.LandsEdge.class));
        cards.add(new SetCardInfo("Living Armor", 83, Rarity.COMMON, mage.cards.l.LivingArmor.class));
        cards.add(new SetCardInfo("Marhault Elsdragon", 114, Rarity.UNCOMMON, mage.cards.m.MarhaultElsdragon.class));
        cards.add(new SetCardInfo("Metamorphosis", 38, Rarity.COMMON, mage.cards.m.Metamorphosis.class));
        cards.add(new SetCardInfo("Mountain Yeti", 53, Rarity.COMMON, mage.cards.m.MountainYeti.class));
        cards.add(new SetCardInfo("Nebuchadnezzar", 115, Rarity.RARE, mage.cards.n.Nebuchadnezzar.class));
        cards.add(new SetCardInfo("Nicol Bolas", 116, Rarity.RARE, mage.cards.n.NicolBolas.class));
        cards.add(new SetCardInfo("Obelisk of Undoing", 84, Rarity.RARE, mage.cards.o.ObeliskOfUndoing.class));
        cards.add(new SetCardInfo("Palladia-Mors", 117, Rarity.RARE, mage.cards.p.PalladiaMors.class));
        cards.add(new SetCardInfo("Petra Sphinx", 66, Rarity.RARE, mage.cards.p.PetraSphinx.class));
        cards.add(new SetCardInfo("Primordial Ooze", 54, Rarity.UNCOMMON, mage.cards.p.PrimordialOoze.class));
        cards.add(new SetCardInfo("Rabid Wombat", 39, Rarity.UNCOMMON, mage.cards.r.RabidWombat.class));
        cards.add(new SetCardInfo("Rakalite", 85, Rarity.RARE, mage.cards.r.Rakalite.class));
        cards.add(new SetCardInfo("Recall", 24, Rarity.UNCOMMON, mage.cards.r.Recall.class));
        cards.add(new SetCardInfo("Remove Soul", 25, Rarity.COMMON, mage.cards.r.RemoveSoul.class));
        cards.add(new SetCardInfo("Repentant Blacksmith", 67, Rarity.COMMON, mage.cards.r.RepentantBlacksmith.class));
        cards.add(new SetCardInfo("Revelation", 40, Rarity.RARE, mage.cards.r.Revelation.class));
        cards.add(new SetCardInfo("Rubinia Soulsinger", 118, Rarity.RARE, mage.cards.r.RubiniaSoulsinger.class));
        cards.add(new SetCardInfo("Runesword", 86, Rarity.COMMON, mage.cards.r.Runesword.class));
        cards.add(new SetCardInfo("Safe Haven", 93, Rarity.RARE, mage.cards.s.SafeHaven.class));
        cards.add(new SetCardInfo("Scavenger Folk", 41, Rarity.COMMON, mage.cards.s.ScavengerFolk.class));
        cards.add(new SetCardInfo("Sentinel", 87, Rarity.RARE, mage.cards.s.Sentinel.class));
        cards.add(new SetCardInfo("Serpent Generator", 88, Rarity.RARE, mage.cards.s.SerpentGenerator.class));
        cards.add(new SetCardInfo("Shield Wall", 68, Rarity.UNCOMMON, mage.cards.s.ShieldWall.class));
        cards.add(new SetCardInfo("Shimian Night Stalker", 8, Rarity.UNCOMMON, mage.cards.s.ShimianNightStalker.class));
        cards.add(new SetCardInfo("Sivitri Scarzam", 119, Rarity.UNCOMMON, mage.cards.s.SivitriScarzam.class));
        cards.add(new SetCardInfo("Sol'kanar the Swamp King", 120, Rarity.RARE, mage.cards.s.SolkanarTheSwampKing.class));
        cards.add(new SetCardInfo("Stangg", 121, Rarity.RARE, mage.cards.s.Stangg.class));
        cards.add(new SetCardInfo("Storm Seeker", 42, Rarity.UNCOMMON, mage.cards.s.StormSeeker.class));
        cards.add(new SetCardInfo("Teleport", 26, Rarity.RARE, mage.cards.t.Teleport.class));
        cards.add(new SetCardInfo("The Fallen", 10, Rarity.UNCOMMON, mage.cards.t.TheFallen.class));
        cards.add(new SetCardInfo("The Wretched", 11, Rarity.RARE, mage.cards.t.TheWretched.class));
        cards.add(new SetCardInfo("Tobias Andrion", 122, Rarity.UNCOMMON, mage.cards.t.TobiasAndrion.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 89, Rarity.COMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Tor Wauki", 123, Rarity.UNCOMMON, mage.cards.t.TorWauki.class));
        cards.add(new SetCardInfo("Transmutation", 12, Rarity.COMMON, mage.cards.t.Transmutation.class));
        cards.add(new SetCardInfo("Triassic Egg", 90, Rarity.RARE, mage.cards.t.TriassicEgg.class));
        cards.add(new SetCardInfo("Urza's Mine", 94, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 95, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 96, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 97, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 98, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 99, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 100, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 101, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 102, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 103, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 104, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 105, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vaevictis Asmadi", 124, Rarity.RARE, mage.cards.v.VaevictisAsmadi.class));
        cards.add(new SetCardInfo("Wall of Heat", 55, Rarity.COMMON, mage.cards.w.WallOfHeat.class));
        cards.add(new SetCardInfo("Wall of Opposition", 56, Rarity.UNCOMMON, mage.cards.w.WallOfOpposition.class));
        cards.add(new SetCardInfo("Wall of Vapor", 27, Rarity.COMMON, mage.cards.w.WallOfVapor.class));
        cards.add(new SetCardInfo("Wall of Wonder", 28, Rarity.UNCOMMON, mage.cards.w.WallOfWonder.class));
        cards.add(new SetCardInfo("War Elephant", 69, Rarity.COMMON, mage.cards.w.WarElephant.class));
        cards.add(new SetCardInfo("Witch Hunter", 70, Rarity.UNCOMMON, mage.cards.w.WitchHunter.class));
        cards.add(new SetCardInfo("Xira Arien", 125, Rarity.RARE, mage.cards.x.XiraArien.class));
        cards.add(new SetCardInfo("Yawgmoth Demon", 14, Rarity.RARE, mage.cards.y.YawgmothDemon.class));
    }

}
