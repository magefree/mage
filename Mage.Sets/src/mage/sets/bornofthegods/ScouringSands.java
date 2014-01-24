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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ScryEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ScouringSands extends CardImpl<ScouringSands> {

    public ScouringSands(UUID ownerId) {
        super(ownerId, 110, "Scouring Sands", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "BNG";

        this.color.setRed(true);

        // Scouring Sands deals 1 damage to each creature your opponents control. Scry 1.
        this.getSpellAbility().addEffect(new ScouringSandsDamageEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1));
        
    }

    public ScouringSands(final ScouringSands card) {
        super(card);
    }

    @Override
    public ScouringSands copy() {
        return new ScouringSands(this);
    }
}

class ScouringSandsDamageEffect extends OneShotEffect<ScouringSandsDamageEffect> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ScouringSandsDamageEffect() {
        super(Outcome.GainLife);
        staticText = "{this} deals 1 damage to each creature your opponents control";
    }

    public ScouringSandsDamageEffect(ScouringSandsDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (creature != null) {
                creature.damage(1, source.getSourceId(), game, false, false);
            }
        }
        return true;
    }

    @Override
    public ScouringSandsDamageEffect copy() {
        return new ScouringSandsDamageEffect(this);
    }

}
