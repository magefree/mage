package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class UnstoppableSlasher extends CardImpl {

    public UnstoppableSlasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Unstoppable Slasher deals combat damage to a player, they lose half their life, rounded up.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseHalfLifeTargetEffect()
                .setText("they lose half their life, rounded up"), false, true));

        // When Unstoppable Slasher dies, if it had no counters on it, return it to the battlefield tapped under its owner's control with two stun counters on it.
        this.addAbility(new DiesSourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                        CounterType.STUN.createInstance(2),
                        true, true, false, false
                ).setText("return it to the battlefield tapped under its owner's control with two stun counters on it")
        ).withInterveningIf(UnstoppableSlasherCondition.instance));
    }

    private UnstoppableSlasher(final UnstoppableSlasher card) {
        super(card);
    }

    @Override
    public UnstoppableSlasher copy() {
        return new UnstoppableSlasher(this);
    }
}

enum UnstoppableSlasherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null
                && permanent
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .sum() == 0;
    }

    @Override
    public String toString() {
        return "it had no counters on it";
    }
}
