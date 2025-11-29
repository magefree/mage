package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeirloomMirror extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.RITUAL, 3);

    public HeirloomMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{B}",
                "Inherited Fiend",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B"
        );

        // Heirloom Mirror
        // {1}, {T}, Pay 1 life, Discard a card: Draw a card, mill a card, then put a ritual counter on Heirloom Mirror. Then if it has 3 or more ritual counters on it, remove them and transform it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new MillCardsControllerEffect(1).concatBy(","));
        ability.addEffect(new AddCountersSourceEffect(CounterType.RITUAL.createInstance()).concatBy(", then"));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.RITUAL), condition,
                "Then if it has three or more ritual counters on it, remove them and transform it"
        ).addEffect(new TransformSourceEffect()));
        this.getLeftHalfCard().addAbility(ability);

        // Inherited Fiend
        this.getRightHalfCard().setPT(4, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // {2}{B}: Exile target creature card from a graveyard. Put a +1/+1 counter on Inherited Fiend.
        Ability backAbility = new SimpleActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        backAbility.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("."));
        backAbility.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.getRightHalfCard().addAbility(backAbility);
    }

    private HeirloomMirror(final HeirloomMirror card) {
        super(card);
    }

    @Override
    public HeirloomMirror copy() {
        return new HeirloomMirror(this);
    }
}
