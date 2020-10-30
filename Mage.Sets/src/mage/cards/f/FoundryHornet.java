
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FoundryHornet extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature with a +1/+1 counter on it");
    private static final FilterCreaturePermanent filterOpponent = new FilterCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
        filterOpponent.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final String rule = "When {this} enters the battlefield, if you control a creature with a +1/+1 counter on it, creatures your opponents control get -1/-1 until end of turn.";

    public FoundryHornet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Foundry Hornet enters the battlefield, if you control a creature with a +1/+1 counter on it, creatures your opponents control get -1/-1 until end of turn.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filterOpponent, false), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(filter), rule));
    }

    public FoundryHornet(final FoundryHornet card) {
        super(card);
    }

    @Override
    public FoundryHornet copy() {
        return new FoundryHornet(this);
    }
}
