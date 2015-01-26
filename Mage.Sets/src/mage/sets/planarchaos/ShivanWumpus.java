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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PostResolveEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ShivanWumpus extends CardImpl {

    public ShivanWumpus(UUID ownerId) {
        super(ownerId, 121, "Shivan Wumpus", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Beast");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // When Shivan Wumpus enters the battlefield, any player may sacrifice a land. If a player does, put Shivan Wumpus on top of its owner's library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfAnyPlayerPaysEffect(
                        new PutOnLibrarySourceEffect(true), 
                        new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))), 
                        "Sacrifice a land to return {this} to top of its owners library?"),
                false
        ));
    }

    public ShivanWumpus(final ShivanWumpus card) {
        super(card);
    }

    @Override
    public ShivanWumpus copy() {
        return new ShivanWumpus(this);
    }
}

class DoIfAnyPlayerPaysEffect extends OneShotEffect {
    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoIfAnyPlayerPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoIfAnyPlayerPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoIfAnyPlayerPaysEffect(final DoIfAnyPlayerPaysEffect effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    public void addEffect(Effect effect) {
        executingEffects.add(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + cost.getText() + " to prevent (" + effectText.substring(0, effectText.length() -1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, sourceObject.getLogName());
            boolean result = true;
            boolean doEffect = false;
            // check if any player is willing to pay
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null && cost.canPay(source, source.getSourceId(), player.getId(), game) && player.chooseUse(Outcome.Detriment, message, game)) {
                    cost.clearPaid();
                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                        game.informPlayers(sourceObject.getLogName() + ": " + player.getName() + " pays the cost");
                        doEffect = true;
                        break;
                    }
                }
            }
            // do the effects if nobody paid
            if (doEffect) {
                for(Effect effect: executingEffects) {
                    effect.setTargetPointer(this.targetPointer);
                    if (effect instanceof OneShotEffect) {
                        if (!(effect instanceof PostResolveEffect)) {
                            result &= effect.apply(game, source);
                        }
                    }
                    else {
                        game.addEffect((ContinuousEffect) effect, source);
                    }
                }
            }
            return result;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        // any player may sacrifice a land. If a player does, put Shivan Wumpus on top of its owner's library.
        String effectsText = executingEffects.getText(mode);
        return  "any player may " + cost.getText() + ". If a player does, " + effectsText.substring(0, effectsText.length() -1) ;
    }

    @Override
    public DoIfAnyPlayerPaysEffect copy() {
        return new DoIfAnyPlayerPaysEffect(this);
    }
}
