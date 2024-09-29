
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author fireshoes
 */
public final class WalkerOfTheWastes extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("land you control named Wastes");

    static {
        filter.add(new NamePredicate("Wastes"));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public WalkerOfTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{C}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Walker of the Wastes gets +1/+1 for each land you control named Wastes.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(
                new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));
    }

    private WalkerOfTheWastes(final WalkerOfTheWastes card) {
        super(card);
    }

    @Override
    public WalkerOfTheWastes copy() {
        return new WalkerOfTheWastes(this);
    }
}
