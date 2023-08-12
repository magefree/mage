package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HamaPasharRuinSeeker extends CardImpl {

    public HamaPasharRuinSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Room abilities of dungeons you own trigger an additional time.
        this.addAbility(new SimpleStaticAbility(new HamaPasharRuinSeekerEffect()));
    }

    private HamaPasharRuinSeeker(final HamaPasharRuinSeeker card) {
        super(card);
    }

    @Override
    public HamaPasharRuinSeeker copy() {
        return new HamaPasharRuinSeeker(this);
    }
}

class HamaPasharRuinSeekerEffect extends ReplacementEffectImpl {

    HamaPasharRuinSeekerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "room abilities of dungeons you own trigger an additional time";
    }

    private HamaPasharRuinSeekerEffect(final HamaPasharRuinSeekerEffect effect) {
        super(effect);
    }

    @Override
    public HamaPasharRuinSeekerEffect copy() {
        return new HamaPasharRuinSeekerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        GameEvent gameEvent = ((NumberOfTriggersEvent) event).getSourceEvent();
        return gameEvent != null
                && gameEvent.getType() == GameEvent.EventType.ROOM_ENTERED
                && source.isControlledBy(gameEvent.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
