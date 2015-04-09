
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class OrnateKanzashi extends CardImpl {


    public OrnateKanzashi(UUID ownerId) {
        super(ownerId, 157, "Ornate Kanzashi", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "BOK";

        // {2}, {T}: Target opponent exiles the top card of his or her library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrnateKanzashiEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public OrnateKanzashi(final OrnateKanzashi card) {
        super(card);
    }

    @Override
    public OrnateKanzashi copy() {
        return new OrnateKanzashi(this);
    }

}
class OrnateKanzashiEffect extends OneShotEffect {

    public OrnateKanzashiEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent exiles the top card of his or her library. You may play that card this turn";
    }

    public OrnateKanzashiEffect(final OrnateKanzashiEffect effect) {
        super(effect);
    }

    @Override
    public OrnateKanzashiEffect copy() {
        return new OrnateKanzashiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());        
        if (sourceObject != null && opponent != null) {
            if (opponent.getLibrary().size() > 0) {
                Library library = opponent.getLibrary();
                Card card = library.getFromTop(game);
                if (card != null) {
                    opponent.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getLogName(), source.getSourceId(), game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new OrnateKanzashiCastFromExileEffect(card.getId());
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class OrnateKanzashiCastFromExileEffect extends AsThoughEffectImpl {

    public OrnateKanzashiCastFromExileEffect(UUID cardId) {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play that card from exile this turn";
    }

    public OrnateKanzashiCastFromExileEffect(final OrnateKanzashiCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OrnateKanzashiCastFromExileEffect copy() {
        return new OrnateKanzashiCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {        
        return source.getControllerId().equals(affectedControllerId) && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}