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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class EndlessWhispers extends CardImpl {

    public EndlessWhispers(UUID ownerId) {
        super(ownerId, 49, "Endless Whispers", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "5DN";

        this.color.setBlack(true);

        // Each creature has "When this creature dies, choose target opponent. That player puts this card from its owner's graveyard onto the battlefield under his or her control at the beginning of the next end step."
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceToBattlefieldEffect());
        Effect effect = new CreateDelayedTriggeredAbilityEffect(delayedAbility, true);
        effect.setText("choose target opponent. That player puts this card from its owner's graveyard onto the battlefield under his or her control at the beginning of the next end step");
        Ability gainAbility = new DiesTriggeredAbility(effect);
        gainAbility.addTarget(new TargetOpponent());
        
        effect = new GainAbilityAllEffect(gainAbility, Duration.WhileOnBattlefield, new FilterCreaturePermanent("Each creature"));
        effect.setText("Each creature has \"When this creature dies, choose target opponent. That player puts this card from its owner's graveyard onto the battlefield under his or her control at the beginning of the next end step.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    
    }

    public EndlessWhispers(final EndlessWhispers card) {
        super(card);
    }

    @Override
    public EndlessWhispers copy() {
        return new EndlessWhispers(this);
    }
}

class ReturnSourceToBattlefieldEffect extends OneShotEffect {


    public ReturnSourceToBattlefieldEffect() {
        this(false);
    }

    public ReturnSourceToBattlefieldEffect(boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        setText();
    }
    public ReturnSourceToBattlefieldEffect(boolean tapped, boolean ownerControl) {
        super(Outcome.PutCreatureInPlay);
        setText();
    }

    public ReturnSourceToBattlefieldEffect(final ReturnSourceToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSourceToBattlefieldEffect copy() {
        return new ReturnSourceToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getState().getZone(source.getSourceId()).equals(Zone.GRAVEYARD)) {
            return false;
        }        
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        
        Player player = game.getPlayer(source.getFirstTarget());
   
        if (player == null) {            
            return false;
        }        
        
        return player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId(), false);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("That player puts this card from its owner's graveyard onto the battlefield under his or her control");

    }

}