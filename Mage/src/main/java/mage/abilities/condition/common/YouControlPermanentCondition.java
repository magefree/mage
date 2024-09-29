package mage.abilities.condition.common;

import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.util.CardUtil;

/**
 * PermanentsOnTheBattlefieldCondition with onlyControlled parameter at true
 * Separate as it has a name closer to card's text, so easier to find.
 *
 * @author Susucr
 */
public class YouControlPermanentCondition extends PermanentsOnTheBattlefieldCondition {

    public Hint getHint() {
        return new ConditionHint(this);
    }

    public YouControlPermanentCondition(FilterPermanent filter) {
        this(filter, ComparisonType.OR_GREATER, 1);
    }

    public YouControlPermanentCondition(FilterPermanent filter, ComparisonType comparisonType, int count) {
        super(filter, comparisonType, count, true);
    }

    @Override
    public String toString() {
        String text = "you control ";
        String filterText = filter.getMessage();
        if (filterText.endsWith(" you control")) {
            filterText = filterText.substring(0, filterText.length() - " you control".length());
        }
        switch (type) {
            case OR_LESS:
                text += CardUtil.numberToText(count) + " or fewer " + filterText;
                break;
            case OR_GREATER:
                if (count == 1) {
                    text += CardUtil.addArticle(filterText);
                } else {
                    text += CardUtil.numberToText(count) + " or more " + filterText;
                }
                break;
            case EQUAL_TO:
                if (count == 0) {
                    text += "no " + filterText;
                } else {
                    text += "exactly " + CardUtil.numberToText(count) + " " + filterText;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage: ComparisonType not handled in text generation: " + type);
        }
        return text;
    }
}
