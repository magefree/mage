
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.LastTimeCounterRemovedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Gal Lerman
 *
 */
public final class Chronozoa extends CardImpl {

    public Chronozoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vanishing 3 (This permanent enters the battlefield with three time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(3)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(3));
        this.addAbility(new VanishingSacrificeAbility());

        // When Chronozoa is put into a graveyard from play, if it had no time counters on it, create two tokens that are copies of it.
        Effect effect = new CreateTokenCopySourceEffect(2);
        effect.setText("create two tokens that are copies of it");
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DiesSourceTriggeredAbility(effect, false),
                LastTimeCounterRemovedCondition.instance,
                "When {this} dies, if it had no time counters on it, create two tokens that are copies of it."));
    }

    private Chronozoa(final Chronozoa card) {
        super(card);
    }

    @Override
    public Chronozoa copy() {
        return new Chronozoa(this);
    }
}
