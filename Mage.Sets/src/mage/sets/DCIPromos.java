package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdci
 */
public class DCIPromos extends ExpansionSet {

    private static final DCIPromos instance = new DCIPromos();

    public static DCIPromos getInstance() {
        return instance;
    }

    private DCIPromos() {
        super("DCI Promos", "PDCI", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Wood Elves", 1, Rarity.RARE, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 2, Rarity.RARE, mage.cards.i.IcatianJavelineers.class));
        cards.add(new SetCardInfo("Fiery Temper", 3, Rarity.RARE, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Boomerang", 4, Rarity.RARE, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Calciderm", 5, Rarity.RARE, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Reckless Wurm", 6, Rarity.RARE, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Yixlid Jailer", 7, Rarity.RARE, mage.cards.y.YixlidJailer.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 8, Rarity.RARE, mage.cards.z.ZoeticCavern.class));
        cards.add(new SetCardInfo("Llanowar Elves", 9, Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 10, Rarity.RARE, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mind Stone", 11, Rarity.RARE, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Dauntless Dourbark", 12, Rarity.RARE, mage.cards.d.DauntlessDourbark.class));
        cards.add(new SetCardInfo("Lava Axe", 13, Rarity.RARE, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Cenn's Tactician", 14, Rarity.RARE, mage.cards.c.CennsTactician.class));
        cards.add(new SetCardInfo("Oona's Blackguard", 15, Rarity.RARE, mage.cards.o.OonasBlackguard.class));
        cards.add(new SetCardInfo("Gravedigger", 16, Rarity.RARE, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Boggart Ram-Gang", 17, Rarity.RARE, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 18, Rarity.RARE, mage.cards.w.WiltLeafCavaliers.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 19, Rarity.RARE, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Selkie Hedge-Mage", 20, Rarity.RARE, mage.cards.s.SelkieHedgeMage.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", 21, Rarity.RARE, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 22, Rarity.RARE, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Path to Exile", 24, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Hellspark Elemental", 25, Rarity.RARE, mage.cards.h.HellsparkElemental.class));
        cards.add(new SetCardInfo("Marisi's Twinclaws", 26, Rarity.RARE, mage.cards.m.MarisisTwinclaws.class));
        cards.add(new SetCardInfo("Slave of Bolas", 27, Rarity.RARE, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Mycoid Shepherd", 28, Rarity.RARE, mage.cards.m.MycoidShepherd.class));
        cards.add(new SetCardInfo("Naya Sojourners", 29, Rarity.RARE, mage.cards.n.NayaSojourners.class));
        cards.add(new SetCardInfo("Mind Control", 30, Rarity.RARE, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Rise from the Grave", 31, Rarity.RARE, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Kor Duelist", 32, Rarity.RARE, mage.cards.k.KorDuelist.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 33, Rarity.RARE, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Nissa's Chosen", 34, Rarity.RARE, mage.cards.n.NissasChosen.class));
        cards.add(new SetCardInfo("Emeria Angel", 35, Rarity.RARE, mage.cards.e.EmeriaAngel.class));
        cards.add(new SetCardInfo("Kor Firewalker", 36, Rarity.RARE, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Leatherback Baloth", 37, Rarity.RARE, mage.cards.l.LeatherbackBaloth.class));
        cards.add(new SetCardInfo("Hada Freeblade", 38, Rarity.RARE, mage.cards.h.HadaFreeblade.class));
        cards.add(new SetCardInfo("Kalastria Highborn", 39, Rarity.RARE, mage.cards.k.KalastriaHighborn.class));
        cards.add(new SetCardInfo("Syphon Mind", 40, Rarity.RARE, mage.cards.s.SyphonMind.class));
        // Planes 41-45
        cards.add(new SetCardInfo("Pathrazer of Ulamog", 46, Rarity.RARE, mage.cards.p.PathrazerOfUlamog.class));
        cards.add(new SetCardInfo("Curse of Wizardry", 47, Rarity.RARE, mage.cards.c.CurseOfWizardry.class));
        cards.add(new SetCardInfo("Staggershock", 48, Rarity.RARE, mage.cards.s.Staggershock.class));
        cards.add(new SetCardInfo("Deathless Angel", 49, Rarity.RARE, mage.cards.d.DeathlessAngel.class));
        cards.add(new SetCardInfo("Fling", 50, Rarity.RARE, mage.cards.f.Fling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Ranger", 51, Rarity.RARE, mage.cards.s.SylvanRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Specter", 52, Rarity.COMMON, mage.cards.l.LilianasSpecter.class));
        cards.add(new SetCardInfo("Mitotic Slime", 53, Rarity.RARE, mage.cards.m.MitoticSlime.class));
        // Schemes 54-58
        cards.add(new SetCardInfo("Plague Stinger", 59, Rarity.RARE, mage.cards.p.PlagueStinger.class));
        cards.add(new SetCardInfo("Golem's Heart", 60, Rarity.RARE, mage.cards.g.GolemsHeart.class));
        cards.add(new SetCardInfo("Memnite", 61, Rarity.UNCOMMON, mage.cards.m.Memnite.class));
        cards.add(new SetCardInfo("Tempered Steel", 62, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Skinrender", 63, Rarity.RARE, mage.cards.s.Skinrender.class));
        cards.add(new SetCardInfo("Master's Call", 64, Rarity.RARE, mage.cards.m.MastersCall.class));
        cards.add(new SetCardInfo("Plague Myr", 65, Rarity.RARE, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Signal Pest", 66, Rarity.RARE, mage.cards.s.SignalPest.class));
        cards.add(new SetCardInfo("Treasure Mage", 67, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Black Sun's Zenith", 68, Rarity.RARE, mage.cards.b.BlackSunsZenith.class));
        cards.add(new SetCardInfo("Fling", 69, Rarity.RARE, mage.cards.f.Fling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Ranger", 70, Rarity.RARE, mage.cards.s.SylvanRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vault Skirge", 71, Rarity.RARE, mage.cards.v.VaultSkirge.class));
        cards.add(new SetCardInfo("Maul Splicer", 72, Rarity.RARE, mage.cards.m.MaulSplicer.class));
        cards.add(new SetCardInfo("Shrine of Burning Rage", 73, Rarity.RARE, mage.cards.s.ShrineOfBurningRage.class));
        cards.add(new SetCardInfo("Priest of Urabrask", 74, Rarity.UNCOMMON, mage.cards.p.PriestOfUrabrask.class));
        cards.add(new SetCardInfo("Myr Superion", 75, Rarity.RARE, mage.cards.m.MyrSuperion.class));
        cards.add(new SetCardInfo("Tormented Soul", 76, Rarity.RARE, mage.cards.t.TormentedSoul.class));
        cards.add(new SetCardInfo("Auramancer", 77, Rarity.RARE, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Circle of Flame", 78, Rarity.RARE, mage.cards.c.CircleOfFlame.class));
        cards.add(new SetCardInfo("Stormblood Berserker", 79, Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Dungrove Elder", 80, Rarity.RARE, mage.cards.d.DungroveElder.class));

     }
}
