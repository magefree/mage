
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WoollyRazorback extends CardImpl {

    public WoollyRazorback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Woolly Razorback enters the battlefield with three ice counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.ICE.createInstance(3)),
                "with three ice counters on it"));
        // As long as Woolly Razorback has an ice counter on it, prevent all combat damage it would deal and it has defender.
        ConditionalReplacementEffect effect = new ConditionalReplacementEffect(new PreventCombatDamageBySourceEffect(Duration.WhileOnBattlefield),
                new SourceHasCounterCondition(CounterType.ICE));
        effect.setText("as long as {this} has an ice counter on it, prevent all combat damage it would deal");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(DefenderAbility.getInstance()),
                new SourceHasCounterCondition(CounterType.ICE), "and it has defender"));
        this.addAbility(ability);
        // Whenever Woolly Razorback blocks, remove an ice counter from it.
        this.addAbility(new BlocksSourceTriggeredAbility(new RemoveCounterSourceEffect(CounterType.ICE.createInstance()).setText("remove an ice counter from it"), false));
    }

    private WoollyRazorback(final WoollyRazorback card) {
        super(card);
    }

    @Override
    public WoollyRazorback copy() {
        return new WoollyRazorback(this);
    }
}
