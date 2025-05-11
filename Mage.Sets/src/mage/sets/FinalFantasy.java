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
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("A Realm Reborn", 196, Rarity.RARE, mage.cards.a.ARealmReborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("A Realm Reborn", 344, Rarity.RARE, mage.cards.a.ARealmReborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adventurer's Inn", 271, Rarity.COMMON, mage.cards.a.AdventurersInn.class));
        cards.add(new SetCardInfo("Black Mage's Rod", 90, Rarity.COMMON, mage.cards.b.BlackMagesRod.class));
        cards.add(new SetCardInfo("Chaos, the Endless", 221, Rarity.UNCOMMON, mage.cards.c.ChaosTheEndless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaos, the Endless", 486, Rarity.UNCOMMON, mage.cards.c.ChaosTheEndless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 216, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 407, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 408, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 409, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 410, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 411, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 412, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 413, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 414, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 415, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 416, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 417, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 418, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 419, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 420, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cid, Timeless Artificer", 480, Rarity.UNCOMMON, mage.cards.c.CidTimelessArtificer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clive, Ifrit's Dominant", 133, Rarity.MYTHIC, mage.cards.c.CliveIfritsDominant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clive, Ifrit's Dominant", 318, Rarity.MYTHIC, mage.cards.c.CliveIfritsDominant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clive, Ifrit's Dominant", 385, Rarity.MYTHIC, mage.cards.c.CliveIfritsDominant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clive, Ifrit's Dominant", 458, Rarity.MYTHIC, mage.cards.c.CliveIfritsDominant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clive, Ifrit's Dominant", 530, Rarity.MYTHIC, mage.cards.c.CliveIfritsDominant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Midgar Mercenary", 10, Rarity.MYTHIC, mage.cards.c.CloudMidgarMercenary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Midgar Mercenary", 375, Rarity.MYTHIC, mage.cards.c.CloudMidgarMercenary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Midgar Mercenary", 427, Rarity.MYTHIC, mage.cards.c.CloudMidgarMercenary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Midgar Mercenary", 520, Rarity.MYTHIC, mage.cards.c.CloudMidgarMercenary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Planet's Champion", 482, Rarity.MYTHIC, mage.cards.c.CloudPlanetsChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Planet's Champion", 552, Rarity.MYTHIC, mage.cards.c.CloudPlanetsChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Commune with Beavers", 182, Rarity.COMMON, mage.cards.c.CommuneWithBeavers.class));
        cards.add(new SetCardInfo("Cooking Campsite", 31, Rarity.UNCOMMON, mage.cards.c.CookingCampsite.class));
        cards.add(new SetCardInfo("Dark Confidant", 334, Rarity.MYTHIC, mage.cards.d.DarkConfidant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Confidant", 94, Rarity.MYTHIC, mage.cards.d.DarkConfidant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragoon's Wyvern", 49, Rarity.COMMON, mage.cards.d.DragoonsWyvern.class));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 218, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 394, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 483, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emet-Selch, Unsundered", 539, Rarity.MYTHIC, mage.cards.e.EmetSelchUnsundered.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fang, Fearless l'Cie", 381, Rarity.UNCOMMON, mage.cards.f.FangFearlessLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fang, Fearless l'Cie", 446, Rarity.UNCOMMON, mage.cards.f.FangFearlessLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fang, Fearless l'Cie", 526, Rarity.UNCOMMON, mage.cards.f.FangFearlessLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fang, Fearless l'Cie", 99, Rarity.UNCOMMON, mage.cards.f.FangFearlessLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Magic", 136, Rarity.UNCOMMON, mage.cards.f.FireMagic.class));
        cards.add(new SetCardInfo("Forest", 306, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 307, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 308, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 576, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Garland, Knight of Cornelia", 221, Rarity.UNCOMMON, mage.cards.g.GarlandKnightOfCornelia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garland, Knight of Cornelia", 486, Rarity.UNCOMMON, mage.cards.g.GarlandKnightOfCornelia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gladiolus Amicitia", 224, Rarity.UNCOMMON, mage.cards.g.GladiolusAmicitia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gladiolus Amicitia", 489, Rarity.UNCOMMON, mage.cards.g.GladiolusAmicitia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 218, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 394, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 483, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hades, Sorcerer of Eld", 539, Rarity.MYTHIC, mage.cards.h.HadesSorcererOfEld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ice Magic", 56, Rarity.COMMON, mage.cards.i.IceMagic.class));
        cards.add(new SetCardInfo("Ifrit, Warden of Inferno", 133, Rarity.MYTHIC, mage.cards.i.IfritWardenOfInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ifrit, Warden of Inferno", 318, Rarity.MYTHIC, mage.cards.i.IfritWardenOfInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ifrit, Warden of Inferno", 385, Rarity.MYTHIC, mage.cards.i.IfritWardenOfInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ifrit, Warden of Inferno", 458, Rarity.MYTHIC, mage.cards.i.IfritWardenOfInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ifrit, Warden of Inferno", 530, Rarity.MYTHIC, mage.cards.i.IfritWardenOfInferno.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 297, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 298, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 299, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 573, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jidoor, Aristocratic Capital", 284, Rarity.RARE, mage.cards.j.JidoorAristocraticCapital.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jidoor, Aristocratic Capital", 311, Rarity.RARE, mage.cards.j.JidoorAristocraticCapital.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jumbo Cactuar", 191, Rarity.RARE, mage.cards.j.JumboCactuar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jumbo Cactuar", 343, Rarity.RARE, mage.cards.j.JumboCactuar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malboro", 106, Rarity.COMMON, mage.cards.m.Malboro.class));
        cards.add(new SetCardInfo("Mountain", 303, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 304, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 305, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 575, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 294, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 295, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 296, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 572, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ragnarok, Divine Deliverance", "381b", Rarity.UNCOMMON, mage.cards.r.RagnarokDivineDeliverance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ragnarok, Divine Deliverance", "446b", Rarity.UNCOMMON, mage.cards.r.RagnarokDivineDeliverance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ragnarok, Divine Deliverance", "526b", Rarity.UNCOMMON, mage.cards.r.RagnarokDivineDeliverance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ragnarok, Divine Deliverance", "99b", Rarity.UNCOMMON, mage.cards.r.RagnarokDivineDeliverance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sazh's Chocobo", 200, Rarity.UNCOMMON, mage.cards.s.SazhsChocobo.class));
        cards.add(new SetCardInfo("Sephiroth, Planet's Heir", 505, Rarity.MYTHIC, mage.cards.s.SephirothPlanetsHeir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sephiroth, Planet's Heir", 553, Rarity.MYTHIC, mage.cards.s.SephirothPlanetsHeir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shantotto, Tactician Magician", 241, Rarity.UNCOMMON, mage.cards.s.ShantottoTacticianMagician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shantotto, Tactician Magician", 507, Rarity.UNCOMMON, mage.cards.s.ShantottoTacticianMagician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shinryu, Transcendent Rival", 127, Rarity.RARE, mage.cards.s.ShinryuTranscendentRival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shinryu, Transcendent Rival", 384, Rarity.RARE, mage.cards.s.ShinryuTranscendentRival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shinryu, Transcendent Rival", 455, Rarity.RARE, mage.cards.s.ShinryuTranscendentRival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shinryu, Transcendent Rival", 529, Rarity.RARE, mage.cards.s.ShinryuTranscendentRival.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sidequest: Catch a Fish", 31, Rarity.UNCOMMON, mage.cards.s.SidequestCatchAFish.class));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 242, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 348, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sin, Spira's Punishment", 508, Rarity.RARE, mage.cards.s.SinSpirasPunishment.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starting Town", 289, Rarity.RARE, mage.cards.s.StartingTown.class));
        cards.add(new SetCardInfo("Stiltzkin, Moogle Merchant", 327, Rarity.RARE, mage.cards.s.StiltzkinMoogleMerchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stiltzkin, Moogle Merchant", 34, Rarity.RARE, mage.cards.s.StiltzkinMoogleMerchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stiltzkin, Moogle Merchant", 433, Rarity.RARE, mage.cards.s.StiltzkinMoogleMerchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Bahamut", 1, Rarity.MYTHIC, mage.cards.s.SummonBahamut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Bahamut", 356, Rarity.MYTHIC, mage.cards.s.SummonBahamut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Primal Odin", 121, Rarity.RARE, mage.cards.s.SummonPrimalOdin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Primal Odin", 365, Rarity.RARE, mage.cards.s.SummonPrimalOdin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Shiva", 362, Rarity.UNCOMMON, mage.cards.s.SummonShiva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Summon: Shiva", 78, Rarity.UNCOMMON, mage.cards.s.SummonShiva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 300, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 301, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 302, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 574, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("The Crystal's Chosen", 14, Rarity.UNCOMMON, mage.cards.t.TheCrystalsChosen.class));
        cards.add(new SetCardInfo("Tifa Lockhart", 206, Rarity.RARE, mage.cards.t.TifaLockhart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa Lockhart", 391, Rarity.RARE, mage.cards.t.TifaLockhart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa Lockhart", 473, Rarity.RARE, mage.cards.t.TifaLockhart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa Lockhart", 536, Rarity.RARE, mage.cards.t.TifaLockhart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tonberry", 122, Rarity.UNCOMMON, mage.cards.t.Tonberry.class));
        cards.add(new SetCardInfo("Town Greeter", 209, Rarity.COMMON, mage.cards.t.TownGreeter.class));
        cards.add(new SetCardInfo("Ultimecia, Omnipotent", 247, Rarity.UNCOMMON, mage.cards.u.UltimeciaOmnipotent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ultimecia, Omnipotent", 513, Rarity.UNCOMMON, mage.cards.u.UltimeciaOmnipotent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ultimecia, Time Sorceress", 247, Rarity.UNCOMMON, mage.cards.u.UltimeciaTimeSorceress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ultimecia, Time Sorceress", 513, Rarity.UNCOMMON, mage.cards.u.UltimeciaTimeSorceress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanille, Cheerful l'Cie", 211, Rarity.UNCOMMON, mage.cards.v.VanilleCheerfulLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanille, Cheerful l'Cie", 392, Rarity.UNCOMMON, mage.cards.v.VanilleCheerfulLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanille, Cheerful l'Cie", 475, Rarity.UNCOMMON, mage.cards.v.VanilleCheerfulLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanille, Cheerful l'Cie", 537, Rarity.UNCOMMON, mage.cards.v.VanilleCheerfulLCie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wastes", 309, Rarity.COMMON, mage.cards.w.Wastes.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("White Mage's Staff", 42, Rarity.COMMON, mage.cards.w.WhiteMagesStaff.class));
        cards.add(new SetCardInfo("Zanarkand, Ancient Metropolis", 293, Rarity.RARE, mage.cards.z.ZanarkandAncientMetropolis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zanarkand, Ancient Metropolis", 314, Rarity.RARE, mage.cards.z.ZanarkandAncientMetropolis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zell Dincht", 170, Rarity.RARE, mage.cards.z.ZellDincht.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zell Dincht", 468, Rarity.RARE, mage.cards.z.ZellDincht.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zenos yae Galvus", 127, Rarity.RARE, mage.cards.z.ZenosYaeGalvus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zenos yae Galvus", 384, Rarity.RARE, mage.cards.z.ZenosYaeGalvus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zenos yae Galvus", 455, Rarity.RARE, mage.cards.z.ZenosYaeGalvus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zenos yae Galvus", 529, Rarity.RARE, mage.cards.z.ZenosYaeGalvus.class, NON_FULL_USE_VARIOUS));
    }
}
