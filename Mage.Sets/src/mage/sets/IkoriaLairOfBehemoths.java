package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class IkoriaLairOfBehemoths extends ExpansionSet {

    private static final IkoriaLairOfBehemoths instance = new IkoriaLairOfBehemoths();

    public static IkoriaLairOfBehemoths getInstance() {
        return instance;
    }

    private IkoriaLairOfBehemoths() {
        super("Ikoria: Lair of Behemoths", "IKO", ExpansionSet.buildDate(2020, 4, 24), SetType.EXPANSION);
        this.blockName = "Ikoria: Lair of Behemoths";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;
        this.hasBasicLands = false; // remove when basics are available

        cards.add(new SetCardInfo("Boon of the Wish-Giver", 43, Rarity.UNCOMMON, mage.cards.b.BoonOfTheWishGiver.class));
        cards.add(new SetCardInfo("Bristling Boar", 146, Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Cavern Whisperer", 79, Rarity.COMMON, mage.cards.c.CavernWhisperer.class));
        cards.add(new SetCardInfo("Chittering Harvester", 80, Rarity.UNCOMMON, mage.cards.c.ChitteringHarvester.class));
        cards.add(new SetCardInfo("Cloudpiercer", 112, Rarity.COMMON, mage.cards.c.Cloudpiercer.class));
        cards.add(new SetCardInfo("Colossification", 148, Rarity.RARE, mage.cards.c.Colossification.class));
        cards.add(new SetCardInfo("Dire Tactics", 183, Rarity.UNCOMMON, mage.cards.d.DireTactics.class));
        cards.add(new SetCardInfo("Dirge Bat", 84, Rarity.RARE, mage.cards.d.DirgeBat.class));
        cards.add(new SetCardInfo("Dreamtail Heron", 47, Rarity.COMMON, mage.cards.d.DreamtailHeron.class));
        cards.add(new SetCardInfo("Essence Scatter", 49, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Fertilid", 152, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Footfall Crater", 118, Rarity.UNCOMMON, mage.cards.f.FootfallCrater.class));
        cards.add(new SetCardInfo("Fully Grown", 154, Rarity.COMMON, mage.cards.f.FullyGrown.class));
        cards.add(new SetCardInfo("Gemrazer", 155, Rarity.RARE, mage.cards.g.Gemrazer.class));
        cards.add(new SetCardInfo("Gloom Pangolin", 89, Rarity.COMMON, mage.cards.g.GloomPangolin.class));
        cards.add(new SetCardInfo("Glowstone Recluse", 156, Rarity.UNCOMMON, mage.cards.g.GlowstoneRecluse.class));
        cards.add(new SetCardInfo("Grimdancer", 90, Rarity.UNCOMMON, mage.cards.g.Grimdancer.class));
        cards.add(new SetCardInfo("Indatha Crystal", 235, Rarity.UNCOMMON, mage.cards.i.IndathaCrystal.class));
        cards.add(new SetCardInfo("Ketria Crystal", 236, Rarity.UNCOMMON, mage.cards.k.KetriaCrystal.class));
        cards.add(new SetCardInfo("Kogla, the Titan Ape", 162, Rarity.RARE, mage.cards.k.KoglaTheTitanApe.class));
        cards.add(new SetCardInfo("Migration Path", 164, Rarity.UNCOMMON, mage.cards.m.MigrationPath.class));
        cards.add(new SetCardInfo("Mosscoat Goriak", 167, Rarity.COMMON, mage.cards.m.MosscoatGoriak.class));
        cards.add(new SetCardInfo("Mysterious Egg", 3, Rarity.COMMON, mage.cards.m.MysteriousEgg.class));
        cards.add(new SetCardInfo("Nethroi, Apex of Death", 197, Rarity.MYTHIC, mage.cards.n.NethroiApexOfDeath.class));
        cards.add(new SetCardInfo("Neutralize", 59, Rarity.UNCOMMON, mage.cards.n.Neutralize.class));
        cards.add(new SetCardInfo("Pacifism", 25, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Phase Dolphin", 62, Rarity.COMMON, mage.cards.p.PhaseDolphin.class));
        cards.add(new SetCardInfo("Pouncing Shoreshark", 64, Rarity.UNCOMMON, mage.cards.p.PouncingShoreshark.class));
        cards.add(new SetCardInfo("Primal Empathy", 200, Rarity.UNCOMMON, mage.cards.p.PrimalEmpathy.class));
        cards.add(new SetCardInfo("Raugrin Crystal", 238, Rarity.UNCOMMON, mage.cards.r.RaugrinCrystal.class));
        cards.add(new SetCardInfo("Reconnaissance Mission", 65, Rarity.UNCOMMON, mage.cards.r.ReconnaissanceMission.class));
        cards.add(new SetCardInfo("Savai Crystal", 239, Rarity.UNCOMMON, mage.cards.s.SavaiCrystal.class));
        cards.add(new SetCardInfo("Savai Sabertooth", 29, Rarity.COMMON, mage.cards.s.SavaiSabertooth.class));
        cards.add(new SetCardInfo("Shredded Sails", 136, Rarity.COMMON, mage.cards.s.ShreddedSails.class));
        cards.add(new SetCardInfo("Skull Prophet", 206, Rarity.UNCOMMON, mage.cards.s.SkullProphet.class));
        cards.add(new SetCardInfo("Snapdax, Apex of the Hunt", 209, Rarity.MYTHIC, mage.cards.s.SnapdaxApexOfTheHunt.class));
        cards.add(new SetCardInfo("Sprite Dragon", 211, Rarity.UNCOMMON, mage.cards.s.SpriteDragon.class));
        cards.add(new SetCardInfo("Thieving Otter", 69, Rarity.COMMON, mage.cards.t.ThievingOtter.class));
        cards.add(new SetCardInfo("Titanoth Rex", 174, Rarity.UNCOMMON, mage.cards.t.TitanothRex.class));
        cards.add(new SetCardInfo("Trumpeting Gnarr", 213, Rarity.UNCOMMON, mage.cards.t.TrumpetingGnarr.class));
        cards.add(new SetCardInfo("Void Beckoner", 104, Rarity.UNCOMMON, mage.cards.v.VoidBeckoner.class));
        cards.add(new SetCardInfo("Voracious Greatshark", 70, Rarity.RARE, mage.cards.v.VoraciousGreatshark.class));
        cards.add(new SetCardInfo("Wingfold Pteron", 71, Rarity.COMMON, mage.cards.w.WingfoldPteron.class));
        cards.add(new SetCardInfo("Zagoth Crystal", 242, Rarity.UNCOMMON, mage.cards.z.ZagothCrystal.class));
        cards.add(new SetCardInfo("Zagoth Mamba", 106, Rarity.UNCOMMON, mage.cards.z.ZagothMamba.class));
    }
}
