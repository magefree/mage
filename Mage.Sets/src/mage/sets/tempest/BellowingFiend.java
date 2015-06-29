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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public class BellowingFiend extends CardImpl {

    public BellowingFiend(UUID ownerId) {
        super(ownerId, 2, "Bellowing Fiend", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Spirit");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Bellowing Fiend deals damage to a creature, Bellowing Fiend deals 3 damage to that creature's controller and 3 damage to you.
        Ability ability = new DealsDamageToACreatureTriggeredAbility(new BellowingFiendEffect(), false, false, true);
        Effect effect = new DamageControllerEffect(3);
        effect.setText("and 3 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public BellowingFiend(final BellowingFiend card) {
        super(card);
    }

    @Override
    public BellowingFiend copy() {
        return new BellowingFiend(this);
    }
}

class BellowingFiendEffect extends OneShotEffect {

    public BellowingFiendEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals 3 damage to that creature's controller";                                                                                          }

    public BellowingFiendEffect(final BellowingFiendEffect effect) {
        super(effect);
    }

    @Override
    public BellowingFiendEffect copy() {
        return new BellowingFiendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Permanent damagedCreature = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if(damagedCreature != null) {
            Player controller = game.getPlayer(damagedCreature.getControllerId());
            if(controller != null) {
                controller.damage(3, source.getSourceId(), game, false, true);                                                                                                       applied = true;
            }
        }
        return applied;
    }
}
