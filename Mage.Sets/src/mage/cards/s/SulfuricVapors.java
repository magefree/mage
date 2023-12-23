

package mage.cards.s;

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
import mage.game.stack.StackObject;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class SulfuricVapors extends CardImpl {

    public SulfuricVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // If a red spell would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SulfuricVaporsEffect()));
    }

    private SulfuricVapors(final SulfuricVapors card) {
        super(card);
    }

    @Override
    public SulfuricVapors copy() {
        return new SulfuricVapors(this);
    }

}

class SulfuricVaporsEffect extends ReplacementEffectImpl {

    public SulfuricVaporsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a red spell would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead";
    }

    private SulfuricVaporsEffect(final SulfuricVaporsEffect effect) {
        super(effect);
    }

    @Override
    public SulfuricVaporsEffect copy() {
        return new SulfuricVaporsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject spell = game.getStack().getStackObject(event.getSourceId());
        return spell != null && spell.getColor(game).isRed();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

}
