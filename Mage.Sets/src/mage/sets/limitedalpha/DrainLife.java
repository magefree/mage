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
package mage.sets.limitedalpha;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author KholdFuzion

 */
public class DrainLife extends CardImpl<DrainLife> {

    public static final FilterMana filterBlack = new FilterMana();

    static {
        filterBlack.setBlack(true);
    }


    public DrainLife(UUID ownerId) {
        super(ownerId, 14, "Drain Life", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");
        this.expansionSetCode = "LEA";

        this.color.setBlack(true);

        // Spend only black mana on X.
        // Drain Life deals X damage to target creature or player. You gain life equal to the damage dealt, but not more life than the player's life total before Drain Life dealt damage or the creature's toughness.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        this.getSpellAbility().addEffect(new DrainLifeEffect());
        this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0).setFilter(filterBlack);
    }

    public DrainLife(final DrainLife card) {
        super(card);
    }

    @Override
    public DrainLife copy() {
        return new DrainLife(this);
    }
}

class DrainLifeEffect extends OneShotEffect<DrainLifeEffect> {

    public DrainLifeEffect() {
        super(Outcome.Damage);
        staticText = "Drain Life deals X damage to target creature or player. You gain life equal to the damage dealt, but not more life than the player's life total before Drain Life dealt damage or the creature's toughness.";
    }

    public DrainLifeEffect(final DrainLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        int lifetogain = amount;
        if (amount > 0) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null ) {
                if (permanent.getToughness().getValue() < amount) {
                    lifetogain = permanent.getToughness().getValue();
                }
                permanent.damage(amount, source.getSourceId(), game, true, false);
            } else {
                Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (player != null) {
                    if (player.getLife() < amount) {
                        lifetogain = player.getLife();
                    }
                    player.damage(amount, source.getSourceId(), game, false, true);
                } else {
                    return false;
                }
            }
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(lifetogain, game);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public DrainLifeEffect copy() {
        return new DrainLifeEffect(this);
    }

}
