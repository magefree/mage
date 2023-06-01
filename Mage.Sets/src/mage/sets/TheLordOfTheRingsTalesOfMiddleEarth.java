package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class TheLordOfTheRingsTalesOfMiddleEarth extends ExpansionSet {

    private static final TheLordOfTheRingsTalesOfMiddleEarth instance = new TheLordOfTheRingsTalesOfMiddleEarth();

    public static TheLordOfTheRingsTalesOfMiddleEarth getInstance() {
        return instance;
    }

    private TheLordOfTheRingsTalesOfMiddleEarth() {
        super("The Lord of the Rings: Tales of Middle-earth", "LTR", ExpansionSet.buildDate(2023, 6, 23), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "The Lord of the Rings: Tales of Middle-earth";
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Aragorn and Arwen, Wed", 287, Rarity.MYTHIC, mage.cards.a.AragornAndArwenWed.class));
        cards.add(new SetCardInfo("Aragorn, the Uniter", 192, Rarity.MYTHIC, mage.cards.a.AragornTheUniter.class));
        cards.add(new SetCardInfo("Arwen's Gift", 39, Rarity.COMMON, mage.cards.a.ArwensGift.class));
        cards.add(new SetCardInfo("Bilbo, Retired Burglar", 196, Rarity.UNCOMMON, mage.cards.b.BilboRetiredBurglar.class));
        cards.add(new SetCardInfo("Bombadil's Song", 154, Rarity.COMMON, mage.cards.b.BombadilsSong.class));
        cards.add(new SetCardInfo("Book of Mazarbul", 116, Rarity.UNCOMMON, mage.cards.b.BookOfMazarbul.class));
        cards.add(new SetCardInfo("Call of the Ring", 79, Rarity.RARE, mage.cards.c.CallOfTheRing.class));
        cards.add(new SetCardInfo("Cast into the Fire", 118, Rarity.COMMON, mage.cards.c.CastIntoTheFire.class));
        cards.add(new SetCardInfo("Council's Deliberation", 48, Rarity.UNCOMMON, mage.cards.c.CouncilsDeliberation.class));
        cards.add(new SetCardInfo("Dunland Crebain", 82, Rarity.COMMON, mage.cards.d.DunlandCrebain.class));
        cards.add(new SetCardInfo("Dunedain Blade", 6, Rarity.COMMON, mage.cards.d.DunedainBlade.class));
        cards.add(new SetCardInfo("Easterling Vanguard", 83, Rarity.COMMON, mage.cards.e.EasterlingVanguard.class));
        cards.add(new SetCardInfo("Foray of Orcs", 128, Rarity.UNCOMMON, mage.cards.f.ForayOfOrcs.class));
        cards.add(new SetCardInfo("Forest", 270, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frodo Baggins", 205, Rarity.UNCOMMON, mage.cards.f.FrodoBaggins.class));
        cards.add(new SetCardInfo("Frodo, Determined Hero", 388, Rarity.RARE, mage.cards.f.FrodoDeterminedHero.class));
        cards.add(new SetCardInfo("Frodo, Sauron's Bane", 18, Rarity.RARE, mage.cards.f.FrodoSauronsBane.class));
        cards.add(new SetCardInfo("Galadhrim Bow", 167, Rarity.COMMON, mage.cards.g.GaladhrimBow.class));
        cards.add(new SetCardInfo("Galadriel, Gift-Giver", 296, Rarity.RARE, mage.cards.g.GaladrielGiftGiver.class));
        cards.add(new SetCardInfo("Gandalf the Grey", 207, Rarity.RARE, mage.cards.g.GandalfTheGrey.class));
        cards.add(new SetCardInfo("Gandalf, Friend of the Shire", 50, Rarity.UNCOMMON, mage.cards.g.GandalfFriendOfTheShire.class));
        cards.add(new SetCardInfo("Generous Ent", 169, Rarity.COMMON, mage.cards.g.GenerousEnt.class));
        cards.add(new SetCardInfo("Goblin Assailant", 295, Rarity.COMMON, mage.cards.g.GoblinAssailant.class));
        cards.add(new SetCardInfo("Gollum's Bite", 85, Rarity.UNCOMMON, mage.cards.g.GollumsBite.class));
        cards.add(new SetCardInfo("Gollum, Patient Plotter", 84, Rarity.UNCOMMON, mage.cards.g.GollumPatientPlotter.class));
        cards.add(new SetCardInfo("Gothmog, Morgul Lieutenant", 87, Rarity.UNCOMMON, mage.cards.g.GothmogMorgulLieutenant.class));
        cards.add(new SetCardInfo("Grond, the Gatebreaker", 89, Rarity.UNCOMMON, mage.cards.g.GrondTheGatebreaker.class));
        cards.add(new SetCardInfo("Island", 264, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knight of the Keep", 291, Rarity.COMMON, mage.cards.k.KnightOfTheKeep.class));
        cards.add(new SetCardInfo("Knights of Dol Amroth", 59, Rarity.COMMON, mage.cards.k.KnightsOfDolAmroth.class));
        cards.add(new SetCardInfo("Lobelia Sackville-Baggins", 93, Rarity.RARE, mage.cards.l.LobeliaSackvilleBaggins.class));
        cards.add(new SetCardInfo("Long List of the Ents", 174, Rarity.UNCOMMON, mage.cards.l.LongListOfTheEnts.class));
        cards.add(new SetCardInfo("Many Partings", 176, Rarity.COMMON, mage.cards.m.ManyPartings.class));
        cards.add(new SetCardInfo("Mauhur, Uruk-hai Captain", 214, Rarity.UNCOMMON, mage.cards.m.MauhurUrukHaiCaptain.class));
        cards.add(new SetCardInfo("Mount Doom", 258, Rarity.MYTHIC, mage.cards.m.MountDoom.class));
        cards.add(new SetCardInfo("Mountain", 268, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nazgul", 100, Rarity.UNCOMMON, mage.cards.n.Nazgul.class));
        cards.add(new SetCardInfo("Pippin's Bravery", 182, Rarity.COMMON, mage.cards.p.PippinsBravery.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prince Imrahil the Fair", 219, Rarity.UNCOMMON, mage.cards.p.PrinceImrahilTheFair.class));
        cards.add(new SetCardInfo("Protector of Gondor", 25, Rarity.COMMON, mage.cards.p.ProtectorOfGondor.class));
        cards.add(new SetCardInfo("Quickbeam, Upstart Ent", 183, Rarity.UNCOMMON, mage.cards.q.QuickbeamUpstartEnt.class));
        cards.add(new SetCardInfo("Reprieve", 26, Rarity.UNCOMMON, mage.cards.r.Reprieve.class));
        cards.add(new SetCardInfo("Ringsight", 220, Rarity.UNCOMMON, mage.cards.r.Ringsight.class));
        cards.add(new SetCardInfo("Rising of the Day", 145, Rarity.UNCOMMON, mage.cards.r.RisingOfTheDay.class));
        cards.add(new SetCardInfo("Samwise Gamgee", 222, Rarity.RARE, mage.cards.s.SamwiseGamgee.class));
        cards.add(new SetCardInfo("Samwise the Stouthearted", 28, Rarity.UNCOMMON, mage.cards.s.SamwiseTheStouthearted.class));
        cards.add(new SetCardInfo("Saruman the White", 67, Rarity.UNCOMMON, mage.cards.s.SarumanTheWhite.class));
        cards.add(new SetCardInfo("Saruman's Trickery", 68, Rarity.UNCOMMON, mage.cards.s.SarumansTrickery.class));
        cards.add(new SetCardInfo("Sauron, the Lidless Eye", 288, Rarity.MYTHIC, mage.cards.s.SauronTheLidlessEye.class));
        cards.add(new SetCardInfo("Sauron, the Necromancer", 106, Rarity.RARE, mage.cards.s.SauronTheNecromancer.class));
        cards.add(new SetCardInfo("Shelob's Ambush", 108, Rarity.COMMON, mage.cards.s.ShelobsAmbush.class));
        cards.add(new SetCardInfo("Shire Terrace", 261, Rarity.COMMON, mage.cards.s.ShireTerrace.class));
        cards.add(new SetCardInfo("Snarling Warg", 109, Rarity.COMMON, mage.cards.s.SnarlingWarg.class));
        cards.add(new SetCardInfo("Stew the Coneys", 189, Rarity.UNCOMMON, mage.cards.s.StewTheConeys.class));
        cards.add(new SetCardInfo("Swamp", 266, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarming of Moria", 150, Rarity.COMMON, mage.cards.s.SwarmingOfMoria.class));
        cards.add(new SetCardInfo("The Balrog, Flame of Udun", 395, Rarity.RARE, mage.cards.t.TheBalrogFlameOfUdun.class));
        cards.add(new SetCardInfo("The One Ring", 246, Rarity.MYTHIC, mage.cards.t.TheOneRing.class));
        cards.add(new SetCardInfo("The Shire", 260, Rarity.RARE, mage.cards.t.TheShire.class));
        cards.add(new SetCardInfo("Tom Bombadil", 234, Rarity.MYTHIC, mage.cards.t.TomBombadil.class));
        cards.add(new SetCardInfo("Trailblazer's Boots", 398, Rarity.RARE, mage.cards.t.TrailblazersBoots.class));
        cards.add(new SetCardInfo("Westfold Rider", 37, Rarity.COMMON, mage.cards.w.WestfoldRider.class));
        cards.add(new SetCardInfo("Wizard's Rockets", 252, Rarity.COMMON, mage.cards.w.WizardsRockets.class));
        cards.add(new SetCardInfo("Wose Pathfinder", 190, Rarity.COMMON, mage.cards.w.WosePathfinder.class));
        cards.add(new SetCardInfo("You Cannot Pass!", 38, Rarity.UNCOMMON, mage.cards.y.YouCannotPass.class));
    }
}
