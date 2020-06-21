package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelPlumeMarshal extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("attacking creatures you control with flying");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SteelPlumeMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Steel-Plume Marshal attacks, other attacking creatures you control with flying get +2/+2 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(
                2, 2, Duration.EndOfTurn, filter, true
        ), false));
    }

    private SteelPlumeMarshal(final SteelPlumeMarshal card) {
        super(card);
    }

    @Override
    public SteelPlumeMarshal copy() {
        return new SteelPlumeMarshal(this);
    }
}
