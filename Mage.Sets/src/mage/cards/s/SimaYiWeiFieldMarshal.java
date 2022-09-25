
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class SimaYiWeiFieldMarshal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamps you control");

    static{
        filter.add(SubType.SWAMP.getPredicate());
    }

   public SimaYiWeiFieldMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Sima Yi, Wei Field Marshal's power is equal to the number of Swamps you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));                                                         }

    private SimaYiWeiFieldMarshal(final SimaYiWeiFieldMarshal card) {
        super(card);
    }

    @Override
    public SimaYiWeiFieldMarshal copy() {
        return new SimaYiWeiFieldMarshal(this);
    }
}
