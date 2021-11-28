package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fireshoes
 */
public final class FateReforged extends ExpansionSet {

    private static final FateReforged instance = new FateReforged();

    public static FateReforged getInstance() {
        return instance;
    }

    private FateReforged() {
        super("Fate Reforged", "FRF", ExpansionSet.buildDate(2015, 1, 23), SetType.EXPANSION);
        this.blockName = "Khans of Tarkir";
        this.parentSet = KhansOfTarkir.getInstance();
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterSpecial = 1;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abzan Advantage", 2, Rarity.COMMON, mage.cards.a.AbzanAdvantage.class));
        cards.add(new SetCardInfo("Abzan Beastmaster", 119, Rarity.UNCOMMON, mage.cards.a.AbzanBeastmaster.class));
        cards.add(new SetCardInfo("Abzan Kin-Guard", 120, Rarity.UNCOMMON, mage.cards.a.AbzanKinGuard.class));
        cards.add(new SetCardInfo("Abzan Runemark", 3, Rarity.COMMON, mage.cards.a.AbzanRunemark.class));
        cards.add(new SetCardInfo("Abzan Skycaptain", 4, Rarity.COMMON, mage.cards.a.AbzanSkycaptain.class));
        cards.add(new SetCardInfo("Ainok Guide", 121, Rarity.COMMON, mage.cards.a.AinokGuide.class));
        cards.add(new SetCardInfo("Alesha's Vanguard", 60, Rarity.COMMON, mage.cards.a.AleshasVanguard.class));
        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", 90, Rarity.RARE, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Ambush Krotiq", 122, Rarity.COMMON, mage.cards.a.AmbushKrotiq.class));
        cards.add(new SetCardInfo("Ancestral Vengeance", 61, Rarity.COMMON, mage.cards.a.AncestralVengeance.class));
        cards.add(new SetCardInfo("Arashin Cleric", 5, Rarity.COMMON, mage.cards.a.ArashinCleric.class));
        cards.add(new SetCardInfo("Arashin War Beast", 123, Rarity.UNCOMMON, mage.cards.a.ArashinWarBeast.class));
        cards.add(new SetCardInfo("Arcbond", 91, Rarity.RARE, mage.cards.a.Arcbond.class));
        cards.add(new SetCardInfo("Archers of Qarsi", 124, Rarity.COMMON, mage.cards.a.ArchersOfQarsi.class));
        cards.add(new SetCardInfo("Archfiend of Depravity", 62, Rarity.RARE, mage.cards.a.ArchfiendOfDepravity.class));
        cards.add(new SetCardInfo("Atarka, World Render", 149, Rarity.RARE, mage.cards.a.AtarkaWorldRender.class));
        cards.add(new SetCardInfo("Aven Skirmisher", 6, Rarity.COMMON, mage.cards.a.AvenSkirmisher.class));
        cards.add(new SetCardInfo("Aven Surveyor", 31, Rarity.COMMON, mage.cards.a.AvenSurveyor.class));
        cards.add(new SetCardInfo("Bathe in Dragonfire", 92, Rarity.COMMON, mage.cards.b.BatheInDragonfire.class));
        cards.add(new SetCardInfo("Battle Brawler", 63, Rarity.UNCOMMON, mage.cards.b.BattleBrawler.class));
        cards.add(new SetCardInfo("Battlefront Krushok", 125, Rarity.UNCOMMON, mage.cards.b.BattlefrontKrushok.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 165, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodfire Enforcers", 93, Rarity.UNCOMMON, mage.cards.b.BloodfireEnforcers.class));
        cards.add(new SetCardInfo("Blossoming Sands", 166, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Break Through the Line", 94, Rarity.UNCOMMON, mage.cards.b.BreakThroughTheLine.class));
        cards.add(new SetCardInfo("Brutal Hordechief", 64, Rarity.MYTHIC, mage.cards.b.BrutalHordechief.class));
        cards.add(new SetCardInfo("Cached Defenses", 126, Rarity.UNCOMMON, mage.cards.c.CachedDefenses.class));
        cards.add(new SetCardInfo("Channel Harm", 7, Rarity.UNCOMMON, mage.cards.c.ChannelHarm.class));
        cards.add(new SetCardInfo("Citadel Siege", 8, Rarity.RARE, mage.cards.c.CitadelSiege.class));
        cards.add(new SetCardInfo("Cloudform", 32, Rarity.UNCOMMON, mage.cards.c.Cloudform.class));
        cards.add(new SetCardInfo("Collateral Damage", 95, Rarity.COMMON, mage.cards.c.CollateralDamage.class));
        cards.add(new SetCardInfo("Crucible of the Spirit Dragon", 167, Rarity.RARE, mage.cards.c.CrucibleOfTheSpiritDragon.class));
        cards.add(new SetCardInfo("Crux of Fate", 65, Rarity.RARE, mage.cards.c.CruxOfFate.class));
        cards.add(new SetCardInfo("Cunning Strike", 150, Rarity.COMMON, mage.cards.c.CunningStrike.class));
        cards.add(new SetCardInfo("Daghatar the Adamant", 9, Rarity.RARE, mage.cards.d.DaghatarTheAdamant.class));
        cards.add(new SetCardInfo("Dark Deal", 66, Rarity.UNCOMMON, mage.cards.d.DarkDeal.class));
        cards.add(new SetCardInfo("Defiant Ogre", 96, Rarity.COMMON, mage.cards.d.DefiantOgre.class));
        cards.add(new SetCardInfo("Destructor Dragon", 127, Rarity.UNCOMMON, mage.cards.d.DestructorDragon.class));
        cards.add(new SetCardInfo("Diplomacy of the Wastes", 67, Rarity.UNCOMMON, mage.cards.d.DiplomacyOfTheWastes.class));
        cards.add(new SetCardInfo("Dismal Backwater", 168, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Douse in Gloom", 68, Rarity.COMMON, mage.cards.d.DouseInGloom.class));
        cards.add(new SetCardInfo("Dragon Bell Monk", 10, Rarity.COMMON, mage.cards.d.DragonBellMonk.class));
        cards.add(new SetCardInfo("Dragonrage", 97, Rarity.UNCOMMON, mage.cards.d.Dragonrage.class));
        cards.add(new SetCardInfo("Dragonscale General", 11, Rarity.RARE, mage.cards.d.DragonscaleGeneral.class));
        cards.add(new SetCardInfo("Dromoka, the Eternal", 151, Rarity.RARE, mage.cards.d.DromokaTheEternal.class));
        cards.add(new SetCardInfo("Elite Scaleguard", 12, Rarity.UNCOMMON, mage.cards.e.EliteScaleguard.class));
        cards.add(new SetCardInfo("Enhanced Awareness", 33, Rarity.COMMON, mage.cards.e.EnhancedAwareness.class));
        cards.add(new SetCardInfo("Ethereal Ambush", 152, Rarity.COMMON, mage.cards.e.EtherealAmbush.class));
        cards.add(new SetCardInfo("Fascination", 34, Rarity.UNCOMMON, mage.cards.f.Fascination.class));
        cards.add(new SetCardInfo("Fearsome Awakening", 69, Rarity.UNCOMMON, mage.cards.f.FearsomeAwakening.class));
        cards.add(new SetCardInfo("Feral Krushok", 128, Rarity.COMMON, mage.cards.f.FeralKrushok.class));
        cards.add(new SetCardInfo("Fierce Invocation", 98, Rarity.COMMON, mage.cards.f.FierceInvocation.class));
        cards.add(new SetCardInfo("Flamerush Rider", 99, Rarity.RARE, mage.cards.f.FlamerushRider.class));
        cards.add(new SetCardInfo("Flamewake Phoenix", 100, Rarity.RARE, mage.cards.f.FlamewakePhoenix.class));
        cards.add(new SetCardInfo("Forest", 184, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 185, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Formless Nurturing", 129, Rarity.COMMON, mage.cards.f.FormlessNurturing.class));
        cards.add(new SetCardInfo("Friendly Fire", 101, Rarity.UNCOMMON, mage.cards.f.FriendlyFire.class));
        cards.add(new SetCardInfo("Frontier Mastodon", 130, Rarity.COMMON, mage.cards.f.FrontierMastodon.class));
        cards.add(new SetCardInfo("Frontier Siege", 131, Rarity.RARE, mage.cards.f.FrontierSiege.class));
        cards.add(new SetCardInfo("Frost Walker", 35, Rarity.UNCOMMON, mage.cards.f.FrostWalker.class));
        cards.add(new SetCardInfo("Fruit of the First Tree", 132, Rarity.UNCOMMON, mage.cards.f.FruitOfTheFirstTree.class));
        cards.add(new SetCardInfo("Ghastly Conscription", 70, Rarity.MYTHIC, mage.cards.g.GhastlyConscription.class));
        cards.add(new SetCardInfo("Goblin Boom Keg", 159, Rarity.UNCOMMON, mage.cards.g.GoblinBoomKeg.class));
        cards.add(new SetCardInfo("Goblin Heelcutter", 102, Rarity.COMMON, mage.cards.g.GoblinHeelcutter.class));
        cards.add(new SetCardInfo("Gore Swine", 103, Rarity.COMMON, mage.cards.g.GoreSwine.class));
        cards.add(new SetCardInfo("Grave Strength", 71, Rarity.UNCOMMON, mage.cards.g.GraveStrength.class));
        cards.add(new SetCardInfo("Great-Horn Krushok", 13, Rarity.COMMON, mage.cards.g.GreatHornKrushok.class));
        cards.add(new SetCardInfo("Grim Contest", 153, Rarity.COMMON, mage.cards.g.GrimContest.class));
        cards.add(new SetCardInfo("Gurmag Angler", 72, Rarity.COMMON, mage.cards.g.GurmagAngler.class));
        cards.add(new SetCardInfo("Harsh Sustenance", 154, Rarity.COMMON, mage.cards.h.HarshSustenance.class));
        cards.add(new SetCardInfo("Hero's Blade", 160, Rarity.UNCOMMON, mage.cards.h.HerosBlade.class));
        cards.add(new SetCardInfo("Hewed Stone Retainers", 161, Rarity.UNCOMMON, mage.cards.h.HewedStoneRetainers.class));
        cards.add(new SetCardInfo("Honor's Reward", 14, Rarity.UNCOMMON, mage.cards.h.HonorsReward.class));
        cards.add(new SetCardInfo("Hooded Assassin", 73, Rarity.COMMON, mage.cards.h.HoodedAssassin.class));
        cards.add(new SetCardInfo("Humble Defector", 104, Rarity.UNCOMMON, mage.cards.h.HumbleDefector.class));
        cards.add(new SetCardInfo("Hungering Yeti", 105, Rarity.UNCOMMON, mage.cards.h.HungeringYeti.class));
        cards.add(new SetCardInfo("Hunt the Weak", 133, Rarity.COMMON, mage.cards.h.HuntTheWeak.class));
        cards.add(new SetCardInfo("Island", 178, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 179, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeskai Barricade", 15, Rarity.UNCOMMON, mage.cards.j.JeskaiBarricade.class));
        cards.add(new SetCardInfo("Jeskai Infiltrator", 36, Rarity.RARE, mage.cards.j.JeskaiInfiltrator.class));
        cards.add(new SetCardInfo("Jeskai Runemark", 37, Rarity.COMMON, mage.cards.j.JeskaiRunemark.class));
        cards.add(new SetCardInfo("Jeskai Sage", 38, Rarity.COMMON, mage.cards.j.JeskaiSage.class));
        cards.add(new SetCardInfo("Jungle Hollow", 169, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kolaghan, the Storm's Fury", 155, Rarity.RARE, mage.cards.k.KolaghanTheStormsFury.class));
        cards.add(new SetCardInfo("Lightform", 16, Rarity.UNCOMMON, mage.cards.l.Lightform.class));
        cards.add(new SetCardInfo("Lightning Shrieker", 106, Rarity.COMMON, mage.cards.l.LightningShrieker.class));
        cards.add(new SetCardInfo("Lotus-Eye Mystics", 17, Rarity.UNCOMMON, mage.cards.l.LotusEyeMystics.class));
        cards.add(new SetCardInfo("Lotus Path Djinn", 39, Rarity.COMMON, mage.cards.l.LotusPathDjinn.class));
        cards.add(new SetCardInfo("Map the Wastes", 134, Rarity.COMMON, mage.cards.m.MapTheWastes.class));
        cards.add(new SetCardInfo("Marang River Prowler", 40, Rarity.UNCOMMON, mage.cards.m.MarangRiverProwler.class));
        cards.add(new SetCardInfo("Mardu Runemark", 107, Rarity.COMMON, mage.cards.m.MarduRunemark.class));
        cards.add(new SetCardInfo("Mardu Scout", 108, Rarity.COMMON, mage.cards.m.MarduScout.class));
        cards.add(new SetCardInfo("Mardu Shadowspear", 74, Rarity.UNCOMMON, mage.cards.m.MarduShadowspear.class));
        cards.add(new SetCardInfo("Mardu Strike Leader", 75, Rarity.RARE, mage.cards.m.MarduStrikeLeader.class));
        cards.add(new SetCardInfo("Mardu Woe-Reaper", 18, Rarity.UNCOMMON, mage.cards.m.MarduWoeReaper.class));
        cards.add(new SetCardInfo("Mastery of the Unseen", 19, Rarity.RARE, mage.cards.m.MasteryOfTheUnseen.class));
        cards.add(new SetCardInfo("Merciless Executioner", 76, Rarity.UNCOMMON, mage.cards.m.MercilessExecutioner.class));
        cards.add(new SetCardInfo("Mindscour Dragon", 41, Rarity.UNCOMMON, mage.cards.m.MindscourDragon.class));
        cards.add(new SetCardInfo("Mistfire Adept", 42, Rarity.UNCOMMON, mage.cards.m.MistfireAdept.class));
        cards.add(new SetCardInfo("Mob Rule", 109, Rarity.RARE, mage.cards.m.MobRule.class));
        cards.add(new SetCardInfo("Monastery Mentor", 20, Rarity.MYTHIC, mage.cards.m.MonasteryMentor.class));
        cards.add(new SetCardInfo("Monastery Siege", 43, Rarity.RARE, mage.cards.m.MonasterySiege.class));
        cards.add(new SetCardInfo("Mountain", 182, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 183, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neutralizing Blast", 44, Rarity.UNCOMMON, mage.cards.n.NeutralizingBlast.class));
        cards.add(new SetCardInfo("Noxious Dragon", 77, Rarity.UNCOMMON, mage.cards.n.NoxiousDragon.class));
        cards.add(new SetCardInfo("Ojutai, Soul of Winter", 156, Rarity.RARE, mage.cards.o.OjutaiSoulOfWinter.class));
        cards.add(new SetCardInfo("Orc Sureshot", 78, Rarity.UNCOMMON, mage.cards.o.OrcSureshot.class));
        cards.add(new SetCardInfo("Outpost Siege", 110, Rarity.RARE, mage.cards.o.OutpostSiege.class));
        cards.add(new SetCardInfo("Palace Siege", 79, Rarity.RARE, mage.cards.p.PalaceSiege.class));
        cards.add(new SetCardInfo("Pilgrim of the Fires", 162, Rarity.UNCOMMON, mage.cards.p.PilgrimOfTheFires.class));
        cards.add(new SetCardInfo("Plains", 176, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 177, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pressure Point", 21, Rarity.COMMON, mage.cards.p.PressurePoint.class));
        cards.add(new SetCardInfo("Pyrotechnics", 111, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Qarsi High Priest", 80, Rarity.UNCOMMON, mage.cards.q.QarsiHighPriest.class));
        cards.add(new SetCardInfo("Rageform", 112, Rarity.UNCOMMON, mage.cards.r.Rageform.class));
        cards.add(new SetCardInfo("Rakshasa's Disdain", 45, Rarity.COMMON, mage.cards.r.RakshasasDisdain.class));
        cards.add(new SetCardInfo("Rally the Ancestors", 22, Rarity.RARE, mage.cards.r.RallyTheAncestors.class));
        cards.add(new SetCardInfo("Reach of Shadows", 81, Rarity.COMMON, mage.cards.r.ReachOfShadows.class));
        cards.add(new SetCardInfo("Reality Shift", 46, Rarity.UNCOMMON, mage.cards.r.RealityShift.class));
        cards.add(new SetCardInfo("Refocus", 47, Rarity.COMMON, mage.cards.r.Refocus.class));
        cards.add(new SetCardInfo("Renowned Weaponsmith", 48, Rarity.UNCOMMON, mage.cards.r.RenownedWeaponsmith.class));
        cards.add(new SetCardInfo("Return to the Earth", 135, Rarity.COMMON, mage.cards.r.ReturnToTheEarth.class));
        cards.add(new SetCardInfo("Rite of Undoing", 49, Rarity.UNCOMMON, mage.cards.r.RiteOfUndoing.class));
        cards.add(new SetCardInfo("Rugged Highlands", 170, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Ruthless Instincts", 136, Rarity.UNCOMMON, mage.cards.r.RuthlessInstincts.class));
        cards.add(new SetCardInfo("Sage-Eye Avengers", 50, Rarity.RARE, mage.cards.s.SageEyeAvengers.class));
        cards.add(new SetCardInfo("Sage's Reverie", 23, Rarity.UNCOMMON, mage.cards.s.SagesReverie.class));
        cards.add(new SetCardInfo("Sandblast", 24, Rarity.COMMON, mage.cards.s.Sandblast.class));
        cards.add(new SetCardInfo("Sandsteppe Mastodon", 137, Rarity.RARE, mage.cards.s.SandsteppeMastodon.class));
        cards.add(new SetCardInfo("Sandsteppe Outcast", 25, Rarity.COMMON, mage.cards.s.SandsteppeOutcast.class));
        cards.add(new SetCardInfo("Scoured Barrens", 171, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scroll of the Masters", 163, Rarity.RARE, mage.cards.s.ScrollOfTheMasters.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 138, Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Shaman of the Great Hunt", 113, Rarity.MYTHIC, mage.cards.s.ShamanOfTheGreatHunt.class));
        cards.add(new SetCardInfo("Shifting Loyalties", 51, Rarity.UNCOMMON, mage.cards.s.ShiftingLoyalties.class));
        cards.add(new SetCardInfo("Shockmaw Dragon", 114, Rarity.UNCOMMON, mage.cards.s.ShockmawDragon.class));
        cards.add(new SetCardInfo("Shu Yun, the Silent Tempest", 52, Rarity.RARE, mage.cards.s.ShuYunTheSilentTempest.class));
        cards.add(new SetCardInfo("Sibsig Host", 82, Rarity.COMMON, mage.cards.s.SibsigHost.class));
        cards.add(new SetCardInfo("Sibsig Muckdraggers", 83, Rarity.UNCOMMON, mage.cards.s.SibsigMuckdraggers.class));
        cards.add(new SetCardInfo("Silumgar, the Drifting Death", 157, Rarity.RARE, mage.cards.s.SilumgarTheDriftingDeath.class));
        cards.add(new SetCardInfo("Smoldering Efreet", 115, Rarity.COMMON, mage.cards.s.SmolderingEfreet.class));
        cards.add(new SetCardInfo("Soulfire Grand Master", 27, Rarity.MYTHIC, mage.cards.s.SoulfireGrandMaster.class));
        cards.add(new SetCardInfo("Soulflayer", 84, Rarity.RARE, mage.cards.s.Soulflayer.class));
        cards.add(new SetCardInfo("Soul Summons", 26, Rarity.COMMON, mage.cards.s.SoulSummons.class));
        cards.add(new SetCardInfo("Sudden Reclamation", 139, Rarity.UNCOMMON, mage.cards.s.SuddenReclamation.class));
        cards.add(new SetCardInfo("Sultai Emissary", 85, Rarity.COMMON, mage.cards.s.SultaiEmissary.class));
        cards.add(new SetCardInfo("Sultai Runemark", 86, Rarity.COMMON, mage.cards.s.SultaiRunemark.class));
        cards.add(new SetCardInfo("Sultai Skullkeeper", 53, Rarity.COMMON, mage.cards.s.SultaiSkullkeeper.class));
        cards.add(new SetCardInfo("Supplant Form", 54, Rarity.RARE, mage.cards.s.SupplantForm.class));
        cards.add(new SetCardInfo("Swamp", 180, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 181, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 172, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Tasigur's Cruelty", 88, Rarity.COMMON, mage.cards.t.TasigursCruelty.class));
        cards.add(new SetCardInfo("Tasigur, the Golden Fang", 87, Rarity.RARE, mage.cards.t.TasigurTheGoldenFang.class));
        cards.add(new SetCardInfo("Temporal Trespass", 55, Rarity.MYTHIC, mage.cards.t.TemporalTrespass.class));
        cards.add(new SetCardInfo("Temur Battle Rage", 116, Rarity.COMMON, mage.cards.t.TemurBattleRage.class));
        cards.add(new SetCardInfo("Temur Runemark", 140, Rarity.COMMON, mage.cards.t.TemurRunemark.class));
        cards.add(new SetCardInfo("Temur Sabertooth", 141, Rarity.UNCOMMON, mage.cards.t.TemurSabertooth.class));
        cards.add(new SetCardInfo("Temur War Shaman", 142, Rarity.RARE, mage.cards.t.TemurWarShaman.class));
        cards.add(new SetCardInfo("Thornwood Falls", 173, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Torrent Elemental", 56, Rarity.MYTHIC, mage.cards.t.TorrentElemental.class));
        cards.add(new SetCardInfo("Tranquil Cove", 174, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Typhoid Rats", 89, Rarity.COMMON, mage.cards.t.TyphoidRats.class));
        cards.add(new SetCardInfo("Ugin's Construct", 164, Rarity.UNCOMMON, mage.cards.u.UginsConstruct.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Valorous Stance", 28, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vaultbreaker", 117, Rarity.UNCOMMON, mage.cards.v.Vaultbreaker.class));
        cards.add(new SetCardInfo("Wandering Champion", 29, Rarity.UNCOMMON, mage.cards.w.WanderingChampion.class));
        cards.add(new SetCardInfo("Warden of the First Tree", 143, Rarity.MYTHIC, mage.cards.w.WardenOfTheFirstTree.class));
        cards.add(new SetCardInfo("Wardscale Dragon", 30, Rarity.UNCOMMON, mage.cards.w.WardscaleDragon.class));
        cards.add(new SetCardInfo("War Flare", 158, Rarity.COMMON, mage.cards.w.WarFlare.class));
        cards.add(new SetCardInfo("Whisk Away", 57, Rarity.COMMON, mage.cards.w.WhiskAway.class));
        cards.add(new SetCardInfo("Whisperer of the Wilds", 144, Rarity.COMMON, mage.cards.w.WhispererOfTheWilds.class));
        cards.add(new SetCardInfo("Whisperwood Elemental", 145, Rarity.MYTHIC, mage.cards.w.WhisperwoodElemental.class));
        cards.add(new SetCardInfo("Wildcall", 146, Rarity.RARE, mage.cards.w.Wildcall.class));
        cards.add(new SetCardInfo("Wild Slash", 118, Rarity.UNCOMMON, mage.cards.w.WildSlash.class));
        cards.add(new SetCardInfo("Will of the Naga", 58, Rarity.COMMON, mage.cards.w.WillOfTheNaga.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 175, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Winds of Qal Sisma", 147, Rarity.UNCOMMON, mage.cards.w.WindsOfQalSisma.class));
        cards.add(new SetCardInfo("Write into Being", 59, Rarity.COMMON, mage.cards.w.WriteIntoBeing.class));
        cards.add(new SetCardInfo("Yasova Dragonclaw", 148, Rarity.RARE, mage.cards.y.YasovaDragonclaw.class));
    }

    @Override
    protected void addSpecialCards(List<Card> booster, int number) {
        // number is here always 1
        Rarity rarity;
        if (RandomUtil.nextInt(24) < 23) {
            rarity = Rarity.COMMON;
        } else {
            rarity = Rarity.RARE;
        }
        addToBooster(booster, getSpecialCardsByRarity(rarity));
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        CardCriteria criteria = new CardCriteria();
        criteria.rarities(rarity).types(CardType.LAND);
        if (rarity == Rarity.RARE) {
            // fetchlands
            criteria.setCodes("KTK");
        } else {
            // gainlands
            criteria.setCodes(this.code);
        }
        return CardRepository.instance.findCards(criteria);
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("KTK").rarities(Rarity.RARE).types(CardType.LAND))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("KTK_" + cardInfo.getCardNumber(), cardInfo));
    }

    @Override
    public BoosterCollator createCollator() {
        return new FateReforgedCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/frf.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class FateReforgedCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "5", "57", "82", "53", "24", "38", "86", "153", "21", "6", "39", "73", "152", "2", "47", "68", "58", "4", "33", "89", "153", "10", "45", "72", "86", "3", "31", "61", "158", "26", "53", "81", "154", "21", "57", "85", "37", "24", "38", "82", "150", "5", "2", "31", "89", "152", "10", "33", "72", "47", "26", "58", "61", "150", "3", "45", "81", "73", "6", "39", "85", "154", "4", "37", "68", "158");
    private final CardRun commonB = new CardRun(true, "106", "121", "25", "115", "133", "107", "129", "88", "116", "135", "108", "124", "98", "128", "13", "92", "130", "96", "121", "60", "103", "144", "107", "122", "59", "102", "134", "95", "129", "25", "116", "133", "98", "140", "88", "108", "128", "106", "124", "115", "135", "13", "102", "122", "103", "130", "60", "96", "144", "95", "140", "59", "92", "134");
    private final CardRun uncommon = new CardRun(true, "104", "49", "32", "67", "46", "139", "14", "101", "83", "23", "160", "16", "76", "69", "126", "105", "35", "97", "30", "159", "80", "125", "118", "77", "34", "51", "7", "40", "94", "28", "78", "136", "114", "161", "48", "42", "12", "66", "83", "120", "117", "44", "18", "67", "127", "15", "112", "141", "32", "29", "132", "164", "63", "123", "119", "93", "71", "41", "101", "111", "17", "49", "162", "74", "80", "139", "147", "30", "104", "46", "69", "77", "14", "105", "159", "164", "118", "23", "63", "160", "123", "97", "93", "141", "41", "44", "126", "17", "136", "34", "112", "76", "78", "71", "162", "16", "114", "7", "161", "120", "51", "35", "74", "66", "18", "125", "40", "42", "132", "28", "147", "12", "94", "48", "119", "29", "15", "117", "127", "111");
    private final CardRun rare = new CardRun(false, "8", "9", "11", "19", "22", "36", "43", "50", "52", "54", "62", "65", "75", "79", "84", "87", "90", "91", "99", "100", "109", "110", "131", "137", "138", "142", "146", "148", "149", "151", "155", "156", "157", "163", "167", "8", "9", "11", "19", "22", "36", "43", "50", "52", "54", "62", "65", "75", "79", "84", "87", "90", "91", "99", "100", "109", "110", "131", "137", "138", "142", "146", "148", "149", "151", "155", "156", "157", "163", "167", "1", "20", "27", "55", "56", "64", "70", "113", "143", "145");
    private final CardRun landCommon = new CardRun(true, "165", "169", "166", "171", "168", "165", "174", "171", "173", "172", "166", "170", "169", "175", "168", "173", "170", "165", "172", "169", "174", "166", "175", "171", "168", "170", "172", "174", "175", "173");
    private final CardRun landRare = new CardRun(false, "KTK_230", "KTK_233", "KTK_239", "KTK_248", "KTK_249");

    private final BoosterStructure AAAAABBBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB
    );
    private final BoosterStructure AAAAAABBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB
    );
    private final BoosterStructure U1 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(landCommon);
    private final BoosterStructure L2 = new BoosterStructure(landRare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(AAAAABBBBB, AAAAAABBBB);
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U1);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(
        L1, L1, L1, L1, L1, L1, L1, L1,
        L1, L1, L1, L1, L1, L1, L1, L1,
        L1, L1, L1, L1, L1, L1, L1, L2
    );

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
