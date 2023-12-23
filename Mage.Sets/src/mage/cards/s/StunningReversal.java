
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class StunningReversal extends CardImpl {

    public StunningReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // The next time you would lose the game this turn, instead draw seven cards and your life total becomes 1.
        this.getSpellAbility().addEffect(new StunningReversalEffect());

        // Exile Stunning Reversal.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private StunningReversal(final StunningReversal card) {
        super(card);
    }

    @Override
    public StunningReversal copy() {
        return new StunningReversal(this);
    }
}

class StunningReversalEffect extends ReplacementEffectImpl {

    public StunningReversalEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next time you would lose the game this turn, instead draw seven cards and your life total becomes 1";
    }

    private StunningReversalEffect(final StunningReversalEffect effect) {
        super(effect);
    }

    @Override
    public StunningReversalEffect copy() {
        return new StunningReversalEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.drawCards(7, source, game); // original event is not a draw event, so skip it in params
            player.setLife(1, game, source);
            this.discard();
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

}
