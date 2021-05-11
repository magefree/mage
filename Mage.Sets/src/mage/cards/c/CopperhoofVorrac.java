
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**

 *
 * @author LoneFox
 */
public final class CopperhoofVorrac extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("untapped permanent your opponents control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public CopperhoofVorrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Copperhoof Vorrac gets +1/+1 for each untapped permanent your opponents control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));
    }

    private CopperhoofVorrac(final CopperhoofVorrac card) {
        super(card);
    }

    @Override
    public CopperhoofVorrac copy() {
        return new CopperhoofVorrac(this);
    }
}
