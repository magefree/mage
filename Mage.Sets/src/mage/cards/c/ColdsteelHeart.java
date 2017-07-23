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
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class ColdsteelHeart extends CardImpl {

    public ColdsteelHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.addSuperType(SuperType.SNOW);

        // Coldsteel Heart enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // As Coldsteel Heart enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral), null, "As {this} enters the battlefield, choose a color.", null));
        // {T}: Add one mana of the chosen color to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new ColdsteelHeartManaEffect(), new TapSourceCost()));
    }

    public ColdsteelHeart(final ColdsteelHeart card) {
        super(card);
    }

    @Override
    public ColdsteelHeart copy() {
        return new ColdsteelHeart(this);
    }
}

class ColdsteelHeartManaEffect extends ManaEffect {

    public ColdsteelHeartManaEffect() {
        super();
        staticText = "Add one mana of the chosen color to your mana pool";
    }

    public ColdsteelHeartManaEffect(final ColdsteelHeartManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(getMana(game, source), game, source);
        }
        return true;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
        } else {
            return null;
        }
    }

    @Override
    public ColdsteelHeartManaEffect copy() {
        return new ColdsteelHeartManaEffect(this);
    }
}
