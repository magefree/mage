
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class AviaryMechanic extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AviaryMechanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Aviary Mechanic enters the battlefield, you may return another permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), true));
    }

    private AviaryMechanic(final AviaryMechanic card) {
        super(card);
    }

    @Override
    public AviaryMechanic copy() {
        return new AviaryMechanic(this);
    }
}
