
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksBlessedVsCursed extends ExpansionSet {

    private static final DuelDecksBlessedVsCursed instance = new DuelDecksBlessedVsCursed();

    public static DuelDecksBlessedVsCursed getInstance() {
        return instance;
    }

    private DuelDecksBlessedVsCursed() {
        super("Duel Decks: Blessed vs. Cursed", "DDQ", ExpansionSet.buildDate(2016, 2, 26), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abattoir Ghoul", 50, Rarity.UNCOMMON, mage.cards.a.AbattoirGhoul.class));
        cards.add(new SetCardInfo("Appetite for Brains", 51, Rarity.UNCOMMON, mage.cards.a.AppetiteForBrains.class));
        cards.add(new SetCardInfo("Barter in Blood", 52, Rarity.UNCOMMON, mage.cards.b.BarterInBlood.class));
        cards.add(new SetCardInfo("Bonds of Faith", 2, Rarity.COMMON, mage.cards.b.BondsOfFaith.class));
        cards.add(new SetCardInfo("Butcher Ghoul", 53, Rarity.COMMON, mage.cards.b.ButcherGhoul.class));
        cards.add(new SetCardInfo("Butcher's Cleaver", 31, Rarity.UNCOMMON, mage.cards.b.ButchersCleaver.class));
        cards.add(new SetCardInfo("Captain of the Mists", 24, Rarity.RARE, mage.cards.c.CaptainOfTheMists.class));
        cards.add(new SetCardInfo("Cathedral Sanctifier", 3, Rarity.COMMON, mage.cards.c.CathedralSanctifier.class));
        cards.add(new SetCardInfo("Champion of the Parish", 4, Rarity.RARE, mage.cards.c.ChampionOfTheParish.class));
        cards.add(new SetCardInfo("Chapel Geist", 5, Rarity.COMMON, mage.cards.c.ChapelGeist.class));
        cards.add(new SetCardInfo("Cobbled Wings", 69, Rarity.COMMON, mage.cards.c.CobbledWings.class));
        cards.add(new SetCardInfo("Compelling Deterrence", 42, Rarity.UNCOMMON, mage.cards.c.CompellingDeterrence.class));
        cards.add(new SetCardInfo("Dearly Departed", 6, Rarity.RARE, mage.cards.d.DearlyDeparted.class));
        cards.add(new SetCardInfo("Diregraf Captain", 68, Rarity.UNCOMMON, mage.cards.d.DiregrafCaptain.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 54, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Dismal Backwater", 70, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Doomed Traveler", 7, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Dread Return", 55, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Driver of the Dead", 56, Rarity.COMMON, mage.cards.d.DriverOfTheDead.class));
        cards.add(new SetCardInfo("Eerie Interlude", 8, Rarity.RARE, mage.cards.e.EerieInterlude.class));
        cards.add(new SetCardInfo("Elder Cathar", 9, Rarity.COMMON, mage.cards.e.ElderCathar.class));
        cards.add(new SetCardInfo("Emancipation Angel", 10, Rarity.UNCOMMON, mage.cards.e.EmancipationAngel.class));
        cards.add(new SetCardInfo("Falkenrath Noble", 57, Rarity.UNCOMMON, mage.cards.f.FalkenrathNoble.class));
        cards.add(new SetCardInfo("Fiend Hunter", 11, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 43, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Gather the Townsfolk", 12, Rarity.COMMON, mage.cards.g.GatherTheTownsfolk.class));
        cards.add(new SetCardInfo("Geist of Saint Traft", 1, Rarity.MYTHIC, mage.cards.g.GeistOfSaintTraft.class));
        cards.add(new SetCardInfo("Ghoulraiser", 58, Rarity.COMMON, mage.cards.g.Ghoulraiser.class));
        cards.add(new SetCardInfo("Goldnight Redeemer", 13, Rarity.UNCOMMON, mage.cards.g.GoldnightRedeemer.class));
        cards.add(new SetCardInfo("Gravecrawler", 59, Rarity.RARE, mage.cards.g.Gravecrawler.class));
        cards.add(new SetCardInfo("Gryff Vanguard", 25, Rarity.COMMON, mage.cards.g.GryffVanguard.class));
        cards.add(new SetCardInfo("Harvester of Souls", 60, Rarity.RARE, mage.cards.h.HarvesterOfSouls.class));
        cards.add(new SetCardInfo("Havengul Runebinder", 44, Rarity.RARE, mage.cards.h.HavengulRunebinder.class));
        cards.add(new SetCardInfo("Human Frailty", 61, Rarity.UNCOMMON, mage.cards.h.HumanFrailty.class));
        cards.add(new SetCardInfo("Increasing Devotion", 14, Rarity.RARE, mage.cards.i.IncreasingDevotion.class));
        cards.add(new SetCardInfo("Island", 35, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 36, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 37, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 71, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 72, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 73, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Makeshift Mauler", 45, Rarity.COMMON, mage.cards.m.MakeshiftMauler.class));
        cards.add(new SetCardInfo("Mindwrack Demon", 41, Rarity.MYTHIC, mage.cards.m.MindwrackDemon.class));
        cards.add(new SetCardInfo("Mist Raven", 26, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Moan of the Unhallowed", 62, Rarity.UNCOMMON, mage.cards.m.MoanOfTheUnhallowed.class));
        cards.add(new SetCardInfo("Momentary Blink", 15, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Moorland Inquisitor", 16, Rarity.COMMON, mage.cards.m.MoorlandInquisitor.class));
        cards.add(new SetCardInfo("Nephalia Smuggler", 27, Rarity.UNCOMMON, mage.cards.n.NephaliaSmuggler.class));
        cards.add(new SetCardInfo("Plains", 38, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 39, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 40, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pore Over the Pages", 28, Rarity.UNCOMMON, mage.cards.p.PoreOverThePages.class));
        cards.add(new SetCardInfo("Rebuke", 17, Rarity.COMMON, mage.cards.r.Rebuke.class));
        cards.add(new SetCardInfo("Relentless Skaabs", 46, Rarity.UNCOMMON, mage.cards.r.RelentlessSkaabs.class));
        cards.add(new SetCardInfo("Scrapskin Drake", 47, Rarity.COMMON, mage.cards.s.ScrapskinDrake.class));
        cards.add(new SetCardInfo("Screeching Skaab", 48, Rarity.COMMON, mage.cards.s.ScreechingSkaab.class));
        cards.add(new SetCardInfo("Seraph Sanctuary", 33, Rarity.COMMON, mage.cards.s.SeraphSanctuary.class));
        cards.add(new SetCardInfo("Sever the Bloodline", 63, Rarity.RARE, mage.cards.s.SeverTheBloodline.class));
        cards.add(new SetCardInfo("Sharpened Pitchfork", 32, Rarity.UNCOMMON, mage.cards.s.SharpenedPitchfork.class));
        cards.add(new SetCardInfo("Slayer of the Wicked", 18, Rarity.UNCOMMON, mage.cards.s.SlayerOfTheWicked.class));
        cards.add(new SetCardInfo("Spectral Gateguards", 19, Rarity.COMMON, mage.cards.s.SpectralGateguards.class));
        cards.add(new SetCardInfo("Stitched Drake", 49, Rarity.COMMON, mage.cards.s.StitchedDrake.class));
        cards.add(new SetCardInfo("Swamp", 74, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 75, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 76, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tandem Lookout", 29, Rarity.UNCOMMON, mage.cards.t.TandemLookout.class));
        cards.add(new SetCardInfo("Thraben Heretic", 20, Rarity.UNCOMMON, mage.cards.t.ThrabenHeretic.class));
        cards.add(new SetCardInfo("Tooth Collector", 64, Rarity.UNCOMMON, mage.cards.t.ToothCollector.class));
        cards.add(new SetCardInfo("Topplegeist", 21, Rarity.UNCOMMON, mage.cards.t.Topplegeist.class));
        cards.add(new SetCardInfo("Tower Geist", 30, Rarity.UNCOMMON, mage.cards.t.TowerGeist.class));
        cards.add(new SetCardInfo("Tranquil Cove", 34, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Tribute to Hunger", 65, Rarity.UNCOMMON, mage.cards.t.TributeToHunger.class));
        cards.add(new SetCardInfo("Unbreathing Horde", 66, Rarity.RARE, mage.cards.u.UnbreathingHorde.class));
        cards.add(new SetCardInfo("Victim of Night", 67, Rarity.COMMON, mage.cards.v.VictimOfNight.class));
        cards.add(new SetCardInfo("Village Bell-Ringer", 22, Rarity.COMMON, mage.cards.v.VillageBellRinger.class));
        cards.add(new SetCardInfo("Voice of the Provinces", 23, Rarity.COMMON, mage.cards.v.VoiceOfTheProvinces.class));
    }
}
