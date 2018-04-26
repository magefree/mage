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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EnchantedCreatureBlockedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public class LaccolithRig extends CardImpl {

    public LaccolithRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature becomes blocked, you may have it deal damage equal to its power to target creature. If you do, the first creature assigns no combat damage this turn.
        Ability ability2 = new EnchantedCreatureBlockedTriggeredAbility(new LaccolithRigEffect(), true);
        ability2.addTarget(new TargetCreaturePermanent());
        Effect effect = new GainAbilityTargetEffect(new SimpleStaticAbility(Zone.BATTLEFIELD, new AssignNoCombatDamageSourceEffect(Duration.Custom, true).setText("")), Duration.EndOfTurn, "If you do, the first creature assigns no combat damage this turn");
        ability2.addEffect(effect);
        this.addAbility(ability2);
    }

    public LaccolithRig(final LaccolithRig card) {
        super(card);
    }

    @Override
    public LaccolithRig copy() {
        return new LaccolithRig(this);
    }
}

class LaccolithRigEffect extends OneShotEffect {

    public LaccolithRigEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have it deal damage equal to its power to target creature";
    }

    public LaccolithRigEffect(final LaccolithRigEffect effect) {
        super(effect);
    }

    @Override
    public LaccolithRigEffect copy() {
        return new LaccolithRigEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent ownCreature = game.getPermanent(enchantment.getAttachedTo());
        if (ownCreature != null) {
            int damage = ownCreature.getPower().getValue();
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(damage, ownCreature.getId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
