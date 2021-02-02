
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.DeserterToken;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 *
 * @author TheElk801
 */
public final class KjeldoranHomeGuard extends CardImpl {

    public KjeldoranHomeGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // At end of combat, if Kjeldoran Home Guard attacked or blocked this combat, put a -0/-1 counter on Kjeldoran Home Guard and put a 0/1 white Deserter creature token onto the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EndOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.M0M1.createInstance()), false),
                AttackedOrBlockedThisCombatSourceCondition.instance,
                "At end of combat, if {this} attacked or blocked this combat, put a -0/-1 counter on {this} and create a 0/1 white Deserter creature token.");
        ability.addEffect(new CreateTokenEffect(new DeserterToken()).setText("and create a 0/1 white Deserter creature token."));
        this.addAbility(ability, new AttackedOrBlockedThisCombatWatcher());
    }

    private KjeldoranHomeGuard(final KjeldoranHomeGuard card) {
        super(card);
    }

    @Override
    public KjeldoranHomeGuard copy() {
        return new KjeldoranHomeGuard(this);
    }
}
