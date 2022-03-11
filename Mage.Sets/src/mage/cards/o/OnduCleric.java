
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class OnduCleric extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Allies you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public OnduCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)), true).setAbilityWord(null));
    }

    private OnduCleric(final OnduCleric card) {
        super(card);
    }

    @Override
    public OnduCleric copy() {
        return new OnduCleric(this);
    }
}
