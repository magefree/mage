package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public final class Commander2018 extends ExpansionSet {

    private static final Commander2018 instance = new Commander2018();

    public static Commander2018 getInstance() {
        return instance;
    }

    private Commander2018() {
        super("Commander 2018 Edition", "C18", ExpansionSet.buildDate(2018, 8, 10), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Ancient Stone Idol", 53, Rarity.RARE, mage.cards.a.AncientStoneIdol.class));
        cards.add(new SetCardInfo("Avenger of Zendikar", 129, Rarity.MYTHIC, mage.cards.a.AvengerOfZendikar.class));
        cards.add(new SetCardInfo("Bear Umbra", 131, Rarity.RARE, mage.cards.b.BearUmbra.class));
        cards.add(new SetCardInfo("Bloodtracker", 14, Rarity.RARE, mage.cards.b.Bloodtracker.class));
        cards.add(new SetCardInfo("Bruna, Light of Alabaster", 170, Rarity.MYTHIC, mage.cards.b.BrunaLightOfAlabaster.class));
        cards.add(new SetCardInfo("Budoka Gardener", 134, Rarity.RARE, mage.cards.b.BudokaGardener.class));
        cards.add(new SetCardInfo("Centaur Vinecrasher", 135, Rarity.RARE, mage.cards.c.CentaurVinecrasher.class));
        cards.add(new SetCardInfo("Chain Reaction", 121, Rarity.RARE, mage.cards.c.ChainReaction.class));
        cards.add(new SetCardInfo("Chaos Warp", 122, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Crash of Rhino Beetles", 29, Rarity.RARE, mage.cards.c.CrashOfRhinoBeetles.class));
        cards.add(new SetCardInfo("Dictate of Kruphix", 86, Rarity.RARE, mage.cards.d.DictateOfKruphix.class));
        cards.add(new SetCardInfo("Echo Storm", 7, Rarity.RARE, mage.cards.e.EchoStorm.class));
        cards.add(new SetCardInfo("Eel Umbra", 89, Rarity.COMMON, mage.cards.e.EelUmbra.class));
        cards.add(new SetCardInfo("Eidolon of Blossoms", 140, Rarity.RARE, mage.cards.e.EidolonOfBlossoms.class));
        cards.add(new SetCardInfo("Empyrial Storm", 2, Rarity.RARE, mage.cards.e.EmpyrialStorm.class));
        cards.add(new SetCardInfo("Enchanter's Bane", 21, Rarity.RARE, mage.cards.e.EnchantersBane.class));
        cards.add(new SetCardInfo("Enchantress's Presence", 141, Rarity.RARE, mage.cards.e.EnchantresssPresence.class));
        cards.add(new SetCardInfo("Explosive Vegetation", 144, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Finest Hour", 180, Rarity.RARE, mage.cards.f.FinestHour.class));
        cards.add(new SetCardInfo("Forge of Heroes", 58, Rarity.COMMON, mage.cards.f.ForgeOfHeroes.class));
        cards.add(new SetCardInfo("Fury Storm", 22, Rarity.RARE, mage.cards.f.FuryStorm.class));
        cards.add(new SetCardInfo("Genesis Storm", 30, Rarity.RARE, mage.cards.g.GenesisStorm.class));
        cards.add(new SetCardInfo("Herald of the Pantheon", 151, Rarity.RARE, mage.cards.h.HeraldOfThePantheon.class));
        cards.add(new SetCardInfo("Hydra Omnivore", 153, Rarity.MYTHIC, mage.cards.h.HydraOmnivore.class));
        cards.add(new SetCardInfo("Kestia, the Cultivator", 42, Rarity.MYTHIC, mage.cards.k.KestiaTheCultivator.class));
        cards.add(new SetCardInfo("Lord Windgrace", 43, Rarity.MYTHIC, mage.cards.l.LordWindgrace.class));
        cards.add(new SetCardInfo("Loyal Apprentice", 23, Rarity.UNCOMMON, mage.cards.l.LoyalApprentice.class));
        cards.add(new SetCardInfo("Loyal Drake", 10, Rarity.UNCOMMON, mage.cards.l.LoyalDrake.class));
        cards.add(new SetCardInfo("Loyal Guardian", 31, Rarity.UNCOMMON, mage.cards.l.LoyalGuardian.class));
        cards.add(new SetCardInfo("Loyal Subordinate", 16, Rarity.UNCOMMON, mage.cards.l.LoyalSubordinate.class));
        cards.add(new SetCardInfo("Nesting Dragon", 24, Rarity.RARE, mage.cards.n.NestingDragon.class));
        cards.add(new SetCardInfo("Nylea's Colossus", 33, Rarity.RARE, mage.cards.n.NyleasColossus.class));
        cards.add(new SetCardInfo("Octopus Umbra", 11, Rarity.RARE, mage.cards.o.OctopusUmbra.class));
        cards.add(new SetCardInfo("Rampaging Baloths", 158, Rarity.RARE, mage.cards.r.RampagingBaloths.class));
        cards.add(new SetCardInfo("Ravenous Slime", 34, Rarity.RARE, mage.cards.r.RavenousSlime.class));
        cards.add(new SetCardInfo("Retrofitter Foundry", 57, Rarity.RARE, mage.cards.r.RetrofitterFoundry.class));
        cards.add(new SetCardInfo("Ruinous Path", 117, Rarity.RARE, mage.cards.r.RuinousPath.class));
        cards.add(new SetCardInfo("Saheeli's Directive", 26, Rarity.RARE, mage.cards.s.SaheelisDirective.class));
        cards.add(new SetCardInfo("Scute Mob", 161, Rarity.RARE, mage.cards.s.ScuteMob.class));
        cards.add(new SetCardInfo("Sigil of the Empty Throne", 74, Rarity.RARE, mage.cards.s.SigilOfTheEmptyThrone.class));
        cards.add(new SetCardInfo("Spawning Grounds", 163, Rarity.RARE, mage.cards.s.SpawningGrounds.class));
        cards.add(new SetCardInfo("Tawnos, Urza's Apprentice", 45, Rarity.MYTHIC, mage.cards.t.TawnosUrzasApprentice.class));
        cards.add(new SetCardInfo("Thantis the Warweaver", 46, Rarity.MYTHIC, mage.cards.t.ThantisTheWarweaver.class));
        cards.add(new SetCardInfo("Thopter Spy Network", 107, Rarity.RARE, mage.cards.t.ThopterSpyNetwork.class));
        cards.add(new SetCardInfo("Turntimber Sower", 35, Rarity.RARE, mage.cards.t.TurntimberSower.class));
        cards.add(new SetCardInfo("Tuvasa the Sunlit", 47, Rarity.MYTHIC, mage.cards.t.TuvasaTheSunlit.class));
        cards.add(new SetCardInfo("Varchild, Betrayer of Kjeldor", 28, Rarity.RARE, mage.cards.v.VarchildBetrayerOfKjeldor.class));
        cards.add(new SetCardInfo("Whiptongue Hydra", 36, Rarity.RARE, mage.cards.w.WhiptongueHydra.class));
        cards.add(new SetCardInfo("Winds of Rath", 79, Rarity.RARE, mage.cards.w.WindsOfRath.class));
    }
}
