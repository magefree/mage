
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author emerald000
 */
public final class ElementalBond extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public ElementalBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a creature with power 3 or greater enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private ElementalBond(final ElementalBond card) {
        super(card);
    }

    @Override
    public ElementalBond copy() {
        return new ElementalBond(this);
    }
}
