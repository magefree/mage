package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulcipherBoard extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.OMEN, ComparisonType.EQUAL_TO, 0);

    public SoulcipherBoard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{U}",
                "Cipherbound Spirit",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "U"
        );

        // Soulcipher Board
        // Soulcipher Board enters the battlefield with three omen counters on it.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OMEN.createInstance(3)),
                "with three omen counters on it"
        ));

        // {1}{U}, {T}: Look at the top two cards of your library. Put one of them into your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.GRAVEYARD, PutCards.TOP_ANY),
                new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Whenever a creature card is put into your graveyard from anywhere, remove an omen counter from Soulcipher Board. Then if it has no omen counters on it, transform it.
        Ability ability2 = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.OMEN.createInstance()),
                false, StaticFilters.FILTER_CARD_CREATURE_A, TargetController.YOU
        );
        ability2.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if it has no omen counters on it, transform it"
        ));
        this.getLeftHalfCard().addAbility(ability2);

        // Cipherbound Spirit
        this.getRightHalfCard().setPT(3, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Cipherbound Spirit can block only creatures with flying.
        this.getRightHalfCard().addAbility(new CanBlockOnlyFlyingAbility());

        // {3}{U}: Draw two cards, then discard a card.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(2, 1), new ManaCostsImpl<>("{3}{U}")
        ));
    }

    private SoulcipherBoard(final SoulcipherBoard card) {
        super(card);
    }

    @Override
    public SoulcipherBoard copy() {
        return new SoulcipherBoard(this);
    }
}
