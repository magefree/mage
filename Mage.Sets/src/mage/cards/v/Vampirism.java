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
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author tcontis
 */
public class Vampirism extends CardImpl {

    public Vampirism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Vampirism enters the battlefield, draw a card at the beginning of the next turn's upkeep.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse)), false));

        // Enchanted creature gets +1/+1 for each other creature you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VampirismBoostEnchantedEffect()));

        // Other creatures you control get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(-1, -1, Duration.WhileOnBattlefield, true)));

    }

    public Vampirism(final Vampirism card) {
        super(card);
    }

    @Override
    public Vampirism copy() {
        return new Vampirism(this);
    }
}

class VampirismBoostEnchantedEffect extends ContinuousEffectImpl {

    public VampirismBoostEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Enchanted creature gets +1/+1 for each other creature you control";
    }

    public VampirismBoostEnchantedEffect(final VampirismBoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public VampirismBoostEnchantedEffect copy() {
        return new VampirismBoostEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) - 1;
        if (count > 0) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent creature = game.getPermanent(enchantment.getAttachedTo());
                if (creature != null) {
                    creature.addPower(count);
                    creature.addToughness(count);
                    return true;
                }
            }
        }
        return false;
    }
}
