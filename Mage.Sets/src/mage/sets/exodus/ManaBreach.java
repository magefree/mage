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
package mage.sets.exodus;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox

 */
public class ManaBreach extends CardImpl {

    public ManaBreach(UUID ownerId) {
        super(ownerId, 38, "Mana Breach", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "EXO";

        // Whenever a player casts a spell, that player returns a land he or she controls to its owner's hand.
        this.addAbility(new SpellCastAllTriggeredAbility(new ManaBreachEffect(), new FilterSpell(), false, SetTargetPointer.PLAYER));
    }

    public ManaBreach(final ManaBreach card) {
        super(card);
    }

    @Override
    public ManaBreach copy() {
        return new ManaBreach(this);
    }
}

class ManaBreachEffect extends OneShotEffect {

    public ManaBreachEffect() {
        super(Outcome.Detriment);
        staticText="that player returns a land he or she controls to its owner's hand.";
    }

    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if(player != null) {
            FilterLandPermanent filter = new FilterLandPermanent("a land you control");
            filter.add(new ControllerIdPredicate(player.getId()));
            TargetLandPermanent toBounce = new TargetLandPermanent(1, 1, filter, true);
            if(player.chooseTarget(Outcome.ReturnToHand, toBounce, source, game)) {
                Permanent land = game.getPermanent(toBounce.getTargets().get(0));
                land.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }

    public ManaBreachEffect(final ManaBreachEffect effect) {
        super(effect);
    }

    public ManaBreachEffect copy() {
        return new ManaBreachEffect(this);
    }

}
