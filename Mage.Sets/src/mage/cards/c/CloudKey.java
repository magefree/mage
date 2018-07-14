/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author nick.myers
 */
public final class CloudKey extends CardImpl {

    public CloudKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // As Cloud Key enters the battlefield, choose artifact, creature, enchantment, instant, or sorcery.
        this.addAbility(new AsEntersBattlefieldAbility(new CloudKeyChooseTypeEffect()));

        // Spells you cast of the chosen type cost {1} less to cast.
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
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (mageObject != null && controller != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            choices.setMessage("Choose a spell type");
            choices.getChoices().add(CardType.ARTIFACT.toString());
            choices.getChoices().add(CardType.CREATURE.toString());
            choices.getChoices().add(CardType.ENCHANTMENT.toString());
            choices.getChoices().add(CardType.INSTANT.toString());
            choices.getChoices().add(CardType.SORCERY.toString());
            if (controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(mageObject.getLogName() + ": chosen spell type is " + choices.getChoice());
                game.getState().setValue(source.getSourceId().toString() + "_CloudKey", choices.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosenCardType", CardUtil.addToolTipMarkTags("Chosen card type: " + choices.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }

}

class CloudKeyCostModificationEffect extends CostModificationEffectImpl {

    public CloudKeyCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells you cast of the chosen type cost {1} less to cast.";
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
        if (abilityToModify instanceof SpellAbility && abilityToModify.isControlledBy(source.getControllerId())) {
            Spell spell = game.getStack().getSpell(abilityToModify.getSourceId());
            if (spell != null && spell.getCardType().toString().contains((String) game.getState().getValue(source.getSourceId().toString() + "_CloudKey"))) {
                return true;
            }
        }

        return false;
    }
}
