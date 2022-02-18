package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeirOfTheAncientFang extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a modified creature");

    public HeirOfTheAncientFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Heir of the Ancient Fang enters the battlefield with a +1/+1 counter on it if you control a modified creature.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), condition,
                null, "with a +1/+1 counter on it if you control a modified creature"
        ).addHint(hint));
    }

    private HeirOfTheAncientFang(final HeirOfTheAncientFang card) {
        super(card);
    }

    @Override
    public HeirOfTheAncientFang copy() {
        return new HeirOfTheAncientFang(this);
    }
}
