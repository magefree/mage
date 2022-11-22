package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Jumpstart2022 extends ExpansionSet {

    private static final Jumpstart2022 instance = new Jumpstart2022();

    public static Jumpstart2022 getInstance() {
        return instance;
    }

    private Jumpstart2022() {
        super("Jumpstart 2022", "J22", ExpansionSet.buildDate(2020, 12, 2), SetType.SUPPLEMENTAL);
        this.blockName = "Jumpstart";
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani's Pridemate", 142, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, Strength of the Pride", 141, Rarity.MYTHIC, mage.cards.a.AjaniStrengthOfThePride.class));
        cards.add(new SetCardInfo("Ardoz, Cobbler of War", 29, Rarity.RARE, mage.cards.a.ArdozCobblerOfWar.class));
        cards.add(new SetCardInfo("Arlinn, Voice of the Pack", 85, Rarity.UNCOMMON, mage.cards.a.ArlinnVoiceOfThePack.class));
        cards.add(new SetCardInfo("Arrest", 52, Rarity.UNCOMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Balan, Wandering Knight", 53, Rarity.RARE, mage.cards.b.BalanWanderingKnight.class));
        cards.add(new SetCardInfo("Blood Artist", 117, Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Burglar Rat", 384, Rarity.COMMON, mage.cards.b.BurglarRat.class));
        cards.add(new SetCardInfo("Chittering Rats", 387, Rarity.COMMON, mage.cards.c.ChitteringRats.class));
        cards.add(new SetCardInfo("Coldsteel Heart", 94, Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Crypt Rats", 392, Rarity.UNCOMMON, mage.cards.c.CryptRats.class));
        cards.add(new SetCardInfo("Demon of Catastrophes", 397, Rarity.RARE, mage.cards.d.DemonOfCatastrophes.class));
        cards.add(new SetCardInfo("Diabolic Edict", 67, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Dread Presence", 403, Rarity.RARE, mage.cards.d.DreadPresence.class));
        cards.add(new SetCardInfo("Dutiful Replicator", 48, Rarity.COMMON, mage.cards.d.DutifulReplicator.class));
        cards.add(new SetCardInfo("Elvish Rejuvenator", 88, Rarity.COMMON, mage.cards.e.ElvishRejuvenator.class));
        cards.add(new SetCardInfo("Eviscerate", 412, Rarity.COMMON, mage.cards.e.Eviscerate.class));
        cards.add(new SetCardInfo("Feast of Blood", 118, Rarity.UNCOMMON, mage.cards.f.FeastOfBlood.class));
        cards.add(new SetCardInfo("Feast on the Fallen", 68, Rarity.UNCOMMON, mage.cards.f.FeastOnTheFallen.class));
        cards.add(new SetCardInfo("Felidar Retreat", 184, Rarity.RARE, mage.cards.f.FelidarRetreat.class));
        cards.add(new SetCardInfo("Festering Evil", 119, Rarity.UNCOMMON, mage.cards.f.FesteringEvil.class));
        cards.add(new SetCardInfo("Flicker of Fate", 56, Rarity.COMMON, mage.cards.f.FlickerOfFate.class));
        cards.add(new SetCardInfo("Infernal Idol", 49, Rarity.COMMON, mage.cards.i.InfernalIdol.class));
        cards.add(new SetCardInfo("Isu the Abominable", 12, Rarity.MYTHIC, mage.cards.i.IsuTheAbominable.class));
        cards.add(new SetCardInfo("Karn Liberated", 97, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Kibo, Uktabi Prince", 40, Rarity.MYTHIC, mage.cards.k.KiboUktabiPrince.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 79, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("King of the Pride", 57, Rarity.UNCOMMON, mage.cards.k.KingOfThePride.class));
        cards.add(new SetCardInfo("Kothophed, Soul Hoarder", 431, Rarity.RARE, mage.cards.k.KothophedSoulHoarder.class));
        cards.add(new SetCardInfo("Leonin Warleader", 208, Rarity.RARE, mage.cards.l.LeoninWarleader.class));
        cards.add(new SetCardInfo("Magnifying Glass", 95, Rarity.UNCOMMON, mage.cards.m.MagnifyingGlass.class));
        cards.add(new SetCardInfo("Merfolk Pupil", 15, Rarity.COMMON, mage.cards.m.MerfolkPupil.class));
        cards.add(new SetCardInfo("Mirror Image", 62, Rarity.UNCOMMON, mage.cards.m.MirrorImage.class));
        cards.add(new SetCardInfo("Nezumi Bone-Reader", 323, Rarity.UNCOMMON, mage.cards.n.NezumiBoneReader.class));
        cards.add(new SetCardInfo("Oathsworn Vampire", 70, Rarity.UNCOMMON, mage.cards.o.OathswornVampire.class));
        cards.add(new SetCardInfo("Ogre Slumlord", 71, Rarity.RARE, mage.cards.o.OgreSlumlord.class));
        cards.add(new SetCardInfo("Peacewalker Colossus", 96, Rarity.RARE, mage.cards.p.PeacewalkerColossus.class));
        cards.add(new SetCardInfo("Primeval Herald", 42, Rarity.UNCOMMON, mage.cards.p.PrimevalHerald.class));
        cards.add(new SetCardInfo("Regal Caracal", 232, Rarity.RARE, mage.cards.r.RegalCaracal.class));
        cards.add(new SetCardInfo("Renegade Demon", 126, Rarity.COMMON, mage.cards.r.RenegadeDemon.class));
        cards.add(new SetCardInfo("Seizan, Perverter of Truth", 463, Rarity.RARE, mage.cards.s.SeizanPerverterOfTruth.class));
        cards.add(new SetCardInfo("Sinuous Vermin", 465, Rarity.COMMON, mage.cards.s.SinuousVermin.class));
        cards.add(new SetCardInfo("Spectral Sailor", 64, Rarity.UNCOMMON, mage.cards.s.SpectralSailor.class));
        cards.add(new SetCardInfo("Spellstutter Sprite", 65, Rarity.COMMON, mage.cards.s.SpellstutterSprite.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 92, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Towering Gibbon", 46, Rarity.UNCOMMON, mage.cards.t.ToweringGibbon.class));
        cards.add(new SetCardInfo("Trove Warden", 259, Rarity.RARE, mage.cards.t.TroveWarden.class));
        cards.add(new SetCardInfo("Typhoid Rats", 468, Rarity.COMMON, mage.cards.t.TyphoidRats.class));
        cards.add(new SetCardInfo("Uktabi Orangutan", 133, Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class));
        cards.add(new SetCardInfo("Ulcerate", 481, Rarity.UNCOMMON, mage.cards.u.Ulcerate.class));
        cards.add(new SetCardInfo("Walking Ballista", 806, Rarity.RARE, mage.cards.w.WalkingBallista.class));
        cards.add(new SetCardInfo("Whirler Rogue", 66, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Wicked Wolf", 134, Rarity.RARE, mage.cards.w.WickedWolf.class));
    }
}
