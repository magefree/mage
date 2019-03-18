
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class ChiefOfTheFoundry extends CardImpl {

    private static final FilterCreaturePermanent filterBoosted = new FilterCreaturePermanent("Other artifact creatures you control");

    static {
        filterBoosted.add(new CardTypePredicate(CardType.ARTIFACT));
        filterBoosted.add(new ControllerPredicate(TargetController.YOU));
    }

    public ChiefOfTheFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other artifact creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBoosted, true)));
    }

    public ChiefOfTheFoundry(final ChiefOfTheFoundry card) {
        super(card);
    }

    @Override
    public ChiefOfTheFoundry copy() {
        return new ChiefOfTheFoundry(this);
    }
}
