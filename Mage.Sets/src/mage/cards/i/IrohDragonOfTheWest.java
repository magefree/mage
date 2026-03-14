package mage.cards.i;

import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MentorAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class IrohDragonOfTheWest extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public IrohDragonOfTheWest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Mentor
        this.addAbility(new MentorAbility());

        // At the beginning of combat on your turn, each creature you control with a counter on it gains firebending 2 until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainAbilityControlledEffect(
                new FirebendingAbility(2), Duration.EndOfTurn, filter
        ).setText("each creature you control with a counter on it gains firebending 2 until end of turn")));
    }

    private IrohDragonOfTheWest(final IrohDragonOfTheWest card) {
        super(card);
    }

    @Override
    public IrohDragonOfTheWest copy() {
        return new IrohDragonOfTheWest(this);
    }
}
