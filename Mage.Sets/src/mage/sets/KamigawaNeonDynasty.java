package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class KamigawaNeonDynasty extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Armguard Familiar", "Bronzeplate Boar", "Cloudsteel Kirin", "Leech Gauntlet", "Lion Sash", "Lizard Blades", "Simian Sling", "The Reality Chip");
    private static final KamigawaNeonDynasty instance = new KamigawaNeonDynasty();

    public static KamigawaNeonDynasty getInstance() {
        return instance;
    }

    private KamigawaNeonDynasty() {
        super("Kamigawa: Neon Dynasty", "NEO", ExpansionSet.buildDate(2022, 2, 18), SetType.EXPANSION);
        this.blockName = "Kamigawa: Neon Dynasty";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterDoubleFaced = 1; // temporary test fix

        cards.add(new SetCardInfo("Akki Ember-Keeper", 130, Rarity.COMMON, mage.cards.a.AkkiEmberKeeper.class));
        cards.add(new SetCardInfo("Akki War Paint", 132, Rarity.COMMON, mage.cards.a.AkkiWarPaint.class));
        cards.add(new SetCardInfo("Ancestral Katana", 1, Rarity.COMMON, mage.cards.a.AncestralKatana.class));
        cards.add(new SetCardInfo("Anchor to Reality", 45, Rarity.UNCOMMON, mage.cards.a.AnchorToReality.class));
        cards.add(new SetCardInfo("Ao, the Dawn Sky", 2, Rarity.MYTHIC, mage.cards.a.AoTheDawnSky.class));
        cards.add(new SetCardInfo("Armguard Familiar", 46, Rarity.COMMON, mage.cards.a.ArmguardFamiliar.class));
        cards.add(new SetCardInfo("Asari Captain", 215, Rarity.UNCOMMON, mage.cards.a.AsariCaptain.class));
        cards.add(new SetCardInfo("Assassin's Ink", 87, Rarity.UNCOMMON, mage.cards.a.AssassinsInk.class));
        cards.add(new SetCardInfo("Atsushi, the Blazing Sky", 134, Rarity.MYTHIC, mage.cards.a.AtsushiTheBlazingSky.class));
        cards.add(new SetCardInfo("Awakened Awareness", 47, Rarity.UNCOMMON, mage.cards.a.AwakenedAwareness.class));
        cards.add(new SetCardInfo("Bamboo Grove Archer", 173, Rarity.COMMON, mage.cards.b.BambooGroveArcher.class));
        cards.add(new SetCardInfo("Banishing Slash", 3, Rarity.UNCOMMON, mage.cards.b.BanishingSlash.class));
        cards.add(new SetCardInfo("Befriending the Moths", 4, Rarity.COMMON, mage.cards.b.BefriendingTheMoths.class));
        cards.add(new SetCardInfo("Biting-Palm Ninja", 88, Rarity.RARE, mage.cards.b.BitingPalmNinja.class));
        cards.add(new SetCardInfo("Boseiju Reaches Skyward", 177, Rarity.UNCOMMON, mage.cards.b.BoseijuReachesSkyward.class));
        cards.add(new SetCardInfo("Branch of Boseiju", 177, Rarity.UNCOMMON, mage.cards.b.BranchOfBoseiju.class));
        cards.add(new SetCardInfo("Brilliant Restoration", 7, Rarity.RARE, mage.cards.b.BrilliantRestoration.class));
        cards.add(new SetCardInfo("Bronzeplate Boar", 135, Rarity.UNCOMMON, mage.cards.b.BronzeplateBoar.class));
        cards.add(new SetCardInfo("Circuit Mender", 242, Rarity.UNCOMMON, mage.cards.c.CircuitMender.class));
        cards.add(new SetCardInfo("Coiling Stalker", 179, Rarity.COMMON, mage.cards.c.CoilingStalker.class));
        cards.add(new SetCardInfo("Covert Technician", 49, Rarity.UNCOMMON, mage.cards.c.CovertTechnician.class));
        cards.add(new SetCardInfo("Dokuchi Shadow-Walker", 94, Rarity.COMMON, mage.cards.d.DokuchiShadowWalker.class));
        cards.add(new SetCardInfo("Eiganjo Uprising", 217, Rarity.RARE, mage.cards.e.EiganjoUprising.class));
        cards.add(new SetCardInfo("Enormous Energy Blade", 96, Rarity.UNCOMMON, mage.cards.e.EnormousEnergyBlade.class));
        cards.add(new SetCardInfo("Enthusiastic Mechanaut", 218, Rarity.UNCOMMON, mage.cards.e.EnthusiasticMechanaut.class));
        cards.add(new SetCardInfo("Era of Enlightenment", 11, Rarity.COMMON, mage.cards.e.EraOfEnlightenment.class));
        cards.add(new SetCardInfo("Essence Capture", 52, Rarity.UNCOMMON, mage.cards.e.EssenceCapture.class));
        cards.add(new SetCardInfo("Fang of Shigeki", 183, Rarity.COMMON, mage.cards.f.FangOfShigeki.class));
        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Futurist Operative", 53, Rarity.UNCOMMON, mage.cards.f.FuturistOperative.class));
        cards.add(new SetCardInfo("Generous Visitor", 185, Rarity.UNCOMMON, mage.cards.g.GenerousVisitor.class));
        cards.add(new SetCardInfo("Geothermal Kami", 186, Rarity.COMMON, mage.cards.g.GeothermalKami.class));
        cards.add(new SetCardInfo("Gloomshrieker", 219, Rarity.UNCOMMON, mage.cards.g.Gloomshrieker.class));
        cards.add(new SetCardInfo("Go-Shintai of Ancient Wars", 144, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfAncientWars.class));
        cards.add(new SetCardInfo("Go-Shintai of Shared Purpose", 14, Rarity.UNCOMMON, mage.cards.g.GoShintaiOfSharedPurpose.class));
        cards.add(new SetCardInfo("Goro-Goro, Disciple of Ryusei", 145, Rarity.RARE, mage.cards.g.GoroGoroDiscipleOfRyusei.class));
        cards.add(new SetCardInfo("Greater Tanuki", 189, Rarity.COMMON, mage.cards.g.GreaterTanuki.class));
        cards.add(new SetCardInfo("Guardians of Oboro", 56, Rarity.COMMON, mage.cards.g.GuardiansOfOboro.class));
        cards.add(new SetCardInfo("Hand of Enlightenment", 11, Rarity.COMMON, mage.cards.h.HandOfEnlightenment.class));
        cards.add(new SetCardInfo("Heiko Yamazaki, the General", 146, Rarity.UNCOMMON, mage.cards.h.HeikoYamazakiTheGeneral.class));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 99, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class));
        cards.add(new SetCardInfo("High-Speed Hoverbike", 247, Rarity.UNCOMMON, mage.cards.h.HighSpeedHoverbike.class));
        cards.add(new SetCardInfo("Hotshot Mechanic", 16, Rarity.UNCOMMON, mage.cards.h.HotshotMechanic.class));
        cards.add(new SetCardInfo("Imperial Moth", 4, Rarity.COMMON, mage.cards.i.ImperialMoth.class));
        cards.add(new SetCardInfo("Imperial Subduer", 19, Rarity.COMMON, mage.cards.i.ImperialSubduer.class));
        cards.add(new SetCardInfo("Inkrise Infiltrator", 100, Rarity.COMMON, mage.cards.i.InkriseInfiltrator.class));
        cards.add(new SetCardInfo("Island", 285, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jukai Naturalist", 225, Rarity.UNCOMMON, mage.cards.j.JukaiNaturalist.class));
        cards.add(new SetCardInfo("Junji, the Midnight Sky", 102, Rarity.MYTHIC, mage.cards.j.JunjiTheMidnightSky.class));
        cards.add(new SetCardInfo("Kaito Shizuki", 226, Rarity.MYTHIC, mage.cards.k.KaitoShizuki.class));
        cards.add(new SetCardInfo("Kappa Tech-Wrecker", 198, Rarity.UNCOMMON, mage.cards.k.KappaTechWrecker.class));
        cards.add(new SetCardInfo("Kodama of the West Tree", 199, Rarity.MYTHIC, mage.cards.k.KodamaOfTheWestTree.class));
        cards.add(new SetCardInfo("Kyodai, Soul of Kamigawa", 23, Rarity.RARE, mage.cards.k.KyodaiSoulOfKamigawa.class));
        cards.add(new SetCardInfo("Leech Gauntlet", 106, Rarity.UNCOMMON, mage.cards.l.LeechGauntlet.class));
        cards.add(new SetCardInfo("Life of Toshiro Umezawa", 108, Rarity.UNCOMMON, mage.cards.l.LifeOfToshiroUmezawa.class));
        cards.add(new SetCardInfo("Light the Way", 24, Rarity.COMMON, mage.cards.l.LightTheWay.class));
        cards.add(new SetCardInfo("Lion Sash", 26, Rarity.RARE, mage.cards.l.LionSash.class));
        cards.add(new SetCardInfo("Lizard Blades", 153, Rarity.RARE, mage.cards.l.LizardBlades.class));
        cards.add(new SetCardInfo("Malicious Malfunction", 110, Rarity.UNCOMMON, mage.cards.m.MaliciousMalfunction.class));
        cards.add(new SetCardInfo("Memory of Toshiro", 108, Rarity.UNCOMMON, mage.cards.m.MemoryOfToshiro.class));
        cards.add(new SetCardInfo("Michiko's Reign of Truth", 29, Rarity.UNCOMMON, mage.cards.m.MichikosReignOfTruth.class));
        cards.add(new SetCardInfo("Moon-Circuit Hacker", 67, Rarity.COMMON, mage.cards.m.MoonCircuitHacker.class));
        cards.add(new SetCardInfo("Mountain", 289, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nameless Conqueror", 162, Rarity.COMMON, mage.cards.n.NamelessConqueror.class));
        cards.add(new SetCardInfo("Naomi, Pillar of Order", 229, Rarity.UNCOMMON, mage.cards.n.NaomiPillarOfOrder.class));
        cards.add(new SetCardInfo("Network Disruptor", 71, Rarity.COMMON, mage.cards.n.NetworkDisruptor.class));
        cards.add(new SetCardInfo("Nezumi Bladeblesser", 115, Rarity.COMMON, mage.cards.n.NezumiBladeblesser.class));
        cards.add(new SetCardInfo("Nezumi Prowler", 116, Rarity.UNCOMMON, mage.cards.n.NezumiProwler.class));
        cards.add(new SetCardInfo("Norika Yamazaki, the Poet", 31, Rarity.UNCOMMON, mage.cards.n.NorikaYamazakiThePoet.class));
        cards.add(new SetCardInfo("Oni-Cult Anvil", 230, Rarity.UNCOMMON, mage.cards.o.OniCultAnvil.class));
        cards.add(new SetCardInfo("Plains", 283, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portrait of Michiko", 29, Rarity.UNCOMMON, mage.cards.p.PortraitOfMichiko.class));
        cards.add(new SetCardInfo("Prodigy's Prototype", 231, Rarity.UNCOMMON, mage.cards.p.ProdigysPrototype.class));
        cards.add(new SetCardInfo("Raiyuu, Storm's Edge", 232, Rarity.RARE, mage.cards.r.RaiyuuStormsEdge.class));
        cards.add(new SetCardInfo("Reckoner Bankbuster", 255, Rarity.RARE, mage.cards.r.ReckonerBankbuster.class));
        cards.add(new SetCardInfo("Satoru Umezawa", 234, Rarity.RARE, mage.cards.s.SatoruUmezawa.class));
        cards.add(new SetCardInfo("Satsuki, the Living Lore", 235, Rarity.RARE, mage.cards.s.SatsukiTheLivingLore.class));
        cards.add(new SetCardInfo("Seven-Tail Mentor", 36, Rarity.COMMON, mage.cards.s.SevenTailMentor.class));
        cards.add(new SetCardInfo("Siba Trespassers", 77, Rarity.COMMON, mage.cards.s.SibaTrespassers.class));
        cards.add(new SetCardInfo("Silver-Fur Master", 236, Rarity.UNCOMMON, mage.cards.s.SilverFurMaster.class));
        cards.add(new SetCardInfo("Sokenzan Smelter", 164, Rarity.UNCOMMON, mage.cards.s.SokenzanSmelter.class));
        cards.add(new SetCardInfo("Sokenzan, Crucible of Defiance", 276, Rarity.RARE, mage.cards.s.SokenzanCrucibleOfDefiance.class));
        cards.add(new SetCardInfo("Spell Pierce", 80, Rarity.COMMON, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Spirited Companion", 38, Rarity.COMMON, mage.cards.s.SpiritedCompanion.class));
        cards.add(new SetCardInfo("Sunblade Samurai", 39, Rarity.COMMON, mage.cards.s.SunbladeSamurai.class));
        cards.add(new SetCardInfo("Surgehacker Mech", 260, Rarity.RARE, mage.cards.s.SurgehackerMech.class));
        cards.add(new SetCardInfo("Swamp", 287, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tempered in Solitude", 165, Rarity.UNCOMMON, mage.cards.t.TemperedInSolitude.class));
        cards.add(new SetCardInfo("The Modern Age", 66, Rarity.COMMON, mage.cards.t.TheModernAge.class));
        cards.add(new SetCardInfo("The Shattered States Era", 162, Rarity.COMMON, mage.cards.t.TheShatteredStatesEra.class));
        cards.add(new SetCardInfo("The Wandering Emperor", 42, Rarity.MYTHIC, mage.cards.t.TheWanderingEmperor.class));
        cards.add(new SetCardInfo("Thousand-Faced Shadow", 86, Rarity.RARE, mage.cards.t.ThousandFacedShadow.class));
        cards.add(new SetCardInfo("Twinshot Sniper", 168, Rarity.UNCOMMON, mage.cards.t.TwinshotSniper.class));
        cards.add(new SetCardInfo("Unstoppable Ogre", 169, Rarity.COMMON, mage.cards.u.UnstoppableOgre.class));
        cards.add(new SetCardInfo("Upriser Renegade", 170, Rarity.UNCOMMON, mage.cards.u.UpriserRenegade.class));
        cards.add(new SetCardInfo("Vector Glider", 66, Rarity.COMMON, mage.cards.v.VectorGlider.class));
        cards.add(new SetCardInfo("You Are Already Dead", 129, Rarity.COMMON, mage.cards.y.YouAreAlreadyDead.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
