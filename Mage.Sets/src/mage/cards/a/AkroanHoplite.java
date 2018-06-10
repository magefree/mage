
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class AkroanHoplite extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AttackingPredicate());
    }

    public AkroanHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Akroan Hoplite attacks, it gets +X/+0 until end of turn, where X is the number of attacking creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new StaticValue(0), Duration.EndOfTurn, true), false));
    }

    public AkroanHoplite(final AkroanHoplite card) {
        super(card);
    }

    @Override
    public AkroanHoplite copy() {
        return new AkroanHoplite(this);
    }
}
