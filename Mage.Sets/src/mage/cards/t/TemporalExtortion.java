
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TemporalExtortion extends CardImpl {

    public TemporalExtortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}{B}{B}");

        // When you cast Temporal Extortion, any player may pay half their life, rounded up. If a player does, counter Temporal Extortion.
        this.addAbility(new CastSourceTriggeredAbility(new TemporalExtortionCounterSourceEffect()));

        // Take an extra turn after this one.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
    }

    public TemporalExtortion(final TemporalExtortion card) {
        super(card);
    }

    @Override
    public TemporalExtortion copy() {
        return new TemporalExtortion(this);
    }
}

class TemporalExtortionCounterSourceEffect extends OneShotEffect {

    public TemporalExtortionCounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "any player may pay half their life, rounded up. If a player does, counter {source}";
    }

    public TemporalExtortionCounterSourceEffect(final TemporalExtortionCounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public TemporalExtortionCounterSourceEffect copy() {
        return new TemporalExtortionCounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player.chooseUse(outcome, "Pay half your life, rounded up to counter " + sourceObject.getIdName() + '?', source, game)) {
                    Integer amount = (int) Math.ceil(player.getLife() / 2f);
                    player.loseLife(amount, game, false);
                    game.informPlayers(player.getLogName() + " pays half their life, rounded up to counter " + sourceObject.getIdName() + '.');
                    Spell spell = game.getStack().getSpell(source.getSourceId());
                    if (spell != null) {
                        game.getStack().counter(spell.getId(), source.getSourceId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
