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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class SibilantSpirit extends CardImpl<SibilantSpirit> {

    public SibilantSpirit(UUID ownerId) {
        super(ownerId, 97, "Sibilant Spirit", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Sibilant Spirit attacks, defending player may draw a card.
        this.addAbility(new AttacksTriggeredAbility(new SibilantSpiritEffect(), false));
    }

    public SibilantSpirit(final SibilantSpirit card) {
        super(card);
    }

    @Override
    public SibilantSpirit copy() {
        return new SibilantSpirit(this);
    }
}

class SibilantSpiritEffect extends OneShotEffect<SibilantSpiritEffect> {

    public SibilantSpiritEffect() {
        super(Outcome.DrawCard);
        staticText = "defending player may draw a card";
    }

    public SibilantSpiritEffect(final SibilantSpiritEffect effect) {
        super(effect);
    }

    @Override
    public SibilantSpiritEffect copy() {
        return new SibilantSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        Player defender = game.getPlayer(defenderId);
        if (defender != null) {
            if (defender.chooseUse(outcome, "Draw a card?", game)) {
                defender.drawCards(1, game);
            }
        }
        return false;
    }
}