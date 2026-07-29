package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class UltronsAuxiliary extends CardImpl {

    public UltronsAuxiliary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another artifact is put into your graveyard from the battlefield or an artifact card is put into your graveyard from anywhere other than the battlefield, put a +1/+1 counter on this creature.
        this.addAbility(new UltronsAuxiliaryTriggeredAbility());
    }

    private UltronsAuxiliary(final UltronsAuxiliary card) {
        super(card);
    }

    @Override
    public UltronsAuxiliary copy() {
        return new UltronsAuxiliary(this);
    }
}

class UltronsAuxiliaryTriggeredAbility extends TriggeredAbilityImpl {

    UltronsAuxiliaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setLeavesTheBattlefieldTrigger(true);
    }

    private UltronsAuxiliaryTriggeredAbility(final UltronsAuxiliaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UltronsAuxiliaryTriggeredAbility copy() {
        return new UltronsAuxiliaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        // Whenever another artifact enters your graveyard from the battlefield
        if (zEvent.isDiesEvent()
                && zEvent.isPermanentMoved()
                && !zEvent.getTargetId().equals(this.getSourceId())
                && zEvent.getTarget().isArtifact(game)) {
            return true;
        }
        Card card = game.getCard(zEvent.getTargetId());
        // Or an artifact card is put into a graveyard from anywhere other than the battlefield
        if (card == null || !card.isArtifact(game)) {
            return false;
        }
        if (zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() != Zone.BATTLEFIELD) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever another artifact is put into your graveyard from the battlefield " +
            "or an artifact card is put into your graveyard from anywhere other than the battlefield, " +
            "put a +1/+1 counter on this creature.";
    }
}
