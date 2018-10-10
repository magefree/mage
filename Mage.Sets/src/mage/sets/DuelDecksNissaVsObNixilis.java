package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksNissaVsObNixilis extends ExpansionSet {

    private static final DuelDecksNissaVsObNixilis instance = new DuelDecksNissaVsObNixilis();

    public static DuelDecksNissaVsObNixilis getInstance() {
        return instance;
    }

    private DuelDecksNissaVsObNixilis() {
        super("Duel Decks: Nissa vs. Ob Nixilis", "DDR", ExpansionSet.buildDate(2016, 9, 2), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abundance", 2, Rarity.RARE, mage.cards.a.Abundance.class));
        cards.add(new SetCardInfo("Altar's Reap", 37, Rarity.COMMON, mage.cards.a.AltarsReap.class));
        cards.add(new SetCardInfo("Ambition's Cost", 38, Rarity.UNCOMMON, mage.cards.a.AmbitionsCost.class));
        cards.add(new SetCardInfo("Bala Ged Scorpion", 39, Rarity.COMMON, mage.cards.b.BalaGedScorpion.class));
        cards.add(new SetCardInfo("Blistergrub", 40, Rarity.COMMON, mage.cards.b.Blistergrub.class));
        cards.add(new SetCardInfo("Briarhorn", 3, Rarity.UNCOMMON, mage.cards.b.Briarhorn.class));
        cards.add(new SetCardInfo("Cadaver Imp", 41, Rarity.COMMON, mage.cards.c.CadaverImp.class));
        cards.add(new SetCardInfo("Carrier Thrall", 42, Rarity.UNCOMMON, mage.cards.c.CarrierThrall.class));
        cards.add(new SetCardInfo("Citanul Woodreaders", 4, Rarity.COMMON, mage.cards.c.CitanulWoodreaders.class));
        cards.add(new SetCardInfo("Civic Wayfinder", 5, Rarity.COMMON, mage.cards.c.CivicWayfinder.class));
        cards.add(new SetCardInfo("Cloudthresher", 6, Rarity.RARE, mage.cards.c.Cloudthresher.class));
        cards.add(new SetCardInfo("Crop Rotation", 7, Rarity.COMMON, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Demon's Grasp", 43, Rarity.COMMON, mage.cards.d.DemonsGrasp.class));
        cards.add(new SetCardInfo("Desecration Demon", 44, Rarity.RARE, mage.cards.d.DesecrationDemon.class));
        cards.add(new SetCardInfo("Despoiler of Souls", 45, Rarity.RARE, mage.cards.d.DespoilerOfSouls.class));
        cards.add(new SetCardInfo("Disfigure", 46, Rarity.COMMON, mage.cards.d.Disfigure.class));
        cards.add(new SetCardInfo("Doom Blade", 47, Rarity.UNCOMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Elvish Visionary", 8, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Fertile Thicket", 27, Rarity.COMMON, mage.cards.f.FertileThicket.class));
        cards.add(new SetCardInfo("Fertilid", 9, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Fetid Imp", 48, Rarity.COMMON, mage.cards.f.FetidImp.class));
        cards.add(new SetCardInfo("Forest", 31, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 32, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 33, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 34, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 35, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Imp", 49, Rarity.COMMON, mage.cards.f.FoulImp.class));
        cards.add(new SetCardInfo("Gaea's Blessing", 10, Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Giant Scorpion", 50, Rarity.COMMON, mage.cards.g.GiantScorpion.class));
        cards.add(new SetCardInfo("Gilt-Leaf Seer", 11, Rarity.COMMON, mage.cards.g.GiltLeafSeer.class));
        cards.add(new SetCardInfo("Grim Discovery", 51, Rarity.COMMON, mage.cards.g.GrimDiscovery.class));
        cards.add(new SetCardInfo("Hideous End", 52, Rarity.COMMON, mage.cards.h.HideousEnd.class));
        cards.add(new SetCardInfo("Indulgent Tormentor", 53, Rarity.RARE, mage.cards.i.IndulgentTormentor.class));
        cards.add(new SetCardInfo("Innocent Blood", 54, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Jaddi Lifestrider", 12, Rarity.UNCOMMON, mage.cards.j.JaddiLifestrider.class));
        cards.add(new SetCardInfo("Khalni Garden", 28, Rarity.COMMON, mage.cards.k.KhalniGarden.class));
        cards.add(new SetCardInfo("Leechridden Swamp", 65, Rarity.UNCOMMON, mage.cards.l.LeechriddenSwamp.class));
        cards.add(new SetCardInfo("Mire's Toll", 55, Rarity.COMMON, mage.cards.m.MiresToll.class));
        cards.add(new SetCardInfo("Mosswort Bridge", 29, Rarity.RARE, mage.cards.m.MosswortBridge.class));
        cards.add(new SetCardInfo("Natural Connection", 13, Rarity.COMMON, mage.cards.n.NaturalConnection.class));
        cards.add(new SetCardInfo("Nissa's Chosen", 14, Rarity.COMMON, mage.cards.n.NissasChosen.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", 1, Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Oakgnarl Warrior", 15, Rarity.COMMON, mage.cards.o.OakgnarlWarrior.class));
        cards.add(new SetCardInfo("Ob Nixilis Reignited", 36, Rarity.MYTHIC, mage.cards.o.ObNixilisReignited.class));
        cards.add(new SetCardInfo("Oran-Rief Hydra", 16, Rarity.RARE, mage.cards.o.OranRiefHydra.class));
        cards.add(new SetCardInfo("Oran-Rief Invoker", 17, Rarity.COMMON, mage.cards.o.OranRiefInvoker.class));
        cards.add(new SetCardInfo("Pestilence Demon", 56, Rarity.RARE, mage.cards.p.PestilenceDemon.class));
        cards.add(new SetCardInfo("Priest of the Blood Rite", 57, Rarity.RARE, mage.cards.p.PriestOfTheBloodRite.class));
        cards.add(new SetCardInfo("Quest for the Gravelord", 58, Rarity.UNCOMMON, mage.cards.q.QuestForTheGravelord.class));
        cards.add(new SetCardInfo("Renegade Demon", 59, Rarity.COMMON, mage.cards.r.RenegadeDemon.class));
        cards.add(new SetCardInfo("Saddleback Lagac", 18, Rarity.COMMON, mage.cards.s.SaddlebackLagac.class));
        cards.add(new SetCardInfo("Scythe Leopard", 19, Rarity.UNCOMMON, mage.cards.s.ScytheLeopard.class));
        cards.add(new SetCardInfo("Seek the Horizon", 20, Rarity.UNCOMMON, mage.cards.s.SeekTheHorizon.class));
        cards.add(new SetCardInfo("Shadows of the Past", 60, Rarity.UNCOMMON, mage.cards.s.ShadowsOfThePast.class));
        cards.add(new SetCardInfo("Smallpox", 61, Rarity.UNCOMMON, mage.cards.s.Smallpox.class));
        cards.add(new SetCardInfo("Squelching Leeches", 62, Rarity.UNCOMMON, mage.cards.s.SquelchingLeeches.class));
        cards.add(new SetCardInfo("Swamp", 66, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 67, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 68, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 69, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 70, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tendrils of Corruption", 63, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Thicket Elemental", 21, Rarity.RARE, mage.cards.t.ThicketElemental.class));
        cards.add(new SetCardInfo("Thornweald Archer", 22, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Treetop Village", 30, Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Unhallowed Pact", 64, Rarity.COMMON, mage.cards.u.UnhallowedPact.class));
        cards.add(new SetCardInfo("Vines of the Recluse", 23, Rarity.COMMON, mage.cards.v.VinesOfTheRecluse.class));
        cards.add(new SetCardInfo("Walker of the Grove", 24, Rarity.UNCOMMON, mage.cards.w.WalkerOfTheGrove.class));
        cards.add(new SetCardInfo("Woodborn Behemoth", 26, Rarity.UNCOMMON, mage.cards.w.WoodbornBehemoth.class));
        cards.add(new SetCardInfo("Wood Elves", 25, Rarity.COMMON, mage.cards.w.WoodElves.class));
    }
}
