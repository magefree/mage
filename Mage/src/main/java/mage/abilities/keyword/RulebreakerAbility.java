package mage.abilities.keyword;

import mage.ObjectColor;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.util.CardUtil;

/**
 *
 * @author Grath
 */
public class RulebreakerAbility extends StaticAbility implements MageSingleton {

    private final FilterCard filter;
    private final boolean onlyOneExtraColor;
    private final boolean maximumDeckSize;
    private ObjectColor extraColor;
    private String rulesText;

    public RulebreakerAbility(FilterCard filter) {
        this(filter, false);
    }

    public RulebreakerAbility(FilterCard filter, boolean onlyOneExtraColor) {
        this(filter, onlyOneExtraColor, true);
    }

    public RulebreakerAbility(FilterCard filter, boolean onlyOneExtraColor, boolean maximumDeckSize) {
        super(Zone.BATTLEFIELD, null);
        this.filter = filter;
        this.onlyOneExtraColor = onlyOneExtraColor;
        this.maximumDeckSize = maximumDeckSize;
        this.extraColor = null;
        this.rulesText = null;
    }

    protected RulebreakerAbility(final RulebreakerAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.onlyOneExtraColor = ability.onlyOneExtraColor;
        this.maximumDeckSize = ability.maximumDeckSize;
        this.extraColor = ability.extraColor;
        this.rulesText = ability.rulesText;
    }

    @Override
    public RulebreakerAbility copy() {
        return new RulebreakerAbility(this);
    }

    @Override
    public String getRule() {
        if (rulesText != null) {
            return rulesText;
        }
        StringBuilder sb = new StringBuilder(CardUtil.italicizeWithEmDash("Rulebreaker"));
        sb.append("A deck with this commander ");
        if (filter != null) {
            sb.append(filter);
        }
        if (!maximumDeckSize) {
            sb.append("has no maximum deck size.");
        }
        return sb.toString();
    }

    public FilterCard getFilter() {
        return filter;
    }

    public boolean getOnlyOneExtraColor() {
        return onlyOneExtraColor;
    }

    public ObjectColor getExtraColor() {
        return extraColor;
    }

    public void setExtraColor(ObjectColor color) {
        this.extraColor = color;
    }

    public boolean getMaximumDeckSize() {
        return maximumDeckSize;
    }

    public RulebreakerAbility setText(String text) {
        // The 98-days-before-release functional errata to Tolabow also radically changed the text away from the other
        // Rulebreaker templating. Thanks, WotC!
        this.rulesText = text;
        return this;
    }

    public static RulebreakerAbility subtypeRuleBreaker(SubType subType) {
        FilterCard filter = new FilterCard(" can have " + subType.getDescription() + " cards of any color identity and any basic lands.");
        if (subType.getSubTypeSet() == SubTypeSet.CreatureType) {
            // Important Note: If you're implementing a Rulebreaker which applies to an entire creature type, you must also
            // filter for cards with Changeling, because the game is null and thus Changeling isn't yet applied.
            filter.add(Predicates.or(subType.getPredicate(), new AbilityPredicate(ChangelingAbility.class), SuperType.BASIC.getPredicate()));
        }
        else {
            filter.add(Predicates.or(subType.getPredicate(), SuperType.BASIC.getPredicate()));
        }
        return new RulebreakerAbility(filter);
    }

}
