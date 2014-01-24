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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class SpitefulReturned extends CardImpl<SpitefulReturned> {

    public SpitefulReturned(UUID ownerId) {
        super(ownerId, 84, "Spiteful Returned", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {3}{B}
        this.addAbility(new BestowAbility(this, "{3}{B}"));
        
        // Whenever Spiteful Returned or enchanted creature attacks, defending player loses 2 life.
        Ability ability = new AttacksTriggeredAbility(new SpitefulReturnedEffect(), false);
        this.addAbility(ability);
        
        ability = new AttacksAttachedTriggeredAbility(new SpitefulReturnedEffect(), AttachmentType.AURA, false);
        this.addAbility(ability);
        
        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    public SpitefulReturned(final SpitefulReturned card) {
        super(card);
    }

    @Override
    public SpitefulReturned copy() {
        return new SpitefulReturned(this);
    }
}

class SpitefulReturnedEffect extends OneShotEffect<SpitefulReturnedEffect> {

    public SpitefulReturnedEffect() {
        super(Outcome.LoseLife);
        staticText = "defending player loses 2 life";
    }

    public SpitefulReturnedEffect(final SpitefulReturnedEffect effect) {
        super(effect);
    }

    @Override
    public SpitefulReturnedEffect copy() {
        return new SpitefulReturnedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        Player defender = game.getPlayer(defenderId);
        if (defender != null) {
            defender.loseLife(2, game);
            return true;
        }
        return false;
    }
}
