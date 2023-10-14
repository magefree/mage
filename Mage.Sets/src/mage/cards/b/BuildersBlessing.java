package mage.cards.b;

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
 * @author noxx
 */
public final class BuildersBlessing extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creatures");
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BuildersBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // Untapped creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(0, 2, Duration.WhileOnBattlefield, filter)));
    }

    private BuildersBlessing(final BuildersBlessing card) {
        super(card);
    }

    @Override
    public BuildersBlessing copy() {
        return new BuildersBlessing(this);
    }
}
