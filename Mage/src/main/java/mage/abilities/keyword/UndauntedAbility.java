/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class UndauntedAbility extends SimpleStaticAbility {

    public UndauntedAbility() {
        super(Zone.ALL, new UndauntedEffect());
        setRuleAtTheTop(true);
    }

    public UndauntedAbility(final UndauntedAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new UndauntedAbility(this);
    }

}

class UndauntedEffect extends CostModificationEffectImpl {

    public UndauntedEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "undaunted <i>(This spell costs {1} less to cast for each opponent.)</i>";
    }

    public UndauntedEffect(final UndauntedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
            int count = game.getOpponents(source.getControllerId()).size();
            int newCount = mana.getGeneric() - count;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setGeneric(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public UndauntedEffect copy() {
        return new UndauntedEffect(this);
    }
}
