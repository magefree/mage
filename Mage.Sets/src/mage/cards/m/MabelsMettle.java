package mage.cards.m;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class MabelsMettle extends CardImpl {

    public MabelsMettle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn. Up to one other target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
    }

    private MabelsMettle(final MabelsMettle card) {
        super(card);
    }

    @Override
    public MabelsMettle copy() {
        return new MabelsMettle(this);
    }
}
