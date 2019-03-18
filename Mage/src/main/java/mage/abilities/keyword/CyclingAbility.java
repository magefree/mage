
package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.CyclingDiscardCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CyclingAbility extends ActivatedAbilityImpl {

    private final Cost cost;
    private final String text;

    public CyclingAbility(Cost cost) {
        super(Zone.HAND, new DrawCardSourceControllerEffect(1), cost);
        this.addCost(new CyclingDiscardCost());
        this.cost = cost;
        this.text = "Cycling";
    }

    public CyclingAbility(Cost cost, FilterCard filter, String text) {
        super(Zone.HAND, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), cost);
        this.addCost(new CyclingDiscardCost());
        this.cost = cost;
        this.text = text;
    }

    public CyclingAbility(final CyclingAbility ability) {
        super(ability);
        this.cost = ability.cost;
        this.text = ability.text;
    }

    @Override
    public CyclingAbility copy() {
        return new CyclingAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder rule = new StringBuilder(this.text);
        if (cost instanceof ManaCost) {
            rule.append(' ');
        } else {
            rule.append("&mdash;");
        }
        rule.append(cost.getText()).append(" <i>(").append(super.getRule(true)).append(")</i>");
        return rule.toString();
    }

}
