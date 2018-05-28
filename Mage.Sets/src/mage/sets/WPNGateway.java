/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.i.IcatianJavelineers;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class WPNGateway extends ExpansionSet {

    private static final WPNGateway instance = new WPNGateway();

    public static WPNGateway getInstance() {
        return instance;
    }

    private WPNGateway() {
        super("WPN Gateway", "GRC", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Auramancer", 77, Rarity.SPECIAL, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Bloodcrazed Neonate", 83, Rarity.SPECIAL, mage.cards.b.BloodcrazedNeonate.class));
        cards.add(new SetCardInfo("Boggart Ram-Gang", 17, Rarity.SPECIAL, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 84, Rarity.SPECIAL, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Boomerang", 4, Rarity.SPECIAL, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Calciderm", 5, Rarity.SPECIAL, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Cenn's Tactician", 14, Rarity.SPECIAL, mage.cards.c.CennsTactician.class));
        cards.add(new SetCardInfo("Circle of Flame", 78, Rarity.SPECIAL, mage.cards.c.CircleOfFlame.class));
        cards.add(new SetCardInfo("Curse of the Bloody Tome", 80, Rarity.SPECIAL, mage.cards.c.CurseOfTheBloodyTome.class));
        cards.add(new SetCardInfo("Curse of Thirst", 81, Rarity.SPECIAL, mage.cards.c.CurseOfThirst.class));
        cards.add(new SetCardInfo("Curse of Wizardry", 47, Rarity.SPECIAL, mage.cards.c.CurseOfWizardry.class));
        cards.add(new SetCardInfo("Dauntless Dourbark", 12, Rarity.SPECIAL, mage.cards.d.DauntlessDourbark.class));
        cards.add(new SetCardInfo("Deathless Angel", 49, Rarity.SPECIAL, mage.cards.d.DeathlessAngel.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 19, Rarity.SPECIAL, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Emeria Angel", 35, Rarity.SPECIAL, mage.cards.e.EmeriaAngel.class));
        cards.add(new SetCardInfo("Fiery Temper", 3, Rarity.SPECIAL, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Fling", 69, Rarity.SPECIAL, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Gather the Townsfolk", 79, Rarity.SPECIAL, mage.cards.g.GatherTheTownsfolk.class));
        cards.add(new SetCardInfo("Golem's Heart", 60, Rarity.SPECIAL, mage.cards.g.GolemsHeart.class));
        cards.add(new SetCardInfo("Gravedigger", 16, Rarity.SPECIAL, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Hada Freeblade", 38, Rarity.SPECIAL, mage.cards.h.HadaFreeblade.class));
        cards.add(new SetCardInfo("Hellspark Elemental", 25, Rarity.SPECIAL, mage.cards.h.HellsparkElemental.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 2, Rarity.SPECIAL, IcatianJavelineers.class));
        cards.add(new SetCardInfo("Kalastria Highborn", 39, Rarity.SPECIAL, mage.cards.k.KalastriaHighborn.class));
        cards.add(new SetCardInfo("Kor Duelist", 32, Rarity.SPECIAL, mage.cards.k.KorDuelist.class));
        cards.add(new SetCardInfo("Kor Firewalker", 36, Rarity.SPECIAL, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Lava Axe", 13, Rarity.SPECIAL, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Leatherback Baloth", 37, Rarity.SPECIAL, mage.cards.l.LeatherbackBaloth.class));
        cards.add(new SetCardInfo("Llanowar Elves", 9, Rarity.SPECIAL, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Marisi's Twinclaws", 26, Rarity.SPECIAL, mage.cards.m.MarisisTwinclaws.class));
        cards.add(new SetCardInfo("Master's Call", 64, Rarity.SPECIAL, mage.cards.m.MastersCall.class));
        cards.add(new SetCardInfo("Maul Splicer", 72, Rarity.SPECIAL, mage.cards.m.MaulSplicer.class));
        cards.add(new SetCardInfo("Mind Control", 30, Rarity.SPECIAL, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Mind Stone", 11, Rarity.SPECIAL, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 10, Rarity.SPECIAL, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mycoid Shepherd", 28, Rarity.SPECIAL, mage.cards.m.MycoidShepherd.class));
        cards.add(new SetCardInfo("Naya Sojourners", 29, Rarity.SPECIAL, mage.cards.n.NayaSojourners.class));
        cards.add(new SetCardInfo("Nearheath Stalker", 82, Rarity.SPECIAL, mage.cards.n.NearheathStalker.class));
        cards.add(new SetCardInfo("Nissa's Chosen", 34, Rarity.SPECIAL, mage.cards.n.NissasChosen.class));
        cards.add(new SetCardInfo("Oona's Blackguard", 15, Rarity.SPECIAL, mage.cards.o.OonasBlackguard.class));
        cards.add(new SetCardInfo("Pathrazer of Ulamog", 46, Rarity.SPECIAL, mage.cards.p.PathrazerOfUlamog.class));
        cards.add(new SetCardInfo("Path to Exile", 24, Rarity.SPECIAL, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Plague Myr", 65, Rarity.SPECIAL, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Plague Stinger", 59, Rarity.SPECIAL, mage.cards.p.PlagueStinger.class));
        cards.add(new SetCardInfo("Reckless Wurm", 6, Rarity.SPECIAL, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Rise from the Grave", 31, Rarity.SPECIAL, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Selkie Hedge-Mage", 20, Rarity.SPECIAL, mage.cards.s.SelkieHedgeMage.class));
        cards.add(new SetCardInfo("Shrine of Burning Rage", 73, Rarity.SPECIAL, mage.cards.s.ShrineOfBurningRage.class));
        cards.add(new SetCardInfo("Signal Pest", 66, Rarity.SPECIAL, mage.cards.s.SignalPest.class));
        cards.add(new SetCardInfo("Skinrender", 63, Rarity.SPECIAL, mage.cards.s.Skinrender.class));
        cards.add(new SetCardInfo("Slave of Bolas", 27, Rarity.SPECIAL, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", 21, Rarity.SPECIAL, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Staggershock", 48, Rarity.SPECIAL, mage.cards.s.Staggershock.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 70, Rarity.SPECIAL, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Syphon Mind", 40, Rarity.SPECIAL, mage.cards.s.SyphonMind.class));
        cards.add(new SetCardInfo("Tormented Soul", 76, Rarity.SPECIAL, mage.cards.t.TormentedSoul.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 33, Rarity.SPECIAL, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vault Skirge", 71, Rarity.SPECIAL, mage.cards.v.VaultSkirge.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 18, Rarity.SPECIAL, mage.cards.w.WiltLeafCavaliers.class));
        cards.add(new SetCardInfo("Wood Elves", 1, Rarity.SPECIAL, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 22, Rarity.SPECIAL, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Yixlid Jailer", 7, Rarity.SPECIAL, mage.cards.y.YixlidJailer.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 8, Rarity.SPECIAL, mage.cards.z.ZoeticCavern.class));
    }
}
