package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FinalFantasy extends ExpansionSet {

    private static final FinalFantasy instance = new FinalFantasy();

    public static FinalFantasy getInstance() {
        return instance;
    }

    private FinalFantasy() {
        super("Final Fantasy", "FIN", ExpansionSet.buildDate(2025, 6, 13), SetType.EXPANSION);
        this.blockName = "Final Fantasy"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Adventurer's Inn", 271, Rarity.COMMON, mage.cards.a.AdventurersInn.class));
        cards.add(new SetCardInfo("Black Mage's Rod", 90, Rarity.COMMON, mage.cards.b.BlackMagesRod.class));
        cards.add(new SetCardInfo("Chaos, the Endless", 221, Rarity.UNCOMMON, mage.cards.c.ChaosTheEndless.class));
        cards.add(new SetCardInfo("Cloud, Planet's Champion", 552, Rarity.MYTHIC, mage.cards.c.CloudPlanetsChampion.class));
        cards.add(new SetCardInfo("Commune with Beavers", 102, Rarity.COMMON, mage.cards.c.CommuneWithBeavers.class));
        cards.add(new SetCardInfo("Cooking Campsite", 31, Rarity.UNCOMMON, mage.cards.c.CookingCampsite.class));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 218, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 394, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 483, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garland, Knight of Cornelia", 221, Rarity.UNCOMMON, mage.cards.g.GarlandKnightOfCornelia.class));
        cards.add(new SetCardInfo("Gladiolus Amicitia", 224, Rarity.UNCOMMON, mage.cards.g.GladiolusAmicitia.class));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 218, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 394, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 483, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jumbo Cactuar", 191, Rarity.RARE, mage.cards.j.JumboCactuar.class));
        cards.add(new SetCardInfo("Sazh's Chocobo", 200, Rarity.UNCOMMON, mage.cards.s.SazhsChocobo.class));
        cards.add(new SetCardInfo("Sephiroth, Planet's Heir", 553, Rarity.MYTHIC, mage.cards.s.SephirothPlanetsHeir.class));
        cards.add(new SetCardInfo("Shantotto, Tactician Magician", 241, Rarity.UNCOMMON, mage.cards.s.ShantottoTacticianMagician.class));
        cards.add(new SetCardInfo("Shinryu, Transcendent Rival", 127, Rarity.RARE, mage.cards.s.ShinryuTranscendentRival.class));
        cards.add(new SetCardInfo("Sidequest: Catch a Fish", 31, Rarity.UNCOMMON, mage.cards.s.SidequestCatchAFish.class));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 242, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 348, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 508, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stiltzkin, Moogle Merchant", 34, Rarity.RARE, mage.cards.s.StiltzkinMoogleMerchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stiltzkin, Moogle Merchant", 327, Rarity.RARE, mage.cards.s.StiltzkinMoogleMerchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Shiva", 78, Rarity.UNCOMMON, mage.cards.s.SummonShiva.class));
        cards.add(new SetCardInfo("Tonberry", 122, Rarity.UNCOMMON, mage.cards.t.Tonberry.class));
        cards.add(new SetCardInfo("Ultimecia, Omnipotent", 247, Rarity.UNCOMMON, mage.cards.u.UltimeciaOmnipotent.class));
        cards.add(new SetCardInfo("Ultimecia, Time Sorceress", 247, Rarity.UNCOMMON, mage.cards.u.UltimeciaTimeSorceress.class));
        cards.add(new SetCardInfo("White Mage's Staff", 42, Rarity.COMMON, mage.cards.w.WhiteMagesStaff.class));
        cards.add(new SetCardInfo("Zell Dincht", 170, Rarity.RARE, mage.cards.z.ZellDincht.class));
        cards.add(new SetCardInfo("Zenos yae Galvus", 127, Rarity.RARE, mage.cards.z.ZenosYaeGalvus.class));
    }
}
