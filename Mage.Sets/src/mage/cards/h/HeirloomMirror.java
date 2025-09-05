package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeirloomMirror extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.RITUAL, 3);

    public HeirloomMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.secondSideCardClazz = mage.cards.i.InheritedFiend.class;

        // {1}, {T}, Pay 1 life, Discard a card: Draw a card, mill a card, then put a ritual counter on Heirloom Mirror. Then if it has 3 or more ritual counters on it, remove them and transform it. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new MillCardsControllerEffect(1).concatBy(","));
        ability.addEffect(new AddCountersSourceEffect(CounterType.RITUAL.createInstance()).concatBy(", then"));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.RITUAL), condition,
                "Then if it has 3 or more ritual counters on it, remove them and transform it"
        ).addEffect(new TransformSourceEffect()));
        this.addAbility(ability);
    }

    private HeirloomMirror(final HeirloomMirror card) {
        super(card);
    }

    @Override
    public HeirloomMirror copy() {
        return new HeirloomMirror(this);
    }
}
