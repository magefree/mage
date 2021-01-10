package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Kaldheim extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList(
            "Alrund's Epiphany",
            "Augury Raven",
            "Behold the Multiverse",
            "Cosmos Charger",
            "Doomskar Oracle",
            "Gods' Hall Guardian",
            "Kaya's Onslaught",
            "Quakebringer",
            "Ranar the Ever-Watchful",
            "Ravenform",
            "Sarulf's Packmate",
            "Saw It Coming"
    );

    private static final Kaldheim instance = new Kaldheim();

    public static Kaldheim getInstance() {
        return instance;
    }

    private Kaldheim() {
        super("Kaldheim", "KHM", ExpansionSet.buildDate(2021, 2, 5), SetType.EXPANSION);
        this.blockName = "Kaldheim";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 285;

        cards.add(new SetCardInfo("Absorb Identity", 383, Rarity.UNCOMMON, mage.cards.a.AbsorbIdentity.class));
        cards.add(new SetCardInfo("Alpine Meadow", 248, Rarity.COMMON, mage.cards.a.AlpineMeadow.class));
        cards.add(new SetCardInfo("Alrund's Epiphany", 41, Rarity.MYTHIC, mage.cards.a.AlrundsEpiphany.class));
        cards.add(new SetCardInfo("Annul", 42, Rarity.COMMON, mage.cards.a.Annul.class));
        cards.add(new SetCardInfo("Arctic Treeline", 249, Rarity.COMMON, mage.cards.a.ArcticTreeline.class));
        cards.add(new SetCardInfo("Armed and Armored", 379, Rarity.UNCOMMON, mage.cards.a.ArmedAndArmored.class));
        cards.add(new SetCardInfo("Augury Raven", 44, Rarity.COMMON, mage.cards.a.AuguryRaven.class));
        cards.add(new SetCardInfo("Barkchannel Pathway", 251, Rarity.RARE, mage.cards.b.BarkchannelPathway.class));
        cards.add(new SetCardInfo("Bearded Axe", 388, Rarity.UNCOMMON, mage.cards.b.BeardedAxe.class));
        cards.add(new SetCardInfo("Behold the Multiverse", 46, Rarity.COMMON, mage.cards.b.BeholdTheMultiverse.class));
        cards.add(new SetCardInfo("Binding the Old Gods", 206, Rarity.UNCOMMON, mage.cards.b.BindingTheOldGods.class));
        cards.add(new SetCardInfo("Blightstep Pathway", 252, Rarity.RARE, mage.cards.b.BlightstepPathway.class));
        cards.add(new SetCardInfo("Bretagard Stronghold", 253, Rarity.UNCOMMON, mage.cards.b.BretagardStronghold.class));
        cards.add(new SetCardInfo("Broken Wings", 164, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Calamity Bearer", 125, Rarity.RARE, mage.cards.c.CalamityBearer.class));
        cards.add(new SetCardInfo("Canopy Tactician", 378, Rarity.RARE, mage.cards.c.CanopyTactician.class));
        cards.add(new SetCardInfo("Cleaving Reaper", 376, Rarity.RARE, mage.cards.c.CleavingReaper.class));
        cards.add(new SetCardInfo("Darkbore Pathway", 254, Rarity.RARE, mage.cards.d.DarkborePathway.class));
        cards.add(new SetCardInfo("Doomskar Oracle", 10, Rarity.COMMON, mage.cards.d.DoomskarOracle.class));
        cards.add(new SetCardInfo("Elderfang Ritualist", 385, Rarity.UNCOMMON, mage.cards.e.ElderfangRitualist.class));
        cards.add(new SetCardInfo("Elven Ambush", 391, Rarity.UNCOMMON, mage.cards.e.ElvenAmbush.class));
        cards.add(new SetCardInfo("Elvish Warmaster", 167, Rarity.RARE, mage.cards.e.ElvishWarmaster.class));
        cards.add(new SetCardInfo("Esika's Chariot", 169, Rarity.RARE, mage.cards.e.EsikasChariot.class));
        cards.add(new SetCardInfo("Esika, God of the Tree", 168, Rarity.MYTHIC, mage.cards.e.EsikaGodOfTheTree.class));
        cards.add(new SetCardInfo("Fire Giant's Fury", 389, Rarity.UNCOMMON, mage.cards.f.FireGiantsFury.class));
        cards.add(new SetCardInfo("Firja, Judge of Valor", 209, Rarity.UNCOMMON, mage.cards.f.FirjaJudgeOfValor.class));
        cards.add(new SetCardInfo("Forging the Tyrite Sword", 211, Rarity.UNCOMMON, mage.cards.f.ForgingTheTyriteSword.class));
        cards.add(new SetCardInfo("Frost Bite", 138, Rarity.COMMON, mage.cards.f.FrostBite.class));
        cards.add(new SetCardInfo("Giant's Amulet", 59, Rarity.UNCOMMON, mage.cards.g.GiantsAmulet.class));
        cards.add(new SetCardInfo("Giant's Grasp", 384, Rarity.UNCOMMON, mage.cards.g.GiantsGrasp.class));
        cards.add(new SetCardInfo("Gilded Assault Cart", 390, Rarity.UNCOMMON, mage.cards.g.GildedAssaultCart.class));
        cards.add(new SetCardInfo("Glacial Floodplain", 257, Rarity.COMMON, mage.cards.g.GlacialFloodplain.class));
        cards.add(new SetCardInfo("Gladewalker Ritualist", 392, Rarity.UNCOMMON, mage.cards.g.GladewalkerRitualist.class));
        cards.add(new SetCardInfo("Gods' Hall Guardian", 13, Rarity.COMMON, mage.cards.g.GodsHallGuardian.class));
        cards.add(new SetCardInfo("Goldspan Dragon", 139, Rarity.MYTHIC, mage.cards.g.GoldspanDragon.class));
        cards.add(new SetCardInfo("Hagi Mob", 140, Rarity.COMMON, mage.cards.h.HagiMob.class));
        cards.add(new SetCardInfo("Hailstorm Valkyrie", 97, Rarity.UNCOMMON, mage.cards.h.HailstormValkyrie.class));
        cards.add(new SetCardInfo("Halvar, God of Battle", 15, Rarity.MYTHIC, mage.cards.h.HalvarGodOfBattle.class));
        cards.add(new SetCardInfo("Hengegate Pathway", 260, Rarity.RARE, mage.cards.h.HengegatePathway.class));
        cards.add(new SetCardInfo("Highland Forest", 261, Rarity.COMMON, mage.cards.h.HighlandForest.class));
        cards.add(new SetCardInfo("Ice Tunnel", 262, Rarity.COMMON, mage.cards.i.IceTunnel.class));
        cards.add(new SetCardInfo("Inga Rune-Eyes", 64, Rarity.UNCOMMON, mage.cards.i.IngaRuneEyes.class));
        cards.add(new SetCardInfo("Invasion of the Giants", 215, Rarity.UNCOMMON, mage.cards.i.InvasionOfTheGiants.class));
        cards.add(new SetCardInfo("Kaya the Inexorable", 218, Rarity.MYTHIC, mage.cards.k.KayaTheInexorable.class));
        cards.add(new SetCardInfo("Kaya's Onslaught", 18, Rarity.UNCOMMON, mage.cards.k.KayasOnslaught.class));
        cards.add(new SetCardInfo("Koll, the Forgemaster", 220, Rarity.UNCOMMON, mage.cards.k.KollTheForgemaster.class));
        cards.add(new SetCardInfo("Koma's Faithful", 102, Rarity.COMMON, mage.cards.k.KomasFaithful.class));
        cards.add(new SetCardInfo("Magda, Brazen Outlaw", 142, Rarity.RARE, mage.cards.m.MagdaBrazenOutlaw.class));
        cards.add(new SetCardInfo("Masked Vandal", 184, Rarity.COMMON, mage.cards.m.MaskedVandal.class));
        cards.add(new SetCardInfo("Niko Aris", 225, Rarity.MYTHIC, mage.cards.n.NikoAris.class));
        cards.add(new SetCardInfo("Pyre of Heroes", 241, Rarity.RARE, mage.cards.p.PyreOfHeroes.class));
        cards.add(new SetCardInfo("Rampage of the Valkyries", 393, Rarity.UNCOMMON, mage.cards.r.RampageOfTheValkyries.class));
        cards.add(new SetCardInfo("Ravenform", 72, Rarity.COMMON, mage.cards.r.Ravenform.class));
        cards.add(new SetCardInfo("Realmwalker", 188, Rarity.RARE, mage.cards.r.Realmwalker.class));
        cards.add(new SetCardInfo("Renegade Reaper", 386, Rarity.UNCOMMON, mage.cards.r.RenegadeReaper.class));
        cards.add(new SetCardInfo("Replicating Ring", 244, Rarity.UNCOMMON, mage.cards.r.ReplicatingRing.class));
        cards.add(new SetCardInfo("Rimewood Falls", 266, Rarity.COMMON, mage.cards.r.RimewoodFalls.class));
        cards.add(new SetCardInfo("Sarulf's Packmate", 192, Rarity.COMMON, mage.cards.s.SarulfsPackmate.class));
        cards.add(new SetCardInfo("Sarulf, Realm Eater", 228, Rarity.RARE, mage.cards.s.SarulfRealmEater.class));
        cards.add(new SetCardInfo("Saw It Coming", 76, Rarity.UNCOMMON, mage.cards.s.SawItComing.class));
        cards.add(new SetCardInfo("Sculptor of Winter", 193, Rarity.COMMON, mage.cards.s.SculptorOfWinter.class));
        cards.add(new SetCardInfo("Seize the Spoils", 149, Rarity.COMMON, mage.cards.s.SeizeTheSpoils.class));
        cards.add(new SetCardInfo("Showdown of the Skalds", 229, Rarity.RARE, mage.cards.s.ShowdownOfTheSkalds.class));
        cards.add(new SetCardInfo("Sigrid, God-Favored", 29, Rarity.RARE, mage.cards.s.SigridGodFavored.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 284, Rarity.LAND, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 278, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 282, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 276, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 280, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Snowfield Sinkhole", 269, Rarity.COMMON, mage.cards.s.SnowfieldSinkhole.class));
        cards.add(new SetCardInfo("Spirit of the Aldergard", 195, Rarity.UNCOMMON, mage.cards.s.SpiritOfTheAldergard.class));
        cards.add(new SetCardInfo("Starnheim Aspirant", 380, Rarity.UNCOMMON, mage.cards.s.StarnheimAspirant.class));
        cards.add(new SetCardInfo("Sulfurous Mine", 270, Rarity.COMMON, mage.cards.s.SulfurousMine.class));
        cards.add(new SetCardInfo("Surtland Elementalist", 375, Rarity.RARE, mage.cards.s.SurtlandElementalist.class));
        cards.add(new SetCardInfo("Surtland Flinger", 377, Rarity.RARE, mage.cards.s.SurtlandFlinger.class));
        cards.add(new SetCardInfo("The Trickster-God's Heist", 232, Rarity.UNCOMMON, mage.cards.t.TheTricksterGodsHeist.class));
        cards.add(new SetCardInfo("The World Tree", 275, Rarity.RARE, mage.cards.t.TheWorldTree.class));
        cards.add(new SetCardInfo("Thornmantle Striker", 387, Rarity.UNCOMMON, mage.cards.t.ThornmantleStriker.class));
        cards.add(new SetCardInfo("Toski, Bearer of Secrets", 197, Rarity.RARE, mage.cards.t.ToskiBearerOfSecrets.class));
        cards.add(new SetCardInfo("Undersea Invader", 78, Rarity.COMMON, mage.cards.u.UnderseaInvader.class));
        cards.add(new SetCardInfo("Valkyrie Harbinger", 374, Rarity.RARE, mage.cards.v.ValkyrieHarbinger.class));
        cards.add(new SetCardInfo("Varragoth, Bloodsky Sire", 115, Rarity.RARE, mage.cards.v.VarragothBloodskySire.class));
        cards.add(new SetCardInfo("Village Rites", 117, Rarity.COMMON, mage.cards.v.VillageRites.class));
        cards.add(new SetCardInfo("Volatile Fjord", 273, Rarity.COMMON, mage.cards.v.VolatileFjord.class));
        cards.add(new SetCardInfo("Warchanter Skald", 381, Rarity.UNCOMMON, mage.cards.w.WarchanterSkald.class));
        cards.add(new SetCardInfo("Woodland Chasm", 274, Rarity.COMMON, mage.cards.w.WoodlandChasm.class));
        cards.add(new SetCardInfo("Youthful Valkyrie", 382, Rarity.UNCOMMON, mage.cards.y.YouthfulValkyrie.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
