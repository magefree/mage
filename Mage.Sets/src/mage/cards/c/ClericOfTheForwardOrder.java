
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author fireshoes
 */
public final class ClericOfTheForwardOrder extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control named Cleric of the Forward Order");

    static {
        filter.add(new NamePredicate("Cleric of the Forward Order"));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ClericOfTheForwardOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Cleric of the Forward Order enters the battlefield, you gain 2 life for each creature you control named Cleric of the Forward Order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)), false));
    }

    private ClericOfTheForwardOrder(final ClericOfTheForwardOrder card) {
        super(card);
    }

    @Override
    public ClericOfTheForwardOrder copy() {
        return new ClericOfTheForwardOrder(this);
    }
}
