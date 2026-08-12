package mage.cards.e;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class EvasiveManeuvers extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("blocking creature you control");

    static {
        filter.add(BlockingPredicate.instance);
    }

    public EvasiveManeuvers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target blocking creature you control gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EvasiveManeuvers(final EvasiveManeuvers card) {
        super(card);
    }

    @Override
    public EvasiveManeuvers copy() {
        return new EvasiveManeuvers(this);
    }
}
