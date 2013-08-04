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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class MagisterSphinx extends CardImpl<MagisterSphinx> {

    public MagisterSphinx(UUID ownerId) {
        super(ownerId, 116, "Magister Sphinx", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{U}{B}");
        this.expansionSetCode = "CON";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Magister Sphinx enters the battlefield, target player's life total becomes 10.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MagisterSphinxEffect(), false);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        
    }

    public MagisterSphinx(final MagisterSphinx card) {
        super(card);
    }

    @Override
    public MagisterSphinx copy() {
        return new MagisterSphinx(this);
    }
}

class MagisterSphinxEffect extends OneShotEffect<MagisterSphinxEffect> {

    MagisterSphinxEffect() {
        super(Outcome.Detriment);
        staticText = "target player's life total becomes 10";
    }

    MagisterSphinxEffect(final MagisterSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.setLife(10, game);
            return true;
        }
        return false;
    }

    @Override
    public MagisterSphinxEffect copy() {
        return new MagisterSphinxEffect(this);
    }

}