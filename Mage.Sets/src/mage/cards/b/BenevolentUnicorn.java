
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author LoneFox, Ketsuban

 */
public final class BenevolentUnicorn extends CardImpl {

    public BenevolentUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.UNICORN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // If a spell would deal damage to a permanent or player, it deals that much damage minus 1 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BenevolentUnicornEffect()));
    }

    private BenevolentUnicorn(final BenevolentUnicorn card) {
        super(card);
    }

    @Override
    public BenevolentUnicorn copy() {
        return new BenevolentUnicorn(this);
    }
}

class BenevolentUnicornEffect extends ReplacementEffectImpl {

    public BenevolentUnicornEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a spell would deal damage to a permanent or player, it deals that much damage minus 1 to that permanent or player instead.";
    }

    private BenevolentUnicornEffect(final BenevolentUnicornEffect effect) {
        super(effect);
    }

    @Override
    public BenevolentUnicornEffect copy() {
        return new BenevolentUnicornEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() - 1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
        }
        return stackObject instanceof Spell;
    }

}
