
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class BoonReflection extends CardImpl {

    public BoonReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W}");

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoonReflectionEffect()));
    }

    public BoonReflection(final BoonReflection card) {
        super(card);
    }

    @Override
    public BoonReflection copy() {
        return new BoonReflection(this);
    }
}

class BoonReflectionEffect extends ReplacementEffectImpl {

    public BoonReflectionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would gain life, you gain twice that much life instead";
    }

    public BoonReflectionEffect(final BoonReflectionEffect effect) {
        super(effect);
    }

    @Override
    public BoonReflectionEffect copy() {
        return new BoonReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId()) && (source.getControllerId() != null);
    }
}
