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
package mage.sets.tenth;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class CrucibleOfWorlds extends CardImpl<CrucibleOfWorlds> {

    public CrucibleOfWorlds(UUID ownerId) {
        super(ownerId, 319, "Crucible of Worlds", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "10E";

        // You may play land cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CrucibleOfWorldsEffect()));
    }

    public CrucibleOfWorlds(final CrucibleOfWorlds card) {
        super(card);
    }

    @Override
    public CrucibleOfWorlds copy() {
        return new CrucibleOfWorlds(this);
    }
}

class CrucibleOfWorldsEffect extends ContinuousEffectImpl<CrucibleOfWorldsEffect> {

    public CrucibleOfWorldsEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "You may play land cards from your graveyard";
    }

    public CrucibleOfWorldsEffect(final CrucibleOfWorldsEffect effect) {
        super(effect);
    }

    @Override
    public CrucibleOfWorldsEffect copy() {
        return new CrucibleOfWorldsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID cardId: player.getGraveyard()) {
                Card card = game.getCard(cardId);
                if(card != null && card.getCardType().contains(CardType.LAND)){
                    PlayLandFromGraveyardAbility ability = new PlayLandFromGraveyardAbility(card.getName());
                    if (ability != null) {
                        ability.setSourceId(cardId);
                        ability.setControllerId(card.getOwnerId());
                        game.getState().addOtherAbility(cardId, ability);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class PlayLandFromGraveyardAbility extends PlayLandAbility{
    PlayLandFromGraveyardAbility(String name){
        super(name);
        zone = Zone.GRAVEYARD;
    }
}