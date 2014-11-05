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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattelfieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.continious.LoseAbilitySourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AuraPermanentCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class AnimateDead extends CardImpl {

    public AnimateDead(UUID ownerId) {
        super(ownerId, 1, "Animate Dead", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "LEA";
        this.subtype.add("Aura");

        this.color.setBlack(true);

        // Enchant creature card in a graveyard
        TargetCardInGraveyard auraTarget = new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard"));
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AnimateDeadAttachEffect(Outcome.PutCreatureInPlay));
        Ability enchantAbility = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(enchantAbility);        
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard" 
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield 
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AnimateDeadReAttachEffect(), false),
                SourceOnBattelfieldCondition.getInstance(),
                "When Animate Dead enters the battlefield, if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with Animate Dead.\" Return enchanted creature card to the battlefield under your control and attach Animate Dead to it."));
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new AnimateDeadLeavesBattlefieldTriggeredEffect(), false));        
        
        // Enchanted creature gets -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-1, 0, Duration.WhileOnBattlefield)));
        
    }

    public AnimateDead(final AnimateDead card) {
        super(card);
    }

    @Override
    public AnimateDead copy() {
        return new AnimateDead(this);
    }
}

class AnimateDeadReAttachEffect extends OneShotEffect {
    
    public AnimateDeadReAttachEffect() {
        super(Outcome.Benefit);
        this.staticText = "if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with Animate Dead.\" Return enchanted creature card to the battlefield under your control and attach Animate Dead to it";
    }
    
    public AnimateDeadReAttachEffect(final AnimateDeadReAttachEffect effect) {
        super(effect);
    }
    
    @Override
    public AnimateDeadReAttachEffect copy() {
        return new AnimateDeadReAttachEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        
        if (controller != null && enchantment != null) {
            Card cardInGraveyard = game.getCard(enchantment.getAttachedTo());
            if (cardInGraveyard == null) {
                return true;
            }
            
            // put card into play
            controller.putOntoBattlefieldWithInfo(cardInGraveyard, game, Zone.GRAVEYARD, source.getSourceId());
            Ability abilityToLose = null;
            for (Ability ability : enchantment.getAbilities()) {
                if (ability instanceof EnchantAbility) {
                    abilityToLose = ability; 
                    break;
                }
            }
            if (abilityToLose == null) {
                return false;
            }
            Filter filter = new FilterCreaturePermanent();
            filter.add(new AuraPermanentCanAttachToPermanentId(cardInGraveyard.getId()));

            Ability newEnchantAbility = new EnchantAbility("enchant creature put onto the battlefield with Animate Dead");
            GainAbilitySourceEffect gainAbilitySourceEffect = new GainAbilitySourceEffect(newEnchantAbility, Duration.WhileOnBattlefield);
            game.addEffect(gainAbilitySourceEffect, source);
            
            enchantment.attachTo(cardInGraveyard.getId(), game);
            
            // remove enchant card in graveyard
            LoseAbilitySourceEffect loseAbilitySourceEffect = new LoseAbilitySourceEffect(abilityToLose, Duration.WhileOnBattlefield);
            game.addEffect(loseAbilitySourceEffect, source);
            
            return true;
        }
        
        return false;
    }
}
        
class AnimateDeadLeavesBattlefieldTriggeredEffect extends OneShotEffect {
    
    public AnimateDeadLeavesBattlefieldTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "enchanted creature's controller sacrifices it";
    }
    
    public AnimateDeadLeavesBattlefieldTriggeredEffect(final AnimateDeadLeavesBattlefieldTriggeredEffect effect) {
        super(effect);
    }
    
    @Override
    public AnimateDeadLeavesBattlefieldTriggeredEffect copy() {
        return new AnimateDeadLeavesBattlefieldTriggeredEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (sourcePermanent.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
                if (attachedTo != null) {
                    attachedTo.sacrifice(source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
}

class AnimateDeadAttachEffect extends OneShotEffect {

    public AnimateDeadAttachEffect(Outcome outcome) {
        super(outcome);
    }

    public AnimateDeadAttachEffect(Outcome outcome, String rule) {
        super(outcome);
        staticText = rule;
    }

    public AnimateDeadAttachEffect(final AnimateDeadAttachEffect effect) {
        super(effect);
    }

    @Override
    public AnimateDeadAttachEffect copy() {
        return new AnimateDeadAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null && game.getState().getZone(source.getFirstTarget()).equals(Zone.GRAVEYARD)) {
            // Card have no attachedTo attribute yet so write ref only to enchantment now
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null) {
                enchantment.attachTo(card.getId(), game);
            }
            return true;
        }
        return false;
    }

}
