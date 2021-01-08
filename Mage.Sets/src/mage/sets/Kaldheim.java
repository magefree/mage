package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Kaldheim extends ExpansionSet {

    private static final Kaldheim instance = new Kaldheim();

    public static Kaldheim getInstance() {
        return instance;
    }

    private Kaldheim() {
        super("Kaldheim", "KHM", ExpansionSet.buildDate(2021, 2, 5), SetType.EXPANSION);
        this.blockName = "Kaldheim";
        this.hasBasicLands = false; // TODO: change after more cards added
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 285;

        cards.add(new SetCardInfo("Absorb Identity", 383, Rarity.UNCOMMON, mage.cards.a.AbsorbIdentity.class));
        cards.add(new SetCardInfo("Alpine Meadow", 248, Rarity.COMMON, mage.cards.a.AlpineMeadow.class));
        cards.add(new SetCardInfo("Arctic Treeline", 249, Rarity.COMMON, mage.cards.a.ArcticTreeline.class));
        cards.add(new SetCardInfo("Armed and Armored", 379, Rarity.UNCOMMON, mage.cards.a.ArmedAndArmored.class));
        cards.add(new SetCardInfo("Barkchannel Pathway", 251, Rarity.RARE, mage.cards.b.BarkchannelPathway.class));
        cards.add(new SetCardInfo("Bearded Axe", 388, Rarity.UNCOMMON, mage.cards.b.BeardedAxe.class));
        cards.add(new SetCardInfo("Blightstep Pathway", 252, Rarity.RARE, mage.cards.b.BlightstepPathway.class));
        cards.add(new SetCardInfo("Canopy Tactician", 378, Rarity.RARE, mage.cards.c.CanopyTactician.class));
        cards.add(new SetCardInfo("Cleaving Reaper", 376, Rarity.RARE, mage.cards.c.CleavingReaper.class));
        cards.add(new SetCardInfo("Darkbore Pathway", 254, Rarity.RARE, mage.cards.d.DarkborePathway.class));
        cards.add(new SetCardInfo("Elderfang Ritualist", 385, Rarity.UNCOMMON, mage.cards.e.ElderfangRitualist.class));
        cards.add(new SetCardInfo("Elven Ambush", 391, Rarity.UNCOMMON, mage.cards.e.ElvenAmbush.class));
        cards.add(new SetCardInfo("Fire Giant's Fury", 389, Rarity.UNCOMMON, mage.cards.f.FireGiantsFury.class));
        cards.add(new SetCardInfo("Giant's Grasp", 384, Rarity.UNCOMMON, mage.cards.g.GiantsGrasp.class));
        cards.add(new SetCardInfo("Gilded Assault Cart", 390, Rarity.UNCOMMON, mage.cards.g.GildedAssaultCart.class));
        cards.add(new SetCardInfo("Glacial Floodplain", 257, Rarity.COMMON, mage.cards.g.GlacialFloodplain.class));
        cards.add(new SetCardInfo("Gladewalker Ritualist", 392, Rarity.UNCOMMON, mage.cards.g.GladewalkerRitualist.class));
        cards.add(new SetCardInfo("Halvar, God of Battle", 15, Rarity.MYTHIC, mage.cards.h.HalvarGodOfBattle.class));
        cards.add(new SetCardInfo("Hengegate Pathway", 260, Rarity.RARE, mage.cards.h.HengegatePathway.class));
        cards.add(new SetCardInfo("Highland Forest", 261, Rarity.COMMON, mage.cards.h.HighlandForest.class));
        cards.add(new SetCardInfo("Ice Tunnel", 262, Rarity.COMMON, mage.cards.i.IceTunnel.class));
        cards.add(new SetCardInfo("Invasion of the Giants", 215, Rarity.UNCOMMON, mage.cards.i.InvasionOfTheGiants.class));
        cards.add(new SetCardInfo("Kaya the Inexorable", 218, Rarity.MYTHIC, mage.cards.k.KayaTheInexorable.class));
        cards.add(new SetCardInfo("Magda, Brazen Outlaw", 142, Rarity.RARE, mage.cards.m.MagdaBrazenOutlaw.class));
        cards.add(new SetCardInfo("Pyre of Heroes", 241, Rarity.RARE, mage.cards.p.PyreOfHeroes.class));
        cards.add(new SetCardInfo("Rampage of the Valkyries", 393, Rarity.UNCOMMON, mage.cards.r.RampageOfTheValkyries.class));
        cards.add(new SetCardInfo("Realmwalker", 188, Rarity.RARE, mage.cards.r.Realmwalker.class));
        cards.add(new SetCardInfo("Renegade Reaper", 386, Rarity.UNCOMMON, mage.cards.r.RenegadeReaper.class));
        cards.add(new SetCardInfo("Rimewood Falls", 266, Rarity.COMMON, mage.cards.r.RimewoodFalls.class));
        cards.add(new SetCardInfo("Sarulf, Realm Eater", 228, Rarity.RARE, mage.cards.s.SarulfRealmEater.class));
        cards.add(new SetCardInfo("Showdown of the Skalds", 229, Rarity.RARE, mage.cards.s.ShowdownOfTheSkalds.class));
        cards.add(new SetCardInfo("Snowfield Sinkhole", 269, Rarity.COMMON, mage.cards.s.SnowfieldSinkhole.class));
        cards.add(new SetCardInfo("Starnheim Aspirant", 380, Rarity.UNCOMMON, mage.cards.s.StarnheimAspirant.class));
        cards.add(new SetCardInfo("Sulfurous Mine", 270, Rarity.COMMON, mage.cards.s.SulfurousMine.class));
        cards.add(new SetCardInfo("Surtland Elementalist", 375, Rarity.RARE, mage.cards.s.SurtlandElementalist.class));
        cards.add(new SetCardInfo("Surtland Flinger", 377, Rarity.RARE, mage.cards.s.SurtlandFlinger.class));
        cards.add(new SetCardInfo("Thornmantle Striker", 387, Rarity.UNCOMMON, mage.cards.t.ThornmantleStriker.class));
        cards.add(new SetCardInfo("Toski, Bearer of Secrets", 197, Rarity.RARE, mage.cards.t.ToskiBearerOfSecrets.class));
        cards.add(new SetCardInfo("Valkyrie Harbinger", 374, Rarity.RARE, mage.cards.v.ValkyrieHarbinger.class));
        cards.add(new SetCardInfo("Volatile Fjord", 273, Rarity.COMMON, mage.cards.v.VolatileFjord.class));
        cards.add(new SetCardInfo("Warchanter Skald", 381, Rarity.UNCOMMON, mage.cards.w.WarchanterSkald.class));
        cards.add(new SetCardInfo("Woodland Chasm", 274, Rarity.COMMON, mage.cards.w.WoodlandChasm.class));
        cards.add(new SetCardInfo("Youthful Valkyrie", 382, Rarity.UNCOMMON, mage.cards.y.YouthfulValkyrie.class));
    }
}
