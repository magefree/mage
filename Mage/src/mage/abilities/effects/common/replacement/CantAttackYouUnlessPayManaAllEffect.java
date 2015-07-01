/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl.RestrictType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class CantAttackYouUnlessPayManaAllEffect extends PayCostToAttackBlockEffectImpl {

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, manaCosts);
        staticText = "Creatures can't attack you unless their controller pays " + manaCosts.getText() + " for each creature he or she controls that's attacking you";
    }

    CantAttackYouUnlessPayManaAllEffect(CantAttackYouUnlessPayManaAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getControllerId().equals(event.getTargetId());
    }

    @Override
    public CantAttackYouUnlessPayManaAllEffect copy() {
        return new CantAttackYouUnlessPayManaAllEffect(this);
    }
}
