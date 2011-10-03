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
package mage.sets.shardsofalara;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public class SedrisTheTraitorKing extends CardImpl<SedrisTheTraitorKing> {

    public SedrisTheTraitorKing(UUID ownerId) {
        super(ownerId, 193, "Sedris, the Traitor King", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");
        this.expansionSetCode = "ALA";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Each creature card in your graveyard has unearth {2}{B}.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SedrisTheTraitorKingEffect()));
    }

    public SedrisTheTraitorKing(final SedrisTheTraitorKing card) {
        super(card);
    }

    @Override
    public SedrisTheTraitorKing copy() {
        return new SedrisTheTraitorKing(this);
    }
}

class SedrisTheTraitorKingEffect extends ContinuousEffectImpl<SedrisTheTraitorKingEffect> {
    SedrisTheTraitorKingEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        staticText = "Each creature card in your graveyard has unearth {2}{B}";
    }

    SedrisTheTraitorKingEffect(final SedrisTheTraitorKingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                    UnearthAbility ability = new UnearthAbility(new ManaCostsImpl("{2}{B}"));
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(cardId, ability);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SedrisTheTraitorKingEffect copy() {
        return new SedrisTheTraitorKingEffect(this);
    }
}
