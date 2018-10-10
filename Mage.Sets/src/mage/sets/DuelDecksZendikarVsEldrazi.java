package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksZendikarVsEldrazi extends ExpansionSet {

    private static final DuelDecksZendikarVsEldrazi instance = new DuelDecksZendikarVsEldrazi();

    public static DuelDecksZendikarVsEldrazi getInstance() {
        return instance;
    }

    private DuelDecksZendikarVsEldrazi() {
        super("Duel Decks: Zendikar vs. Eldrazi", "DDP", ExpansionSet.buildDate(2015, 8, 28), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Affa Guard Hound", 2, Rarity.UNCOMMON, mage.cards.a.AffaGuardHound.class));
        cards.add(new SetCardInfo("Akoum Refuge", 67, Rarity.UNCOMMON, mage.cards.a.AkoumRefuge.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 42, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Avenger of Zendikar", 1, Rarity.MYTHIC, mage.cards.a.AvengerOfZendikar.class));
        cards.add(new SetCardInfo("Beastbreaker of Bala Ged", 10, Rarity.UNCOMMON, mage.cards.b.BeastbreakerOfBalaGed.class));
        cards.add(new SetCardInfo("Bloodrite Invoker", 45, Rarity.COMMON, mage.cards.b.BloodriteInvoker.class));
        cards.add(new SetCardInfo("Bloodthrone Vampire", 46, Rarity.COMMON, mage.cards.b.BloodthroneVampire.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 47, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Cadaver Imp", 48, Rarity.COMMON, mage.cards.c.CadaverImp.class));
        cards.add(new SetCardInfo("Caravan Escort", 3, Rarity.COMMON, mage.cards.c.CaravanEscort.class));
        cards.add(new SetCardInfo("Consume the Meek", 49, Rarity.RARE, mage.cards.c.ConsumeTheMeek.class));
        cards.add(new SetCardInfo("Corpsehatch", 50, Rarity.UNCOMMON, mage.cards.c.Corpsehatch.class));
        cards.add(new SetCardInfo("Daggerback Basilisk", 11, Rarity.COMMON, mage.cards.d.DaggerbackBasilisk.class));
        cards.add(new SetCardInfo("Dominator Drone", 51, Rarity.COMMON, mage.cards.d.DominatorDrone.class));
        cards.add(new SetCardInfo("Eldrazi Temple", 68, Rarity.UNCOMMON, mage.cards.e.EldraziTemple.class));
        cards.add(new SetCardInfo("Emrakul's Hatcher", 59, Rarity.COMMON, mage.cards.e.EmrakulsHatcher.class));
        cards.add(new SetCardInfo("Evolving Wilds", 31, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Explorer's Scope", 28, Rarity.COMMON, mage.cards.e.ExplorersScope.class));
        cards.add(new SetCardInfo("Forerunner of Slaughter", 64, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfSlaughter.class));
        cards.add(new SetCardInfo("Forest", 38, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 39, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 40, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forked Bolt", 60, Rarity.COMMON, mage.cards.f.ForkedBolt.class));
        cards.add(new SetCardInfo("Frontier Guide", 12, Rarity.UNCOMMON, mage.cards.f.FrontierGuide.class));
        cards.add(new SetCardInfo("Graypelt Hunter", 13, Rarity.COMMON, mage.cards.g.GraypeltHunter.class));
        cards.add(new SetCardInfo("Graypelt Refuge", 32, Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Grazing Gladehart", 14, Rarity.COMMON, mage.cards.g.GrazingGladehart.class));
        cards.add(new SetCardInfo("Groundswell", 15, Rarity.COMMON, mage.cards.g.Groundswell.class));
        cards.add(new SetCardInfo("Harrow", 16, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Heartstabber Mosquito", 52, Rarity.COMMON, mage.cards.h.HeartstabberMosquito.class));
        cards.add(new SetCardInfo("Hellion Eruption", 61, Rarity.RARE, mage.cards.h.HellionEruption.class));
        cards.add(new SetCardInfo("Induce Despair", 53, Rarity.COMMON, mage.cards.i.InduceDespair.class));
        cards.add(new SetCardInfo("It That Betrays", 43, Rarity.RARE, mage.cards.i.ItThatBetrays.class));
        cards.add(new SetCardInfo("Joraga Bard", 17, Rarity.COMMON, mage.cards.j.JoragaBard.class));
        cards.add(new SetCardInfo("Kabira Vindicator", 4, Rarity.UNCOMMON, mage.cards.k.KabiraVindicator.class));
        cards.add(new SetCardInfo("Khalni Heart Expedition", 18, Rarity.COMMON, mage.cards.k.KhalniHeartExpedition.class));
        cards.add(new SetCardInfo("Knight of Cliffhaven", 5, Rarity.COMMON, mage.cards.k.KnightOfCliffhaven.class));
        cards.add(new SetCardInfo("Magmaw", 62, Rarity.RARE, mage.cards.m.Magmaw.class));
        cards.add(new SetCardInfo("Makindi Griffin", 6, Rarity.COMMON, mage.cards.m.MakindiGriffin.class));
        cards.add(new SetCardInfo("Marsh Casualties", 54, Rarity.UNCOMMON, mage.cards.m.MarshCasualties.class));
        cards.add(new SetCardInfo("Mind Stone", 65, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mountain", 73, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 74, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 75, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oblivion Sower", 41, Rarity.MYTHIC, mage.cards.o.OblivionSower.class));
        cards.add(new SetCardInfo("Ondu Giant", 19, Rarity.COMMON, mage.cards.o.OnduGiant.class));
        cards.add(new SetCardInfo("Oust", 7, Rarity.UNCOMMON, mage.cards.o.Oust.class));
        cards.add(new SetCardInfo("Pawn of Ulamog", 55, Rarity.UNCOMMON, mage.cards.p.PawnOfUlamog.class));
        cards.add(new SetCardInfo("Plains", 35, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 36, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 37, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Command", 20, Rarity.RARE, mage.cards.p.PrimalCommand.class));
        cards.add(new SetCardInfo("Read the Bones", 56, Rarity.COMMON, mage.cards.r.ReadTheBones.class));
        cards.add(new SetCardInfo("Repel the Darkness", 8, Rarity.COMMON, mage.cards.r.RepelTheDarkness.class));
        cards.add(new SetCardInfo("Retreat to Kazandu", 21, Rarity.UNCOMMON, mage.cards.r.RetreatToKazandu.class));
        cards.add(new SetCardInfo("Rocky Tar Pit", 69, Rarity.UNCOMMON, mage.cards.r.RockyTarPit.class));
        cards.add(new SetCardInfo("Runed Servitor", 66, Rarity.UNCOMMON, mage.cards.r.RunedServitor.class));
        cards.add(new SetCardInfo("Scute Mob", 22, Rarity.RARE, mage.cards.s.ScuteMob.class));
        cards.add(new SetCardInfo("Seer's Sundial", 29, Rarity.RARE, mage.cards.s.SeersSundial.class));
        cards.add(new SetCardInfo("Sheer Drop", 9, Rarity.COMMON, mage.cards.s.SheerDrop.class));
        cards.add(new SetCardInfo("Smother", 57, Rarity.UNCOMMON, mage.cards.s.Smother.class));
        cards.add(new SetCardInfo("Stirring Wildwood", 33, Rarity.RARE, mage.cards.s.StirringWildwood.class));
        cards.add(new SetCardInfo("Stonework Puma", 30, Rarity.COMMON, mage.cards.s.StoneworkPuma.class));
        cards.add(new SetCardInfo("Swamp", 70, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 71, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 72, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tajuru Archer", 23, Rarity.UNCOMMON, mage.cards.t.TajuruArcher.class));
        cards.add(new SetCardInfo("Territorial Baloth", 24, Rarity.COMMON, mage.cards.t.TerritorialBaloth.class));
        cards.add(new SetCardInfo("Torch Slinger", 63, Rarity.COMMON, mage.cards.t.TorchSlinger.class));
        cards.add(new SetCardInfo("Turntimber Basilisk", 25, Rarity.UNCOMMON, mage.cards.t.TurntimberBasilisk.class));
        cards.add(new SetCardInfo("Turntimber Grove", 34, Rarity.COMMON, mage.cards.t.TurntimberGrove.class));
        cards.add(new SetCardInfo("Ulamog's Crusher", 44, Rarity.COMMON, mage.cards.u.UlamogsCrusher.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 58, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Veteran Warleader", 27, Rarity.RARE, mage.cards.v.VeteranWarleader.class));
        cards.add(new SetCardInfo("Wildheart Invoker", 26, Rarity.COMMON, mage.cards.w.WildheartInvoker.class));
    }
}
