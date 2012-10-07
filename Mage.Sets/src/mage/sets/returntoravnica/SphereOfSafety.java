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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterEnchantment;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SphereOfSafety extends CardImpl<SphereOfSafety> {

    public SphereOfSafety (UUID ownerId) {
        super(ownerId, 24, "Sphere of Safety", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");
        this.expansionSetCode = "RTR";

        this.color.setWhite(true);

        // Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SphereOfSafetyReplacementEffect()));

    }

    public SphereOfSafety (final SphereOfSafety card) {
        super(card);
    }

    @Override
    public SphereOfSafety copy() {
        return new SphereOfSafety(this);
    }

}

class SphereOfSafetyReplacementEffect extends ReplacementEffectImpl<SphereOfSafetyReplacementEffect> {

    private static final String effectText = "Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control";
    private static final FilterEnchantment filter = new FilterEnchantment("enchantment you control");
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
    }
    private PermanentsOnBattlefieldCount countEnchantments = new PermanentsOnBattlefieldCount(filter);

    
    SphereOfSafetyReplacementEffect ( ) {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = effectText;
    }

    SphereOfSafetyReplacementEffect ( SphereOfSafetyReplacementEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            Player player = game.getPlayer(event.getPlayerId());
            if ( player != null ) {
                int ce = countEnchantments.calculate(game, source);
                ManaCostsImpl safetyCosts = new ManaCostsImpl("{"+ ce +"}");
                if ( safetyCosts.canPay(source.getSourceId(), event.getPlayerId(), game) &&
                     player.chooseUse(Constants.Outcome.Benefit, "Pay {"+ ce +"} to declare attacker?", game) )
                {
                    if (safetyCosts.payOrRollback(source, game, this.getId(), event.getPlayerId())) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            if (event.getTargetId().equals(source.getControllerId()) ) {
                return true;
            }
            // planeswalker
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())
                                  && permanent.getCardType().contains(CardType.PLANESWALKER)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SphereOfSafetyReplacementEffect copy() {
        return new SphereOfSafetyReplacementEffect(this);
    }

}