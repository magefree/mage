package mage.cards.d;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Defile extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, -1);

    public Defile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -1/-1 until end of turn for each Swamp you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("Target creature gets -1/-1 until end of turn for each Swamp you control."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Defile(final Defile card) {
        super(card);
    }

    @Override
    public Defile copy() {
        return new Defile(this);
    }
}
