package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class WildsOfEldraine extends ExpansionSet {

    private static final WildsOfEldraine instance = new WildsOfEldraine();

    public static WildsOfEldraine getInstance() {
        return instance;
    }

    private WildsOfEldraine() {
        super("Wilds of Eldraine", "WOE", ExpansionSet.buildDate(2023, 9, 8), SetType.EXPANSION);
        this.blockName = "Wilds of Eldraine";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Ash, Party Crasher", 201, Rarity.UNCOMMON, mage.cards.a.AshPartyCrasher.class));
        cards.add(new SetCardInfo("Ashiok's Reaper", 79, Rarity.UNCOMMON, mage.cards.a.AshioksReaper.class));
        cards.add(new SetCardInfo("Beanstalk Wurm", 161, Rarity.COMMON, mage.cards.b.BeanstalkWurm.class));
        cards.add(new SetCardInfo("Besotted Knight", 4, Rarity.COMMON, mage.cards.b.BesottedKnight.class));
        cards.add(new SetCardInfo("Break the Spell", 5, Rarity.COMMON, mage.cards.b.BreakTheSpell.class));
        cards.add(new SetCardInfo("Conceited Witch", 84, Rarity.COMMON, mage.cards.c.ConceitedWitch.class));
        cards.add(new SetCardInfo("Cruel Somnophage", 222, Rarity.RARE, mage.cards.c.CruelSomnophage.class));
        cards.add(new SetCardInfo("Cursed Courtier", 9, Rarity.UNCOMMON, mage.cards.c.CursedCourtier.class));
        cards.add(new SetCardInfo("Elvish Archivist", 168, Rarity.RARE, mage.cards.e.ElvishArchivist.class));
        cards.add(new SetCardInfo("Embereth Veteran", 127, Rarity.UNCOMMON, mage.cards.e.EmberethVeteran.class));
        cards.add(new SetCardInfo("Evolving Wilds", 256, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faerie Dreamthief", 89, Rarity.UNCOMMON, mage.cards.f.FaerieDreamthief.class));
        cards.add(new SetCardInfo("Farsight Ritual", 49, Rarity.RARE, mage.cards.f.FarsightRitual.class));
        cards.add(new SetCardInfo("Faunsbane Troll", 203, Rarity.RARE, mage.cards.f.FaunsbaneTroll.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frolicking Familiar", 226, Rarity.UNCOMMON, mage.cards.f.FrolickingFamiliar.class));
        cards.add(new SetCardInfo("Gallant Pie-Wielder", 15, Rarity.UNCOMMON, mage.cards.g.GallantPieWielder.class));
        cards.add(new SetCardInfo("Glass Casket", 16, Rarity.UNCOMMON, mage.cards.g.GlassCasket.class));
        cards.add(new SetCardInfo("Greta, Sweettooth Scourge", 205, Rarity.UNCOMMON, mage.cards.g.GretaSweettoothScourge.class));
        cards.add(new SetCardInfo("Howling Galefang", 175, Rarity.UNCOMMON, mage.cards.h.HowlingGalefang.class));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knight of Doves", 19, Rarity.UNCOMMON, mage.cards.k.KnightOfDoves.class));
        cards.add(new SetCardInfo("Living Lectern", 59, Rarity.COMMON, mage.cards.l.LivingLectern.class));
        cards.add(new SetCardInfo("Lord Skitter's Blessing", 98, Rarity.RARE, mage.cards.l.LordSkittersBlessing.class));
        cards.add(new SetCardInfo("Lord Skitter's Butcher", 99, Rarity.UNCOMMON, mage.cards.l.LordSkittersButcher.class));
        cards.add(new SetCardInfo("Monstrous Rage", 142, Rarity.UNCOMMON, mage.cards.m.MonstrousRage.class));
        cards.add(new SetCardInfo("Moonshaker Cavalry", 21, Rarity.MYTHIC, mage.cards.m.MoonshakerCavalry.class));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neva, Stalked by Nightmares", 209, Rarity.UNCOMMON, mage.cards.n.NevaStalkedByNightmares.class));
        cards.add(new SetCardInfo("Obyra, Dreaming Duelist", 210, Rarity.UNCOMMON, mage.cards.o.ObyraDreamingDuelist.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rat Out", 103, Rarity.COMMON, mage.cards.r.RatOut.class));
        cards.add(new SetCardInfo("Ratcatcher Trainee", 144, Rarity.COMMON, mage.cards.r.RatcatcherTrainee.class));
        cards.add(new SetCardInfo("Realm-Scorcher Hellkite", 145, Rarity.MYTHIC, mage.cards.r.RealmScorcherHellkite.class));
        cards.add(new SetCardInfo("Restless Cottage", 258, Rarity.RARE, mage.cards.r.RestlessCottage.class));
        cards.add(new SetCardInfo("Restless Fortress", 259, Rarity.RARE, mage.cards.r.RestlessFortress.class));
        cards.add(new SetCardInfo("Return from the Wilds", 181, Rarity.COMMON, mage.cards.r.ReturnFromTheWilds.class));
        cards.add(new SetCardInfo("Rootrider Faun", 182, Rarity.COMMON, mage.cards.r.RootriderFaun.class));
        cards.add(new SetCardInfo("Ruby, Daring Tracker", 212, Rarity.UNCOMMON, mage.cards.r.RubyDaringTracker.class));
        cards.add(new SetCardInfo("Scalding Viper", 235, Rarity.RARE, mage.cards.s.ScaldingViper.class));
        cards.add(new SetCardInfo("Sharae of Numbing Depths", 213, Rarity.UNCOMMON, mage.cards.s.SharaeOfNumbingDepths.class));
        cards.add(new SetCardInfo("Skybeast Tracker", 185, Rarity.COMMON, mage.cards.s.SkybeastTracker.class));
        cards.add(new SetCardInfo("Sleight of Hand", 67, Rarity.COMMON, mage.cards.s.SleightOfHand.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 251, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Spellbook Vendor", 31, Rarity.RARE, mage.cards.s.SpellbookVendor.class));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sweettooth Witch", 111, Rarity.COMMON, mage.cards.s.SweettoothWitch.class));
        cards.add(new SetCardInfo("Syr Armont, the Redeemer", 214, Rarity.UNCOMMON, mage.cards.s.SyrArmontTheRedeemer.class));
        cards.add(new SetCardInfo("Syr Ginger, the Meal Ender", 252, Rarity.RARE, mage.cards.s.SyrGingerTheMealEnder.class));
        cards.add(new SetCardInfo("Talion, the Kindly Lord", 215, Rarity.MYTHIC, mage.cards.t.TalionTheKindlyLord.class));
        cards.add(new SetCardInfo("Tanglespan Lookout", 188, Rarity.UNCOMMON, mage.cards.t.TanglespanLookout.class));
        cards.add(new SetCardInfo("The Goose Mother", 204, Rarity.RARE, mage.cards.t.TheGooseMother.class));
        cards.add(new SetCardInfo("Three Blind Mice", 35, Rarity.RARE, mage.cards.t.ThreeBlindMice.class));
        cards.add(new SetCardInfo("Thunderous Debut", 190, Rarity.RARE, mage.cards.t.ThunderousDebut.class));
        cards.add(new SetCardInfo("Torch the Tower", 153, Rarity.COMMON, mage.cards.t.TorchTheTower.class));
        cards.add(new SetCardInfo("Totentanz, Swarm Piper", 216, Rarity.UNCOMMON, mage.cards.t.TotentanzSwarmPiper.class));
        cards.add(new SetCardInfo("Tough Cookie", 193, Rarity.UNCOMMON, mage.cards.t.ToughCookie.class));
        cards.add(new SetCardInfo("Troublemaker Ouphe", 194, Rarity.COMMON, mage.cards.t.TroublemakerOuphe.class));
        cards.add(new SetCardInfo("Troyan, Gutsy Explorer", 217, Rarity.UNCOMMON, mage.cards.t.TroyanGutsyExplorer.class));
        cards.add(new SetCardInfo("Twisted Fealty", 154, Rarity.UNCOMMON, mage.cards.t.TwistedFealty.class));
        cards.add(new SetCardInfo("Warehouse Tabby", 117, Rarity.COMMON, mage.cards.w.WarehouseTabby.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new WildsOfEldraineCollator();
//    }
}
