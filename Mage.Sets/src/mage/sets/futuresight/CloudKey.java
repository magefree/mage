/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author nick.myers
 */
public class CloudKey extends CardImpl {

    public CloudKey(UUID ownerId) {
        super(ownerId, 160, "Cloud Key", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "FUT";

        // As Cloud Key enters the battlefield, choose artifact, creature,
        // enchantment, instant, or sorcery.
        this.addAbility(new AsEntersBattlefieldAbility(new CloudKeyChooseTypeEffect()));

        // Spells of the chosen type cost {1} less to cast
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CloudKeyCostModificationEffect()));
    }

    @Override
    public CloudKey copy() {
        return new CloudKey(this);
    }

    public CloudKey(final CloudKey card) {
        super(card);
    }
}

class CloudKeyChooseTypeEffect extends OneShotEffect {

    public CloudKeyChooseTypeEffect() {
        super(Outcome.Neutral);
        this.staticText = "choose artifact, creature, enchantment, instant, or sorcery.";
    }

    public CloudKeyChooseTypeEffect(final CloudKeyChooseTypeEffect effect) {
        super(effect);
    }

    @Override
    public CloudKeyChooseTypeEffect copy() {
        return new CloudKeyChooseTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            choices.setMessage("Choose a spell type");
            choices.getChoices().add(CardType.ARTIFACT.toString());
            choices.getChoices().add(CardType.CREATURE.toString());
            choices.getChoices().add(CardType.ENCHANTMENT.toString());
            choices.getChoices().add(CardType.INSTANT.toString());
            choices.getChoices().add(CardType.SORCERY.toString());
            if(controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(sourceObject.getLogName() + ": chosen spell type is " + choices.getChoice());
                game.getState().setValue(source.getSourceId().toString() + "_CloudKey", choices.getChoice());
                return true;
            }
        }
        return false;
    }

}

class CloudKeyCostModificationEffect extends CostModificationEffectImpl {

    public CloudKeyCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells of the chosen type cost {1} less to cast.";
    }

    public CloudKeyCostModificationEffect(final CloudKeyCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public CloudKeyCostModificationEffect copy() {
        return new CloudKeyCostModificationEffect(this);
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
            if (card.getCardType().toString().contains((String) game.getState().getValue(source.getSourceId().toString() + "_CloudKey"))) {
                return true;
            }
        }

        return false;
    }
}

