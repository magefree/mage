package mage.constants;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.filter.common.*;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum AffinityType {
    // Card types
    ARTIFACTS(new FilterControlledArtifactPermanent("artifacts"), ArtifactYouControlHint.instance),
    CREATURES(new FilterControlledCreaturePermanent("creatures"), CreaturesYouControlHint.instance),
    ARTIFACT_CREATURES(AffinityFilters.ARTIFACT_CREATURES),
    ENCHANTMENTS(new FilterControlledEnchantmentPermanent("enchantments")),
    PLANESWALKERS(new FilterControlledPlaneswalkerPermanent("planeswalkers")),

    // Basic Lands
    PLAINS(new FilterControlledPermanent(SubType.PLAINS, "Plains")),
    ISLANDS(new FilterControlledPermanent(SubType.ISLAND, "Islands")),
    SWAMPS(new FilterControlledPermanent(SubType.SWAMP, "Swamps")),
    MOUNTAINS(new FilterControlledPermanent(SubType.MOUNTAIN, "Mountains")),
    FORESTS(new FilterControlledPermanent(SubType.FOREST, "Forests")),

    // Subtypes
    ALLIES(new FilterControlledPermanent(SubType.ALLY, "Allies"), "Ally"),
    AURAS(new FilterControlledPermanent(SubType.AURA, "Auras")),
    BIRDS(new FilterControlledPermanent(SubType.BIRD, "Birds")),
    CITIZENS(new FilterControlledPermanent(SubType.CITIZEN, "Citizens")),
    DALEKS(new FilterControlledPermanent(SubType.DALEK, "Daleks")),
    ELVES(new FilterControlledPermanent(SubType.ELF, "Elves")),
    EQUIPMENT(new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment"), "Equipment"),
    FOOD(new FilterControlledPermanent(SubType.FOOD, "Food"), "Food"),
    FROGS(new FilterControlledPermanent(SubType.FROG, "Frogs")),
    GATES(new FilterControlledPermanent(SubType.GATE, "Gates"), GatesYouControlHint.instance),
    HUMANS(new FilterControlledPermanent(SubType.HUMAN, "Humans")),
    KNIGHTS(new FilterControlledPermanent(SubType.KNIGHT, "Knights")),
    LIZARDS(new FilterControlledPermanent(SubType.LIZARD, "Lizards")),
    SLIVERS(new FilterControlledPermanent(SubType.SLIVER, "Slivers")),
    SPIRITS(new FilterControlledPermanent(SubType.SPIRIT, "Spirits")),
    TOWNS(new FilterControlledPermanent(SubType.TOWN, "Towns")),

    OUTLAWS(AffinityFilters.OUTLAWS),
    HISTORIC(AffinityFilters.HISTORIC),
    SNOW_LANDS(AffinityFilters.SNOW_LANDS),
    TOKENS(AffinityFilters.TOKENS);

    private final FilterControlledPermanent filter;
    private final Hint hint;
    private final String singularName;

    AffinityType(FilterControlledPermanent filter) {
        this(filter, filter.getMessage());
    }

    AffinityType(FilterControlledPermanent filter, String singularName) {
        this(filter, new ValueHint(
                CardUtil.getTextWithFirstCharUpperCase(filter.getMessage()) + " you control",
                new PermanentsOnBattlefieldCount(filter)
        ), singularName);
    }

    AffinityType(FilterControlledPermanent filter, Hint hint) {
        this(filter, hint, filter.getMessage().substring(0, filter.getMessage().length() - 1));
    }

    AffinityType(FilterControlledPermanent filter, Hint hint, String singularName) {
        this.filter = filter;
        this.hint = hint;
        this.singularName = singularName;
    }

    public FilterControlledPermanent getFilter() {
        return filter;
    }

    public Hint getHint() {
        return hint;
    }

    public String getSingularName() {
        return singularName;
    }
}

class AffinityFilters {
    static final FilterControlledPermanent TOKENS = new FilterControlledPermanent("tokens");

    static {
        TOKENS.add(TokenPredicate.TRUE);
    }

    static final FilterControlledPermanent SNOW_LANDS = new FilterControlledLandPermanent("snow lands");

    static {
        SNOW_LANDS.add(SuperType.SNOW.getPredicate());
    }

    static final FilterControlledPermanent OUTLAWS = new FilterControlledPermanent("outlaws");

    static {
        OUTLAWS.add(OutlawPredicate.instance);
    }

    static final FilterControlledPermanent HISTORIC = new FilterControlledPermanent("historic permanents");

    static {
        HISTORIC.add(HistoricPredicate.instance);
    }

    static final FilterControlledPermanent ARTIFACT_CREATURES = new FilterControlledPermanent("artifact creatures");

    static {
        ARTIFACT_CREATURES.add(CardType.ARTIFACT.getPredicate());
        ARTIFACT_CREATURES.add(CardType.CREATURE.getPredicate());
    }
}
