package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Warhammer40000 extends ExpansionSet {

    private static final Warhammer40000 instance = new Warhammer40000();

    public static Warhammer40000 getInstance() {
        return instance;
    }

    private Warhammer40000() {
        super("Warhammer 40,000", "40K", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abaddon the Despoiler", 2, Rarity.MYTHIC, mage.cards.a.AbaddonTheDespoiler.class));
        cards.add(new SetCardInfo("Abundance", 210, Rarity.RARE, mage.cards.a.Abundance.class));
        cards.add(new SetCardInfo("Aetherize", 191, Rarity.UNCOMMON, mage.cards.a.Aetherize.class));
        cards.add(new SetCardInfo("Arcane Signet", 227, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Atalan Jackal", 105, Rarity.RARE, mage.cards.a.AtalanJackal.class));
        cards.add(new SetCardInfo("Be'lakor, the Dark Master", 6, Rarity.MYTHIC, mage.cards.b.BelakorTheDarkMaster.class));
        cards.add(new SetCardInfo("Bred for the Hunt", 222, Rarity.UNCOMMON, mage.cards.b.BredForTheHunt.class));
        cards.add(new SetCardInfo("Broodlord", 89, Rarity.RARE, mage.cards.b.Broodlord.class));
        cards.add(new SetCardInfo("Cave of Temptation", 267, Rarity.COMMON, mage.cards.c.CaveOfTemptation.class));
        cards.add(new SetCardInfo("Cinder Glade", 269, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Command Tower", 270, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Cultivate", 211, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Death's Presence", 212, Rarity.RARE, mage.cards.d.DeathsPresence.class));
        cards.add(new SetCardInfo("Deathleaper, Terror Weapon", 115, Rarity.RARE, mage.cards.d.DeathleaperTerrorWeapon.class));
        cards.add(new SetCardInfo("Exotic Orchard", 278, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Explore", 213, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Fabricate", 181, Rarity.RARE, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Farseek", 214, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Forest", 317, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frontier Bivouac", 281, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Game Trail", 282, Rarity.RARE, mage.cards.g.GameTrail.class));
        cards.add(new SetCardInfo("Hardened Scales", 215, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Harrow", 216, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Herald's Horn", 241, Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Hull Breach", 224, Rarity.UNCOMMON, mage.cards.h.HullBreach.class));
        cards.add(new SetCardInfo("Icon of Ancestry", 242, Rarity.RARE, mage.cards.i.IconOfAncestry.class));
        cards.add(new SetCardInfo("Imotekh the Stormlord", 5, Rarity.MYTHIC, mage.cards.i.ImotekhTheStormlord.class));
        cards.add(new SetCardInfo("Inquisitor Greyfax", 3, Rarity.MYTHIC, mage.cards.i.InquisitorGreyfax.class));
        cards.add(new SetCardInfo("Inspiring Call", 217, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Island", 309, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 315, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("New Horizons", 218, Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Noise Marine", 82, Rarity.UNCOMMON, mage.cards.n.NoiseMarine.class));
        cards.add(new SetCardInfo("Old One Eye", 96, Rarity.RARE, mage.cards.o.OldOneEye.class));
        cards.add(new SetCardInfo("Opal Palace", 286, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Overgrowth", 219, Rarity.COMMON, mage.cards.o.Overgrowth.class));
        cards.add(new SetCardInfo("Path of Ancestry", 287, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Rampant Growth", 220, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rugged Highlands", 292, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Screamer-Killer", 84, Rarity.RARE, mage.cards.s.ScreamerKiller.class));
        cards.add(new SetCardInfo("Sol Ring", 249, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Starstorm", 208, Rarity.RARE, mage.cards.s.Starstorm.class));
        cards.add(new SetCardInfo("Szarekh, the Silent King", 1, Rarity.MYTHIC, mage.cards.s.SzarekhTheSilentKing.class));
        cards.add(new SetCardInfo("Temple of Abandon", 297, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 298, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Mystery", 299, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("The Swarmlord", 4, Rarity.MYTHIC, mage.cards.t.TheSwarmlord.class));
        cards.add(new SetCardInfo("Thornwood Falls", 302, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Tyranid Prime", 145, Rarity.RARE, mage.cards.t.TyranidPrime.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 304, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Venomthrope", 147, Rarity.UNCOMMON, mage.cards.v.Venomthrope.class));
        cards.add(new SetCardInfo("Zoanthrope", 149, Rarity.RARE, mage.cards.z.Zoanthrope.class));
    }
}
