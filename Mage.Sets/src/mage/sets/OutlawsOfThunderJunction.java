package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class OutlawsOfThunderJunction extends ExpansionSet {

    private static final OutlawsOfThunderJunction instance = new OutlawsOfThunderJunction();

    public static OutlawsOfThunderJunction getInstance() {
        return instance;
    }

    private OutlawsOfThunderJunction() {
        super("Outlaws of Thunder Junction", "OTJ", ExpansionSet.buildDate(2024, 4, 19), SetType.EXPANSION);
        this.blockName = "Outlaws of Thunder Junction"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.maxCardNumberInBooster = 276;

        cards.add(new SetCardInfo("Abraded Bluffs", 251, Rarity.COMMON, mage.cards.a.AbradedBluffs.class));
        cards.add(new SetCardInfo("Akul the Unrepentant", 189, Rarity.RARE, mage.cards.a.AkulTheUnrepentant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akul the Unrepentant", 346, Rarity.RARE, mage.cards.a.AkulTheUnrepentant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aloe Alchemist", 152, Rarity.UNCOMMON, mage.cards.a.AloeAlchemist.class));
        cards.add(new SetCardInfo("Ambush Gigapede", 77, Rarity.COMMON, mage.cards.a.AmbushGigapede.class));
        cards.add(new SetCardInfo("Ankle Biter", 153, Rarity.COMMON, mage.cards.a.AnkleBiter.class));
        cards.add(new SetCardInfo("Annie Flash, the Veteran", 190, Rarity.MYTHIC, mage.cards.a.AnnieFlashTheVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Annie Flash, the Veteran", 291, Rarity.MYTHIC, mage.cards.a.AnnieFlashTheVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Annie Joins Up", 191, Rarity.RARE, mage.cards.a.AnnieJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Annie Joins Up", 347, Rarity.RARE, mage.cards.a.AnnieJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Another Round", 1, Rarity.RARE, mage.cards.a.AnotherRound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Another Round", 307, Rarity.RARE, mage.cards.a.AnotherRound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archangel of Tithes", 2, Rarity.MYTHIC, mage.cards.a.ArchangelOfTithes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archangel of Tithes", 308, Rarity.MYTHIC, mage.cards.a.ArchangelOfTithes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archmage's Newt", 316, Rarity.RARE, mage.cards.a.ArchmagesNewt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archmage's Newt", 39, Rarity.RARE, mage.cards.a.ArchmagesNewt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arid Archway", 252, Rarity.UNCOMMON, mage.cards.a.AridArchway.class));
        cards.add(new SetCardInfo("Armored Armadillo", 3, Rarity.COMMON, mage.cards.a.ArmoredArmadillo.class));
        cards.add(new SetCardInfo("Assimilation Aegis", 192, Rarity.MYTHIC, mage.cards.a.AssimilationAegis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Assimilation Aegis", 348, Rarity.MYTHIC, mage.cards.a.AssimilationAegis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("At Knifepoint", 193, Rarity.UNCOMMON, mage.cards.a.AtKnifepoint.class));
        cards.add(new SetCardInfo("Aven Interrupter", 309, Rarity.RARE, mage.cards.a.AvenInterrupter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aven Interrupter", 4, Rarity.RARE, mage.cards.a.AvenInterrupter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Badlands Revival", 194, Rarity.UNCOMMON, mage.cards.b.BadlandsRevival.class));
        cards.add(new SetCardInfo("Bandit's Haul", 240, Rarity.UNCOMMON, mage.cards.b.BanditsHaul.class));
        cards.add(new SetCardInfo("Baron Bertram Graywater", 195, Rarity.UNCOMMON, mage.cards.b.BaronBertramGraywater.class));
        cards.add(new SetCardInfo("Beastbond Outcaster", 154, Rarity.UNCOMMON, mage.cards.b.BeastbondOutcaster.class));
        cards.add(new SetCardInfo("Betrayal at the Vault", 155, Rarity.UNCOMMON, mage.cards.b.BetrayalAtTheVault.class));
        cards.add(new SetCardInfo("Binding Negotiation", 78, Rarity.UNCOMMON, mage.cards.b.BindingNegotiation.class));
        cards.add(new SetCardInfo("Blacksnag Buzzard", 79, Rarity.COMMON, mage.cards.b.BlacksnagBuzzard.class));
        cards.add(new SetCardInfo("Blood Hustler", 80, Rarity.UNCOMMON, mage.cards.b.BloodHustler.class));
        cards.add(new SetCardInfo("Blooming Marsh", 266, Rarity.RARE, mage.cards.b.BloomingMarsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blooming Marsh", 300, Rarity.RARE, mage.cards.b.BloomingMarsh.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boneyard Desecrator", 81, Rarity.COMMON, mage.cards.b.BoneyardDesecrator.class));
        cards.add(new SetCardInfo("Bonny Pall, Clearcutter", 196, Rarity.RARE, mage.cards.b.BonnyPallClearcutter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonny Pall, Clearcutter", 349, Rarity.RARE, mage.cards.b.BonnyPallClearcutter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boom Box", 241, Rarity.UNCOMMON, mage.cards.b.BoomBox.class));
        cards.add(new SetCardInfo("Botanical Sanctum", 267, Rarity.RARE, mage.cards.b.BotanicalSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Botanical Sanctum", 301, Rarity.RARE, mage.cards.b.BotanicalSanctum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bounding Felidar", 5, Rarity.UNCOMMON, mage.cards.b.BoundingFelidar.class));
        cards.add(new SetCardInfo("Bovine Intervention", 6, Rarity.UNCOMMON, mage.cards.b.BovineIntervention.class));
        cards.add(new SetCardInfo("Breeches, the Blastmaker", 197, Rarity.RARE, mage.cards.b.BreechesTheBlastmaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeches, the Blastmaker", 292, Rarity.RARE, mage.cards.b.BreechesTheBlastmaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bridled Bighorn", 7, Rarity.COMMON, mage.cards.b.BridledBighorn.class));
        cards.add(new SetCardInfo("Brimstone Roundup", 115, Rarity.UNCOMMON, mage.cards.b.BrimstoneRoundup.class));
        cards.add(new SetCardInfo("Bristlepack Sentry", 156, Rarity.COMMON, mage.cards.b.BristlepackSentry.class));
        cards.add(new SetCardInfo("Bristling Backwoods", 253, Rarity.COMMON, mage.cards.b.BristlingBackwoods.class));
        cards.add(new SetCardInfo("Bristly Bill, Spine Sower", 157, Rarity.MYTHIC, mage.cards.b.BristlyBillSpineSower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bristly Bill, Spine Sower", 338, Rarity.MYTHIC, mage.cards.b.BristlyBillSpineSower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bruse Tarl, Roving Rancher", 198, Rarity.RARE, mage.cards.b.BruseTarlRovingRancher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bruse Tarl, Roving Rancher", 350, Rarity.RARE, mage.cards.b.BruseTarlRovingRancher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bucolic Ranch", 265, Rarity.UNCOMMON, mage.cards.b.BucolicRanch.class));
        cards.add(new SetCardInfo("Cactarantula", 158, Rarity.COMMON, mage.cards.c.Cactarantula.class));
        cards.add(new SetCardInfo("Cactusfolk Sureshot", 199, Rarity.UNCOMMON, mage.cards.c.CactusfolkSureshot.class));
        cards.add(new SetCardInfo("Calamity, Galloping Inferno", 116, Rarity.RARE, mage.cards.c.CalamityGallopingInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Calamity, Galloping Inferno", 330, Rarity.RARE, mage.cards.c.CalamityGallopingInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Canyon Crab", 40, Rarity.UNCOMMON, mage.cards.c.CanyonCrab.class));
        cards.add(new SetCardInfo("Caught in the Crossfire", 117, Rarity.UNCOMMON, mage.cards.c.CaughtInTheCrossfire.class));
        cards.add(new SetCardInfo("Caustic Bronco", 324, Rarity.RARE, mage.cards.c.CausticBronco.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Caustic Bronco", 82, Rarity.RARE, mage.cards.c.CausticBronco.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Claim Jumper", 310, Rarity.RARE, mage.cards.c.ClaimJumper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Claim Jumper", 8, Rarity.RARE, mage.cards.c.ClaimJumper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colossal Rattlewurm", 159, Rarity.RARE, mage.cards.c.ColossalRattlewurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colossal Rattlewurm", 339, Rarity.RARE, mage.cards.c.ColossalRattlewurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Concealed Courtyard", 268, Rarity.RARE, mage.cards.c.ConcealedCourtyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Concealed Courtyard", 302, Rarity.RARE, mage.cards.c.ConcealedCourtyard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conduit Pylons", 254, Rarity.COMMON, mage.cards.c.ConduitPylons.class));
        cards.add(new SetCardInfo("Congregation Gryff", 200, Rarity.UNCOMMON, mage.cards.c.CongregationGryff.class));
        cards.add(new SetCardInfo("Consuming Ashes", 83, Rarity.COMMON, mage.cards.c.ConsumingAshes.class));
        cards.add(new SetCardInfo("Corrupted Conviction", 84, Rarity.COMMON, mage.cards.c.CorruptedConviction.class));
        cards.add(new SetCardInfo("Creosote Heath", 255, Rarity.COMMON, mage.cards.c.CreosoteHeath.class));
        cards.add(new SetCardInfo("Cunning Coyote", 118, Rarity.UNCOMMON, mage.cards.c.CunningCoyote.class));
        cards.add(new SetCardInfo("Dance of the Tumbleweeds", 160, Rarity.COMMON, mage.cards.d.DanceOfTheTumbleweeds.class));
        cards.add(new SetCardInfo("Daring Thunder-Thief", 41, Rarity.COMMON, mage.cards.d.DaringThunderThief.class));
        cards.add(new SetCardInfo("Deadeye Duelist", 119, Rarity.COMMON, mage.cards.d.DeadeyeDuelist.class));
        cards.add(new SetCardInfo("Deepmuck Desperado", 42, Rarity.UNCOMMON, mage.cards.d.DeepmuckDesperado.class));
        cards.add(new SetCardInfo("Demonic Ruckus", 120, Rarity.UNCOMMON, mage.cards.d.DemonicRuckus.class));
        cards.add(new SetCardInfo("Desert's Due", 85, Rarity.COMMON, mage.cards.d.DesertsDue.class));
        cards.add(new SetCardInfo("Desperate Bloodseeker", 86, Rarity.COMMON, mage.cards.d.DesperateBloodseeker.class));
        cards.add(new SetCardInfo("Discerning Peddler", 121, Rarity.COMMON, mage.cards.d.DiscerningPeddler.class));
        cards.add(new SetCardInfo("Djinn of Fool's Fall", 43, Rarity.COMMON, mage.cards.d.DjinnOfFoolsFall.class));
        cards.add(new SetCardInfo("Doc Aurlock, Grizzled Genius", 201, Rarity.UNCOMMON, mage.cards.d.DocAurlockGrizzledGenius.class));
        cards.add(new SetCardInfo("Double Down", 317, Rarity.MYTHIC, mage.cards.d.DoubleDown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Double Down", 44, Rarity.MYTHIC, mage.cards.d.DoubleDown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drover Grizzly", 161, Rarity.COMMON, mage.cards.d.DroverGrizzly.class));
        cards.add(new SetCardInfo("Duelist of the Mind", 318, Rarity.RARE, mage.cards.d.DuelistOfTheMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duelist of the Mind", 45, Rarity.RARE, mage.cards.d.DuelistOfTheMind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Animus", 311, Rarity.RARE, mage.cards.d.DustAnimus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dust Animus", 9, Rarity.RARE, mage.cards.d.DustAnimus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emergent Haunting", 46, Rarity.UNCOMMON, mage.cards.e.EmergentHaunting.class));
        cards.add(new SetCardInfo("Eriette's Lullaby", 10, Rarity.COMMON, mage.cards.e.EriettesLullaby.class));
        cards.add(new SetCardInfo("Eriette, the Beguiler", 202, Rarity.RARE, mage.cards.e.ErietteTheBeguiler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eriette, the Beguiler", 293, Rarity.RARE, mage.cards.e.ErietteTheBeguiler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eroded Canyon", 256, Rarity.COMMON, mage.cards.e.ErodedCanyon.class));
        cards.add(new SetCardInfo("Ertha Jo, Frontier Mentor", 203, Rarity.UNCOMMON, mage.cards.e.ErthaJoFrontierMentor.class));
        cards.add(new SetCardInfo("Explosive Derailment", 122, Rarity.COMMON, mage.cards.e.ExplosiveDerailment.class));
        cards.add(new SetCardInfo("Failed Fording", 47, Rarity.COMMON, mage.cards.f.FailedFording.class));
        cards.add(new SetCardInfo("Fake Your Own Death", 87, Rarity.COMMON, mage.cards.f.FakeYourOwnDeath.class));
        cards.add(new SetCardInfo("Fblthp, Lost on the Range", 319, Rarity.RARE, mage.cards.f.FblthpLostOnTheRange.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fblthp, Lost on the Range", 48, Rarity.RARE, mage.cards.f.FblthpLostOnTheRange.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ferocification", 123, Rarity.UNCOMMON, mage.cards.f.Ferocification.class));
        cards.add(new SetCardInfo("Festering Gulch", 257, Rarity.COMMON, mage.cards.f.FesteringGulch.class));
        cards.add(new SetCardInfo("Final Showdown", 11, Rarity.MYTHIC, mage.cards.f.FinalShowdown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Final Showdown", 312, Rarity.MYTHIC, mage.cards.f.FinalShowdown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fleeting Reflection", 49, Rarity.UNCOMMON, mage.cards.f.FleetingReflection.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 285, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forlorn Flats", 258, Rarity.COMMON, mage.cards.f.ForlornFlats.class));
        cards.add(new SetCardInfo("Form a Posse", 204, Rarity.UNCOMMON, mage.cards.f.FormAPosse.class));
        cards.add(new SetCardInfo("Forsaken Miner", 88, Rarity.UNCOMMON, mage.cards.f.ForsakenMiner.class));
        cards.add(new SetCardInfo("Fortune, Loyal Steed", 12, Rarity.RARE, mage.cards.f.FortuneLoyalSteed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortune, Loyal Steed", 313, Rarity.RARE, mage.cards.f.FortuneLoyalSteed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Freestrider Commando", 162, Rarity.COMMON, mage.cards.f.FreestriderCommando.class));
        cards.add(new SetCardInfo("Freestrider Lookout", 163, Rarity.RARE, mage.cards.f.FreestriderLookout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Freestrider Lookout", 340, Rarity.RARE, mage.cards.f.FreestriderLookout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frontier Seeker", 13, Rarity.UNCOMMON, mage.cards.f.FrontierSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frontier Seeker", 368, Rarity.UNCOMMON, mage.cards.f.FrontierSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Full Steam Ahead", 164, Rarity.UNCOMMON, mage.cards.f.FullSteamAhead.class));
        cards.add(new SetCardInfo("Geralf, the Fleshwright", 287, Rarity.MYTHIC, mage.cards.g.GeralfTheFleshwright.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geralf, the Fleshwright", 50, Rarity.MYTHIC, mage.cards.g.GeralfTheFleshwright.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Getaway Glamer", 14, Rarity.UNCOMMON, mage.cards.g.GetawayGlamer.class));
        cards.add(new SetCardInfo("Geyser Drake", 51, Rarity.COMMON, mage.cards.g.GeyserDrake.class));
        cards.add(new SetCardInfo("Ghired, Mirror of the Wilds", 205, Rarity.MYTHIC, mage.cards.g.GhiredMirrorOfTheWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghired, Mirror of the Wilds", 351, Rarity.MYTHIC, mage.cards.g.GhiredMirrorOfTheWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Beaver", 165, Rarity.COMMON, mage.cards.g.GiantBeaver.class));
        cards.add(new SetCardInfo("Gila Courser", 124, Rarity.UNCOMMON, mage.cards.g.GilaCourser.class));
        cards.add(new SetCardInfo("Gisa, the Hellraiser", 288, Rarity.MYTHIC, mage.cards.g.GisaTheHellraiser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gisa, the Hellraiser", 89, Rarity.MYTHIC, mage.cards.g.GisaTheHellraiser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gold Pan", 242, Rarity.COMMON, mage.cards.g.GoldPan.class));
        cards.add(new SetCardInfo("Gold Rush", 166, Rarity.UNCOMMON, mage.cards.g.GoldRush.class));
        cards.add(new SetCardInfo("Goldvein Hydra", 167, Rarity.MYTHIC, mage.cards.g.GoldveinHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goldvein Hydra", 341, Rarity.MYTHIC, mage.cards.g.GoldveinHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Great Train Heist", 125, Rarity.RARE, mage.cards.g.GreatTrainHeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Great Train Heist", 331, Rarity.RARE, mage.cards.g.GreatTrainHeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hardbristle Bandit", 168, Rarity.COMMON, mage.cards.h.HardbristleBandit.class));
        cards.add(new SetCardInfo("Harrier Strix", 52, Rarity.COMMON, mage.cards.h.HarrierStrix.class));
        cards.add(new SetCardInfo("Hell to Pay", 126, Rarity.RARE, mage.cards.h.HellToPay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hell to Pay", 332, Rarity.RARE, mage.cards.h.HellToPay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hellspur Brute", 127, Rarity.UNCOMMON, mage.cards.h.HellspurBrute.class));
        cards.add(new SetCardInfo("Hellspur Posse Boss", 128, Rarity.RARE, mage.cards.h.HellspurPosseBoss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hellspur Posse Boss", 333, Rarity.RARE, mage.cards.h.HellspurPosseBoss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High Noon", 15, Rarity.RARE, mage.cards.h.HighNoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("High Noon", 314, Rarity.RARE, mage.cards.h.HighNoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Highway Robbery", 129, Rarity.COMMON, mage.cards.h.HighwayRobbery.class));
        cards.add(new SetCardInfo("Hollow Marauder", 90, Rarity.UNCOMMON, mage.cards.h.HollowMarauder.class));
        cards.add(new SetCardInfo("Holy Cow", 16, Rarity.COMMON, mage.cards.h.HolyCow.class));
        cards.add(new SetCardInfo("Honest Rutstein", 207, Rarity.UNCOMMON, mage.cards.h.HonestRutstein.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Honest Rutstein", 370, Rarity.UNCOMMON, mage.cards.h.HonestRutstein.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Insatiable Avarice", 325, Rarity.RARE, mage.cards.i.InsatiableAvarice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Insatiable Avarice", 91, Rarity.RARE, mage.cards.i.InsatiableAvarice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspiring Vantage", 269, Rarity.RARE, mage.cards.i.InspiringVantage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspiring Vantage", 303, Rarity.RARE, mage.cards.i.InspiringVantage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Intimidation Campaign", 208, Rarity.UNCOMMON, mage.cards.i.IntimidationCampaign.class));
        cards.add(new SetCardInfo("Intrepid Stablemaster", 169, Rarity.UNCOMMON, mage.cards.i.IntrepidStablemaster.class));
        cards.add(new SetCardInfo("Inventive Wingsmith", 17, Rarity.COMMON, mage.cards.i.InventiveWingsmith.class));
        cards.add(new SetCardInfo("Irascible Wolverine", 130, Rarity.COMMON, mage.cards.i.IrascibleWolverine.class));
        cards.add(new SetCardInfo("Iron-Fist Pulverizer", 131, Rarity.COMMON, mage.cards.i.IronFistPulverizer.class));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 279, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace Reawakened", 271, Rarity.MYTHIC, mage.cards.j.JaceReawakened.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace Reawakened", 306, Rarity.MYTHIC, mage.cards.j.JaceReawakened.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jagged Barrens", 259, Rarity.COMMON, mage.cards.j.JaggedBarrens.class));
        cards.add(new SetCardInfo("Jailbreak Scheme", 53, Rarity.COMMON, mage.cards.j.JailbreakScheme.class));
        cards.add(new SetCardInfo("Jem Lightfoote, Sky Explorer", 209, Rarity.UNCOMMON, mage.cards.j.JemLightfooteSkyExplorer.class));
        cards.add(new SetCardInfo("Jolene, Plundering Pugilist", 210, Rarity.UNCOMMON, mage.cards.j.JolenePlunderingPugilist.class));
        cards.add(new SetCardInfo("Kaervek, the Punisher", 289, Rarity.RARE, mage.cards.k.KaervekThePunisher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaervek, the Punisher", 92, Rarity.RARE, mage.cards.k.KaervekThePunisher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kambal, Profiteering Mayor", 211, Rarity.RARE, mage.cards.k.KambalProfiteeringMayor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kambal, Profiteering Mayor", 353, Rarity.RARE, mage.cards.k.KambalProfiteeringMayor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kellan Joins Up", 212, Rarity.RARE, mage.cards.k.KellanJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kellan Joins Up", 354, Rarity.RARE, mage.cards.k.KellanJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kellan, the Kid", 213, Rarity.MYTHIC, mage.cards.k.KellanTheKid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kellan, the Kid", 294, Rarity.MYTHIC, mage.cards.k.KellanTheKid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraum, Violent Cacophony", 214, Rarity.UNCOMMON, mage.cards.k.KraumViolentCacophony.class));
        cards.add(new SetCardInfo("Lassoed by the Law", 18, Rarity.UNCOMMON, mage.cards.l.LassoedByTheLaw.class));
        cards.add(new SetCardInfo("Laughing Jasper Flint", 215, Rarity.RARE, mage.cards.l.LaughingJasperFlint.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Laughing Jasper Flint", 355, Rarity.RARE, mage.cards.l.LaughingJasperFlint.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lavaspur Boots", 243, Rarity.UNCOMMON, mage.cards.l.LavaspurBoots.class));
        cards.add(new SetCardInfo("Lazav, Familiar Stranger", 216, Rarity.UNCOMMON, mage.cards.l.LazavFamiliarStranger.class));
        cards.add(new SetCardInfo("Lilah, Undefeated Slickshot", 217, Rarity.RARE, mage.cards.l.LilahUndefeatedSlickshot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lilah, Undefeated Slickshot", 356, Rarity.RARE, mage.cards.l.LilahUndefeatedSlickshot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lively Dirge", 93, Rarity.UNCOMMON, mage.cards.l.LivelyDirge.class));
        cards.add(new SetCardInfo("Loan Shark", 55, Rarity.COMMON, mage.cards.l.LoanShark.class));
        cards.add(new SetCardInfo("Lonely Arroyo", 260, Rarity.COMMON, mage.cards.l.LonelyArroyo.class));
        cards.add(new SetCardInfo("Longhorn Sharpshooter", 132, Rarity.UNCOMMON, mage.cards.l.LonghornSharpshooter.class));
        cards.add(new SetCardInfo("Lush Oasis", 261, Rarity.COMMON, mage.cards.l.LushOasis.class));
        cards.add(new SetCardInfo("Luxurious Locomotive", 244, Rarity.UNCOMMON, mage.cards.l.LuxuriousLocomotive.class));
        cards.add(new SetCardInfo("Magda, the Hoardmaster", 133, Rarity.RARE, mage.cards.m.MagdaTheHoardmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magda, the Hoardmaster", 334, Rarity.RARE, mage.cards.m.MagdaTheHoardmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magda, the Hoardmaster", 374, Rarity.RARE, mage.cards.m.MagdaTheHoardmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magebane Lizard", 134, Rarity.UNCOMMON, mage.cards.m.MagebaneLizard.class));
        cards.add(new SetCardInfo("Make Your Own Luck", 218, Rarity.UNCOMMON, mage.cards.m.MakeYourOwnLuck.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Make Your Own Luck", 371, Rarity.UNCOMMON, mage.cards.m.MakeYourOwnLuck.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malcolm, the Eyes", 219, Rarity.RARE, mage.cards.m.MalcolmTheEyes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malcolm, the Eyes", 295, Rarity.RARE, mage.cards.m.MalcolmTheEyes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Map the Frontier", 170, Rarity.UNCOMMON, mage.cards.m.MapTheFrontier.class));
        cards.add(new SetCardInfo("Marauding Sphinx", 56, Rarity.UNCOMMON, mage.cards.m.MaraudingSphinx.class));
        cards.add(new SetCardInfo("Marchesa, Dealer of Death", 220, Rarity.RARE, mage.cards.m.MarchesaDealerOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marchesa, Dealer of Death", 357, Rarity.RARE, mage.cards.m.MarchesaDealerOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Metamorphic Blast", 57, Rarity.UNCOMMON, mage.cards.m.MetamorphicBlast.class));
        cards.add(new SetCardInfo("Mine Raider", 135, Rarity.COMMON, mage.cards.m.MineRaider.class));
        cards.add(new SetCardInfo("Mirage Mesa", 262, Rarity.COMMON, mage.cards.m.MirageMesa.class));
        cards.add(new SetCardInfo("Miriam, Herd Whisperer", 221, Rarity.UNCOMMON, mage.cards.m.MiriamHerdWhisperer.class));
        cards.add(new SetCardInfo("Mobile Homestead", 245, Rarity.UNCOMMON, mage.cards.m.MobileHomestead.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 283, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mourner's Surprise", 94, Rarity.COMMON, mage.cards.m.MournersSurprise.class));
        cards.add(new SetCardInfo("Mystical Tether", 19, Rarity.COMMON, mage.cards.m.MysticalTether.class));
        cards.add(new SetCardInfo("Neutralize the Guards", 95, Rarity.UNCOMMON, mage.cards.n.NeutralizeTheGuards.class));
        cards.add(new SetCardInfo("Nezumi Linkbreaker", 96, Rarity.COMMON, mage.cards.n.NezumiLinkbreaker.class));
        cards.add(new SetCardInfo("Nimble Brigand", 58, Rarity.UNCOMMON, mage.cards.n.NimbleBrigand.class));
        cards.add(new SetCardInfo("Nurturing Pixie", 20, Rarity.UNCOMMON, mage.cards.n.NurturingPixie.class));
        cards.add(new SetCardInfo("Oasis Gardener", 246, Rarity.COMMON, mage.cards.o.OasisGardener.class));
        cards.add(new SetCardInfo("Obeka, Splitter of Seconds", 222, Rarity.RARE, mage.cards.o.ObekaSplitterOfSeconds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obeka, Splitter of Seconds", 358, Rarity.RARE, mage.cards.o.ObekaSplitterOfSeconds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, the Ringleader", 223, Rarity.MYTHIC, mage.cards.o.OkoTheRingleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, the Ringleader", 296, Rarity.MYTHIC, mage.cards.o.OkoTheRingleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, the Ringleader", 305, Rarity.MYTHIC, mage.cards.o.OkoTheRingleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Omenport Vigilante", 21, Rarity.UNCOMMON, mage.cards.o.OmenportVigilante.class));
        cards.add(new SetCardInfo("One Last Job", 22, Rarity.RARE, mage.cards.o.OneLastJob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("One Last Job", 315, Rarity.RARE, mage.cards.o.OneLastJob.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ornery Tumblewagg", 171, Rarity.RARE, mage.cards.o.OrneryTumblewagg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ornery Tumblewagg", 342, Rarity.RARE, mage.cards.o.OrneryTumblewagg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outcaster Greenblade", 172, Rarity.UNCOMMON, mage.cards.o.OutcasterGreenblade.class));
        cards.add(new SetCardInfo("Outcaster Trailblazer", 173, Rarity.RARE, mage.cards.o.OutcasterTrailblazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outcaster Trailblazer", 343, Rarity.RARE, mage.cards.o.OutcasterTrailblazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outlaw Medic", 23, Rarity.COMMON, mage.cards.o.OutlawMedic.class));
        cards.add(new SetCardInfo("Outlaw Stitcher", 59, Rarity.UNCOMMON, mage.cards.o.OutlawStitcher.class));
        cards.add(new SetCardInfo("Outlaws' Fury", 136, Rarity.COMMON, mage.cards.o.OutlawsFury.class));
        cards.add(new SetCardInfo("Overzealous Muscle", 97, Rarity.COMMON, mage.cards.o.OverzealousMuscle.class));
        cards.add(new SetCardInfo("Patient Naturalist", 174, Rarity.COMMON, mage.cards.p.PatientNaturalist.class));
        cards.add(new SetCardInfo("Peerless Ropemaster", 60, Rarity.COMMON, mage.cards.p.PeerlessRopemaster.class));
        cards.add(new SetCardInfo("Phantom Interference", 61, Rarity.COMMON, mage.cards.p.PhantomInterference.class));
        cards.add(new SetCardInfo("Pillage the Bog", 224, Rarity.RARE, mage.cards.p.PillageTheBog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pillage the Bog", 359, Rarity.RARE, mage.cards.p.PillageTheBog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pitiless Carnage", 326, Rarity.RARE, mage.cards.p.PitilessCarnage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pitiless Carnage", 98, Rarity.RARE, mage.cards.p.PitilessCarnage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 277, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plan the Heist", 62, Rarity.UNCOMMON, mage.cards.p.PlanTheHeist.class));
        cards.add(new SetCardInfo("Prairie Dog", 24, Rarity.UNCOMMON, mage.cards.p.PrairieDog.class));
        cards.add(new SetCardInfo("Prickly Pair", 137, Rarity.COMMON, mage.cards.p.PricklyPair.class));
        cards.add(new SetCardInfo("Prosperity Tycoon", 25, Rarity.UNCOMMON, mage.cards.p.ProsperityTycoon.class));
        cards.add(new SetCardInfo("Quick Draw", 138, Rarity.COMMON, mage.cards.q.QuickDraw.class));
        cards.add(new SetCardInfo("Quilled Charger", 139, Rarity.COMMON, mage.cards.q.QuilledCharger.class));
        cards.add(new SetCardInfo("Railway Brawler", 175, Rarity.MYTHIC, mage.cards.r.RailwayBrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Railway Brawler", 344, Rarity.MYTHIC, mage.cards.r.RailwayBrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Joins Up", 225, Rarity.RARE, mage.cards.r.RakdosJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Joins Up", 360, Rarity.RARE, mage.cards.r.RakdosJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, the Muscle", 226, Rarity.MYTHIC, mage.cards.r.RakdosTheMuscle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, the Muscle", 297, Rarity.MYTHIC, mage.cards.r.RakdosTheMuscle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakish Crew", 99, Rarity.UNCOMMON, mage.cards.r.RakishCrew.class));
        cards.add(new SetCardInfo("Rambling Possum", 176, Rarity.UNCOMMON, mage.cards.r.RamblingPossum.class));
        cards.add(new SetCardInfo("Rattleback Apothecary", 100, Rarity.UNCOMMON, mage.cards.r.RattlebackApothecary.class));
        cards.add(new SetCardInfo("Raucous Entertainer", 177, Rarity.UNCOMMON, mage.cards.r.RaucousEntertainer.class));
        cards.add(new SetCardInfo("Raven of Fell Omens", 101, Rarity.COMMON, mage.cards.r.RavenOfFellOmens.class));
        cards.add(new SetCardInfo("Razzle-Dazzler", 63, Rarity.COMMON, mage.cards.r.RazzleDazzler.class));
        cards.add(new SetCardInfo("Reach for the Sky", 178, Rarity.COMMON, mage.cards.r.ReachForTheSky.class));
        cards.add(new SetCardInfo("Reckless Lackey", 140, Rarity.COMMON, mage.cards.r.RecklessLackey.class));
        cards.add(new SetCardInfo("Redrock Sentinel", 247, Rarity.UNCOMMON, mage.cards.r.RedrockSentinel.class));
        cards.add(new SetCardInfo("Requisition Raid", 26, Rarity.UNCOMMON, mage.cards.r.RequisitionRaid.class));
        cards.add(new SetCardInfo("Resilient Roadrunner", 141, Rarity.UNCOMMON, mage.cards.r.ResilientRoadrunner.class));
        cards.add(new SetCardInfo("Return the Favor", 142, Rarity.UNCOMMON, mage.cards.r.ReturnTheFavor.class));
        cards.add(new SetCardInfo("Rictus Robber", 102, Rarity.UNCOMMON, mage.cards.r.RictusRobber.class));
        cards.add(new SetCardInfo("Riku of Many Paths", 227, Rarity.RARE, mage.cards.r.RikuOfManyPaths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Riku of Many Paths", 361, Rarity.RARE, mage.cards.r.RikuOfManyPaths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rise of the Varmints", 179, Rarity.UNCOMMON, mage.cards.r.RiseOfTheVarmints.class));
        cards.add(new SetCardInfo("Rodeo Pyromancers", 143, Rarity.COMMON, mage.cards.r.RodeoPyromancers.class));
        cards.add(new SetCardInfo("Rooftop Assassin", 103, Rarity.COMMON, mage.cards.r.RooftopAssassin.class));
        cards.add(new SetCardInfo("Roxanne, Starfall Savant", 228, Rarity.RARE, mage.cards.r.RoxanneStarfallSavant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roxanne, Starfall Savant", 362, Rarity.RARE, mage.cards.r.RoxanneStarfallSavant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rush of Dread", 104, Rarity.RARE, mage.cards.r.RushOfDread.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rush of Dread", 327, Rarity.RARE, mage.cards.r.RushOfDread.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rustler Rampage", 27, Rarity.UNCOMMON, mage.cards.r.RustlerRampage.class));
        cards.add(new SetCardInfo("Ruthless Lawbringer", 229, Rarity.UNCOMMON, mage.cards.r.RuthlessLawbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruthless Lawbringer", 372, Rarity.UNCOMMON, mage.cards.r.RuthlessLawbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sandstorm Verge", 263, Rarity.UNCOMMON, mage.cards.s.SandstormVerge.class));
        cards.add(new SetCardInfo("Satoru, the Infiltrator", 230, Rarity.RARE, mage.cards.s.SatoruTheInfiltrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Satoru, the Infiltrator", 298, Rarity.RARE, mage.cards.s.SatoruTheInfiltrator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scalestorm Summoner", 144, Rarity.UNCOMMON, mage.cards.s.ScalestormSummoner.class));
        cards.add(new SetCardInfo("Scorching Shot", 145, Rarity.UNCOMMON, mage.cards.s.ScorchingShot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scorching Shot", 369, Rarity.UNCOMMON, mage.cards.s.ScorchingShot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seize the Secrets", 64, Rarity.COMMON, mage.cards.s.SeizeTheSecrets.class));
        cards.add(new SetCardInfo("Selvala, Eager Trailblazer", 231, Rarity.MYTHIC, mage.cards.s.SelvalaEagerTrailblazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selvala, Eager Trailblazer", 363, Rarity.MYTHIC, mage.cards.s.SelvalaEagerTrailblazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seraphic Steed", 232, Rarity.RARE, mage.cards.s.SeraphicSteed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seraphic Steed", 364, Rarity.RARE, mage.cards.s.SeraphicSteed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Servant of the Stinger", 105, Rarity.UNCOMMON, mage.cards.s.ServantOfTheStinger.class));
        cards.add(new SetCardInfo("Shackle Slinger", 65, Rarity.UNCOMMON, mage.cards.s.ShackleSlinger.class));
        cards.add(new SetCardInfo("Shepherd of the Clouds", 28, Rarity.UNCOMMON, mage.cards.s.ShepherdOfTheClouds.class));
        cards.add(new SetCardInfo("Sheriff of Safe Passage", 29, Rarity.UNCOMMON, mage.cards.s.SheriffOfSafePassage.class));
        cards.add(new SetCardInfo("Shifting Grift", 66, Rarity.UNCOMMON, mage.cards.s.ShiftingGrift.class));
        cards.add(new SetCardInfo("Shoot the Sheriff", 106, Rarity.UNCOMMON, mage.cards.s.ShootTheSheriff.class));
        cards.add(new SetCardInfo("Silver Deputy", 248, Rarity.COMMON, mage.cards.s.SilverDeputy.class));
        cards.add(new SetCardInfo("Skulduggery", 107, Rarity.COMMON, mage.cards.s.Skulduggery.class));
        cards.add(new SetCardInfo("Slick Sequence", 233, Rarity.UNCOMMON, mage.cards.s.SlickSequence.class));
        cards.add(new SetCardInfo("Slickshot Lockpicker", 67, Rarity.UNCOMMON, mage.cards.s.SlickshotLockpicker.class));
        cards.add(new SetCardInfo("Slickshot Show-Off", 146, Rarity.RARE, mage.cards.s.SlickshotShowOff.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slickshot Show-Off", 335, Rarity.RARE, mage.cards.s.SlickshotShowOff.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slickshot Vault-Buster", 68, Rarity.COMMON, mage.cards.s.SlickshotVaultBuster.class));
        cards.add(new SetCardInfo("Smuggler's Surprise", 180, Rarity.RARE, mage.cards.s.SmugglersSurprise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smuggler's Surprise", 345, Rarity.RARE, mage.cards.s.SmugglersSurprise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snakeskin Veil", 181, Rarity.COMMON, mage.cards.s.SnakeskinVeil.class));
        cards.add(new SetCardInfo("Soured Springs", 264, Rarity.COMMON, mage.cards.s.SouredSprings.class));
        cards.add(new SetCardInfo("Spinewoods Armadillo", 182, Rarity.UNCOMMON, mage.cards.s.SpinewoodsArmadillo.class));
        cards.add(new SetCardInfo("Spinewoods Paladin", 183, Rarity.COMMON, mage.cards.s.SpinewoodsPaladin.class));
        cards.add(new SetCardInfo("Spirebluff Canal", 270, Rarity.RARE, mage.cards.s.SpirebluffCanal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spirebluff Canal", 304, Rarity.RARE, mage.cards.s.SpirebluffCanal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spring Splasher", 69, Rarity.COMMON, mage.cards.s.SpringSplasher.class));
        cards.add(new SetCardInfo("Stagecoach Security", 30, Rarity.COMMON, mage.cards.s.StagecoachSecurity.class));
        cards.add(new SetCardInfo("Steer Clear", 31, Rarity.COMMON, mage.cards.s.SteerClear.class));
        cards.add(new SetCardInfo("Step Between Worlds", 321, Rarity.RARE, mage.cards.s.StepBetweenWorlds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Step Between Worlds", 70, Rarity.RARE, mage.cards.s.StepBetweenWorlds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sterling Hound", 249, Rarity.COMMON, mage.cards.s.SterlingHound.class));
        cards.add(new SetCardInfo("Sterling Keykeeper", 32, Rarity.COMMON, mage.cards.s.SterlingKeykeeper.class));
        cards.add(new SetCardInfo("Sterling Supplier", 33, Rarity.COMMON, mage.cards.s.SterlingSupplier.class));
        cards.add(new SetCardInfo("Stingerback Terror", 147, Rarity.RARE, mage.cards.s.StingerbackTerror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingerback Terror", 336, Rarity.RARE, mage.cards.s.StingerbackTerror.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stoic Sphinx", 322, Rarity.RARE, mage.cards.s.StoicSphinx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stoic Sphinx", 71, Rarity.RARE, mage.cards.s.StoicSphinx.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stop Cold", 72, Rarity.COMMON, mage.cards.s.StopCold.class));
        cards.add(new SetCardInfo("Stubborn Burrowfiend", 184, Rarity.UNCOMMON, mage.cards.s.StubbornBurrowfiend.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 281, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taii Wakeen, Perfect Shot", 234, Rarity.RARE, mage.cards.t.TaiiWakeenPerfectShot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taii Wakeen, Perfect Shot", 365, Rarity.RARE, mage.cards.t.TaiiWakeenPerfectShot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Take Up the Shield", 34, Rarity.COMMON, mage.cards.t.TakeUpTheShield.class));
        cards.add(new SetCardInfo("Take for a Ride", 148, Rarity.UNCOMMON, mage.cards.t.TakeForARide.class));
        cards.add(new SetCardInfo("Take the Fall", 73, Rarity.COMMON, mage.cards.t.TakeTheFall.class));
        cards.add(new SetCardInfo("Terror of the Peaks", 149, Rarity.MYTHIC, mage.cards.t.TerrorOfThePeaks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror of the Peaks", 337, Rarity.MYTHIC, mage.cards.t.TerrorOfThePeaks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Gitrog, Ravenous Ride", 206, Rarity.MYTHIC, mage.cards.t.TheGitrogRavenousRide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Gitrog, Ravenous Ride", 352, Rarity.MYTHIC, mage.cards.t.TheGitrogRavenousRide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Key to the Vault", 320, Rarity.RARE, mage.cards.t.TheKeyToTheVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Key to the Vault", 373, Rarity.RARE, mage.cards.t.TheKeyToTheVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Key to the Vault", 54, Rarity.RARE, mage.cards.t.TheKeyToTheVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("This Town Ain't Big Enough", 74, Rarity.UNCOMMON, mage.cards.t.ThisTownAintBigEnough.class));
        cards.add(new SetCardInfo("Three Steps Ahead", 323, Rarity.RARE, mage.cards.t.ThreeStepsAhead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Steps Ahead", 75, Rarity.RARE, mage.cards.t.ThreeStepsAhead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Throw from the Saddle", 185, Rarity.COMMON, mage.cards.t.ThrowFromTheSaddle.class));
        cards.add(new SetCardInfo("Thunder Lasso", 35, Rarity.UNCOMMON, mage.cards.t.ThunderLasso.class));
        cards.add(new SetCardInfo("Thunder Salvo", 150, Rarity.COMMON, mage.cards.t.ThunderSalvo.class));
        cards.add(new SetCardInfo("Tinybones Joins Up", 108, Rarity.RARE, mage.cards.t.TinybonesJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tinybones Joins Up", 328, Rarity.RARE, mage.cards.t.TinybonesJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tinybones, the Pickpocket", 109, Rarity.MYTHIC, mage.cards.t.TinybonesThePickpocket.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tinybones, the Pickpocket", 290, Rarity.MYTHIC, mage.cards.t.TinybonesThePickpocket.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tomb Trawler", 250, Rarity.UNCOMMON, mage.cards.t.TombTrawler.class));
        cards.add(new SetCardInfo("Trained Arynx", 36, Rarity.COMMON, mage.cards.t.TrainedArynx.class));
        cards.add(new SetCardInfo("Trash the Town", 186, Rarity.UNCOMMON, mage.cards.t.TrashTheTown.class));
        cards.add(new SetCardInfo("Treasure Dredger", 110, Rarity.UNCOMMON, mage.cards.t.TreasureDredger.class));
        cards.add(new SetCardInfo("Trick Shot", 151, Rarity.COMMON, mage.cards.t.TrickShot.class));
        cards.add(new SetCardInfo("Tumbleweed Rising", 187, Rarity.COMMON, mage.cards.t.TumbleweedRising.class));
        cards.add(new SetCardInfo("Unfortunate Accident", 111, Rarity.UNCOMMON, mage.cards.u.UnfortunateAccident.class));
        cards.add(new SetCardInfo("Unscrupulous Contractor", 112, Rarity.UNCOMMON, mage.cards.u.UnscrupulousContractor.class));
        cards.add(new SetCardInfo("Vadmir, New Blood", 113, Rarity.RARE, mage.cards.v.VadmirNewBlood.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vadmir, New Blood", 329, Rarity.RARE, mage.cards.v.VadmirNewBlood.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vault Plunderer", 114, Rarity.COMMON, mage.cards.v.VaultPlunderer.class));
        cards.add(new SetCardInfo("Vengeful Townsfolk", 37, Rarity.COMMON, mage.cards.v.VengefulTownsfolk.class));
        cards.add(new SetCardInfo("Vial Smasher, Gleeful Grenadier", 235, Rarity.UNCOMMON, mage.cards.v.VialSmasherGleefulGrenadier.class));
        cards.add(new SetCardInfo("Visage Bandit", 76, Rarity.UNCOMMON, mage.cards.v.VisageBandit.class));
        cards.add(new SetCardInfo("Voracious Varmint", 188, Rarity.COMMON, mage.cards.v.VoraciousVarmint.class));
        cards.add(new SetCardInfo("Vraska Joins Up", 236, Rarity.RARE, mage.cards.v.VraskaJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska Joins Up", 366, Rarity.RARE, mage.cards.v.VraskaJoinsUp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska, the Silencer", 237, Rarity.MYTHIC, mage.cards.v.VraskaTheSilencer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska, the Silencer", 299, Rarity.MYTHIC, mage.cards.v.VraskaTheSilencer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wanted Griffin", 38, Rarity.COMMON, mage.cards.w.WantedGriffin.class));
        cards.add(new SetCardInfo("Wrangler of the Damned", 238, Rarity.UNCOMMON, mage.cards.w.WranglerOfTheDamned.class));
        cards.add(new SetCardInfo("Wylie Duke, Atiin Hero", 239, Rarity.RARE, mage.cards.w.WylieDukeAtiinHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wylie Duke, Atiin Hero", 367, Rarity.RARE, mage.cards.w.WylieDukeAtiinHero.class, NON_FULL_USE_VARIOUS));
    }

    private Set<Integer> specialLands = new HashSet<>(Arrays.asList(251, 253, 255, 256, 257, 258, 259, 260, 261, 264));

    // otp: 30 rare, 15 mythic, otj: 60, 20
    private static double ratioRareMythicOfOtpInFoilSlot = (30.0 * 2.0 + 15.0) / ((30.0 + 60.0) * 2.0 + (15.0 + 20.0));
    private static double ratioMythic = 20.0 / (20.0 + 60.0 * 2.0);
    private static double ratioOTPMythic = 15.0 / (15.0 + 30.0 * 2.0);
    // otp: 20, otj: 100
    private static double ratioUncommonOPTInFoilSlot = 20.0 / (20.0 + 100.0);

    @Override
    public List<Card> tryBooster() {
        // TODO: make part of this more generic, this is the first try at a play booster generation.
        // We start by deciding the various slots.
        // Land Slot: 1/2 chance for a basic, 1/2 chance for a nonbasic in the special list
        int basicLand = 0;
        int nonbasicLand = 0;
        {
            if (RandomUtil.nextDouble() <= 0.5) {
                basicLand++;
            } else {
                nonbasicLand++;
            }
        }

        // 1 slot is guarantee opt.
        int otpUncommon = 0;
        int otpRareOrMythic = 0;
        {
            double rollOtp = RandomUtil.nextDouble();
            if (rollOtp >= 1.0 / 3.0) { // know probability of 2/3 to have an uncommon.
                otpUncommon++;
            } else {
                otpRareOrMythic++;
            }
        }

        // 8 slots have guarantee rarity
        int rareOrMythic = 1;
        int uncommon = 3;
        int common = 5;

        // 1 slot is 1/64 chance to be spg, and 1/5 - 1/64 to be otp, 4/5 to be common
        int spg = 0;
        int big = 0;
        {
            double rollBig = RandomUtil.nextDouble();
            if (rollBig <= 1.0 / 64.0) { // know probability of 1/64 to be spg
                spg++;
            } else if (rollBig <= 1.0 / 5.0) {
                big++;
            } else {
                common++;
            }
        }

        // 1 slot is a wildcard, with 1/12 to be r/m as a known info.
        // MISSING INFO: relative chance of C/U in that slot. Let's assume 3/12 uncommon and 8/12 common.
        // MISSING INFO: what about the special common lands? do they count here?
        {
            double rollWildcard = RandomUtil.nextDouble();
            if (rollWildcard <= 1.0 / 12.0) {
                rareOrMythic++;
            } else if (rollWildcard <= 4.0 / 12.0) {
                uncommon++;
            } else {
                if (rollWildcard >= 1.0 - (8.0 / 12.0) * 10.0 / (10.0 + 81.0)) {
                    nonbasicLand++;
                } else {
                    common++;
                }
            }
        }

        // 1 slot is a (foil) wildcard that can be otp, we know nothing more here.
        // MISSING INFO: all the following chances are made up
        {
            double rollFoilWildcard = RandomUtil.nextDouble();
            if (rollFoilWildcard <= 1.0 / 12.0) {
                // Let's assume any of the rare among set + otp have same chance, that is twice the chance of mythic
                if (rollFoilWildcard <= (1.0 / 12.0) * ratioRareMythicOfOtpInFoilSlot) {
                    otpRareOrMythic++;
                } else {
                    rareOrMythic++;
                }
            } else if (rollFoilWildcard <= 4.0 / 12.0) {
                if (rollFoilWildcard <= (1.0 / 12.0) + (3.0 / 12.0) * ratioUncommonOPTInFoilSlot) {
                    otpUncommon++;
                } else {
                    uncommon++;
                }
            } else {
                common++;
            }
        }

        /*
        int total = rareOrMythic + uncommon + common + nonbasicLand + basicLand + otpRareOrMythic + otpUncommon + big + spg;
        System.out.println(
                "Total" + total
                        + "R" + rareOrMythic + " U" + uncommon + " C" + common
                        + " SL" + nonbasicLand + " B" + basicLand
                        + " OTP-R" + otpRareOrMythic + " OPT-U" + otpUncommon
                        + " BIG" + big + " SPG" + spg
        );
        */

        // The booster we are building
        List<Card> booster = new ArrayList<>();

        List<CardInfo> list_OTJ_C_And_SL =
                getCardsByRarity(Rarity.COMMON).stream()
                        .filter(info -> info.getCardNumberAsInt() <= maxCardNumberInBooster)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTJ_C = // All commons, minus the special lands
                list_OTJ_C_And_SL.stream()
                        .filter(info -> !(specialLands.contains(info.getCardNumberAsInt())))
                        .collect(Collectors.toList());
        List<CardInfo> list_OTJ_SL =
                list_OTJ_C_And_SL.stream()
                        .filter(info -> specialLands.contains(info.getCardNumberAsInt()))
                        .collect(Collectors.toList());
        List<CardInfo> list_OTJ_U =
                getCardsByRarity(Rarity.UNCOMMON)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= maxCardNumberInBooster)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTJ_R =
                getCardsByRarity(Rarity.RARE)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= maxCardNumberInBooster)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTJ_M =
                getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= maxCardNumberInBooster)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTJ_Basic =
                getCardsByRarity(Rarity.LAND)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= maxCardNumberInBooster)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTP_U =
                BreakingNews.getInstance().getCardsByRarity(Rarity.UNCOMMON)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 65)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTP_R =
                BreakingNews.getInstance().getCardsByRarity(Rarity.RARE)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 65)
                        .collect(Collectors.toList());
        List<CardInfo> list_OTP_M =
                BreakingNews.getInstance().getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 65)
                        .collect(Collectors.toList());
        List<CardInfo> list_BIG =
                TheBigScore.getInstance().getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> info.getCardNumberAsInt() <= 30)
                        .collect(Collectors.toList());
        List<CardInfo> list_SPG =
                SpecialGuests.getInstance().getCardsByRarity(Rarity.MYTHIC)
                        .stream()
                        .filter(info -> {
                            int cn = info.getCardNumberAsInt();
                            return cn >= 29 && cn <= 38;
                        })
                        .collect(Collectors.toList());

        for (int i = 0; i < spg; i++) {
            addToBooster(booster, list_SPG);
        }
        for (int i = 0; i < big; i++) {
            addToBooster(booster, list_BIG);
        }
        for (int i = 0; i < rareOrMythic; i++) {
            if (RandomUtil.nextDouble() <= ratioMythic) {
                addToBooster(booster, list_OTJ_M);
            } else {
                addToBooster(booster, list_OTJ_R);
            }
        }
        for (int i = 0; i < otpRareOrMythic; i++) {
            if (RandomUtil.nextDouble() <= ratioOTPMythic) {
                addToBooster(booster, list_OTP_M);
            } else {
                addToBooster(booster, list_OTP_R);
            }
        }
        for (int i = 0; i < otpUncommon; i++) {
            addToBooster(booster, list_OTP_U);
        }
        for (int i = 0; i < uncommon; i++) {
            addToBooster(booster, list_OTJ_U);
        }
        for (int i = 0; i < common; i++) {
            addToBooster(booster, list_OTJ_C);
        }
        for (int i = 0; i < nonbasicLand; i++) {
            addToBooster(booster, list_OTJ_SL);
        }
        for (int i = 0; i < basicLand; i++) {
            addToBooster(booster, list_OTJ_Basic);
        }

        return booster;
    }
}
