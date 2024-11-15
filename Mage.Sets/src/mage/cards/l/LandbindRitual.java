
package mage.cards.l;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class LandbindRitual extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.PLAINS));
    private static final Hint hint = new ValueHint("Plains you control", xValue);

    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains you control");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LandbindRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");


        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)));
        this.getSpellAbility().addHint(hint);
    }

    private LandbindRitual(final LandbindRitual card) {
        super(card);
    }

    @Override
    public LandbindRitual copy() {
        return new LandbindRitual(this);
    }
}
