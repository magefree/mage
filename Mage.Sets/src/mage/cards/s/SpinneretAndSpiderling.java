package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinneretAndSpiderling extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIDER, "Spiders");

    public SpinneretAndSpiderling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you attack with two or more Spiders, put a +1/+1 counter on Spinneret and Spiderling.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), 2, filter
        ));

        // Whenever Spinneret and Spiderling deals 4 or more damage, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new SpinneretAndSpiderlingTriggeredAbility());
    }

    private SpinneretAndSpiderling(final SpinneretAndSpiderling card) {
        super(card);
    }

    @Override
    public SpinneretAndSpiderling copy() {
        return new SpinneretAndSpiderling(this);
    }
}

class SpinneretAndSpiderlingTriggeredAbility extends DealsDamageSourceTriggeredAbility {

    SpinneretAndSpiderlingTriggeredAbility() {
        super(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn));
        setTriggerPhrase("Whenever {this} deals 4 or more damage, ");
    }

    private SpinneretAndSpiderlingTriggeredAbility(final SpinneretAndSpiderlingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpinneretAndSpiderlingTriggeredAbility copy() {
        return new SpinneretAndSpiderlingTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && event.getAmount() >= 4;
    }
}
