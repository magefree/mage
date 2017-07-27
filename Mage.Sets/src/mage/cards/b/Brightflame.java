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
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Dilnu
 */
public class Brightflame extends CardImpl {

    public Brightflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}{W}{W}");

        // Radiance - Brightflame deals X damage to target creature and each other creature that shares a color with it. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new BrightflameEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    public Brightflame(final Brightflame card) {
        super(card);
    }

    @Override
    public Brightflame copy() {
        return new Brightflame(this);
    }
}

class BrightflameEffect extends OneShotEffect {

    static final FilterPermanent filter = new FilterPermanent("creature");
    protected DynamicValue amount;

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    BrightflameEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "{this} deals X damage to target creature and each other creature that shares a color with it. You gain life equal to the damage dealt this way.";
    }

    BrightflameEffect(final BrightflameEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        int damageDealt = 0;
        if (target != null) {
            ObjectColor color = target.getColor(game);
            damageDealt += target.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
            for (Permanent p : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (!target.getId().equals(p.getId()) && p.getColor(game).shares(color)) {
                    damageDealt += p.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
                }
            }
            
            Player you = game.getPlayer(source.getControllerId());
            if (you != null && damageDealt > 0) {
                you.gainLife(damageDealt, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public BrightflameEffect copy() {
        return new BrightflameEffect(this);
    }
}