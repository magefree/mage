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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class DeathforgeShaman extends CardImpl<DeathforgeShaman> {

    public DeathforgeShaman(UUID ownerId) {
        super(ownerId, 80, "Deathforge Shaman", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Ogre");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Multikicker {R}
        this.addAbility(new MultikickerAbility(new ManaCostsImpl("{R}")));
        
        // When Deathforge Shaman enters the battlefield, it deals damage to target player equal to twice the number of times it was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeathforgeShamanEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public DeathforgeShaman(final DeathforgeShaman card) {
        super(card);
    }

    @Override
    public DeathforgeShaman copy() {
        return new DeathforgeShaman(this);
    }
}

class DeathforgeShamanEffect extends OneShotEffect<DeathforgeShamanEffect> {

    public DeathforgeShamanEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage to target player equal to twice the number of times it was kicked";
    }

    public DeathforgeShamanEffect(final DeathforgeShamanEffect effect) {
        super(effect);
    }

    @Override
    public DeathforgeShamanEffect copy() {
        return new DeathforgeShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue value = new MultikickerCount();
        int damage = value.calculate(game, source) * 2;

        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, id, game, false, true);
            return true;
        }
        return false;
    }

}
