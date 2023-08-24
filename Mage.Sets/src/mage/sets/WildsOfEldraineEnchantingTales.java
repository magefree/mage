package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class WildsOfEldraineEnchantingTales extends ExpansionSet {

    private static final WildsOfEldraineEnchantingTales instance = new WildsOfEldraineEnchantingTales();

    public static WildsOfEldraineEnchantingTales getInstance() {
        return instance;
    }

    private WildsOfEldraineEnchantingTales() {
        super("Wilds of Eldraine: Enchanting Tales", "WOT", ExpansionSet.buildDate(2023, 9, 8), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 63;

        cards.add(new SetCardInfo("Aggravated Assault", 39, Rarity.RARE, mage.cards.a.AggravatedAssault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aggravated Assault", 76, Rarity.RARE, mage.cards.a.AggravatedAssault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aggravated Assault", 96, Rarity.RARE, mage.cards.a.AggravatedAssault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("As Foretold", 14, Rarity.RARE, mage.cards.a.AsForetold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("As Foretold", 68, Rarity.RARE, mage.cards.a.AsForetold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("As Foretold", 88, Rarity.RARE, mage.cards.a.AsForetold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterblossom", 27, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterblossom", 72, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitterblossom", 92, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blind Obedience", 1, Rarity.RARE, mage.cards.b.BlindObedience.class));
        cards.add(new SetCardInfo("Blood Moon", 40, Rarity.MYTHIC, mage.cards.b.BloodMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Moon", 77, Rarity.MYTHIC, mage.cards.b.BloodMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Moon", 97, Rarity.MYTHIC, mage.cards.b.BloodMoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Compulsion", 15, Rarity.UNCOMMON, mage.cards.c.Compulsion.class));
        cards.add(new SetCardInfo("Copy Enchantment", 16, Rarity.RARE, mage.cards.c.CopyEnchantment.class));
        cards.add(new SetCardInfo("Curiosity", 17, Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Dark Tutelage", 28, Rarity.UNCOMMON, mage.cards.d.DarkTutelage.class));
        cards.add(new SetCardInfo("Dawn of Hope", 2, Rarity.RARE, mage.cards.d.DawnOfHope.class));
        cards.add(new SetCardInfo("Defense of the Heart", 100, Rarity.MYTHIC, mage.cards.d.DefenseOfTheHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defense of the Heart", 51, Rarity.MYTHIC, mage.cards.d.DefenseOfTheHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defense of the Heart", 80, Rarity.MYTHIC, mage.cards.d.DefenseOfTheHeart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doubling Season", 101, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doubling Season", 52, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doubling Season", 81, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon Mantle", 41, Rarity.UNCOMMON, mage.cards.d.DragonMantle.class));
        cards.add(new SetCardInfo("Fiery Emancipation", 42, Rarity.RARE, mage.cards.f.FieryEmancipation.class));
        cards.add(new SetCardInfo("Forced Fruition", 18, Rarity.RARE, mage.cards.f.ForcedFruition.class));
        cards.add(new SetCardInfo("Fraying Sanity", 19, Rarity.RARE, mage.cards.f.FrayingSanity.class));
        cards.add(new SetCardInfo("Garruk's Uprising", 53, Rarity.UNCOMMON, mage.cards.g.GarruksUprising.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 43, Rarity.RARE, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Grasp of Fate", 3, Rarity.UNCOMMON, mage.cards.g.GraspOfFate.class));
        cards.add(new SetCardInfo("Grave Pact", 29, Rarity.MYTHIC, mage.cards.g.GravePact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grave Pact", 73, Rarity.MYTHIC, mage.cards.g.GravePact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grave Pact", 93, Rarity.MYTHIC, mage.cards.g.GravePact.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greater Auramancy", 4, Rarity.MYTHIC, mage.cards.g.GreaterAuramancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greater Auramancy", 64, Rarity.MYTHIC, mage.cards.g.GreaterAuramancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greater Auramancy", 84, Rarity.MYTHIC, mage.cards.g.GreaterAuramancy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Griffin Aerie", 5, Rarity.UNCOMMON, mage.cards.g.GriffinAerie.class));
        cards.add(new SetCardInfo("Ground Seal", 54, Rarity.UNCOMMON, mage.cards.g.GroundSeal.class));
        cards.add(new SetCardInfo("Hardened Scales", 55, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Hatching Plans", 20, Rarity.UNCOMMON, mage.cards.h.HatchingPlans.class));
        cards.add(new SetCardInfo("Impact Tremors", 44, Rarity.UNCOMMON, mage.cards.i.ImpactTremors.class));
        cards.add(new SetCardInfo("Intangible Virtue", 6, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Intruder Alarm", 21, Rarity.RARE, mage.cards.i.IntruderAlarm.class));
        cards.add(new SetCardInfo("Karmic Justice", 65, Rarity.RARE, mage.cards.k.KarmicJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karmic Justice", 7, Rarity.RARE, mage.cards.k.KarmicJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karmic Justice", 85, Rarity.RARE, mage.cards.k.KarmicJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kindred Discovery", 22, Rarity.MYTHIC, mage.cards.k.KindredDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kindred Discovery", 69, Rarity.MYTHIC, mage.cards.k.KindredDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kindred Discovery", 89, Rarity.MYTHIC, mage.cards.k.KindredDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knightly Valor", 8, Rarity.UNCOMMON, mage.cards.k.KnightlyValor.class));
        cards.add(new SetCardInfo("Land Tax", 66, Rarity.MYTHIC, mage.cards.l.LandTax.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Land Tax", 86, Rarity.MYTHIC, mage.cards.l.LandTax.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Land Tax", 9, Rarity.MYTHIC, mage.cards.l.LandTax.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leyline of Abundance", 56, Rarity.RARE, mage.cards.l.LeylineOfAbundance.class));
        cards.add(new SetCardInfo("Leyline of Anticipation", 23, Rarity.RARE, mage.cards.l.LeylineOfAnticipation.class));
        cards.add(new SetCardInfo("Leyline of Lightning", 45, Rarity.RARE, mage.cards.l.LeylineOfLightning.class));
        cards.add(new SetCardInfo("Leyline of Sanctity", 10, Rarity.RARE, mage.cards.l.LeylineOfSanctity.class));
        cards.add(new SetCardInfo("Leyline of the Void", 30, Rarity.RARE, mage.cards.l.LeylineOfTheVoid.class));
        cards.add(new SetCardInfo("Mana Flare", 46, Rarity.RARE, mage.cards.m.ManaFlare.class));
        cards.add(new SetCardInfo("Nature's Will", 102, Rarity.RARE, mage.cards.n.NaturesWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nature's Will", 57, Rarity.RARE, mage.cards.n.NaturesWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nature's Will", 82, Rarity.RARE, mage.cards.n.NaturesWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necropotence", 31, Rarity.MYTHIC, mage.cards.n.Necropotence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necropotence", 74, Rarity.MYTHIC, mage.cards.n.Necropotence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necropotence", 94, Rarity.MYTHIC, mage.cards.n.Necropotence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Omniscience", 24, Rarity.MYTHIC, mage.cards.o.Omniscience.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Omniscience", 70, Rarity.MYTHIC, mage.cards.o.Omniscience.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Omniscience", 90, Rarity.MYTHIC, mage.cards.o.Omniscience.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oppression", 32, Rarity.RARE, mage.cards.o.Oppression.class));
        cards.add(new SetCardInfo("Oversold Cemetery", 33, Rarity.RARE, mage.cards.o.OversoldCemetery.class));
        cards.add(new SetCardInfo("Parallel Lives", 103, Rarity.MYTHIC, mage.cards.p.ParallelLives.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Parallel Lives", 58, Rarity.MYTHIC, mage.cards.p.ParallelLives.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Parallel Lives", 83, Rarity.MYTHIC, mage.cards.p.ParallelLives.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Unlife", 11, Rarity.RARE, mage.cards.p.PhyrexianUnlife.class));
        cards.add(new SetCardInfo("Polluted Bonds", 34, Rarity.RARE, mage.cards.p.PollutedBonds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Bonds", 75, Rarity.RARE, mage.cards.p.PollutedBonds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Bonds", 95, Rarity.RARE, mage.cards.p.PollutedBonds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Vigor", 59, Rarity.RARE, mage.cards.p.PrimalVigor.class));
        cards.add(new SetCardInfo("Prismatic Omen", 60, Rarity.RARE, mage.cards.p.PrismaticOmen.class));
        cards.add(new SetCardInfo("Raid Bombardment", 47, Rarity.UNCOMMON, mage.cards.r.RaidBombardment.class));
        cards.add(new SetCardInfo("Repercussion", 48, Rarity.MYTHIC, mage.cards.r.Repercussion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Repercussion", 78, Rarity.MYTHIC, mage.cards.r.Repercussion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Repercussion", 98, Rarity.MYTHIC, mage.cards.r.Repercussion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rest in Peace", 12, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Rhystic Study", 25, Rarity.MYTHIC, mage.cards.r.RhysticStudy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rhystic Study", 71, Rarity.MYTHIC, mage.cards.r.RhysticStudy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rhystic Study", 91, Rarity.MYTHIC, mage.cards.r.RhysticStudy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanguine Bond", 35, Rarity.RARE, mage.cards.s.SanguineBond.class));
        cards.add(new SetCardInfo("Season of Growth", 61, Rarity.UNCOMMON, mage.cards.s.SeasonOfGrowth.class));
        cards.add(new SetCardInfo("Shared Animosity", 49, Rarity.RARE, mage.cards.s.SharedAnimosity.class));
        cards.add(new SetCardInfo("Smothering Tithe", 13, Rarity.MYTHIC, mage.cards.s.SmotheringTithe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smothering Tithe", 67, Rarity.MYTHIC, mage.cards.s.SmotheringTithe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smothering Tithe", 87, Rarity.MYTHIC, mage.cards.s.SmotheringTithe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sneak Attack", 50, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sneak Attack", 79, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sneak Attack", 99, Rarity.MYTHIC, mage.cards.s.SneakAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spreading Seas", 26, Rarity.UNCOMMON, mage.cards.s.SpreadingSeas.class));
        cards.add(new SetCardInfo("Stab Wound", 36, Rarity.UNCOMMON, mage.cards.s.StabWound.class));
        cards.add(new SetCardInfo("Unnatural Growth", 62, Rarity.RARE, mage.cards.u.UnnaturalGrowth.class));
        cards.add(new SetCardInfo("Utopia Sprawl", 63, Rarity.UNCOMMON, mage.cards.u.UtopiaSprawl.class));
        cards.add(new SetCardInfo("Vampiric Rites", 37, Rarity.UNCOMMON, mage.cards.v.VampiricRites.class));
        cards.add(new SetCardInfo("Waste Not", 38, Rarity.RARE, mage.cards.w.WasteNot.class));
    }
}
