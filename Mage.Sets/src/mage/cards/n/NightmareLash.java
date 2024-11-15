
package mage.cards.n;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class NightmareLash extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue);
    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp you control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public NightmareLash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each Swamp you control.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(value, value)));
        // Equip-Pay 3 life.
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new PayLifeCost(3)));
        this.getSpellAbility().addHint(hint);
    }

    private NightmareLash(final NightmareLash card) {
        super(card);
    }

    @Override
    public NightmareLash copy() {
        return new NightmareLash(this);
    }
}
