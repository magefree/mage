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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public class IncreasingSavagery extends CardImpl<IncreasingSavagery> {

    public IncreasingSavagery(UUID ownerId) {
        super(ownerId, 120, "Increasing Savagery", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");
        this.expansionSetCode = "DKA";

        this.color.setGreen(true);

        // Put five +1/+1 counters on target creature. If Increasing Savagery was cast from a graveyard, put ten +1/+1 counters on that creature instead.
        this.getSpellAbility().addEffect(new IncreasingSavageryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{G}{G}"), Constants.TimingRule.SORCERY));
    }

    public IncreasingSavagery(final IncreasingSavagery card) {
        super(card);
    }

    @Override
    public IncreasingSavagery copy() {
        return new IncreasingSavagery(this);
    }
}

class IncreasingSavageryEffect extends OneShotEffect<IncreasingSavageryEffect> {
        
    public IncreasingSavageryEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "Put five +1/+1 counters on target creature. If Increasing Savagery was cast from a graveyard, put ten +1/+1 counters on that creature instead";
    }

    public IncreasingSavageryEffect(final IncreasingSavageryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 5;
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (spell != null) {
            if (spell.getFromZone() == Constants.Zone.GRAVEYARD) {
                amount = 10;
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public IncreasingSavageryEffect copy() {
        return new IncreasingSavageryEffect(this);
    }

}
