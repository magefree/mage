package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieryEmancipation extends CardImpl {

    public FieryEmancipation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}{R}");

        // If a source you control would deal damage to a permanent or player, it deals triple that damage to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new FieryEmancipationEffect()));
    }

    private FieryEmancipation(final FieryEmancipation card) {
        super(card);
    }

    @Override
    public FieryEmancipation copy() {
        return new FieryEmancipation(this);
    }
}

class FieryEmancipationEffect extends ReplacementEffectImpl {

    FieryEmancipationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source you control would deal damage to a permanent or player, " +
                "it deals triple that damage to that permanent or player instead";
    }

    private FieryEmancipationEffect(final FieryEmancipationEffect effect) {
        super(effect);
    }

    @Override
    public FieryEmancipationEffect copy() {
        return new FieryEmancipationEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE)
                || event.getType().equals(GameEvent.EventType.DAMAGE_PLANESWALKER);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.addWithOverflowCheck(
                CardUtil.addWithOverflowCheck(
                        event.getAmount(), event.getAmount()
                ), event.getAmount()
        ));
        return false;
    }
}
