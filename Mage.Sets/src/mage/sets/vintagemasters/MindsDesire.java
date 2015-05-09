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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class MindsDesire extends CardImpl {

    public MindsDesire(UUID ownerId) {
        super(ownerId, 80, "Mind's Desire", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");
        this.expansionSetCode = "VMA";

        this.color.setBlue(true);

        // Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.
        this.getSpellAbility().addEffect(new MindsDesireEffect());
        
        // Storm
        this.addAbility(new StormAbility());
    }

    public MindsDesire(final MindsDesire card) {
        super(card);
    }

    @Override
    public MindsDesire copy() {
        return new MindsDesire(this);
    }
}

class MindsDesireEffect extends OneShotEffect {
    
    MindsDesireEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.";
    }
    
    MindsDesireEffect(final MindsDesireEffect effect) {
        super(effect);
    }
    
    @Override
    public MindsDesireEffect copy() {
        return new MindsDesireEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.shuffleLibrary(game);
            if (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    player.moveCardToExileWithInfo(card, source.getSourceId(), "Mind's Desire", source.getSourceId(), game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new MindsDesireCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class MindsDesireCastFromExileEffect extends AsThoughEffectImpl {

    MindsDesireCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may play that card without paying its mana cost";
    }

    MindsDesireCastFromExileEffect(final MindsDesireCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MindsDesireCastFromExileEffect copy() {
        return new MindsDesireCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null && targetId.equals(sourceId)) {
            if (affectedControllerId.equals(source.getControllerId())) {
                Card card = game.getCard(sourceId);
                if (card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
                    Player player = game.getPlayer(affectedControllerId);
                    player.setCastSourceIdWithAlternateMana(sourceId, null);
                    return true;
                }
            }
        }
        return false;
    }
}