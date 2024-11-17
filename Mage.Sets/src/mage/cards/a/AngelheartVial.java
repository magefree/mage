package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.YoureDealtDamageTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AngelheartVial extends CardImpl {

    public AngelheartVial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever you're dealt damage, you may put that many charge counters on Angelheart Vial.
        this.addAbility(new YoureDealtDamageTriggeredAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(), SavedDamageValue.MANY), true));

        // {2}, {tap}, Remove four charge counters from Angelheart Vial: You gain 2 life and draw a card.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(2), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(4)));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private AngelheartVial(final AngelheartVial card) {
        super(card);
    }

    @Override
    public AngelheartVial copy() {
        return new AngelheartVial(this);
    }
}
