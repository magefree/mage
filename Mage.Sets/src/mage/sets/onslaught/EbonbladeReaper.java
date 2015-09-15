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
package mage.sets.onslaught;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseHalfLifeEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BijanT
 */
public class EbonbladeReaper extends CardImpl {

    public EbonbladeReaper(UUID ownerId) {
        super(ownerId, 141, "Ebonblade Reaper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Whenever Ebonblade Reaper attacks, you lose half your life, rounded up.
        this.addAbility(new AttacksTriggeredAbility(new LoseHalfLifeEffect(), false));

        //Whenever Ebonblade Reaper deals combat damage to a player, that player loses half his or her life, rounded up.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new EbonbladeReaperEffect(), false, true));

        //Morph {3}{B}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{3}{B}{B}")));
    }

    public EbonbladeReaper(final EbonbladeReaper card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new EbonbladeReaper(this);
    }
}

class EbonbladeReaperEffect extends OneShotEffect {

    public EbonbladeReaperEffect() {
        super(Outcome.Damage);
        this.staticText = "that player loses half his or her life, rounded up";
    }

    public EbonbladeReaperEffect(final EbonbladeReaperEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new EbonbladeReaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game);
            }
            return true;
        }
        return false;
    }
}
