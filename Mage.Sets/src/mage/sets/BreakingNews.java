package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class BreakingNews extends ExpansionSet {

    private static final BreakingNews instance = new BreakingNews();

    public static BreakingNews getInstance() {
        return instance;
    }

    private BreakingNews() {
        super("Breaking News", "OTP", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 65;

        cards.add(new SetCardInfo("Abrupt Decay", 34, Rarity.RARE, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Anguished Unmaking", 35, Rarity.MYTHIC, mage.cards.a.AnguishedUnmaking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anguished Unmaking", 74, Rarity.MYTHIC, mage.cards.a.AnguishedUnmaking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archive Trap", 7, Rarity.RARE, mage.cards.a.ArchiveTrap.class));
        cards.add(new SetCardInfo("Archmage's Charm", 8, Rarity.RARE, mage.cards.a.ArchmagesCharm.class));
        cards.add(new SetCardInfo("Back for More", 36, Rarity.UNCOMMON, mage.cards.b.BackForMore.class));
        cards.add(new SetCardInfo("Bedevil", 37, Rarity.RARE, mage.cards.b.Bedevil.class));
        cards.add(new SetCardInfo("Buried in the Garden", 38, Rarity.UNCOMMON, mage.cards.b.BuriedInTheGarden.class));
        cards.add(new SetCardInfo("Clear Shot", 28, Rarity.UNCOMMON, mage.cards.c.ClearShot.class));
        cards.add(new SetCardInfo("Collective Defiance", 21, Rarity.RARE, mage.cards.c.CollectiveDefiance.class));
        cards.add(new SetCardInfo("Commandeer", 9, Rarity.RARE, mage.cards.c.Commandeer.class));
        cards.add(new SetCardInfo("Contagion Engine", 61, Rarity.MYTHIC, mage.cards.c.ContagionEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Contagion Engine", 78, Rarity.MYTHIC, mage.cards.c.ContagionEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crackle with Power", 22, Rarity.MYTHIC, mage.cards.c.CrackleWithPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crackle with Power", 71, Rarity.MYTHIC, mage.cards.c.CrackleWithPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crime // Punishment", 39, Rarity.MYTHIC, mage.cards.c.CrimePunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crime // Punishment", 75, Rarity.MYTHIC, mage.cards.c.CrimePunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cruel Ultimatum", 40, Rarity.RARE, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Decimate", 41, Rarity.RARE, mage.cards.d.Decimate.class));
        cards.add(new SetCardInfo("Decisive Denial", 42, Rarity.UNCOMMON, mage.cards.d.DecisiveDenial.class));
        cards.add(new SetCardInfo("Detention Sphere", 43, Rarity.RARE, mage.cards.d.DetentionSphere.class));
        cards.add(new SetCardInfo("Dust Bowl", 65, Rarity.RARE, mage.cards.d.DustBowl.class));
        cards.add(new SetCardInfo("Electrodominance", 23, Rarity.RARE, mage.cards.e.Electrodominance.class));
        cards.add(new SetCardInfo("Endless Detour", 44, Rarity.RARE, mage.cards.e.EndlessDetour.class));
        cards.add(new SetCardInfo("Essence Capture", 10, Rarity.UNCOMMON, mage.cards.e.EssenceCapture.class));
        cards.add(new SetCardInfo("Fell the Mighty", 1, Rarity.RARE, mage.cards.f.FellTheMighty.class));
        cards.add(new SetCardInfo("Fierce Retribution", 2, Rarity.UNCOMMON, mage.cards.f.FierceRetribution.class));
        cards.add(new SetCardInfo("Fling", 24, Rarity.UNCOMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Force of Vigor", 29, Rarity.MYTHIC, mage.cards.f.ForceOfVigor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Force of Vigor", 73, Rarity.MYTHIC, mage.cards.f.ForceOfVigor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fractured Identity", 45, Rarity.MYTHIC, mage.cards.f.FracturedIdentity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fractured Identity", 76, Rarity.MYTHIC, mage.cards.f.FracturedIdentity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grindstone", 62, Rarity.MYTHIC, mage.cards.g.Grindstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grindstone", 79, Rarity.MYTHIC, mage.cards.g.Grindstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heartless Pillage", 14, Rarity.UNCOMMON, mage.cards.h.HeartlessPillage.class));
        cards.add(new SetCardInfo("Hindering Light", 46, Rarity.UNCOMMON, mage.cards.h.HinderingLight.class));
        cards.add(new SetCardInfo("Humiliate", 47, Rarity.UNCOMMON, mage.cards.h.Humiliate.class));
        cards.add(new SetCardInfo("Hypothesizzle", 48, Rarity.UNCOMMON, mage.cards.h.Hypothesizzle.class));
        cards.add(new SetCardInfo("Imp's Mischief", 15, Rarity.RARE, mage.cards.i.ImpsMischief.class));
        cards.add(new SetCardInfo("Indomitable Creativity", 25, Rarity.MYTHIC, mage.cards.i.IndomitableCreativity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Indomitable Creativity", 72, Rarity.MYTHIC, mage.cards.i.IndomitableCreativity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ionize", 49, Rarity.RARE, mage.cards.i.Ionize.class));
        cards.add(new SetCardInfo("Journey to Nowhere", 3, Rarity.UNCOMMON, mage.cards.j.JourneyToNowhere.class));
        cards.add(new SetCardInfo("Leyline Binding", 4, Rarity.MYTHIC, mage.cards.l.LeylineBinding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline Binding", 66, Rarity.MYTHIC, mage.cards.l.LeylineBinding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Drain", 11, Rarity.MYTHIC, mage.cards.m.ManaDrain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Drain", 67, Rarity.MYTHIC, mage.cards.m.ManaDrain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindbreak Trap", 12, Rarity.MYTHIC, mage.cards.m.MindbreakTrap.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindbreak Trap", 68, Rarity.MYTHIC, mage.cards.m.MindbreakTrap.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindslaver", 63, Rarity.MYTHIC, mage.cards.m.Mindslaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mindslaver", 80, Rarity.MYTHIC, mage.cards.m.Mindslaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 16, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Oko, Thief of Crowns", 50, Rarity.MYTHIC, mage.cards.o.OkoThiefOfCrowns.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oko, Thief of Crowns", 77, Rarity.MYTHIC, mage.cards.o.OkoThiefOfCrowns.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Outlaws' Merriment", 51, Rarity.RARE, mage.cards.o.OutlawsMerriment.class));
        cards.add(new SetCardInfo("Overwhelming Forces", 17, Rarity.MYTHIC, mage.cards.o.OverwhelmingForces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overwhelming Forces", 69, Rarity.MYTHIC, mage.cards.o.OverwhelmingForces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pariah", 5, Rarity.RARE, mage.cards.p.Pariah.class));
        cards.add(new SetCardInfo("Path to Exile", 6, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pest Infestation", 30, Rarity.RARE, mage.cards.p.PestInfestation.class));
        cards.add(new SetCardInfo("Primal Command", 31, Rarity.RARE, mage.cards.p.PrimalCommand.class));
        cards.add(new SetCardInfo("Primal Might", 32, Rarity.RARE, mage.cards.p.PrimalMight.class));
        cards.add(new SetCardInfo("Reanimate", 18, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Repulse", 13, Rarity.UNCOMMON, mage.cards.r.Repulse.class));
        cards.add(new SetCardInfo("Ride Down", 52, Rarity.UNCOMMON, mage.cards.r.RideDown.class));
        cards.add(new SetCardInfo("Savage Smash", 53, Rarity.UNCOMMON, mage.cards.s.SavageSmash.class));
        cards.add(new SetCardInfo("Siphon Insight", 54, Rarity.RARE, mage.cards.s.SiphonInsight.class));
        cards.add(new SetCardInfo("Skewer the Critics", 26, Rarity.UNCOMMON, mage.cards.s.SkewerTheCritics.class));
        cards.add(new SetCardInfo("Skullcrack", 27, Rarity.RARE, mage.cards.s.Skullcrack.class));
        cards.add(new SetCardInfo("Surgical Extraction", 19, Rarity.RARE, mage.cards.s.SurgicalExtraction.class));
        cards.add(new SetCardInfo("Terminal Agony", 55, Rarity.UNCOMMON, mage.cards.t.TerminalAgony.class));
        cards.add(new SetCardInfo("Thornado", 33, Rarity.UNCOMMON, mage.cards.t.Thornado.class));
        cards.add(new SetCardInfo("Thoughtseize", 20, Rarity.MYTHIC, mage.cards.t.Thoughtseize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thoughtseize", 70, Rarity.MYTHIC, mage.cards.t.Thoughtseize.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tyrant's Scorn", 56, Rarity.UNCOMMON, mage.cards.t.TyrantsScorn.class));
        cards.add(new SetCardInfo("Unlicensed Hearse", 64, Rarity.RARE, mage.cards.u.UnlicensedHearse.class));
        cards.add(new SetCardInfo("Vanishing Verse", 57, Rarity.RARE, mage.cards.v.VanishingVerse.class));
        cards.add(new SetCardInfo("Villainous Wealth", 58, Rarity.RARE, mage.cards.v.VillainousWealth.class));
        cards.add(new SetCardInfo("Void Rend", 59, Rarity.RARE, mage.cards.v.VoidRend.class));
        cards.add(new SetCardInfo("Voidslime", 60, Rarity.RARE, mage.cards.v.Voidslime.class));
    }
}
