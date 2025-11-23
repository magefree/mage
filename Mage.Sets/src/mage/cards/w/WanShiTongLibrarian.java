package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanShiTongLibrarian extends CardImpl {

    private static final DynamicValue xValue = new HalfValue(GetXValue.instance, false);

    public WanShiTongLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Wan Shi Tong enters, put X +1/+1 counters on him. Then draw half X cards, rounded down.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), GetXValue.instance)
                        .setText("put X +1/+1 counters on him")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(xValue).setText("Then draw half X cards, rounded down"));
        this.addAbility(ability);

        // Whenever an opponent searches their library, put a +1/+1 counter on Wan Shi Tong and draw a card.
        this.addAbility(new WanShiTongLibrarianTriggeredAbility());
    }

    private WanShiTongLibrarian(final WanShiTongLibrarian card) {
        super(card);
    }

    @Override
    public WanShiTongLibrarian copy() {
        return new WanShiTongLibrarian(this);
    }
}

class WanShiTongLibrarianTriggeredAbility extends TriggeredAbilityImpl {

    WanShiTongLibrarianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.setTriggerPhrase("Whenever an opponent searches their library, ");
        this.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
    }

    private WanShiTongLibrarianTriggeredAbility(final WanShiTongLibrarianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WanShiTongLibrarianTriggeredAbility copy() {
        return new WanShiTongLibrarianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SEARCHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getPlayerId());
    }
}
