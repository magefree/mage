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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class DemonicPact extends CardImpl {

    public DemonicPact(UUID ownerId) {
        super(ownerId, 92, "Demonic Pact", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "ORI";

        // At the beginning of your upkeep, choose one that hasn't been chosen
        // - Demonic Pact deals 4 damage to target creature or player and you gain 4 life;
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(4), TargetController.YOU, false);
        ability.getModes().setEachModeOnlyOnce(true);
        ability.addTarget(new TargetCreatureOrPlayer());
        Effect effect = new GainLifeEffect(4);
        effect.setText("and you gain 4 life");
        ability.addEffect(effect);

        // - Target opponent discards two cards
        Mode mode = new Mode();
        mode.getTargets().add(new TargetOpponent());
        mode.getEffects().add(new DiscardTargetEffect(2));
        ability.addMode(mode);

        // - Draw two cards
        mode = new Mode();
        mode.getEffects().add(new DrawCardSourceControllerEffect(2));
        ability.addMode(mode);

        // - You lose the game.
        mode = new Mode();
        mode.getEffects().add(new LoseGameSourceControllerEffect());
        ability.addMode(mode);

        this.addAbility(ability);

    }

    public DemonicPact(final DemonicPact card) {
        super(card);
    }

    @Override
    public DemonicPact copy() {
        return new DemonicPact(this);
    }
}
