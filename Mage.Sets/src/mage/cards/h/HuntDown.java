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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class HuntDown extends CardImpl {
    
    private static final FilterCreaturePermanent filterMustBlock = new FilterCreaturePermanent("Creature that must block");
    private static final FilterCreaturePermanent filterToBeBlocked = new FilterCreaturePermanent("Creature that is to be blocked");
    
    

    public HuntDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");
        

        // Target creature blocks target creature this turn if able.
        this.getSpellAbility().addEffect(new HuntDownEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterMustBlock));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterToBeBlocked));
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
        
    }

    public HuntDown(final HuntDown card) {
        super(card);
    }

    @Override
    public HuntDown copy() {
        return new HuntDown(this);
    }
}

class HuntDownEffect extends RequirementEffect {

    public HuntDownEffect() {
        this(Duration.EndOfTurn);
    }

    public HuntDownEffect(Duration duration) {
        super(duration);
        staticText = "Target creature blocks target creature this turn if able";
    }

    public HuntDownEffect(final HuntDownEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getTargets().get(0).getFirstTarget())) {
            Permanent blocker = game.getPermanent(source.getTargets().get(0).getFirstTarget());
            if (blocker != null 
                    && blocker.canBlock(source.getTargets().get(1).getFirstTarget(), game)) {              
                Permanent attacker = (Permanent) game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (attacker != null) {
                    BlockedAttackerWatcher blockedAttackerWatcher = (BlockedAttackerWatcher) game.getState().getWatchers().get(BlockedAttackerWatcher.class.getSimpleName());
                    if (blockedAttackerWatcher != null 
                            && blockedAttackerWatcher.creatureHasBlockedAttacker(attacker, blocker, game)) {
                        // has already blocked this turn, so no need to do again
                        return false;
                    }                
                    return true;
                } else {
                    discard();
                }
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {  
        return source.getTargets().get(1).getFirstTarget();
    }

    @Override
    public HuntDownEffect copy() {
        return new HuntDownEffect(this);
    }

}
