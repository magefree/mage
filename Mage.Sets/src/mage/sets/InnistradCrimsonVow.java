package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class InnistradCrimsonVow extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Brine Comber", "Brinebound Gift", "Distracting Geist", "Clever Distraction", "Dorothea, Vengeful Victim", "Dorothea's Retribution", "Drogskol Infantry", "Drogskol Armaments", "Fearful Villager", "Fearsome Werewolf", "Howlpack Piper", "Wildsong Howler", "Kindly Ancestor", "Ancestor's Embrace", "Lantern Bearer", "Lanterns' Lift", "Mirrorhall Mimic", "Ghastly Mimicry", "Mischievous Geist", "Catlike Curiosity", "Twinblade Geist", "Twinblade Invocation", "Volatile Arsonist", "Dire-Strain Anarchist", "Weary Prisoner", "Wrathful Jailbreaker", "Weaver of Blossoms", "Blossom-Clad Werewolf");
    private static final InnistradCrimsonVow instance = new InnistradCrimsonVow();

    public static InnistradCrimsonVow getInstance() {
        return instance;
    }

    private InnistradCrimsonVow() {
        super("Innistrad: Crimson Vow", "VOW", ExpansionSet.buildDate(2021, 11, 19), SetType.EXPANSION);
        this.blockName = "Innistrad: Midnight Hunt";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;

        cards.add(new SetCardInfo("Abrade", 139, Rarity.COMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Ancestor's Embrace", 22, Rarity.COMMON, mage.cards.a.AncestorsEmbrace.class));
        cards.add(new SetCardInfo("Ancient Lumberknot", 230, Rarity.UNCOMMON, mage.cards.a.AncientLumberknot.class));
        cards.add(new SetCardInfo("Angelic Quartermaster", 2, Rarity.UNCOMMON, mage.cards.a.AngelicQuartermaster.class));
        cards.add(new SetCardInfo("Anje, Maid of Dishonor", 231, Rarity.RARE, mage.cards.a.AnjeMaidOfDishonor.class));
        cards.add(new SetCardInfo("Apprentice Sharpshooter", 185, Rarity.COMMON, mage.cards.a.ApprenticeSharpshooter.class));
        cards.add(new SetCardInfo("Archghoul of Thraben", 93, Rarity.UNCOMMON, mage.cards.a.ArchghoulOfThraben.class));
        cards.add(new SetCardInfo("Ascendant Packleader", 186, Rarity.RARE, mage.cards.a.AscendantPackleader.class));
        cards.add(new SetCardInfo("Belligerent Guest", 144, Rarity.COMMON, mage.cards.b.BelligerentGuest.class));
        cards.add(new SetCardInfo("Blood Hypnotist", 145, Rarity.UNCOMMON, mage.cards.b.BloodHypnotist.class));
        cards.add(new SetCardInfo("Bloodsworn Knight", 97, Rarity.UNCOMMON, mage.cards.b.BloodswornKnight.class));
        cards.add(new SetCardInfo("Bloodsworn Squire", 97, Rarity.UNCOMMON, mage.cards.b.BloodswornSquire.class));
        cards.add(new SetCardInfo("Blossom-Clad Werewolf", 226, Rarity.COMMON, mage.cards.b.BlossomCladWerewolf.class));
        cards.add(new SetCardInfo("Bramble Wurm", 189, Rarity.UNCOMMON, mage.cards.b.BrambleWurm.class));
        cards.add(new SetCardInfo("By Invitation Only", 5, Rarity.RARE, mage.cards.b.ByInvitationOnly.class));
        cards.add(new SetCardInfo("Change of Fortune", 150, Rarity.RARE, mage.cards.c.ChangeOfFortune.class));
        cards.add(new SetCardInfo("Clever Distraction", 9, Rarity.UNCOMMON, mage.cards.c.CleverDistraction.class));
        cards.add(new SetCardInfo("Cloaked Cadet", 192, Rarity.UNCOMMON, mage.cards.c.CloakedCadet.class));
        cards.add(new SetCardInfo("Dawnhart Geist", 8, Rarity.UNCOMMON, mage.cards.d.DawnhartGeist.class));
        cards.add(new SetCardInfo("Deathcap Glade", 261, Rarity.RARE, mage.cards.d.DeathcapGlade.class));
        cards.add(new SetCardInfo("Demonic Bargain", 103, Rarity.RARE, mage.cards.d.DemonicBargain.class));
        cards.add(new SetCardInfo("Dig Up", 197, Rarity.RARE, mage.cards.d.DigUp.class));
        cards.add(new SetCardInfo("Dire-Strain Anarchist", 181, Rarity.MYTHIC, mage.cards.d.DireStrainAnarchist.class));
        cards.add(new SetCardInfo("Distracting Geist", 9, Rarity.UNCOMMON, mage.cards.d.DistractingGeist.class));
        cards.add(new SetCardInfo("Dominating Vampire", 154, Rarity.RARE, mage.cards.d.DominatingVampire.class));
        cards.add(new SetCardInfo("Dormant Grove", 198, Rarity.UNCOMMON, mage.cards.d.DormantGrove.class));
        cards.add(new SetCardInfo("Dreadfeast Demon", 108, Rarity.RARE, mage.cards.d.DreadfeastDemon.class));
        cards.add(new SetCardInfo("Dreamroot Cascade", 262, Rarity.RARE, mage.cards.d.DreamrootCascade.class));
        cards.add(new SetCardInfo("Drogskol Armaments", 10, Rarity.COMMON, mage.cards.d.DrogskolArmaments.class));
        cards.add(new SetCardInfo("Drogskol Infantry", 10, Rarity.COMMON, mage.cards.d.DrogskolInfantry.class));
        cards.add(new SetCardInfo("Fearful Villager", 157, Rarity.COMMON, mage.cards.f.FearfulVillager.class));
        cards.add(new SetCardInfo("Fearsome Werewolf", 157, Rarity.COMMON, mage.cards.f.FearsomeWerewolf.class));
        cards.add(new SetCardInfo("Fell Stinger", 112, Rarity.UNCOMMON, mage.cards.f.FellStinger.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Geistlight Snare", 60, Rarity.UNCOMMON, mage.cards.g.GeistlightSnare.class));
        cards.add(new SetCardInfo("Geralf, Visionary Stitcher", 61, Rarity.RARE, mage.cards.g.GeralfVisionaryStitcher.class));
        cards.add(new SetCardInfo("Gluttonous Guest", 114, Rarity.COMMON, mage.cards.g.GluttonousGuest.class));
        cards.add(new SetCardInfo("Gnarled Grovestrider", 198, Rarity.UNCOMMON, mage.cards.g.GnarledGrovestrider.class));
        cards.add(new SetCardInfo("Grolnok, the Omnivore", 238, Rarity.RARE, mage.cards.g.GrolnokTheOmnivore.class));
        cards.add(new SetCardInfo("Groom's Finery", 117, Rarity.UNCOMMON, mage.cards.g.GroomsFinery.class));
        cards.add(new SetCardInfo("Gryff Rider", 15, Rarity.COMMON, mage.cards.g.GryffRider.class));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 239, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class));
        cards.add(new SetCardInfo("Hallowed Haunting", 17, Rarity.MYTHIC, mage.cards.h.HallowedHaunting.class));
        cards.add(new SetCardInfo("Headless Rider", 118, Rarity.RARE, mage.cards.h.HeadlessRider.class));
        cards.add(new SetCardInfo("Hero's Downfall", 120, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Honeymoon Hearse", 160, Rarity.UNCOMMON, mage.cards.h.HoneymoonHearse.class));
        cards.add(new SetCardInfo("Honored Heirloom", 257, Rarity.COMMON, mage.cards.h.HonoredHeirloom.class));
        cards.add(new SetCardInfo("Inspired Idea", 64, Rarity.RARE, mage.cards.i.InspiredIdea.class));
        cards.add(new SetCardInfo("Investigator's Journal", 258, Rarity.RARE, mage.cards.i.InvestigatorsJournal.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Kessig Wolfrider", 165, Rarity.RARE, mage.cards.k.KessigWolfrider.class));
        cards.add(new SetCardInfo("Kindly Ancestor", 22, Rarity.COMMON, mage.cards.k.KindlyAncestor.class));
        cards.add(new SetCardInfo("Laid to Rest", 207, Rarity.UNCOMMON, mage.cards.l.LaidToRest.class));
        cards.add(new SetCardInfo("Lantern Bearer", 66, Rarity.COMMON, mage.cards.l.LanternBearer.class));
        cards.add(new SetCardInfo("Lanterns' Lift", 66, Rarity.COMMON, mage.cards.l.LanternsLift.class));
        cards.add(new SetCardInfo("Lunar Rejection", 67, Rarity.UNCOMMON, mage.cards.l.LunarRejection.class));
        cards.add(new SetCardInfo("Massive Might", 208, Rarity.COMMON, mage.cards.m.MassiveMight.class));
        cards.add(new SetCardInfo("Mindleech Ghoul", 122, Rarity.COMMON, mage.cards.m.MindleechGhoul.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mulch", 210, Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Olivia, Crimson Bride", 245, Rarity.MYTHIC, mage.cards.o.OliviaCrimsonBride.class));
        cards.add(new SetCardInfo("Overcharged Amalgam", 71, Rarity.RARE, mage.cards.o.OverchargedAmalgam.class));
        cards.add(new SetCardInfo("Packsong Pup", 213, Rarity.UNCOMMON, mage.cards.p.PacksongPup.class));
        cards.add(new SetCardInfo("Path of Peril", 124, Rarity.RARE, mage.cards.p.PathOfPeril.class));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Reckless Impulse", 174, Rarity.COMMON, mage.cards.r.RecklessImpulse.class));
        cards.add(new SetCardInfo("Rending Flame", 175, Rarity.UNCOMMON, mage.cards.r.RendingFlame.class));
        cards.add(new SetCardInfo("Retrieve", 215, Rarity.UNCOMMON, mage.cards.r.Retrieve.class));
        cards.add(new SetCardInfo("Rot-Tide Gargantua", 129, Rarity.COMMON, mage.cards.r.RotTideGargantua.class));
        cards.add(new SetCardInfo("Sanctify", 33, Rarity.COMMON, mage.cards.s.Sanctify.class));
        cards.add(new SetCardInfo("Shattered Sanctum", 264, Rarity.RARE, mage.cards.s.ShatteredSanctum.class));
        cards.add(new SetCardInfo("Sorin the Mirthless", 131, Rarity.MYTHIC, mage.cards.s.SorinTheMirthless.class));
        cards.add(new SetCardInfo("Stormcarved Coast", 265, Rarity.RARE, mage.cards.s.StormcarvedCoast.class));
        cards.add(new SetCardInfo("Sundown Pass", 266, Rarity.RARE, mage.cards.s.SundownPass.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Syncopate", 83, Rarity.COMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 38, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Thirst for Discovery", 85, Rarity.UNCOMMON, mage.cards.t.ThirstForDiscovery.class));
        cards.add(new SetCardInfo("Torens, Fist of the Angels", 249, Rarity.RARE, mage.cards.t.TorensFistOfTheAngels.class));
        cards.add(new SetCardInfo("Twinblade Geist", 40, Rarity.UNCOMMON, mage.cards.t.TwinbladeGeist.class));
        cards.add(new SetCardInfo("Twinblade Invocation", 40, Rarity.UNCOMMON, mage.cards.t.TwinbladeInvocation.class));
        cards.add(new SetCardInfo("Ulvenwald Behemoth", 225, Rarity.RARE, mage.cards.u.UlvenwaldBehemoth.class));
        cards.add(new SetCardInfo("Ulvenwald Oddity", 225, Rarity.RARE, mage.cards.u.UlvenwaldOddity.class));
        cards.add(new SetCardInfo("Valorous Stance", 42, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vampires' Vengeance", 180, Rarity.UNCOMMON, mage.cards.v.VampiresVengeance.class));
        cards.add(new SetCardInfo("Volatile Arsonist", 181, Rarity.MYTHIC, mage.cards.v.VolatileArsonist.class));
        cards.add(new SetCardInfo("Voldaren Estate", 267, Rarity.RARE, mage.cards.v.VoldarenEstate.class));
        cards.add(new SetCardInfo("Weary Prisoner", 184, Rarity.COMMON, mage.cards.w.WearyPrisoner.class));
        cards.add(new SetCardInfo("Weaver of Blossoms", 226, Rarity.COMMON, mage.cards.w.WeaverOfBlossoms.class));
        cards.add(new SetCardInfo("Wedding Announcement", 45, Rarity.RARE, mage.cards.w.WeddingAnnouncement.class));
        cards.add(new SetCardInfo("Wedding Festivity", 45, Rarity.RARE, mage.cards.w.WeddingFestivity.class));
        cards.add(new SetCardInfo("Wedding Invitation", 260, Rarity.COMMON, mage.cards.w.WeddingInvitation.class));
        cards.add(new SetCardInfo("Wedding Security", 138, Rarity.UNCOMMON, mage.cards.w.WeddingSecurity.class));
        cards.add(new SetCardInfo("Whispering Wizard", 68, Rarity.UNCOMMON, mage.cards.w.WhisperingWizard.class));
        cards.add(new SetCardInfo("Wrathful Jailbreaker", 184, Rarity.COMMON, mage.cards.w.WrathfulJailbreaker.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
