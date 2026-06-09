package mage.cards.b;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelunasGatekeeper extends AdventureCard {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control with mana value 3 or less");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BelunasGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GIANT, SubType.SOLDIER}, "{5}{U}",
                "Entry Denied",
                new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Beluna's Gatekeeper
        this.getLeftHalfCard().setPT(6, 5);

        // Entry Denied
        // Return target creature you don't control with mana value 3 or less to its owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private BelunasGatekeeper(final BelunasGatekeeper card) {
        super(card);
    }

    @Override
    public BelunasGatekeeper copy() {
        return new BelunasGatekeeper(this);
    }
}
