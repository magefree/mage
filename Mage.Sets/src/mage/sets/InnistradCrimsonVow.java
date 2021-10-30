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

    private static final List<String> unfinished = Arrays.asList("Dorothea, Vengeful Victim", "Dorothea's Retribution", "Drogskol Infantry", "Drogskol Armaments", "Volatile Arsonist", "Dire-Strain Anarchist", "Weary Prisoner", "Wrathful Jailbreaker");
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

        cards.add(new SetCardInfo("Anje, Maid of Dishonor", 231, Rarity.RARE, mage.cards.a.AnjeMaidOfDishonor.class));
        cards.add(new SetCardInfo("Apprentice Sharpshooter", 185, Rarity.COMMON, mage.cards.a.ApprenticeSharpshooter.class));
        cards.add(new SetCardInfo("Archghoul of Thraben", 93, Rarity.UNCOMMON, mage.cards.a.ArchghoulOfThraben.class));
        cards.add(new SetCardInfo("By Invitation Only", 5, Rarity.RARE, mage.cards.b.ByInvitationOnly.class));
        cards.add(new SetCardInfo("Change of Fortune", 150, Rarity.RARE, mage.cards.c.ChangeOfFortune.class));
        cards.add(new SetCardInfo("Dawnhart Geist", 8, Rarity.UNCOMMON, mage.cards.d.DawnhartGeist.class));
        cards.add(new SetCardInfo("Deathcap Glade", 261, Rarity.RARE, mage.cards.d.DeathcapGlade.class));
        cards.add(new SetCardInfo("Demonic Bargain", 103, Rarity.RARE, mage.cards.d.DemonicBargain.class));
        cards.add(new SetCardInfo("Dig Up", 197, Rarity.RARE, mage.cards.d.DigUp.class));
        cards.add(new SetCardInfo("Dire-Strain Anarchist", 181, Rarity.MYTHIC, mage.cards.d.DireStrainAnarchist.class));
        cards.add(new SetCardInfo("Dominating Vampire", 154, Rarity.RARE, mage.cards.d.DominatingVampire.class));
        cards.add(new SetCardInfo("Dormant Grove", 198, Rarity.UNCOMMON, mage.cards.d.DormantGrove.class));
        cards.add(new SetCardInfo("Dreamroot Cascade", 262, Rarity.RARE, mage.cards.d.DreamrootCascade.class));
        cards.add(new SetCardInfo("Fell Stinger", 112, Rarity.UNCOMMON, mage.cards.f.FellStinger.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Geistlight Snare", 60, Rarity.UNCOMMON, mage.cards.g.GeistlightSnare.class));
        cards.add(new SetCardInfo("Gluttonous Guest", 114, Rarity.COMMON, mage.cards.g.GluttonousGuest.class));
        cards.add(new SetCardInfo("Gnarled Grovestrider", 198, Rarity.UNCOMMON, mage.cards.g.GnarledGrovestrider.class));
        cards.add(new SetCardInfo("Grolnok, the Omnivore", 238, Rarity.RARE, mage.cards.g.GrolnokTheOmnivore.class));
        cards.add(new SetCardInfo("Gryff Rider", 15, Rarity.COMMON, mage.cards.g.GryffRider.class));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 239, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Massive Might", 208, Rarity.COMMON, mage.cards.m.MassiveMight.class));
        cards.add(new SetCardInfo("Mindleech Ghoul", 122, Rarity.COMMON, mage.cards.m.MindleechGhoul.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Overcharged Amalgam", 71, Rarity.RARE, mage.cards.o.OverchargedAmalgam.class));
        cards.add(new SetCardInfo("Path of Peril", 124, Rarity.RARE, mage.cards.p.PathOfPeril.class));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Rot-Tide Gargantua", 129, Rarity.COMMON, mage.cards.r.RotTideGargantua.class));
        cards.add(new SetCardInfo("Shattered Sanctum", 264, Rarity.RARE, mage.cards.s.ShatteredSanctum.class));
        cards.add(new SetCardInfo("Sorin the Mirthless", 131, Rarity.MYTHIC, mage.cards.s.SorinTheMirthless.class));
        cards.add(new SetCardInfo("Stormcarved Coast", 265, Rarity.RARE, mage.cards.s.StormcarvedCoast.class));
        cards.add(new SetCardInfo("Sundown Pass", 266, Rarity.RARE, mage.cards.s.SundownPass.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 38, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Torens, Fist of the Angels", 249, Rarity.RARE, mage.cards.t.TorensFistOfTheAngels.class));
        cards.add(new SetCardInfo("Vampires' Vengeance", 180, Rarity.UNCOMMON, mage.cards.v.VampiresVengeance.class));
        cards.add(new SetCardInfo("Volatile Arsonist", 181, Rarity.MYTHIC, mage.cards.v.VolatileArsonist.class));
        cards.add(new SetCardInfo("Voldaren Estate", 267, Rarity.RARE, mage.cards.v.VoldarenEstate.class));
        cards.add(new SetCardInfo("Weary Prisoner", 184, Rarity.COMMON, mage.cards.w.WearyPrisoner.class));
        cards.add(new SetCardInfo("Wedding Announcement", 45, Rarity.RARE, mage.cards.w.WeddingAnnouncement.class));
        cards.add(new SetCardInfo("Wedding Festivity", 45, Rarity.RARE, mage.cards.w.WeddingFestivity.class));
        cards.add(new SetCardInfo("Wedding Invitation", 260, Rarity.COMMON, mage.cards.w.WeddingInvitation.class));
        cards.add(new SetCardInfo("Wrathful Jailbreaker", 184, Rarity.COMMON, mage.cards.w.WrathfulJailbreaker.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
