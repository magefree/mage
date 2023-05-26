package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class LashknifeBarrier extends CardImpl {

    public LashknifeBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Lashknife Barrier enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
        // If a source would deal damage to a creature you control, it deals that much damage minus 1 to that creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LashknifeBarrierEffect()));
    }

    private LashknifeBarrier(final LashknifeBarrier card) {
        super(card);
    }

    @Override
    public LashknifeBarrier copy() {
        return new LashknifeBarrier(this);
    }
}

class LashknifeBarrierEffect extends ReplacementEffectImpl {

    public LashknifeBarrierEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a source would deal damage to a creature you control, it deals that much damage minus 1 to that creature instead.";
    }

    public LashknifeBarrierEffect(final LashknifeBarrierEffect effect) {
        super(effect);
    }

    @Override
    public LashknifeBarrierEffect copy() {
        return new LashknifeBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() - 1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(source.getControllerId());
    }

}
