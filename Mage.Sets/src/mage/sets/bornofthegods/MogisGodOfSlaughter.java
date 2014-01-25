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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class MogisGodOfSlaughter extends CardImpl<MogisGodOfSlaughter> {

    public MogisGodOfSlaughter(UUID ownerId) {
        super(ownerId, 151, "Mogis, God of Slaughter", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "BNG";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to black and red is less than seven, Mogis isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.B, ColoredManaSymbol.R), 7);
        effect.setText("As long as your devotion to black and red is less than seven, Mogis isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));        
        
        // At the beginning of each opponent's upkeep, Mogis deals 2 damage to that player unless he or she sacrifices a creature.        
        effect = new DoUnlessTargetPaysCost(new DamageTargetEffect(2, false, "that player"), new SacrificeTargetCost(new TargetControlledCreaturePermanent()),
                "Sacrifice a creature? (otherwise you get 2 damage)");
        effect.setText("Mogis deals 2 damage to that player unless he or she sacrifices a creature");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.OPPONENT, false, true);
        this.addAbility(ability);
    }

    public MogisGodOfSlaughter(final MogisGodOfSlaughter card) {
        super(card);
    }

    @Override
    public MogisGodOfSlaughter copy() {
        return new MogisGodOfSlaughter(this);
    }
}

class DoUnlessTargetPaysCost extends OneShotEffect<DoUnlessTargetPaysCost> {
    private final OneShotEffect executingEffect;
    private final Cost cost;
    private final String userMessage;

    public DoUnlessTargetPaysCost(OneShotEffect effect, Cost cost) {
        this(effect, cost, null);
    }
    public DoUnlessTargetPaysCost(OneShotEffect effect, Cost cost, String userMessage) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.cost = cost;
        this.userMessage = userMessage;
    }

    public DoUnlessTargetPaysCost(final DoUnlessTargetPaysCost effect) {
        super(effect);
        this.executingEffect = (OneShotEffect) effect.executingEffect.copy();
        this.cost = effect.cost.copy();
        this.userMessage = effect.userMessage;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            String message = userMessage;
            if (message == null) {
                message = new StringBuilder(getCostText()).append(" to prevent ").append(executingEffect.getText(source.getModes().getMode())).append("?").toString();
            }
            message = CardUtil.replaceSourceName(message, mageObject.getName());
            cost.clearPaid();
            if (cost.canPay(source.getSourceId(), player.getId(), game) && player.chooseUse(executingEffect.getOutcome(), message, game)) {            
                cost.pay(source, game, source.getSourceId(), player.getId(), false);
            }
            if (!cost.isPaid()) {
                executingEffect.setTargetPointer(this.targetPointer);
                return executingEffect.apply(game, source);                
            }            
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(executingEffect.getText(mode));
        sb.append("unless he or she");
        sb.append(getCostText());
        return sb.toString();
    }

    private String getCostText() {
        StringBuilder sb = new StringBuilder();
        String costText = cost.getText();
        if (costText != null &&
                !costText.toLowerCase().startsWith("discard")
                && !costText.toLowerCase().startsWith("sacrifice")
                && !costText.toLowerCase().startsWith("remove")) {
            sb.append("pay ");
        }
        return sb.append(costText).toString();
    }

    @Override
    public DoUnlessTargetPaysCost copy() {
        return new DoUnlessTargetPaysCost(this);
    }
}
