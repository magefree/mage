package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FinalFantasyCommander extends ExpansionSet {

    private static final FinalFantasyCommander instance = new FinalFantasyCommander();

    public static FinalFantasyCommander getInstance() {
        return instance;
    }

    private FinalFantasyCommander() {
        super("Final Fantasy Commander", "FIC", ExpansionSet.buildDate(2025, 6, 13), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Alisaie Leveilleur", 9, Rarity.RARE, mage.cards.a.AlisaieLeveilleur.class));
        cards.add(new SetCardInfo("Alphinaud Leveilleur", 33, Rarity.RARE, mage.cards.a.AlphinaudLeveilleur.class));
        cards.add(new SetCardInfo("Altered Ego", 317, Rarity.RARE, mage.cards.a.AlteredEgo.class));
        cards.add(new SetCardInfo("An Offer You Can't Refuse", 267, Rarity.UNCOMMON, mage.cards.a.AnOfferYouCantRefuse.class));
        cards.add(new SetCardInfo("Angel of the Ruins", 229, Rarity.UNCOMMON, mage.cards.a.AngelOfTheRuins.class));
        cards.add(new SetCardInfo("Anger", 289, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Archaeomancer's Map", 230, Rarity.RARE, mage.cards.a.ArchaeomancersMap.class));
        cards.add(new SetCardInfo("Archfiend of Depravity", 273, Rarity.RARE, mage.cards.a.ArchfiendOfDepravity.class));
        cards.add(new SetCardInfo("Archmage Emeritus", 261, Rarity.RARE, mage.cards.a.ArchmageEmeritus.class));
        cards.add(new SetCardInfo("Austere Command", 231, Rarity.RARE, mage.cards.a.AustereCommand.class));
        cards.add(new SetCardInfo("Authority of the Consuls", 232, Rarity.RARE, mage.cards.a.AuthorityOfTheConsuls.class));
        cards.add(new SetCardInfo("Baleful Strix", 318, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Bane of Progress", 299, Rarity.RARE, mage.cards.b.BaneOfProgress.class));
        cards.add(new SetCardInfo("Bastion Protector", 233, Rarity.RARE, mage.cards.b.BastionProtector.class));
        cards.add(new SetCardInfo("Bastion of Remembrance", 274, Rarity.UNCOMMON, mage.cards.b.BastionOfRemembrance.class));
        cards.add(new SetCardInfo("Bedevil", 319, Rarity.RARE, mage.cards.b.Bedevil.class));
        cards.add(new SetCardInfo("Behemoth Sledge", 320, Rarity.UNCOMMON, mage.cards.b.BehemothSledge.class));
        cards.add(new SetCardInfo("Big Score", 290, Rarity.COMMON, mage.cards.b.BigScore.class));
        cards.add(new SetCardInfo("Bred for the Hunt", 321, Rarity.UNCOMMON, mage.cards.b.BredForTheHunt.class));
        cards.add(new SetCardInfo("Bronze Guardian", 234, Rarity.RARE, mage.cards.b.BronzeGuardian.class));
        cards.add(new SetCardInfo("Celes, Rune Knight", 1, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 167, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 201, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 209, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 220, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaos Warp", 291, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chasm Skulker", 262, Rarity.RARE, mage.cards.c.ChasmSkulker.class));
        cards.add(new SetCardInfo("Cleansing Nova", 235, Rarity.RARE, mage.cards.c.CleansingNova.class));
        cards.add(new SetCardInfo("Clever Concealment", 236, Rarity.RARE, mage.cards.c.CleverConcealment.class));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 168, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 2, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 202, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 210, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 221, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Collective Effort", 237, Rarity.RARE, mage.cards.c.CollectiveEffort.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", 292, Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class));
        cards.add(new SetCardInfo("Crackling Doom", 322, Rarity.UNCOMMON, mage.cards.c.CracklingDoom.class));
        cards.add(new SetCardInfo("Crux of Fate", 275, Rarity.RARE, mage.cards.c.CruxOfFate.class));
        cards.add(new SetCardInfo("Cultivate", 300, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Cut a Deal", 238, Rarity.UNCOMMON, mage.cards.c.CutADeal.class));
        cards.add(new SetCardInfo("Damning Verdict", 239, Rarity.RARE, mage.cards.d.DamningVerdict.class));
        cards.add(new SetCardInfo("Destroy Evil", 240, Rarity.COMMON, mage.cards.d.DestroyEvil.class));
        cards.add(new SetCardInfo("Dig Through Time", 263, Rarity.RARE, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Dispatch", 241, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Duskshell Crawler", 301, Rarity.COMMON, mage.cards.d.DuskshellCrawler.class));
        cards.add(new SetCardInfo("Exsanguinate", 276, Rarity.UNCOMMON, mage.cards.e.Exsanguinate.class));
        cards.add(new SetCardInfo("Farewell", 242, Rarity.RARE, mage.cards.f.Farewell.class));
        cards.add(new SetCardInfo("Farseek", 302, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Fight Rigging", 303, Rarity.RARE, mage.cards.f.FightRigging.class));
        cards.add(new SetCardInfo("Final Judgment", 243, Rarity.MYTHIC, mage.cards.f.FinalJudgment.class));
        cards.add(new SetCardInfo("Flayer of the Hatebound", 293, Rarity.RARE, mage.cards.f.FlayerOfTheHatebound.class));
        cards.add(new SetCardInfo("Forgotten Ancient", 304, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Furious Rise", 294, Rarity.UNCOMMON, mage.cards.f.FuriousRise.class));
        cards.add(new SetCardInfo("Generous Patron", 305, Rarity.RARE, mage.cards.g.GenerousPatron.class));
        cards.add(new SetCardInfo("Grateful Apparition", 244, Rarity.UNCOMMON, mage.cards.g.GratefulApparition.class));
        cards.add(new SetCardInfo("Gyre Sage", 306, Rarity.RARE, mage.cards.g.GyreSage.class));
        cards.add(new SetCardInfo("Hardened Scales", 307, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Harmonize", 308, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Hellkite Tyrant", 295, Rarity.RARE, mage.cards.h.HellkiteTyrant.class));
        cards.add(new SetCardInfo("Herald's Horn", 228, Rarity.RARE, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Hildibrand Manderville", 173, Rarity.RARE, mage.cards.h.HildibrandManderville.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hildibrand Manderville", 83, Rarity.RARE, mage.cards.h.HildibrandManderville.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hypnotic Sprite", 264, Rarity.UNCOMMON, mage.cards.h.HypnoticSprite.class));
        cards.add(new SetCardInfo("Incubation Druid", 309, Rarity.RARE, mage.cards.i.IncubationDruid.class));
        cards.add(new SetCardInfo("Inexorable Tide", 265, Rarity.RARE, mage.cards.i.InexorableTide.class));
        cards.add(new SetCardInfo("Inspiring Call", 310, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Into the Story", 266, Rarity.UNCOMMON, mage.cards.i.IntoTheStory.class));
        cards.add(new SetCardInfo("Lethal Scheme", 277, Rarity.RARE, mage.cards.l.LethalScheme.class));
        cards.add(new SetCardInfo("Lingering Souls", 245, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Luminous Broodmoth", 246, Rarity.MYTHIC, mage.cards.l.LuminousBroodmoth.class));
        cards.add(new SetCardInfo("Morbid Opportunist", 278, Rarity.UNCOMMON, mage.cards.m.MorbidOpportunist.class));
        cards.add(new SetCardInfo("Murderous Rider", 279, Rarity.RARE, mage.cards.m.MurderousRider.class));
        cards.add(new SetCardInfo("Nature's Lore", 311, Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Night's Whisper", 280, Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Palace Jailer", 247, Rarity.UNCOMMON, mage.cards.p.PalaceJailer.class));
        cards.add(new SetCardInfo("Path of Discovery", 312, Rarity.RARE, mage.cards.p.PathOfDiscovery.class));
        cards.add(new SetCardInfo("Path to Exile", 248, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pitiless Plunderer", 281, Rarity.UNCOMMON, mage.cards.p.PitilessPlunderer.class));
        cards.add(new SetCardInfo("Professional Face-Breaker", 296, Rarity.RARE, mage.cards.p.ProfessionalFaceBreaker.class));
        cards.add(new SetCardInfo("Promise of Loyalty", 249, Rarity.RARE, mage.cards.p.PromiseOfLoyalty.class));
        cards.add(new SetCardInfo("Propaganda", 268, Rarity.UNCOMMON, mage.cards.p.Propaganda.class));
        cards.add(new SetCardInfo("Pull from Tomorrow", 269, Rarity.RARE, mage.cards.p.PullFromTomorrow.class));
        cards.add(new SetCardInfo("Puresteel Paladin", 250, Rarity.RARE, mage.cards.p.PuresteelPaladin.class));
        cards.add(new SetCardInfo("Rampant Growth", 313, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rampant Rejuvenator", 314, Rarity.RARE, mage.cards.r.RampantRejuvenator.class));
        cards.add(new SetCardInfo("Reanimate", 282, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Resourceful Defense", 251, Rarity.RARE, mage.cards.r.ResourcefulDefense.class));
        cards.add(new SetCardInfo("Rise of the Dark Realms", 283, Rarity.MYTHIC, mage.cards.r.RiseOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Rite of Replication", 270, Rarity.RARE, mage.cards.r.RiteOfReplication.class));
        cards.add(new SetCardInfo("Ruin Grinder", 297, Rarity.RARE, mage.cards.r.RuinGrinder.class));
        cards.add(new SetCardInfo("Scholar of New Horizons", 252, Rarity.RARE, mage.cards.s.ScholarOfNewHorizons.class));
        cards.add(new SetCardInfo("Secret Rendezvous", 217, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 218, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 219, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 253, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sephiroth, Fallen Hero", 182, Rarity.RARE, mage.cards.s.SephirothFallenHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sephiroth, Fallen Hero", 92, Rarity.RARE, mage.cards.s.SephirothFallenHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sepulchral Primordial", 284, Rarity.RARE, mage.cards.s.SepulchralPrimordial.class));
        cards.add(new SetCardInfo("Snuff Out", 285, Rarity.COMMON, mage.cards.s.SnuffOut.class));
        cards.add(new SetCardInfo("Stitch Together", 286, Rarity.UNCOMMON, mage.cards.s.StitchTogether.class));
        cards.add(new SetCardInfo("Stitcher's Supplier", 287, Rarity.UNCOMMON, mage.cards.s.StitchersSupplier.class));
        cards.add(new SetCardInfo("Sublime Epiphany", 271, Rarity.RARE, mage.cards.s.SublimeEpiphany.class));
        cards.add(new SetCardInfo("Sun Titan", 254, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Sunscorch Regent", 255, Rarity.RARE, mage.cards.s.SunscorchRegent.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 256, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Syphon Mind", 288, Rarity.COMMON, mage.cards.s.SyphonMind.class));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 186, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 204, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 212, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 223, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 4, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Three Visits", 315, Rarity.UNCOMMON, mage.cards.t.ThreeVisits.class));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 188, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 206, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 214, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 225, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 6, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tireless Tracker", 316, Rarity.RARE, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("Together Forever", 257, Rarity.RARE, mage.cards.t.TogetherForever.class));
        cards.add(new SetCardInfo("Torrential Gearhulk", 272, Rarity.RARE, mage.cards.t.TorrentialGearhulk.class));
        cards.add(new SetCardInfo("Tragic Arrogance", 258, Rarity.RARE, mage.cards.t.TragicArrogance.class));
        cards.add(new SetCardInfo("Unfinished Business", 259, Rarity.RARE, mage.cards.u.UnfinishedBusiness.class));
        cards.add(new SetCardInfo("Vandalblast", 298, Rarity.UNCOMMON, mage.cards.v.Vandalblast.class));
        cards.add(new SetCardInfo("Vanquish the Horde", 260, Rarity.RARE, mage.cards.v.VanquishTheHorde.class));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 191, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 207, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 215, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 226, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 7, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
    }
}
