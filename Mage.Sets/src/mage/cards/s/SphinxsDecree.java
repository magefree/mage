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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SphinxsDecree extends CardImpl {

    public SphinxsDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Each opponent can't cast instant or sorcery spells during that player's next turn.
        this.getSpellAbility().addEffect(new SphinxsDecreeEffect());
    }

    public SphinxsDecree(final SphinxsDecree card) {
        super(card);
    }

    @Override
    public SphinxsDecree copy() {
        return new SphinxsDecree(this);
    }
}

class SphinxsDecreeEffect extends OneShotEffect {

    public SphinxsDecreeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent can't cast instant or sorcery spells during that player's next turn";
    }

    public SphinxsDecreeEffect(final SphinxsDecreeEffect effect) {
        super(effect);
    }

    @Override
    public SphinxsDecreeEffect copy() {
        return new SphinxsDecreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            ContinuousEffect effect = new SphinxsDecreeCantCastEffect();
            effect.setTargetPointer(new FixedTarget(opponentId));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class SphinxsDecreeCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    int playersNextTurn;

    public SphinxsDecreeCantCastEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "You can't cast instant or sorcery spells during this turn";
        playersNextTurn = 0;
    }

    public SphinxsDecreeCantCastEffect(final SphinxsDecreeCantCastEffect effect) {
        super(effect);
        this.playersNextTurn = effect.playersNextTurn;
    }

    @Override
    public SphinxsDecreeCantCastEffect copy() {
        return new SphinxsDecreeCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast instant or sorcery spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID opponentId = getTargetPointer().getFirst(game, source);
        if (game.getActivePlayerId().equals(opponentId)) {
            if (playersNextTurn == 0) {
                playersNextTurn = game.getTurnNum();
            }
            if (playersNextTurn == game.getTurnNum()) {
                if (opponentId.equals(event.getPlayerId())) {
                    MageObject object = game.getObject(event.getSourceId());
                    if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                        if (object.isInstant() || object.isSorcery()) {
                            return true;
                        }
                    }
                }
            } else {
                discard();
            }
        } else if (playersNextTurn > 0) {
            discard();
        }
        return false;
    }
}
