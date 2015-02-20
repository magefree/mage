/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.futuresight;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author nick.myers
 */
public class CloudKey extends CardImpl {
    
    private static final Choice spellTypeChoice = new ChoiceImpl(true);
    private static final Set<String> spellTypeChoices = new HashSet<String>();
    static {
        spellTypeChoice.setMessage("Choose a spell type");
        spellTypeChoices.add(CardType.ARTIFACT.toString());
        spellTypeChoices.add(CardType.CREATURE.toString());
        spellTypeChoices.add(CardType.ENCHANTMENT.toString());
        spellTypeChoices.add(CardType.INSTANT.toString());
        spellTypeChoices.add(CardType.SORCERY.toString());
        spellTypeChoice.setChoices(spellTypeChoices);
    }
    
    public CloudKey(UUID ownerId) {
        super(ownerId, 160, "Cloud Key", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "FUT";
        
        // As Cloud Key enters the battlefield, choose artifact, creature, 
        // enchantment, instant, or sorcery.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CloudKeyChooseEffect(), false);
        ability.addChoice(spellTypeChoice);
        this.addAbility(ability);
        
        // Spells of the chosen type cost {1} less to cast
        // implement here
    }
    
    @Override
    public CloudKey copy() {
        return new CloudKey(this);
    }
    
    public CloudKey(final CloudKey card) {
        super(card);
    }
}

class CloudKeyChooseEffect extends CostModificationEffectImpl {

    public CloudKeyChooseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "choose artifact, creature, enchantment, instant, or sorcery. Spells of the chosen type cost {1} less to cast.";
    }
    
    public CloudKeyChooseEffect(final CloudKeyChooseEffect effect) {
        super(effect);
    }
    
    @Override
    public CloudKeyChooseEffect copy() {
        return new CloudKeyChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        
        if (abilityToModify instanceof SpellAbility && abilityToModify.getControllerId().equals(source.getControllerId())) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card.getCardType().toString().contains(source.getChoices().get(0).getChoice())) {                
                return true;
            }
        }
        
        return false;
    }    
}

