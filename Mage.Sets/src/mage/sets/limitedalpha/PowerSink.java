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
package mage.sets.limitedalpha;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Quercitron
 */
public class PowerSink extends CardImpl {

    public PowerSink(UUID ownerId) {
        super(ownerId, 73, "Power Sink", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{X}{U}");
        this.expansionSetCode = "LEA";


        // Counter target spell unless its controller pays {X}. If he or she doesn't, that player taps all lands with mana abilities he or she controls and empties his or her mana pool.
        this.getSpellAbility().addEffect(new PowerSinkCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public PowerSink(final PowerSink card) {
        super(card);
    }

    @Override
    public PowerSink copy() {
        return new PowerSink(this);
    }
}

class PowerSinkCounterUnlessPaysEffect extends OneShotEffect {

    public PowerSinkCounterUnlessPaysEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell unless its controller pays {X}. If he or she doesn't, that player taps all lands with mana abilities he or she controls and empties his or her mana pool.";
    }

    public PowerSinkCounterUnlessPaysEffect(final PowerSinkCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public PowerSinkCounterUnlessPaysEffect copy() {
        return new PowerSinkCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (player != null && controller != null && sourceObject != null) {
                int amount = source.getManaCostsToPay().getX();
                if (amount > 0) {
                    GenericManaCost cost = new GenericManaCost(amount);
                    StringBuilder sb = new StringBuilder("Pay ").append(cost.getText()).append("?");
                    if (player.chooseUse(Outcome.Benefit, sb.toString(), game)) {
                        if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                            game.informPlayers(new StringBuilder(sourceObject.getName()).append(": additional cost was paid").toString());
                            return true;
                        }
                    }
                    
                    // Counter target spell unless its controller pays {X}
                    if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
                        game.informPlayers(new StringBuilder(sourceObject.getName()).append(": additional cost wasn't paid - countering ").append(spell.getName()).toString());
                    }
                    
                    // that player taps all lands with mana abilities he or she controls...
                    List<Permanent> lands = game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), player.getId(), game);
                    for (Permanent land : lands) {
                        Abilities<Ability> landAbilities = land.getAbilities();
                        for (Ability ability : landAbilities) {
                            if (ability instanceof ManaAbility) {
                                land.tap(game);
                                break;
                            }
                        }
                    }

                    // ...and empties his or her mana pool
                    player.getManaPool().emptyPool(game);
                }
                return true;
            }
        }
        return false;
    }
    
}
