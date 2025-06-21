package mage.cards.c;

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
 * @author LevelX2
 */
public final class ConsumeStrength extends CardImpl {

    public ConsumeStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{G}");

        // Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2)
                .setText("another target creature gets -2/-2 until end of turn").setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("+2/+2").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("-2/-2").setTargetTag(2));
    }

    private ConsumeStrength(final ConsumeStrength card) {
        super(card);
    }

    @Override
    public ConsumeStrength copy() {
        return new ConsumeStrength(this);
    }
}
