
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ChannelerInitiate extends CardImpl {

    public ChannelerInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Channeler Initiate enters the battlefield, put three -1/-1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(3)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {T}, Remove a -1/-1 counter from Channeler Initiate: Add one mana of any color.
        ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance(1)));
        this.addAbility(ability);
    }

    private ChannelerInitiate(final ChannelerInitiate card) {
        super(card);
    }

    @Override
    public ChannelerInitiate copy() {
        return new ChannelerInitiate(this);
    }
}
