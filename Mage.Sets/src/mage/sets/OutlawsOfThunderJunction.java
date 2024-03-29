package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class OutlawsOfThunderJunction extends ExpansionSet {

    // This is a list of cards with the plot mechanic, it will be removed when the mechanic is implemented
    private static final List<String> unfinished = Arrays.asList("Aloe Alchemist", "Aven Interrupter", "Blacksnag Buzzard", "Cunning Coyote", "Fblthp, Lost on the Range", "Jace Reawakened", "Loan Shark", "Longhorn Sharpshooter", "Make Your Own Luck", "Outcaster Trailblazer", "Plan the Heist", "Rictus Robber", "Slickshot Lockpicker", "Slickshot Show-Off", "Step Between Worlds", "Tumbleweed Rising", "Unscrupulous Contractor");
    private static final OutlawsOfThunderJunction instance = new OutlawsOfThunderJunction();

    public static OutlawsOfThunderJunction getInstance() {
        return instance;
    }

    private OutlawsOfThunderJunction() {
        super("Outlaws of Thunder Junction", "OTJ", ExpansionSet.buildDate(2024, 4, 19), SetType.EXPANSION);
        this.blockName = "Outlaws of Thunder Junction"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Abraded Bluffs", 251, Rarity.COMMON, mage.cards.a.AbradedBluffs.class));
        cards.add(new SetCardInfo("Archangel of Tithes", 2, Rarity.MYTHIC, mage.cards.a.ArchangelOfTithes.class));
        cards.add(new SetCardInfo("Arid Archway", 252, Rarity.UNCOMMON, mage.cards.a.AridArchway.class));
        cards.add(new SetCardInfo("Armored Armadillo", 3, Rarity.COMMON, mage.cards.a.ArmoredArmadillo.class));
        cards.add(new SetCardInfo("At Knifepoint", 193, Rarity.UNCOMMON, mage.cards.a.AtKnifepoint.class));
        cards.add(new SetCardInfo("Blooming Marsh", 266, Rarity.RARE, mage.cards.b.BloomingMarsh.class));
        cards.add(new SetCardInfo("Botanical Sanctum", 267, Rarity.RARE, mage.cards.b.BotanicalSanctum.class));
        cards.add(new SetCardInfo("Bovine Intervention", 6, Rarity.UNCOMMON, mage.cards.b.BovineIntervention.class));
        cards.add(new SetCardInfo("Bristlepack Sentry", 156, Rarity.COMMON, mage.cards.b.BristlepackSentry.class));
        cards.add(new SetCardInfo("Bristling Backwoods", 253, Rarity.COMMON, mage.cards.b.BristlingBackwoods.class));
        cards.add(new SetCardInfo("Bristly Bill, Spine Sower", 157, Rarity.MYTHIC, mage.cards.b.BristlyBillSpineSower.class));
        cards.add(new SetCardInfo("Bruse Tarl, Roving Rancher", 198, Rarity.RARE, mage.cards.b.BruseTarlRovingRancher.class));
        cards.add(new SetCardInfo("Cactarantula", 158, Rarity.COMMON, mage.cards.c.Cactarantula.class));
        cards.add(new SetCardInfo("Colossal Rattlewurm", 159, Rarity.RARE, mage.cards.c.ColossalRattlewurm.class));
        cards.add(new SetCardInfo("Concealed Courtyard", 268, Rarity.RARE, mage.cards.c.ConcealedCourtyard.class));
        cards.add(new SetCardInfo("Creosote Heath", 255, Rarity.COMMON, mage.cards.c.CreosoteHeath.class));
        cards.add(new SetCardInfo("Cunning Coyote", 118, Rarity.UNCOMMON, mage.cards.c.CunningCoyote.class));
        cards.add(new SetCardInfo("Desperate Bloodseeker", 86, Rarity.COMMON, mage.cards.d.DesperateBloodseeker.class));
        cards.add(new SetCardInfo("Discerning Peddler", 121, Rarity.COMMON, mage.cards.d.DiscerningPeddler.class));
        cards.add(new SetCardInfo("Double Down", 44, Rarity.MYTHIC, mage.cards.d.DoubleDown.class));
        cards.add(new SetCardInfo("Duelist of the Mind", 45, Rarity.RARE, mage.cards.d.DuelistOfTheMind.class));
        cards.add(new SetCardInfo("Eroded Canyon", 256, Rarity.COMMON, mage.cards.e.ErodedCanyon.class));
        cards.add(new SetCardInfo("Festering Gulch", 257, Rarity.COMMON, mage.cards.f.FesteringGulch.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forlorn Flats", 258, Rarity.COMMON, mage.cards.f.ForlornFlats.class));
        cards.add(new SetCardInfo("Form a Posse", 204, Rarity.UNCOMMON, mage.cards.f.FormAPosse.class));
        cards.add(new SetCardInfo("Freestrider Lookout", 163, Rarity.RARE, mage.cards.f.FreestriderLookout.class));
        cards.add(new SetCardInfo("Frontier Seeker", 13, Rarity.UNCOMMON, mage.cards.f.FrontierSeeker.class));
        cards.add(new SetCardInfo("Hardbristle Bandit", 168, Rarity.COMMON, mage.cards.h.HardbristleBandit.class));
        cards.add(new SetCardInfo("Hell to Pay", 126, Rarity.RARE, mage.cards.h.HellToPay.class));
        cards.add(new SetCardInfo("Hellspur Brute", 127, Rarity.UNCOMMON, mage.cards.h.HellspurBrute.class));
        cards.add(new SetCardInfo("High Noon", 15, Rarity.RARE, mage.cards.h.HighNoon.class));
        cards.add(new SetCardInfo("Holy Cow", 16, Rarity.COMMON, mage.cards.h.HolyCow.class));
        cards.add(new SetCardInfo("Honest Rutstein", 207, Rarity.UNCOMMON, mage.cards.h.HonestRutstein.class));
        cards.add(new SetCardInfo("Inspiring Vantage", 269, Rarity.RARE, mage.cards.i.InspiringVantage.class));
        cards.add(new SetCardInfo("Intimidation Campaign", 208, Rarity.UNCOMMON, mage.cards.i.IntimidationCampaign.class));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jagged Barrens", 259, Rarity.COMMON, mage.cards.j.JaggedBarrens.class));
        cards.add(new SetCardInfo("Jolene, Plundering Pugilist", 210, Rarity.UNCOMMON, mage.cards.j.JolenePlunderingPugilist.class));
        cards.add(new SetCardInfo("Kraum, Violent Cacophony", 214, Rarity.UNCOMMON, mage.cards.k.KraumViolentCacophony.class));
        cards.add(new SetCardInfo("Lavaspur Boots", 243, Rarity.UNCOMMON, mage.cards.l.LavaspurBoots.class));
        cards.add(new SetCardInfo("Loan Shark", 55, Rarity.COMMON, mage.cards.l.LoanShark.class));
        cards.add(new SetCardInfo("Lonely Arroyo", 260, Rarity.COMMON, mage.cards.l.LonelyArroyo.class));
        cards.add(new SetCardInfo("Lush Oasis", 261, Rarity.COMMON, mage.cards.l.LushOasis.class));
        cards.add(new SetCardInfo("Marauding Sphinx", 56, Rarity.UNCOMMON, mage.cards.m.MaraudingSphinx.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, the Ringleader", 223, Rarity.MYTHIC, mage.cards.o.OkoTheRingleader.class));
        cards.add(new SetCardInfo("Outcaster Trailblazer", 173, Rarity.RARE, mage.cards.o.OutcasterTrailblazer.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plan the Heist", 62, Rarity.UNCOMMON, mage.cards.p.PlanTheHeist.class));
        cards.add(new SetCardInfo("Rakish Crew", 99, Rarity.UNCOMMON, mage.cards.r.RakishCrew.class));
        cards.add(new SetCardInfo("Rattleback Apothecary", 100, Rarity.UNCOMMON, mage.cards.r.RattlebackApothecary.class));
        cards.add(new SetCardInfo("Reckless Lackey", 140, Rarity.COMMON, mage.cards.r.RecklessLackey.class));
        cards.add(new SetCardInfo("Resilient Roadrunner", 141, Rarity.UNCOMMON, mage.cards.r.ResilientRoadrunner.class));
        cards.add(new SetCardInfo("Ruthless Lawbringer", 229, Rarity.UNCOMMON, mage.cards.r.RuthlessLawbringer.class));
        cards.add(new SetCardInfo("Scorching Shot", 145, Rarity.UNCOMMON, mage.cards.s.ScorchingShot.class));
        cards.add(new SetCardInfo("Shoot the Sheriff", 106, Rarity.UNCOMMON, mage.cards.s.ShootTheSheriff.class));
        cards.add(new SetCardInfo("Slickshot Show-Off", 146, Rarity.RARE, mage.cards.s.SlickshotShowOff.class));
        cards.add(new SetCardInfo("Soured Springs", 264, Rarity.COMMON, mage.cards.s.SouredSprings.class));
        cards.add(new SetCardInfo("Spirebluff Canal", 270, Rarity.RARE, mage.cards.s.SpirebluffCanal.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror of the Peaks", 149, Rarity.MYTHIC, mage.cards.t.TerrorOfThePeaks.class));
        cards.add(new SetCardInfo("Tomb Trawler", 250, Rarity.UNCOMMON, mage.cards.t.TombTrawler.class));
        cards.add(new SetCardInfo("Unscrupulous Contractor", 112, Rarity.UNCOMMON, mage.cards.u.UnscrupulousContractor.class));
        cards.add(new SetCardInfo("Vial Smasher, Gleeful Grenadier", 235, Rarity.UNCOMMON, mage.cards.v.VialSmasherGleefulGrenadier.class));
        cards.add(new SetCardInfo("Vraska Joins Up", 236, Rarity.RARE, mage.cards.v.VraskaJoinsUp.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }
}
