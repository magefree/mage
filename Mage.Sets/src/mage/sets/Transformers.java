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
        // cards.add(new SetCardInfo("Blaster, Combat DJ", 8, Rarity.MYTHIC, mage.cards.a.BlasterCombatDj.class, NON_FULL_USE_VARIOUS));
        // cards.add(new SetCardInfo("Blaster, Combat DJ", 22, Rarity.MYTHIC, mage.cards.a.BlasterCombatDj.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blitzwing, Cruel Tormentor", 4, Rarity.MYTHIC, mage.cards.b.BlitzwingCruelTormentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blitzwing, Cruel Tormentor", 19, Rarity.MYTHIC, mage.cards.b.BlitzwingCruelTormentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cyclonus, the Saboteur", 9, Rarity.MYTHIC, mage.cards.c.CyclonusTheSaboteur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cyclonus, the Saboteur", 23, Rarity.MYTHIC, mage.cards.c.CyclonusTheSaboteur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flamewar, Brash Veteran", 10, Rarity.MYTHIC, mage.cards.f.FlamewarBrashVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flamewar, Brash Veteran", 24, Rarity.MYTHIC, mage.cards.f.FlamewarBrashVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goldbug, Humanity's Ally", 11, Rarity.MYTHIC, mage.cards.g.GoldbugHumanitysAlly.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goldbug, Humanity's Ally", 25, Rarity.MYTHIC, mage.cards.g.GoldbugHumanitysAlly.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jetfire, Ingenious Scientist", 3, Rarity.MYTHIC, mage.cards.j.JetfireIngeniousScientist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jetfire, Ingenious Scientist", 18, Rarity.MYTHIC, mage.cards.j.JetfireIngeniousScientist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Megatron, Tyrant", 12, Rarity.MYTHIC, mage.cards.m.MegatronTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Megatron, Tyrant", 26, Rarity.MYTHIC, mage.cards.m.MegatronTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Optimus Prime, Hero", 13, Rarity.MYTHIC, mage.cards.o.OptimusPrimeHero.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Optimus Prime, Hero", 27, Rarity.MYTHIC, mage.cards.o.OptimusPrimeHero.class, NON_FULL_USE_VARIOUS));
        // cards.add(new SetCardInfo("Prowl, Stoic Strategist", 1, Rarity.MYTHIC, mage.cards.a.ProwlStoicStrategist.class, NON_FULL_USE_VARIOUS));
        // cards.add(new SetCardInfo("Prowl, Stoic Strategist", 16, Rarity.MYTHIC, mage.cards.a.ProwlStoicStrategist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ratchet, Field Medic", 2, Rarity.MYTHIC, mage.cards.r.RatchetFieldMedic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ratchet, Field Medic", 17, Rarity.MYTHIC, mage.cards.r.RatchetFieldMedic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slicer, Hired Muscle", 6, Rarity.MYTHIC, mage.cards.s.SlicerHiredMuscle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slicer, Hired Muscle", 21, Rarity.MYTHIC, mage.cards.s.SlicerHiredMuscle.class, NON_FULL_USE_VARIOUS));
        // cards.add(new SetCardInfo("Soundwave, Sonic Spy", 14, Rarity.MYTHIC, mage.cards.s.SoundwaveSonicSpy.class, NON_FULL_USE_VARIOUS));
        // cards.add(new SetCardInfo("Soundwave, Sonic Spy", 28, Rarity.MYTHIC, mage.cards.s.SoundwaveSonicSpy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starscream, Power Hungry", 5, Rarity.MYTHIC, mage.cards.s.StarscreamPowerHungry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starscream, Power Hungry", 20, Rarity.MYTHIC, mage.cards.s.StarscreamPowerHungry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ultra Magnus, Tactician", 15, Rarity.MYTHIC, mage.cards.u.UltraMagnusTactician.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ultra Magnus, Tactician", 29, Rarity.MYTHIC, mage.cards.u.UltraMagnusTactician.class, NON_FULL_USE_VARIOUS));
    }
}