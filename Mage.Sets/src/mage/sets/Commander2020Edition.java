package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Commander2020Edition extends ExpansionSet {

    private static final Commander2020Edition instance = new Commander2020Edition();

    public static Commander2020Edition getInstance() {
        return instance;
    }

    private Commander2020Edition() {
        super("Commander 2020 Edition", "C20", ExpansionSet.buildDate(2020, 4, 24), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Ajani Unyielding", 201, Rarity.MYTHIC, mage.cards.a.AjaniUnyielding.class));
        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", 143, Rarity.RARE, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Arcane Signet", 237, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Boneyard Mycodrax", 40, Rarity.RARE, mage.cards.b.BoneyardMycodrax.class));
        cards.add(new SetCardInfo("Brallin, Skyshark Rider", 4, Rarity.MYTHIC, mage.cards.b.BrallinSkysharkRider.class));
        cards.add(new SetCardInfo("Crop Rotation", 169, Rarity.COMMON, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Curious Herd", 59, Rarity.RARE, mage.cards.c.CuriousHerd.class));
        cards.add(new SetCardInfo("Deadly Tempest", 131, Rarity.RARE, mage.cards.d.DeadlyTempest.class));
        cards.add(new SetCardInfo("Flawless Maneuver", 26, Rarity.RARE, mage.cards.f.FlawlessManeuver.class));
        cards.add(new SetCardInfo("Jace, Architect of Thought", 114, Rarity.MYTHIC, mage.cards.j.JaceArchitectOfThought.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", 244, Rarity.RARE, mage.cards.l.LifecraftersBestiary.class));
        cards.add(new SetCardInfo("Masked Admirers", 163, Rarity.RARE, mage.cards.m.MaskedAdmirers.class));
        cards.add(new SetCardInfo("Netherborn Altar", 45, Rarity.RARE, mage.cards.n.NetherbornAltar.class));
        cards.add(new SetCardInfo("Otrimi, the Ever-Playful", 12, Rarity.COMMON, mage.cards.o.OtrimiTheEverPlayful.class));
        cards.add(new SetCardInfo("Path of Ancestry", 298, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Rashmi, Eternities Crafter", 229, Rarity.RARE, mage.cards.r.RashmiEternitiesCrafter.class));
        cards.add(new SetCardInfo("Shabraz, the Skyshark", 14, Rarity.MYTHIC, mage.cards.s.ShabrazTheSkyshark.class));
        cards.add(new SetCardInfo("Shared Animosity", 158, Rarity.RARE, mage.cards.s.SharedAnimosity.class));
        cards.add(new SetCardInfo("Silent Arbiter", 250, Rarity.RARE, mage.cards.s.SilentArbiter.class));
    }
}
