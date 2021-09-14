package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KylerSigardianEmissary extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.HUMAN, "another Human");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.HUMAN, "");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
    }

    public KylerSigardianEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Kyler, Sigardian Emissary.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));

        // Other Humans you control get +1/+1 for each counter on Kyler, Sigardian Emissary.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                KylerSigardianEmissaryValue.instance, KylerSigardianEmissaryValue.instance,
                Duration.WhileOnBattlefield, filter2, true,
                "Other Humans you control get +1/+1 for each counter on {this}"
        )).addHint(KylerSigardianEmissaryHint.instance));
    }

    private KylerSigardianEmissary(final KylerSigardianEmissary card) {
        super(card);
    }

    @Override
    public KylerSigardianEmissary copy() {
        return new KylerSigardianEmissary(this);
    }
}

enum KylerSigardianEmissaryValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentIfItStillExists(game);
        return permanent != null
                ? permanent
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .sum() : 0;
    }

    @Override
    public KylerSigardianEmissaryValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum KylerSigardianEmissaryHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Permanent permanent = ability.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return null;
        }
        return "Counters on " + permanent.getName() + ": "
                + permanent
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .sum();
    }

    @Override
    public KylerSigardianEmissaryHint copy() {
        return this;
    }
}
