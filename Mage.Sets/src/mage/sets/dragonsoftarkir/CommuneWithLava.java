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
package mage.sets.dragonsoftarkir;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class CommuneWithLava extends CardImpl {

    public CommuneWithLava(UUID ownerId) {
        super(ownerId, 131, "Commune with Lava", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");
        this.expansionSetCode = "DTK";

        // Exile the top X cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new CommuneWithLavaEffect());

    }

    public CommuneWithLava(final CommuneWithLava card) {
        super(card);
    }

    @Override
    public CommuneWithLava copy() {
        return new CommuneWithLava(this);
    }
}

class CommuneWithLavaEffect extends OneShotEffect {

    public CommuneWithLavaEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top X cards of your library. Until the end of your next turn, you may play those cards";
    }

    public CommuneWithLavaEffect(final CommuneWithLavaEffect effect) {
        super(effect);
    }

    @Override
    public CommuneWithLavaEffect copy() {
        return new CommuneWithLavaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null) {
            int amount = source.getManaCostsToPay().getX();
            List<Card> cards = controller.getLibrary().getTopCards(game, amount);
            for (Card card : cards) {
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source), sourceCard.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new CommuneWithLavaMayPlayEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }

            return true;
        }
        return false;
    }
}

class CommuneWithLavaMayPlayEffect extends AsThoughEffectImpl {

    int castOnTurn = 0;

    public CommuneWithLavaMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    public CommuneWithLavaMayPlayEffect(final CommuneWithLavaMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public CommuneWithLavaMayPlayEffect copy() {
        return new CommuneWithLavaMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(sourceId)) {
            return game.getState().getZone(sourceId).equals(Zone.EXILED);
        }
        return false;
    }

}
