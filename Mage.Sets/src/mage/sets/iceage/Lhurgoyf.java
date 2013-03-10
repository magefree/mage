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
package mage.sets.iceage;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class Lhurgoyf extends CardImpl<Lhurgoyf> {

    public Lhurgoyf(UUID ownerId) {
        super(ownerId, 140, "Lhurgoyf", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Lhurgoyf");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Lhurgoyf's power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new LhurgoyfEffect()));
    }

    public Lhurgoyf(final Lhurgoyf card) {
        super(card);
    }

    @Override
    public Lhurgoyf copy() {
        return new Lhurgoyf(this);
    }
}



class LhurgoyfEffect extends ContinuousEffectImpl<LhurgoyfEffect> {


    public LhurgoyfEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.SetPT_7b, Constants.Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1";
    }


    public LhurgoyfEffect(final LhurgoyfEffect effect) {
        super(effect);
    }

    @Override
    public LhurgoyfEffect copy() {
        return new LhurgoyfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getSourceId());
        if (target != null) {
            int number = 0;
            
            for(Player player : game.getPlayers().values()){
                if(player != null){
                    number += player.getGraveyard().count(new FilterCreatureCard(), game);
                }
            }
            
            target.getPower().setValue(number);
            target.getToughness().setValue(number + 1);
            return true;
            
        }
        return false;
    }

}