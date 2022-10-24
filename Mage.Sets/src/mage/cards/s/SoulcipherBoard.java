package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulcipherBoard extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.OMEN, 0, 0);

    public SoulcipherBoard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");
        this.secondSideCardClazz = mage.cards.c.CipherboundSpirit.class;

        // Soulcipher Board enters the battlefield with three omen counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OMEN.createInstance(3)),
                "with three omen counters on it"
        ));

        // {1}{U}, {T}: Look at the top two cards of your library. Put one of them into your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.GRAVEYARD, PutCards.TOP_ANY),
                new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever a creature card is put into your graveyard from anywhere, remove an omen counter from Soulcipher Board. Then if it has no omen counters on it, transform it.
        this.addAbility(new TransformAbility());
        ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.OMEN.createInstance()),
                false, StaticFilters.FILTER_CARD_CREATURE_A, TargetController.YOU
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if it has no omen counters on it, transform it"
        ));
        this.addAbility(ability);
    }

    private SoulcipherBoard(final SoulcipherBoard card) {
        super(card);
    }

    @Override
    public SoulcipherBoard copy() {
        return new SoulcipherBoard(this);
    }
}
