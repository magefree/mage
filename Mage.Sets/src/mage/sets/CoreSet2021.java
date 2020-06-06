package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class CoreSet2021 extends ExpansionSet {

    private static final CoreSet2021 instance = new CoreSet2021();

    public static CoreSet2021 getInstance() {
        return instance;
    }

    private final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private CoreSet2021() {
        super("Core Set 2021", "M21", ExpansionSet.buildDate(2020, 7, 3), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterSpecial = 0;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;

        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 173, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class));
        cards.add(new SetCardInfo("Bad Deal", 89, Rarity.UNCOMMON, mage.cards.b.BadDeal.class));
        cards.add(new SetCardInfo("Baneslayer Angel", 4, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class));
        cards.add(new SetCardInfo("Chandra's Firemaw", 333, Rarity.RARE, mage.cards.c.ChandrasFiremaw.class));
        cards.add(new SetCardInfo("Containment Priest", 314, Rarity.RARE, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Double Vision", 142, Rarity.RARE, mage.cards.d.DoubleVision.class));
        cards.add(new SetCardInfo("Fierce Empath", 181, Rarity.UNCOMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Gadrak, the Crown-Scourge", 146, Rarity.RARE, mage.cards.g.GadrakTheCrownScourge.class));
        cards.add(new SetCardInfo("Grim Tutor", 103, Rarity.MYTHIC, mage.cards.g.GrimTutor.class));
        cards.add(new SetCardInfo("Indulging Patrician", 219, Rarity.UNCOMMON, mage.cards.i.IndulgingPatrician.class));
        cards.add(new SetCardInfo("Island", 310, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeskai Elder", 53, Rarity.UNCOMMON, mage.cards.j.JeskaiElder.class));
        cards.add(new SetCardInfo("Llanowar Visionary", 193, Rarity.COMMON, mage.cards.l.LlanowarVisionary.class));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 27, Rarity.MYTHIC, mage.cards.m.MangaraTheDiplomat.class));
        cards.add(new SetCardInfo("Mystic Skyfish", 326, Rarity.COMMON, mage.cards.m.MysticSkyfish.class));
        cards.add(new SetCardInfo("Pack Leader", 29, Rarity.RARE, mage.cards.p.PackLeader.class));
        cards.add(new SetCardInfo("Peer into the Abyss", 117, Rarity.RARE, mage.cards.p.PeerIntoTheAbyss.class));
        cards.add(new SetCardInfo("Quirion Dryad", 198, Rarity.UNCOMMON, mage.cards.q.QuirionDryad.class));
        cards.add(new SetCardInfo("Rain of Revelation", 61, Rarity.UNCOMMON, mage.cards.r.RainOfRevelation.class));
        cards.add(new SetCardInfo("Runed Halo", 32, Rarity.RARE, mage.cards.r.RunedHalo.class));
        cards.add(new SetCardInfo("Shipwreck Dowser", 71, Rarity.UNCOMMON, mage.cards.s.ShipwreckDowser.class));
        cards.add(new SetCardInfo("Sigiled Contender", 323, Rarity.UNCOMMON, mage.cards.s.SigiledContender.class));
        cards.add(new SetCardInfo("Sparkhunter Masticore", 240, Rarity.RARE, mage.cards.s.SparkhunterMasticore.class));
        cards.add(new SetCardInfo("Spirit of Malevolence", 331, Rarity.COMMON, mage.cards.s.SpiritOfMalevolence.class));
        cards.add(new SetCardInfo("Storm Caller", 335, Rarity.COMMON, mage.cards.s.StormCaller.class));
        cards.add(new SetCardInfo("Teferi's Protege", 77, Rarity.COMMON, mage.cards.t.TeferisProtege.class));
        cards.add(new SetCardInfo("Teferi, Master of Time", 75, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 241, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Vito, Thorn of the Dusk Rose", 127, Rarity.RARE, mage.cards.v.VitoThornOfTheDuskRose.class));
        cards.add(new SetCardInfo("Wildwood Patrol", 339, Rarity.COMMON, mage.cards.w.WildwoodPatrol.class));
    }
}
