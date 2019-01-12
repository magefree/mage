
package mage.abilities.keyword;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.Card;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * 701.32. Support
 *
 * 701.32a “Support N” on a permanent means “Put a +1/+1 counter on each of up
 * to N other target creatures.” “Support N” on an instant or sorcery spell
 * means “Put a +1/+1 counter on each of up to N target creatures.”
 *
 * @author LevelX2
 */
public class SupportAbility extends EntersBattlefieldTriggeredAbility {

    public SupportAbility(Card card, int amount) {
        super(new SupportEffect(card, amount, true));
        if (!card.isInstant() && !card.isSorcery()) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures");
            if (card.isCreature()) {
                filter.add(AnotherPredicate.instance);
                filter.setMessage("other target creatures");
            }
            addTarget(new TargetCreaturePermanent(0, amount, filter, false));
        }
    }

    public SupportAbility(final SupportAbility ability) {
        super(ability);
    }

    @Override
    public SupportAbility copy() {
        return new SupportAbility(this);
    }
}
