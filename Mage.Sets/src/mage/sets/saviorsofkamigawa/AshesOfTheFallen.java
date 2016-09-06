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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class AshesOfTheFallen extends CardImpl {

    public AshesOfTheFallen(UUID ownerId) {
        super(ownerId, 152, "Ashes of the Fallen", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "SOK";

        // As Ashes of the Fallen enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));
        
        // Each creature card in your graveyard has the chosen creature type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshesOfTheFallenEffect()));
    }

    public AshesOfTheFallen(final AshesOfTheFallen card) {
        super(card);
    }

    @Override
    public AshesOfTheFallen copy() {
        return new AshesOfTheFallen(this);
    }
}

class AshesOfTheFallenEffect extends ContinuousEffectImpl {
    
    AshesOfTheFallenEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Each creature card in your graveyard has the chosen creature type in addition to its other types";
    }

    AshesOfTheFallenEffect(final AshesOfTheFallenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getCardType().contains(CardType.CREATURE) && !card.getSubtype(game).contains(subtype)) {
                    game.getState().getCreateCardAttribute(card).getSubtype().add(subtype);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AshesOfTheFallenEffect copy() {
        return new AshesOfTheFallenEffect(this);
    }
}

