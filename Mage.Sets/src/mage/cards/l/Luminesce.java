
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Styxo
 */
public final class Luminesce extends CardImpl {

    public Luminesce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Prevent all damage that black sources and red sources would deal this turn.
        getSpellAbility().addEffect(new LuminescePreventionEffect());
    }

    private Luminesce(final Luminesce card) {
        super(card);
    }

    @Override
    public Luminesce copy() {
        return new Luminesce(this);
    }
}

class LuminescePreventionEffect extends PreventionEffectImpl {

    public LuminescePreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "Prevent all damage that black sources and red sources would deal this turn";
    }

    private LuminescePreventionEffect(final LuminescePreventionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                    || event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
                MageObject sourceObject = game.getObject(event.getSourceId());
                if (sourceObject != null
                        && (sourceObject.getColor(game).shares(ObjectColor.BLACK) || sourceObject.getColor(game).shares(ObjectColor.RED))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LuminescePreventionEffect copy() {
        return new LuminescePreventionEffect(this);
    }
}
