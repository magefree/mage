package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
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

        cards.add(new SetCardInfo("Abu Ja'far", 1, Rarity.UNCOMMON, mage.cards.a.AbuJafar.class, RETRO_ART));
        cards.add(new SetCardInfo("Active Volcano", 43, Rarity.COMMON, mage.cards.a.ActiveVolcano.class, RETRO_ART));
        cards.add(new SetCardInfo("Akron Legionnaire", 2, Rarity.RARE, mage.cards.a.AkronLegionnaire.class, RETRO_ART));
        cards.add(new SetCardInfo("Aladdin", 44, Rarity.UNCOMMON, mage.cards.a.Aladdin.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Voices", 3, Rarity.RARE, mage.cards.a.AngelicVoices.class, RETRO_ART));
        cards.add(new SetCardInfo("Arcades Sabboth", 71, Rarity.RARE, mage.cards.a.ArcadesSabboth.class, RETRO_ART));
        cards.add(new SetCardInfo("Arena of the Ancients", 91, Rarity.RARE, mage.cards.a.ArenaOfTheAncients.class, RETRO_ART));
        cards.add(new SetCardInfo("Argothian Pixies", 57, Rarity.COMMON, mage.cards.a.ArgothianPixies.class, RETRO_ART));
        cards.add(new SetCardInfo("Ashnod's Altar", 92, Rarity.COMMON, mage.cards.a.AshnodsAltar.class, RETRO_ART));
        cards.add(new SetCardInfo("Ashnod's Transmogrant", 93, Rarity.COMMON, mage.cards.a.AshnodsTransmogrant.class, RETRO_ART));
        cards.add(new SetCardInfo("Axelrod Gunnarson", 72, Rarity.RARE, mage.cards.a.AxelrodGunnarson.class, RETRO_ART));
        cards.add(new SetCardInfo("Ayesha Tanaka", 73, Rarity.RARE, mage.cards.a.AyeshaTanaka.class, RETRO_ART));
        cards.add(new SetCardInfo("Azure Drake", 15, Rarity.UNCOMMON, mage.cards.a.AzureDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Banshee", 29, Rarity.UNCOMMON, mage.cards.b.Banshee.class, RETRO_ART));
        cards.add(new SetCardInfo("Barl's Cage", 94, Rarity.RARE, mage.cards.b.BarlsCage.class, RETRO_ART));
        cards.add(new SetCardInfo("Beasts of Bogardan", 45, Rarity.UNCOMMON, mage.cards.b.BeastsOfBogardan.class, RETRO_ART));
        cards.add(new SetCardInfo("Blood Moon", 46, Rarity.RARE, mage.cards.b.BloodMoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Blood of the Martyr", 4, Rarity.UNCOMMON, mage.cards.b.BloodOfTheMartyr.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Rats", 30, Rarity.COMMON, mage.cards.b.BogRats.class, RETRO_ART));
        cards.add(new SetCardInfo("Book of Rass", 95, Rarity.RARE, mage.cards.b.BookOfRass.class, RETRO_ART));
        cards.add(new SetCardInfo("Boomerang", 16, Rarity.COMMON, mage.cards.b.Boomerang.class, RETRO_ART));
        cards.add(new SetCardInfo("Bronze Horse", 96, Rarity.RARE, mage.cards.b.BronzeHorse.class, RETRO_ART));
        cards.add(new SetCardInfo("Cat Warriors", 58, Rarity.COMMON, mage.cards.c.CatWarriors.class, RETRO_ART));
        cards.add(new SetCardInfo("Chromium", 74, Rarity.RARE, mage.cards.c.Chromium.class, RETRO_ART));
        cards.add(new SetCardInfo("City of Brass", 112, Rarity.RARE, mage.cards.c.CityOfBrass.class, RETRO_ART));
        cards.add(new SetCardInfo("Cocoon", 59, Rarity.UNCOMMON, mage.cards.c.Cocoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Concordant Crossroads", 60, Rarity.RARE, mage.cards.c.ConcordantCrossroads.class, RETRO_ART));
        cards.add(new SetCardInfo("Craw Giant", 61, Rarity.UNCOMMON, mage.cards.c.CrawGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Cuombajj Witches", 31, Rarity.COMMON, mage.cards.c.CuombajjWitches.class, RETRO_ART));
        cards.add(new SetCardInfo("Cyclone", 62, Rarity.RARE, mage.cards.c.Cyclone.class, RETRO_ART));
        cards.add(new SetCardInfo("D'Avenant Archer", 5, Rarity.COMMON, mage.cards.d.DAvenantArcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Dakkon Blackblade", 75, Rarity.RARE, mage.cards.d.DakkonBlackblade.class, RETRO_ART));
        cards.add(new SetCardInfo("Dance of Many", 17, Rarity.RARE, mage.cards.d.DanceOfMany.class, RETRO_ART));
        cards.add(new SetCardInfo("Dandan", 18, Rarity.COMMON, mage.cards.d.Dandan.class, RETRO_ART));
        cards.add(new SetCardInfo("Divine Offering", 6, Rarity.COMMON, mage.cards.d.DivineOffering.class, RETRO_ART));
        cards.add(new SetCardInfo("Emerald Dragonfly", 63, Rarity.COMMON, mage.cards.e.EmeraldDragonfly.class, RETRO_ART));
        cards.add(new SetCardInfo("Enchantment Alteration", 19, Rarity.UNCOMMON, mage.cards.e.EnchantmentAlteration.class, RETRO_ART));
        cards.add(new SetCardInfo("Erhnam Djinn", 64, Rarity.UNCOMMON, mage.cards.e.ErhnamDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Fallen Angel", 32, Rarity.UNCOMMON, mage.cards.f.FallenAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Feldon's Cane", 97, Rarity.COMMON, mage.cards.f.FeldonsCane.class, RETRO_ART));
        cards.add(new SetCardInfo("Fire Drake", 47, Rarity.UNCOMMON, mage.cards.f.FireDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Fishliver Oil", 20, Rarity.COMMON, mage.cards.f.FishliverOil.class, RETRO_ART));
        cards.add(new SetCardInfo("Flash Flood", 21, Rarity.COMMON, mage.cards.f.FlashFlood.class, RETRO_ART));
        cards.add(new SetCardInfo("Fountain of Youth", 98, Rarity.COMMON, mage.cards.f.FountainOfYouth.class, RETRO_ART));
        cards.add(new SetCardInfo("Gabriel Angelfire", 76, Rarity.RARE, mage.cards.g.GabrielAngelfire.class, RETRO_ART));
        cards.add(new SetCardInfo("Gauntlets of Chaos", 99, Rarity.RARE, mage.cards.g.GauntletsOfChaos.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghazban Ogre", 65, Rarity.COMMON, mage.cards.g.GhazbanOgre.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Slug", 33, Rarity.COMMON, mage.cards.g.GiantSlug.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Artisans", 48, Rarity.UNCOMMON, mage.cards.g.GoblinArtisans.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Digging Team", 49, Rarity.COMMON, mage.cards.g.GoblinDiggingTeam.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Shrine", 50, Rarity.COMMON, mage.cards.g.GoblinShrine.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblins of the Flarg", 51, Rarity.COMMON, mage.cards.g.GoblinsOfTheFlarg.class, RETRO_ART));
        cards.add(new SetCardInfo("Hasran Ogress", 34, Rarity.COMMON, mage.cards.h.HasranOgress.class, RETRO_ART));
        cards.add(new SetCardInfo("Hell's Caretaker", 35, Rarity.RARE, mage.cards.h.HellsCaretaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Horn of Deafening", 100, Rarity.RARE, mage.cards.h.HornOfDeafening.class, RETRO_ART));
        cards.add(new SetCardInfo("Indestructible Aura", 7, Rarity.COMMON, mage.cards.i.IndestructibleAura.class, RETRO_ART));
        cards.add(new SetCardInfo("Ivory Guardians", 8, Rarity.UNCOMMON, mage.cards.i.IvoryGuardians.class, RETRO_ART));
        cards.add(new SetCardInfo("Jalum Tome", 101, Rarity.RARE, mage.cards.j.JalumTome.class, RETRO_ART));
        cards.add(new SetCardInfo("Johan", 77, Rarity.RARE, mage.cards.j.Johan.class, RETRO_ART));
        cards.add(new SetCardInfo("Juxtapose", 22, Rarity.RARE, mage.cards.j.Juxtapose.class, RETRO_ART));
        cards.add(new SetCardInfo("Keepers of the Faith", 9, Rarity.COMMON, mage.cards.k.KeepersOfTheFaith.class, RETRO_ART));
        cards.add(new SetCardInfo("Kei Takahashi", 78, Rarity.UNCOMMON, mage.cards.k.KeiTakahashi.class, RETRO_ART));
        cards.add(new SetCardInfo("Land's Edge", 52, Rarity.RARE, mage.cards.l.LandsEdge.class, RETRO_ART));
        cards.add(new SetCardInfo("Living Armor", 103, Rarity.COMMON, mage.cards.l.LivingArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Marhault Elsdragon", 79, Rarity.UNCOMMON, mage.cards.m.MarhaultElsdragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Metamorphosis", 66, Rarity.COMMON, mage.cards.m.Metamorphosis.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain Yeti", 53, Rarity.COMMON, mage.cards.m.MountainYeti.class, RETRO_ART));
        cards.add(new SetCardInfo("Nebuchadnezzar", 80, Rarity.RARE, mage.cards.n.Nebuchadnezzar.class, RETRO_ART));
        cards.add(new SetCardInfo("Nicol Bolas", 81, Rarity.RARE, mage.cards.n.NicolBolas.class, RETRO_ART));
        cards.add(new SetCardInfo("Obelisk of Undoing", 104, Rarity.RARE, mage.cards.o.ObeliskOfUndoing.class, RETRO_ART));
        cards.add(new SetCardInfo("Palladia-Mors", 82, Rarity.RARE, mage.cards.p.PalladiaMors.class, RETRO_ART));
        cards.add(new SetCardInfo("Petra Sphinx", 10, Rarity.RARE, mage.cards.p.PetraSphinx.class, RETRO_ART));
        cards.add(new SetCardInfo("Primordial Ooze", 54, Rarity.UNCOMMON, mage.cards.p.PrimordialOoze.class, RETRO_ART));
        cards.add(new SetCardInfo("Puppet Master", 23, Rarity.UNCOMMON, mage.cards.p.PuppetMaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Rabid Wombat", 67, Rarity.UNCOMMON, mage.cards.r.RabidWombat.class, RETRO_ART));
        cards.add(new SetCardInfo("Rakalite", 105, Rarity.RARE, mage.cards.r.Rakalite.class, RETRO_ART));
        cards.add(new SetCardInfo("Recall", 24, Rarity.UNCOMMON, mage.cards.r.Recall.class, RETRO_ART));
        cards.add(new SetCardInfo("Remove Soul", 25, Rarity.COMMON, mage.cards.r.RemoveSoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Repentant Blacksmith", 11, Rarity.COMMON, mage.cards.r.RepentantBlacksmith.class, RETRO_ART));
        cards.add(new SetCardInfo("Revelation", 68, Rarity.RARE, mage.cards.r.Revelation.class, RETRO_ART));
        cards.add(new SetCardInfo("Rubinia Soulsinger", 83, Rarity.RARE, mage.cards.r.RubiniaSoulsinger.class, RETRO_ART));
        cards.add(new SetCardInfo("Runesword", 106, Rarity.COMMON, mage.cards.r.Runesword.class, RETRO_ART));
        cards.add(new SetCardInfo("Safe Haven", 113, Rarity.RARE, mage.cards.s.SafeHaven.class, RETRO_ART));
        cards.add(new SetCardInfo("Scavenger Folk", 69, Rarity.COMMON, mage.cards.s.ScavengerFolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Sentinel", 107, Rarity.RARE, mage.cards.s.Sentinel.class, RETRO_ART));
        cards.add(new SetCardInfo("Serpent Generator", 108, Rarity.RARE, mage.cards.s.SerpentGenerator.class, RETRO_ART));
        cards.add(new SetCardInfo("Shield Wall", 12, Rarity.UNCOMMON, mage.cards.s.ShieldWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Shimian Night Stalker", 36, Rarity.UNCOMMON, mage.cards.s.ShimianNightStalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Sivitri Scarzam", 84, Rarity.UNCOMMON, mage.cards.s.SivitriScarzam.class, RETRO_ART));
        cards.add(new SetCardInfo("Sol'kanar the Swamp King", 85, Rarity.RARE, mage.cards.s.SolkanarTheSwampKing.class, RETRO_ART));
        cards.add(new SetCardInfo("Stangg", 86, Rarity.RARE, mage.cards.s.Stangg.class, RETRO_ART));
        cards.add(new SetCardInfo("Storm Seeker", 70, Rarity.UNCOMMON, mage.cards.s.StormSeeker.class, RETRO_ART));
        cards.add(new SetCardInfo("Takklemaggot", 37, Rarity.UNCOMMON, mage.cards.t.Takklemaggot.class, RETRO_ART));
        cards.add(new SetCardInfo("Teleport", 26, Rarity.RARE, mage.cards.t.Teleport.class, RETRO_ART));
        cards.add(new SetCardInfo("The Fallen", 38, Rarity.UNCOMMON, mage.cards.t.TheFallen.class, RETRO_ART));
        cards.add(new SetCardInfo("The Wretched", 39, Rarity.RARE, mage.cards.t.TheWretched.class, RETRO_ART));
        cards.add(new SetCardInfo("Tobias Andrion", 87, Rarity.UNCOMMON, mage.cards.t.TobiasAndrion.class, RETRO_ART));
        cards.add(new SetCardInfo("Tor Wauki", 88, Rarity.UNCOMMON, mage.cards.t.TorWauki.class, RETRO_ART));
        cards.add(new SetCardInfo("Tormod's Crypt", 109, Rarity.COMMON, mage.cards.t.TormodsCrypt.class, RETRO_ART));
        cards.add(new SetCardInfo("Transmutation", 40, Rarity.COMMON, mage.cards.t.Transmutation.class, RETRO_ART));
        cards.add(new SetCardInfo("Triassic Egg", 110, Rarity.RARE, mage.cards.t.TriassicEgg.class, RETRO_ART));
        cards.add(new SetCardInfo("Urza's Mine", "114a", Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", "114b", Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", "114c", Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", "114d", Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "115a", Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "115b", Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "115c", Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "115d", Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "116a", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "116b", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "116c", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "116d", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Vaevictis Asmadi", 89, Rarity.RARE, mage.cards.v.VaevictisAsmadi.class, RETRO_ART));
        cards.add(new SetCardInfo("Voodoo Doll", 111, Rarity.RARE, mage.cards.v.VoodooDoll.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Heat", 55, Rarity.COMMON, mage.cards.w.WallOfHeat.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Opposition", 56, Rarity.UNCOMMON, mage.cards.w.WallOfOpposition.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Shadows", 41, Rarity.COMMON, mage.cards.w.WallOfShadows.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Vapor", 27, Rarity.COMMON, mage.cards.w.WallOfVapor.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Wonder", 28, Rarity.UNCOMMON, mage.cards.w.WallOfWonder.class, RETRO_ART));
        cards.add(new SetCardInfo("War Elephant", 13, Rarity.COMMON, mage.cards.w.WarElephant.class, RETRO_ART));
        cards.add(new SetCardInfo("Witch Hunter", 14, Rarity.UNCOMMON, mage.cards.w.WitchHunter.class, RETRO_ART));
        cards.add(new SetCardInfo("Xira Arien", 90, Rarity.RARE, mage.cards.x.XiraArien.class, RETRO_ART));
        cards.add(new SetCardInfo("Yawgmoth Demon", 42, Rarity.RARE, mage.cards.y.YawgmothDemon.class, RETRO_ART));
    }

}
