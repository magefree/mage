package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

public final class WarOfTheSpark extends ExpansionSet {

    private static final WarOfTheSpark instance = new WarOfTheSpark();

    public static WarOfTheSpark getInstance() {
        return instance;
    }

    private WarOfTheSpark() {
        super("War of the Spark", "WAR", ExpansionSet.buildDate(2019, 5, 3), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.needsPlaneswalker = true;
        this.maxCardNumberInBooster = 264;

        cards.add(new SetCardInfo("Ahn-Crop Invader", 113, Rarity.COMMON, mage.cards.a.AhnCropInvader.class));
        cards.add(new SetCardInfo("Aid the Fallen", 76, Rarity.COMMON, mage.cards.a.AidTheFallen.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 4, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, the Greathearted", "184*", Rarity.RARE, mage.cards.a.AjaniTheGreathearted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani, the Greathearted", 184, Rarity.RARE, mage.cards.a.AjaniTheGreathearted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angrath's Rampage", 185, Rarity.UNCOMMON, mage.cards.a.AngrathsRampage.class));
        cards.add(new SetCardInfo("Angrath, Captain of Chaos", "227*", Rarity.UNCOMMON, mage.cards.a.AngrathCaptainOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angrath, Captain of Chaos", 227, Rarity.UNCOMMON, mage.cards.a.AngrathCaptainOfChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arboreal Grazer", 149, Rarity.COMMON, mage.cards.a.ArborealGrazer.class));
        cards.add(new SetCardInfo("Arlinn's Wolf", 151, Rarity.COMMON, mage.cards.a.ArlinnsWolf.class));
        cards.add(new SetCardInfo("Arlinn, Voice of the Pack", "150*", Rarity.UNCOMMON, mage.cards.a.ArlinnVoiceOfThePack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arlinn, Voice of the Pack", 150, Rarity.UNCOMMON, mage.cards.a.ArlinnVoiceOfThePack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashiok's Skulker", 40, Rarity.COMMON, mage.cards.a.AshioksSkulker.class));
        cards.add(new SetCardInfo("Ashiok, Dream Render", "228*", Rarity.UNCOMMON, mage.cards.a.AshiokDreamRender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashiok, Dream Render", 228, Rarity.UNCOMMON, mage.cards.a.AshiokDreamRender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Augur of Bolas", 41, Rarity.UNCOMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Aven Eternal", 42, Rarity.COMMON, mage.cards.a.AvenEternal.class));
        cards.add(new SetCardInfo("Awakening of Vitu-Ghazi", 152, Rarity.RARE, mage.cards.a.AwakeningOfVituGhazi.class));
        cards.add(new SetCardInfo("Band Together", 153, Rarity.COMMON, mage.cards.b.BandTogether.class));
        cards.add(new SetCardInfo("Banehound", 77, Rarity.COMMON, mage.cards.b.Banehound.class));
        cards.add(new SetCardInfo("Battlefield Promotion", 5, Rarity.COMMON, mage.cards.b.BattlefieldPromotion.class));
        cards.add(new SetCardInfo("Bioessence Hydra", 186, Rarity.RARE, mage.cards.b.BioessenceHydra.class));
        cards.add(new SetCardInfo("Blast Zone", 244, Rarity.RARE, mage.cards.b.BlastZone.class));
        cards.add(new SetCardInfo("Bleeding Edge", 78, Rarity.UNCOMMON, mage.cards.b.BleedingEdge.class));
        cards.add(new SetCardInfo("Blindblast", 114, Rarity.COMMON, mage.cards.b.Blindblast.class));
        cards.add(new SetCardInfo("Bloom Hulk", 154, Rarity.COMMON, mage.cards.b.BloomHulk.class));
        cards.add(new SetCardInfo("Bolas's Citadel", 79, Rarity.RARE, mage.cards.b.BolassCitadel.class));
        cards.add(new SetCardInfo("Bolt Bend", 115, Rarity.UNCOMMON, mage.cards.b.BoltBend.class));
        cards.add(new SetCardInfo("Bond of Discipline", 6, Rarity.UNCOMMON, mage.cards.b.BondOfDiscipline.class));
        cards.add(new SetCardInfo("Bond of Flourishing", 155, Rarity.UNCOMMON, mage.cards.b.BondOfFlourishing.class));
        cards.add(new SetCardInfo("Bond of Insight", 43, Rarity.UNCOMMON, mage.cards.b.BondOfInsight.class));
        cards.add(new SetCardInfo("Bond of Passion", 116, Rarity.UNCOMMON, mage.cards.b.BondOfPassion.class));
        cards.add(new SetCardInfo("Bond of Revival", 80, Rarity.UNCOMMON, mage.cards.b.BondOfRevival.class));
        cards.add(new SetCardInfo("Bulwark Giant", 7, Rarity.COMMON, mage.cards.b.BulwarkGiant.class));
        cards.add(new SetCardInfo("Burning Prophet", 117, Rarity.COMMON, mage.cards.b.BurningProphet.class));
        cards.add(new SetCardInfo("Callous Dismissal", 44, Rarity.COMMON, mage.cards.c.CallousDismissal.class));
        cards.add(new SetCardInfo("Casualties of War", 187, Rarity.RARE, mage.cards.c.CasualtiesOfWar.class));
        cards.add(new SetCardInfo("Centaur Nurturer", 156, Rarity.COMMON, mage.cards.c.CentaurNurturer.class));
        cards.add(new SetCardInfo("Chainwhip Cyclops", 118, Rarity.COMMON, mage.cards.c.ChainwhipCyclops.class));
        cards.add(new SetCardInfo("Challenger Troll", 157, Rarity.UNCOMMON, mage.cards.c.ChallengerTroll.class));
        cards.add(new SetCardInfo("Chandra's Pyrohelix", 120, Rarity.COMMON, mage.cards.c.ChandrasPyrohelix.class));
        cards.add(new SetCardInfo("Chandra's Triumph", 121, Rarity.UNCOMMON, mage.cards.c.ChandrasTriumph.class));
        cards.add(new SetCardInfo("Chandra, Fire Artisan", "119*", Rarity.RARE, mage.cards.c.ChandraFireArtisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Fire Artisan", 119, Rarity.RARE, mage.cards.c.ChandraFireArtisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Charity Extractor", 81, Rarity.COMMON, mage.cards.c.CharityExtractor.class));
        cards.add(new SetCardInfo("Charmed Stray", 8, Rarity.COMMON, mage.cards.c.CharmedStray.class));
        cards.add(new SetCardInfo("Command the Dreadhorde", 82, Rarity.RARE, mage.cards.c.CommandTheDreadhorde.class));
        cards.add(new SetCardInfo("Commence the Endgame", 45, Rarity.RARE, mage.cards.c.CommenceTheEndgame.class));
        cards.add(new SetCardInfo("Contentious Plan", 46, Rarity.COMMON, mage.cards.c.ContentiousPlan.class));
        cards.add(new SetCardInfo("Courage in Crisis", 158, Rarity.COMMON, mage.cards.c.CourageInCrisis.class));
        cards.add(new SetCardInfo("Cruel Celebrant", 188, Rarity.UNCOMMON, mage.cards.c.CruelCelebrant.class));
        cards.add(new SetCardInfo("Crush Dissent", 47, Rarity.COMMON, mage.cards.c.CrushDissent.class));
        cards.add(new SetCardInfo("Cyclops Electromancer", 122, Rarity.UNCOMMON, mage.cards.c.CyclopsElectromancer.class));
        cards.add(new SetCardInfo("Davriel's Shadowfugue", 84, Rarity.COMMON, mage.cards.d.DavrielsShadowfugue.class));
        cards.add(new SetCardInfo("Davriel, Rogue Shadowmage", "83*", Rarity.UNCOMMON, mage.cards.d.DavrielRogueShadowmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Davriel, Rogue Shadowmage", 83, Rarity.UNCOMMON, mage.cards.d.DavrielRogueShadowmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathsprout", 189, Rarity.UNCOMMON, mage.cards.d.Deathsprout.class));
        cards.add(new SetCardInfo("Defiant Strike", 9, Rarity.COMMON, mage.cards.d.DefiantStrike.class));
        cards.add(new SetCardInfo("Deliver Unto Evil", 85, Rarity.RARE, mage.cards.d.DeliverUntoEvil.class));
        cards.add(new SetCardInfo("Demolish", 123, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Despark", 190, Rarity.UNCOMMON, mage.cards.d.Despark.class));
        cards.add(new SetCardInfo("Desperate Lunge", 266, Rarity.COMMON, mage.cards.d.DesperateLunge.class));
        cards.add(new SetCardInfo("Devouring Hellion", 124, Rarity.UNCOMMON, mage.cards.d.DevouringHellion.class));
        cards.add(new SetCardInfo("Divine Arrow", 10, Rarity.COMMON, mage.cards.d.DivineArrow.class));
        cards.add(new SetCardInfo("Domri's Ambush", 192, Rarity.UNCOMMON, mage.cards.d.DomrisAmbush.class));
        cards.add(new SetCardInfo("Domri, Anarch of Bolas", "191*", Rarity.RARE, mage.cards.d.DomriAnarchOfBolas.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Domri, Anarch of Bolas", 191, Rarity.RARE, mage.cards.d.DomriAnarchOfBolas.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dovin's Veto", 193, Rarity.UNCOMMON, mage.cards.d.DovinsVeto.class));
        cards.add(new SetCardInfo("Dovin, Hand of Control", "229*", Rarity.UNCOMMON, mage.cards.d.DovinHandOfControl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dovin, Hand of Control", 229, Rarity.UNCOMMON, mage.cards.d.DovinHandOfControl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreadhorde Arcanist", 125, Rarity.RARE, mage.cards.d.DreadhordeArcanist.class));
        cards.add(new SetCardInfo("Dreadhorde Butcher", 194, Rarity.RARE, mage.cards.d.DreadhordeButcher.class));
        cards.add(new SetCardInfo("Dreadhorde Invasion", 86, Rarity.RARE, mage.cards.d.DreadhordeInvasion.class));
        cards.add(new SetCardInfo("Dreadhorde Twins", 126, Rarity.UNCOMMON, mage.cards.d.DreadhordeTwins.class));
        cards.add(new SetCardInfo("Dreadmalkin", 87, Rarity.UNCOMMON, mage.cards.d.Dreadmalkin.class));
        cards.add(new SetCardInfo("Duskmantle Operative", 88, Rarity.COMMON, mage.cards.d.DuskmantleOperative.class));
        cards.add(new SetCardInfo("Elite Guardmage", 195, Rarity.UNCOMMON, mage.cards.e.EliteGuardmage.class));
        cards.add(new SetCardInfo("Emergence Zone", 245, Rarity.UNCOMMON, mage.cards.e.EmergenceZone.class));
        cards.add(new SetCardInfo("Enforcer Griffin", 11, Rarity.COMMON, mage.cards.e.EnforcerGriffin.class));
        cards.add(new SetCardInfo("Enter the God-Eternals", 196, Rarity.RARE, mage.cards.e.EnterTheGodEternals.class));
        cards.add(new SetCardInfo("Erratic Visionary", 48, Rarity.COMMON, mage.cards.e.ErraticVisionary.class));
        cards.add(new SetCardInfo("Eternal Skylord", 49, Rarity.UNCOMMON, mage.cards.e.EternalSkylord.class));
        cards.add(new SetCardInfo("Eternal Taskmaster", 90, Rarity.UNCOMMON, mage.cards.e.EternalTaskmaster.class));
        cards.add(new SetCardInfo("Evolution Sage", 159, Rarity.UNCOMMON, mage.cards.e.EvolutionSage.class));
        cards.add(new SetCardInfo("Fblthp, the Lost", 50, Rarity.RARE, mage.cards.f.FblthpTheLost.class));
        cards.add(new SetCardInfo("Feather, the Redeemed", 197, Rarity.RARE, mage.cards.f.FeatherTheRedeemed.class));
        cards.add(new SetCardInfo("Finale of Devastation", 160, Rarity.MYTHIC, mage.cards.f.FinaleOfDevastation.class));
        cards.add(new SetCardInfo("Finale of Eternity", 91, Rarity.MYTHIC, mage.cards.f.FinaleOfEternity.class));
        cards.add(new SetCardInfo("Finale of Glory", 12, Rarity.MYTHIC, mage.cards.f.FinaleOfGlory.class));
        cards.add(new SetCardInfo("Finale of Promise", 127, Rarity.MYTHIC, mage.cards.f.FinaleOfPromise.class));
        cards.add(new SetCardInfo("Finale of Revelation", 51, Rarity.MYTHIC, mage.cards.f.FinaleOfRevelation.class));
        cards.add(new SetCardInfo("Firemind Vessel", 237, Rarity.UNCOMMON, mage.cards.f.FiremindVessel.class));
        cards.add(new SetCardInfo("Flux Channeler", 52, Rarity.UNCOMMON, mage.cards.f.FluxChanneler.class));
        cards.add(new SetCardInfo("Forced Landing", 161, Rarity.COMMON, mage.cards.f.ForcedLanding.class));
        cards.add(new SetCardInfo("Forest", 262, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 263, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gateway Plaza", 246, Rarity.COMMON, mage.cards.g.GatewayPlaza.class));
        cards.add(new SetCardInfo("Giant Growth", 162, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gideon Blackblade", "13*", Rarity.MYTHIC, mage.cards.g.GideonBlackblade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gideon Blackblade", 13, Rarity.MYTHIC, mage.cards.g.GideonBlackblade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gideon's Battle Cry", 267, Rarity.RARE, mage.cards.g.GideonsBattleCry.class));
        cards.add(new SetCardInfo("Gideon's Company", 268, Rarity.UNCOMMON, mage.cards.g.GideonsCompany.class));
        cards.add(new SetCardInfo("Gideon's Sacrifice", 14, Rarity.COMMON, mage.cards.g.GideonsSacrifice.class));
        cards.add(new SetCardInfo("Gideon's Triumph", 15, Rarity.UNCOMMON, mage.cards.g.GideonsTriumph.class));
        cards.add(new SetCardInfo("Gideon, the Oathsworn", 265, Rarity.MYTHIC, mage.cards.g.GideonTheOathsworn.class));
        cards.add(new SetCardInfo("Gleaming Overseer", 198, Rarity.UNCOMMON, mage.cards.g.GleamingOverseer.class));
        cards.add(new SetCardInfo("Goblin Assailant", 128, Rarity.COMMON, mage.cards.g.GoblinAssailant.class));
        cards.add(new SetCardInfo("Goblin Assault Team", 129, Rarity.COMMON, mage.cards.g.GoblinAssaultTeam.class));
        cards.add(new SetCardInfo("God-Eternal Bontu", 92, Rarity.MYTHIC, mage.cards.g.GodEternalBontu.class));
        cards.add(new SetCardInfo("God-Eternal Kefnet", 53, Rarity.MYTHIC, mage.cards.g.GodEternalKefnet.class));
        cards.add(new SetCardInfo("God-Eternal Oketra", 16, Rarity.MYTHIC, mage.cards.g.GodEternalOketra.class));
        cards.add(new SetCardInfo("God-Eternal Rhonas", 163, Rarity.MYTHIC, mage.cards.g.GodEternalRhonas.class));
        cards.add(new SetCardInfo("God-Pharaoh's Statue", 238, Rarity.UNCOMMON, mage.cards.g.GodPharaohsStatue.class));
        cards.add(new SetCardInfo("Grateful Apparition", 17, Rarity.UNCOMMON, mage.cards.g.GratefulApparition.class));
        cards.add(new SetCardInfo("Grim Initiate", 130, Rarity.COMMON, mage.cards.g.GrimInitiate.class));
        cards.add(new SetCardInfo("Guild Globe", 239, Rarity.COMMON, mage.cards.g.GuildGlobe.class));
        cards.add(new SetCardInfo("Guildpact Informant", 271, Rarity.COMMON, mage.cards.g.GuildpactInformant.class));
        cards.add(new SetCardInfo("Heartfire", 131, Rarity.COMMON, mage.cards.h.Heartfire.class));
        cards.add(new SetCardInfo("Heartwarming Redemption", 199, Rarity.UNCOMMON, mage.cards.h.HeartwarmingRedemption.class));
        cards.add(new SetCardInfo("Herald of the Dreadhorde", 93, Rarity.COMMON, mage.cards.h.HeraldOfTheDreadhorde.class));
        cards.add(new SetCardInfo("Honor the God-Pharaoh", 132, Rarity.COMMON, mage.cards.h.HonorTheGodPharaoh.class));
        cards.add(new SetCardInfo("Huatli's Raptor", 200, Rarity.UNCOMMON, mage.cards.h.HuatlisRaptor.class));
        cards.add(new SetCardInfo("Huatli, the Sun's Heart", "230*", Rarity.UNCOMMON, mage.cards.h.HuatliTheSunsHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Huatli, the Sun's Heart", 230, Rarity.UNCOMMON, mage.cards.h.HuatliTheSunsHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ignite the Beacon", 18, Rarity.RARE, mage.cards.i.IgniteTheBeacon.class));
        cards.add(new SetCardInfo("Ilharg, the Raze-Boar", 133, Rarity.MYTHIC, mage.cards.i.IlhargTheRazeBoar.class));
        cards.add(new SetCardInfo("Interplanar Beacon", 247, Rarity.UNCOMMON, mage.cards.i.InterplanarBeacon.class));
        cards.add(new SetCardInfo("Invade the City", 201, Rarity.UNCOMMON, mage.cards.i.InvadeTheCity.class));
        cards.add(new SetCardInfo("Invading Manticore", 134, Rarity.COMMON, mage.cards.i.InvadingManticore.class));
        cards.add(new SetCardInfo("Iron Bully", 240, Rarity.COMMON, mage.cards.i.IronBully.class));
        cards.add(new SetCardInfo("Ironclad Krovod", 19, Rarity.COMMON, mage.cards.i.IroncladKrovod.class));
        cards.add(new SetCardInfo("Island", 253, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace's Projection", 272, Rarity.UNCOMMON, mage.cards.j.JacesProjection.class));
        cards.add(new SetCardInfo("Jace's Ruse", 273, Rarity.RARE, mage.cards.j.JacesRuse.class));
        cards.add(new SetCardInfo("Jace's Triumph", 55, Rarity.UNCOMMON, mage.cards.j.JacesTriumph.class));
        cards.add(new SetCardInfo("Jace, Arcane Strategist", 270, Rarity.MYTHIC, mage.cards.j.JaceArcaneStrategist.class));
        cards.add(new SetCardInfo("Jace, Wielder of Mysteries", "54*", Rarity.RARE, mage.cards.j.JaceWielderOfMysteries.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Wielder of Mysteries", 54, Rarity.RARE, mage.cards.j.JaceWielderOfMysteries.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaya's Greeting", 136, Rarity.COMMON, mage.cards.j.JayasGreeting.class));
        cards.add(new SetCardInfo("Jaya, Venerated Firemage", "135*", Rarity.UNCOMMON, mage.cards.j.JayaVeneratedFiremage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaya, Venerated Firemage", 135, Rarity.UNCOMMON, mage.cards.j.JayaVeneratedFiremage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jiang Yanggu, Wildcrafter", "164*", Rarity.UNCOMMON, mage.cards.j.JiangYangguWildcrafter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jiang Yanggu, Wildcrafter", 164, Rarity.UNCOMMON, mage.cards.j.JiangYangguWildcrafter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn's Bastion", 248, Rarity.RARE, mage.cards.k.KarnsBastion.class));
        cards.add(new SetCardInfo("Karn, the Great Creator", "1*", Rarity.RARE, mage.cards.k.KarnTheGreatCreator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn, the Great Creator", 1, Rarity.RARE, mage.cards.k.KarnTheGreatCreator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kasmina's Transmutation", 57, Rarity.COMMON, mage.cards.k.KasminasTransmutation.class));
        cards.add(new SetCardInfo("Kasmina, Enigmatic Mentor", "56*", Rarity.UNCOMMON, mage.cards.k.KasminaEnigmaticMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kasmina, Enigmatic Mentor", 56, Rarity.UNCOMMON, mage.cards.k.KasminaEnigmaticMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya's Ghostform", 94, Rarity.COMMON, mage.cards.k.KayasGhostform.class));
        cards.add(new SetCardInfo("Kaya, Bane of the Dead", "231*", Rarity.UNCOMMON, mage.cards.k.KayaBaneOfTheDead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaya, Bane of the Dead", 231, Rarity.UNCOMMON, mage.cards.k.KayaBaneOfTheDead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kiora's Dambreaker", 58, Rarity.COMMON, mage.cards.k.KiorasDambreaker.class));
        cards.add(new SetCardInfo("Kiora, Behemoth Beckoner", "232*", Rarity.UNCOMMON, mage.cards.k.KioraBehemothBeckoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kiora, Behemoth Beckoner", 232, Rarity.UNCOMMON, mage.cards.k.KioraBehemothBeckoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraul Stinger", 165, Rarity.COMMON, mage.cards.k.KraulStinger.class));
        cards.add(new SetCardInfo("Krenko, Tin Street Kingpin", 137, Rarity.RARE, mage.cards.k.KrenkoTinStreetKingpin.class));
        cards.add(new SetCardInfo("Kronch Wrangler", 166, Rarity.COMMON, mage.cards.k.KronchWrangler.class));
        cards.add(new SetCardInfo("Law-Rune Enforcer", 20, Rarity.COMMON, mage.cards.l.LawRuneEnforcer.class));
        cards.add(new SetCardInfo("Lazotep Behemoth", 95, Rarity.COMMON, mage.cards.l.LazotepBehemoth.class));
        cards.add(new SetCardInfo("Lazotep Plating", 59, Rarity.UNCOMMON, mage.cards.l.LazotepPlating.class));
        cards.add(new SetCardInfo("Lazotep Reaver", 96, Rarity.COMMON, mage.cards.l.LazotepReaver.class));
        cards.add(new SetCardInfo("Leyline Prowler", 202, Rarity.UNCOMMON, mage.cards.l.LeylineProwler.class));
        cards.add(new SetCardInfo("Liliana's Triumph", 98, Rarity.UNCOMMON, mage.cards.l.LilianasTriumph.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", "97*", Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", 97, Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living Twister", 203, Rarity.RARE, mage.cards.l.LivingTwister.class));
        cards.add(new SetCardInfo("Loxodon Sergeant", 21, Rarity.COMMON, mage.cards.l.LoxodonSergeant.class));
        cards.add(new SetCardInfo("Makeshift Battalion", 22, Rarity.COMMON, mage.cards.m.MakeshiftBattalion.class));
        cards.add(new SetCardInfo("Mana Geode", 241, Rarity.COMMON, mage.cards.m.ManaGeode.class));
        cards.add(new SetCardInfo("Martyr for the Cause", 23, Rarity.COMMON, mage.cards.m.MartyrForTheCause.class));
        cards.add(new SetCardInfo("Massacre Girl", 99, Rarity.RARE, mage.cards.m.MassacreGirl.class));
        cards.add(new SetCardInfo("Mayhem Devil", 204, Rarity.UNCOMMON, mage.cards.m.MayhemDevil.class));
        cards.add(new SetCardInfo("Merfolk Skydiver", 205, Rarity.UNCOMMON, mage.cards.m.MerfolkSkydiver.class));
        cards.add(new SetCardInfo("Mizzium Tank", 138, Rarity.RARE, mage.cards.m.MizziumTank.class));
        cards.add(new SetCardInfo("Mobilized District", 249, Rarity.RARE, mage.cards.m.MobilizedDistrict.class));
        cards.add(new SetCardInfo("Mountain", 259, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 260, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 261, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mowu, Loyal Companion", 167, Rarity.UNCOMMON, mage.cards.m.MowuLoyalCompanion.class));
        cards.add(new SetCardInfo("Naga Eternal", 60, Rarity.COMMON, mage.cards.n.NagaEternal.class));
        cards.add(new SetCardInfo("Nahiri's Stoneblades", 139, Rarity.COMMON, mage.cards.n.NahirisStoneblades.class));
        cards.add(new SetCardInfo("Nahiri, Storm of Stone", "233*", Rarity.UNCOMMON, mage.cards.n.NahiriStormOfStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nahiri, Storm of Stone", 233, Rarity.UNCOMMON, mage.cards.n.NahiriStormOfStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Narset's Reversal", 62, Rarity.RARE, mage.cards.n.NarsetsReversal.class));
        cards.add(new SetCardInfo("Narset, Parter of Veils", "61*", Rarity.UNCOMMON, mage.cards.n.NarsetParterOfVeils.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Narset, Parter of Veils", 61, Rarity.UNCOMMON, mage.cards.n.NarsetParterOfVeils.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neheb, Dreadhorde Champion", 140, Rarity.RARE, mage.cards.n.NehebDreadhordeChampion.class));
        cards.add(new SetCardInfo("Neoform", 206, Rarity.UNCOMMON, mage.cards.n.Neoform.class));
        cards.add(new SetCardInfo("New Horizons", 168, Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Nicol Bolas, Dragon-God", "207*", Rarity.MYTHIC, mage.cards.n.NicolBolasDragonGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nicol Bolas, Dragon-God", 207, Rarity.MYTHIC, mage.cards.n.NicolBolasDragonGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa's Triumph", 170, Rarity.UNCOMMON, mage.cards.n.NissasTriumph.class));
        cards.add(new SetCardInfo("Nissa, Who Shakes the World", "169*", Rarity.RARE, mage.cards.n.NissaWhoShakesTheWorld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nissa, Who Shakes the World", 169, Rarity.RARE, mage.cards.n.NissaWhoShakesTheWorld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niv-Mizzet Reborn", 208, Rarity.MYTHIC, mage.cards.n.NivMizzetReborn.class));
        cards.add(new SetCardInfo("No Escape", 63, Rarity.COMMON, mage.cards.n.NoEscape.class));
        cards.add(new SetCardInfo("Oath of Kaya", 209, Rarity.RARE, mage.cards.o.OathOfKaya.class));
        cards.add(new SetCardInfo("Ob Nixilis's Cruelty", 101, Rarity.COMMON, mage.cards.o.ObNixilissCruelty.class));
        cards.add(new SetCardInfo("Ob Nixilis, the Hate-Twisted", "100*", Rarity.UNCOMMON, mage.cards.o.ObNixilisTheHateTwisted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ob Nixilis, the Hate-Twisted", 100, Rarity.UNCOMMON, mage.cards.o.ObNixilisTheHateTwisted.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Guildgate", 269, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class));
        cards.add(new SetCardInfo("Paradise Druid", 171, Rarity.UNCOMMON, mage.cards.p.ParadiseDruid.class));
        cards.add(new SetCardInfo("Parhelion II", 24, Rarity.RARE, mage.cards.p.ParhelionII.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planewide Celebration", 172, Rarity.RARE, mage.cards.p.PlanewideCelebration.class));
        cards.add(new SetCardInfo("Pledge of Unity", 210, Rarity.UNCOMMON, mage.cards.p.PledgeOfUnity.class));
        cards.add(new SetCardInfo("Pollenbright Druid", 173, Rarity.COMMON, mage.cards.p.PollenbrightDruid.class));
        cards.add(new SetCardInfo("Pouncing Lynx", 25, Rarity.COMMON, mage.cards.p.PouncingLynx.class));
        cards.add(new SetCardInfo("Price of Betrayal", 102, Rarity.UNCOMMON, mage.cards.p.PriceOfBetrayal.class));
        cards.add(new SetCardInfo("Primordial Wurm", 174, Rarity.COMMON, mage.cards.p.PrimordialWurm.class));
        cards.add(new SetCardInfo("Prismite", 242, Rarity.COMMON, mage.cards.p.Prismite.class));
        cards.add(new SetCardInfo("Prison Realm", 26, Rarity.UNCOMMON, mage.cards.p.PrisonRealm.class));
        cards.add(new SetCardInfo("Raging Kronch", 141, Rarity.COMMON, mage.cards.r.RagingKronch.class));
        cards.add(new SetCardInfo("Ral's Outburst", 212, Rarity.UNCOMMON, mage.cards.r.RalsOutburst.class));
        cards.add(new SetCardInfo("Ral, Storm Conduit", "211*", Rarity.RARE, mage.cards.r.RalStormConduit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ral, Storm Conduit", 211, Rarity.RARE, mage.cards.r.RalStormConduit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rally of Wings", 27, Rarity.UNCOMMON, mage.cards.r.RallyOfWings.class));
        cards.add(new SetCardInfo("Ravnica at War", 28, Rarity.RARE, mage.cards.r.RavnicaAtWar.class));
        cards.add(new SetCardInfo("Relentless Advance", 64, Rarity.COMMON, mage.cards.r.RelentlessAdvance.class));
        cards.add(new SetCardInfo("Rescuer Sphinx", 65, Rarity.UNCOMMON, mage.cards.r.RescuerSphinx.class));
        cards.add(new SetCardInfo("Return to Nature", 175, Rarity.COMMON, mage.cards.r.ReturnToNature.class));
        cards.add(new SetCardInfo("Rising Populace", 29, Rarity.COMMON, mage.cards.r.RisingPopulace.class));
        cards.add(new SetCardInfo("Roalesk, Apex Hybrid", 213, Rarity.MYTHIC, mage.cards.r.RoaleskApexHybrid.class));
        cards.add(new SetCardInfo("Role Reversal", 214, Rarity.RARE, mage.cards.r.RoleReversal.class));
        cards.add(new SetCardInfo("Rubblebelt Rioters", 215, Rarity.UNCOMMON, mage.cards.r.RubblebeltRioters.class));
        cards.add(new SetCardInfo("Saheeli's Silverwing", 243, Rarity.COMMON, mage.cards.s.SaheelisSilverwing.class));
        cards.add(new SetCardInfo("Saheeli, Sublime Artificer", "234*", Rarity.UNCOMMON, mage.cards.s.SaheeliSublimeArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saheeli, Sublime Artificer", 234, Rarity.UNCOMMON, mage.cards.s.SaheeliSublimeArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Samut's Sprint", 142, Rarity.COMMON, mage.cards.s.SamutsSprint.class));
        cards.add(new SetCardInfo("Samut, Tyrant Smasher", "235*", Rarity.UNCOMMON, mage.cards.s.SamutTyrantSmasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Samut, Tyrant Smasher", 235, Rarity.UNCOMMON, mage.cards.s.SamutTyrantSmasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarkhan the Masterless", "143*", Rarity.RARE, mage.cards.s.SarkhanTheMasterless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarkhan the Masterless", 143, Rarity.RARE, mage.cards.s.SarkhanTheMasterless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarkhan's Catharsis", 144, Rarity.COMMON, mage.cards.s.SarkhansCatharsis.class));
        cards.add(new SetCardInfo("Shriekdiver", 103, Rarity.COMMON, mage.cards.s.Shriekdiver.class));
        cards.add(new SetCardInfo("Silent Submersible", 66, Rarity.RARE, mage.cards.s.SilentSubmersible.class));
        cards.add(new SetCardInfo("Simic Guildgate", 274, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Single Combat", 30, Rarity.RARE, mage.cards.s.SingleCombat.class));
        cards.add(new SetCardInfo("Sky Theater Strix", 67, Rarity.COMMON, mage.cards.s.SkyTheaterStrix.class));
        cards.add(new SetCardInfo("Snarespinner", 176, Rarity.COMMON, mage.cards.s.Snarespinner.class));
        cards.add(new SetCardInfo("Solar Blaze", 216, Rarity.RARE, mage.cards.s.SolarBlaze.class));
        cards.add(new SetCardInfo("Sorin's Thirst", 104, Rarity.COMMON, mage.cards.s.SorinsThirst.class));
        cards.add(new SetCardInfo("Sorin, Vengeful Bloodlord", "217*", Rarity.RARE, mage.cards.s.SorinVengefulBloodlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorin, Vengeful Bloodlord", 217, Rarity.RARE, mage.cards.s.SorinVengefulBloodlord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Diviner", 218, Rarity.RARE, mage.cards.s.SoulDiviner.class));
        cards.add(new SetCardInfo("Spark Double", 68, Rarity.RARE, mage.cards.s.SparkDouble.class));
        cards.add(new SetCardInfo("Spark Harvest", 105, Rarity.COMMON, mage.cards.s.SparkHarvest.class));
        cards.add(new SetCardInfo("Spark Reaper", 106, Rarity.COMMON, mage.cards.s.SparkReaper.class));
        cards.add(new SetCardInfo("Spellgorger Weird", 145, Rarity.COMMON, mage.cards.s.SpellgorgerWeird.class));
        cards.add(new SetCardInfo("Spellkeeper Weird", 69, Rarity.COMMON, mage.cards.s.SpellkeeperWeird.class));
        cards.add(new SetCardInfo("Steady Aim", 177, Rarity.COMMON, mage.cards.s.SteadyAim.class));
        cards.add(new SetCardInfo("Stealth Mission", 70, Rarity.COMMON, mage.cards.s.StealthMission.class));
        cards.add(new SetCardInfo("Storm the Citadel", 178, Rarity.UNCOMMON, mage.cards.s.StormTheCitadel.class));
        cards.add(new SetCardInfo("Storrev, Devkarin Lich", 219, Rarity.RARE, mage.cards.s.StorrevDevkarinLich.class));
        cards.add(new SetCardInfo("Sunblade Angel", 31, Rarity.UNCOMMON, mage.cards.s.SunbladeAngel.class));
        cards.add(new SetCardInfo("Swamp", 256, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 257, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo's Epiphany", 71, Rarity.COMMON, mage.cards.t.TamiyosEpiphany.class));
        cards.add(new SetCardInfo("Tamiyo, Collector of Tales", "220*", Rarity.RARE, mage.cards.t.TamiyoCollectorOfTales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo, Collector of Tales", 220, Rarity.RARE, mage.cards.t.TamiyoCollectorOfTales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Time Twist", 72, Rarity.COMMON, mage.cards.t.TeferisTimeTwist.class));
        cards.add(new SetCardInfo("Teferi, Time Raveler", "221*", Rarity.RARE, mage.cards.t.TeferiTimeRaveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Time Raveler", 221, Rarity.RARE, mage.cards.t.TeferiTimeRaveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tenth District Legionnaire", 222, Rarity.UNCOMMON, mage.cards.t.TenthDistrictLegionnaire.class));
        cards.add(new SetCardInfo("Teyo's Lightshield", 33, Rarity.COMMON, mage.cards.t.TeyosLightshield.class));
        cards.add(new SetCardInfo("Teyo, the Shieldmage", "32*", Rarity.UNCOMMON, mage.cards.t.TeyoTheShieldmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teyo, the Shieldmage", 32, Rarity.UNCOMMON, mage.cards.t.TeyoTheShieldmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Master of the Bridge", 275, Rarity.MYTHIC, mage.cards.t.TezzeretMasterOfTheBridge.class));
        cards.add(new SetCardInfo("The Elderspell", 89, Rarity.RARE, mage.cards.t.TheElderspell.class));
        cards.add(new SetCardInfo("The Wanderer", "37*", Rarity.UNCOMMON, mage.cards.t.TheWanderer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Wanderer", 37, Rarity.UNCOMMON, mage.cards.t.TheWanderer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thunder Drake", 73, Rarity.COMMON, mage.cards.t.ThunderDrake.class));
        cards.add(new SetCardInfo("Thundering Ceratok", 179, Rarity.COMMON, mage.cards.t.ThunderingCeratok.class));
        cards.add(new SetCardInfo("Tibalt's Rager", 147, Rarity.UNCOMMON, mage.cards.t.TibaltsRager.class));
        cards.add(new SetCardInfo("Tibalt, Rakish Instigator", "146*", Rarity.UNCOMMON, mage.cards.t.TibaltRakishInstigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tibalt, Rakish Instigator", 146, Rarity.UNCOMMON, mage.cards.t.TibaltRakishInstigator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Time Wipe", 223, Rarity.RARE, mage.cards.t.TimeWipe.class));
        cards.add(new SetCardInfo("Tithebearer Giant", 107, Rarity.COMMON, mage.cards.t.TithebearerGiant.class));
        cards.add(new SetCardInfo("Toll of the Invasion", 108, Rarity.COMMON, mage.cards.t.TollOfTheInvasion.class));
        cards.add(new SetCardInfo("Tolsimir, Friend to Wolves", 224, Rarity.RARE, mage.cards.t.TolsimirFriendToWolves.class));
        cards.add(new SetCardInfo("Tomik, Distinguished Advokist", 34, Rarity.RARE, mage.cards.t.TomikDistinguishedAdvokist.class));
        cards.add(new SetCardInfo("Topple the Statue", 35, Rarity.COMMON, mage.cards.t.ToppleTheStatue.class));
        cards.add(new SetCardInfo("Totally Lost", 74, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Trusted Pegasus", 36, Rarity.COMMON, mage.cards.t.TrustedPegasus.class));
        cards.add(new SetCardInfo("Turret Ogre", 148, Rarity.COMMON, mage.cards.t.TurretOgre.class));
        cards.add(new SetCardInfo("Tyrant's Scorn", 225, Rarity.UNCOMMON, mage.cards.t.TyrantsScorn.class));
        cards.add(new SetCardInfo("Ugin's Conjurant", 3, Rarity.UNCOMMON, mage.cards.u.UginsConjurant.class));
        cards.add(new SetCardInfo("Ugin, the Ineffable", "2*", Rarity.RARE, mage.cards.u.UginTheIneffable.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ugin, the Ineffable", 2, Rarity.RARE, mage.cards.u.UginTheIneffable.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unlikely Aid", 109, Rarity.COMMON, mage.cards.u.UnlikelyAid.class));
        cards.add(new SetCardInfo("Vampire Opportunist", 110, Rarity.COMMON, mage.cards.v.VampireOpportunist.class));
        cards.add(new SetCardInfo("Vivien's Arkbow", 181, Rarity.RARE, mage.cards.v.ViviensArkbow.class));
        cards.add(new SetCardInfo("Vivien's Grizzly", 182, Rarity.COMMON, mage.cards.v.ViviensGrizzly.class));
        cards.add(new SetCardInfo("Vivien, Champion of the Wilds", "180*", Rarity.RARE, mage.cards.v.VivienChampionOfTheWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vivien, Champion of the Wilds", 180, Rarity.RARE, mage.cards.v.VivienChampionOfTheWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vizier of the Scorpion", 111, Rarity.UNCOMMON, mage.cards.v.VizierOfTheScorpion.class));
        cards.add(new SetCardInfo("Vraska's Finisher", 112, Rarity.COMMON, mage.cards.v.VraskasFinisher.class));
        cards.add(new SetCardInfo("Vraska, Swarm's Eminence", "236*", Rarity.UNCOMMON, mage.cards.v.VraskaSwarmsEminence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska, Swarm's Eminence", 236, Rarity.UNCOMMON, mage.cards.v.VraskaSwarmsEminence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wall of Runes", 75, Rarity.COMMON, mage.cards.w.WallOfRunes.class));
        cards.add(new SetCardInfo("Wanderer's Strike", 38, Rarity.COMMON, mage.cards.w.WanderersStrike.class));
        cards.add(new SetCardInfo("War Screecher", 39, Rarity.COMMON, mage.cards.w.WarScreecher.class));
        cards.add(new SetCardInfo("Wardscale Crocodile", 183, Rarity.COMMON, mage.cards.w.WardscaleCrocodile.class));
        cards.add(new SetCardInfo("Widespread Brutality", 226, Rarity.RARE, mage.cards.w.WidespreadBrutality.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new WarOfTheSparkCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/war.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class WarOfTheSparkCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "57", "129", "19", "69", "113", "39", "40", "134", "7", "58", "141", "14", "72", "130", "23", "46", "114", "5", "47", "118", "33", "63", "148", "19", "40", "132", "29", "70", "129", "25", "67", "128", "22", "57", "142", "39", "72", "113", "7", "47", "134", "14", "60", "141", "5", "67", "114", "21", "58", "118", "33", "69", "148", "25", "70", "132", "29", "63", "130", "22", "46", "142", "23", "60", "128", "21");
    private final CardRun commonB = new CardRun(true, "175", "103", "176", "108", "165", "95", "183", "88", "182", "106", "158", "104", "161", "110", "177", "76", "175", "81", "168", "108", "174", "95", "166", "94", "176", "107", "183", "106", "161", "103", "158", "104", "165", "110", "182", "88", "176", "108", "166", "76", "177", "81", "174", "95", "175", "94", "168", "107", "182", "106", "158", "103", "183", "88", "161", "104", "165", "110", "177", "81", "166", "107", "168", "76", "174", "94");
    private final CardRun commonC1 = new CardRun(true, "243", "154", "8", "42", "117", "101", "136", "74", "156", "240", "48", "11", "96", "123", "20", "9", "241", "73", "8", "112", "131", "173", "239", "120", "84", "38", "64", "11", "136", "109", "42", "144", "154", "243", "101", "74", "20", "117", "64", "156", "123", "96", "9", "48", "239", "120", "109", "38", "131", "241", "173", "84", "73", "144", "112");
    private final CardRun commonC2 = new CardRun(true, "145", "149", "93", "242", "162", "105", "149", "10", "75", "151", "240", "44", "139", "153", "242", "93", "179", "75", "105", "35", "162", "145", "71", "77", "246", "153", "36", "151", "139", "44", "149", "10", "179", "145", "35", "93", "246", "71", "36", "77", "162", "35", "44", "246", "151", "75", "10", "242", "105", "179", "139", "71", "77", "36", "153");
    private final CardRun uncommonA = new CardRun(true, "159", "205", "49", "188", "116", "189", "4", "204", "171", "121", "215", "6", "200", "87", "202", "212", "41", "147", "190", "31", "78", "205", "178", "192", "111", "188", "55", "26", "204", "122", "4", "189", "87", "215", "159", "116", "49", "200", "78", "147", "202", "171", "212", "6", "111", "121", "41", "178", "31", "192", "55", "122", "190", "26");
    private final CardRun uncommonB = new CardRun(true, "245", "237", "206", "124", "27", "198", "115", "3", "102", "195", "126", "59", "222", "157", "65", "247", "225", "90", "167", "193", "17", "185", "245", "98", "155", "199", "170", "43", "210", "237", "80", "157", "201", "52", "90", "238", "15", "206", "124", "198", "3", "27", "65", "222", "193", "167", "102", "195", "59", "199", "115", "43", "247", "225", "155", "80", "17", "52", "126", "170", "201", "98", "185", "238", "15", "210");
    private final CardRun uncommonPlaneswalker = new CardRun(false, "32", "37", "56", "61", "83", "100", "135", "146", "150", "164", "227", "228", "229", "230", "231", "232", "233", "234", "235", "236");
    private final CardRun rare = new CardRun(false, "18", "24", "28", "30", "34", "45", "50", "62", "66", "68", "79", "82", "85", "86", "89", "99", "125", "137", "138", "140", "152", "172", "181", "186", "187", "194", "196", "197", "203", "209", "214", "216", "218", "219", "223", "224", "226", "244", "248", "249", "18", "24", "28", "30", "34", "45", "50", "62", "66", "68", "79", "82", "85", "86", "89", "99", "125", "137", "138", "140", "152", "172", "181", "186", "187", "194", "196", "197", "203", "209", "214", "216", "218", "219", "223", "224", "226", "244", "248", "249", "12", "16", "51", "53", "91", "92", "127", "133", "160", "163", "208", "213");
    private final CardRun rarePlaneswalker = new CardRun(false, "1", "2", "54", "119", "143", "169", "180", "184", "191", "211", "217", "220", "221", "1", "2", "54", "119", "143", "169", "180", "184", "191", "211", "217", "220", "221", "13", "97", "207");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure ABP = new BoosterStructure(uncommonA, uncommonB, uncommonPlaneswalker, rare);
    private final BoosterStructure BBP = new BoosterStructure(uncommonB, uncommonB, uncommonPlaneswalker, rare);
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB, rarePlaneswalker);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB, rarePlaneswalker);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.01 A uncommons            (81 / 80)
    // 1.24 B uncommons            (99 / 80)
    // 0.75  uncommon planeswalker (60 / 80)
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP,
            ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP,
            ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP,
            ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP,
            ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP, ABP,
            ABP, ABP, ABP, ABP, BBP, BBP, BBP, BBP, BBP, BBP,

            AAB, AAB, AAB, AAB, AAB, AAB, AAB, ABB, ABB, ABB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
