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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author stravant
 */
public class CartoucheOfStrength extends CardImpl {

    public CartoucheOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.subtype.add("Aura");
        this.subtype.add("Cartouche");

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Cartouche of Strength enters the battlefield, you may have enchanted creature fight target creature an opponent controls.
        ability = new EntersBattlefieldTriggeredAbility(new FightEnchantedTargetEffect(), /* optional = */true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has trample.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public CartoucheOfStrength(final CartoucheOfStrength card) {
        super(card);
    }

    @Override
    public CartoucheOfStrength copy() {
        return new CartoucheOfStrength(this);
    }
}

/**
 *
 * @author stravant
 */
class FightEnchantedTargetEffect extends OneShotEffect {
    public FightEnchantedTargetEffect() {
        super(Outcome.Damage);
    }

    public FightEnchantedTargetEffect(final FightEnchantedTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent  = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent originalCreature = game.getPermanentOrLKIBattlefield(sourcePermanent.getAttachedTo());
            if (originalCreature != null) {
                Permanent enchantedCreature = game.getPermanent(sourcePermanent.getAttachedTo());
                // only if target is legal the effect will be applied
                if (source.getTargets().get(0).isLegal(source, game)) {
                    Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
                    // 20110930 - 701.10
                    if (creature1 != null && enchantedCreature != null) {
                        if (creature1.isCreature() && enchantedCreature.isCreature()) {
                            return enchantedCreature.fight(creature1, source, game);
                        }
                    }
                }
                if (!game.isSimulation())
                    game.informPlayers(originalCreature.getLogName() + ": Fighting effect has been fizzled.");
            }
        }
        return false;
    }

    @Override
    public FightEnchantedTargetEffect copy() {
        return new FightEnchantedTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "you may have enchanted creature fight target creature an opponent controls.";
    }

}