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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeTargetedAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MTGfan & L_J
 */
public class AntiMagicAura extends CardImpl {

    public AntiMagicAura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't be the target of spells and can't be enchanted by other Auras.
        CantBeTargetedAttachedEffect cantTargetEffect = new CantBeTargetedAttachedEffect(new FilterSpell("spells"), Duration.WhileOnBattlefield, AttachmentType.AURA, TargetController.ANY);
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, cantTargetEffect);
        ability2.addEffect(new AntiMagicAuraRuleEffect());
        this.addAbility(ability2);
    }

    public AntiMagicAura(final AntiMagicAura card) {
        super(card);
    }

    @Override
    public AntiMagicAura copy() {
        return new AntiMagicAura(this);
    }
}

// 9/25/2006 ruling: If Consecrate Land enters the battlefield attached to a land that’s enchanted by other Auras, those Auras are put into their owners’ graveyards.
class AntiMagicAuraRuleEffect extends ContinuousRuleModifyingEffectImpl {

    public AntiMagicAuraRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "and can't be enchanted by other Auras";
    }

    public AntiMagicAuraRuleEffect(final AntiMagicAuraRuleEffect effect) {
        super(effect);
    }

    @Override
    public AntiMagicAuraRuleEffect copy() {
        return new AntiMagicAuraRuleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACH || event.getType() == EventType.STAY_ATTACHED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null && sourceObject.getAttachedTo() != null) {
            if (event.getTargetId().equals(sourceObject.getAttachedTo())) {
                return !event.getSourceId().equals(source.getSourceId());
            }
        }
        return false;
    }
}
