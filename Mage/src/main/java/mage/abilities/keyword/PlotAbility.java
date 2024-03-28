package mage.abilities.keyword;

import mage.abilities.SpecialAction;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * TODO: Implement this
 *
 * @author TheElk801
 */
public class PlotAbility extends SpecialAction {

    public PlotAbility(String plotCost) {
        super(Zone.HAND);
        this.addCost(new ManaCostsImpl<>(plotCost));
        this.setTiming(TimingRule.SORCERY);
    }

    private PlotAbility(final PlotAbility ability) {
        super(ability);
    }

    @Override
    public PlotAbility copy() {
        return new PlotAbility(this);
    }

    @Override
    public String getRule() {
        return "Plot";
    }
}
