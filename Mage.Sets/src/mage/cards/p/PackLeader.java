package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PackLeader extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DOG, "Dogs");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PackLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Dogs you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Pack Leader attacks, prevent all combat damage that would be dealt this turn to Dogs you control.
        this.addAbility(new AttacksTriggeredAbility(new PreventAllDamageToAllEffect(
                Duration.EndOfTurn, filter, true
        ).setText("prevent all combat damage that would be dealt this turn to Dogs you control"), false));
    }

    private PackLeader(final PackLeader card) {
        super(card);
    }

    @Override
    public PackLeader copy() {
        return new PackLeader(this);
    }
}
