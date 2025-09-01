package mage.cards.l;

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
 * @author North
 */
public final class LeechingBite extends CardImpl {

    public LeechingBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +1/+1 until end of turn. Another target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1).setTargetPointer(new SecondTargetPointer()).setText("Another target creature gets -1/-1 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("+1/+1").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("-1/-1").setTargetTag(2));
    }

    private LeechingBite(final LeechingBite card) {
        super(card);
    }

    @Override
    public LeechingBite copy() {
        return new LeechingBite(this);
    }
}
