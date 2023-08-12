package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarchOfTheMachineTheAftermath extends ExpansionSet {

    private static final MarchOfTheMachineTheAftermath instance = new MarchOfTheMachineTheAftermath();

    public static MarchOfTheMachineTheAftermath getInstance() {
        return instance;
    }

    private MarchOfTheMachineTheAftermath() {
        super("March of the Machine: The Aftermath", "MAT", ExpansionSet.buildDate(2023, 5, 12), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "March of the Machine";
        this.hasBasicLands = false;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Animist's Might", 20, Rarity.UNCOMMON, mage.cards.a.AnimistsMight.class));
        cards.add(new SetCardInfo("Arni Metalbrow", 16, Rarity.RARE, mage.cards.a.ArniMetalbrow.class));
        cards.add(new SetCardInfo("Ayara's Oathsworn", 11, Rarity.RARE, mage.cards.a.AyarasOathsworn.class));
        cards.add(new SetCardInfo("Blot Out", 12, Rarity.UNCOMMON, mage.cards.b.BlotOut.class));
        cards.add(new SetCardInfo("Calix, Guided by Fate", 26, Rarity.MYTHIC, mage.cards.c.CalixGuidedByFate.class));
        cards.add(new SetCardInfo("Campus Renovation", 27, Rarity.UNCOMMON, mage.cards.c.CampusRenovation.class));
        cards.add(new SetCardInfo("Coppercoat Vanguard", 1, Rarity.UNCOMMON, mage.cards.c.CoppercoatVanguard.class));
        cards.add(new SetCardInfo("Cosmic Rebirth", 28, Rarity.UNCOMMON, mage.cards.c.CosmicRebirth.class));
        cards.add(new SetCardInfo("Danitha, New Benalia's Light", 29, Rarity.RARE, mage.cards.d.DanithaNewBenaliasLight.class));
        cards.add(new SetCardInfo("Death-Rattle Oni", 13, Rarity.UNCOMMON, mage.cards.d.DeathRattleOni.class));
        cards.add(new SetCardInfo("Drannith Ruins", 50, Rarity.RARE, mage.cards.d.DrannithRuins.class));
        cards.add(new SetCardInfo("Feast of the Victorious Dead", 30, Rarity.UNCOMMON, mage.cards.f.FeastOfTheVictoriousDead.class));
        cards.add(new SetCardInfo("Filter Out", 7, Rarity.UNCOMMON, mage.cards.f.FilterOut.class));
        cards.add(new SetCardInfo("Gold-Forged Thopteryx", 31, Rarity.UNCOMMON, mage.cards.g.GoldForgedThopteryx.class));
        cards.add(new SetCardInfo("Harnessed Snubhorn", 3, Rarity.UNCOMMON, mage.cards.h.HarnessedSnubhorn.class));
        cards.add(new SetCardInfo("Jirina, Dauntless General", 32, Rarity.RARE, mage.cards.j.JirinaDauntlessGeneral.class));
        cards.add(new SetCardInfo("Jolrael, Voice of Zhalfir", 33, Rarity.RARE, mage.cards.j.JolraelVoiceOfZhalfir.class));
        cards.add(new SetCardInfo("Karn, Legacy Reforged", 49, Rarity.MYTHIC, mage.cards.k.KarnLegacyReforged.class));
        cards.add(new SetCardInfo("Kiora, Sovereign of the Deep", 35, Rarity.MYTHIC, mage.cards.k.KioraSovereignOfTheDeep.class));
        cards.add(new SetCardInfo("Kolaghan Warmonger", 17, Rarity.UNCOMMON, mage.cards.k.KolaghanWarmonger.class));
        cards.add(new SetCardInfo("Leyline Immersion", 21, Rarity.RARE, mage.cards.l.LeylineImmersion.class));
        cards.add(new SetCardInfo("Markov Baron", 14, Rarity.UNCOMMON, mage.cards.m.MarkovBaron.class));
        cards.add(new SetCardInfo("Metropolis Reformer", 4, Rarity.RARE, mage.cards.m.MetropolisReformer.class));
        cards.add(new SetCardInfo("Nahiri, Forged in Fury", 36, Rarity.MYTHIC, mage.cards.n.NahiriForgedInFury.class));
        cards.add(new SetCardInfo("Nahiri's Resolve", 37, Rarity.RARE, mage.cards.n.NahirisResolve.class));
        cards.add(new SetCardInfo("Narset, Enlightened Exile", 38, Rarity.MYTHIC, mage.cards.n.NarsetEnlightenedExile.class));
        cards.add(new SetCardInfo("Nashi, Moon's Legacy", 39, Rarity.RARE, mage.cards.n.NashiMoonsLegacy.class));
        cards.add(new SetCardInfo("Nissa, Resurgent Animist", 22, Rarity.MYTHIC, mage.cards.n.NissaResurgentAnimist.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Supreme", 40, Rarity.RARE, mage.cards.n.NivMizzetSupreme.class));
        cards.add(new SetCardInfo("Open the Way", 23, Rarity.RARE, mage.cards.o.OpenTheWay.class));
        cards.add(new SetCardInfo("Pia Nalaar, Consul of Revival", 42, Rarity.RARE, mage.cards.p.PiaNalaarConsulOfRevival.class));
        cards.add(new SetCardInfo("Plargg and Nassari", 18, Rarity.RARE, mage.cards.p.PlarggAndNassari.class));
        cards.add(new SetCardInfo("Rebuild the City", 43, Rarity.RARE, mage.cards.r.RebuildTheCity.class));
        cards.add(new SetCardInfo("Reckless Handling", 19, Rarity.UNCOMMON, mage.cards.r.RecklessHandling.class));
        cards.add(new SetCardInfo("Rocco, Street Chef", 44, Rarity.RARE, mage.cards.r.RoccoStreetChef.class));
        cards.add(new SetCardInfo("Samut, Vizier of Naktamun", 45, Rarity.MYTHIC, mage.cards.s.SamutVizierOfNaktamun.class));
        cards.add(new SetCardInfo("Sarkhan, Soul Aflame", 46, Rarity.MYTHIC, mage.cards.s.SarkhanSoulAflame.class));
        cards.add(new SetCardInfo("Sigarda, Font of Blessings", 47, Rarity.RARE, mage.cards.s.SigardaFontOfBlessings.class));
        cards.add(new SetCardInfo("Spark Rupture", 5, Rarity.RARE, mage.cards.s.SparkRupture.class));
        cards.add(new SetCardInfo("Tazri, Stalwart Survivor", 6, Rarity.RARE, mage.cards.t.TazriStalwartSurvivor.class));
        cards.add(new SetCardInfo("The Kenriths' Royal Funeral", 34, Rarity.RARE, mage.cards.t.TheKenrithsRoyalFuneral.class));
        cards.add(new SetCardInfo("Tolarian Contempt", 8, Rarity.UNCOMMON, mage.cards.t.TolarianContempt.class));
        cards.add(new SetCardInfo("Training Grounds", 9, Rarity.RARE, mage.cards.t.TrainingGrounds.class));
        cards.add(new SetCardInfo("Tranquil Frillback", 24, Rarity.RARE, mage.cards.t.TranquilFrillback.class));
        cards.add(new SetCardInfo("Tyvar the Bellicose", 48, Rarity.MYTHIC, mage.cards.t.TyvarTheBellicose.class));
        cards.add(new SetCardInfo("Undercity Upheaval", 25, Rarity.UNCOMMON, mage.cards.u.UndercityUpheaval.class));
        cards.add(new SetCardInfo("Urborg Scavengers", 15, Rarity.RARE, mage.cards.u.UrborgScavengers.class));
        cards.add(new SetCardInfo("Vesuvan Drifter", 10, Rarity.RARE, mage.cards.v.VesuvanDrifter.class));
    }
}
