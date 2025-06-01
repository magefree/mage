package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FinalFantasyThroughTheAges extends ExpansionSet {

    private static final FinalFantasyThroughTheAges instance = new FinalFantasyThroughTheAges();

    public static FinalFantasyThroughTheAges getInstance() {
        return instance;
    }

    private FinalFantasyThroughTheAges() {
        super("Final Fantasy: Through the Ages", "FCA", ExpansionSet.buildDate(2025, 6, 13), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 64;

        cards.add(new SetCardInfo("Adeline, Resplendent Cathar", 1, Rarity.RARE, mage.cards.a.AdelineResplendentCathar.class));
        cards.add(new SetCardInfo("Akroma's Will", 21, Rarity.MYTHIC, mage.cards.a.AkromasWill.class));
        cards.add(new SetCardInfo("Ancient Copper Dragon", 12, Rarity.MYTHIC, mage.cards.a.AncientCopperDragon.class));
        cards.add(new SetCardInfo("Atraxa, Grand Unifier", 49, Rarity.MYTHIC, mage.cards.a.AtraxaGrandUnifier.class));
        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 15, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class));
        cards.add(new SetCardInfo("Bolas's Citadel", 7, Rarity.RARE, mage.cards.b.BolassCitadel.class));
        cards.add(new SetCardInfo("Brainstorm", 28, Rarity.UNCOMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Bruse Tarl, Boorish Herder", 50, Rarity.RARE, mage.cards.b.BruseTarlBoorishHerder.class));
        cards.add(new SetCardInfo("Captain Lannery Storm", 38, Rarity.UNCOMMON, mage.cards.c.CaptainLanneryStorm.class));
        cards.add(new SetCardInfo("Carpet of Flowers", 44, Rarity.RARE, mage.cards.c.CarpetOfFlowers.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 61, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Command Beacon", 64, Rarity.RARE, mage.cards.c.CommandBeacon.class));
        cards.add(new SetCardInfo("Counterspell", 4, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Cryptic Command", 29, Rarity.RARE, mage.cards.c.CrypticCommand.class));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", 22, Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Dark Ritual", 8, Rarity.RARE, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Deadly Dispute", 33, Rarity.UNCOMMON, mage.cards.d.DeadlyDispute.class));
        cards.add(new SetCardInfo("Diabolic Intent", 34, Rarity.RARE, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Dovin's Veto", 51, Rarity.UNCOMMON, mage.cards.d.DovinsVeto.class));
        cards.add(new SetCardInfo("Farseek", 45, Rarity.UNCOMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fatal Push", 9, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fynn, the Fangbearer", 46, Rarity.UNCOMMON, mage.cards.f.FynnTheFangbearer.class));
        cards.add(new SetCardInfo("Gix, Yawgmoth Praetor", 35, Rarity.MYTHIC, mage.cards.g.GixYawgmothPraetor.class));
        cards.add(new SetCardInfo("Godo, Bandit Warlord", 13, Rarity.RARE, mage.cards.g.GodoBanditWarlord.class));
        cards.add(new SetCardInfo("Inalla, Archmage Ritualist", 52, Rarity.RARE, mage.cards.i.InallaArchmageRitualist.class));
        cards.add(new SetCardInfo("Ishai, Ojutai Dragonspeaker", 53, Rarity.RARE, mage.cards.i.IshaiOjutaiDragonspeaker.class));
        cards.add(new SetCardInfo("Isshin, Two Heavens as One", 54, Rarity.RARE, mage.cards.i.IsshinTwoHeavensAsOne.class));
        cards.add(new SetCardInfo("Jodah, the Unifier", 17, Rarity.RARE, mage.cards.j.JodahTheUnifier.class));
        cards.add(new SetCardInfo("K'rrik, Son of Yawgmoth", 36, Rarity.RARE, mage.cards.k.KrrikSonOfYawgmoth.class));
        cards.add(new SetCardInfo("Kenrith, the Returned King", 23, Rarity.RARE, mage.cards.k.KenrithTheReturnedKing.class));
        cards.add(new SetCardInfo("Kinnan, Bonder Prodigy", 55, Rarity.MYTHIC, mage.cards.k.KinnanBonderProdigy.class));
        cards.add(new SetCardInfo("Kraum, Ludevic's Opus", 56, Rarity.RARE, mage.cards.k.KraumLudevicsOpus.class));
        cards.add(new SetCardInfo("Laboratory Maniac", 30, Rarity.UNCOMMON, mage.cards.l.LaboratoryManiac.class));
        cards.add(new SetCardInfo("Light Up the Stage", 39, Rarity.UNCOMMON, mage.cards.l.LightUpTheStage.class));
        cards.add(new SetCardInfo("Lightning Bolt", 40, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Loran of the Third Path", 24, Rarity.RARE, mage.cards.l.LoranOfTheThirdPath.class));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 25, Rarity.RARE, mage.cards.m.MangaraTheDiplomat.class));
        cards.add(new SetCardInfo("Mizzix's Mastery", 41, Rarity.RARE, mage.cards.m.MizzixsMastery.class));
        cards.add(new SetCardInfo("Muldrotha, the Gravetide", 57, Rarity.RARE, mage.cards.m.MuldrothaTheGravetide.class));
        cards.add(new SetCardInfo("Najeela, the Blade-Blossom", 42, Rarity.MYTHIC, mage.cards.n.NajeelaTheBladeBlossom.class));
        cards.add(new SetCardInfo("Nature's Claim", 47, Rarity.UNCOMMON, mage.cards.n.NaturesClaim.class));
        cards.add(new SetCardInfo("Nyxbloom Ancient", 16, Rarity.MYTHIC, mage.cards.n.NyxbloomAncient.class));
        cards.add(new SetCardInfo("Primeval Titan", 48, Rarity.RARE, mage.cards.p.PrimevalTitan.class));
        cards.add(new SetCardInfo("Purphoros, God of the Forge", 14, Rarity.MYTHIC, mage.cards.p.PurphorosGodOfTheForge.class));
        cards.add(new SetCardInfo("Ragavan, Nimble Pilferer", 43, Rarity.MYTHIC, mage.cards.r.RagavanNimblePilferer.class));
        cards.add(new SetCardInfo("Ranger-Captain of Eos", 2, Rarity.MYTHIC, mage.cards.r.RangerCaptainOfEos.class));
        cards.add(new SetCardInfo("Rhystic Study", 31, Rarity.MYTHIC, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Smuggler's Copter", 62, Rarity.RARE, mage.cards.s.SmugglersCopter.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", 3, Rarity.RARE, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Strixhaven Stadium", 63, Rarity.UNCOMMON, mage.cards.s.StrixhavenStadium.class));
        cards.add(new SetCardInfo("Stroke of Midnight", 26, Rarity.UNCOMMON, mage.cards.s.StrokeOfMidnight.class));
        cards.add(new SetCardInfo("Syr Konrad, the Grim", 10, Rarity.UNCOMMON, mage.cards.s.SyrKonradTheGrim.class));
        cards.add(new SetCardInfo("Teferi, Mage of Zhalfir", 32, Rarity.RARE, mage.cards.t.TeferiMageOfZhalfir.class));
        cards.add(new SetCardInfo("Thrasios, Triton Hero", 58, Rarity.MYTHIC, mage.cards.t.ThrasiosTritonHero.class));
        cards.add(new SetCardInfo("Traxos, Scourge of Kroog", 20, Rarity.RARE, mage.cards.t.TraxosScourgeOfKroog.class));
        cards.add(new SetCardInfo("Tymna the Weaver", 18, Rarity.RARE, mage.cards.t.TymnaTheWeaver.class));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 5, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class));
        cards.add(new SetCardInfo("Varragoth, Bloodsky Sire", 37, Rarity.RARE, mage.cards.v.VarragothBloodskySire.class));
        cards.add(new SetCardInfo("Venser, Shaper Savant", 6, Rarity.RARE, mage.cards.v.VenserShaperSavant.class));
        cards.add(new SetCardInfo("Vial Smasher the Fierce", 59, Rarity.MYTHIC, mage.cards.v.VialSmasherTheFierce.class));
        cards.add(new SetCardInfo("Wall of Omens", 27, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Winota, Joiner of Forces", 19, Rarity.RARE, mage.cards.w.WinotaJoinerOfForces.class));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 11, Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class));
        cards.add(new SetCardInfo("Yuriko, the Tiger's Shadow", 60, Rarity.RARE, mage.cards.y.YurikoTheTigersShadow.class));
    }
}
