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
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.needsPlaneswalker = true;
        this.maxCardNumberInBooster = 264;

        cards.add(new SetCardInfo("Ahn-Crop Invader", 113, Rarity.COMMON, mage.cards.a.AhnCropInvader.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 4, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, the Greathearted", 184, Rarity.RARE, mage.cards.a.AjaniTheGreathearted.class));
        cards.add(new SetCardInfo("Angrath's Rampage", 185, Rarity.UNCOMMON, mage.cards.a.AngrathsRampage.class));
        cards.add(new SetCardInfo("Angrath, Captain of Chaos", 227, Rarity.UNCOMMON, mage.cards.a.AngrathCaptainOfChaos.class));
        cards.add(new SetCardInfo("Arlinn's Wolf", 151, Rarity.COMMON, mage.cards.a.ArlinnsWolf.class));
        cards.add(new SetCardInfo("Arlinn, Voice of the Pack", 150, Rarity.UNCOMMON, mage.cards.a.ArlinnVoiceOfThePack.class));
        cards.add(new SetCardInfo("Augur of Bolas", 41, Rarity.UNCOMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Banehound", 77, Rarity.COMMON, mage.cards.b.Banehound.class));
        cards.add(new SetCardInfo("Bulwark Giant", 7, Rarity.COMMON, mage.cards.b.BulwarkGiant.class));
        cards.add(new SetCardInfo("Burning Prophet", 117, Rarity.COMMON, mage.cards.b.BurningProphet.class));
        cards.add(new SetCardInfo("Cruel Celebrant", 188, Rarity.UNCOMMON, mage.cards.c.CruelCelebrant.class));
        cards.add(new SetCardInfo("Crush Dissent", 47, Rarity.COMMON, mage.cards.c.CrushDissent.class));
        cards.add(new SetCardInfo("Davriel's Shadowfugue", 84, Rarity.COMMON, mage.cards.d.DavrielsShadowfugue.class));
        cards.add(new SetCardInfo("Davriel, Rogue Shadowmage", 83, Rarity.UNCOMMON, mage.cards.d.DavrielRogueShadowmage.class));
        cards.add(new SetCardInfo("Deathsprout", 189, Rarity.UNCOMMON, mage.cards.d.Deathsprout.class));
        cards.add(new SetCardInfo("Dovin's Veto", 193, Rarity.UNCOMMON, mage.cards.d.DovinsVeto.class));
        cards.add(new SetCardInfo("Dreadhorde Butcher", 194, Rarity.RARE, mage.cards.d.DreadhordeButcher.class));
        cards.add(new SetCardInfo("Dreadhorde Invasion", 86, Rarity.RARE, mage.cards.d.DreadhordeInvasion.class));
        cards.add(new SetCardInfo("Emergence Zone", 245, Rarity.UNCOMMON, mage.cards.e.EmergenceZone.class));
        cards.add(new SetCardInfo("Erratic Visionary", 48, Rarity.COMMON, mage.cards.e.ErraticVisionary.class));
        cards.add(new SetCardInfo("Eternal Taskmaster", 90, Rarity.UNCOMMON, mage.cards.e.EternalTaskmaster.class));
        cards.add(new SetCardInfo("Flux Channeler", 52, Rarity.UNCOMMON, mage.cards.f.FluxChanneler.class));
        cards.add(new SetCardInfo("Forest", 262, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Growth", 162, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gideon's Triumph", 15, Rarity.UNCOMMON, mage.cards.g.GideonsTriumph.class));
        cards.add(new SetCardInfo("Gleaming Overseer", 198, Rarity.UNCOMMON, mage.cards.g.GleamingOverseer.class));
        cards.add(new SetCardInfo("God-Pharaoh's Statue", 238, Rarity.UNCOMMON, mage.cards.g.GodPharaohsStatue.class));
        cards.add(new SetCardInfo("Grim Initiate", 130, Rarity.COMMON, mage.cards.g.GrimInitiate.class));
        cards.add(new SetCardInfo("Herald of the Dreadhorde", 93, Rarity.COMMON, mage.cards.h.HeraldOfTheDreadhorde.class));
        cards.add(new SetCardInfo("Honor the God-Pharaoh", 132, Rarity.COMMON, mage.cards.h.HonorTheGodPharaoh.class));
        cards.add(new SetCardInfo("Ignite the Beacon", 18, Rarity.RARE, mage.cards.i.IgniteTheBeacon.class));
        cards.add(new SetCardInfo("Interplanar Beacon", 247, Rarity.UNCOMMON, mage.cards.i.InterplanarBeacon.class));
        cards.add(new SetCardInfo("Invade the City", 201, Rarity.UNCOMMON, mage.cards.i.InvadeTheCity.class));
        cards.add(new SetCardInfo("Invading Manticore", 134, Rarity.COMMON, mage.cards.i.InvadingManticore.class));
        cards.add(new SetCardInfo("Island", 253, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace, Wielder of Mysteries", 54, Rarity.RARE, mage.cards.j.JaceWielderOfMysteries.class));
        cards.add(new SetCardInfo("Jiang Yanggu, Wildcrafter", 164, Rarity.UNCOMMON, mage.cards.j.JiangYangguWildcrafter.class));
        cards.add(new SetCardInfo("Karn's Bastion", 248, Rarity.RARE, mage.cards.k.KarnsBastion.class));
        cards.add(new SetCardInfo("Kaya, Bane of the Dead", 231, Rarity.UNCOMMON, mage.cards.k.KayaBaneOfTheDead.class));
        cards.add(new SetCardInfo("Kiora's Dambreaker", 58, Rarity.COMMON, mage.cards.k.KiorasDambreaker.class));
        cards.add(new SetCardInfo("Kiora, Behemoth Beckoner", 232, Rarity.UNCOMMON, mage.cards.k.KioraBehemothBeckoner.class));
        cards.add(new SetCardInfo("Lazotep Behemoth", 95, Rarity.COMMON, mage.cards.l.LazotepBehemoth.class));
        cards.add(new SetCardInfo("Lazotep Plating", 59, Rarity.UNCOMMON, mage.cards.l.LazotepPlating.class));
        cards.add(new SetCardInfo("Lazotep Reaver", 96, Rarity.COMMON, mage.cards.l.LazotepReaver.class));
        cards.add(new SetCardInfo("Leyline Prowler", 202, Rarity.UNCOMMON, mage.cards.l.LeylineProwler.class));
        cards.add(new SetCardInfo("Liliana's Triumph", 98, Rarity.UNCOMMON, mage.cards.l.LilianasTriumph.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", 97, Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class));
        cards.add(new SetCardInfo("Mountain", 260, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 261, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mowu, Loyal Companion", 167, Rarity.UNCOMMON, mage.cards.m.MowuLoyalCompanion.class));
        cards.add(new SetCardInfo("Naga Eternal", 60, Rarity.COMMON, mage.cards.n.NagaEternal.class));
        cards.add(new SetCardInfo("Nahiri's Stoneblades", 139, Rarity.COMMON, mage.cards.n.NahirisStoneblades.class));
        cards.add(new SetCardInfo("Nahiri, Storm of Stone", 233, Rarity.UNCOMMON, mage.cards.n.NahiriStormOfStone.class));
        cards.add(new SetCardInfo("Neheb, Dreadhorde Champion", 140, Rarity.RARE, mage.cards.n.NehebDreadhordeChampion.class));
        cards.add(new SetCardInfo("No Escape", 63, Rarity.COMMON, mage.cards.n.NoEscape.class));
        cards.add(new SetCardInfo("Ob Nixilis's Cruelty", 101, Rarity.COMMON, mage.cards.o.ObNixilissCruelty.class));
        cards.add(new SetCardInfo("Ob Nixilis, the Hate-Twisted", 100, Rarity.UNCOMMON, mage.cards.o.ObNixilisTheHateTwisted.class));
        cards.add(new SetCardInfo("Paradise Druid", 171, Rarity.UNCOMMON, mage.cards.p.ParadiseDruid.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravnica at War", 28, Rarity.RARE, mage.cards.r.RavnicaAtWar.class));
        cards.add(new SetCardInfo("Relentless Advance", 64, Rarity.COMMON, mage.cards.r.RelentlessAdvance.class));
        cards.add(new SetCardInfo("Rising Populace", 29, Rarity.COMMON, mage.cards.r.RisingPopulace.class));
        cards.add(new SetCardInfo("Samut's Sprint", 142, Rarity.COMMON, mage.cards.s.SamutsSprint.class));
        cards.add(new SetCardInfo("Samut, Tyrant Smasher", 235, Rarity.UNCOMMON, mage.cards.s.SamutTyrantSmasher.class));
        cards.add(new SetCardInfo("Sorin's Thirst", 104, Rarity.COMMON, mage.cards.s.SorinsThirst.class));
        cards.add(new SetCardInfo("Sorin, Vengeful Bloodlord", 217, Rarity.RARE, mage.cards.s.SorinVengefulBloodlord.class));
        cards.add(new SetCardInfo("Spellgorger Weird", 145, Rarity.COMMON, mage.cards.s.SpellgorgerWeird.class));
        cards.add(new SetCardInfo("Swamp", 256, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Time Raveler", 221, Rarity.RARE, mage.cards.t.TeferiTimeRaveler.class));
        cards.add(new SetCardInfo("Teyo's Lightshield", 33, Rarity.COMMON, mage.cards.t.TeyosLightshield.class));
        cards.add(new SetCardInfo("Teyo, the Shieldmage", 32, Rarity.UNCOMMON, mage.cards.t.TeyoTheShieldmage.class));
        cards.add(new SetCardInfo("Tezzeret, Master of the Bridge", 275, Rarity.MYTHIC, mage.cards.t.TezzeretMasterOfTheBridge.class));
        cards.add(new SetCardInfo("The Wanderer", 37, Rarity.UNCOMMON, mage.cards.t.TheWanderer.class));
        cards.add(new SetCardInfo("Tibalt's Rager", 147, Rarity.UNCOMMON, mage.cards.t.TibaltsRager.class));
        cards.add(new SetCardInfo("Tibalt, Rakish Instigator", 146, Rarity.UNCOMMON, mage.cards.t.TibaltRakishInstigator.class));
        cards.add(new SetCardInfo("Time Wipe", 223, Rarity.RARE, mage.cards.t.TimeWipe.class));
        cards.add(new SetCardInfo("Totally Lost", 74, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Vivien's Arkbow", 181, Rarity.RARE, mage.cards.v.ViviensArkbow.class));
        cards.add(new SetCardInfo("Vivien's Grizzly", 182, Rarity.COMMON, mage.cards.v.ViviensGrizzly.class));
        cards.add(new SetCardInfo("Vraska's Finisher", 112, Rarity.COMMON, mage.cards.v.VraskasFinisher.class));
        cards.add(new SetCardInfo("Vraska, Swarm's Eminence", 236, Rarity.UNCOMMON, mage.cards.v.VraskaSwarmsEminence.class));
        cards.add(new SetCardInfo("Wanderer's Strike", 38, Rarity.COMMON, mage.cards.w.WanderersStrike.class));
        cards.add(new SetCardInfo("Widespread Brutality", 226, Rarity.RARE, mage.cards.w.WidespreadBrutality.class));
    }
}
