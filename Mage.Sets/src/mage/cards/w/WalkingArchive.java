
package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class WalkingArchive extends CardImpl {

    public WalkingArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Walking Archive enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance()));

        // At the beginning of each player's upkeep, that player draws a card for each +1/+1 counter on Walking Archive.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER, new DrawCardTargetEffect(new CountersSourceCount(CounterType.P1P1)), false));

        // {2}{W}{U}: Put a +1/+1 counter on Walking Archive.
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}{W}{U}")));
    }

    private WalkingArchive(final WalkingArchive card) {
        super(card);
    }

    @Override
    public WalkingArchive copy() {
        return new WalkingArchive(this);
    }
}
