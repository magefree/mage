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

        cards.add(new SetCardInfo("A Tale for the Ages", 34, Rarity.RARE, mage.cards.a.ATaleForTheAges.class));
        cards.add(new SetCardInfo("Agatha of the Vile Cauldron", 199, Rarity.MYTHIC, mage.cards.a.AgathaOfTheVileCauldron.class));
        cards.add(new SetCardInfo("Agatha's Champion", 160, Rarity.UNCOMMON, mage.cards.a.AgathasChampion.class));
        cards.add(new SetCardInfo("Armory Mice", 3, Rarity.COMMON, mage.cards.a.ArmoryMice.class));
        cards.add(new SetCardInfo("Ash, Party Crasher", 201, Rarity.UNCOMMON, mage.cards.a.AshPartyCrasher.class));
        cards.add(new SetCardInfo("Ashiok's Reaper", 79, Rarity.UNCOMMON, mage.cards.a.AshioksReaper.class));
        cards.add(new SetCardInfo("Asinine Antics", 42, Rarity.MYTHIC, mage.cards.a.AsinineAntics.class));
        cards.add(new SetCardInfo("Back for Seconds", 80, Rarity.UNCOMMON, mage.cards.b.BackForSeconds.class));
        cards.add(new SetCardInfo("Beanstalk Wurm", 161, Rarity.COMMON, mage.cards.b.BeanstalkWurm.class));
        cards.add(new SetCardInfo("Belligerent of the Ball", 120, Rarity.UNCOMMON, mage.cards.b.BelligerentOfTheBall.class));
        cards.add(new SetCardInfo("Beluna Grandsquall", 220, Rarity.MYTHIC, mage.cards.b.BelunaGrandsquall.class));
        cards.add(new SetCardInfo("Beseech the Mirror", 82, Rarity.MYTHIC, mage.cards.b.BeseechTheMirror.class));
        cards.add(new SetCardInfo("Besotted Knight", 4, Rarity.COMMON, mage.cards.b.BesottedKnight.class));
        cards.add(new SetCardInfo("Bitter Chill", 44, Rarity.UNCOMMON, mage.cards.b.BitterChill.class));
        cards.add(new SetCardInfo("Blossoming Tortoise", 163, Rarity.MYTHIC, mage.cards.b.BlossomingTortoise.class));
        cards.add(new SetCardInfo("Boundary Lands Ranger", 123, Rarity.UNCOMMON, mage.cards.b.BoundaryLandsRanger.class));
        cards.add(new SetCardInfo("Bramble Familiar", 164, Rarity.RARE, mage.cards.b.BrambleFamiliar.class));
        cards.add(new SetCardInfo("Brave the Wilds", 165, Rarity.COMMON, mage.cards.b.BraveTheWilds.class));
        cards.add(new SetCardInfo("Break the Spell", 5, Rarity.COMMON, mage.cards.b.BreakTheSpell.class));
        cards.add(new SetCardInfo("Callous Sell-Sword", 221, Rarity.UNCOMMON, mage.cards.c.CallousSellSword.class));
        cards.add(new SetCardInfo("Chancellor of Tales", 45, Rarity.UNCOMMON, mage.cards.c.ChancellorOfTales.class));
        cards.add(new SetCardInfo("Charmed Clothier", 6, Rarity.COMMON, mage.cards.c.CharmedClothier.class));
        cards.add(new SetCardInfo("Charming Scoundrel", 124, Rarity.RARE, mage.cards.c.CharmingScoundrel.class));
        cards.add(new SetCardInfo("Conceited Witch", 84, Rarity.COMMON, mage.cards.c.ConceitedWitch.class));
        cards.add(new SetCardInfo("Cruel Somnophage", 222, Rarity.RARE, mage.cards.c.CruelSomnophage.class));
        cards.add(new SetCardInfo("Cursed Courtier", 9, Rarity.UNCOMMON, mage.cards.c.CursedCourtier.class));
        cards.add(new SetCardInfo("Cut In", 125, Rarity.COMMON, mage.cards.c.CutIn.class));
        cards.add(new SetCardInfo("Decadent Dragon", 223, Rarity.RARE, mage.cards.d.DecadentDragon.class));
        cards.add(new SetCardInfo("Discerning Financier", 10, Rarity.UNCOMMON, mage.cards.d.DiscerningFinancier.class));
        cards.add(new SetCardInfo("Dutiful Griffin", 11, Rarity.UNCOMMON, mage.cards.d.DutifulGriffin.class));
        cards.add(new SetCardInfo("Edgewall Inn", 255, Rarity.UNCOMMON, mage.cards.e.EdgewallInn.class));
        cards.add(new SetCardInfo("Elvish Archivist", 168, Rarity.RARE, mage.cards.e.ElvishArchivist.class));
        cards.add(new SetCardInfo("Embereth Veteran", 127, Rarity.UNCOMMON, mage.cards.e.EmberethVeteran.class));
        cards.add(new SetCardInfo("Evolving Wilds", 256, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Expel the Interlopers", 13, Rarity.RARE, mage.cards.e.ExpelTheInterlopers.class));
        cards.add(new SetCardInfo("Faerie Dreamthief", 89, Rarity.UNCOMMON, mage.cards.f.FaerieDreamthief.class));
        cards.add(new SetCardInfo("Faerie Fencing", 90, Rarity.UNCOMMON, mage.cards.f.FaerieFencing.class));
        cards.add(new SetCardInfo("Farsight Ritual", 49, Rarity.RARE, mage.cards.f.FarsightRitual.class));
        cards.add(new SetCardInfo("Faunsbane Troll", 203, Rarity.RARE, mage.cards.f.FaunsbaneTroll.class));
        cards.add(new SetCardInfo("Flick a Coin", 128, Rarity.COMMON, mage.cards.f.FlickACoin.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Freeze in Place", 50, Rarity.COMMON, mage.cards.f.FreezeInPlace.class));
        cards.add(new SetCardInfo("Frolicking Familiar", 226, Rarity.UNCOMMON, mage.cards.f.FrolickingFamiliar.class));
        cards.add(new SetCardInfo("Gadwick's First Duel", 51, Rarity.UNCOMMON, mage.cards.g.GadwicksFirstDuel.class));
        cards.add(new SetCardInfo("Gallant Pie-Wielder", 15, Rarity.UNCOMMON, mage.cards.g.GallantPieWielder.class));
        cards.add(new SetCardInfo("Galvanic Giant", 52, Rarity.UNCOMMON, mage.cards.g.GalvanicGiant.class));
        cards.add(new SetCardInfo("Glass Casket", 16, Rarity.UNCOMMON, mage.cards.g.GlassCasket.class));
        cards.add(new SetCardInfo("Graceful Takedown", 171, Rarity.UNCOMMON, mage.cards.g.GracefulTakedown.class));
        cards.add(new SetCardInfo("Grand Ball Guest", 134, Rarity.COMMON, mage.cards.g.GrandBallGuest.class));
        cards.add(new SetCardInfo("Greta, Sweettooth Scourge", 205, Rarity.UNCOMMON, mage.cards.g.GretaSweettoothScourge.class));
        cards.add(new SetCardInfo("Harried Spearguard", 135, Rarity.COMMON, mage.cards.h.HarriedSpearguard.class));
        cards.add(new SetCardInfo("Hopeless Nightmare", 95, Rarity.COMMON, mage.cards.h.HopelessNightmare.class));
        cards.add(new SetCardInfo("Howling Galefang", 175, Rarity.UNCOMMON, mage.cards.h.HowlingGalefang.class));
        cards.add(new SetCardInfo("Hylda of the Icy Crown", 206, Rarity.MYTHIC, mage.cards.h.HyldaOfTheIcyCrown.class));
        cards.add(new SetCardInfo("Hylda's Crown of Winter", 247, Rarity.RARE, mage.cards.h.HyldasCrownOfWinter.class));
        cards.add(new SetCardInfo("Ice Out", 54, Rarity.COMMON, mage.cards.i.IceOut.class));
        cards.add(new SetCardInfo("Icewrought Sentry", 55, Rarity.UNCOMMON, mage.cards.i.IcewroughtSentry.class));
        cards.add(new SetCardInfo("Ingenious Prodigy", 56, Rarity.RARE, mage.cards.i.IngeniousProdigy.class));
        cards.add(new SetCardInfo("Into the Fae Court", 57, Rarity.COMMON, mage.cards.i.IntoTheFaeCourt.class));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Johann's Stopgap", 58, Rarity.COMMON, mage.cards.j.JohannsStopgap.class));
        cards.add(new SetCardInfo("Johann, Apprentice Sorcerer", 207, Rarity.UNCOMMON, mage.cards.j.JohannApprenticeSorcerer.class));
        cards.add(new SetCardInfo("Kellan, the Fae-Blooded", 230, Rarity.MYTHIC, mage.cards.k.KellanTheFaeBlooded.class));
        cards.add(new SetCardInfo("Knight of Doves", 19, Rarity.UNCOMMON, mage.cards.k.KnightOfDoves.class));
        cards.add(new SetCardInfo("Korvold and the Noble Thief", 139, Rarity.UNCOMMON, mage.cards.k.KorvoldAndTheNobleThief.class));
        cards.add(new SetCardInfo("Lich-Knights' Conquest", 96, Rarity.RARE, mage.cards.l.LichKnightsConquest.class));
        cards.add(new SetCardInfo("Likeness Looter", 208, Rarity.RARE, mage.cards.l.LikenessLooter.class));
        cards.add(new SetCardInfo("Living Lectern", 59, Rarity.COMMON, mage.cards.l.LivingLectern.class));
        cards.add(new SetCardInfo("Lord Skitter's Blessing", 98, Rarity.RARE, mage.cards.l.LordSkittersBlessing.class));
        cards.add(new SetCardInfo("Lord Skitter's Butcher", 99, Rarity.UNCOMMON, mage.cards.l.LordSkittersButcher.class));
        cards.add(new SetCardInfo("Lord Skitter, Sewer King", 97, Rarity.RARE, mage.cards.l.LordSkitterSewerKing.class));
        cards.add(new SetCardInfo("Mocking Sprite", 62, Rarity.COMMON, mage.cards.m.MockingSprite.class));
        cards.add(new SetCardInfo("Monstrous Rage", 142, Rarity.UNCOMMON, mage.cards.m.MonstrousRage.class));
        cards.add(new SetCardInfo("Moonshaker Cavalry", 21, Rarity.MYTHIC, mage.cards.m.MoonshakerCavalry.class));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neva, Stalked by Nightmares", 209, Rarity.UNCOMMON, mage.cards.n.NevaStalkedByNightmares.class));
        cards.add(new SetCardInfo("Night of the Sweets' Revenge", 178, Rarity.UNCOMMON, mage.cards.n.NightOfTheSweetsRevenge.class));
        cards.add(new SetCardInfo("Not Dead After All", 101, Rarity.COMMON, mage.cards.n.NotDeadAfterAll.class));
        cards.add(new SetCardInfo("Obyra, Dreaming Duelist", 210, Rarity.UNCOMMON, mage.cards.o.ObyraDreamingDuelist.class));
        cards.add(new SetCardInfo("Picklock Prankster", 64, Rarity.COMMON, mage.cards.p.PicklockPrankster.class));
        cards.add(new SetCardInfo("Picnic Ruiner", 232, Rarity.UNCOMMON, mage.cards.p.PicnicRuiner.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prophetic Prism", 249, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Raging Battle Mouse", 143, Rarity.RARE, mage.cards.r.RagingBattleMouse.class));
        cards.add(new SetCardInfo("Rat Out", 103, Rarity.COMMON, mage.cards.r.RatOut.class));
        cards.add(new SetCardInfo("Ratcatcher Trainee", 144, Rarity.COMMON, mage.cards.r.RatcatcherTrainee.class));
        cards.add(new SetCardInfo("Realm-Scorcher Hellkite", 145, Rarity.MYTHIC, mage.cards.r.RealmScorcherHellkite.class));
        cards.add(new SetCardInfo("Redcap Gutter-Dweller", 146, Rarity.RARE, mage.cards.r.RedcapGutterDweller.class));
        cards.add(new SetCardInfo("Restless Bivouac", 257, Rarity.RARE, mage.cards.r.RestlessBivouac.class));
        cards.add(new SetCardInfo("Restless Cottage", 258, Rarity.RARE, mage.cards.r.RestlessCottage.class));
        cards.add(new SetCardInfo("Restless Fortress", 259, Rarity.RARE, mage.cards.r.RestlessFortress.class));
        cards.add(new SetCardInfo("Restless Spire", 260, Rarity.RARE, mage.cards.r.RestlessSpire.class));
        cards.add(new SetCardInfo("Return Triumphant", 26, Rarity.COMMON, mage.cards.r.ReturnTriumphant.class));
        cards.add(new SetCardInfo("Return from the Wilds", 181, Rarity.COMMON, mage.cards.r.ReturnFromTheWilds.class));
        cards.add(new SetCardInfo("Rootrider Faun", 182, Rarity.COMMON, mage.cards.r.RootriderFaun.class));
        cards.add(new SetCardInfo("Rowan's Grim Search", 104, Rarity.COMMON, mage.cards.r.RowansGrimSearch.class));
        cards.add(new SetCardInfo("Rowan, Scion of War", 211, Rarity.MYTHIC, mage.cards.r.RowanScionOfWar.class));
        cards.add(new SetCardInfo("Royal Treatment", 183, Rarity.UNCOMMON, mage.cards.r.RoyalTreatment.class));
        cards.add(new SetCardInfo("Ruby, Daring Tracker", 212, Rarity.UNCOMMON, mage.cards.r.RubyDaringTracker.class));
        cards.add(new SetCardInfo("Scalding Viper", 235, Rarity.RARE, mage.cards.s.ScaldingViper.class));
        cards.add(new SetCardInfo("Sentinel of Lost Lore", 184, Rarity.RARE, mage.cards.s.SentinelOfLostLore.class));
        cards.add(new SetCardInfo("Sharae of Numbing Depths", 213, Rarity.UNCOMMON, mage.cards.s.SharaeOfNumbingDepths.class));
        cards.add(new SetCardInfo("Shrouded Shepherd", 236, Rarity.UNCOMMON, mage.cards.s.ShroudedShepherd.class));
        cards.add(new SetCardInfo("Skybeast Tracker", 185, Rarity.COMMON, mage.cards.s.SkybeastTracker.class));
        cards.add(new SetCardInfo("Sleight of Hand", 67, Rarity.COMMON, mage.cards.s.SleightOfHand.class));
        cards.add(new SetCardInfo("Solitary Sanctuary", 30, Rarity.UNCOMMON, mage.cards.s.SolitarySanctuary.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 251, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Specter of Mortality", 107, Rarity.RARE, mage.cards.s.SpecterOfMortality.class));
        cards.add(new SetCardInfo("Spell Stutter", 69, Rarity.COMMON, mage.cards.s.SpellStutter.class));
        cards.add(new SetCardInfo("Spellbook Vendor", 31, Rarity.RARE, mage.cards.s.SpellbookVendor.class));
        cards.add(new SetCardInfo("Spellscorn Coven", 237, Rarity.UNCOMMON, mage.cards.s.SpellscornCoven.class));
        cards.add(new SetCardInfo("Spiteful Hexmage", 108, Rarity.RARE, mage.cards.s.SpitefulHexmage.class));
        cards.add(new SetCardInfo("Splashy Spellcaster", 70, Rarity.UNCOMMON, mage.cards.s.SplashySpellcaster.class));
        cards.add(new SetCardInfo("Stonesplitter Bolt", 151, Rarity.UNCOMMON, mage.cards.s.StonesplitterBolt.class));
        cards.add(new SetCardInfo("Stormkeld Vanguard", 187, Rarity.UNCOMMON, mage.cards.s.StormkeldVanguard.class));
        cards.add(new SetCardInfo("Stroke of Midnight", 33, Rarity.UNCOMMON, mage.cards.s.StrokeOfMidnight.class));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sweettooth Witch", 111, Rarity.COMMON, mage.cards.s.SweettoothWitch.class));
        cards.add(new SetCardInfo("Syr Armont, the Redeemer", 214, Rarity.UNCOMMON, mage.cards.s.SyrArmontTheRedeemer.class));
        cards.add(new SetCardInfo("Syr Ginger, the Meal Ender", 252, Rarity.RARE, mage.cards.s.SyrGingerTheMealEnder.class));
        cards.add(new SetCardInfo("Taken by Nightmares", 112, Rarity.UNCOMMON, mage.cards.t.TakenByNightmares.class));
        cards.add(new SetCardInfo("Talion's Messenger", 73, Rarity.RARE, mage.cards.t.TalionsMessenger.class));
        cards.add(new SetCardInfo("Talion, the Kindly Lord", 215, Rarity.MYTHIC, mage.cards.t.TalionTheKindlyLord.class));
        cards.add(new SetCardInfo("Tanglespan Lookout", 188, Rarity.UNCOMMON, mage.cards.t.TanglespanLookout.class));
        cards.add(new SetCardInfo("Tattered Ratter", 152, Rarity.UNCOMMON, mage.cards.t.TatteredRatter.class));
        cards.add(new SetCardInfo("Tempest Hart", 238, Rarity.UNCOMMON, mage.cards.t.TempestHart.class));
        cards.add(new SetCardInfo("The Goose Mother", 204, Rarity.RARE, mage.cards.t.TheGooseMother.class));
        cards.add(new SetCardInfo("The Huntsman's Redemption", 176, Rarity.RARE, mage.cards.t.TheHuntsmansRedemption.class));
        cards.add(new SetCardInfo("The Princess Takes Flight", 23, Rarity.UNCOMMON, mage.cards.t.ThePrincessTakesFlight.class));
        cards.add(new SetCardInfo("Threadbind Clique", 239, Rarity.UNCOMMON, mage.cards.t.ThreadbindClique.class));
        cards.add(new SetCardInfo("Three Blind Mice", 35, Rarity.RARE, mage.cards.t.ThreeBlindMice.class));
        cards.add(new SetCardInfo("Three Bowls of Porridge", 253, Rarity.UNCOMMON, mage.cards.t.ThreeBowlsOfPorridge.class));
        cards.add(new SetCardInfo("Thunderous Debut", 190, Rarity.RARE, mage.cards.t.ThunderousDebut.class));
        cards.add(new SetCardInfo("Torch the Tower", 153, Rarity.COMMON, mage.cards.t.TorchTheTower.class));
        cards.add(new SetCardInfo("Totentanz, Swarm Piper", 216, Rarity.UNCOMMON, mage.cards.t.TotentanzSwarmPiper.class));
        cards.add(new SetCardInfo("Tough Cookie", 193, Rarity.UNCOMMON, mage.cards.t.ToughCookie.class));
        cards.add(new SetCardInfo("Troublemaker Ouphe", 194, Rarity.COMMON, mage.cards.t.TroublemakerOuphe.class));
        cards.add(new SetCardInfo("Troyan, Gutsy Explorer", 217, Rarity.UNCOMMON, mage.cards.t.TroyanGutsyExplorer.class));
        cards.add(new SetCardInfo("Twisted Fealty", 154, Rarity.UNCOMMON, mage.cards.t.TwistedFealty.class));
        cards.add(new SetCardInfo("Twisted Sewer-Witch", 114, Rarity.UNCOMMON, mage.cards.t.TwistedSewerWitch.class));
        cards.add(new SetCardInfo("Two-Headed Hunter", 155, Rarity.UNCOMMON, mage.cards.t.TwoHeadedHunter.class));
        cards.add(new SetCardInfo("Up the Beanstalk", 195, Rarity.COMMON, mage.cards.u.UpTheBeanstalk.class));
        cards.add(new SetCardInfo("Virtue of Knowledge", 76, Rarity.MYTHIC, mage.cards.v.VirtueOfKnowledge.class));
        cards.add(new SetCardInfo("Virtue of Loyalty", 38, Rarity.MYTHIC, mage.cards.v.VirtueOfLoyalty.class));
        cards.add(new SetCardInfo("Virtue of Persistence", 115, Rarity.MYTHIC, mage.cards.v.VirtueOfPersistence.class));
        cards.add(new SetCardInfo("Voracious Vermin", 116, Rarity.COMMON, mage.cards.v.VoraciousVermin.class));
        cards.add(new SetCardInfo("Warehouse Tabby", 117, Rarity.COMMON, mage.cards.w.WarehouseTabby.class));
        cards.add(new SetCardInfo("Water Wings", 77, Rarity.COMMON, mage.cards.w.WaterWings.class));
        cards.add(new SetCardInfo("Welcome to Sweettooth", 198, Rarity.UNCOMMON, mage.cards.w.WelcomeToSweettooth.class));
        cards.add(new SetCardInfo("Werefox Bodyguard", 39, Rarity.RARE, mage.cards.w.WerefoxBodyguard.class));
        cards.add(new SetCardInfo("Will, Scion of Peace", 218, Rarity.MYTHIC, mage.cards.w.WillScionOfPeace.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new WildsOfEldraineCollator();
//    }
}
