package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CyclonicRift extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public CyclonicRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent you don't control to its owner's hand.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.ImplementOverloadAbility(this, new ManaCostsImpl<>("{6}{U}"),
                new TargetPermanent(filter), new ReturnToHandTargetEffect());
    }

    private CyclonicRift(final CyclonicRift card) {
        super(card);
    }

    @Override
    public CyclonicRift copy() {
        return new CyclonicRift(this);
    }
}
