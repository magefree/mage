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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureAttachedTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.condition.common.PermanentOnBattelfieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SkipUntapTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;

/**
 *
 * @author LevelX2
 */
public class NekoTe extends CardImpl<NekoTe> {

    public NekoTe(UUID ownerId) {
        super(ownerId, 155, "Neko-Te", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Equipment");

        // Whenever equipped creature deals damage to a creature, tap that creature. That creature doesn't untap during its controller's untap step for as long as Neko-Te remains on the battlefield.
        ReplacementEffect skipUntapEffect = new SkipUntapTargetEffect(Duration.WhileOnBattlefield);
        skipUntapEffect.setText("That creature doesn't untap during its controller's untap step for as long as {this} remains on the battlefield");
        ConditionalReplacementEffect effect = new ConditionalReplacementEffect(skipUntapEffect, new PermanentOnBattelfieldCondition(), false);
        Ability ability = new DealsDamageToACreatureAttachedTriggeredAbility(new TapTargetEffect("that creature"), false, "equipped creature", false, true);
        ability.addEffect(effect);
        this.addAbility(ability);
        // Whenever equipped creature deals damage to a player, that player loses 1 life.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new LoseLifeTargetEffect(1), "equipped creature", false, true, false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(2)));
    }

    public NekoTe(final NekoTe card) {
        super(card);
    }

    @Override
    public NekoTe copy() {
        return new NekoTe(this);
    }
}
