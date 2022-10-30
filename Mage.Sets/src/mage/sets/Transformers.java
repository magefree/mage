package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Transformers extends ExpansionSet {

    private static final Transformers instance = new Transformers();

    public static Transformers getInstance() {
        return instance;
    }

    private Transformers() {
        super("Transformers", "BOT", ExpansionSet.buildDate(2022, 11, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arcee, Acrobatic Coupe", 7, Rarity.MYTHIC, mage.cards.a.ArceeAcrobaticCoupe.class));
        cards.add(new SetCardInfo("Arcee, Sharpshooter", 7, Rarity.MYTHIC, mage.cards.a.ArceeSharpshooter.class));
        cards.add(new SetCardInfo("Blitzwing, Adaptive Assailant", 4, Rarity.MYTHIC, mage.cards.b.BlitzwingAdaptiveAssailant.class));
        cards.add(new SetCardInfo("Blitzwing, Cruel Tormentor", 4, Rarity.MYTHIC, mage.cards.b.BlitzwingCruelTormentor.class));
        cards.add(new SetCardInfo("Flamewar, Brash Veteran", 10, Rarity.MYTHIC, mage.cards.f.FlamewarBrashVeteran.class));
        cards.add(new SetCardInfo("Flamewar, Streetwise Operative", 10, Rarity.MYTHIC, mage.cards.f.FlamewarStreetwiseOperative.class));
        cards.add(new SetCardInfo("Goldbug, Humanity's Ally", 11, Rarity.MYTHIC, mage.cards.g.GoldbugHumanitysAlly.class));
        cards.add(new SetCardInfo("Goldbug, Scrappy Scout", 11, Rarity.MYTHIC, mage.cards.g.GoldbugScrappyScout.class));
        cards.add(new SetCardInfo("Jetfire, Air Guardian", 3, Rarity.MYTHIC, mage.cards.j.JetfireAirGuardian.class));
        cards.add(new SetCardInfo("Jetfire, Ingenious Scientist", 3, Rarity.MYTHIC, mage.cards.j.JetfireIngeniousScientist.class));
        cards.add(new SetCardInfo("Ratchet, Field Medic", 2, Rarity.MYTHIC, mage.cards.r.RatchetFieldMedic.class));
        cards.add(new SetCardInfo("Ratchet, Rescue Racer", 2, Rarity.MYTHIC, mage.cards.r.RatchetRescueRacer.class));
        cards.add(new SetCardInfo("Ultra Magnus, Armored Carrier", 15, Rarity.MYTHIC, mage.cards.u.UltraMagnusArmoredCarrier.class));
        cards.add(new SetCardInfo("Ultra Magnus, Tactician", 15, Rarity.MYTHIC, mage.cards.u.UltraMagnusTactician.class));
    }
}
