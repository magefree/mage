package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author muz
 */
public final class HulklingBurgeoningBruiser extends CardImpl {

    public HulklingBurgeoningBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.SKRULL);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever another creature you control enters, if it has greater power or toughness than Hulkling, put a +1/+1 counter on Hulkling.
        this.addAbility(new HulklingBurgeoningBruiserTriggeredAbility());
    }

    private HulklingBurgeoningBruiser(final HulklingBurgeoningBruiser card) {
        super(card);
    }

    @Override
    public HulklingBurgeoningBruiser copy() {
        return new HulklingBurgeoningBruiser(this);
    }
}

class HulklingBurgeoningBruiserTriggeredAbility extends TriggeredAbilityImpl {

    HulklingBurgeoningBruiserTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setTriggerPhrase("Whenever another creature you control enters, " +
            "if it has greater power or toughness than {this}, ");
    }

    private HulklingBurgeoningBruiserTriggeredAbility(final HulklingBurgeoningBruiserTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HulklingBurgeoningBruiserTriggeredAbility copy() {
        return new HulklingBurgeoningBruiserTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enteringCreature = game.getPermanent(event.getTargetId());
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (enteringCreature == null
                || permanent == null
                || !StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL.match(enteringCreature, getControllerId(), this, game)) {
            return false;
        }

        return !(enteringCreature.getPower().getValue() <= permanent.getPower().getValue() &&
                enteringCreature.getToughness().getValue() <= permanent.getToughness().getValue());
    }
}
