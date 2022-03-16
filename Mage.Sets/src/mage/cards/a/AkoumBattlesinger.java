
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class AkoumBattlesinger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("have Ally creatures");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AkoumBattlesinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Akoum Battlesinger or another Ally enters the battlefield under your control, you may have Ally creatures you control get +1/+0 until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter, false), true).setAbilityWord(null));
    }

    private AkoumBattlesinger(final AkoumBattlesinger card) {
        super(card);
    }

    @Override
    public AkoumBattlesinger copy() {
        return new AkoumBattlesinger(this);
    }
}
