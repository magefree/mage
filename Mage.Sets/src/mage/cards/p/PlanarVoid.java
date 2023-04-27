package mage.cards.p;

import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class PlanarVoid extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    static {
        filter.add(AnotherPredicate.instance);
    }

    public PlanarVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever another card is put into a graveyard from anywhere, exile that card.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(new ExileTargetEffect(), false, filter, TargetController.ANY, SetTargetPointer.CARD));
    }

    private PlanarVoid(final PlanarVoid card) {
        super(card);
    }

    @Override
    public PlanarVoid copy() {
        return new PlanarVoid(this);
    }
}
