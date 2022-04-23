package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class KamigawaNeonDynasty extends ExpansionSet {

    private static final KamigawaNeonDynasty instance = new KamigawaNeonDynasty();

    public static KamigawaNeonDynasty getInstance() {
        return instance;
    }

    private KamigawaNeonDynasty() {
        super("Kamigawa: Neon Dynasty", "NEO", ExpansionSet.buildDate(2022, 2, 18), SetType.EXPANSION);
        this.blockName = "Kamigawa: Neon Dynasty";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7.4;
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 302;
        this.ratioBoosterSpecialLand = 2;
        this.ratioBoosterSpecialLandNumerator = 1;

        cards.add(new SetCardInfo("Acquisition Octopus", 44, Rarity.UNCOMMON, mage.cards.a.AcquisitionOctopus.class));
        cards.add(new SetCardInfo("Akki Ember-Keeper", 130, Rarity.COMMON, mage.cards.a.AkkiEmberKeeper.class));
        cards.add(new SetCardInfo("Akki Ronin", 131, Rarity.COMMON, mage.cards.a.AkkiRonin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akki Ronin", 319, Rarity.COMMON, mage.cards.a.AkkiRonin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akki War Paint", 132, Rarity.COMMON, mage.cards.a.AkkiWarPaint.class));
        cards.add(new SetCardInfo("Ambitious Assault", 133, Rarity.COMMON, mage.cards.a.AmbitiousAssault.class));
        cards.add(new SetCardInfo("Ancestral Katana", 1, Rarity.COMMON, mage.cards.a.AncestralKatana.class));
        cards.add(new SetCardInfo("Anchor to Reality", 45, Rarity.UNCOMMON, mage.cards.a.AnchorToReality.class));
        cards.add(new SetCardInfo("Animus of Night's Reach", 109, Rarity.UNCOMMON, mage.cards.a.AnimusOfNightsReach.class));
        cards.add(new SetCardInfo("Ao, the Dawn Sky", 2, Rarity.MYTHIC, mage.cards.a.AoTheDawnSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ao, the Dawn Sky", 406, Rarity.MYTHIC, mage.cards.a.AoTheDawnSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ao, the Dawn Sky", 433, Rarity.MYTHIC, mage.cards.a.AoTheDawnSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Architect of Restoration", 34, Rarity.RARE, mage.cards.a.ArchitectOfRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Architect of Restoration", 354, Rarity.RARE, mage.cards.a.ArchitectOfRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Architect of Restoration", 442, Rarity.RARE, mage.cards.a.ArchitectOfRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Armguard Familiar", 46, Rarity.COMMON, mage.cards.a.ArmguardFamiliar.class));
        cards.add(new SetCardInfo("Asari Captain", 215, Rarity.UNCOMMON, mage.cards.a.AsariCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Asari Captain", 327, Rarity.UNCOMMON, mage.cards.a.AsariCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assassin's Ink", 87, Rarity.UNCOMMON, mage.cards.a.AssassinsInk.class));
        cards.add(new SetCardInfo("Atsushi, the Blazing Sky", 134, Rarity.MYTHIC, mage.cards.a.AtsushiTheBlazingSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atsushi, the Blazing Sky", 410, Rarity.MYTHIC, mage.cards.a.AtsushiTheBlazingSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atsushi, the Blazing Sky", 463, Rarity.MYTHIC, mage.cards.a.AtsushiTheBlazingSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Automated Artificer", 239, Rarity.COMMON, mage.cards.a.AutomatedArtificer.class));
        cards.add(new SetCardInfo("Awakened Awareness", 47, Rarity.UNCOMMON, mage.cards.a.AwakenedAwareness.class));
        cards.add(new SetCardInfo("Azusa's Many Journeys", 172, Rarity.UNCOMMON, mage.cards.a.AzusasManyJourneys.class));
        cards.add(new SetCardInfo("Bamboo Grove Archer", 173, Rarity.COMMON, mage.cards.b.BambooGroveArcher.class));
        cards.add(new SetCardInfo("Banishing Slash", 3, Rarity.UNCOMMON, mage.cards.b.BanishingSlash.class));
        cards.add(new SetCardInfo("Bearer of Memory", 174, Rarity.COMMON, mage.cards.b.BearerOfMemory.class));
        cards.add(new SetCardInfo("Befriending the Moths", 4, Rarity.COMMON, mage.cards.b.BefriendingTheMoths.class));
        cards.add(new SetCardInfo("Behold the Unspeakable", 48, Rarity.UNCOMMON, mage.cards.b.BeholdTheUnspeakable.class));
        cards.add(new SetCardInfo("Biting-Palm Ninja", 338, Rarity.RARE, mage.cards.b.BitingPalmNinja.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Biting-Palm Ninja", 452, Rarity.RARE, mage.cards.b.BitingPalmNinja.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Biting-Palm Ninja", 88, Rarity.RARE, mage.cards.b.BitingPalmNinja.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade of the Oni", 377, Rarity.MYTHIC, mage.cards.b.BladeOfTheOni.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade of the Oni", 420, Rarity.MYTHIC, mage.cards.b.BladeOfTheOni.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade of the Oni", 453, Rarity.MYTHIC, mage.cards.b.BladeOfTheOni.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade of the Oni", 89, Rarity.MYTHIC, mage.cards.b.BladeOfTheOni.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade-Blizzard Kitsune", 331, Rarity.UNCOMMON, mage.cards.b.BladeBlizzardKitsune.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blade-Blizzard Kitsune", 5, Rarity.UNCOMMON, mage.cards.b.BladeBlizzardKitsune.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodfell Caves", 264, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Blossom Prancer", 175, Rarity.UNCOMMON, mage.cards.b.BlossomPrancer.class));
        cards.add(new SetCardInfo("Blossoming Sands", 265, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Boon of Boseiju", 176, Rarity.UNCOMMON, mage.cards.b.BoonOfBoseiju.class));
        cards.add(new SetCardInfo("Born to Drive", 6, Rarity.UNCOMMON, mage.cards.b.BornToDrive.class));
        cards.add(new SetCardInfo("Boseiju Reaches Skyward", 177, Rarity.UNCOMMON, mage.cards.b.BoseijuReachesSkyward.class));
        cards.add(new SetCardInfo("Boseiju, Who Endures", 266, Rarity.RARE, mage.cards.b.BoseijuWhoEndures.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boseiju, Who Endures", 412, Rarity.RARE, mage.cards.b.BoseijuWhoEndures.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boseiju, Who Endures", 501, Rarity.RARE, mage.cards.b.BoseijuWhoEndures.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Branch of Boseiju", 177, Rarity.UNCOMMON, mage.cards.b.BranchOfBoseiju.class));
        cards.add(new SetCardInfo("Brilliant Restoration", 363, Rarity.RARE, mage.cards.b.BrilliantRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brilliant Restoration", 434, Rarity.RARE, mage.cards.b.BrilliantRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brilliant Restoration", 7, Rarity.RARE, mage.cards.b.BrilliantRestoration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bronze Cudgels", 240, Rarity.UNCOMMON, mage.cards.b.BronzeCudgels.class));
        cards.add(new SetCardInfo("Bronzeplate Boar", 135, Rarity.UNCOMMON, mage.cards.b.BronzeplateBoar.class));
        cards.add(new SetCardInfo("Brute Suit", 241, Rarity.COMMON, mage.cards.b.BruteSuit.class));
        cards.add(new SetCardInfo("Careful Cultivation", 178, Rarity.COMMON, mage.cards.c.CarefulCultivation.class));
        cards.add(new SetCardInfo("Chainflail Centipede", 90, Rarity.COMMON, mage.cards.c.ChainflailCentipede.class));
        cards.add(new SetCardInfo("Circuit Mender", 242, Rarity.UNCOMMON, mage.cards.c.CircuitMender.class));
        cards.add(new SetCardInfo("Clawing Torment", 91, Rarity.COMMON, mage.cards.c.ClawingTorment.class));
        cards.add(new SetCardInfo("Cloudsteel Kirin", 364, Rarity.RARE, mage.cards.c.CloudsteelKirin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloudsteel Kirin", 435, Rarity.RARE, mage.cards.c.CloudsteelKirin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloudsteel Kirin", 8, Rarity.RARE, mage.cards.c.CloudsteelKirin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coiling Stalker", 179, Rarity.COMMON, mage.cards.c.CoilingStalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Coiling Stalker", 346, Rarity.COMMON, mage.cards.c.CoilingStalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colossal Skyturtle", 216, Rarity.UNCOMMON, mage.cards.c.ColossalSkyturtle.class));
        cards.add(new SetCardInfo("Commune with Spirits", 180, Rarity.COMMON, mage.cards.c.CommuneWithSpirits.class));
        cards.add(new SetCardInfo("Containment Construct", 243, Rarity.UNCOMMON, mage.cards.c.ContainmentConstruct.class));
        cards.add(new SetCardInfo("Covert Technician", 332, Rarity.UNCOMMON, mage.cards.c.CovertTechnician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Covert Technician", 49, Rarity.UNCOMMON, mage.cards.c.CovertTechnician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crackling Emergence", 136, Rarity.COMMON, mage.cards.c.CracklingEmergence.class));
        cards.add(new SetCardInfo("Debt to the Kami", 92, Rarity.COMMON, mage.cards.d.DebtToTheKami.class));
        cards.add(new SetCardInfo("Discover the Impossible", 50, Rarity.UNCOMMON, mage.cards.d.DiscoverTheImpossible.class));
        cards.add(new SetCardInfo("Dismal Backwater", 267, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Disruption Protocol", 51, Rarity.COMMON, mage.cards.d.DisruptionProtocol.class));
        cards.add(new SetCardInfo("Dockside Chef", 93, Rarity.UNCOMMON, mage.cards.d.DocksideChef.class));
        cards.add(new SetCardInfo("Dokuchi Shadow-Walker", 339, Rarity.COMMON, mage.cards.d.DokuchiShadowWalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dokuchi Shadow-Walker", 94, Rarity.COMMON, mage.cards.d.DokuchiShadowWalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dokuchi Silencer", 340, Rarity.UNCOMMON, mage.cards.d.DokuchiSilencer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dokuchi Silencer", 95, Rarity.UNCOMMON, mage.cards.d.DokuchiSilencer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon-Kami's Egg", 181, Rarity.RARE, mage.cards.d.DragonKamisEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon-Kami's Egg", 358, Rarity.RARE, mage.cards.d.DragonKamisEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon-Kami's Egg", 473, Rarity.RARE, mage.cards.d.DragonKamisEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragonfly Suit", 9, Rarity.COMMON, mage.cards.d.DragonflySuit.class));
        cards.add(new SetCardInfo("Dragonspark Reactor", 137, Rarity.UNCOMMON, mage.cards.d.DragonsparkReactor.class));
        cards.add(new SetCardInfo("Dramatist's Puppet", 244, Rarity.COMMON, mage.cards.d.DramatistsPuppet.class));
        cards.add(new SetCardInfo("Eater of Virtue", 245, Rarity.RARE, mage.cards.e.EaterOfVirtue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eater of Virtue", 401, Rarity.RARE, mage.cards.e.EaterOfVirtue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eater of Virtue", 496, Rarity.RARE, mage.cards.e.EaterOfVirtue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Echo of Death's Wail", 124, Rarity.RARE, mage.cards.e.EchoOfDeathsWail.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Echo of Death's Wail", 356, Rarity.RARE, mage.cards.e.EchoOfDeathsWail.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Echo of Death's Wail", 462, Rarity.RARE, mage.cards.e.EchoOfDeathsWail.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ecologist's Terrarium", 246, Rarity.COMMON, mage.cards.e.EcologistsTerrarium.class));
        cards.add(new SetCardInfo("Eiganjo Exemplar", 10, Rarity.COMMON, mage.cards.e.EiganjoExemplar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo Exemplar", 309, Rarity.COMMON, mage.cards.e.EiganjoExemplar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo Uprising", 217, Rarity.RARE, mage.cards.e.EiganjoUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo Uprising", 396, Rarity.RARE, mage.cards.e.EiganjoUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo Uprising", 484, Rarity.RARE, mage.cards.e.EiganjoUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo, Seat of the Empire", 268, Rarity.RARE, mage.cards.e.EiganjoSeatOfTheEmpire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo, Seat of the Empire", 413, Rarity.RARE, mage.cards.e.EiganjoSeatOfTheEmpire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eiganjo, Seat of the Empire", 502, Rarity.RARE, mage.cards.e.EiganjoSeatOfTheEmpire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enormous Energy Blade", 96, Rarity.UNCOMMON, mage.cards.e.EnormousEnergyBlade.class));
        cards.add(new SetCardInfo("Enthusiastic Mechanaut", 218, Rarity.UNCOMMON, mage.cards.e.EnthusiasticMechanaut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enthusiastic Mechanaut", 509, Rarity.UNCOMMON, mage.cards.e.EnthusiasticMechanaut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Era of Enlightenment", 11, Rarity.COMMON, mage.cards.e.EraOfEnlightenment.class));
        cards.add(new SetCardInfo("Essence Capture", 52, Rarity.UNCOMMON, mage.cards.e.EssenceCapture.class));
        cards.add(new SetCardInfo("Etching of Kumano", 152, Rarity.UNCOMMON, mage.cards.e.EtchingOfKumano.class));
        cards.add(new SetCardInfo("Experimental Synthesizer", 138, Rarity.COMMON, mage.cards.e.ExperimentalSynthesizer.class));
        cards.add(new SetCardInfo("Explosive Entry", 139, Rarity.COMMON, mage.cards.e.ExplosiveEntry.class));
        cards.add(new SetCardInfo("Explosive Singularity", 140, Rarity.MYTHIC, mage.cards.e.ExplosiveSingularity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Explosive Singularity", 383, Rarity.MYTHIC, mage.cards.e.ExplosiveSingularity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Explosive Singularity", 422, Rarity.MYTHIC, mage.cards.e.ExplosiveSingularity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Explosive Singularity", 464, Rarity.MYTHIC, mage.cards.e.ExplosiveSingularity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fable of the Mirror-Breaker", 141, Rarity.RARE, mage.cards.f.FableOfTheMirrorBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fable of the Mirror-Breaker", 357, Rarity.RARE, mage.cards.f.FableOfTheMirrorBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fable of the Mirror-Breaker", 465, Rarity.RARE, mage.cards.f.FableOfTheMirrorBreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fade into Antiquity", 182, Rarity.COMMON, mage.cards.f.FadeIntoAntiquity.class));
        cards.add(new SetCardInfo("Fang of Shigeki", 183, Rarity.COMMON, mage.cards.f.FangOfShigeki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fang of Shigeki", 347, Rarity.COMMON, mage.cards.f.FangOfShigeki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Farewell", 13, Rarity.RARE, mage.cards.f.Farewell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Farewell", 365, Rarity.RARE, mage.cards.f.Farewell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Farewell", 417, Rarity.RARE, mage.cards.f.Farewell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Farewell", 436, Rarity.RARE, mage.cards.f.Farewell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Favor of Jukai", 184, Rarity.COMMON, mage.cards.f.FavorOfJukai.class));
        cards.add(new SetCardInfo("Flame Discharge", 142, Rarity.UNCOMMON, mage.cards.f.FlameDischarge.class));
        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 292, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 301, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 302, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fragment of Konda", 12, Rarity.UNCOMMON, mage.cards.f.FragmentOfKonda.class));
        cards.add(new SetCardInfo("Futurist Operative", 333, Rarity.UNCOMMON, mage.cards.f.FuturistOperative.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Futurist Operative", 53, Rarity.UNCOMMON, mage.cards.f.FuturistOperative.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Futurist Sentinel", 54, Rarity.COMMON, mage.cards.f.FuturistSentinel.class));
        cards.add(new SetCardInfo("Generous Visitor", 185, Rarity.UNCOMMON, mage.cards.g.GenerousVisitor.class));
        cards.add(new SetCardInfo("Geothermal Kami", 186, Rarity.COMMON, mage.cards.g.GeothermalKami.class));
        cards.add(new SetCardInfo("Gift of Wrath", 143, Rarity.COMMON, mage.cards.g.GiftOfWrath.class));
        cards.add(new SetCardInfo("Gloomshrieker", 219, Rarity.UNCOMMON, mage.cards.g.Gloomshrieker.class));
        cards.add(new SetCardInfo("Go-Shintai of Ancient Wars", 144, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfAncientWars.class));
        cards.add(new SetCardInfo("Go-Shintai of Boundless Vigor", 187, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfBoundlessVigor.class));
        cards.add(new SetCardInfo("Go-Shintai of Hidden Cruelty", 97, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfHiddenCruelty.class));
        cards.add(new SetCardInfo("Go-Shintai of Lost Wisdom", 55, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfLostWisdom.class));
        cards.add(new SetCardInfo("Go-Shintai of Shared Purpose", 14, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfSharedPurpose.class));
        cards.add(new SetCardInfo("Golden-Tail Disciple", 15, Rarity.COMMON, mage.cards.g.GoldenTailDisciple.class));
        cards.add(new SetCardInfo("Goro-Goro, Disciple of Ryusei", 145, Rarity.RARE, mage.cards.g.GoroGoroDiscipleOfRyusei.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goro-Goro, Disciple of Ryusei", 320, Rarity.RARE, mage.cards.g.GoroGoroDiscipleOfRyusei.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goro-Goro, Disciple of Ryusei", 466, Rarity.RARE, mage.cards.g.GoroGoroDiscipleOfRyusei.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grafted Growth", 188, Rarity.COMMON, mage.cards.g.GraftedGrowth.class));
        cards.add(new SetCardInfo("Gravelighter", 98, Rarity.UNCOMMON, mage.cards.g.Gravelighter.class));
        cards.add(new SetCardInfo("Greasefang, Okiba Boss", 220, Rarity.RARE, mage.cards.g.GreasefangOkibaBoss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greasefang, Okiba Boss", 397, Rarity.RARE, mage.cards.g.GreasefangOkibaBoss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greasefang, Okiba Boss", 485, Rarity.RARE, mage.cards.g.GreasefangOkibaBoss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greater Tanuki", 189, Rarity.COMMON, mage.cards.g.GreaterTanuki.class));
        cards.add(new SetCardInfo("Guardians of Oboro", 317, Rarity.COMMON, mage.cards.g.GuardiansOfOboro.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardians of Oboro", 56, Rarity.COMMON, mage.cards.g.GuardiansOfOboro.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hand of Enlightenment", 11, Rarity.COMMON, mage.cards.h.HandOfEnlightenment.class));
        cards.add(new SetCardInfo("Harmonious Emergence", 190, Rarity.COMMON, mage.cards.h.HarmoniousEmergence.class));
        cards.add(new SetCardInfo("Heiko Yamazaki, the General", 146, Rarity.UNCOMMON, mage.cards.h.HeikoYamazakiTheGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heiko Yamazaki, the General", 321, Rarity.UNCOMMON, mage.cards.h.HeikoYamazakiTheGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heir of the Ancient Fang", 191, Rarity.COMMON, mage.cards.h.HeirOfTheAncientFang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heir of the Ancient Fang", 325, Rarity.COMMON, mage.cards.h.HeirOfTheAncientFang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu Consumes All", 221, Rarity.MYTHIC, mage.cards.h.HidetsuguConsumesAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu Consumes All", 361, Rarity.MYTHIC, mage.cards.h.HidetsuguConsumesAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu Consumes All", 486, Rarity.MYTHIC, mage.cards.h.HidetsuguConsumesAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 378, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 429, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 430, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 431, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 432, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 454, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 99, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High-Speed Hoverbike", 247, Rarity.UNCOMMON, mage.cards.h.HighSpeedHoverbike.class));
        cards.add(new SetCardInfo("Hinata, Dawn-Crowned", 222, Rarity.RARE, mage.cards.h.HinataDawnCrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hinata, Dawn-Crowned", 398, Rarity.RARE, mage.cards.h.HinataDawnCrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hinata, Dawn-Crowned", 487, Rarity.RARE, mage.cards.h.HinataDawnCrowned.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Historian's Wisdom", 192, Rarity.UNCOMMON, mage.cards.h.HistoriansWisdom.class));
        cards.add(new SetCardInfo("Hotshot Mechanic", 16, Rarity.UNCOMMON, mage.cards.h.HotshotMechanic.class));
        cards.add(new SetCardInfo("Imperial Moth", 4, Rarity.COMMON, mage.cards.i.ImperialMoth.class));
        cards.add(new SetCardInfo("Imperial Oath", 17, Rarity.COMMON, mage.cards.i.ImperialOath.class));
        cards.add(new SetCardInfo("Imperial Recovery Unit", 18, Rarity.UNCOMMON, mage.cards.i.ImperialRecoveryUnit.class));
        cards.add(new SetCardInfo("Imperial Subduer", 19, Rarity.COMMON, mage.cards.i.ImperialSubduer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Imperial Subduer", 310, Rarity.COMMON, mage.cards.i.ImperialSubduer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkrise Infiltrator", 100, Rarity.COMMON, mage.cards.i.InkriseInfiltrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inkrise Infiltrator", 341, Rarity.COMMON, mage.cards.i.InkriseInfiltrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Intercessor's Arrest", 20, Rarity.COMMON, mage.cards.i.IntercessorsArrest.class));
        cards.add(new SetCardInfo("Inventive Iteration", 355, Rarity.RARE, mage.cards.i.InventiveIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inventive Iteration", 443, Rarity.RARE, mage.cards.i.InventiveIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inventive Iteration", 57, Rarity.RARE, mage.cards.i.InventiveIteration.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invigorating Hot Spring", 223, Rarity.UNCOMMON, mage.cards.i.InvigoratingHotSpring.class));
        cards.add(new SetCardInfo("Invoke Calamity", 147, Rarity.RARE, mage.cards.i.InvokeCalamity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Calamity", 384, Rarity.RARE, mage.cards.i.InvokeCalamity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Calamity", 467, Rarity.RARE, mage.cards.i.InvokeCalamity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Despair", 101, Rarity.RARE, mage.cards.i.InvokeDespair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Despair", 379, Rarity.RARE, mage.cards.i.InvokeDespair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Despair", 455, Rarity.RARE, mage.cards.i.InvokeDespair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Despair", 506, Rarity.RARE, mage.cards.i.InvokeDespair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Justice", 21, Rarity.RARE, mage.cards.i.InvokeJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Justice", 366, Rarity.RARE, mage.cards.i.InvokeJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke Justice", 437, Rarity.RARE, mage.cards.i.InvokeJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke the Ancients", 193, Rarity.RARE, mage.cards.i.InvokeTheAncients.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke the Ancients", 390, Rarity.RARE, mage.cards.i.InvokeTheAncients.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke the Ancients", 474, Rarity.RARE, mage.cards.i.InvokeTheAncients.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke the Winds", 370, Rarity.RARE, mage.cards.i.InvokeTheWinds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke the Winds", 444, Rarity.RARE, mage.cards.i.InvokeTheWinds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invoke the Winds", 58, Rarity.RARE, mage.cards.i.InvokeTheWinds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Apprentice", 248, Rarity.COMMON, mage.cards.i.IronApprentice.class));
        cards.add(new SetCardInfo("Ironhoof Boar", 148, Rarity.COMMON, mage.cards.i.IronhoofBoar.class));
        cards.add(new SetCardInfo("Island", 285, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 286, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 295, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 296, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Isshin, Two Heavens as One", 224, Rarity.RARE, mage.cards.i.IsshinTwoHeavensAsOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isshin, Two Heavens as One", 328, Rarity.RARE, mage.cards.i.IsshinTwoHeavensAsOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isshin, Two Heavens as One", 488, Rarity.RARE, mage.cards.i.IsshinTwoHeavensAsOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jin-Gitaxias, Progress Tyrant", 307, Rarity.MYTHIC, mage.cards.j.JinGitaxiasProgressTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jin-Gitaxias, Progress Tyrant", 371, Rarity.MYTHIC, mage.cards.j.JinGitaxiasProgressTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jin-Gitaxias, Progress Tyrant", 427, Rarity.MYTHIC, mage.cards.j.JinGitaxiasProgressTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jin-Gitaxias, Progress Tyrant", 445, Rarity.MYTHIC, mage.cards.j.JinGitaxiasProgressTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jin-Gitaxias, Progress Tyrant", 59, Rarity.MYTHIC, mage.cards.j.JinGitaxiasProgressTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jugan Defends the Temple", 194, Rarity.MYTHIC, mage.cards.j.JuganDefendsTheTemple.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jugan Defends the Temple", 359, Rarity.MYTHIC, mage.cards.j.JuganDefendsTheTemple.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jugan Defends the Temple", 475, Rarity.MYTHIC, mage.cards.j.JuganDefendsTheTemple.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jukai Naturalist", 225, Rarity.UNCOMMON, mage.cards.j.JukaiNaturalist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jukai Naturalist", 510, Rarity.UNCOMMON, mage.cards.j.JukaiNaturalist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jukai Preserver", 195, Rarity.COMMON, mage.cards.j.JukaiPreserver.class));
        cards.add(new SetCardInfo("Jukai Trainee", 196, Rarity.COMMON, mage.cards.j.JukaiTrainee.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jukai Trainee", 326, Rarity.COMMON, mage.cards.j.JukaiTrainee.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Hollow", 269, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Junji, the Midnight Sky", 102, Rarity.MYTHIC, mage.cards.j.JunjiTheMidnightSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Junji, the Midnight Sky", 409, Rarity.MYTHIC, mage.cards.j.JunjiTheMidnightSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Junji, the Midnight Sky", 456, Rarity.MYTHIC, mage.cards.j.JunjiTheMidnightSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kairi, the Swirling Sky", 408, Rarity.MYTHIC, mage.cards.k.KairiTheSwirlingSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kairi, the Swirling Sky", 446, Rarity.MYTHIC, mage.cards.k.KairiTheSwirlingSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kairi, the Swirling Sky", 60, Rarity.MYTHIC, mage.cards.k.KairiTheSwirlingSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaito Shizuki", 226, Rarity.MYTHIC, mage.cards.k.KaitoShizuki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaito Shizuki", 305, Rarity.MYTHIC, mage.cards.k.KaitoShizuki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaito Shizuki", 350, Rarity.MYTHIC, mage.cards.k.KaitoShizuki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaito Shizuki", 424, Rarity.MYTHIC, mage.cards.k.KaitoShizuki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaito's Pursuit", 103, Rarity.COMMON, mage.cards.k.KaitosPursuit.class));
        cards.add(new SetCardInfo("Kami of Industry", 149, Rarity.COMMON, mage.cards.k.KamiOfIndustry.class));
        cards.add(new SetCardInfo("Kami of Restless Shadows", 104, Rarity.COMMON, mage.cards.k.KamiOfRestlessShadows.class));
        cards.add(new SetCardInfo("Kami of Terrible Secrets", 105, Rarity.COMMON, mage.cards.k.KamiOfTerribleSecrets.class));
        cards.add(new SetCardInfo("Kami of Transience", 197, Rarity.RARE, mage.cards.k.KamiOfTransience.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kami of Transience", 391, Rarity.RARE, mage.cards.k.KamiOfTransience.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kami of Transience", 476, Rarity.RARE, mage.cards.k.KamiOfTransience.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kami's Flare", 150, Rarity.COMMON, mage.cards.k.KamisFlare.class));
        cards.add(new SetCardInfo("Kappa Tech-Wrecker", 198, Rarity.UNCOMMON, mage.cards.k.KappaTechWrecker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kappa Tech-Wrecker", 348, Rarity.UNCOMMON, mage.cards.k.KappaTechWrecker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kindled Fury", 151, Rarity.COMMON, mage.cards.k.KindledFury.class));
        cards.add(new SetCardInfo("Kirin-Touched Orochi", 212, Rarity.RARE, mage.cards.k.KirinTouchedOrochi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kirin-Touched Orochi", 360, Rarity.RARE, mage.cards.k.KirinTouchedOrochi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kirin-Touched Orochi", 482, Rarity.RARE, mage.cards.k.KirinTouchedOrochi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kitsune Ace", 22, Rarity.COMMON, mage.cards.k.KitsuneAce.class));
        cards.add(new SetCardInfo("Kodama of the West Tree", 199, Rarity.MYTHIC, mage.cards.k.KodamaOfTheWestTree.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kodama of the West Tree", 392, Rarity.MYTHIC, mage.cards.k.KodamaOfTheWestTree.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kodama of the West Tree", 423, Rarity.MYTHIC, mage.cards.k.KodamaOfTheWestTree.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kodama of the West Tree", 477, Rarity.MYTHIC, mage.cards.k.KodamaOfTheWestTree.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kotose, the Silent Spider", 228, Rarity.RARE, mage.cards.k.KotoseTheSilentSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kotose, the Silent Spider", 351, Rarity.RARE, mage.cards.k.KotoseTheSilentSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kotose, the Silent Spider", 490, Rarity.RARE, mage.cards.k.KotoseTheSilentSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kumano Faces Kakkazan", 152, Rarity.UNCOMMON, mage.cards.k.KumanoFacesKakkazan.class));
        cards.add(new SetCardInfo("Kura, the Boundless Sky", 200, Rarity.MYTHIC, mage.cards.k.KuraTheBoundlessSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kura, the Boundless Sky", 411, Rarity.MYTHIC, mage.cards.k.KuraTheBoundlessSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kura, the Boundless Sky", 478, Rarity.MYTHIC, mage.cards.k.KuraTheBoundlessSky.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kyodai, Soul of Kamigawa", 23, Rarity.RARE, mage.cards.k.KyodaiSoulOfKamigawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kyodai, Soul of Kamigawa", 407, Rarity.RARE, mage.cards.k.KyodaiSoulOfKamigawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kyodai, Soul of Kamigawa", 438, Rarity.RARE, mage.cards.k.KyodaiSoulOfKamigawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leech Gauntlet", 106, Rarity.UNCOMMON, mage.cards.l.LeechGauntlet.class));
        cards.add(new SetCardInfo("Lethal Exploit", 107, Rarity.COMMON, mage.cards.l.LethalExploit.class));
        cards.add(new SetCardInfo("Life of Toshiro Umezawa", 108, Rarity.UNCOMMON, mage.cards.l.LifeOfToshiroUmezawa.class));
        cards.add(new SetCardInfo("Light the Way", 24, Rarity.COMMON, mage.cards.l.LightTheWay.class));
        cards.add(new SetCardInfo("Light-Paws, Emperor's Voice", 25, Rarity.RARE, mage.cards.l.LightPawsEmperorsVoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Light-Paws, Emperor's Voice", 367, Rarity.RARE, mage.cards.l.LightPawsEmperorsVoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Light-Paws, Emperor's Voice", 439, Rarity.RARE, mage.cards.l.LightPawsEmperorsVoice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Likeness of the Seeker", 172, Rarity.UNCOMMON, mage.cards.l.LikenessOfTheSeeker.class));
        cards.add(new SetCardInfo("Lion Sash", 26, Rarity.RARE, mage.cards.l.LionSash.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lion Sash", 368, Rarity.RARE, mage.cards.l.LionSash.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lion Sash", 440, Rarity.RARE, mage.cards.l.LionSash.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living Breakthrough", 355, Rarity.RARE, mage.cards.l.LivingBreakthrough.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living Breakthrough", 443, Rarity.RARE, mage.cards.l.LivingBreakthrough.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living Breakthrough", 57, Rarity.RARE, mage.cards.l.LivingBreakthrough.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lizard Blades", 153, Rarity.RARE, mage.cards.l.LizardBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lizard Blades", 385, Rarity.RARE, mage.cards.l.LizardBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lizard Blades", 468, Rarity.RARE, mage.cards.l.LizardBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lucky Offering", 27, Rarity.COMMON, mage.cards.l.LuckyOffering.class));
        cards.add(new SetCardInfo("Malicious Malfunction", 110, Rarity.UNCOMMON, mage.cards.m.MaliciousMalfunction.class));
        cards.add(new SetCardInfo("March of Burgeoning Life", 201, Rarity.RARE, mage.cards.m.MarchOfBurgeoningLife.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Burgeoning Life", 393, Rarity.RARE, mage.cards.m.MarchOfBurgeoningLife.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Burgeoning Life", 479, Rarity.RARE, mage.cards.m.MarchOfBurgeoningLife.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Otherworldly Light", 28, Rarity.RARE, mage.cards.m.MarchOfOtherworldlyLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Otherworldly Light", 369, Rarity.RARE, mage.cards.m.MarchOfOtherworldlyLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Otherworldly Light", 441, Rarity.RARE, mage.cards.m.MarchOfOtherworldlyLight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Reckless Joy", 154, Rarity.RARE, mage.cards.m.MarchOfRecklessJoy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Reckless Joy", 386, Rarity.RARE, mage.cards.m.MarchOfRecklessJoy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Reckless Joy", 469, Rarity.RARE, mage.cards.m.MarchOfRecklessJoy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Swirling Mist", 372, Rarity.RARE, mage.cards.m.MarchOfSwirlingMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Swirling Mist", 447, Rarity.RARE, mage.cards.m.MarchOfSwirlingMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Swirling Mist", 61, Rarity.RARE, mage.cards.m.MarchOfSwirlingMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Wretched Sorrow", 111, Rarity.RARE, mage.cards.m.MarchOfWretchedSorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Wretched Sorrow", 380, Rarity.RARE, mage.cards.m.MarchOfWretchedSorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("March of Wretched Sorrow", 457, Rarity.RARE, mage.cards.m.MarchOfWretchedSorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Master's Rebuke", 202, Rarity.COMMON, mage.cards.m.MastersRebuke.class));
        cards.add(new SetCardInfo("Mech Hangar", 270, Rarity.UNCOMMON, mage.cards.m.MechHangar.class));
        cards.add(new SetCardInfo("Mechtitan Core", 249, Rarity.RARE, mage.cards.m.MechtitanCore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mechtitan Core", 402, Rarity.RARE, mage.cards.m.MechtitanCore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mechtitan Core", 497, Rarity.RARE, mage.cards.m.MechtitanCore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory of Toshiro", 108, Rarity.UNCOMMON, mage.cards.m.MemoryOfToshiro.class));
        cards.add(new SetCardInfo("Michiko's Reign of Truth", 29, Rarity.UNCOMMON, mage.cards.m.MichikosReignOfTruth.class));
        cards.add(new SetCardInfo("Mindlink Mech", 373, Rarity.RARE, mage.cards.m.MindlinkMech.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindlink Mech", 448, Rarity.RARE, mage.cards.m.MindlinkMech.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindlink Mech", 62, Rarity.RARE, mage.cards.m.MindlinkMech.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirror Box", 250, Rarity.RARE, mage.cards.m.MirrorBox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirror Box", 403, Rarity.RARE, mage.cards.m.MirrorBox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirror Box", 498, Rarity.RARE, mage.cards.m.MirrorBox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirrorshell Crab", 63, Rarity.COMMON, mage.cards.m.MirrorshellCrab.class));
        cards.add(new SetCardInfo("Mnemonic Sphere", 64, Rarity.COMMON, mage.cards.m.MnemonicSphere.class));
        cards.add(new SetCardInfo("Mobilizer Mech", 65, Rarity.UNCOMMON, mage.cards.m.MobilizerMech.class));
        cards.add(new SetCardInfo("Moon-Circuit Hacker", 334, Rarity.COMMON, mage.cards.m.MoonCircuitHacker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moon-Circuit Hacker", 67, Rarity.COMMON, mage.cards.m.MoonCircuitHacker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moonfolk Puzzlemaker", 68, Rarity.COMMON, mage.cards.m.MoonfolkPuzzlemaker.class));
        cards.add(new SetCardInfo("Moonsnare Prototype", 69, Rarity.COMMON, mage.cards.m.MoonsnarePrototype.class));
        cards.add(new SetCardInfo("Moonsnare Specialist", 335, Rarity.COMMON, mage.cards.m.MoonsnareSpecialist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moonsnare Specialist", 70, Rarity.COMMON, mage.cards.m.MoonsnareSpecialist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mothrider Patrol", 30, Rarity.COMMON, mage.cards.m.MothriderPatrol.class));
        cards.add(new SetCardInfo("Mountain", 289, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 290, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 299, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 300, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mukotai Ambusher", 112, Rarity.COMMON, mage.cards.m.MukotaiAmbusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mukotai Ambusher", 342, Rarity.COMMON, mage.cards.m.MukotaiAmbusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mukotai Soulripper", 113, Rarity.RARE, mage.cards.m.MukotaiSoulripper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mukotai Soulripper", 381, Rarity.RARE, mage.cards.m.MukotaiSoulripper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mukotai Soulripper", 458, Rarity.RARE, mage.cards.m.MukotaiSoulripper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nameless Conqueror", 162, Rarity.COMMON, mage.cards.n.NamelessConqueror.class));
        cards.add(new SetCardInfo("Naomi, Pillar of Order", 229, Rarity.UNCOMMON, mage.cards.n.NaomiPillarOfOrder.class));
        cards.add(new SetCardInfo("Nashi, Moon Sage's Scion", 114, Rarity.MYTHIC, mage.cards.n.NashiMoonSagesScion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nashi, Moon Sage's Scion", 343, Rarity.MYTHIC, mage.cards.n.NashiMoonSagesScion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nashi, Moon Sage's Scion", 421, Rarity.MYTHIC, mage.cards.n.NashiMoonSagesScion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nashi, Moon Sage's Scion", 459, Rarity.MYTHIC, mage.cards.n.NashiMoonSagesScion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Network Disruptor", 71, Rarity.COMMON, mage.cards.n.NetworkDisruptor.class));
        cards.add(new SetCardInfo("Network Terminal", 251, Rarity.COMMON, mage.cards.n.NetworkTerminal.class));
        cards.add(new SetCardInfo("Nezumi Bladeblesser", 115, Rarity.COMMON, mage.cards.n.NezumiBladeblesser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Bladeblesser", 318, Rarity.COMMON, mage.cards.n.NezumiBladeblesser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Prowler", 116, Rarity.UNCOMMON, mage.cards.n.NezumiProwler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Prowler", 344, Rarity.UNCOMMON, mage.cards.n.NezumiProwler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Road Captain", 117, Rarity.COMMON, mage.cards.n.NezumiRoadCaptain.class));
        cards.add(new SetCardInfo("Ninja's Kunai", 252, Rarity.COMMON, mage.cards.n.NinjasKunai.class));
        cards.add(new SetCardInfo("Norika Yamazaki, the Poet", 31, Rarity.UNCOMMON, mage.cards.n.NorikaYamazakiThePoet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Norika Yamazaki, the Poet", 311, Rarity.UNCOMMON, mage.cards.n.NorikaYamazakiThePoet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("O-Kagachi Made Manifest", 227, Rarity.MYTHIC, mage.cards.o.OKagachiMadeManifest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("O-Kagachi Made Manifest", 362, Rarity.MYTHIC, mage.cards.o.OKagachiMadeManifest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("O-Kagachi Made Manifest", 489, Rarity.MYTHIC, mage.cards.o.OKagachiMadeManifest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ogre-Head Helm", 155, Rarity.RARE, mage.cards.o.OgreHeadHelm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ogre-Head Helm", 387, Rarity.RARE, mage.cards.o.OgreHeadHelm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ogre-Head Helm", 470, Rarity.RARE, mage.cards.o.OgreHeadHelm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Okiba Reckoner Raid", 117, Rarity.COMMON, mage.cards.o.OkibaReckonerRaid.class));
        cards.add(new SetCardInfo("Okiba Salvage", 118, Rarity.UNCOMMON, mage.cards.o.OkibaSalvage.class));
        cards.add(new SetCardInfo("Oni-Cult Anvil", 230, Rarity.UNCOMMON, mage.cards.o.OniCultAnvil.class));
        cards.add(new SetCardInfo("Orochi Merge-Keeper", 203, Rarity.UNCOMMON, mage.cards.o.OrochiMergeKeeper.class));
        cards.add(new SetCardInfo("Otawara, Soaring City", 271, Rarity.RARE, mage.cards.o.OtawaraSoaringCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Otawara, Soaring City", 414, Rarity.RARE, mage.cards.o.OtawaraSoaringCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Otawara, Soaring City", 503, Rarity.RARE, mage.cards.o.OtawaraSoaringCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Papercraft Decoy", 253, Rarity.COMMON, mage.cards.p.PapercraftDecoy.class));
        cards.add(new SetCardInfo("Patchwork Automaton", 254, Rarity.UNCOMMON, mage.cards.p.PatchworkAutomaton.class));
        cards.add(new SetCardInfo("Peerless Samurai", 156, Rarity.COMMON, mage.cards.p.PeerlessSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peerless Samurai", 322, Rarity.COMMON, mage.cards.p.PeerlessSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 283, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 284, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 293, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 294, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Planar Incision", 72, Rarity.COMMON, mage.cards.p.PlanarIncision.class));
        cards.add(new SetCardInfo("Portrait of Michiko", 29, Rarity.UNCOMMON, mage.cards.p.PortraitOfMichiko.class));
        cards.add(new SetCardInfo("Prodigy's Prototype", 231, Rarity.UNCOMMON, mage.cards.p.ProdigysPrototype.class));
        cards.add(new SetCardInfo("Prosperous Thief", 336, Rarity.UNCOMMON, mage.cards.p.ProsperousThief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prosperous Thief", 73, Rarity.UNCOMMON, mage.cards.p.ProsperousThief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rabbit Battery", 157, Rarity.UNCOMMON, mage.cards.r.RabbitBattery.class));
        cards.add(new SetCardInfo("Raiyuu, Storm's Edge", 232, Rarity.RARE, mage.cards.r.RaiyuuStormsEdge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raiyuu, Storm's Edge", 329, Rarity.RARE, mage.cards.r.RaiyuuStormsEdge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raiyuu, Storm's Edge", 491, Rarity.RARE, mage.cards.r.RaiyuuStormsEdge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reality Heist", 75, Rarity.UNCOMMON, mage.cards.r.RealityHeist.class));
        cards.add(new SetCardInfo("Reckoner Bankbuster", 255, Rarity.RARE, mage.cards.r.ReckonerBankbuster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reckoner Bankbuster", 404, Rarity.RARE, mage.cards.r.ReckonerBankbuster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reckoner Bankbuster", 499, Rarity.RARE, mage.cards.r.ReckonerBankbuster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reckoner Shakedown", 119, Rarity.COMMON, mage.cards.r.ReckonerShakedown.class));
        cards.add(new SetCardInfo("Reckoner's Bargain", 120, Rarity.COMMON, mage.cards.r.ReckonersBargain.class));
        cards.add(new SetCardInfo("Reflection of Kiki-Jiki", 141, Rarity.RARE, mage.cards.r.ReflectionOfKikiJiki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflection of Kiki-Jiki", 357, Rarity.RARE, mage.cards.r.ReflectionOfKikiJiki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflection of Kiki-Jiki", 465, Rarity.RARE, mage.cards.r.ReflectionOfKikiJiki.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Regent's Authority", 32, Rarity.COMMON, mage.cards.r.RegentsAuthority.class));
        cards.add(new SetCardInfo("Reinforced Ronin", 158, Rarity.UNCOMMON, mage.cards.r.ReinforcedRonin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reinforced Ronin", 323, Rarity.UNCOMMON, mage.cards.r.ReinforcedRonin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reito Sentinel", 256, Rarity.UNCOMMON, mage.cards.r.ReitoSentinel.class));
        cards.add(new SetCardInfo("Remnant of the Rising Star", 194, Rarity.MYTHIC, mage.cards.r.RemnantOfTheRisingStar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Remnant of the Rising Star", 359, Rarity.MYTHIC, mage.cards.r.RemnantOfTheRisingStar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Remnant of the Rising Star", 475, Rarity.MYTHIC, mage.cards.r.RemnantOfTheRisingStar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Repel the Vile", 33, Rarity.COMMON, mage.cards.r.RepelTheVile.class));
        cards.add(new SetCardInfo("Replication Specialist", 76, Rarity.UNCOMMON, mage.cards.r.ReplicationSpecialist.class));
        cards.add(new SetCardInfo("Return to Action", 121, Rarity.COMMON, mage.cards.r.ReturnToAction.class));
        cards.add(new SetCardInfo("Risona, Asari Commander", 233, Rarity.RARE, mage.cards.r.RisonaAsariCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Risona, Asari Commander", 330, Rarity.RARE, mage.cards.r.RisonaAsariCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Risona, Asari Commander", 425, Rarity.RARE, mage.cards.r.RisonaAsariCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Risona, Asari Commander", 492, Rarity.RARE, mage.cards.r.RisonaAsariCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roadside Reliquary", 272, Rarity.UNCOMMON, mage.cards.r.RoadsideReliquary.class));
        cards.add(new SetCardInfo("Roaring Earth", 204, Rarity.UNCOMMON, mage.cards.r.RoaringEarth.class));
        cards.add(new SetCardInfo("Rugged Highlands", 273, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Runaway Trash-Bot", 257, Rarity.UNCOMMON, mage.cards.r.RunawayTrashBot.class));
        cards.add(new SetCardInfo("Saiba Trespassers", 77, Rarity.COMMON, mage.cards.s.SaibaTrespassers.class));
        cards.add(new SetCardInfo("Satoru Umezawa", 234, Rarity.RARE, mage.cards.s.SatoruUmezawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satoru Umezawa", 352, Rarity.RARE, mage.cards.s.SatoruUmezawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satoru Umezawa", 426, Rarity.RARE, mage.cards.s.SatoruUmezawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satoru Umezawa", 493, Rarity.RARE, mage.cards.s.SatoruUmezawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satoru Umezawa", 507, Rarity.RARE, mage.cards.s.SatoruUmezawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satsuki, the Living Lore", 235, Rarity.RARE, mage.cards.s.SatsukiTheLivingLore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satsuki, the Living Lore", 399, Rarity.RARE, mage.cards.s.SatsukiTheLivingLore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satsuki, the Living Lore", 494, Rarity.RARE, mage.cards.s.SatsukiTheLivingLore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scoured Barrens", 274, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scrap Welder", 159, Rarity.RARE, mage.cards.s.ScrapWelder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrap Welder", 388, Rarity.RARE, mage.cards.s.ScrapWelder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrap Welder", 471, Rarity.RARE, mage.cards.s.ScrapWelder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrapyard Steelbreaker", 160, Rarity.COMMON, mage.cards.s.ScrapyardSteelbreaker.class));
        cards.add(new SetCardInfo("Searchlight Companion", 258, Rarity.COMMON, mage.cards.s.SearchlightCompanion.class));
        cards.add(new SetCardInfo("Season of Renewal", 205, Rarity.COMMON, mage.cards.s.SeasonOfRenewal.class));
        cards.add(new SetCardInfo("Secluded Courtyard", 275, Rarity.UNCOMMON, mage.cards.s.SecludedCourtyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secluded Courtyard", 512, Rarity.UNCOMMON, mage.cards.s.SecludedCourtyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seismic Wave", 161, Rarity.UNCOMMON, mage.cards.s.SeismicWave.class));
        cards.add(new SetCardInfo("Selfless Samurai", 312, Rarity.UNCOMMON, mage.cards.s.SelflessSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selfless Samurai", 35, Rarity.UNCOMMON, mage.cards.s.SelflessSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seshiro's Living Legacy", 210, Rarity.COMMON, mage.cards.s.SeshirosLivingLegacy.class));
        cards.add(new SetCardInfo("Seven-Tail Mentor", 313, Rarity.COMMON, mage.cards.s.SevenTailMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seven-Tail Mentor", 36, Rarity.COMMON, mage.cards.s.SevenTailMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shigeki, Jukai Visionary", 206, Rarity.RARE, mage.cards.s.ShigekiJukaiVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shigeki, Jukai Visionary", 394, Rarity.RARE, mage.cards.s.ShigekiJukaiVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shigeki, Jukai Visionary", 480, Rarity.RARE, mage.cards.s.ShigekiJukaiVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Short Circuit", 78, Rarity.COMMON, mage.cards.s.ShortCircuit.class));
        cards.add(new SetCardInfo("Shrine Steward", 259, Rarity.COMMON, mage.cards.s.ShrineSteward.class));
        cards.add(new SetCardInfo("Silver-Fur Master", 236, Rarity.UNCOMMON, mage.cards.s.SilverFurMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silver-Fur Master", 353, Rarity.UNCOMMON, mage.cards.s.SilverFurMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silver-Fur Master", 511, Rarity.UNCOMMON, mage.cards.s.SilverFurMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simian Sling", 163, Rarity.COMMON, mage.cards.s.SimianSling.class));
        cards.add(new SetCardInfo("Sky-Blessed Samurai", 314, Rarity.UNCOMMON, mage.cards.s.SkyBlessedSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sky-Blessed Samurai", 37, Rarity.UNCOMMON, mage.cards.s.SkyBlessedSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyswimmer Koi", 79, Rarity.COMMON, mage.cards.s.SkyswimmerKoi.class));
        cards.add(new SetCardInfo("Sokenzan Smelter", 164, Rarity.UNCOMMON, mage.cards.s.SokenzanSmelter.class));
        cards.add(new SetCardInfo("Sokenzan, Crucible of Defiance", 276, Rarity.RARE, mage.cards.s.SokenzanCrucibleOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokenzan, Crucible of Defiance", 415, Rarity.RARE, mage.cards.s.SokenzanCrucibleOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokenzan, Crucible of Defiance", 504, Rarity.RARE, mage.cards.s.SokenzanCrucibleOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Transfer", 122, Rarity.RARE, mage.cards.s.SoulTransfer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Transfer", 382, Rarity.RARE, mage.cards.s.SoulTransfer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Transfer", 460, Rarity.RARE, mage.cards.s.SoulTransfer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spell Pierce", 80, Rarity.COMMON, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Spinning Wheel Kick", 207, Rarity.UNCOMMON, mage.cards.s.SpinningWheelKick.class));
        cards.add(new SetCardInfo("Spirit-Sister's Call", 237, Rarity.MYTHIC, mage.cards.s.SpiritSistersCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirit-Sister's Call", 400, Rarity.MYTHIC, mage.cards.s.SpiritSistersCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirit-Sister's Call", 495, Rarity.MYTHIC, mage.cards.s.SpiritSistersCall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirited Companion", 38, Rarity.COMMON, mage.cards.s.SpiritedCompanion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirited Companion", 508, Rarity.COMMON, mage.cards.s.SpiritedCompanion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spring-Leaf Avenger", 208, Rarity.RARE, mage.cards.s.SpringLeafAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spring-Leaf Avenger", 349, Rarity.RARE, mage.cards.s.SpringLeafAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spring-Leaf Avenger", 481, Rarity.RARE, mage.cards.s.SpringLeafAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Storyweave", 209, Rarity.UNCOMMON, mage.cards.s.Storyweave.class));
        cards.add(new SetCardInfo("Suit Up", 81, Rarity.COMMON, mage.cards.s.SuitUp.class));
        cards.add(new SetCardInfo("Sunblade Samurai", 315, Rarity.COMMON, mage.cards.s.SunbladeSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunblade Samurai", 39, Rarity.COMMON, mage.cards.s.SunbladeSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Surgehacker Mech", 260, Rarity.RARE, mage.cards.s.SurgehackerMech.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Surgehacker Mech", 405, Rarity.RARE, mage.cards.s.SurgehackerMech.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Surgehacker Mech", 500, Rarity.RARE, mage.cards.s.SurgehackerMech.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 287, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 288, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 297, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 298, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 277, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Takenuma, Abandoned Mire", 278, Rarity.RARE, mage.cards.t.TakenumaAbandonedMire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Takenuma, Abandoned Mire", 416, Rarity.RARE, mage.cards.t.TakenumaAbandonedMire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Takenuma, Abandoned Mire", 505, Rarity.RARE, mage.cards.t.TakenumaAbandonedMire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tales of Master Seshiro", 210, Rarity.COMMON, mage.cards.t.TalesOfMasterSeshiro.class));
        cards.add(new SetCardInfo("Tameshi, Reality Architect", 375, Rarity.RARE, mage.cards.t.TameshiRealityArchitect.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tameshi, Reality Architect", 450, Rarity.RARE, mage.cards.t.TameshiRealityArchitect.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tameshi, Reality Architect", 82, Rarity.RARE, mage.cards.t.TameshiRealityArchitect.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo's Compleation", 83, Rarity.COMMON, mage.cards.t.TamiyosCompleation.class));
        cards.add(new SetCardInfo("Tamiyo's Safekeeping", 211, Rarity.COMMON, mage.cards.t.TamiyosSafekeeping.class));
        cards.add(new SetCardInfo("Tamiyo, Compleated Sage", 238, Rarity.MYTHIC, mage.cards.t.TamiyoCompleatedSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo, Compleated Sage", 306, Rarity.MYTHIC, mage.cards.t.TamiyoCompleatedSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo, Compleated Sage", 308, Rarity.MYTHIC, mage.cards.t.TamiyoCompleatedSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tamiyo, Compleated Sage", 428, Rarity.MYTHIC, mage.cards.t.TamiyoCompleatedSage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tatsunari, Toad Rider", 123, Rarity.RARE, mage.cards.t.TatsunariToadRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tatsunari, Toad Rider", 345, Rarity.RARE, mage.cards.t.TatsunariToadRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tatsunari, Toad Rider", 461, Rarity.RARE, mage.cards.t.TatsunariToadRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teachings of the Kirin", 212, Rarity.RARE, mage.cards.t.TeachingsOfTheKirin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teachings of the Kirin", 360, Rarity.RARE, mage.cards.t.TeachingsOfTheKirin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teachings of the Kirin", 482, Rarity.RARE, mage.cards.t.TeachingsOfTheKirin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tempered in Solitude", 165, Rarity.UNCOMMON, mage.cards.t.TemperedInSolitude.class));
        cards.add(new SetCardInfo("Tezzeret, Betrayer of Flesh", 304, Rarity.MYTHIC, mage.cards.t.TezzeretBetrayerOfFlesh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Betrayer of Flesh", 376, Rarity.MYTHIC, mage.cards.t.TezzeretBetrayerOfFlesh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Betrayer of Flesh", 419, Rarity.MYTHIC, mage.cards.t.TezzeretBetrayerOfFlesh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Betrayer of Flesh", 84, Rarity.MYTHIC, mage.cards.t.TezzeretBetrayerOfFlesh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Dragon-Kami Reborn", 181, Rarity.RARE, mage.cards.t.TheDragonKamiReborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Dragon-Kami Reborn", 358, Rarity.RARE, mage.cards.t.TheDragonKamiReborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Dragon-Kami Reborn", 473, Rarity.RARE, mage.cards.t.TheDragonKamiReborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Fall of Lord Konda", 12, Rarity.UNCOMMON, mage.cards.t.TheFallOfLordKonda.class));
        cards.add(new SetCardInfo("The Kami War", 227, Rarity.MYTHIC, mage.cards.t.TheKamiWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Kami War", 362, Rarity.MYTHIC, mage.cards.t.TheKamiWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Kami War", 489, Rarity.MYTHIC, mage.cards.t.TheKamiWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Long Reach of Night", 109, Rarity.UNCOMMON, mage.cards.t.TheLongReachOfNight.class));
        cards.add(new SetCardInfo("The Modern Age", 66, Rarity.COMMON, mage.cards.t.TheModernAge.class));
        cards.add(new SetCardInfo("The Reality Chip", 374, Rarity.RARE, mage.cards.t.TheRealityChip.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Reality Chip", 449, Rarity.RARE, mage.cards.t.TheRealityChip.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Reality Chip", 74, Rarity.RARE, mage.cards.t.TheRealityChip.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Restoration of Eiganjo", 34, Rarity.RARE, mage.cards.t.TheRestorationOfEiganjo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Restoration of Eiganjo", 354, Rarity.RARE, mage.cards.t.TheRestorationOfEiganjo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Restoration of Eiganjo", 442, Rarity.RARE, mage.cards.t.TheRestorationOfEiganjo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Shattered States Era", 162, Rarity.COMMON, mage.cards.t.TheShatteredStatesEra.class));
        cards.add(new SetCardInfo("The Wandering Emperor", 303, Rarity.MYTHIC, mage.cards.t.TheWanderingEmperor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Wandering Emperor", 316, Rarity.MYTHIC, mage.cards.t.TheWanderingEmperor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Wandering Emperor", 418, Rarity.MYTHIC, mage.cards.t.TheWanderingEmperor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Wandering Emperor", 42, Rarity.MYTHIC, mage.cards.t.TheWanderingEmperor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thirst for Knowledge", 85, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thornwood Falls", 279, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thousand-Faced Shadow", 337, Rarity.RARE, mage.cards.t.ThousandFacedShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thousand-Faced Shadow", 451, Rarity.RARE, mage.cards.t.ThousandFacedShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thousand-Faced Shadow", 86, Rarity.RARE, mage.cards.t.ThousandFacedShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundering Raiju", 166, Rarity.RARE, mage.cards.t.ThunderingRaiju.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundering Raiju", 389, Rarity.RARE, mage.cards.t.ThunderingRaiju.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundering Raiju", 472, Rarity.RARE, mage.cards.t.ThunderingRaiju.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thundersteel Colossus", 261, Rarity.COMMON, mage.cards.t.ThundersteelColossus.class));
        cards.add(new SetCardInfo("Touch the Spirit Realm", 40, Rarity.UNCOMMON, mage.cards.t.TouchTheSpiritRealm.class));
        cards.add(new SetCardInfo("Towashi Guide-Bot", 262, Rarity.UNCOMMON, mage.cards.t.TowashiGuideBot.class));
        cards.add(new SetCardInfo("Towashi Songshaper", 167, Rarity.COMMON, mage.cards.t.TowashiSongshaper.class));
        cards.add(new SetCardInfo("Tranquil Cove", 280, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Tribute to Horobi", 124, Rarity.RARE, mage.cards.t.TributeToHorobi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tribute to Horobi", 356, Rarity.RARE, mage.cards.t.TributeToHorobi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tribute to Horobi", 462, Rarity.RARE, mage.cards.t.TributeToHorobi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Twinshot Sniper", 168, Rarity.UNCOMMON, mage.cards.t.TwinshotSniper.class));
        cards.add(new SetCardInfo("Twisted Embrace", 125, Rarity.COMMON, mage.cards.t.TwistedEmbrace.class));
        cards.add(new SetCardInfo("Uncharted Haven", 281, Rarity.COMMON, mage.cards.u.UnchartedHaven.class));
        cards.add(new SetCardInfo("Undercity Scrounger", 126, Rarity.COMMON, mage.cards.u.UndercityScrounger.class));
        cards.add(new SetCardInfo("Unforgiving One", 127, Rarity.UNCOMMON, mage.cards.u.UnforgivingOne.class));
        cards.add(new SetCardInfo("Unstoppable Ogre", 169, Rarity.COMMON, mage.cards.u.UnstoppableOgre.class));
        cards.add(new SetCardInfo("Upriser Renegade", 170, Rarity.UNCOMMON, mage.cards.u.UpriserRenegade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Upriser Renegade", 324, Rarity.UNCOMMON, mage.cards.u.UpriserRenegade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vector Glider", 66, Rarity.COMMON, mage.cards.v.VectorGlider.class));
        cards.add(new SetCardInfo("Vessel of the All-Consuming", 221, Rarity.MYTHIC, mage.cards.v.VesselOfTheAllConsuming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vessel of the All-Consuming", 361, Rarity.MYTHIC, mage.cards.v.VesselOfTheAllConsuming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vessel of the All-Consuming", 486, Rarity.MYTHIC, mage.cards.v.VesselOfTheAllConsuming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Virus Beetle", 128, Rarity.COMMON, mage.cards.v.VirusBeetle.class));
        cards.add(new SetCardInfo("Vision of the Unspeakable", 48, Rarity.UNCOMMON, mage.cards.v.VisionOfTheUnspeakable.class));
        cards.add(new SetCardInfo("Voltage Surge", 171, Rarity.COMMON, mage.cards.v.VoltageSurge.class));
        cards.add(new SetCardInfo("Walking Skyscraper", 263, Rarity.UNCOMMON, mage.cards.w.WalkingSkyscraper.class));
        cards.add(new SetCardInfo("Wanderer's Intervention", 41, Rarity.COMMON, mage.cards.w.WanderersIntervention.class));
        cards.add(new SetCardInfo("Weaver of Harmony", 213, Rarity.RARE, mage.cards.w.WeaverOfHarmony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Weaver of Harmony", 395, Rarity.RARE, mage.cards.w.WeaverOfHarmony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Weaver of Harmony", 483, Rarity.RARE, mage.cards.w.WeaverOfHarmony.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Webspinner Cuff", 214, Rarity.UNCOMMON, mage.cards.w.WebspinnerCuff.class));
        cards.add(new SetCardInfo("When We Were Young", 43, Rarity.UNCOMMON, mage.cards.w.WhenWeWereYoung.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 282, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("You Are Already Dead", 129, Rarity.COMMON, mage.cards.y.YouAreAlreadyDead.class));
    }

    // add common or uncommon double faced card to booster
    @Override
    protected void addDoubleFace(List<Card> booster) {
        Rarity rarity;
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            // relative frequency of common to uncommon DFCs differs between printings
            // using Japanese ratio as it's closest to the ratio of non-DFC commons to uncommons
            if (RandomUtil.nextInt(102) < 54) {
                rarity = Rarity.COMMON;
            } else {
                rarity = Rarity.UNCOMMON;
            }
            addToBooster(booster, getSpecialCardsByRarity(rarity));
        }
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        // rare and mythic DFCs are normal rares
        if (rarity == Rarity.RARE || rarity == Rarity.MYTHIC) {
            return new ArrayList<CardInfo>();
        }
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        if (rarity == Rarity.LAND) {
            // Uncharted Haven is a normal common
            cardInfos.removeIf(cardInfo -> "Uncharted Haven".equals(cardInfo.getName()));
        }
        return cardInfos;
    }

    @Override
    public BoosterCollator createCollator() {
        return new KamigawaNeonDynastyCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/neo.html
// Using Japanese collation for common/uncommon, rare collation inferred from other sets
class KamigawaNeonDynastyCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "36", "112", "17", "317", "9", "183", "30", "131", "315", "179", "27", "115", "15", "325", "33", "67", "1", "196", "10", "100", "32", "70", "22", "346", "41", "56", "30", "115", "9", "191", "310", "156", "10", "347", "39", "94", "36", "326", "15", "1", "100", "17", "70", "32", "112", "27", "334", "22", "131", "33", "196", "41", "115", "30", "179", "309", "94", "15", "183", "9", "100", "19", "33", "335", "27", "156", "22", "342", "32", "56", "36", "319", "1", "191", "17", "67", "39", "196", "19", "318", "41", "179", "15", "94", "10", "70", "30", "341", "9", "27", "183", "33", "156", "17", "112", "313", "191", "22", "56", "39", "131", "32", "339", "41", "67", "19", "1", "322");
    private final CardRun commonB = new CardRun(true, "78", "244", "188", "54", "259", "174", "80", "258", "186", "81", "132", "178", "46", "239", "205", "51", "261", "180", "71", "24", "173", "63", "202", "149", "77", "184", "248", "72", "190", "69", "136", "211", "78", "182", "54", "103", "174", "83", "259", "188", "46", "244", "189", "80", "261", "186", "64", "132", "205", "81", "239", "178", "51", "136", "173", "77", "24", "180", "63", "184", "149", "71", "190", "258", "72", "202", "78", "248", "188", "83", "174", "69", "103", "182", "64", "244", "211", "54", "239", "186", "80", "259", "178", "81", "149", "205", "46", "261", "189", "51", "258", "173", "71", "132", "184", "63", "180", "24", "77", "202", "136", "72", "182", "69", "248", "211", "83", "190", "64", "103", "189");
    private final CardRun commonC = new CardRun(true, "90", "252", "169", "107", "163", "20", "121", "167", "125", "253", "138", "104", "151", "68", "120", "160", "38", "129", "150", "241", "126", "130", "105", "251", "143", "91", "246", "148", "92", "281", "171", "128", "151", "79", "125", "139", "253", "104", "163", "252", "107", "133", "241", "90", "167", "119", "20", "160", "121", "195", "169", "126", "68", "138", "120", "171", "251", "91", "148", "281", "105", "130", "38", "128", "139", "79", "92", "150", "119", "246", "143", "129", "20", "133", "107", "169", "253", "90", "163", "195", "121", "151", "125", "252", "160", "104", "167", "38", "120", "138", "68", "129", "130", "91", "241", "150", "126", "246", "171", "92", "251", "148", "105", "281", "143", "128", "79", "139", "119", "195", "133");
    private final CardRun commonDFC = new CardRun(true, "4", "210", "48", "11", "109", "162", "108", "12", "66", "172", "152", "117", "177", "4", "210", "11", "108", "162", "29", "48", "117", "66", "4", "177", "11", "210", "162", "172", "12", "117", "48", "108", "4", "109", "210", "162", "29", "66", "172", "11", "152", "12", "108", "177", "109", "66", "29", "210", "4", "162", "117", "11", "152", "66", "117", "162", "4", "48", "210", "12", "172", "152", "11", "117", "162", "109", "66", "4", "108", "177", "29", "162", "11", "66", "109", "210", "117", "177", "4", "48", "11", "108", "152", "12", "172", "29", "210", "4", "117", "66", "109", "48", "12", "162", "11", "210", "152", "172", "66", "29", "177", "117");
    private final CardRun uncommonA = new CardRun(true, "170", "40", "158", "76", "340", "18", "215", "3", "146", "231", "314", "161", "31", "219", "53", "198", "236", "175", "49", "168", "344", "185", "73", "40", "95", "142", "35", "76", "215", "87", "5", "229", "324", "18", "158", "219", "53", "3", "116", "168", "37", "198", "146", "231", "332", "161", "311", "175", "236", "142", "312", "87", "170", "185", "5", "229", "73", "40", "95", "76", "327", "18", "158", "219", "321", "231", "49", "168", "53", "3", "353", "161", "116", "198", "37", "175", "31", "142", "336", "229", "35", "76", "5", "185", "170", "87", "323", "40", "215", "18", "95", "3", "146", "348", "236", "175", "49", "219", "37", "231", "31", "168", "333", "161", "116", "185", "35", "142", "331", "229", "73", "87");
    private final CardRun uncommonB = new CardRun(true, "144", "262", "207", "270", "137", "214", "96", "47", "240", "14", "93", "187", "164", "50", "223", "257", "157", "209", "247", "225", "43", "75", "118", "263", "203", "45", "165", "16", "110", "243", "65", "6", "192", "98", "55", "242", "204", "97", "272", "256", "85", "127", "176", "230", "52", "135", "254", "218", "106", "275", "216", "96", "214", "144", "270", "44", "262", "187", "93", "240", "207", "47", "137", "14", "257", "209", "43", "164", "225", "263", "75", "223", "203", "247", "50", "157", "118", "16", "192", "45", "6", "110", "204", "55", "165", "242", "98", "65", "243", "97", "85", "254", "272", "52", "216", "106", "135", "256", "218", "176", "127", "230", "275", "44");
    private final CardRun rare = new CardRun(false, "7", "8", "13", "21", "23", "25", "26", "28", "34", "57", "58", "61", "62", "74", "82", "86", "88", "99", "101", "111", "113", "122", "123", "124", "141", "145", "147", "153", "154", "155", "159", "166", "181", "193", "197", "201", "206", "208", "212", "213", "217", "220", "222", "224", "228", "232", "233", "234", "235", "245", "249", "250", "255", "260", "266", "268", "271", "276", "278", "7", "8", "13", "21", "23", "25", "26", "28", "34", "57", "58", "61", "62", "74", "82", "86", "88", "99", "101", "111", "113", "122", "123", "124", "141", "145", "147", "153", "154", "155", "159", "166", "181", "193", "197", "201", "206", "208", "212", "213", "217", "220", "222", "224", "228", "232", "233", "234", "235", "245", "249", "250", "255", "260", "266", "268", "271", "276", "278", "2", "42", "59", "60", "84", "89", "102", "114", "134", "140", "194", "199", "200", "221", "226", "227", "237", "238");
    private final CardRun land = new CardRun(true, "265", "274", "282", "279", "264", "285", "277", "283", "273", "267", "280", "274", "300", "295", "282", "287", "290", "292", "284", "298", "279", "296", "297", "277", "293", "269", "282", "289", "267", "273", "279", "286", "302", "274", "294", "280", "277", "295", "291", "299", "265", "282", "269", "264", "279", "297", "280", "300", "274", "285", "265", "273", "296", "293", "302", "287", "269", "294", "279", "298", "267", "301", "283", "295", "277", "273", "280", "299", "269", "274", "302", "267", "298", "264", "290", "286", "282", "293", "297", "300", "265", "296", "277", "284", "301", "273", "264", "292", "274", "289", "294", "267", "282", "288", "279", "280", "269", "265", "291", "298", "299", "264", "301", "267", "293", "300", "280", "273", "265", "269", "277", "294", "297", "301", "264", "299", "295", "302", "288", "296");

    private final BoosterStructure AABBBCCCC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AABBBBCCC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);

    private final BoosterStructure D1 = new BoosterStructure(commonDFC);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.41 A commons (243 / 101)
    // 3.30 B commons (333 / 101)
    // 3.30 C commons (333 / 101)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC,
            AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC,
            AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC,
            AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC, AABBBCCCC,
            AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC,
            AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC,
            AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC,
            AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC, AABBBBCCC,
            AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC,
            AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC,
            AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC,
            AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC,
            AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC, AAABBBCCC,
            AAABBBCCC
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );

    private final RarityConfiguration dfcRuns = new RarityConfiguration(D1);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(dfcRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
