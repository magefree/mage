
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class PyromancersSwath extends CardImpl {

    public PyromancersSwath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // If an instant or sorcery source you control would deal damage to a creature or player, it deals that much damage plus 2 to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PyromancersSwathReplacementEffect()));

        // At the beginning of each end step, discard your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new DiscardHandControllerEffect(), TargetController.ANY, null, false));

    }

    private PyromancersSwath(final PyromancersSwath card) {
        super(card);
    }

    @Override
    public PyromancersSwath copy() {
        return new PyromancersSwath(this);
    }
}

class PyromancersSwathReplacementEffect extends ReplacementEffectImpl {

    PyromancersSwathReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an instant or sorcery source you control would deal damage to a permanent or player, it deals that much damage plus 2 to that permanent or player instead";
    }

    private PyromancersSwathReplacementEffect(final PyromancersSwathReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            MageObject object = game.getObject(event.getSourceId());
            return object != null && object.isInstantOrSorcery(game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }

    @Override
    public PyromancersSwathReplacementEffect copy() {
        return new PyromancersSwathReplacementEffect(this);
    }

}
