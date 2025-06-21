package mage.cards.e;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ElementalBond extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public ElementalBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature with power 3 or greater you control enters, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private ElementalBond(final ElementalBond card) {
        super(card);
    }

    @Override
    public ElementalBond copy() {
        return new ElementalBond(this);
    }
}
