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
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class UrgorosTheEmptyOne extends CardImpl {

    public UrgorosTheEmptyOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Urgoros, the Empty One deals combat damage to a player, that player discards a card at random. If the player can't, you draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new UrgorosTheEmptyOneEffect(), false, true));
    }

    public UrgorosTheEmptyOne(final UrgorosTheEmptyOne card) {
        super(card);
    }

    @Override
    public UrgorosTheEmptyOne copy() {
        return new UrgorosTheEmptyOne(this);
    }
}

class UrgorosTheEmptyOneEffect extends OneShotEffect {

    public UrgorosTheEmptyOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player discards a card at random. If the player can't, you draw a card";
    }

    public UrgorosTheEmptyOneEffect(final UrgorosTheEmptyOneEffect effect) {
        super(effect);
    }

    @Override
    public UrgorosTheEmptyOneEffect copy() {
        return new UrgorosTheEmptyOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attackedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && attackedPlayer != null) {
            if (attackedPlayer.getHand().isEmpty()) {
                controller.drawCards(1, game);
            } else {
                attackedPlayer.discardOne(true, source, game);
            }
            return true;
        }
        return false;
    }
}
