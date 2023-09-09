
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class ChameleonBlur extends CardImpl {

    public ChameleonBlur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Prevent all damage that creatures would deal to players this turn.
        this.getSpellAbility().addEffect(new ChameleonBlurEffect());
    }

    private ChameleonBlur(final ChameleonBlur card) {
        super(card);
    }

    @Override
    public ChameleonBlur copy() {
        return new ChameleonBlur(this);
    }
}

class ChameleonBlurEffect extends PreventionEffectImpl {

    public ChameleonBlurEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "prevent all damage that creatures would deal to players this turn";
    }

    private ChameleonBlurEffect(final ChameleonBlurEffect effect) {
        super(effect);
    }

    @Override
    public ChameleonBlurEffect copy() {
        return new ChameleonBlurEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)
                && event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getAmount() > 0) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            return permanent != null && permanent.isCreature(game);
        }
        return false;
    }
}
