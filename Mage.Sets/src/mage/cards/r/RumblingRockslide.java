package mage.cards.r;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RumblingRockslide extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT);

    public RumblingRockslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Rumbling Rockslide deals damage to target creature equal to the number of lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage to target creature equal to the number of lands you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RumblingRockslide(final RumblingRockslide card) {
        super(card);
    }

    @Override
    public RumblingRockslide copy() {
        return new RumblingRockslide(this);
    }
}
