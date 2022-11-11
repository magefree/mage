package mage.cards.p;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author North
 */
public final class PeelFromReality extends CardImpl {

    public PeelFromReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature you control and target creature you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private PeelFromReality(final PeelFromReality card) {
        super(card);
    }

    @Override
    public PeelFromReality copy() {
        return new PeelFromReality(this);
    }
}
