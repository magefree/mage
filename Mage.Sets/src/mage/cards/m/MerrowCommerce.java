
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class MerrowCommerce extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Merfolk you control");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public MerrowCommerce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);


        // At the beginning of your end step, untap all Merfolk you control.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new UntapAllControllerEffect(filter, "untap all Merfolk you control"), false));
    }

    private MerrowCommerce(final MerrowCommerce card) {
        super(card);
    }

    @Override
    public MerrowCommerce copy() {
        return new MerrowCommerce(this);
    }
}
