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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author ThomasLerner
 */
public class LodestoneBauble extends CardImpl {

    public LodestoneBauble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");
        

        // {1}, {T}, Sacrifice Lodestone Bauble: Put up to four target basic land cards from a player's graveyard on top of his or her library in any order. That player draws a card at the beginning of the next turn's upkeep.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(true), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 4, new FilterBasicLandCard("basic land cards from a player's graveyard")));
        ability.addEffect(new LodestoneBaubleEffect());
        this.addAbility(ability);

    }

    public LodestoneBauble(final LodestoneBauble card) {
        super(card);
    }

    @Override
    public LodestoneBauble copy() {
        return new LodestoneBauble(this);
    }
}

class LodestoneBaubleEffect extends OneShotEffect {

    public LodestoneBaubleEffect() {
        super(Outcome.Detriment);
        staticText = "Put up to four target basic land cards from a player's graveyard on top of his or her library in any order."
                + " That player draws a card at the beginning of the next turn's upkeep";
    }

    public LodestoneBaubleEffect(final LodestoneBaubleEffect effect) {
        super(effect);
    }

    @Override
    public LodestoneBaubleEffect copy() {
        return new LodestoneBaubleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player chosenPlayer = null;
        UUID targetId = this.getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            TargetPlayer target = new TargetPlayer(1, 1, true);
            controller.chooseTarget(outcome, target, source, game);
            chosenPlayer = game.getPlayer(target.getFirstTarget());
        }
        if (targetId != null) {
            chosenPlayer = game.getPlayer(game.getControllerId(targetId));
        }
        if (chosenPlayer != null) {
            Effect effect = new DrawCardTargetEffect(1);
            effect.setTargetPointer(new FixedTarget(chosenPlayer.getId()));
            effect.setText("That player draws a card");
            DelayedTriggeredAbility ability = new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(ability, source);
        }
        return true;
    }

}