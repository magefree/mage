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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author LevelX
 */
public class PainwrackerOni extends CardImpl {

    public PainwrackerOni (UUID ownerId) {
        super(ownerId, 136, "Painwracker Oni", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Demon");
        this.subtype.add("Spirit");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Fear (This creature can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(FearAbility.getInstance());
        
        // At the beginning of your upkeep, sacrifice a creature if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PainwrackerOniEffect(new FilterControlledCreaturePermanent(), 1, ""), TargetController.YOU, false));
    }

    public PainwrackerOni (final PainwrackerOni card) {
        super(card);
    }

    @Override
    public PainwrackerOni copy() {
        return new PainwrackerOni(this);
    }

}

class PainwrackerOniEffect extends SacrificeControllerEffect {
    
    public PainwrackerOniEffect(FilterPermanent filter, int count, String preText) {
        super(filter, count, preText);
        this.staticText = "sacrifice a creature if you don't control an Ogre";
    }
    
    public PainwrackerOniEffect(final PainwrackerOniEffect effect) {
        super(effect);
    }
    
    @Override
    public PainwrackerOniEffect copy() {
        return new PainwrackerOniEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().countAll(new FilterCreaturePermanent("Ogre", "Ogre"), source.getControllerId(), game) < 1) {
            return super.apply(game, source);
        }
        return true;
    }
}
