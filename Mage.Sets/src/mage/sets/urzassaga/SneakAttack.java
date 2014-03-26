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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SneakAttack extends CardImpl<SneakAttack> {

    public SneakAttack(UUID ownerId) {
        super(ownerId, 218, "Sneak Attack", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "USG";

        this.color.setRed(true);

        // {R}: You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice the creature at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SneakAttackEffect(), new ManaCostsImpl("{R}")));
    }

    public SneakAttack(final SneakAttack card) {
        super(card);
    }

    @Override
    public SneakAttack copy() {
        return new SneakAttack(this);
    }
}

class SneakAttackEffect extends OneShotEffect<SneakAttackEffect> {
    
    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";
    
    public SneakAttackEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice the creature at the beginning of the next end step";
    }
    
    public SneakAttackEffect(final SneakAttackEffect effect) {
        super(effect);
    }
    
    @Override
    public SneakAttackEffect copy() {
        return new SneakAttackEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.PutCreatureInPlay, choiceText, game)) {
                TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
                if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId())) {
                            Permanent permanent = game.getPermanent(card.getId());
                            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                            effect.setTargetPointer(new FixedTarget(permanent.getId()));
                            game.addEffect(effect, source);
                            
                            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + card.getName());
                            sacrificeEffect.setTargetPointer(new FixedTarget(card.getId()));
                            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(sacrificeEffect);
                            delayedAbility.setSourceId(source.getSourceId());
                            delayedAbility.setControllerId(source.getControllerId());
                            game.addDelayedTriggeredAbility(delayedAbility);
                            
                            return true;
                        }                        
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
