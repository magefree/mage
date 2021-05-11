
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LevelX2
 */
public final class DragonscaleGeneral extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures you control");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DragonscaleGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, bolster X, where X is the number of tapped creatures you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new BolsterEffect(new PermanentsOnBattlefieldCount(filter)), TargetController.YOU, false));
    }

    private DragonscaleGeneral(final DragonscaleGeneral card) {
        super(card);
    }

    @Override
    public DragonscaleGeneral copy() {
        return new DragonscaleGeneral(this);
    }
}
