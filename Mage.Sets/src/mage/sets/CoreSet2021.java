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
        cards.add(new SetCardInfo("Baneslayer Angel", 4, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class));
        cards.add(new SetCardInfo("Containment Priest", 314, Rarity.RARE, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Fierce Empath", 181, Rarity.UNCOMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Grim Tutor", 103, Rarity.MYTHIC, mage.cards.g.GrimTutor.class));
        cards.add(new SetCardInfo("Island", 310, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeskai Elder", 53, Rarity.UNCOMMON, mage.cards.j.JeskaiElder.class));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 27, Rarity.MYTHIC, mage.cards.m.MangaraTheDiplomat.class));
        cards.add(new SetCardInfo("Quirion Dryad", 198, Rarity.UNCOMMON, mage.cards.q.QuirionDryad.class));
        cards.add(new SetCardInfo("Rain of Revelation", 61, Rarity.UNCOMMON, mage.cards.r.RainOfRevelation.class));
        cards.add(new SetCardInfo("Runed Halo", 32, Rarity.RARE, mage.cards.r.RunedHalo.class));
        cards.add(new SetCardInfo("Teferi's Protege", 77, Rarity.COMMON, mage.cards.t.TeferisProtege.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 241, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Wildwood Patrol", 339, Rarity.COMMON, mage.cards.w.WildwoodPatrol.class));
    }
}
