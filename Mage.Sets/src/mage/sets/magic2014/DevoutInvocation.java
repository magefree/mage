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
package mage.sets.magic2014;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DevoutInvocation extends CardImpl<DevoutInvocation> {

    public DevoutInvocation(UUID ownerId) {
        super(ownerId, 16, "Devout Invocation", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{6}{W}");
        this.expansionSetCode = "M14";

        this.color.setWhite(true);

        // Tap any number of untapped creatures you control. Put a 4/4 white Angel creature token with flying onto the battlefield for each creature tapped this way.
        this.getSpellAbility().addEffect(new DevoutInvocationEffect());
        
    }

    public DevoutInvocation(final DevoutInvocation card) {
        super(card);
    }

    @Override
    public DevoutInvocation copy() {
        return new DevoutInvocation(this);
    }
}

class DevoutInvocationEffect extends OneShotEffect<DevoutInvocationEffect> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");
    
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public DevoutInvocationEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Tap any number of untapped creatures you control. Put a 4/4 white Angel creature token with flying onto the battlefield for each creature tapped this way";
    }

    public DevoutInvocationEffect(DevoutInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int tappedAmount = 0;
            TargetPermanent target = new TargetPermanent(0,1,filter, false);
            while (true && controller.isInGame()) {
                target.clearChosen();
                if (target.canChoose(source.getControllerId(), game)) {
                    Map<String, Serializable> options = new HashMap<>();
                    options.put("UI.right.btn.text", "Tapping complete");
                    controller.choose(outcome, target, source.getControllerId(), game, options);
                    if (target.getTargets().size() > 0) {
                        UUID creature = target.getFirstTarget();
                        if (creature != null) {
                            game.getPermanent(creature).tap(game);
                            tappedAmount++;
                        }
                    } else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            if (tappedAmount > 0) {
                AngelToken angelToken = new AngelToken();
                angelToken.putOntoBattlefield(tappedAmount, game, source.getSourceId(), source.getControllerId());
                game.informPlayers(new StringBuilder(controller.getName()).append(" puts ").append(tappedAmount).append(" token").append(tappedAmount != 1 ?"s":"").append(" onto the battlefield").toString());
            }
            return true;
        }
        return false;
    }

    @Override
    public DevoutInvocationEffect copy() {
        return new DevoutInvocationEffect(this);
    }

}
