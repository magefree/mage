package mage.abilities.costs.common;

import mage.Mana;
import mage.abilities.costs.mana.GenericManaCost;

/**
 * 701.67. Waterbend
 * <p>
 * 701.67a “Waterbend [cost]” means “Pay [cost]. For each generic mana in that cost,
 * you may tap an untapped artifact or creature you control rather than pay that mana.”
 * <p>
 * 701.67b If a waterbend cost is part of the total cost to cast a spell or activate an ability
 * (usually because the waterbend cost itself is an additional cost), the alternate method to pay for mana
 * described in rule 701.67a may be used only to pay for the amount of generic mana in the waterbend cost,
 * even if the total cost to cast that spell or activate that ability includes other generic mana components.
 * <p>
 * If you need Waterbend {X} then use {@link WaterbendXCost}
 * If using as an additional cost for a spell, add an ability with an InfoEffect for proper text generation (see WaterWhip)
 *
 * @author TheElk801
 */
public class WaterbendCost extends GenericManaCost {

    public WaterbendCost(int amount) {
        super(amount);
        for (int i = 0; i < amount; i++) {
            options.add(Mana.ColorlessMana(i));
        }
    }

    private WaterbendCost(final WaterbendCost cost) {
        super(cost);
    }

    @Override
    public WaterbendCost copy() {
        return new WaterbendCost(this);
    }

    @Override
    public String getText() {
        return "waterbend " + super.getText();
    }
}
