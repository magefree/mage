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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class WildSlash extends CardImpl {

    public WildSlash(UUID ownerId) {
        super(ownerId, 118, "Wild Slash", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "FRF";

        // <i>Ferocious</i> If you control a creature with power 4 or greater, damage can't be prevented this turn.
        ContinuousRuleModifyingEffect effect = new DamageCantBePreventedEffect();
        effect.setText("<i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, damage can't be prevented this turn.<br>");
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(effect,
                        new LockedInCondition(FerociousCondition.getInstance())));
        
        // Wild Slash deals 2 damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        
    }

    public WildSlash(final WildSlash card) {
        super(card);
    }

    @Override
    public WildSlash copy() {
        return new WildSlash(this);
    }
}

class DamageCantBePreventedEffect extends ContinuousRuleModifyingEffectImpl {

    public DamageCantBePreventedEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "damage can't be prevented this turn";
    }

    public DamageCantBePreventedEffect(final DamageCantBePreventedEffect effect) {
        super(effect);
    }

    @Override
    public DamageCantBePreventedEffect copy() {
        return new DamageCantBePreventedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
