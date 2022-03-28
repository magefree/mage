
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author magenoxx_at_gmail.com
 */
public final class Worship extends CardImpl {

    public Worship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WorshipReplacementEffect()));
    }

    private Worship(final Worship card) {
        super(card);
    }

    @Override
    public Worship copy() {
        return new Worship(this);
    }
}

class WorshipReplacementEffect extends ReplacementEffectImpl {

    public WorshipReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public WorshipReplacementEffect(final WorshipReplacementEffect effect) {
        super(effect);
    }

    @Override
    public WorshipReplacementEffect copy() {
        return new WorshipReplacementEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(event.getPlayerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() - event.getAmount()) < 1
                    && game.getBattlefield().count(new FilterControlledCreaturePermanent(), event.getPlayerId(), source, game) > 0
                    ) {
                event.setAmount(controller.getLife() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }
}
