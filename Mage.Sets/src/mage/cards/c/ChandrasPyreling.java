package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

import java.util.Objects;
import java.util.UUID;

/**
 * @author htrajan
 */
public final class ChandrasPyreling extends CardImpl {

    public ChandrasPyreling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a source you control deals noncombat damage to an opponent, Chandra's Pyreling gets +1/+0 and gains double strike until end of turn.
        this.addAbility(new ChandrasPyrelingAbility());
    }

    private ChandrasPyreling(final ChandrasPyreling card) {
        super(card);
    }

    @Override
    public ChandrasPyreling copy() {
        return new ChandrasPyreling(this);
    }
}

class ChandrasPyrelingAbility extends TriggeredAbilityImpl {

    ChandrasPyrelingAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn));
        addEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
    }

    private ChandrasPyrelingAbility(final ChandrasPyrelingAbility ability) {
        super(ability);
    }

    @Override
    public ChandrasPyrelingAbility copy() {
        return new ChandrasPyrelingAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        return !damageEvent.isCombatDamage()
                && game.getOpponents(controllerId).contains(event.getTargetId())
                && Objects.equals(controllerId, game.getControllerId(event.getSourceId()));
    }

    @Override
    public String getRule() {
        return "Whenever a source you control deals noncombat damage to an opponent, {this} gets +1/+0 and gains double strike until end of turn.";
    }
}