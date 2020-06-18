package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Jumpstart extends ExpansionSet {

    private static final Jumpstart instance = new Jumpstart();

    public static Jumpstart getInstance() {
        return instance;
    }

    private Jumpstart() {
        super("Jumpstart", "JMP", ExpansionSet.buildDate(2020, 7, 17), SetType.SUPPLEMENTAL);
        this.blockName = "Jumpstart";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Affectionate Indrik", 373, Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Alloy Myr", 457, Rarity.COMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("Auger Spree", 449, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Bake into a Pie", 201, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Black Cat", 203, Rarity.COMMON, mage.cards.b.BlackCat.class));
        cards.add(new SetCardInfo("Blighted Bat", 205, Rarity.COMMON, mage.cards.b.BlightedBat.class));
        cards.add(new SetCardInfo("Blood Divination", 207, Rarity.UNCOMMON, mage.cards.b.BloodDivination.class));
        cards.add(new SetCardInfo("Bloodhunter Bat", 210, Rarity.COMMON, mage.cards.b.BloodhunterBat.class));
        cards.add(new SetCardInfo("Bogbrew Witch", 211, Rarity.RARE, mage.cards.b.BogbrewWitch.class));
        cards.add(new SetCardInfo("Bone Picker", 212, Rarity.UNCOMMON, mage.cards.b.BonePicker.class));
        cards.add(new SetCardInfo("Brushstrider", 381, Rarity.UNCOMMON, mage.cards.b.Brushstrider.class));
        cards.add(new SetCardInfo("Bubbling Cauldron", 460, Rarity.UNCOMMON, mage.cards.b.BubblingCauldron.class));
        cards.add(new SetCardInfo("Cemetery Recruitment", 217, Rarity.COMMON, mage.cards.c.CemeteryRecruitment.class));
        cards.add(new SetCardInfo("Chamber Sentry", 461, Rarity.RARE, mage.cards.c.ChamberSentry.class));
        cards.add(new SetCardInfo("Dinrova Horror", 450, Rarity.UNCOMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Dutiful Attendant", 226, Rarity.COMMON, mage.cards.d.DutifulAttendant.class));
        cards.add(new SetCardInfo("Entomber Exarch", 227, Rarity.UNCOMMON, mage.cards.e.EntomberExarch.class));
        cards.add(new SetCardInfo("Fanatical Firebrand", 315, Rarity.COMMON, mage.cards.f.FanaticalFirebrand.class));
        cards.add(new SetCardInfo("Festering Newt", 234, Rarity.COMMON, mage.cards.f.FesteringNewt.class));
        cards.add(new SetCardInfo("Flames of the Firebrand", 357, Rarity.UNCOMMON, mage.cards.f.FlamesOfTheFirebrand.class));
        cards.add(new SetCardInfo("Forest", 76, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fusion Elemental", 451, Rarity.UNCOMMON, mage.cards.f.FusionElemental.class));
        cards.add(new SetCardInfo("Ghoulcaller's Accomplice", 237, Rarity.COMMON, mage.cards.g.GhoulcallersAccomplice.class));
        cards.add(new SetCardInfo("Ghoulraiser", 238, Rarity.COMMON, mage.cards.g.Ghoulraiser.class));
        cards.add(new SetCardInfo("Gravewaker", 241, Rarity.RARE, mage.cards.g.Gravewaker.class));
        cards.add(new SetCardInfo("Hungry Flames", 336, Rarity.UNCOMMON, mage.cards.h.HungryFlames.class));
        cards.add(new SetCardInfo("Innocent Blood", 244, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Ironroot Warlord", 452, Rarity.UNCOMMON, mage.cards.i.IronrootWarlord.class));
        cards.add(new SetCardInfo("Isamaru, Hound of Konda", 113, Rarity.RARE, mage.cards.i.IsamaruHoundOfKonda.class));
        cards.add(new SetCardInfo("Knight of the Tusk", 114, Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Last Gasp", 247, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Lawmage's Binding", 453, Rarity.COMMON, mage.cards.l.LawmagesBinding.class));
        cards.add(new SetCardInfo("Liliana's Elite", 250, Rarity.UNCOMMON, mage.cards.l.LilianasElite.class));
        cards.add(new SetCardInfo("Maelstrom Archangel", 454, Rarity.MYTHIC, mage.cards.m.MaelstromArchangel.class));
        cards.add(new SetCardInfo("Mire Triton", 257, Rarity.UNCOMMON, mage.cards.m.MireTriton.class));
        cards.add(new SetCardInfo("Mirrodin's Core", 492, Rarity.UNCOMMON, mage.cards.m.MirrodinsCore.class));
        cards.add(new SetCardInfo("Nyxathid", 259, Rarity.RARE, mage.cards.n.Nyxathid.class));
        cards.add(new SetCardInfo("Pillar of Flame", 355, Rarity.RARE, mage.cards.p.PillarOfFlame.class));
        cards.add(new SetCardInfo("Plains", 45, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prophetic Prism", 478, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Pyroclastic Elemental", 356, Rarity.UNCOMMON, mage.cards.p.PyroclasticElemental.class));
        cards.add(new SetCardInfo("Raging Regisaur", 455, Rarity.UNCOMMON, mage.cards.r.RagingRegisaur.class));
        cards.add(new SetCardInfo("Reanimate", 270, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Rupture Spire", 495, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Settle the Score", 276, Rarity.UNCOMMON, mage.cards.s.SettleTheScore.class));
        cards.add(new SetCardInfo("Shambling Goblin", 277, Rarity.COMMON, mage.cards.s.ShamblingGoblin.class));
        cards.add(new SetCardInfo("Skittering Surveyor", 486, Rarity.COMMON, mage.cards.s.SkitteringSurveyor.class));
        cards.add(new SetCardInfo("Swamp", 54, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Bloodflies", 282, Rarity.UNCOMMON, mage.cards.s.SwarmOfBloodflies.class));
        cards.add(new SetCardInfo("Tempting Witch", 283, Rarity.UNCOMMON, mage.cards.t.TemptingWitch.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 78, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thriving Heath", 35, Rarity.COMMON, mage.cards.t.ThrivingHeath.class));
        cards.add(new SetCardInfo("Wailing Ghoul", 286, Rarity.COMMON, mage.cards.w.WailingGhoul.class));
        cards.add(new SetCardInfo("Young Pyromancer", 372, Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
    }
}
