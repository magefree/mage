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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Dilnu
 */
public class BorosFuryShield extends CardImpl {
    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature();

    public BorosFuryShield(UUID ownerId) {
        super(ownerId, 5, "Boros Fury-Shield", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "RAV";

        // Prevent all combat damage that would be dealt by target attacking or blocking creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        
        // If {R} was spent to cast Boros Fury-Shield, it deals damage to that creature's controller equal to the creature's power.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new BorosFuryShieldDamageEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.R), "If {R} was spent to cast {this}, it deals damage to that creature's controller equal to the creature's power"));
    }

    public BorosFuryShield(final BorosFuryShield card) {
        super(card);
    }

    @Override
    public BorosFuryShield copy() {
        return new BorosFuryShield(this);
    }
    
    class BorosFuryShieldDamageEffect extends OneShotEffect {
        BorosFuryShieldDamageEffect() {
            super(Outcome.Damage);
            staticText = "{this} deals damage to that creature's controller equal to the creature's power";
        }

        BorosFuryShieldDamageEffect(final BorosFuryShieldDamageEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
            if (target != null) {
              Player player = game.getPlayer(target.getControllerId());
              if (player != null) {
                int power = target.getPower().getValue();
                player.damage(power, source.getId(), game, false, true);
              }
              
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new BorosFuryShieldDamageEffect(this);
        }
    
    }
}
