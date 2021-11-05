
package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
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
 *
 * @author North
 */
public final class DarkAscension extends ExpansionSet {

    private static final DarkAscension instance = new DarkAscension();

    public static DarkAscension getInstance() {
        return instance;
    }

    private DarkAscension() {
        super("Dark Ascension", "DKA", ExpansionSet.buildDate(2012, 1, 3), SetType.EXPANSION);
        this.blockName = "Innistrad";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        this.parentSet = Innistrad.getInstance();
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Afflicted Deserter", 81, Rarity.UNCOMMON, mage.cards.a.AfflictedDeserter.class));
        cards.add(new SetCardInfo("Alpha Brawl", 82, Rarity.RARE, mage.cards.a.AlphaBrawl.class));
        cards.add(new SetCardInfo("Altar of the Lost", 144, Rarity.UNCOMMON, mage.cards.a.AltarOfTheLost.class));
        cards.add(new SetCardInfo("Archangel's Light", 1, Rarity.MYTHIC, mage.cards.a.ArchangelsLight.class));
        cards.add(new SetCardInfo("Archdemon of Greed", 71, Rarity.RARE, mage.cards.a.ArchdemonOfGreed.class));
        cards.add(new SetCardInfo("Artful Dodge", 27, Rarity.COMMON, mage.cards.a.ArtfulDodge.class));
        cards.add(new SetCardInfo("Avacyn's Collar", 145, Rarity.UNCOMMON, mage.cards.a.AvacynsCollar.class));
        cards.add(new SetCardInfo("Bar the Door", 2, Rarity.COMMON, mage.cards.b.BarTheDoor.class));
        cards.add(new SetCardInfo("Beguiler of Wills", 28, Rarity.MYTHIC, mage.cards.b.BeguilerOfWills.class));
        cards.add(new SetCardInfo("Black Cat", 54, Rarity.COMMON, mage.cards.b.BlackCat.class));
        cards.add(new SetCardInfo("Blood Feud", 83, Rarity.UNCOMMON, mage.cards.b.BloodFeud.class));
        cards.add(new SetCardInfo("Bone to Ash", 29, Rarity.COMMON, mage.cards.b.BoneToAsh.class));
        cards.add(new SetCardInfo("Break of Day", 3, Rarity.COMMON, mage.cards.b.BreakOfDay.class));
        cards.add(new SetCardInfo("Briarpack Alpha", 108, Rarity.UNCOMMON, mage.cards.b.BriarpackAlpha.class));
        cards.add(new SetCardInfo("Burden of Guilt", 4, Rarity.COMMON, mage.cards.b.BurdenOfGuilt.class));
        cards.add(new SetCardInfo("Burning Oil", 84, Rarity.UNCOMMON, mage.cards.b.BurningOil.class));
        cards.add(new SetCardInfo("Call to the Kindred", 30, Rarity.RARE, mage.cards.c.CallToTheKindred.class));
        cards.add(new SetCardInfo("Chalice of Death", 146, Rarity.UNCOMMON, mage.cards.c.ChaliceOfDeath.class));
        cards.add(new SetCardInfo("Chalice of Life", 146, Rarity.UNCOMMON, mage.cards.c.ChaliceOfLife.class));
        cards.add(new SetCardInfo("Chant of the Skifsang", 31, Rarity.COMMON, mage.cards.c.ChantOfTheSkifsang.class));
        cards.add(new SetCardInfo("Chill of Foreboding", 32, Rarity.UNCOMMON, mage.cards.c.ChillOfForeboding.class));
        cards.add(new SetCardInfo("Chosen of Markov", 55, Rarity.COMMON, mage.cards.c.ChosenOfMarkov.class));
        cards.add(new SetCardInfo("Clinging Mists", 109, Rarity.COMMON, mage.cards.c.ClingingMists.class));
        cards.add(new SetCardInfo("Counterlash", 33, Rarity.RARE, mage.cards.c.Counterlash.class));
        cards.add(new SetCardInfo("Crushing Vines", 110, Rarity.COMMON, mage.cards.c.CrushingVines.class));
        cards.add(new SetCardInfo("Curse of Bloodletting", 85, Rarity.RARE, mage.cards.c.CurseOfBloodletting.class));
        cards.add(new SetCardInfo("Curse of Echoes", 34, Rarity.RARE, mage.cards.c.CurseOfEchoes.class));
        cards.add(new SetCardInfo("Curse of Exhaustion", 5, Rarity.UNCOMMON, mage.cards.c.CurseOfExhaustion.class));
        cards.add(new SetCardInfo("Curse of Misfortunes", 56, Rarity.RARE, mage.cards.c.CurseOfMisfortunes.class));
        cards.add(new SetCardInfo("Curse of Thirst", 57, Rarity.UNCOMMON, mage.cards.c.CurseOfThirst.class));
        cards.add(new SetCardInfo("Dawntreader Elk", 111, Rarity.COMMON, mage.cards.d.DawntreaderElk.class));
        cards.add(new SetCardInfo("Deadly Allure", 58, Rarity.UNCOMMON, mage.cards.d.DeadlyAllure.class));
        cards.add(new SetCardInfo("Death's Caress", 59, Rarity.COMMON, mage.cards.d.DeathsCaress.class));
        cards.add(new SetCardInfo("Deranged Outcast", 112, Rarity.RARE, mage.cards.d.DerangedOutcast.class));
        cards.add(new SetCardInfo("Diregraf Captain", 135, Rarity.UNCOMMON, mage.cards.d.DiregrafCaptain.class));
        cards.add(new SetCardInfo("Divination", 35, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Drogskol Captain", 136, Rarity.UNCOMMON, mage.cards.d.DrogskolCaptain.class));
        cards.add(new SetCardInfo("Drogskol Reaver", 137, Rarity.MYTHIC, mage.cards.d.DrogskolReaver.class));
        cards.add(new SetCardInfo("Dungeon Geists", 36, Rarity.RARE, mage.cards.d.DungeonGeists.class));
        cards.add(new SetCardInfo("Elbrus, the Binding Blade", 147, Rarity.MYTHIC, mage.cards.e.ElbrusTheBindingBlade.class));
        cards.add(new SetCardInfo("Elgaud Inquisitor", 6, Rarity.COMMON, mage.cards.e.ElgaudInquisitor.class));
        cards.add(new SetCardInfo("Erdwal Ripper", 86, Rarity.COMMON, mage.cards.e.ErdwalRipper.class));
        cards.add(new SetCardInfo("Evolving Wilds", 155, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Executioner's Hood", 148, Rarity.COMMON, mage.cards.e.ExecutionersHood.class));
        cards.add(new SetCardInfo("Faithless Looting", 87, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Faith's Shield", 7, Rarity.UNCOMMON, mage.cards.f.FaithsShield.class));
        cards.add(new SetCardInfo("Falkenrath Aristocrat", 138, Rarity.MYTHIC, mage.cards.f.FalkenrathAristocrat.class));
        cards.add(new SetCardInfo("Falkenrath Torturer", 60, Rarity.COMMON, mage.cards.f.FalkenrathTorturer.class));
        cards.add(new SetCardInfo("Farbog Boneflinger", 61, Rarity.UNCOMMON, mage.cards.f.FarbogBoneflinger.class));
        cards.add(new SetCardInfo("Favor of the Woods", 113, Rarity.COMMON, mage.cards.f.FavorOfTheWoods.class));
        cards.add(new SetCardInfo("Feed the Pack", 114, Rarity.RARE, mage.cards.f.FeedThePack.class));
        cards.add(new SetCardInfo("Fiend of the Shadows", 62, Rarity.RARE, mage.cards.f.FiendOfTheShadows.class));
        cards.add(new SetCardInfo("Fires of Undeath", 88, Rarity.COMMON, mage.cards.f.FiresOfUndeath.class));
        cards.add(new SetCardInfo("Flayer of the Hatebound", 89, Rarity.RARE, mage.cards.f.FlayerOfTheHatebound.class));
        cards.add(new SetCardInfo("Fling", 90, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Forge Devil", 91, Rarity.COMMON, mage.cards.f.ForgeDevil.class));
        cards.add(new SetCardInfo("Gather the Townsfolk", 8, Rarity.COMMON, mage.cards.g.GatherTheTownsfolk.class));
        cards.add(new SetCardInfo("Gavony Ironwright", 9, Rarity.UNCOMMON, mage.cards.g.GavonyIronwright.class));
        cards.add(new SetCardInfo("Geralf's Messenger", 63, Rarity.RARE, mage.cards.g.GeralfsMessenger.class));
        cards.add(new SetCardInfo("Geralf's Mindcrusher", 37, Rarity.RARE, mage.cards.g.GeralfsMindcrusher.class));
        cards.add(new SetCardInfo("Ghastly Haunting", 50, Rarity.UNCOMMON, mage.cards.g.GhastlyHaunting.class));
        cards.add(new SetCardInfo("Ghoultree", 115, Rarity.RARE, mage.cards.g.Ghoultree.class));
        cards.add(new SetCardInfo("Grafdigger's Cage", 149, Rarity.RARE, mage.cards.g.GrafdiggersCage.class));
        cards.add(new SetCardInfo("Gravecrawler", 64, Rarity.RARE, mage.cards.g.Gravecrawler.class));
        cards.add(new SetCardInfo("Gravepurge", 65, Rarity.COMMON, mage.cards.g.Gravepurge.class));
        cards.add(new SetCardInfo("Gravetiller Wurm", 116, Rarity.UNCOMMON, mage.cards.g.GravetillerWurm.class));
        cards.add(new SetCardInfo("Grim Backwoods", 156, Rarity.RARE, mage.cards.g.GrimBackwoods.class));
        cards.add(new SetCardInfo("Grim Flowering", 117, Rarity.UNCOMMON, mage.cards.g.GrimFlowering.class));
        cards.add(new SetCardInfo("Griptide", 38, Rarity.COMMON, mage.cards.g.Griptide.class));
        cards.add(new SetCardInfo("Gruesome Discovery", 66, Rarity.COMMON, mage.cards.g.GruesomeDiscovery.class));
        cards.add(new SetCardInfo("Harrowing Journey", 67, Rarity.UNCOMMON, mage.cards.h.HarrowingJourney.class));
        cards.add(new SetCardInfo("Haunted Fengraf", 157, Rarity.COMMON, mage.cards.h.HauntedFengraf.class));
        cards.add(new SetCardInfo("Havengul Lich", 139, Rarity.MYTHIC, mage.cards.h.HavengulLich.class));
        cards.add(new SetCardInfo("Havengul Runebinder", 39, Rarity.RARE, mage.cards.h.HavengulRunebinder.class));
        cards.add(new SetCardInfo("Headless Skaab", 40, Rarity.COMMON, mage.cards.h.HeadlessSkaab.class));
        cards.add(new SetCardInfo("Heavy Mattock", 150, Rarity.COMMON, mage.cards.h.HeavyMattock.class));
        cards.add(new SetCardInfo("Heckling Fiends", 92, Rarity.UNCOMMON, mage.cards.h.HecklingFiends.class));
        cards.add(new SetCardInfo("Hellrider", 93, Rarity.RARE, mage.cards.h.Hellrider.class));
        cards.add(new SetCardInfo("Helvault", 151, Rarity.MYTHIC, mage.cards.h.Helvault.class));
        cards.add(new SetCardInfo("Highborn Ghoul", 68, Rarity.COMMON, mage.cards.h.HighbornGhoul.class));
        cards.add(new SetCardInfo("Hinterland Hermit", 94, Rarity.COMMON, mage.cards.h.HinterlandHermit.class));
        cards.add(new SetCardInfo("Hinterland Scourge", 94, Rarity.COMMON, mage.cards.h.HinterlandScourge.class));
        cards.add(new SetCardInfo("Hollowhenge Beast", 118, Rarity.COMMON, mage.cards.h.HollowhengeBeast.class));
        cards.add(new SetCardInfo("Hollowhenge Spirit", 10, Rarity.UNCOMMON, mage.cards.h.HollowhengeSpirit.class));
        cards.add(new SetCardInfo("Hunger of the Howlpack", 119, Rarity.COMMON, mage.cards.h.HungerOfTheHowlpack.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", 140, Rarity.MYTHIC, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Immerwolf", 141, Rarity.UNCOMMON, mage.cards.i.Immerwolf.class));
        cards.add(new SetCardInfo("Increasing Ambition", 69, Rarity.RARE, mage.cards.i.IncreasingAmbition.class));
        cards.add(new SetCardInfo("Increasing Confusion", 41, Rarity.RARE, mage.cards.i.IncreasingConfusion.class));
        cards.add(new SetCardInfo("Increasing Devotion", 11, Rarity.RARE, mage.cards.i.IncreasingDevotion.class));
        cards.add(new SetCardInfo("Increasing Savagery", 120, Rarity.RARE, mage.cards.i.IncreasingSavagery.class));
        cards.add(new SetCardInfo("Increasing Vengeance", 95, Rarity.RARE, mage.cards.i.IncreasingVengeance.class));
        cards.add(new SetCardInfo("Jar of Eyeballs", 152, Rarity.RARE, mage.cards.j.JarOfEyeballs.class));
        cards.add(new SetCardInfo("Kessig Recluse", 121, Rarity.COMMON, mage.cards.k.KessigRecluse.class));
        cards.add(new SetCardInfo("Krallenhorde Killer", 133, Rarity.RARE, mage.cards.k.KrallenhordeKiller.class));
        cards.add(new SetCardInfo("Lambholt Elder", 122, Rarity.UNCOMMON, mage.cards.l.LambholtElder.class));
        cards.add(new SetCardInfo("Lingering Souls", 12, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Lost in the Woods", 123, Rarity.RARE, mage.cards.l.LostInTheWoods.class));
        cards.add(new SetCardInfo("Loyal Cathar", 13, Rarity.COMMON, mage.cards.l.LoyalCathar.class));
        cards.add(new SetCardInfo("Markov Blademaster", 96, Rarity.RARE, mage.cards.m.MarkovBlademaster.class));
        cards.add(new SetCardInfo("Markov's Servant", 55, Rarity.COMMON, mage.cards.m.MarkovsServant.class));
        cards.add(new SetCardInfo("Markov Warlord", 97, Rarity.UNCOMMON, mage.cards.m.MarkovWarlord.class));
        cards.add(new SetCardInfo("Midnight Guard", 14, Rarity.COMMON, mage.cards.m.MidnightGuard.class));
        cards.add(new SetCardInfo("Mikaeus, the Unhallowed", 70, Rarity.MYTHIC, mage.cards.m.MikaeusTheUnhallowed.class));
        cards.add(new SetCardInfo("Mondronen Shaman", 98, Rarity.RARE, mage.cards.m.MondronenShaman.class));
        cards.add(new SetCardInfo("Moonscarred Werewolf", 125, Rarity.COMMON, mage.cards.m.MoonscarredWerewolf.class));
        cards.add(new SetCardInfo("Moonveil Dragon", 99, Rarity.MYTHIC, mage.cards.m.MoonveilDragon.class));
        cards.add(new SetCardInfo("Mystic Retrieval", 42, Rarity.UNCOMMON, mage.cards.m.MysticRetrieval.class));
        cards.add(new SetCardInfo("Nearheath Stalker", 100, Rarity.COMMON, mage.cards.n.NearheathStalker.class));
        cards.add(new SetCardInfo("Nephalia Seakite", 43, Rarity.COMMON, mage.cards.n.NephaliaSeakite.class));
        cards.add(new SetCardInfo("Niblis of the Breath", 44, Rarity.UNCOMMON, mage.cards.n.NiblisOfTheBreath.class));
        cards.add(new SetCardInfo("Niblis of the Mist", 15, Rarity.COMMON, mage.cards.n.NiblisOfTheMist.class));
        cards.add(new SetCardInfo("Niblis of the Urn", 16, Rarity.UNCOMMON, mage.cards.n.NiblisOfTheUrn.class));
        cards.add(new SetCardInfo("Predator Ooze", 124, Rarity.RARE, mage.cards.p.PredatorOoze.class));
        cards.add(new SetCardInfo("Pyreheart Wolf", 101, Rarity.UNCOMMON, mage.cards.p.PyreheartWolf.class));
        cards.add(new SetCardInfo("Ravager of the Fells", 140, Rarity.MYTHIC, mage.cards.r.RavagerOfTheFells.class));
        cards.add(new SetCardInfo("Ravenous Demon", 71, Rarity.RARE, mage.cards.r.RavenousDemon.class));
        cards.add(new SetCardInfo("Ray of Revelation", 17, Rarity.COMMON, mage.cards.r.RayOfRevelation.class));
        cards.add(new SetCardInfo("Reap the Seagraf", 72, Rarity.COMMON, mage.cards.r.ReapTheSeagraf.class));
        cards.add(new SetCardInfo("Relentless Skaabs", 45, Rarity.UNCOMMON, mage.cards.r.RelentlessSkaabs.class));
        cards.add(new SetCardInfo("Requiem Angel", 18, Rarity.RARE, mage.cards.r.RequiemAngel.class));
        cards.add(new SetCardInfo("Russet Wolves", 102, Rarity.COMMON, mage.cards.r.RussetWolves.class));
        cards.add(new SetCardInfo("Sanctuary Cat", 19, Rarity.COMMON, mage.cards.s.SanctuaryCat.class));
        cards.add(new SetCardInfo("Saving Grasp", 46, Rarity.COMMON, mage.cards.s.SavingGrasp.class));
        cards.add(new SetCardInfo("Scorch the Fields", 103, Rarity.COMMON, mage.cards.s.ScorchTheFields.class));
        cards.add(new SetCardInfo("Scorned Villager", 125, Rarity.COMMON, mage.cards.s.ScornedVillager.class));
        cards.add(new SetCardInfo("Screeching Skaab", 47, Rarity.COMMON, mage.cards.s.ScreechingSkaab.class));
        cards.add(new SetCardInfo("Seance", 20, Rarity.RARE, mage.cards.s.Seance.class));
        cards.add(new SetCardInfo("Secrets of the Dead", 48, Rarity.UNCOMMON, mage.cards.s.SecretsOfTheDead.class));
        cards.add(new SetCardInfo("Shattered Perception", 104, Rarity.UNCOMMON, mage.cards.s.ShatteredPerception.class));
        cards.add(new SetCardInfo("Shriekgeist", 49, Rarity.COMMON, mage.cards.s.Shriekgeist.class));
        cards.add(new SetCardInfo("Sightless Ghoul", 73, Rarity.COMMON, mage.cards.s.SightlessGhoul.class));
        cards.add(new SetCardInfo("Silverclaw Griffin", 21, Rarity.COMMON, mage.cards.s.SilverclawGriffin.class));
        cards.add(new SetCardInfo("Silverpelt Werewolf", 122, Rarity.UNCOMMON, mage.cards.s.SilverpeltWerewolf.class));
        cards.add(new SetCardInfo("Skillful Lunge", 22, Rarity.COMMON, mage.cards.s.SkillfulLunge.class));
        cards.add(new SetCardInfo("Skirsdag Flayer", 74, Rarity.UNCOMMON, mage.cards.s.SkirsdagFlayer.class));
        cards.add(new SetCardInfo("Somberwald Dryad", 126, Rarity.COMMON, mage.cards.s.SomberwaldDryad.class));
        cards.add(new SetCardInfo("Sorin, Lord of Innistrad", 142, Rarity.MYTHIC, mage.cards.s.SorinLordOfInnistrad.class));
        cards.add(new SetCardInfo("Soul Seizer", 50, Rarity.UNCOMMON, mage.cards.s.SoulSeizer.class));
        cards.add(new SetCardInfo("Spiteful Shadows", 75, Rarity.COMMON, mage.cards.s.SpitefulShadows.class));
        cards.add(new SetCardInfo("Stormbound Geist", 51, Rarity.COMMON, mage.cards.s.StormboundGeist.class));
        cards.add(new SetCardInfo("Strangleroot Geist", 127, Rarity.UNCOMMON, mage.cards.s.StranglerootGeist.class));
        cards.add(new SetCardInfo("Stromkirk Captain", 143, Rarity.UNCOMMON, mage.cards.s.StromkirkCaptain.class));
        cards.add(new SetCardInfo("Sudden Disappearance", 23, Rarity.RARE, mage.cards.s.SuddenDisappearance.class));
        cards.add(new SetCardInfo("Talons of Falkenrath", 105, Rarity.COMMON, mage.cards.t.TalonsOfFalkenrath.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 24, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Thought Scour", 52, Rarity.COMMON, mage.cards.t.ThoughtScour.class));
        cards.add(new SetCardInfo("Thraben Doomsayer", 25, Rarity.RARE, mage.cards.t.ThrabenDoomsayer.class));
        cards.add(new SetCardInfo("Thraben Heretic", 26, Rarity.UNCOMMON, mage.cards.t.ThrabenHeretic.class));
        cards.add(new SetCardInfo("Torch Fiend", 106, Rarity.COMMON, mage.cards.t.TorchFiend.class));
        cards.add(new SetCardInfo("Tovolar's Magehunter", 98, Rarity.RARE, mage.cards.t.TovolarsMagehunter.class));
        cards.add(new SetCardInfo("Tower Geist", 53, Rarity.UNCOMMON, mage.cards.t.TowerGeist.class));
        cards.add(new SetCardInfo("Tracker's Instincts", 128, Rarity.UNCOMMON, mage.cards.t.TrackersInstincts.class));
        cards.add(new SetCardInfo("Tragic Slip", 76, Rarity.COMMON, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Ulvenwald Bear", 129, Rarity.COMMON, mage.cards.u.UlvenwaldBear.class));
        cards.add(new SetCardInfo("Undying Evil", 77, Rarity.COMMON, mage.cards.u.UndyingEvil.class));
        cards.add(new SetCardInfo("Unhallowed Cathar", 13, Rarity.COMMON, mage.cards.u.UnhallowedCathar.class));
        cards.add(new SetCardInfo("Vault of the Archangel", 158, Rarity.RARE, mage.cards.v.VaultOfTheArchangel.class));
        cards.add(new SetCardInfo("Vengeful Vampire", 78, Rarity.UNCOMMON, mage.cards.v.VengefulVampire.class));
        cards.add(new SetCardInfo("Village Survivors", 130, Rarity.UNCOMMON, mage.cards.v.VillageSurvivors.class));
        cards.add(new SetCardInfo("Vorapede", 131, Rarity.MYTHIC, mage.cards.v.Vorapede.class));
        cards.add(new SetCardInfo("Wakedancer", 79, Rarity.UNCOMMON, mage.cards.w.Wakedancer.class));
        cards.add(new SetCardInfo("Warden of the Wall", 153, Rarity.UNCOMMON, mage.cards.w.WardenOfTheWall.class));
        cards.add(new SetCardInfo("Werewolf Ransacker", 81, Rarity.UNCOMMON, mage.cards.w.WerewolfRansacker.class));
        cards.add(new SetCardInfo("Wild Hunger", 132, Rarity.COMMON, mage.cards.w.WildHunger.class));
        cards.add(new SetCardInfo("Withengar Unbound", 147, Rarity.MYTHIC, mage.cards.w.WithengarUnbound.class));
        cards.add(new SetCardInfo("Wolfbitten Captive", 133, Rarity.RARE, mage.cards.w.WolfbittenCaptive.class));
        cards.add(new SetCardInfo("Wolfhunter's Quiver", 154, Rarity.UNCOMMON, mage.cards.w.WolfhuntersQuiver.class));
        cards.add(new SetCardInfo("Wrack with Madness", 107, Rarity.COMMON, mage.cards.w.WrackWithMadness.class));
        cards.add(new SetCardInfo("Young Wolf", 134, Rarity.COMMON, mage.cards.y.YoungWolf.class));
        cards.add(new SetCardInfo("Zombie Apocalypse", 80, Rarity.RARE, mage.cards.z.ZombieApocalypse.class));
    }

    @Override
    protected void addDoubleFace(List<Card> booster) {
        Rarity rarity;
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            int rarityKey = RandomUtil.nextInt(121);
            if (rarityKey < 72) {
                rarity = Rarity.COMMON;
            } else if (rarityKey < 108) {
                rarity = Rarity.UNCOMMON;
            } else if (rarityKey < 117) {
                rarity = Rarity.RARE;
            } else {
                rarity = Rarity.MYTHIC;
            }
            List<CardInfo> doubleFacedCards = getSpecialCardsByRarity(rarity);
            addToBooster(booster, doubleFacedCards);
        }
    }

    @Override
    public BoosterCollator createCollator() {
        return new DarkAscensionCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/dka.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class DarkAscensionCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "66", "47", "100", "110", "86", "51", "21", "75", "148", "2", "52", "119", "102", "54", "49", "4", "87", "109", "148", "91", "40", "76", "110", "22", "106", "65", "43", "132", "17", "157", "76", "87", "40", "21", "49", "118", "75", "86", "54", "3", "119", "51", "47", "113", "2", "91", "59", "102", "27", "118", "43", "17", "109", "4", "59", "100", "66", "52", "132", "22", "157", "113", "106", "27", "65", "3");
    private final CardRun commonB = new CardRun(true, "103", "126", "29", "8", "73", "88", "60", "111", "19", "15", "29", "107", "35", "126", "150", "90", "73", "129", "6", "15", "68", "46", "155", "105", "72", "14", "129", "77", "38", "6", "121", "88", "134", "31", "111", "72", "103", "35", "19", "121", "90", "14", "31", "68", "105", "8", "60", "150", "134", "155", "38", "107", "46", "77");
    private final CardRun uncommonA = new CardRun(true, "5", "128", "101", "153", "45", "145", "12", "67", "78", "117", "97", "44", "153", "5", "136", "57", "128", "135", "101", "154", "53", "7", "12", "57", "116", "135", "97", "44", "58", "92", "12", "136", "145", "117", "5", "78", "116", "92", "53", "58", "145", "10", "97", "7", "116", "45", "67", "154", "58", "10", "92", "44", "117", "153", "7", "78", "128", "45", "101", "67", "136", "53", "10", "135", "154", "57");
    private final CardRun uncommonB = new CardRun(true, "83", "48", "16", "144", "127", "104", "42", "130", "48", "16", "84", "79", "141", "32", "130", "26", "127", "74", "83", "9", "42", "61", "144", "143", "108", "84", "26", "141", "127", "9", "48", "104", "74", "143", "79", "144", "108", "26", "32", "83", "104", "61", "42", "143", "16", "141", "130", "84", "32", "74", "108", "9", "79", "61");
    private final CardRun rare = new CardRun(false, "11", "18", "20", "23", "24", "25", "30", "33", "34", "36", "37", "39", "41", "56", "62", "63", "64", "69", "80", "82", "85", "89", "93", "95", "96", "112", "114", "115", "120", "123", "124", "149", "152", "156", "158", "11", "18", "20", "23", "24", "25", "30", "33", "34", "36", "37", "39", "41", "56", "62", "63", "64", "69", "80", "82", "85", "89", "93", "95", "96", "112", "114", "115", "120", "123", "124", "149", "152", "156", "158", "1", "28", "70", "99", "131", "137", "138", "139", "142", "151");
    private final CardRun doubleFaced = new CardRun(false, "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "13", "55", "94", "125", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "50", "81", "122", "146", "71", "98", "133", "71", "98", "133", "71", "98", "133", "140", "147", "140", "147");
    private final CardRun land = new CardRun(false, "ISD_250", "ISD_251", "ISD_252", "ISD_253", "ISD_254", "ISD_255", "ISD_256", "ISD_257", "ISD_258", "ISD_259", "ISD_260", "ISD_261", "ISD_262", "ISD_263", "ISD_264");

    private final BoosterStructure AAAAABBBB = new BoosterStructure(
        commonA, commonA, commonA, commonA, commonA,
        commonB, commonB, commonB, commonB
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure D1 = new BoosterStructure(doubleFaced);
    private final BoosterStructure L1 = new BoosterStructure(land);

    private final RarityConfiguration commonRuns = new RarityConfiguration(AAAAABBBB);
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.65 A uncommons (33 / 20)
    // 1.35 B uncommons (27 / 20)
    // These numbers are the same for all sets with 40 uncommons in asymmetrical A/B print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration dfcRuns = new RarityConfiguration(D1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(dfcRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
