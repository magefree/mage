package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class CaptainsDefense extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterBlockingCreature("blocking creature");

    public CaptainsDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target blocking creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CaptainsDefense(final CaptainsDefense card) {
        super(card);
    }

    @Override
    public CaptainsDefense copy() {
        return new CaptainsDefense(this);
    }
}
