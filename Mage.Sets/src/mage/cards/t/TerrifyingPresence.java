
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TerrifyingPresence extends CardImpl {

    public TerrifyingPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Prevent all combat damage that would be dealt by creatures other than target creature this turn.
        this.getSpellAbility().addEffect(new TerrifyingPresenceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TerrifyingPresence(final TerrifyingPresence card) {
        super(card);
    }

    @Override
    public TerrifyingPresence copy() {
        return new TerrifyingPresence(this);
    }
}

class TerrifyingPresenceEffect extends PreventionEffectImpl {

    public TerrifyingPresenceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        this.staticText = "Prevent all combat damage that would be dealt by creatures other than target creature this turn";
    }

    private TerrifyingPresenceEffect(final TerrifyingPresenceEffect effect) {
        super(effect);
    }

    @Override
    public TerrifyingPresenceEffect copy() {
        return new TerrifyingPresenceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && !event.getSourceId().equals(getTargetPointer().getFirst(game, source));
    }
}
