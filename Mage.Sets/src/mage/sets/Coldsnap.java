package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.List;

/**
 * @author North
 */
public final class Coldsnap extends ExpansionSet {

    private static final Coldsnap instance = new Coldsnap();

    public static Coldsnap getInstance() {
        return instance;
    }

    private Coldsnap() {
        super("Coldsnap", "CSP", ExpansionSet.buildDate(2006, 6, 21), SetType.EXPANSION);
        this.blockName = "Ice Age";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        this.parentSet = IceAge.getInstance();
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Adarkar Valkyrie", 1, Rarity.RARE, mage.cards.a.AdarkarValkyrie.class));
        cards.add(new SetCardInfo("Adarkar Windform", 26, Rarity.UNCOMMON, mage.cards.a.AdarkarWindform.class));
        cards.add(new SetCardInfo("Allosaurus Rider", 101, Rarity.RARE, mage.cards.a.AllosaurusRider.class));
        cards.add(new SetCardInfo("Arctic Flats", 143, Rarity.UNCOMMON, mage.cards.a.ArcticFlats.class));
        cards.add(new SetCardInfo("Arctic Nishoba", 102, Rarity.UNCOMMON, mage.cards.a.ArcticNishoba.class));
        cards.add(new SetCardInfo("Arcum Dagsson", 27, Rarity.RARE, mage.cards.a.ArcumDagsson.class));
        cards.add(new SetCardInfo("Aurochs Herd", 103, Rarity.COMMON, mage.cards.a.AurochsHerd.class));
        cards.add(new SetCardInfo("Balduvian Fallen", 51, Rarity.UNCOMMON, mage.cards.b.BalduvianFallen.class));
        cards.add(new SetCardInfo("Balduvian Frostwaker", 28, Rarity.UNCOMMON, mage.cards.b.BalduvianFrostwaker.class));
        cards.add(new SetCardInfo("Balduvian Rage", 76, Rarity.UNCOMMON, mage.cards.b.BalduvianRage.class));
        cards.add(new SetCardInfo("Balduvian Warlord", 77, Rarity.UNCOMMON, mage.cards.b.BalduvianWarlord.class));
        cards.add(new SetCardInfo("Blizzard Specter", 126, Rarity.UNCOMMON, mage.cards.b.BlizzardSpecter.class));
        cards.add(new SetCardInfo("Boreal Centaur", 104, Rarity.COMMON, mage.cards.b.BorealCentaur.class));
        cards.add(new SetCardInfo("Boreal Druid", 105, Rarity.COMMON, mage.cards.b.BorealDruid.class));
        cards.add(new SetCardInfo("Boreal Griffin", 2, Rarity.COMMON, mage.cards.b.BorealGriffin.class));
        cards.add(new SetCardInfo("Boreal Shelf", 144, Rarity.UNCOMMON, mage.cards.b.BorealShelf.class));
        cards.add(new SetCardInfo("Braid of Fire", 78, Rarity.RARE, mage.cards.b.BraidOfFire.class));
        cards.add(new SetCardInfo("Brooding Saurian", 106, Rarity.RARE, mage.cards.b.BroodingSaurian.class));
        cards.add(new SetCardInfo("Bull Aurochs", 107, Rarity.COMMON, mage.cards.b.BullAurochs.class));
        cards.add(new SetCardInfo("Chilling Shade", 53, Rarity.COMMON, mage.cards.c.ChillingShade.class));
        cards.add(new SetCardInfo("Chill to the Bone", 52, Rarity.COMMON, mage.cards.c.ChillToTheBone.class));
        cards.add(new SetCardInfo("Coldsteel Heart", 136, Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Commandeer", 29, Rarity.RARE, mage.cards.c.Commandeer.class));
        cards.add(new SetCardInfo("Controvert", 30, Rarity.UNCOMMON, mage.cards.c.Controvert.class));
        cards.add(new SetCardInfo("Counterbalance", 31, Rarity.UNCOMMON, mage.cards.c.Counterbalance.class));
        cards.add(new SetCardInfo("Cover of Winter", 3, Rarity.RARE, mage.cards.c.CoverOfWinter.class));
        cards.add(new SetCardInfo("Cryoclasm", 79, Rarity.UNCOMMON, mage.cards.c.Cryoclasm.class));
        cards.add(new SetCardInfo("Darien, King of Kjeldor", 4, Rarity.RARE, mage.cards.d.DarienKingOfKjeldor.class));
        cards.add(new SetCardInfo("Dark Depths", 145, Rarity.RARE, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Deathmark", 54, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Deepfire Elemental", 127, Rarity.UNCOMMON, mage.cards.d.DeepfireElemental.class));
        cards.add(new SetCardInfo("Diamond Faerie", 128, Rarity.RARE, mage.cards.d.DiamondFaerie.class));
        cards.add(new SetCardInfo("Disciple of Tevesh Szat", 55, Rarity.COMMON, mage.cards.d.DiscipleOfTeveshSzat.class));
        cards.add(new SetCardInfo("Drelnoch", 32, Rarity.COMMON, mage.cards.d.Drelnoch.class));
        cards.add(new SetCardInfo("Earthen Goo", 80, Rarity.UNCOMMON, mage.cards.e.EarthenGoo.class));
        cards.add(new SetCardInfo("Feast of Flesh", 56, Rarity.COMMON, mage.cards.f.FeastOfFlesh.class));
        cards.add(new SetCardInfo("Field Marshal", 5, Rarity.RARE, mage.cards.f.FieldMarshal.class));
        cards.add(new SetCardInfo("Flashfreeze", 33, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Freyalise's Radiance", 108, Rarity.UNCOMMON, mage.cards.f.FreyalisesRadiance.class));
        cards.add(new SetCardInfo("Frost Marsh", 146, Rarity.UNCOMMON, mage.cards.f.FrostMarsh.class));
        cards.add(new SetCardInfo("Frost Raptor", 34, Rarity.COMMON, mage.cards.f.FrostRaptor.class));
        cards.add(new SetCardInfo("Frostweb Spider", 109, Rarity.COMMON, mage.cards.f.FrostwebSpider.class));
        cards.add(new SetCardInfo("Frozen Solid", 35, Rarity.COMMON, mage.cards.f.FrozenSolid.class));
        cards.add(new SetCardInfo("Fury of the Horde", 81, Rarity.RARE, mage.cards.f.FuryOfTheHorde.class));
        cards.add(new SetCardInfo("Garza Zol, Plague Queen", 129, Rarity.RARE, mage.cards.g.GarzaZolPlagueQueen.class));
        cards.add(new SetCardInfo("Garza's Assassin", 57, Rarity.RARE, mage.cards.g.GarzasAssassin.class));
        cards.add(new SetCardInfo("Gelid Shackles", 6, Rarity.COMMON, mage.cards.g.GelidShackles.class));
        cards.add(new SetCardInfo("Glacial Plating", 7, Rarity.UNCOMMON, mage.cards.g.GlacialPlating.class));
        cards.add(new SetCardInfo("Goblin Furrier", 82, Rarity.COMMON, mage.cards.g.GoblinFurrier.class));
        cards.add(new SetCardInfo("Goblin Rimerunner", 83, Rarity.COMMON, mage.cards.g.GoblinRimerunner.class));
        cards.add(new SetCardInfo("Greater Stone Spirit", 84, Rarity.UNCOMMON, mage.cards.g.GreaterStoneSpirit.class));
        cards.add(new SetCardInfo("Grim Harvest", 58, Rarity.COMMON, mage.cards.g.GrimHarvest.class));
        cards.add(new SetCardInfo("Gristle Grinner", 59, Rarity.UNCOMMON, mage.cards.g.GristleGrinner.class));
        cards.add(new SetCardInfo("Gutless Ghoul", 60, Rarity.COMMON, mage.cards.g.GutlessGhoul.class));
        cards.add(new SetCardInfo("Haakon, Stromgald Scourge", 61, Rarity.RARE, mage.cards.h.HaakonStromgaldScourge.class));
        cards.add(new SetCardInfo("Heidar, Rimewind Master", 36, Rarity.RARE, mage.cards.h.HeidarRimewindMaster.class));
        cards.add(new SetCardInfo("Herald of Leshrac", 62, Rarity.RARE, mage.cards.h.HeraldOfLeshrac.class));
        cards.add(new SetCardInfo("Hibernation's End", 110, Rarity.RARE, mage.cards.h.HibernationsEnd.class));
        cards.add(new SetCardInfo("Highland Weald", 147, Rarity.UNCOMMON, mage.cards.h.HighlandWeald.class));
        cards.add(new SetCardInfo("Icefall", 85, Rarity.COMMON, mage.cards.i.Icefall.class));
        cards.add(new SetCardInfo("Into the North", 111, Rarity.COMMON, mage.cards.i.IntoTheNorth.class));
        cards.add(new SetCardInfo("Jester's Scepter", 137, Rarity.RARE, mage.cards.j.JestersScepter.class));
        cards.add(new SetCardInfo("Jokulmorder", 37, Rarity.RARE, mage.cards.j.Jokulmorder.class));
        cards.add(new SetCardInfo("Jotun Grunt", 8, Rarity.UNCOMMON, mage.cards.j.JotunGrunt.class));
        cards.add(new SetCardInfo("Jotun Owl Keeper", 9, Rarity.UNCOMMON, mage.cards.j.JotunOwlKeeper.class));
        cards.add(new SetCardInfo("Juniper Order Ranger", 130, Rarity.UNCOMMON, mage.cards.j.JuniperOrderRanger.class));
        cards.add(new SetCardInfo("Karplusan Minotaur", 86, Rarity.RARE, mage.cards.k.KarplusanMinotaur.class));
        cards.add(new SetCardInfo("Karplusan Strider", 112, Rarity.UNCOMMON, mage.cards.k.KarplusanStrider.class));
        cards.add(new SetCardInfo("Karplusan Wolverine", 87, Rarity.COMMON, mage.cards.k.KarplusanWolverine.class));
        cards.add(new SetCardInfo("Kjeldoran Gargoyle", 10, Rarity.UNCOMMON, mage.cards.k.KjeldoranGargoyle.class));
        cards.add(new SetCardInfo("Kjeldoran Javelineer", 11, Rarity.COMMON, mage.cards.k.KjeldoranJavelineer.class));
        cards.add(new SetCardInfo("Kjeldoran Outrider", 12, Rarity.COMMON, mage.cards.k.KjeldoranOutrider.class));
        cards.add(new SetCardInfo("Kjeldoran War Cry", 13, Rarity.COMMON, mage.cards.k.KjeldoranWarCry.class));
        cards.add(new SetCardInfo("Krovikan Mist", 38, Rarity.COMMON, mage.cards.k.KrovikanMist.class));
        cards.add(new SetCardInfo("Krovikan Rot", 63, Rarity.UNCOMMON, mage.cards.k.KrovikanRot.class));
        cards.add(new SetCardInfo("Krovikan Scoundrel", 64, Rarity.COMMON, mage.cards.k.KrovikanScoundrel.class));
        cards.add(new SetCardInfo("Krovikan Whispers", 39, Rarity.UNCOMMON, mage.cards.k.KrovikanWhispers.class));
        cards.add(new SetCardInfo("Lightning Serpent", 88, Rarity.RARE, mage.cards.l.LightningSerpent.class));
        cards.add(new SetCardInfo("Lightning Storm", 89, Rarity.UNCOMMON, mage.cards.l.LightningStorm.class));
        cards.add(new SetCardInfo("Lovisa Coldeyes", 90, Rarity.RARE, mage.cards.l.LovisaColdeyes.class));
        cards.add(new SetCardInfo("Luminesce", 14, Rarity.UNCOMMON, mage.cards.l.Luminesce.class));
        cards.add(new SetCardInfo("Magmatic Core", 91, Rarity.UNCOMMON, mage.cards.m.MagmaticCore.class));
        cards.add(new SetCardInfo("Martyr of Ashes", 92, Rarity.COMMON, mage.cards.m.MartyrOfAshes.class));
        cards.add(new SetCardInfo("Martyr of Bones", 65, Rarity.COMMON, mage.cards.m.MartyrOfBones.class));
        cards.add(new SetCardInfo("Martyr of Frost", 40, Rarity.COMMON, mage.cards.m.MartyrOfFrost.class));
        cards.add(new SetCardInfo("Martyr of Sands", 15, Rarity.COMMON, mage.cards.m.MartyrOfSands.class));
        cards.add(new SetCardInfo("Martyr of Spores", 113, Rarity.COMMON, mage.cards.m.MartyrOfSpores.class));
        cards.add(new SetCardInfo("Mishra's Bauble", 138, Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class));
        cards.add(new SetCardInfo("Mouth of Ronom", 148, Rarity.UNCOMMON, mage.cards.m.MouthOfRonom.class));
        cards.add(new SetCardInfo("Mystic Melting", 114, Rarity.UNCOMMON, mage.cards.m.MysticMelting.class));
        cards.add(new SetCardInfo("Ohran Viper", 115, Rarity.RARE, mage.cards.o.OhranViper.class));
        cards.add(new SetCardInfo("Ohran Yeti", 93, Rarity.COMMON, mage.cards.o.OhranYeti.class));
        cards.add(new SetCardInfo("Orcish Bloodpainter", 94, Rarity.COMMON, mage.cards.o.OrcishBloodpainter.class));
        cards.add(new SetCardInfo("Panglacial Wurm", 116, Rarity.RARE, mage.cards.p.PanglacialWurm.class));
        cards.add(new SetCardInfo("Perilous Research", 41, Rarity.UNCOMMON, mage.cards.p.PerilousResearch.class));
        cards.add(new SetCardInfo("Phobian Phantasm", 66, Rarity.UNCOMMON, mage.cards.p.PhobianPhantasm.class));
        cards.add(new SetCardInfo("Phyrexian Etchings", 67, Rarity.RARE, mage.cards.p.PhyrexianEtchings.class));
        cards.add(new SetCardInfo("Phyrexian Ironfoot", 139, Rarity.UNCOMMON, mage.cards.p.PhyrexianIronfoot.class));
        cards.add(new SetCardInfo("Phyrexian Snowcrusher", 140, Rarity.UNCOMMON, mage.cards.p.PhyrexianSnowcrusher.class));
        cards.add(new SetCardInfo("Phyrexian Soulgorger", 141, Rarity.RARE, mage.cards.p.PhyrexianSoulgorger.class));
        cards.add(new SetCardInfo("Resize", 117, Rarity.UNCOMMON, mage.cards.r.Resize.class));
        cards.add(new SetCardInfo("Rimebound Dead", 69, Rarity.COMMON, mage.cards.r.RimeboundDead.class));
        cards.add(new SetCardInfo("Rime Transfusion", 68, Rarity.UNCOMMON, mage.cards.r.RimeTransfusion.class));
        cards.add(new SetCardInfo("Rimefeather Owl", 42, Rarity.RARE, mage.cards.r.RimefeatherOwl.class));
        cards.add(new SetCardInfo("Rimehorn Aurochs", 118, Rarity.UNCOMMON, mage.cards.r.RimehornAurochs.class));
        cards.add(new SetCardInfo("Rimescale Dragon", 95, Rarity.RARE, mage.cards.r.RimescaleDragon.class));
        cards.add(new SetCardInfo("Rimewind Cryomancer", 43, Rarity.UNCOMMON, mage.cards.r.RimewindCryomancer.class));
        cards.add(new SetCardInfo("Rimewind Taskmage", 44, Rarity.COMMON, mage.cards.r.RimewindTaskmage.class));
        cards.add(new SetCardInfo("Rite of Flame", 96, Rarity.COMMON, mage.cards.r.RiteOfFlame.class));
        cards.add(new SetCardInfo("Ronom Hulk", 119, Rarity.COMMON, mage.cards.r.RonomHulk.class));
        cards.add(new SetCardInfo("Ronom Serpent", 45, Rarity.COMMON, mage.cards.r.RonomSerpent.class));
        cards.add(new SetCardInfo("Ronom Unicorn", 16, Rarity.COMMON, mage.cards.r.RonomUnicorn.class));
        cards.add(new SetCardInfo("Rune Snag", 46, Rarity.COMMON, mage.cards.r.RuneSnag.class));
        cards.add(new SetCardInfo("Scrying Sheets", 149, Rarity.RARE, mage.cards.s.ScryingSheets.class));
        cards.add(new SetCardInfo("Sek'Kuar, Deathkeeper", 131, Rarity.RARE, mage.cards.s.SekKuarDeathkeeper.class));
        cards.add(new SetCardInfo("Shape of the Wiitigo", 120, Rarity.RARE, mage.cards.s.ShapeOfTheWiitigo.class));
        cards.add(new SetCardInfo("Sheltering Ancient", 121, Rarity.UNCOMMON, mage.cards.s.ShelteringAncient.class));
        cards.add(new SetCardInfo("Simian Brawler", 122, Rarity.COMMON, mage.cards.s.SimianBrawler.class));
        cards.add(new SetCardInfo("Skred", 97, Rarity.COMMON, mage.cards.s.Skred.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 155, Rarity.LAND, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 152, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 154, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 151, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 153, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Soul Spike", 70, Rarity.RARE, mage.cards.s.SoulSpike.class));
        cards.add(new SetCardInfo("Sound the Call", 123, Rarity.COMMON, mage.cards.s.SoundTheCall.class));
        cards.add(new SetCardInfo("Squall Drifter", 17, Rarity.COMMON, mage.cards.s.SquallDrifter.class));
        cards.add(new SetCardInfo("Stalking Yeti", 98, Rarity.UNCOMMON, mage.cards.s.StalkingYeti.class));
        cards.add(new SetCardInfo("Steam Spitter", 124, Rarity.UNCOMMON, mage.cards.s.SteamSpitter.class));
        cards.add(new SetCardInfo("Stromgald Crusader", 71, Rarity.UNCOMMON, mage.cards.s.StromgaldCrusader.class));
        cards.add(new SetCardInfo("Sun's Bounty", 18, Rarity.COMMON, mage.cards.s.SunsBounty.class));
        cards.add(new SetCardInfo("Sunscour", 19, Rarity.RARE, mage.cards.s.Sunscour.class));
        cards.add(new SetCardInfo("Surging Aether", 47, Rarity.COMMON, mage.cards.s.SurgingAether.class));
        cards.add(new SetCardInfo("Surging Dementia", 72, Rarity.COMMON, mage.cards.s.SurgingDementia.class));
        cards.add(new SetCardInfo("Surging Flame", 99, Rarity.COMMON, mage.cards.s.SurgingFlame.class));
        cards.add(new SetCardInfo("Surging Might", 125, Rarity.COMMON, mage.cards.s.SurgingMight.class));
        cards.add(new SetCardInfo("Surging Sentinels", 20, Rarity.COMMON, mage.cards.s.SurgingSentinels.class));
        cards.add(new SetCardInfo("Survivor of the Unseen", 48, Rarity.COMMON, mage.cards.s.SurvivorOfTheUnseen.class));
        cards.add(new SetCardInfo("Swift Maneuver", 21, Rarity.COMMON, mage.cards.s.SwiftManeuver.class));
        cards.add(new SetCardInfo("Tamanoa", 132, Rarity.RARE, mage.cards.t.Tamanoa.class));
        cards.add(new SetCardInfo("Thermal Flux", 49, Rarity.COMMON, mage.cards.t.ThermalFlux.class));
        cards.add(new SetCardInfo("Thermopod", 100, Rarity.COMMON, mage.cards.t.Thermopod.class));
        cards.add(new SetCardInfo("Thrumming Stone", 142, Rarity.RARE, mage.cards.t.ThrummingStone.class));
        cards.add(new SetCardInfo("Tresserhorn Sinks", 150, Rarity.UNCOMMON, mage.cards.t.TresserhornSinks.class));
        cards.add(new SetCardInfo("Tresserhorn Skyknight", 73, Rarity.UNCOMMON, mage.cards.t.TresserhornSkyknight.class));
        cards.add(new SetCardInfo("Ursine Fylgja", 22, Rarity.UNCOMMON, mage.cards.u.UrsineFylgja.class));
        cards.add(new SetCardInfo("Vanish into Memory", 133, Rarity.UNCOMMON, mage.cards.v.VanishIntoMemory.class));
        cards.add(new SetCardInfo("Vexing Sphinx", 50, Rarity.RARE, mage.cards.v.VexingSphinx.class));
        cards.add(new SetCardInfo("Void Maw", 74, Rarity.RARE, mage.cards.v.VoidMaw.class));
        cards.add(new SetCardInfo("Wall of Shards", 23, Rarity.UNCOMMON, mage.cards.w.WallOfShards.class));
        cards.add(new SetCardInfo("White Shield Crusader", 24, Rarity.UNCOMMON, mage.cards.w.WhiteShieldCrusader.class));
        cards.add(new SetCardInfo("Wilderness Elemental", 134, Rarity.UNCOMMON, mage.cards.w.WildernessElemental.class));
        cards.add(new SetCardInfo("Woolly Razorback", 25, Rarity.RARE, mage.cards.w.WoollyRazorback.class));
        cards.add(new SetCardInfo("Zombie Musher", 75, Rarity.COMMON, mage.cards.z.ZombieMusher.class));
        cards.add(new SetCardInfo("Zur the Enchanter", 135, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class));
    }

    @Override
    protected List<CardInfo> findCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findCardsByRarity(rarity);
        if (rarity == Rarity.COMMON) {
            // Snow basic lands are included in boosters as regular commons
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria().setCodes(this.code).rarities(Rarity.LAND)));
        }
        return cardInfos;
    }
}
