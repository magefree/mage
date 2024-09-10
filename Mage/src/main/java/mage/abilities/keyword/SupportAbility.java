
package mage.abilities.keyword;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.Card;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * 701.32. Support
 * <p>
 * 701.32a “Support N” on a permanent means “Put a +1/+1 counter on each of up
 * to N other target creatures.” “Support N” on an instant or sorcery spell
 * means “Put a +1/+1 counter on each of up to N target creatures.”
 *
 * @author LevelX2
 */
public class SupportAbility extends EntersBattlefieldTriggeredAbility {

    /*
     * For enchantments, the text should not include the word "other".
     * The otherPermanent choice removes the word "other" from rule text creation.
     */
    public SupportAbility(Card card, int amount, boolean otherPermanent) {
        super(new SupportEffect(card, amount, otherPermanent));
        if (!card.isInstantOrSorcery()) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures");
            if (card.isCreature()) {
                filter.add(AnotherPredicate.instance);
                filter.setMessage("other target creatures");
            }
            addTarget(new TargetCreaturePermanent(0, amount, filter, false));
        }
    }


    public SupportAbility(Card card, int amount) {
        this(card, amount, true);
    }


    protected SupportAbility(final SupportAbility ability) {
        super(ability);
    }

    @Override
    public SupportAbility copy() {
        return new SupportAbility(this);
    }
}
