package mage.cards.g;

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
 * @author LevelX2
 */
public final class GroundAssault extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);

    public GroundAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Ground Assault deals damage to target creature equal to the number of lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue).setText("{this} deals damage to target creature equal to the number of lands you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GroundAssault(final GroundAssault card) {
        super(card);
    }

    @Override
    public GroundAssault copy() {
        return new GroundAssault(this);
    }
}
