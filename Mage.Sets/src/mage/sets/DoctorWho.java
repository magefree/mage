package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DoctorWho extends ExpansionSet {

    private static final DoctorWho instance = new DoctorWho();

    public static DoctorWho getInstance() {
        return instance;
    }

    private DoctorWho() {
        super("Doctor Who", "WHO", ExpansionSet.buildDate(2023, 10, 13), SetType.SUPPLEMENTAL);

        cards.add(new SetCardInfo("Arcane Signet", 239, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("As Foretold", 214, Rarity.RARE, mage.cards.a.AsForetold.class));
        cards.add(new SetCardInfo("City of Death", 99, Rarity.RARE, mage.cards.c.CityOfDeath.class));
        cards.add(new SetCardInfo("Command Tower", 264, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Exotic Orchard", 276, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Exterminate!", 68, Rarity.UNCOMMON, mage.cards.e.Exterminate.class));
        cards.add(new SetCardInfo("Farewell", 207, Rarity.RARE, mage.cards.f.Farewell.class));
        cards.add(new SetCardInfo("Fiery Islet", 278, Rarity.RARE, mage.cards.f.FieryIslet.class));
        cards.add(new SetCardInfo("Forest", 204, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Four Knocks", 20, Rarity.RARE, mage.cards.f.FourKnocks.class));
        cards.add(new SetCardInfo("Island", 198, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Greaves", 243, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Mind Stone", 244, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mountain", 202, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Past in Flames", 565, Rarity.RARE, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Path of Ancestry", 293, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Plains", 196, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarah Jane Smith", 6, Rarity.RARE, mage.cards.s.SarahJaneSmith.class));
        cards.add(new SetCardInfo("Sol Ring", 245, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Sunbaked Canyon", 309, Rarity.RARE, mage.cards.s.SunbakedCanyon.class));
        cards.add(new SetCardInfo("Swamp", 200, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Talisman of Conviction", 247, Rarity.UNCOMMON, mage.cards.t.TalismanOfConviction.class));
        cards.add(new SetCardInfo("Talisman of Creativity", 248, Rarity.UNCOMMON, mage.cards.t.TalismanOfCreativity.class));
        cards.add(new SetCardInfo("Talisman of Curiosity", 249, Rarity.UNCOMMON, mage.cards.t.TalismanOfCuriosity.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 250, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Impulse", 251, Rarity.UNCOMMON, mage.cards.t.TalismanOfImpulse.class));
        cards.add(new SetCardInfo("Talisman of Indulgence", 252, Rarity.UNCOMMON, mage.cards.t.TalismanOfIndulgence.class));
        cards.add(new SetCardInfo("Talisman of Progress", 253, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Talisman of Unity", 254, Rarity.UNCOMMON, mage.cards.t.TalismanOfUnity.class));
        cards.add(new SetCardInfo("The Eleventh Hour", 41, Rarity.RARE, mage.cards.t.TheEleventhHour.class));
        cards.add(new SetCardInfo("The Flux", 86, Rarity.RARE, mage.cards.t.TheFlux.class));
        cards.add(new SetCardInfo("The Thirteenth Doctor", 4, Rarity.MYTHIC, mage.cards.t.TheThirteenthDoctor.class));
        cards.add(new SetCardInfo("Time Lord Regeneration", 59, Rarity.UNCOMMON, mage.cards.t.TimeLordRegeneration.class));
        cards.add(new SetCardInfo("War Room", 330, Rarity.RARE, mage.cards.w.WarRoom.class));
        cards.add(new SetCardInfo("Waterlogged Grove", 331, Rarity.RARE, mage.cards.w.WaterloggedGrove.class));
        cards.add(new SetCardInfo("Wedding Ring", 213, Rarity.MYTHIC, mage.cards.w.WeddingRing.class));
        cards.add(new SetCardInfo("Yasmin Khan", 7, Rarity.RARE, mage.cards.y.YasminKhan.class));
    }
}
