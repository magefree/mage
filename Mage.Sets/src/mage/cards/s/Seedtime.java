/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.s;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author ciaccona007
 */
public class Seedtime extends CardImpl {

    public Seedtime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");
        

        // Cast Seedtime only during your turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastOnlyDuringYourTurnEffect()));
        // Take an extra turn after this one if an opponent cast a blue spell this turn.
        this.getSpellAbility().addEffect(new SeedtimeEffect());
    }

    public Seedtime(final Seedtime card) {
        super(card);
    }

    @Override
    public Seedtime copy() {
        return new Seedtime(this);
    }
}

class SeedtimeEffect extends OneShotEffect {

    public SeedtimeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Take an extra turn after this one if an opponent cast a blue spell this turn.";
    }

    public SeedtimeEffect(final SeedtimeEffect effect) {
        super(effect);
    }

    @Override
    public SeedtimeEffect copy() {
        return new SeedtimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean condition = false;
        SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(opponentId);
                if (spells != null) {
                    for (Spell spell : spells) {
                        if (spell.getColor(game).isBlue()) {
                            condition = true;
                        }
                    }
                }
            }
        }
        if(condition && game.getPlayer(source.getControllerId()) != null) {
            return new AddExtraTurnControllerEffect().apply(game, source);
        }
        return false;
    }
}
class CastOnlyDuringYourTurnEffect extends ContinuousRuleModifyingEffectImpl {

    public CastOnlyDuringYourTurnEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "cast {this} only during your turn";
    }

    private CastOnlyDuringYourTurnEffect(final CastOnlyDuringYourTurnEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // has to return true, if the spell cannot be cast in the current phase / step
        UUID activePlayerId = game.getActivePlayerId();
        if (activePlayerId != null && event.getPlayerId().equals(source.getControllerId())) {
            if(source.getControllerId().equals(activePlayerId)) {
                return true;
            }
        }
        return false; // cast not prevented by this effect
    }

    @Override
    public CastOnlyDuringYourTurnEffect copy() {
        return new CastOnlyDuringYourTurnEffect(this);
    }
}
