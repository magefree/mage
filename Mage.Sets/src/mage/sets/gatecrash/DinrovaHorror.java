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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class DinrovaHorror extends CardImpl<DinrovaHorror> {

    public DinrovaHorror(UUID ownerId) {
        super(ownerId, 155, "Dinrova Horror", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Horror");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Dinrova Horror enters the battlefield, return target permanent to its owner's hand, then that player discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DinrovaHorrorEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    public DinrovaHorror(final DinrovaHorror card) {
        super(card);
    }

    @Override
    public DinrovaHorror copy() {
        return new DinrovaHorror(this);
    }
}

class DinrovaHorrorEffect extends OneShotEffect<DinrovaHorrorEffect> {

    public DinrovaHorrorEffect() {
        super(Outcome.Detriment);
        this.staticText = "return target permanent to its owner's hand, then that player discards a card";
    }

    public DinrovaHorrorEffect(final DinrovaHorrorEffect effect) {
        super(effect);
    }

    @Override
    public DinrovaHorrorEffect copy() {
        return new DinrovaHorrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(target.getControllerId());
        if (target != null && controller != null) {
            target.moveToZone(Constants.Zone.HAND, id, game, true);
            controller.discard(1, source, game);
            return true;
        }
        return false;

    }
}