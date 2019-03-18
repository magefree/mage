
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class LaunchParty extends ExpansionSet {

    private static final LaunchParty instance = new LaunchParty();

    public static LaunchParty getInstance() {
        return instance;
    }

    private LaunchParty() {
        super("Launch Party", "MLP", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Ajani Vengeant", 4, Rarity.MYTHIC, mage.cards.a.AjaniVengeant.class));
        cards.add(new SetCardInfo("Ancient Hellkite", 11, Rarity.RARE, mage.cards.a.AncientHellkite.class));
        cards.add(new SetCardInfo("Angel of Deliverance", 35, Rarity.RARE, mage.cards.a.AngelOfDeliverance.class));
        cards.add(new SetCardInfo("Ant Queen", 7, Rarity.RARE, mage.cards.a.AntQueen.class));
        cards.add(new SetCardInfo("Bident of Thassa", 24, Rarity.RARE, mage.cards.b.BidentOfThassa.class));
        cards.add(new SetCardInfo("Bishop of Rebirth", 41, Rarity.RARE, mage.cards.b.BishopOfRebirth.class));
        cards.add(new SetCardInfo("Blight Herder", 32, Rarity.RARE, mage.cards.b.BlightHerder.class));
        cards.add(new SetCardInfo("Breaking // Entering", 22, Rarity.RARE, mage.cards.b.BreakingEntering.class));
        cards.add(new SetCardInfo("Colossal Whale", 23, Rarity.RARE, mage.cards.c.ColossalWhale.class));
        cards.add(new SetCardInfo("Deadbridge Goliath", 20, Rarity.RARE, mage.cards.d.DeadbridgeGoliath.class));
        cards.add(new SetCardInfo("Deathbringer Regent", 30, Rarity.RARE, mage.cards.d.DeathbringerRegent.class));
        cards.add(new SetCardInfo("Dictate of the Twin Gods", 26, Rarity.RARE, mage.cards.d.DictateOfTheTwinGods.class));
        cards.add(new SetCardInfo("Dragon Throne of Tarkir", 27, Rarity.RARE, mage.cards.d.DragonThroneOfTarkir.class));
        cards.add(new SetCardInfo("Earwig Squad", 1, Rarity.RARE, mage.cards.e.EarwigSquad.class));
        cards.add(new SetCardInfo("Endbringer", 34, Rarity.RARE, mage.cards.e.Endbringer.class));
        cards.add(new SetCardInfo("Figure of Destiny", 3, Rarity.RARE, mage.cards.f.FigureOfDestiny.class));
        cards.add(new SetCardInfo("Garruk's Horde", 15, Rarity.RARE, mage.cards.g.GarruksHorde.class));
        cards.add(new SetCardInfo("Identity Thief", 33, Rarity.RARE, mage.cards.i.IdentityThief.class));
        cards.add(new SetCardInfo("In Garruk's Wake", 28, Rarity.RARE, mage.cards.i.InGarruksWake.class));
        cards.add(new SetCardInfo("Joraga Warcaller", 9, Rarity.RARE, mage.cards.j.JoragaWarcaller.class));
        cards.add(new SetCardInfo("Knight of New Alara", 6, Rarity.RARE, mage.cards.k.KnightOfNewAlara.class));
        cards.add(new SetCardInfo("Lord of Shatterskull Pass", 10, Rarity.RARE, mage.cards.l.LordOfShatterskullPass.class));
        cards.add(new SetCardInfo("Ludevic's Abomination", 1064, Rarity.RARE, mage.cards.l.LudevicsAbomination.class));
        cards.add(new SetCardInfo("Ludevic's Test Subject", 16, Rarity.RARE, mage.cards.l.LudevicsTestSubject.class));
        cards.add(new SetCardInfo("Mizzium Meddler", 31, Rarity.RARE, mage.cards.m.MizziumMeddler.class));
        cards.add(new SetCardInfo("Mondronen Shaman", 17, Rarity.RARE, mage.cards.m.MondronenShaman.class));
        cards.add(new SetCardInfo("Obelisk of Alara", 5, Rarity.RARE, mage.cards.o.ObeliskOfAlara.class));
        cards.add(new SetCardInfo("Oracle's Vault", 39, Rarity.RARE, mage.cards.o.OraclesVault.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", 14, Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class));
        cards.add(new SetCardInfo("Quicksmith Rebel", 38, Rarity.RARE, mage.cards.q.QuicksmithRebel.class));
        cards.add(new SetCardInfo("Ramunap Excavator", 40, Rarity.RARE, mage.cards.r.RamunapExcavator.class));
        cards.add(new SetCardInfo("Restoration Angel", 18, Rarity.RARE, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Saheeli's Artistry", 37, Rarity.RARE, mage.cards.s.SaheelisArtistry.class));
        cards.add(new SetCardInfo("Sandsteppe Mastodon", 29, Rarity.RARE, mage.cards.s.SandsteppeMastodon.class));
        cards.add(new SetCardInfo("Skarrg Goliath", 21, Rarity.RARE, mage.cards.s.SkarrgGoliath.class));
        cards.add(new SetCardInfo("Staff of Nin", 19, Rarity.RARE, mage.cards.s.StaffOfNin.class));
        cards.add(new SetCardInfo("Steel Hellkite", 12, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Thopter Assembly", 13, Rarity.RARE, mage.cards.t.ThopterAssembly.class));
        cards.add(new SetCardInfo("Tovolar's Magehunter", 98, Rarity.RARE, mage.cards.t.TovolarsMagehunter.class));
        cards.add(new SetCardInfo("Tromokratis", 25, Rarity.RARE, mage.cards.t.Tromokratis.class));
        cards.add(new SetCardInfo("Valakut, the Molten Pinnacle", 8, Rarity.RARE, mage.cards.v.ValakutTheMoltenPinnacle.class));
        cards.add(new SetCardInfo("Vexing Shusher", 2, Rarity.RARE, mage.cards.v.VexingShusher.class));
    }

}
