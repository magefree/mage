package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.CounterRemovedFromSourceWhileExiledTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WatcherOfHours extends CardImpl {

    public WatcherOfHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new GenericManaCost(3), false));

        // Whenever you remove a time counter from Watcher of Hours while it's exiled, surveil 1.
        this.addAbility(new CounterRemovedFromSourceWhileExiledTriggeredAbility(CounterType.TIME, new SurveilEffect(1, false), false, true));

        // Suspend 6—{1}{U}
        this.addAbility(new SuspendAbility(6, new ManaCostsImpl<>("{1}{U}"), this));
    }

    private WatcherOfHours(final WatcherOfHours card) {
        super(card);
    }

    @Override
    public WatcherOfHours copy() {
        return new WatcherOfHours(this);
    }
}
