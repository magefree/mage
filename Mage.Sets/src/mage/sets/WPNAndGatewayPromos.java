package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes, JayDi85
 */
public final class WPNAndGatewayPromos extends ExpansionSet {

    private static final WPNAndGatewayPromos instance = new WPNAndGatewayPromos();

    public static WPNAndGatewayPromos getInstance() {
        return instance;
    }

    private WPNAndGatewayPromos() {
        super("WPN and Gateway Promos", "GRC", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // one inner set for many promos by years (Gameway + Wizards Play Network promos by years)
        // https://mtg.gamepedia.com/Gateway_cards

        // Gateway Promos -- xmage uses one set (GRC), but scryfall store it by years
        // 2006 - https://scryfall.com/sets/pgtw
        cards.add(new SetCardInfo("Fiery Temper", 3, Rarity.RARE, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 2, Rarity.RARE, mage.cards.i.IcatianJavelineers.class));
        cards.add(new SetCardInfo("Wood Elves", 1, Rarity.RARE, mage.cards.w.WoodElves.class));
        // 2007 - https://scryfall.com/sets/pg07
        cards.add(new SetCardInfo("Boomerang", 4, Rarity.RARE, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Calciderm", 5, Rarity.RARE, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Dauntless Dourbark", 12, Rarity.RARE, mage.cards.d.DauntlessDourbark.class));
        cards.add(new SetCardInfo("Llanowar Elves", 9, Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Mind Stone", 11, Rarity.RARE, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 10, Rarity.RARE, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Reckless Wurm", 6, Rarity.RARE, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Yixlid Jailer", 7, Rarity.RARE, mage.cards.y.YixlidJailer.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 8, Rarity.RARE, mage.cards.z.ZoeticCavern.class));
        // 2008a - https://scryfall.com/sets/pg08
        cards.add(new SetCardInfo("Boggart Ram-Gang", 17, Rarity.RARE, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Cenn's Tactician", 14, Rarity.RARE, mage.cards.c.CennsTactician.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 19, Rarity.RARE, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Gravedigger", 16, Rarity.RARE, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Lava Axe", 13, Rarity.RARE, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Oona's Blackguard", 15, Rarity.RARE, mage.cards.o.OonasBlackguard.class));
        cards.add(new SetCardInfo("Selkie Hedge-Mage", 20, Rarity.RARE, mage.cards.s.SelkieHedgeMage.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 18, Rarity.RARE, mage.cards.w.WiltLeafCavaliers.class));

        // Wizards Play Network Promos -- xmage uses one set (GRC), but scryfall store it by years
        // 2008b - https://scryfall.com/sets/pwpn
        cards.add(new SetCardInfo("Sprouting Thrinax", 21, Rarity.RARE, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 22, Rarity.RARE, mage.cards.w.WoollyThoctar.class));
        // 2009 - https://scryfall.com/sets/pwp09
        cards.add(new SetCardInfo("Hellspark Elemental", 25, Rarity.RARE, mage.cards.h.HellsparkElemental.class));
        cards.add(new SetCardInfo("Kor Duelist", 32, Rarity.RARE, mage.cards.k.KorDuelist.class));
        cards.add(new SetCardInfo("Marisi's Twinclaws", 26, Rarity.RARE, mage.cards.m.MarisisTwinclaws.class));
        cards.add(new SetCardInfo("Mind Control", 30, Rarity.RARE, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Path to Exile", 24, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Rise from the Grave", 31, Rarity.RARE, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Slave of Bolas", 27, Rarity.RARE, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 33, Rarity.RARE, mage.cards.v.VampireNighthawk.class));
        // 2010 - https://scryfall.com/sets/pwp10
        cards.add(new SetCardInfo("Curse of Wizardry", 47, Rarity.RARE, mage.cards.c.CurseOfWizardry.class));
        cards.add(new SetCardInfo("Fling", 50, Rarity.RARE, mage.cards.f.Fling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Golem's Heart", 60, Rarity.RARE, mage.cards.g.GolemsHeart.class));
        cards.add(new SetCardInfo("Kor Firewalker", 36, Rarity.RARE, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Leatherback Baloth", 37, Rarity.RARE, mage.cards.l.LeatherbackBaloth.class));
        cards.add(new SetCardInfo("Pathrazer of Ulamog", 46, Rarity.RARE, mage.cards.p.PathrazerOfUlamog.class));
        cards.add(new SetCardInfo("Plague Stinger", 59, Rarity.RARE, mage.cards.p.PlagueStinger.class));
        cards.add(new SetCardInfo("Skinrender", 63, Rarity.RARE, mage.cards.s.Skinrender.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 51, Rarity.RARE, mage.cards.s.SylvanRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syphon Mind", 40, Rarity.RARE, mage.cards.s.SyphonMind.class));
        // 2011 - https://scryfall.com/sets/pwp11
        cards.add(new SetCardInfo("Auramancer", 77, Rarity.RARE, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Bloodcrazed Neonate", 83, Rarity.RARE, mage.cards.b.BloodcrazedNeonate.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 84, Rarity.RARE, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Circle of Flame", 78, Rarity.RARE, mage.cards.c.CircleOfFlame.class));
        cards.add(new SetCardInfo("Curse of the Bloody Tome", 80, Rarity.RARE, mage.cards.c.CurseOfTheBloodyTome.class));
        cards.add(new SetCardInfo("Fling", 69, Rarity.RARE, mage.cards.f.Fling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Master's Call", 64, Rarity.RARE, mage.cards.m.MastersCall.class));
        cards.add(new SetCardInfo("Maul Splicer", 72, Rarity.RARE, mage.cards.m.MaulSplicer.class));
        cards.add(new SetCardInfo("Plague Myr", 65, Rarity.RARE, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Shrine of Burning Rage", 73, Rarity.RARE, mage.cards.s.ShrineOfBurningRage.class));
        cards.add(new SetCardInfo("Signal Pest", 66, Rarity.RARE, mage.cards.s.SignalPest.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 70, Rarity.RARE, mage.cards.s.SylvanRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tormented Soul", 76, Rarity.RARE, mage.cards.t.TormentedSoul.class));
        cards.add(new SetCardInfo("Vault Skirge", 71, Rarity.RARE, mage.cards.v.VaultSkirge.class));
        // 2012 - https://scryfall.com/sets/pwp12
        cards.add(new SetCardInfo("Curse of Thirst", 81, Rarity.RARE, mage.cards.c.CurseOfThirst.class));
        cards.add(new SetCardInfo("Gather the Townsfolk", 79, Rarity.RARE, mage.cards.g.GatherTheTownsfolk.class));
        cards.add(new SetCardInfo("Nearheath Stalker", 82, Rarity.RARE, mage.cards.n.NearheathStalker.class));
    }
}