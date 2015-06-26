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
package mage.sets.timeshifted;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 *
 * @author Markedagain
 */
public class TeferisMoat extends CardImpl {

    public TeferisMoat(UUID ownerId) {
        super(ownerId, 103, "Teferi's Moat", Rarity.SPECIAL, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");
        this.expansionSetCode = "TSB";
           
        // As Teferi's Moat enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // Creatures of the chosen color without flying can't attack you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TeferisMoatRestrictionEffect()));
    }

    public TeferisMoat(final TeferisMoat card) {
        super(card);
    }

    @Override
    public TeferisMoat copy() {
        return new TeferisMoat(this);
    }
}

class TeferisMoatRestrictionEffect extends RestrictionEffect {

    TeferisMoatRestrictionEffect(){
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures of the chosen color without flying can't attack you";
    }
    
    TeferisMoatRestrictionEffect(final TeferisMoatRestrictionEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        return chosenColor != null &&
                !permanent.getAbilities().contains(FlyingAbility.getInstance()) &&
                permanent.getColor(game).shares(chosenColor) &&
                permanent.getCardType().contains(CardType.CREATURE);
    }
    
    @Override
    public boolean canAttack(UUID defenderId, Ability source, Game game) {
        return !defenderId.equals(source.getControllerId());
    }
    
    @Override
    public TeferisMoatRestrictionEffect copy() {
        return new TeferisMoatRestrictionEffect(this);
    }
}
