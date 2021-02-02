package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class TakeHeart extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
    }

    public TakeHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 until end of turn. You gain 1 life for each attacking creature you control.
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn)
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(
                new PermanentsOnBattlefieldCount(filter)
        ).setText("You gain 1 life for each attacking creature you control."));
    }

    private TakeHeart(final TakeHeart card) {
        super(card);
    }

    @Override
    public TakeHeart copy() {
        return new TakeHeart(this);
    }
}
