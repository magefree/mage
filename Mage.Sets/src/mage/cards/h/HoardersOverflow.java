package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class HoardersOverflow extends CardImpl {

    public HoardersOverflow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");


        // When Hoarder's Overflow enters and whenever you expend 4, put a stash counter on it.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STASH.createInstance()),
                new EntersBattlefieldTriggeredAbility(null),
                new ExpendTriggeredAbility(null, ExpendTriggeredAbility.Expend.FOUR)
        ));

        // {1}{R}, Sacrifice Hoarder's Overflow: Discard your hand, then draw cards equal to the number of stash counters on Hoarder's Overflow.
        Ability ability = new SimpleActivatedAbility(new DiscardHandControllerEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.STASH)).setText(", then draw cards equal to the number of stash counters on {this}"));
        this.addAbility(ability);
    }

    private HoardersOverflow(final HoardersOverflow card) {
        super(card);
    }

    @Override
    public HoardersOverflow copy() {
        return new HoardersOverflow(this);
    }
}
