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
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public class EchoingRuin extends CardImpl {
        private static final FilterPermanent filter = new FilterPermanent("artifact");

    
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public EchoingRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Destroy target artifact and all other artifacts with the same name as that artifact.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new EchoingRuinEffect());
    }

    public EchoingRuin(final EchoingRuin card) {
        super(card);
    }

    @Override
    public EchoingRuin copy() {
        return new EchoingRuin(this);
    }
}

class EchoingRuinEffect extends OneShotEffect {
    EchoingRuinEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact and all other artifacts with the same name as that artifact";
    }

    EchoingRuinEffect(final EchoingRuinEffect effect) {
        super(effect);
    }

    @Override
    public EchoingRuinEffect copy() {
        return new EchoingRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            permanent.destroy(source.getSourceId(), game, false);
            if (!permanent.getName().isEmpty()) { // in case of face down artifact creature
                for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (!perm.getId().equals(permanent.getId()) && perm.getName().equals(permanent.getName()) && perm.isArtifact()) {
                        perm.destroy(source.getSourceId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
