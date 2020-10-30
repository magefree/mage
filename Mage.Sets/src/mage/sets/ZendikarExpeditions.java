package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class ZendikarExpeditions extends ExpansionSet {

    private static final ZendikarExpeditions instance = new ZendikarExpeditions();

    public static ZendikarExpeditions getInstance() {
        return instance;
    }

    private ZendikarExpeditions() {
        super("Zendikar Expeditions", "EXP", ExpansionSet.buildDate(2015, 10, 2), SetType.PROMOTIONAL);
        this.blockName = "Masterpiece Series";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Tomb", 36, Rarity.MYTHIC, mage.cards.a.AncientTomb.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Arid Mesa", 24, Rarity.MYTHIC, mage.cards.a.AridMesa.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 8, Rarity.MYTHIC, mage.cards.b.BloodCrypt.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Bloodstained Mire", 18, Rarity.MYTHIC, mage.cards.b.BloodstainedMire.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 15, Rarity.MYTHIC, mage.cards.b.BreedingPool.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Canopy Vista", 5, Rarity.MYTHIC, mage.cards.c.CanopyVista.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Cascade Bluffs", 32, Rarity.MYTHIC, mage.cards.c.CascadeBluffs.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Cinder Glade", 4, Rarity.MYTHIC, mage.cards.c.CinderGlade.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Dust Bowl", 37, Rarity.MYTHIC, mage.cards.d.DustBowl.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Eye of Ugin", 38, Rarity.MYTHIC, mage.cards.e.EyeOfUgin.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fetid Heath", 31, Rarity.MYTHIC, mage.cards.f.FetidHeath.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fire-Lit Thicket", 29, Rarity.MYTHIC, mage.cards.f.FireLitThicket.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Flooded Grove", 35, Rarity.MYTHIC, mage.cards.f.FloodedGrove.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Flooded Strand", 16, Rarity.MYTHIC, mage.cards.f.FloodedStrand.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forbidden Orchard", 39, Rarity.MYTHIC, mage.cards.f.ForbiddenOrchard.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 11, Rarity.MYTHIC, mage.cards.g.GodlessShrine.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Graven Cairns", 28, Rarity.MYTHIC, mage.cards.g.GravenCairns.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 6, Rarity.MYTHIC, mage.cards.h.HallowedFountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Horizon Canopy", 40, Rarity.MYTHIC, mage.cards.h.HorizonCanopy.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Kor Haven", 41, Rarity.MYTHIC, mage.cards.k.KorHaven.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mana Confluence", 42, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 21, Rarity.MYTHIC, mage.cards.m.MarshFlats.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Misty Rainforest", 25, Rarity.MYTHIC, mage.cards.m.MistyRainforest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mystic Gate", 26, Rarity.MYTHIC, mage.cards.m.MysticGate.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 13, Rarity.MYTHIC, mage.cards.o.OvergrownTomb.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 17, Rarity.MYTHIC, mage.cards.p.PollutedDelta.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Prairie Stream", 1, Rarity.MYTHIC, mage.cards.p.PrairieStream.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Rugged Prairie", 34, Rarity.MYTHIC, mage.cards.r.RuggedPrairie.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 14, Rarity.MYTHIC, mage.cards.s.SacredFoundry.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Scalding Tarn", 22, Rarity.MYTHIC, mage.cards.s.ScaldingTarn.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Smoldering Marsh", 3, Rarity.MYTHIC, mage.cards.s.SmolderingMarsh.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 12, Rarity.MYTHIC, mage.cards.s.SteamVents.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 9, Rarity.MYTHIC, mage.cards.s.StompingGround.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", 43, Rarity.MYTHIC, mage.cards.s.StripMine.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sunken Hollow", 2, Rarity.MYTHIC, mage.cards.s.SunkenHollow.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sunken Ruins", 27, Rarity.MYTHIC, mage.cards.s.SunkenRuins.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tectonic Edge", 44, Rarity.MYTHIC, mage.cards.t.TectonicEdge.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 10, Rarity.MYTHIC, mage.cards.t.TempleGarden.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Twilight Mire", 33, Rarity.MYTHIC, mage.cards.t.TwilightMire.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Verdant Catacombs", 23, Rarity.MYTHIC, mage.cards.v.VerdantCatacombs.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", 45, Rarity.MYTHIC, mage.cards.w.Wasteland.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 7, Rarity.MYTHIC, mage.cards.w.WateryGrave.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Windswept Heath", 20, Rarity.MYTHIC, mage.cards.w.WindsweptHeath.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Wooded Bastion", 30, Rarity.MYTHIC, mage.cards.w.WoodedBastion.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Wooded Foothills", 19, Rarity.MYTHIC, mage.cards.w.WoodedFoothills.class, FULL_ART_BFZ_VARIOUS));
    }
}
