
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public final class FoundryStreetDenizen extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another red creature");
    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    
    public FoundryStreetDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another red creature enters the battlefield under your control, Foundry Street Denizen gets +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), filter, false, null, true));
    }

    public FoundryStreetDenizen(final FoundryStreetDenizen card) {
        super(card);
    }

    @Override
    public FoundryStreetDenizen copy() {
        return new FoundryStreetDenizen(this);
    }
}
