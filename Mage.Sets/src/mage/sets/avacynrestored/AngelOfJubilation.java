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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public class AngelOfJubilation extends CardImpl<AngelOfJubilation> {

    private final static FilterCreaturePermanent filterNonBlack = new FilterCreaturePermanent("nonblack creatures");
    
    static {
        filterNonBlack.setUseColor(true);
        filterNonBlack.setColor(ObjectColor.BLACK);
        filterNonBlack.setNotColor(true);
    }

    public AngelOfJubilation(UUID ownerId) {
        super(ownerId, 2, "Angel of Jubilation", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{W}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other nonblack creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filterNonBlack, true)));

        // Players can't pay life or sacrifice creatures to cast spells or activate abilities.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new AngelOfJubilationEffect(Constants.Duration.WhileOnBattlefield)));
    }

    public AngelOfJubilation(final AngelOfJubilation card) {
        super(card);
    }

    @Override
    public AngelOfJubilation copy() {
        return new AngelOfJubilation(this);
    }
}

class AngelOfJubilationEffect extends ContinuousEffectImpl<AngelOfJubilationEffect> {

    public AngelOfJubilationEffect(Constants.Duration duration) {
        super(duration, Constants.Layer.PlayerEffects, Constants.SubLayer.NA, Constants.Outcome.Detriment);
        staticText = "Players can't pay life or sacrifice creatures to cast spells or activate abilities";
    }

    public AngelOfJubilationEffect(final AngelOfJubilationEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfJubilationEffect copy() {
        return new AngelOfJubilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : game.getPlayers().values()) {
            player.setCanPayLifeCost(false);
            player.setCanPaySacrificeCost(false);
        }
        return true;
    }

}
