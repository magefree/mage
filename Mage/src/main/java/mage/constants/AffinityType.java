package mage.constants;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.filter.StaticFilters;
import mage.filter.common.*;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum AffinityType {
    ARTIFACTS(new FilterControlledArtifactPermanent("artifacts"), ArtifactYouControlHint.instance),
    CREATURES(new FilterControlledCreaturePermanent("creatures"), CreaturesYouControlHint.instance),
    ARTIFACT_CREATURES(StaticFilters.FILTER_AFFINITY_ARTIFACT_CREATURES),
    ENCHANTMENTS(new FilterControlledEnchantmentPermanent("enchantments")),
    PLANESWALKERS(new FilterControlledPlaneswalkerPermanent("planeswalker")),

    EQUIPMENT(new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment"), "Equipment"),
    AURAS(new FilterControlledPermanent(SubType.AURA, "Auras")),
    FOOD(new FilterControlledPermanent(SubType.FOOD, "Food"), "Food"),
    TOKENS(StaticFilters.FILTER_AFFINITY_TOKENS),

    PLAINS(new FilterControlledPermanent(SubType.PLAINS, "Plains")),
    ISLANDS(new FilterControlledPermanent(SubType.ISLAND, "Islands")),
    SWAMPS(new FilterControlledPermanent(SubType.SWAMP, "Swamps")),
    MOUNTAINS(new FilterControlledPermanent(SubType.MOUNTAIN, "Mountains")),
    FORESTS(new FilterControlledPermanent(SubType.FOREST, "Forests")),

    SPIRITS(new FilterControlledPermanent(SubType.SPIRIT, "Spirits")),
    HUMANS(new FilterControlledPermanent(SubType.HUMAN, "Humans")),
    KNIGHTS(new FilterControlledPermanent(SubType.KNIGHT, "Knights")),
    DALEKS(new FilterControlledPermanent(SubType.DALEK, "Daleks")),
    FROGS(new FilterControlledPermanent(SubType.FROG, "Frogs")),
    LIZARDS(new FilterControlledPermanent(SubType.LIZARD, "Lizards")),
    BIRDS(new FilterControlledPermanent(SubType.BIRD, "Birds")),
    CITIZENS(new FilterControlledPermanent(SubType.CITIZEN, "Citizens")),
    TOWNS(new FilterControlledPermanent(SubType.TOWN, "Towns")),
    GATES(new FilterControlledPermanent(SubType.GATE, "Gates"), GatesYouControlHint.instance),
    SNOW_LANDS(StaticFilters.FILTER_AFFINITY_SNOW_LANDS),
    HISTORIC(StaticFilters.FILTER_AFFINITY_HISTORIC),
    OUTLAWS(StaticFilters.FILTER_AFFINITY_OUTLAWS);

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
