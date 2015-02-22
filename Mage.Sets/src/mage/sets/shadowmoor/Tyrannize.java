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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PostResolveEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth using code from LevelX
 */
public class Tyrannize extends CardImpl {
    
    private static final String rule = "Pay 7 life? (otherwise discard your hand)";

    public Tyrannize(UUID ownerId) {
        super(ownerId, 201, "Tyrannize", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{B/R}{B/R}");
        this.expansionSetCode = "SHM";

        // Target player discards his or her hand unless he or she pays 7 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DoUnlessTargetPaysEffect(new DiscardHandTargetEffect(), new PayLifeCost(7), rule));
        
    }

    public Tyrannize(final Tyrannize card) {
        super(card);
    }

    @Override
    public Tyrannize copy() {
        return new Tyrannize(this);
    }
}

class DoUnlessTargetPaysEffect extends OneShotEffect {
    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoUnlessTargetPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessTargetPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessTargetPaysEffect(final DoUnlessTargetPaysEffect effect) {
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
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null && targetPlayer != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + cost.getText() + " to prevent (" + effectText.substring(0, effectText.length() -1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, sourceObject.getLogName());
            boolean result = true;
            boolean doEffect = true;
            // check if target player is willing to pay
            if (cost.canPay(source, source.getSourceId(), targetPlayer.getId(), game) && targetPlayer.chooseUse(Outcome.Detriment, message, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), targetPlayer.getId(), false)) {
                    game.informPlayers(targetPlayer.getName() + " pays the cost to prevent the effect");
                    doEffect = false;
                }
            }
            // do the effects player did not pay
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
        String effectsText = executingEffects.getText(mode);
        return  effectsText.substring(0, effectsText.length() -1) + " unless he or she pays " + cost.getText();
    }

    @Override
    public DoUnlessTargetPaysEffect copy() {
        return new DoUnlessTargetPaysEffect(this);
    }
}
