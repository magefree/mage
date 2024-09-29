package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
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
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public final class PyromancersGauntlet extends CardImpl {

    public PyromancersGauntlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // If a red instant or sorcery spell you control or a red planeswalker you control would deal damage to a permanent or player, it deals that much damage plus 2 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PyromancersGauntletReplacementEffect()));
    }

    private PyromancersGauntlet(final PyromancersGauntlet card) {
        super(card);
    }

    @Override
    public PyromancersGauntlet copy() {
        return new PyromancersGauntlet(this);
    }
}

class PyromancersGauntletReplacementEffect extends ReplacementEffectImpl {

    PyromancersGauntletReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a red instant or sorcery spell you control or a red planeswalker you control would deal damage to a permanent or player, it deals that much damage plus 2 to that permanent or player instead";
    }

    private PyromancersGauntletReplacementEffect(final PyromancersGauntletReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object instanceof Spell) {
            if (((Spell) object).isControlledBy(source.getControllerId())
                    && (object.isInstant(game)
                    || object.isSorcery(game))) {
                return true;
            }
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isPlaneswalker(game)
                && source.isControlledBy(permanent.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }

    @Override
    public PyromancersGauntletReplacementEffect copy() {
        return new PyromancersGauntletReplacementEffect(this);
    }

}
