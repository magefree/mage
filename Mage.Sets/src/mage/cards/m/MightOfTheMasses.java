package mage.cards.m;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class MightOfTheMasses extends CardImpl {

    public MightOfTheMasses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +1/+1 until end of turn for each creature you control.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(value, value, Duration.EndOfTurn, true));
    }

    private MightOfTheMasses(final MightOfTheMasses card) {
        super(card);
    }

    @Override
    public MightOfTheMasses copy() {
        return new MightOfTheMasses(this);
    }
}
