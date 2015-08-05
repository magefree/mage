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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author anonymous
 */
public class BlizzardSpecter extends CardImpl {

    public BlizzardSpecter(UUID ownerId) {
        super(ownerId, 126, "Blizzard Specter", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "CSP";
        this.supertype.add("Snow");
        this.subtype.add("Specter");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Blizzard Specter deals combat damage to a player, choose one - That player returns a permanent he or she controls to its owner's hand; or that player discards a card.
        Ability ability2 = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnToHandEffect(), false);
        
        Mode mode2 = new Mode();
        mode2.getEffects().add(new DiscardTargetEffect(1, false));
        ability2.addMode(mode2);
        
        this.addAbility(ability2);
    }

    public BlizzardSpecter(final BlizzardSpecter card) {
        super(card);
    }

    @Override
    public BlizzardSpecter copy() {
        return new BlizzardSpecter(this);
    }
}

class ReturnToHandEffect extends OneShotEffect {

    public ReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "That player returns a permanent he or she controls to its owner's hand";
    }

    public ReturnToHandEffect(final ReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandEffect copy() {
        return new ReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }

        Target target = new TargetControlledPermanent(1, 1, new FilterControlledPermanent(), true);
        if (target.canChoose(player.getId(), game)) {
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), game)) {
                player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            }

            for (UUID targetId: target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
        }
        return result;
    }
}
