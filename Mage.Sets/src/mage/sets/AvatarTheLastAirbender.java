package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class AvatarTheLastAirbender extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Aang's Iceberg", "Avatar Aang", "Aang, Master of Elements", "Flexible Waterbender", "Geyser Leaper", "Giant Koi", "Katara, Bending Prodigy", "Katara, Water Tribe's Hope", "Waterbending Lesson", "Watery Grasp", "Yue, the Moon Spirit");
    private static final AvatarTheLastAirbender instance = new AvatarTheLastAirbender();

    public static AvatarTheLastAirbender getInstance() {
        return instance;
    }

    private AvatarTheLastAirbender() {
        super("Avatar: The Last Airbender", "TLA", ExpansionSet.buildDate(2025, 11, 21), SetType.EXPANSION);
        this.blockName = "Avatar: The Last Airbender"; // for sorting in GUI
        this.rotationSet = true;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aang's Iceberg", 336, Rarity.RARE, mage.cards.a.AangsIceberg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang's Iceberg", 5, Rarity.RARE, mage.cards.a.AangsIceberg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang's Journey", 1, Rarity.COMMON, mage.cards.a.AangsJourney.class));
        cards.add(new SetCardInfo("Aang, Master of Elements", 207, Rarity.MYTHIC, mage.cards.a.AangMasterOfElements.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang, Master of Elements", 363, Rarity.MYTHIC, mage.cards.a.AangMasterOfElements.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang, the Last Airbender", 4, Rarity.UNCOMMON, mage.cards.a.AangTheLastAirbender.class));
        cards.add(new SetCardInfo("Abandon Attachments", 205, Rarity.COMMON, mage.cards.a.AbandonAttachments.class));
        cards.add(new SetCardInfo("Airbending Lesson", 8, Rarity.COMMON, mage.cards.a.AirbendingLesson.class));
        cards.add(new SetCardInfo("Appa, Steadfast Guardian", 10, Rarity.MYTHIC, mage.cards.a.AppaSteadfastGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Appa, Steadfast Guardian", 316, Rarity.MYTHIC, mage.cards.a.AppaSteadfastGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avatar Aang", 207, Rarity.MYTHIC, mage.cards.a.AvatarAang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avatar Aang", 363, Rarity.MYTHIC, mage.cards.a.AvatarAang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avatar Enthusiasts", 11, Rarity.COMMON, mage.cards.a.AvatarEnthusiasts.class));
        cards.add(new SetCardInfo("Azula Always Lies", 84, Rarity.COMMON, mage.cards.a.AzulaAlwaysLies.class));
        cards.add(new SetCardInfo("Badgermole", 166, Rarity.COMMON, mage.cards.b.Badgermole.class));
        cards.add(new SetCardInfo("Barrels of Blasting Jelly", 254, Rarity.COMMON, mage.cards.b.BarrelsOfBlastingJelly.class));
        cards.add(new SetCardInfo("Beetle-Headed Merchants", 86, Rarity.COMMON, mage.cards.b.BeetleHeadedMerchants.class));
        cards.add(new SetCardInfo("Bender's Waterskin", 255, Rarity.COMMON, mage.cards.b.BendersWaterskin.class));
        cards.add(new SetCardInfo("Buzzard-Wasp Colony", 88, Rarity.UNCOMMON, mage.cards.b.BuzzardWaspColony.class));
        cards.add(new SetCardInfo("Cat-Gator", 91, Rarity.UNCOMMON, mage.cards.c.CatGator.class));
        cards.add(new SetCardInfo("Cat-Owl", 212, Rarity.COMMON, mage.cards.c.CatOwl.class));
        cards.add(new SetCardInfo("Corrupt Court Official", 92, Rarity.COMMON, mage.cards.c.CorruptCourtOfficial.class));
        cards.add(new SetCardInfo("Dai Li Indoctrination", 93, Rarity.COMMON, mage.cards.d.DaiLiIndoctrination.class));
        cards.add(new SetCardInfo("Deserter's Disciple", 131, Rarity.COMMON, mage.cards.d.DesertersDisciple.class));
        cards.add(new SetCardInfo("Earth Kingdom Soldier", 216, Rarity.COMMON, mage.cards.e.EarthKingdomSoldier.class));
        cards.add(new SetCardInfo("Earth Rumble", 174, Rarity.UNCOMMON, mage.cards.e.EarthRumble.class));
        cards.add(new SetCardInfo("Earth Village Ruffians", 219, Rarity.COMMON, mage.cards.e.EarthVillageRuffians.class));
        cards.add(new SetCardInfo("Earthbending Lesson", 176, Rarity.COMMON, mage.cards.e.EarthbendingLesson.class));
        cards.add(new SetCardInfo("Epic Downfall", 96, Rarity.UNCOMMON, mage.cards.e.EpicDownfall.class));
        cards.add(new SetCardInfo("Fated Firepower", 132, Rarity.MYTHIC, mage.cards.f.FatedFirepower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fated Firepower", 341, Rarity.MYTHIC, mage.cards.f.FatedFirepower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Lord Sozin", 117, Rarity.MYTHIC, mage.cards.f.FireLordSozin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Lord Sozin", 356, Rarity.MYTHIC, mage.cards.f.FireLordSozin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Lord Zuko", 221, Rarity.RARE, mage.cards.f.FireLordZuko.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Lord Zuko", 360, Rarity.RARE, mage.cards.f.FireLordZuko.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Nation Attacks", 133, Rarity.UNCOMMON, mage.cards.f.FireNationAttacks.class));
        cards.add(new SetCardInfo("Fire Nation Engineer", 99, Rarity.UNCOMMON, mage.cards.f.FireNationEngineer.class));
        cards.add(new SetCardInfo("Fire Sages", 136, Rarity.UNCOMMON, mage.cards.f.FireSages.class));
        cards.add(new SetCardInfo("First-Time Flyer", 49, Rarity.COMMON, mage.cards.f.FirstTimeFlyer.class));
        cards.add(new SetCardInfo("Flexible Waterbender", 50, Rarity.COMMON, mage.cards.f.FlexibleWaterbender.class));
        cards.add(new SetCardInfo("Flopsie, Bumi's Buddy", 179, Rarity.UNCOMMON, mage.cards.f.FlopsieBumisBuddy.class));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Geyser Leaper", 52, Rarity.COMMON, mage.cards.g.GeyserLeaper.class));
        cards.add(new SetCardInfo("Giant Koi", 53, Rarity.COMMON, mage.cards.g.GiantKoi.class));
        cards.add(new SetCardInfo("Glider Kids", 21, Rarity.COMMON, mage.cards.g.GliderKids.class));
        cards.add(new SetCardInfo("Hakoda, Selfless Commander", 366, Rarity.RARE, mage.cards.h.HakodaSelflessCommander.class));
        cards.add(new SetCardInfo("Haru, Hidden Talent", 182, Rarity.UNCOMMON, mage.cards.h.HaruHiddenTalent.class));
        cards.add(new SetCardInfo("Heartless Act", 103, Rarity.UNCOMMON, mage.cards.h.HeartlessAct.class));
        cards.add(new SetCardInfo("Hei Bai, Spirit of Balance", 225, Rarity.UNCOMMON, mage.cards.h.HeiBaiSpiritOfBalance.class));
        cards.add(new SetCardInfo("Hog-Monkey", 104, Rarity.COMMON, mage.cards.h.HogMonkey.class));
        cards.add(new SetCardInfo("How to Start a Riot", 140, Rarity.COMMON, mage.cards.h.HowToStartARiot.class));
        cards.add(new SetCardInfo("Iguana Parrot", 56, Rarity.COMMON, mage.cards.i.IguanaParrot.class));
        cards.add(new SetCardInfo("Island", 283, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 288, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("It'll Quench Ya!", 58, Rarity.COMMON, mage.cards.i.ItllQuenchYa.class));
        cards.add(new SetCardInfo("Jeong Jeong's Deserters", 25, Rarity.COMMON, mage.cards.j.JeongJeongsDeserters.class));
        cards.add(new SetCardInfo("Katara, Bending Prodigy", 59, Rarity.UNCOMMON, mage.cards.k.KataraBendingProdigy.class));
        cards.add(new SetCardInfo("Katara, Water Tribe's Hope", 231, Rarity.RARE, mage.cards.k.KataraWaterTribesHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, Water Tribe's Hope", 351, Rarity.RARE, mage.cards.k.KataraWaterTribesHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, the Fearless", 230, Rarity.RARE, mage.cards.k.KataraTheFearless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, the Fearless", 350, Rarity.RARE, mage.cards.k.KataraTheFearless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, the Fearless", 361, Rarity.RARE, mage.cards.k.KataraTheFearless.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Strike", 146, Rarity.COMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Long Feng, Grand Secretariat", 233, Rarity.UNCOMMON, mage.cards.l.LongFengGrandSecretariat.class));
        cards.add(new SetCardInfo("Master Pakku", 63, Rarity.UNCOMMON, mage.cards.m.MasterPakku.class));
        cards.add(new SetCardInfo("Master Piandao", 28, Rarity.UNCOMMON, mage.cards.m.MasterPiandao.class));
        cards.add(new SetCardInfo("Merchant of Many Hats", 110, Rarity.COMMON, mage.cards.m.MerchantOfManyHats.class));
        cards.add(new SetCardInfo("Momo, Friendly Flier", 29, Rarity.RARE, mage.cards.m.MomoFriendlyFlier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Momo, Friendly Flier", 317, Rarity.RARE, mage.cards.m.MomoFriendlyFlier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mongoose Lizard", 148, Rarity.COMMON, mage.cards.m.MongooseLizard.class));
        cards.add(new SetCardInfo("Mountain", 285, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 290, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ostrich-Horse", 188, Rarity.COMMON, mage.cards.o.OstrichHorse.class));
        cards.add(new SetCardInfo("Otter-Penguin", 67, Rarity.COMMON, mage.cards.o.OtterPenguin.class));
        cards.add(new SetCardInfo("Ozai's Cruelty", 113, Rarity.UNCOMMON, mage.cards.o.OzaisCruelty.class));
        cards.add(new SetCardInfo("Path to Redemption", 31, Rarity.COMMON, mage.cards.p.PathToRedemption.class));
        cards.add(new SetCardInfo("Pillar Launch", 189, Rarity.COMMON, mage.cards.p.PillarLaunch.class));
        cards.add(new SetCardInfo("Plains", 282, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 287, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Pretending Poxbearers", 237, Rarity.COMMON, mage.cards.p.PretendingPoxbearers.class));
        cards.add(new SetCardInfo("Rabaroo Troop", 32, Rarity.COMMON, mage.cards.r.RabarooTroop.class));
        cards.add(new SetCardInfo("Raucous Audience", 190, Rarity.COMMON, mage.cards.r.RaucousAudience.class));
        cards.add(new SetCardInfo("Razor Rings", 33, Rarity.COMMON, mage.cards.r.RazorRings.class));
        cards.add(new SetCardInfo("Rebellious Captives", 191, Rarity.COMMON, mage.cards.r.RebelliousCaptives.class));
        cards.add(new SetCardInfo("Redirect Lightning", 151, Rarity.RARE, mage.cards.r.RedirectLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Redirect Lightning", 343, Rarity.RARE, mage.cards.r.RedirectLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rocky Rebuke", 193, Rarity.COMMON, mage.cards.r.RockyRebuke.class));
        cards.add(new SetCardInfo("Rough Rhino Cavalry", 152, Rarity.COMMON, mage.cards.r.RoughRhinoCavalry.class));
        cards.add(new SetCardInfo("Rowdy Snowballers", 68, Rarity.COMMON, mage.cards.r.RowdySnowballers.class));
        cards.add(new SetCardInfo("Saber-Tooth Moose-Lion", 194, Rarity.COMMON, mage.cards.s.SaberToothMooseLion.class));
        cards.add(new SetCardInfo("Serpent of the Pass", 70, Rarity.UNCOMMON, mage.cards.s.SerpentOfThePass.class));
        cards.add(new SetCardInfo("Sokka's Haiku", 71, Rarity.UNCOMMON, mage.cards.s.SokkasHaiku.class));
        cards.add(new SetCardInfo("Sokka, Bold Boomeranger", 240, Rarity.RARE, mage.cards.s.SokkaBoldBoomeranger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokka, Bold Boomeranger", 383, Rarity.RARE, mage.cards.s.SokkaBoldBoomeranger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokka, Lateral Strategist", 241, Rarity.UNCOMMON, mage.cards.s.SokkaLateralStrategist.class));
        cards.add(new SetCardInfo("Southern Air Temple", 36, Rarity.UNCOMMON, mage.cards.s.SouthernAirTemple.class));
        cards.add(new SetCardInfo("Suki, Kyoshi Warrior", 243, Rarity.UNCOMMON, mage.cards.s.SukiKyoshiWarrior.class));
        cards.add(new SetCardInfo("Swamp", 284, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 289, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("The Rise of Sozin", 117, Rarity.MYTHIC, mage.cards.t.TheRiseOfSozin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Rise of Sozin", 356, Rarity.MYTHIC, mage.cards.t.TheRiseOfSozin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toph, the Blind Bandit", 198, Rarity.UNCOMMON, mage.cards.t.TophTheBlindBandit.class));
        cards.add(new SetCardInfo("Toph, the First Metalbender", 247, Rarity.RARE, mage.cards.t.TophTheFirstMetalbender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toph, the First Metalbender", 353, Rarity.RARE, mage.cards.t.TophTheFirstMetalbender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Toph, the First Metalbender", 362, Rarity.RARE, mage.cards.t.TophTheFirstMetalbender.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Turtle-Duck", 200, Rarity.COMMON, mage.cards.t.TurtleDuck.class));
        cards.add(new SetCardInfo("Vindictive Warden", 249, Rarity.COMMON, mage.cards.v.VindictiveWarden.class));
        cards.add(new SetCardInfo("Waterbending Lesson", 80, Rarity.COMMON, mage.cards.w.WaterbendingLesson.class));
        cards.add(new SetCardInfo("Watery Grasp", 82, Rarity.COMMON, mage.cards.w.WateryGrasp.class));
        cards.add(new SetCardInfo("Yue, the Moon Spirit", 338, Rarity.RARE, mage.cards.y.YueTheMoonSpirit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yue, the Moon Spirit", 83, Rarity.RARE, mage.cards.y.YueTheMoonSpirit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yuyan Archers", 161, Rarity.COMMON, mage.cards.y.YuyanArchers.class));
        cards.add(new SetCardInfo("Zuko, Exiled Prince", 163, Rarity.UNCOMMON, mage.cards.z.ZukoExiledPrince.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
