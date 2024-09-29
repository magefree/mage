
package mage.abilities.effects.keyword;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class SupportEffect extends AddCountersTargetEffect {

    private final DynamicValue amountSupportTargets;
    private final boolean otherPermanent;

    public SupportEffect(Card card, int amount, boolean otherPermanent) {
        super(CounterType.P1P1.createInstance(0), StaticValue.get(1));
        this.amountSupportTargets = StaticValue.get(amount);
        this.otherPermanent = otherPermanent;
        if (card.isInstantOrSorcery()) {
            card.getSpellAbility().addTarget(new TargetCreaturePermanent(0, amount, new FilterCreaturePermanent("target creatures"), false));
        }
        staticText = setText();
    }

    protected SupportEffect(final SupportEffect effect) {
        super(effect);
        this.amountSupportTargets = effect.amountSupportTargets;
        this.otherPermanent = effect.otherPermanent;
    }

    @Override
    public SupportEffect copy() {
        return new SupportEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("support ");
        if (amountSupportTargets instanceof StaticValue) {
            sb.append(amountSupportTargets);
            sb.append(". <i>(Put a +1/+1 counter on each of up to ");
            sb.append(CardUtil.numberToText(amountSupportTargets.toString()));
        } else {
            sb.append("X, where X is the number of ");
            sb.append(amountSupportTargets.getMessage());
            sb.append(". (Put a +1/+1 counter on each of up to X");
        }
        if (otherPermanent) {
            sb.append(" other");
        }
        sb.append(" target creatures.)</i>");
        return sb.toString();
    }
}
