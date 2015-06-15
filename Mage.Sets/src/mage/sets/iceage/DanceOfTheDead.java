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
package mage.sets.iceage;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DanceOfTheDead extends CardImpl {

    public DanceOfTheDead(UUID ownerId) {
        super(ownerId, 6, "Dance of the Dead", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Aura");


        // Enchant creature card in a graveyard
        TargetCardInGraveyard auraTarget = new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard"));
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new DanceOfTheDeadAttachEffect(Outcome.PutCreatureInPlay));
        Ability enchantAbility = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(enchantAbility);          
        // When Dance of the Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard" and gains "enchant creature put onto the battlefield with Dance of the Dead." Put enchanted creature card to the battlefield tapped under your control and attach Dance of the Dead to it. When Dance of the Dead leaves the battlefield, that creature's controller sacrifices it.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DanceOfTheDeadReAttachEffect(), false),
                SourceOnBattlefieldCondition.getInstance(),
                "When {this} enters the battlefield, if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with {this}.\" Return enchanted creature card to the battlefield under your control and attach {this} to it.");
        ability.addEffect(new DanceOfTheDeadChangeAbilityEffect());
        this.addAbility(ability);
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DanceOfTheDeadLeavesBattlefieldTriggeredEffect(), false));         
        
        // Enchanted creature gets +1/+1 and doesn't untap during its controller's untap step.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Effect effect = new DontUntapInControllersUntapStepEnchantedEffect();
        effect.setText("and doesn't untap during its controller's untap step");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // At the beginning of the upkeep of enchanted creature's controller, that player may pay {1}{B}. If he or she does, untap that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DanceOfTheDeadDoIfCostPaidEffect(), TargetController.CONTROLLER_ATTACHED_TO, false));
        
    }

    public DanceOfTheDead(final DanceOfTheDead card) {
        super(card);
    }

    @Override
    public DanceOfTheDead copy() {
        return new DanceOfTheDead(this);
    }
}

class DanceOfTheDeadReAttachEffect extends OneShotEffect {
    
    public DanceOfTheDeadReAttachEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return enchanted creature card to the battlefield under your control and attach {this} to it";
    }
    
    public DanceOfTheDeadReAttachEffect(final DanceOfTheDeadReAttachEffect effect) {
        super(effect);
    }
    
    @Override
    public DanceOfTheDeadReAttachEffect copy() {
        return new DanceOfTheDeadReAttachEffect(this);
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
            controller.putOntoBattlefieldWithInfo(cardInGraveyard, game, Zone.GRAVEYARD, source.getSourceId(), true);
            Permanent enchantedCreature = game.getPermanent(cardInGraveyard.getId());
            
            FilterCreaturePermanent filter = new FilterCreaturePermanent("enchant creature put onto the battlefield with Dance of the Dead");
            filter.add(new PermanentIdPredicate(cardInGraveyard.getId()));
            Target target = new TargetCreaturePermanent(filter);
            //enchantAbility.setTargetName(target.getTargetName());
            if (enchantedCreature != null) {
                target.addTarget(enchantedCreature.getId(), source, game);
                enchantment.getSpellAbility().getTargets().clear();
                enchantment.getSpellAbility().getTargets().add(target);
                enchantedCreature.addAttachment(enchantment.getId(), game);
            }
            return true;
        }
        
        return false;
    }
}
        
class DanceOfTheDeadLeavesBattlefieldTriggeredEffect extends OneShotEffect {
    
    public DanceOfTheDeadLeavesBattlefieldTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "enchanted creature's controller sacrifices it";
    }
    
    public DanceOfTheDeadLeavesBattlefieldTriggeredEffect(final DanceOfTheDeadLeavesBattlefieldTriggeredEffect effect) {
        super(effect);
    }
    
    @Override
    public DanceOfTheDeadLeavesBattlefieldTriggeredEffect copy() {
        return new DanceOfTheDeadLeavesBattlefieldTriggeredEffect(this);
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

class DanceOfTheDeadAttachEffect extends OneShotEffect {

    public DanceOfTheDeadAttachEffect(Outcome outcome) {
        super(outcome);
    }

    public DanceOfTheDeadAttachEffect(Outcome outcome, String rule) {
        super(outcome);
        staticText = rule;
    }

    public DanceOfTheDeadAttachEffect(final DanceOfTheDeadAttachEffect effect) {
        super(effect);
    }

    @Override
    public DanceOfTheDeadAttachEffect copy() {
        return new DanceOfTheDeadAttachEffect(this);
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

class DanceOfTheDeadChangeAbilityEffect extends ContinuousEffectImpl implements SourceEffect {

    private final static Ability newAbility = new EnchantAbility("creature put onto the battlefield with Dance of the Dead");
    
    static {
        newAbility.setRuleAtTheTop(true);
    }
    
    public DanceOfTheDeadChangeAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with Dance of the Dead\"";
    }


    public DanceOfTheDeadChangeAbilityEffect(final DanceOfTheDeadChangeAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DanceOfTheDeadChangeAbilityEffect copy() {
        return new DanceOfTheDeadChangeAbilityEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);;
        if (permanent != null) {
            Ability abilityToRemove = null;
            for (Ability ability: permanent.getAbilities()) {
                if (ability instanceof EnchantAbility) {
                    abilityToRemove = ability;
                }
            }
            if (abilityToRemove != null) {
                permanent.getAbilities().remove(abilityToRemove);
            }
            permanent.addAbility(newAbility, source.getSourceId(), game);
            return true;
        }           
        return false;
    }
}

class DanceOfTheDeadDoIfCostPaidEffect extends DoIfCostPaid {

    public DanceOfTheDeadDoIfCostPaidEffect() {
        super(new UntapEnchantedEffect(), new ManaCostsImpl("{1}{B}"));
    }

    public DanceOfTheDeadDoIfCostPaidEffect(final DanceOfTheDeadDoIfCostPaidEffect effect) {
        super(effect);
    }

    @Override
    public DanceOfTheDeadDoIfCostPaidEffect copy() {
        return new DanceOfTheDeadDoIfCostPaidEffect(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
        return game.getPlayer(attachedTo.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        return new StringBuilder("that player may ").append(getCostText())
                .append(". If he or she does, ").append(executingEffects.getText(mode)).toString();
    }
}