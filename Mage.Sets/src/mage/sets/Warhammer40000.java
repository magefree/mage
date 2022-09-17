package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Warhammer40000 extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Vanguard Suppressor");

    private static final Warhammer40000 instance = new Warhammer40000();

    public static Warhammer40000 getInstance() {
        return instance;
    }

    private Warhammer40000() {
        super("Warhammer 40,000", "40K", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abaddon the Despoiler", 2, Rarity.MYTHIC, mage.cards.a.AbaddonTheDespoiler.class));
        cards.add(new SetCardInfo("Aberrant", 86, Rarity.UNCOMMON, mage.cards.a.Aberrant.class));
        cards.add(new SetCardInfo("Abundance", 210, Rarity.RARE, mage.cards.a.Abundance.class));
        cards.add(new SetCardInfo("Aetherize", 191, Rarity.UNCOMMON, mage.cards.a.Aetherize.class));
        cards.add(new SetCardInfo("Arcane Signet", 227, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Assault Suit", 230, Rarity.UNCOMMON, mage.cards.a.AssaultSuit.class));
        cards.add(new SetCardInfo("Atalan Jackal", 105, Rarity.RARE, mage.cards.a.AtalanJackal.class));
        cards.add(new SetCardInfo("Barren Moor", 266, Rarity.UNCOMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Be'lakor, the Dark Master", 6, Rarity.MYTHIC, mage.cards.b.BelakorTheDarkMaster.class));
        cards.add(new SetCardInfo("Bile Blight", 195, Rarity.UNCOMMON, mage.cards.b.BileBlight.class));
        cards.add(new SetCardInfo("Bituminous Blast", 221, Rarity.UNCOMMON, mage.cards.b.BituminousBlast.class));
        cards.add(new SetCardInfo("Blasphemous Act", 204, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blight Grenade", 31, Rarity.RARE, mage.cards.b.BlightGrenade.class));
        cards.add(new SetCardInfo("Blood for the Blood God!", 108, Rarity.RARE, mage.cards.b.BloodForTheBloodGod.class));
        cards.add(new SetCardInfo("Bloodcrusher of Khorne", 72, Rarity.UNCOMMON, mage.cards.b.BloodcrusherOfKhorne.class));
        cards.add(new SetCardInfo("Brainstorm", 192, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Bred for the Hunt", 222, Rarity.UNCOMMON, mage.cards.b.BredForTheHunt.class));
        cards.add(new SetCardInfo("Broodlord", 89, Rarity.RARE, mage.cards.b.Broodlord.class));
        cards.add(new SetCardInfo("Caged Sun", 231, Rarity.RARE, mage.cards.c.CagedSun.class));
        cards.add(new SetCardInfo("Canoptek Spyder", 151, Rarity.RARE, mage.cards.c.CanoptekSpyder.class));
        cards.add(new SetCardInfo("Cave of Temptation", 267, Rarity.COMMON, mage.cards.c.CaveOfTemptation.class));
        cards.add(new SetCardInfo("Chaos Defiler", 110, Rarity.RARE, mage.cards.c.ChaosDefiler.class));
        cards.add(new SetCardInfo("Chaos Warp", 205, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Choked Estuary", 268, Rarity.RARE, mage.cards.c.ChokedEstuary.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 232, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Cinder Glade", 269, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Command Tower", 270, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 235, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Cranial Plating", 236, Rarity.UNCOMMON, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 273, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Cryptothrall", 155, Rarity.RARE, mage.cards.c.Cryptothrall.class));
        cards.add(new SetCardInfo("Cultivate", 211, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Dark Ritual", 196, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Darkness", 197, Rarity.COMMON, mage.cards.d.Darkness.class));
        cards.add(new SetCardInfo("Death's Presence", 212, Rarity.RARE, mage.cards.d.DeathsPresence.class));
        cards.add(new SetCardInfo("Deathleaper, Terror Weapon", 115, Rarity.RARE, mage.cards.d.DeathleaperTerrorWeapon.class));
        cards.add(new SetCardInfo("Decree of Pain", 198, Rarity.RARE, mage.cards.d.DecreeOfPain.class));
        cards.add(new SetCardInfo("Defile", 199, Rarity.UNCOMMON, mage.cards.d.Defile.class));
        cards.add(new SetCardInfo("Deny Reality", 223, Rarity.COMMON, mage.cards.d.DenyReality.class));
        cards.add(new SetCardInfo("Dismal Backwater", 276, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dread Return", 200, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Endless Atlas", 237, Rarity.RARE, mage.cards.e.EndlessAtlas.class));
        cards.add(new SetCardInfo("Evolving Wilds", 277, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exalted Flamer of Tzeentch", 119, Rarity.RARE, mage.cards.e.ExaltedFlamerOfTzeentch.class));
        cards.add(new SetCardInfo("Exocrine", 76, Rarity.RARE, mage.cards.e.Exocrine.class));
        cards.add(new SetCardInfo("Exotic Orchard", 278, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Explore", 213, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Fabricate", 181, Rarity.RARE, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Farseek", 214, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Flayed One", 34, Rarity.UNCOMMON, mage.cards.f.FlayedOne.class));
        cards.add(new SetCardInfo("Forest", 317, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Cave", 280, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Frontier Bivouac", 281, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Game Trail", 282, Rarity.RARE, mage.cards.g.GameTrail.class));
        cards.add(new SetCardInfo("Gargoyle Flock", 123, Rarity.RARE, mage.cards.g.GargoyleFlock.class));
        cards.add(new SetCardInfo("Gilded Lotus", 239, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Goliath Truck", 158, Rarity.UNCOMMON, mage.cards.g.GoliathTruck.class));
        cards.add(new SetCardInfo("Great Unclean One", 35, Rarity.RARE, mage.cards.g.GreatUncleanOne.class));
        cards.add(new SetCardInfo("Hardened Scales", 215, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Harrow", 216, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Herald of Slaanesh", 77, Rarity.UNCOMMON, mage.cards.h.HeraldOfSlaanesh.class));
        cards.add(new SetCardInfo("Herald's Horn", 241, Rarity.UNCOMMON, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Heralds of Tzeentch", 23, Rarity.UNCOMMON, mage.cards.h.HeraldsOfTzeentch.class));
        cards.add(new SetCardInfo("Hormagaunt Horde", 93, Rarity.RARE, mage.cards.h.HormagauntHorde.class));
        cards.add(new SetCardInfo("Hull Breach", 224, Rarity.UNCOMMON, mage.cards.h.HullBreach.class));
        cards.add(new SetCardInfo("Icon of Ancestry", 242, Rarity.RARE, mage.cards.i.IconOfAncestry.class));
        cards.add(new SetCardInfo("Imotekh the Stormlord", 5, Rarity.MYTHIC, mage.cards.i.ImotekhTheStormlord.class));
        cards.add(new SetCardInfo("Inquisitor Greyfax", 3, Rarity.MYTHIC, mage.cards.i.InquisitorGreyfax.class));
        cards.add(new SetCardInfo("Inspiring Call", 217, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Island", 308, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kill! Maim! Burn!", 128, Rarity.RARE, mage.cards.k.KillMaimBurn.class));
        cards.add(new SetCardInfo("Let the Galaxy Burn", 81, Rarity.RARE, mage.cards.l.LetTheGalaxyBurn.class));
        cards.add(new SetCardInfo("Lictor", 94, Rarity.RARE, mage.cards.l.Lictor.class));
        cards.add(new SetCardInfo("Living Death", 202, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Lokhust Heavy Destroyer", 38, Rarity.RARE, mage.cards.l.LokhustHeavyDestroyer.class));
        cards.add(new SetCardInfo("Lord of Change", 24, Rarity.RARE, mage.cards.l.LordOfChange.class));
        cards.add(new SetCardInfo("Lychguard", 39, Rarity.RARE, mage.cards.l.Lychguard.class));
        cards.add(new SetCardInfo("Mind Stone", 245, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Molten Slagheap", 284, Rarity.UNCOMMON, mage.cards.m.MoltenSlagheap.class));
        cards.add(new SetCardInfo("Mountain", 315, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutilate", 203, Rarity.RARE, mage.cards.m.Mutilate.class));
        cards.add(new SetCardInfo("Mystic Forge", 246, Rarity.RARE, mage.cards.m.MysticForge.class));
        cards.add(new SetCardInfo("New Horizons", 218, Rarity.COMMON, mage.cards.n.NewHorizons.class));
        cards.add(new SetCardInfo("Night Scythe", 162, Rarity.UNCOMMON, mage.cards.n.NightScythe.class));
        cards.add(new SetCardInfo("Noise Marine", 82, Rarity.UNCOMMON, mage.cards.n.NoiseMarine.class));
        cards.add(new SetCardInfo("Old One Eye", 96, Rarity.RARE, mage.cards.o.OldOneEye.class));
        cards.add(new SetCardInfo("Opal Palace", 286, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Overgrowth", 219, Rarity.COMMON, mage.cards.o.Overgrowth.class));
        cards.add(new SetCardInfo("Past in Flames", 206, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Path of Ancestry", 287, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Plasmancer", 48, Rarity.UNCOMMON, mage.cards.p.Plasmancer.class));
        cards.add(new SetCardInfo("Purestrain Genestealer", 97, Rarity.UNCOMMON, mage.cards.p.PurestrainGenestealer.class));
        cards.add(new SetCardInfo("Rampant Growth", 220, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Reliquary Tower", 291, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Reverberate", 207, Rarity.RARE, mage.cards.r.Reverberate.class));
        cards.add(new SetCardInfo("Royal Warden", 52, Rarity.RARE, mage.cards.r.RoyalWarden.class));
        cards.add(new SetCardInfo("Rugged Highlands", 292, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Screamer-Killer", 84, Rarity.RARE, mage.cards.s.ScreamerKiller.class));
        cards.add(new SetCardInfo("Skorpekh Destroyer", 57, Rarity.UNCOMMON, mage.cards.s.SkorpekhDestroyer.class));
        cards.add(new SetCardInfo("Skorpekh Lord", 58, Rarity.RARE, mage.cards.s.SkorpekhLord.class));
        cards.add(new SetCardInfo("Sol Ring", 249, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Starstorm", 208, Rarity.RARE, mage.cards.s.Starstorm.class));
        cards.add(new SetCardInfo("Sunken Hollow", 295, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Swamp", 310, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 296, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Szarekh, the Silent King", 1, Rarity.MYTHIC, mage.cards.s.SzarekhTheSilentKing.class));
        cards.add(new SetCardInfo("Talisman of Creativity", 253, Rarity.UNCOMMON, mage.cards.t.TalismanOfCreativity.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 255, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Hierarchy", 256, Rarity.UNCOMMON, mage.cards.t.TalismanOfHierarchy.class));
        cards.add(new SetCardInfo("Talisman of Indulgence", 257, Rarity.UNCOMMON, mage.cards.t.TalismanOfIndulgence.class));
        cards.add(new SetCardInfo("Temple of Abandon", 297, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 298, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Mystery", 299, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of the False God", 300, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Termagant Swarm", 99, Rarity.RARE, mage.cards.t.TermagantSwarm.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 301, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Tervigon", 100, Rarity.RARE, mage.cards.t.Tervigon.class));
        cards.add(new SetCardInfo("The Swarmlord", 4, Rarity.MYTHIC, mage.cards.t.TheSwarmlord.class));
        cards.add(new SetCardInfo("Their Name Is Death", 62, Rarity.RARE, mage.cards.t.TheirNameIsDeath.class));
        cards.add(new SetCardInfo("Thornwood Falls", 302, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thought Vessel", 259, Rarity.COMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Trygon Prime", 143, Rarity.UNCOMMON, mage.cards.t.TrygonPrime.class));
        cards.add(new SetCardInfo("Tyranid Invasion", 102, Rarity.UNCOMMON, mage.cards.t.TyranidInvasion.class));
        cards.add(new SetCardInfo("Tyranid Prime", 145, Rarity.RARE, mage.cards.t.TyranidPrime.class));
        cards.add(new SetCardInfo("Tyrant Guard", 103, Rarity.RARE, mage.cards.t.TyrantGuard.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 304, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Vanguard Suppressor", 27, Rarity.RARE, mage.cards.v.VanguardSuppressor.class));
        cards.add(new SetCardInfo("Venomcrawler", 68, Rarity.RARE, mage.cards.v.Venomcrawler.class));
        cards.add(new SetCardInfo("Venomthrope", 147, Rarity.UNCOMMON, mage.cards.v.Venomthrope.class));
        cards.add(new SetCardInfo("Warstorm Surge", 209, Rarity.RARE, mage.cards.w.WarstormSurge.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 261, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Winged Hive Tyrant", 148, Rarity.RARE, mage.cards.w.WingedHiveTyrant.class));
        cards.add(new SetCardInfo("Worn Powerstone", 263, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Zoanthrope", 149, Rarity.RARE, mage.cards.z.Zoanthrope.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }
}
