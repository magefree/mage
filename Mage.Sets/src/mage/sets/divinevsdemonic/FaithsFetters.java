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
package mage.sets.divinevsdemonic;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class FaithsFetters extends CardImpl {

    public FaithsFetters(UUID ownerId) {
        super(ownerId, 20, "Faith's Fetters", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "DDC";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // When Faith's Fetters enters the battlefield, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4)));
        // Enchanted permanent can't attack or block, and its activated abilities can't be activated unless they're mana abilities.
        Effect effect = new CantAttackBlockAttachedEffect(AttachmentType.AURA);
        effect.setText("Enchanted permanent can't attack or block,");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new FaithsFettersEffect());
        this.addAbility(ability);
    }

    public FaithsFetters(final FaithsFetters card) {
        super(card);
    }

    @Override
    public FaithsFetters copy() {
        return new FaithsFetters(this);
    }
}

class FaithsFettersEffect extends ContinuousRuleModifyingEffectImpl {

    public FaithsFettersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "and its activated abilities can't be activated unless they're mana abilities";
    }

    public FaithsFettersEffect(final FaithsFettersEffect effect) {
        super(effect);
    }

    @Override
    public FaithsFettersEffect copy() {
        return new FaithsFettersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo().equals(event.getSourceId())) {
            Ability ability = game.getAbility(event.getTargetId(), event.getSourceId());
            if (ability != null) {
                if (ability.getAbilityType() != AbilityType.MANA) {
                    return true;
                }
            }
        }
        return false;
    }
}