/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class CanAttackAsThoughItDidntHaveDefenderAllEffect extends AsThoughEffectImpl {

    private final FilterPermanent filter;
    
    public CanAttackAsThoughItDidntHaveDefenderAllEffect(Duration duration) {
        this(duration, new FilterCreaturePermanent());
    }
    
    public CanAttackAsThoughItDidntHaveDefenderAllEffect(Duration duration, FilterPermanent filter) {
        super(AsThoughEffectType.ATTACK, duration, Outcome.Benefit);
        this.filter = filter;
        this.staticText = getText();
    }

    public CanAttackAsThoughItDidntHaveDefenderAllEffect(final CanAttackAsThoughItDidntHaveDefenderAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public CanAttackAsThoughItDidntHaveDefenderAllEffect copy() {
        return new CanAttackAsThoughItDidntHaveDefenderAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(objectId);
        return permanent != null && filter.match(permanent, game);
    }
    
    private String getText() {
        StringBuilder sb = new StringBuilder(filter.getMessage());
        sb.append(" can attack ");
        if (!duration.toString().isEmpty()) {            
            if(Duration.EndOfTurn.equals(duration)) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
            sb.append(" ");
        }
        sb.append("as though they didn't have defender");
        return  sb.toString();
    }
}
