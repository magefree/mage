
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class PrimalForcemage extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    private static final String rule = "Whenever another creature enters the battlefield under your control, that creature gets +3/+3 until end of turn.";

    public PrimalForcemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, that creature gets +3/+3 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(3, 3, Duration.EndOfTurn),
                filter, false, SetTargetPointer.PERMANENT, rule, true));
    }

    public PrimalForcemage(final PrimalForcemage card) {
        super(card);
    }

    @Override
    public PrimalForcemage copy() {
        return new PrimalForcemage(this);
    }
}
