package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGreatHenge extends CardImpl {

    public TheGreatHenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // This spell costs {X} less to cast, where X is the greatest power among creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(GreatestPowerAmongControlledCreaturesValue.instance)
        ).setRuleAtTheTop(true).addHint(GreatestPowerAmongControlledCreaturesValue.getHint()));

        // {T}: Add {G}{G}. You gain 2 life.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost());
        ability.addEffect(new GainLifeEffect(2).setText("You gain 2 life."));
        this.addAbility(ability);

        // Whenever a nontoken creature enters the battlefield under your control, put a +1/+1 counter on it and draw a card.
        ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CREATURE_NON_TOKEN, false, SetTargetPointer.PERMANENT, "Whenever a nontoken creature " +
                "enters the battlefield under your control, put a +1/+1 counter on it and draw a card."
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private TheGreatHenge(final TheGreatHenge card) {
        super(card);
    }

    @Override
    public TheGreatHenge copy() {
        return new TheGreatHenge(this);
    }
}