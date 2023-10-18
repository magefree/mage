package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 *
 * @author KholdFuzion
 */
public final class Castle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creatures");
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public Castle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        
        // Untapped creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(0, 2, Duration.WhileOnBattlefield, filter)));
    }

    private Castle(final Castle card) {
        super(card);
    }

    @Override
    public Castle copy() {
        return new Castle(this);
    }
}
