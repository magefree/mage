
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;

/**
 * @author JRHerlehy
 */
public final class GreenwheelLiberator extends CardImpl {

    public GreenwheelLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Revolt</i> &mdash; Greenbelt Liberator enters the battlefield with two +1/+1 counters on it if a
        // permanent you controlled left the battlefield this turn.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false, RevoltCondition.instance,
                "<i>Revolt</i> &mdash; {this} enters the battlefield with two +1/+1 counters on it if a permanent you controlled left the battlefield this turn.", null);
        ability.addWatcher(new RevoltWatcher());
        this.addAbility(ability);
    }

    private GreenwheelLiberator(final GreenwheelLiberator card) {
        super(card);
    }

    @Override
    public GreenwheelLiberator copy() {
        return new GreenwheelLiberator(this);
    }
}
