package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Will
 */
public final class CeaselessSearblades extends CardImpl {

    public CeaselessSearblades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you activate an ability of an Elemental, Ceaseless Searblades gets +1/+0 until end of turn.
        this.addAbility(new CeaselessSearbladesTriggeredAbility());
    }

    private CeaselessSearblades(final CeaselessSearblades card) {
        super(card);
    }

    @Override
    public CeaselessSearblades copy() {
        return new CeaselessSearblades(this);
    }
}

class CeaselessSearbladesTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCard filter = new FilterCard("an Elemental");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    CeaselessSearbladesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), false);
        setTriggerPhrase("Whenever you activate an ability of an Elemental, ");
    }

    CeaselessSearbladesTriggeredAbility(final CeaselessSearbladesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CeaselessSearbladesTriggeredAbility copy() {
        return new CeaselessSearbladesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // can be normal and mana abilities
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY || event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && game.inCheckPlayableState()) {
            // ignore mana abilities on playable checking
            return false;
        }

        Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return event.getPlayerId().equals(getControllerId())
                && source != null
                && filter.match(source, game);
    }
}
