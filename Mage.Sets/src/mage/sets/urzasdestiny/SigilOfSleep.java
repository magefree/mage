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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LevelX2
 */
public class SigilOfSleep extends CardImpl<SigilOfSleep> {

    public SigilOfSleep(UUID ownerId) {
        super(ownerId, 46, "Sigil of Sleep", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "UDS";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted creature deals damage to a player, return target creature that player controls to its owner's hand.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return target creature that player controls to its owner's hand");
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(effect, "enchanted", false, true);
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof DealsDamageToAPlayerAttachedTriggeredAbility) {
            UUID playerId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
            if (playerId != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that player controls");
                filter.add(new ControllerIdPredicate(playerId));
                Target target = new TargetCreaturePermanent(filter);
                target.setRequired(true);
                ability.getTargets().clear();
                ability.addTarget(target);
                ability.getEffects().get(0).setTargetPointer(new FirstTargetPointer());
            }

        }
    }

    public SigilOfSleep(final SigilOfSleep card) {
        super(card);
    }

    @Override
    public SigilOfSleep copy() {
        return new SigilOfSleep(this);
    }
}
