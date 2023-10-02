package mage.cards.c;

import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author xenohedron
 */
public final class CloudCover extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another permanent you control");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public CloudCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{U}");

        // Whenever another permanent you control becomes the target of a spell or ability an opponent controls, you may return that permanent to its owner's hand.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new ReturnToHandTargetEffect().setText("return that permanent to its owner's hand"),
                filter, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.PERMANENT, true));
    }

    private CloudCover(final CloudCover card) {
        super(card);
    }

    @Override
    public CloudCover copy() {
        return new CloudCover(this);
    }
}
