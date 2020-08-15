package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * https://scryfall.com/sets/akr
 */
public class AmonkhetRemastered extends ExpansionSet {

  private static final AmonkhetRemastered instance = new AmonkhetRemastered();

  public static AmonkhetRemastered getInstance() {
    return instance;
  }

  private final List<CardInfo> savedSpecialLand = new ArrayList<>();

  private AmonkhetRemastered() {
    super("Amonkhet Remastered", "AKR", ExpansionSet.buildDate(2020, 8, 13), SetType.MAGIC_ARENA);
    this.hasBoosters = true;
    this.hasBasicLands = false;
    this.numBoosterLands = 1;
    this.numBoosterCommon = 10;
    this.numBoosterUncommon = 3;
    this.numBoosterRare = 1;
    this.ratioBoosterMythic = 8;
    this.ratioBoosterSpecialLand = 1;

    cards.add(new SetCardInfo("Angel of Sanctions", "W-AOS", Rarity.MYTHIC, mage.cards.a.AngelOfSanctions.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Anointed Procession", "W-AP", Rarity.RARE, mage.cards.a.AnointedProcession.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Anointer Priest", "W-APR", Rarity.COMMON, mage.cards.a.AnointerPriest.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Approach of the Second Sun", "W-AOTSS", Rarity.RARE, mage.cards.a.ApproachOfTheSecondSun.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Aven Mindcensor", "W-AC", Rarity.RARE, mage.cards.a.AvenMindcensor.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Aven of Enduring Hope", "W-AOEH", Rarity.COMMON, mage.cards.a.AvenOfEnduringHope.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Beneath the Sands", "G-BTS", Rarity.COMMON, mage.cards.b.BeneathTheSands.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Binding Mummy", "W-BM", Rarity.COMMON, mage.cards.b.BindingMummy.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Bitterbow Sharpshooters", "G-BS", Rarity.COMMON, mage.cards.b.BitterbowSharpshooters.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Cartouche of Solidarity", "W-COS", Rarity.COMMON, mage.cards.c.CartoucheOfSolidarity.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Cartouche of Strength", "G-COS", Rarity.COMMON, mage.cards.c.CartoucheOfStrength.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Cast Out", "W-CO", Rarity.UNCOMMON, mage.cards.c.CastOut.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Champion of Rhonas", "G-COR", Rarity.RARE, mage.cards.c.ChampionOfRhonas.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Collected Company", "G-CC", Rarity.RARE, mage.cards.c.CollectedCompany.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Compulsory Rest", "W-CR", Rarity.COMMON, mage.cards.c.CompulsoryRest.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Crested Sunmare", "W-CRS", Rarity.MYTHIC, mage.cards.c.CrestedSunmare.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Crocodile of the Crossing", "G-COTC", Rarity.UNCOMMON, mage.cards.c.CrocodileOfTheCrossing.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Dauntless Aven", "W-DA", Rarity.COMMON, mage.cards.d.DauntlessAven.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Defiant Greatmaw", "G-DG", Rarity.UNCOMMON, mage.cards.d.DefiantGreatmaw.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Desert's Hold", "W-DH", Rarity.UNCOMMON, mage.cards.d.DesertsHold.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Disposal Mummy", "W-DM", Rarity.COMMON, mage.cards.d.DisposalMummy.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Dissenter's Deliverance", "G-DD", Rarity.COMMON, mage.cards.d.DissentersDeliverance.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Djeru's Resolve", "W-DR", Rarity.COMMON, mage.cards.d.DjerusResolve.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Dusk // Dawn", "W-DD", Rarity.RARE, mage.cards.d.DuskDawn.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Dusk // Dawn", "W-DD", Rarity.RARE, mage.cards.d.DuskDawn.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Exemplar of Strength", "G-EOS", Rarity.UNCOMMON, mage.cards.e.ExemplarOfStrength.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Fan Bearer", "W-FB", Rarity.COMMON, mage.cards.f.FanBearer.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Feral Prowler", "G-FP", Rarity.COMMON, mage.cards.f.FeralProwler.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Forsake the Worldly", "W-FTW", Rarity.COMMON, mage.cards.f.ForsakeTheWorldly.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Gideon of the Trials", "W-GOTT", Rarity.MYTHIC, mage.cards.g.GideonOfTheTrials.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Gideon's Intervention", "W-GI", Rarity.RARE, mage.cards.g.GideonsIntervention.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Glory-Bound Initiate", "W-GBI", Rarity.RARE, mage.cards.g.GloryBoundInitiate.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Greater Sandwurm", "G-GS", Rarity.COMMON, mage.cards.g.GreaterSandwurm.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Gust Walker", "W-GW", Rarity.COMMON, mage.cards.g.GustWalker.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Haze of Pollen", "G-HOP", Rarity.COMMON, mage.cards.h.HazeOfPollen.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Hooded Brawler", "G-HB", Rarity.COMMON, mage.cards.h.HoodedBrawler.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Hope Tender", "G-HT", Rarity.UNCOMMON, mage.cards.h.HopeTender.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Hornet Queen", "G-HQ", Rarity.MYTHIC, mage.cards.h.HornetQueen.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Hour of Promise", "G-HOPR", Rarity.RARE, mage.cards.h.HourOfPromise.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Hour of Revelation", "W-HOR", Rarity.RARE, mage.cards.h.HourOfRevelation.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Impeccable Timing", "W-IT", Rarity.COMMON, mage.cards.i.ImpeccableTiming.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("In Oketra's Name", "W-ION", Rarity.COMMON, mage.cards.i.InOketrasName.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Initiate's Companion", "G-IC", Rarity.COMMON, mage.cards.i.InitiatesCompanion.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Life Goes On", "G-LGO", Rarity.COMMON, mage.cards.l.LifeGoesOn.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Majestic Myriarch", "G-MM", Rarity.MYTHIC, mage.cards.m.MajesticMyriarch.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Manglehorn", "G-MH", Rarity.UNCOMMON, mage.cards.m.Manglehorn.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Mighty Leap", "W-ML", Rarity.COMMON, mage.cards.m.MightyLeap.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Mouth // Feed", "G-MTF", Rarity.RARE, mage.cards.m.MouthFeed.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Mouth // Feed", "G-MTF", Rarity.RARE, mage.cards.m.MouthFeed.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Naga Vitalist", "G-NF", Rarity.COMMON, mage.cards.n.NagaVitalist.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Oashra Cultivator", "G-OC", Rarity.COMMON, mage.cards.o.OashraCultivator.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Oasis Ritualist", "G-ORT", Rarity.COMMON, mage.cards.o.OasisRitualist.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Oketra the True", "W-OKET", Rarity.MYTHIC, mage.cards.o.OketraTheTrue.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Oketra's Attendant", "W-OA", Rarity.UNCOMMON, mage.cards.o.OketrasAttendant.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Oketra's Avenger", "W-OAV", Rarity.COMMON, mage.cards.o.OketrasAvenger.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Ornery Kudu", "G-OK", Rarity.COMMON, mage.cards.o.OrneryKudu.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Overwhelming Splendor", "W-OVS", Rarity.MYTHIC, mage.cards.o.OverwhelmingSplendor.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Pouncing Cheetah", "G-PC", Rarity.COMMON, mage.cards.p.PouncingCheetah.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Pride Sovereign", "G-PS", Rarity.RARE, mage.cards.p.PrideSovereign.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Protection of the Hekma", "W-POTH", Rarity.UNCOMMON, mage.cards.p.ProtectionOfTheHekma.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Prowling Serpopard", "G-PRS", Rarity.RARE, mage.cards.p.ProwlingSerpopard.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Quarry Hauler", "G-HQLR", Rarity.COMMON, mage.cards.q.QuarryHauler.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Ramunap Excavator", "G-RE", Rarity.RARE, mage.cards.r.RamunapExcavator.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Renewed Faith", "W-RF", Rarity.UNCOMMON, mage.cards.r.RenewedFaith.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Resilient Khenra", "G-RK", Rarity.RARE, mage.cards.r.ResilientKhenra.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Rest in Peace", "W-RIP", Rarity.RARE, mage.cards.r.RestInPeace.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Rhonas the Indomitable", "G-RTI", Rarity.MYTHIC, mage.cards.r.RhonasTheIndomitable.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Rhonas's Stalwart", "G-RST", Rarity.COMMON, mage.cards.r.RhonassStalwart.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Sacred Cat", "W-SC", Rarity.COMMON, mage.cards.s.SacredCat.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Sandwurm Convergence", "G-SC", Rarity.RARE, mage.cards.s.SandwurmConvergence.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Shed Weakness", "G-SW", Rarity.COMMON, mage.cards.s.ShedWeakness.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Shefet Monitor", "G-SM", Rarity.UNCOMMON, mage.cards.s.ShefetMonitor.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Sidewinder Naga", "G-SN", Rarity.COMMON, mage.cards.s.SidewinderNaga.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Sifter Wurm", "G-SWM", Rarity.UNCOMMON, mage.cards.s.SifterWurm.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Sixth Sense", "G-SIX", Rarity.UNCOMMON, mage.cards.s.SixthSense.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Solemnity", "W-SOL", Rarity.RARE, mage.cards.s.Solemnity.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Solitary Camel", "W-SCAM", Rarity.COMMON, mage.cards.s.SolitaryCamel.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Steward of Solidarity", "W-SOS", Rarity.UNCOMMON, mage.cards.s.StewardOfSolidarity.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Stinging Shot", "G-STS", Rarity.COMMON, mage.cards.s.StingingShot.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Sunscourge Champion", "W-SSC", Rarity.UNCOMMON, mage.cards.s.SunscourgeChampion.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Supply Caravan", "W-SUPC", Rarity.COMMON, mage.cards.s.SupplyCaravan.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Synchronized Strike", "G-SYS", Rarity.UNCOMMON, mage.cards.s.SynchronizedStrike.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Tah-Crop Elite", "W-TCE", Rarity.COMMON, mage.cards.t.TahCropElite.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Those Who Serve", "W-TWS", Rarity.COMMON, mage.cards.t.ThoseWhoServe.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Trial of Solidarity", "W-TOS", Rarity.UNCOMMON, mage.cards.t.TrialOfSolidarity.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Trial of Strength", "G-TOS", Rarity.UNCOMMON, mage.cards.t.TrialOfStrength.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Unconventional Tactics", "W-UT", Rarity.UNCOMMON, mage.cards.u.UnconventionalTactics.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Vizier of Deferment", "W-VOD", Rarity.UNCOMMON, mage.cards.v.VizierOfDeferment.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Vizier of Remedies", "W-VIR", Rarity.UNCOMMON, mage.cards.v.VizierOfRemedies.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Vizier of the Menagerie", "G-VOTM", Rarity.MYTHIC, mage.cards.v.VizierOfTheMenagerie.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Watchful Naga", "G-WN", Rarity.UNCOMMON, mage.cards.w.WatchfulNaga.class, NON_FULL_USE_VARIOUS));
    cards.add(new SetCardInfo("Wrath of God", "W-WOG", Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
  }

  @Override
  // the common taplands replacing the basic land
  public List<CardInfo> getSpecialLand() {
    if (savedSpecialLand.isEmpty()) {
      CardCriteria criteria = new CardCriteria();
      criteria.setCodes(this.code);
      criteria.rarities(Rarity.COMMON);
      criteria.types(CardType.LAND);
      savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
    }

    return new ArrayList<>(savedSpecialLand);
  }

}
