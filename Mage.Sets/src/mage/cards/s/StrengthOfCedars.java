

package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class StrengthOfCedars extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("lands you control");

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public StrengthOfCedars (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}");
        this.subtype.add(SubType.ARCANE);

        // Target creature gets +X/+X until end of turn, where X is the number of lands you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public StrengthOfCedars (final StrengthOfCedars card) {
        super(card);
    }

    @Override
    public StrengthOfCedars copy() {
        return new StrengthOfCedars(this);
    }
}
