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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class SpeciesGorger extends CardImpl<SpeciesGorger> {

    public SpeciesGorger(UUID ownerId) {
        super(ownerId, 105, "Species Gorger", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Frog");
        this.subtype.add("Beast");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, return a creature you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new ReturnToHandChooseEffect(), TargetController.YOU, false));
        
    }

    public SpeciesGorger(final SpeciesGorger card) {
        super(card);
    }

    @Override
    public SpeciesGorger copy() {
        return new SpeciesGorger(this);
    }
}

class ReturnToHandChooseEffect extends OneShotEffect<ReturnToHandChooseEffect> {

    public ReturnToHandChooseEffect() {
        super(Constants.Outcome.ReturnToHand);
        this.staticText = "return a creature you control to its owner's hand";
    }

    public ReturnToHandChooseEffect(final ReturnToHandChooseEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandChooseEffect copy() {
        return new ReturnToHandChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
            target.setRequired(true);
            if (player.choose(this.outcome, target, source.getSourceId(), game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    return permanent.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
