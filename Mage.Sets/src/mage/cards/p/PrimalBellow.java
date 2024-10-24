
package mage.cards.p;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class PrimalBellow extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());

    }

    public PrimalBellow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(value, value, Duration.EndOfTurn));
        this.getSpellAbility().addHint(hint);
    }

    private PrimalBellow(final PrimalBellow card) {
        super(card);
    }

    @Override
    public PrimalBellow copy() {
        return new PrimalBellow(this);
    }
}
