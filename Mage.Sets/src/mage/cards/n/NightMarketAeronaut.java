

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;

/**
 * @author JRHerlehy
 */
public final class NightMarketAeronaut extends CardImpl {

    public NightMarketAeronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Revolt</i> &mdash; Night Market Aeronaut enters the battlefield with a +1/+1 counter on it if
        // a permanent you controlled left the battlefield this turn.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, RevoltCondition.instance,
                "<i>Revolt</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it if a permanent you controlled left the battlefield this turn", null);
        ability.addWatcher(new RevoltWatcher());
        this.addAbility(ability);
    }

    private NightMarketAeronaut(final NightMarketAeronaut card) {
        super(card);
    }

    @Override
    public NightMarketAeronaut copy() {
        return new NightMarketAeronaut(this);
    }
}
