
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class SagesRowDenizen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SagesRowDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another blue creature enters the battlefield under your control, target player puts the top two cards of their library into their graveyard.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(2), filter, false, null, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public SagesRowDenizen(final SagesRowDenizen card) {
        super(card);
    }

    @Override
    public SagesRowDenizen copy() {
        return new SagesRowDenizen(this);
    }
}
