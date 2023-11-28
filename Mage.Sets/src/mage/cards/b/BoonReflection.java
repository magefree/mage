package mage.cards.b;

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
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BoonReflection extends CardImpl {

    public BoonReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoonReflectionEffect()));
    }

    private BoonReflection(final BoonReflection card) {
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

    private BoonReflectionEffect(final BoonReflectionEffect effect) {
        super(effect);
    }

    @Override
    public BoonReflectionEffect copy() {
        return new BoonReflectionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId()) && (source.getControllerId() != null);
    }
}
