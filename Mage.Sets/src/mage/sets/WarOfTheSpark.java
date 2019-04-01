package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class WarOfTheSpark extends ExpansionSet {

    private static final WarOfTheSpark instance = new WarOfTheSpark();

    public static WarOfTheSpark getInstance() {
        return instance;
    }

    private WarOfTheSpark() {
        super("War of the Spark", "WAR", ExpansionSet.buildDate(2019, 5, 3), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.needsPlaneswalker = true;
        this.maxCardNumberInBooster = 264;

        cards.add(new SetCardInfo("Ajani's Pridemate", 4, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, the Greathearted", 184, Rarity.RARE, mage.cards.a.AjaniTheGreathearted.class));
        cards.add(new SetCardInfo("Arlinn's Wolf", 151, Rarity.COMMON, mage.cards.a.ArlinnsWolf.class));
        cards.add(new SetCardInfo("Arlinn, Voice of the Pack", 150, Rarity.UNCOMMON, mage.cards.a.ArlinnVoiceOfThePack.class));
        cards.add(new SetCardInfo("Crush Dissent", 47, Rarity.COMMON, mage.cards.c.CrushDissent.class));
        cards.add(new SetCardInfo("Dreadhorde Invasion", 86, Rarity.RARE, mage.cards.d.DreadhordeInvasion.class));
        cards.add(new SetCardInfo("Emergence Zone", 245, Rarity.UNCOMMON, mage.cards.e.EmergenceZone.class));
        cards.add(new SetCardInfo("Flux Channeler", 52, Rarity.UNCOMMON, mage.cards.f.FluxChanneler.class));
        cards.add(new SetCardInfo("Forest", 262, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 263, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Herald of the Dreadhorde", 93, Rarity.COMMON, mage.cards.h.HeraldOfTheDreadhorde.class));
        cards.add(new SetCardInfo("Ignite the Beacon", 18, Rarity.RARE, mage.cards.i.IgniteTheBeacon.class));
        cards.add(new SetCardInfo("Interplanar Beacon", 247, Rarity.UNCOMMON, mage.cards.i.InterplanarBeacon.class));
        cards.add(new SetCardInfo("Invade the City", 201, Rarity.UNCOMMON, mage.cards.i.InvadeTheCity.class));
        cards.add(new SetCardInfo("Island", 253, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Wielder of Mysteries", 54, Rarity.RARE, mage.cards.j.JaceWielderOfMysteries.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", 97, Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class));
        cards.add(new SetCardInfo("Mountain", 259, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 260, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 261, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No Escape", 63, Rarity.COMMON, mage.cards.n.NoEscape.class));
        cards.add(new SetCardInfo("Ob Nixilis's Cruelty", 101, Rarity.COMMON, mage.cards.o.ObNixilissCruelty.class));
        cards.add(new SetCardInfo("Ob Nixilis, the Hate-Twisted", 100, Rarity.UNCOMMON, mage.cards.o.ObNixilisTheHateTwisted.class));
        cards.add(new SetCardInfo("Paradise Druid", 171, Rarity.UNCOMMON, mage.cards.p.ParadiseDruid.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravnica at War", 28, Rarity.RARE, mage.cards.r.RavnicaAtWar.class));
        cards.add(new SetCardInfo("Relentless Advance", 64, Rarity.COMMON, mage.cards.r.RelentlessAdvance.class));
        cards.add(new SetCardInfo("Swamp", 256, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 257, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Master of the Bridge", 275, Rarity.MYTHIC, mage.cards.t.TezzeretMasterOfTheBridge.class));
        cards.add(new SetCardInfo("Tibalt's Rager", 147, Rarity.UNCOMMON, mage.cards.t.TibaltsRager.class));
        cards.add(new SetCardInfo("Tibalt, Rakish Instigator", 146, Rarity.UNCOMMON, mage.cards.t.TibaltRakishInstigator.class));
        cards.add(new SetCardInfo("Time Wipe", 223, Rarity.RARE, mage.cards.t.TimeWipe.class));
        cards.add(new SetCardInfo("Vraska's Finisher", 112, Rarity.COMMON, mage.cards.v.VraskasFinisher.class));
        cards.add(new SetCardInfo("Vraska, Swarm's Eminence", 236, Rarity.UNCOMMON, mage.cards.v.VraskaSwarmsEminence.class));
        cards.add(new SetCardInfo("Widespread Brutality", 226, Rarity.RARE, mage.cards.w.WidespreadBrutality.class));
    }
}
