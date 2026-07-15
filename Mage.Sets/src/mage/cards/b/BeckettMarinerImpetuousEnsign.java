package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BeckettMarinerImpetuousEnsign extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.PROMOTION, 3);

    public BeckettMarinerImpetuousEnsign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reckless Behavior -- Whenever Beckett Mariner becomes tapped, put a promotion counter on her. Then if there are three or more promotion counters on her, remove those counters and she deals 2 damage to each opponent.
        Ability ability = new BecomesTappedSourceTriggeredAbility(
            new AddCountersSourceEffect(CounterType.PROMOTION.createInstance()).setText("put a promotion counter on her")
        );
        ability.addEffect(new ConditionalOneShotEffect(
            new RemoveAllCountersSourceEffect(CounterType.PROMOTION), condition,
            "Then if there are three or more promotion counters on her, remove those counters and she deals 2 damage to each opponent"
        ).addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT)));
        this.addAbility(ability.withFlavorWord("Reckless Behavior"));
    }

    private BeckettMarinerImpetuousEnsign(final BeckettMarinerImpetuousEnsign card) {
        super(card);
    }

    @Override
    public BeckettMarinerImpetuousEnsign copy() {
        return new BeckettMarinerImpetuousEnsign(this);
    }
}
