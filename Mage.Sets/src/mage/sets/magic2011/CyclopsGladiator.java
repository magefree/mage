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

package mage.sets.magic2011;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CyclopsGladiator extends CardImpl {

    public CyclopsGladiator(UUID ownerId) {
        super(ownerId, 131, "Cyclops Gladiator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}{R}");
        this.expansionSetCode = "M11";
        this.subtype.add("Cyclops");
        this.subtype.add("Warrior");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Cyclops Gladiator attacks, you may have it deal damage equal to its power to target creature defending player controls. 
        // If you do, that creature deals damage equal to its power to Cyclops Gladiator.
        Ability ability = new AttacksTriggeredAbility(new CyclopsGladiatorEffect(), true);
        this.addAbility(ability);
    }

    public CyclopsGladiator(final CyclopsGladiator card) {
        super(card);
    }

    @Override
    public CyclopsGladiator copy() {
        return new CyclopsGladiator(this);
    }

}

class CyclopsGladiatorEffect extends OneShotEffect {

    public CyclopsGladiatorEffect() {
        super(Outcome.Damage);
        staticText = "you may have it deal damage equal to its power to target creature defending player controls. If you do, that creature deals damage equal to its power to {this}";
    }

    public CyclopsGladiatorEffect(final CyclopsGladiatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        if (defenderId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            filter.add(new ControllerIdPredicate(defenderId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            Player player = game.getPlayer(source.getControllerId());
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                if (player.chooseTarget(Outcome.Detriment, target, source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    Permanent cyclops = game.getPermanent(source.getSourceId());
                    if (permanent != null && cyclops != null) {
                        permanent.damage(cyclops.getPower().getValue(), cyclops.getId(), game, false, true);
                        cyclops.damage(permanent.getPower().getValue(), permanent.getId(), game, false, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CyclopsGladiatorEffect copy() {
        return new CyclopsGladiatorEffect(this);
    }

}
