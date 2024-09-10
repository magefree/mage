package mage.cards.e;

import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EerieInterlude extends CardImpl {

    public EerieInterlude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] {CardType.INSTANT}, "{2}{W}");

        // Exile any number of target creatures you control. Return those cards to the
        // battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_CREATURES, false));
    }

    private EerieInterlude(final EerieInterlude card) {
        super(card);
    }

    @Override
    public EerieInterlude copy() {
        return new EerieInterlude(this);
    }
}
