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

        cards.add(new SetCardInfo("Arcee, Sharpshooter", 7, Rarity.MYTHIC, mage.cards.a.ArceeSharpshooter.class));
        cards.add(new SetCardInfo("Blitzwing, Cruel Tormentor", 4, Rarity.MYTHIC, mage.cards.b.BlitzwingCruelTormentor.class));
        cards.add(new SetCardInfo("Flamewar, Brash Veteran", 10, Rarity.MYTHIC, mage.cards.f.FlamewarBrashVeteran.class));
        cards.add(new SetCardInfo("Goldbug, Humanity's Ally", 11, Rarity.MYTHIC, mage.cards.g.GoldbugHumanitysAlly.class));
        cards.add(new SetCardInfo("Jetfire, Ingenious Scientist", 3, Rarity.MYTHIC, mage.cards.j.JetfireIngeniousScientist.class));
        cards.add(new SetCardInfo("Megatron, Tyrant", 12, Rarity.MYTHIC, mage.cards.m.MegatronTyrant.class));
        cards.add(new SetCardInfo("Optimus Prime, Hero", 13, Rarity.MYTHIC, mage.cards.o.OptimusPrimeHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Optimus Prime, Hero", 27, Rarity.MYTHIC, mage.cards.o.OptimusPrimeHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ratchet, Field Medic", 2, Rarity.MYTHIC, mage.cards.r.RatchetFieldMedic.class));
        cards.add(new SetCardInfo("Starscream, Power Hungry", 5, Rarity.MYTHIC, mage.cards.s.StarscreamPowerHungry.class));
        cards.add(new SetCardInfo("Ultra Magnus, Tactician", 15, Rarity.MYTHIC, mage.cards.u.UltraMagnusTactician.class));
    }
}
