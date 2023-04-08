package mage.cards.s;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeerOfStolenSight extends CardImpl {

    public SeerOfStolenSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever one or more artifacts and/or creatures you control are put into a graveyard from the battlefield, surveil 1.
        this.addAbility(new SeerOfStolenSightTriggeredAbility());
    }

    private SeerOfStolenSight(final SeerOfStolenSight card) {
        super(card);
    }

    @Override
    public SeerOfStolenSight copy() {
        return new SeerOfStolenSight(this);
    }
}

class SeerOfStolenSightTriggeredAbility extends TriggeredAbilityImpl {

    SeerOfStolenSightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SurveilEffect(1));
        setTriggerPhrase("Whenever one or more artifacts and/or creatures you control are put into a graveyard from the battlefield, ");
    }

    private SeerOfStolenSightTriggeredAbility(final SeerOfStolenSightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SeerOfStolenSightTriggeredAbility copy() {
        return new SeerOfStolenSightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return zEvent.getToZone().match(Zone.GRAVEYARD)
                && zEvent.getFromZone().match(Zone.BATTLEFIELD)
                && zEvent
                .getCards()
                .stream()
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isArtifact(game) || permanent.isCreature(game))
                .map(Controllable::getControllerId)
                .anyMatch(this::isControlledBy);
    }
}
