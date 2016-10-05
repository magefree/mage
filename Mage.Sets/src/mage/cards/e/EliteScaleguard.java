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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class EliteScaleguard extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");
    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public EliteScaleguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Elite Scaleguard enters the battlefield, bolster 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(2)));
        
        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new EliteScaleguardTapEffect(), false, filter, true);
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        this.addAbility(ability);
    }

    public EliteScaleguard(final EliteScaleguard card) {
        super(card);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof AttacksCreatureYouControlTriggeredAbility) {
            FilterCreaturePermanent filterDefender = new FilterCreaturePermanent("creature defending player controls");
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof EliteScaleguardTapEffect) {
                    filterDefender.add(new ControllerIdPredicate(game.getCombat().getDefendingPlayerId(effect.getTargetPointer().getFirst(game, ability), game)));
                    break;
                }
            }
            ability.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filterDefender);
            ability.addTarget(target);
        }
    }

    @Override
    public EliteScaleguard copy() {
        return new EliteScaleguard(this);
    }
}

class EliteScaleguardTapEffect extends TapTargetEffect {
    
    EliteScaleguardTapEffect() {
        super();
    }
    
    EliteScaleguardTapEffect(final EliteScaleguardTapEffect effect) {
        super(effect);
    }
    
    @Override
    public EliteScaleguardTapEffect copy() {
        return new EliteScaleguardTapEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.tap(game);
            return true;
        }
        return false;
    }
}
