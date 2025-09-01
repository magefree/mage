package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarretWallace extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("equipped creatures you control");

    static {
        filter.add(EquippedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Equipped creatures you control", xValue);

    public BarretWallace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Barret Wallace attacks, it deals damage equal to the number of equipped creatures you control to defending player.
        this.addAbility(new AttacksTriggeredAbility(
                new DamageTargetEffect(xValue, true, "it", true)
                        .setText("it deals damage equal to the number of equipped creatures you control to defending player"),
                false, null, SetTargetPointer.PLAYER
        ).withRuleTextReplacement(true).addHint(hint));
    }

    private BarretWallace(final BarretWallace card) {
        super(card);
    }

    @Override
    public BarretWallace copy() {
        return new BarretWallace(this);
    }
}
