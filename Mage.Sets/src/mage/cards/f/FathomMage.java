package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Plopman
 */
public final class FathomMage extends CardImpl {

    public FathomMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature
        // has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());

        //Whenever a +1/+1 counter is put on Fathom Mage, you may draw a card.
        this.addAbility(new FathomMageTriggeredAbility());
    }

    private FathomMage(final FathomMage card) {
        super(card);
    }

    @Override
    public FathomMage copy() {
        return new FathomMage(this);
    }
}

class FathomMageTriggeredAbility extends TriggeredAbilityImpl {

    public FathomMageTriggeredAbility() {
        super(Zone.ALL, new DrawCardSourceControllerEffect(1), true);
        setTriggerPhrase("Whenever a +1/+1 counter is put on {this}, ");
    }

    public FathomMageTriggeredAbility(FathomMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.P1P1.getName());
    }

    @Override
    public FathomMageTriggeredAbility copy() {
        return new FathomMageTriggeredAbility(this);
    }
}
