package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Epochrasite extends CardImpl {

    public Epochrasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Epochrasite enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                        new InvertCondition(CastFromHandSourcePermanentCondition.instance),
                        "","with three +1/+1 counters on it if you didn't cast it from your hand"),
                new CastFromHandWatcher());

        // When Epochrasite dies, exile it with three time counters on it and it gains suspend.
        this.addAbility(new DiesSourceTriggeredAbility(new ExileSpellWithTimeCountersEffect(3, true)
                .setText("exile it with three time counters on it and it gains suspend." +
                        " <i>(At the beginning of its owner's upkeep, they remove a time counter." +
                        " When the last is removed, they may cast this card without paying its mana cost. It has haste.)</i>")));
    }

    private Epochrasite(final Epochrasite card) {
        super(card);
    }

    @Override
    public Epochrasite copy() {
        return new Epochrasite(this);
    }
}
