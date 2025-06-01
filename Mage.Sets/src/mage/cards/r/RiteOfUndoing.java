package mage.cards.r;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RiteOfUndoing extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public RiteOfUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Delve
        this.addAbility(new DelveAbility(false));

        // Return target nonland permanent you control and target nonland permanent you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RiteOfUndoing(final RiteOfUndoing card) {
        super(card);
    }

    @Override
    public RiteOfUndoing copy() {
        return new RiteOfUndoing(this);
    }
}
