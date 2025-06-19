package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyarasOathsworn extends CardImpl {

    private static final Condition condition1 = new SourceHasCounterCondition(CounterType.P1P1, ComparisonType.FEWER_THAN, 4);
    private static final Condition condition2 = new SourceHasCounterCondition(CounterType.P1P1, ComparisonType.EQUAL_TO, 4);

    public AyarasOathsworn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Ayara's Oathsworn deals combat damage to a player, if it has fewer than four +1/+1 counters on it, put a +1/+1 counter on it. Then if it has exactly four +1/+1 counters on it, search your library for a card, put it into your hand, then shuffle.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"), false
        ).withInterveningIf(condition1).withRuleTextReplacement(true);
        ability.addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false),
                condition2, "Then if it has exactly four +1/+1 counters on it, " +
                "search your library for a card, put it into your hand, then shuffle"
        ));
        this.addAbility(ability);
    }

    private AyarasOathsworn(final AyarasOathsworn card) {
        super(card);
    }

    @Override
    public AyarasOathsworn copy() {
        return new AyarasOathsworn(this);
    }
}
