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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiderToken;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class GloomwidowsFeast extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GloomwidowsFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");


        // Destroy target creature with flying. If that creature was blue or black, create a 1/2 green Spider creature token with reach.
        this.getSpellAbility().addEffect(new GloomwidowsFeastEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    public GloomwidowsFeast(final GloomwidowsFeast card) {
        super(card);
    }

    @Override
    public GloomwidowsFeast copy() {
        return new GloomwidowsFeast(this);
    }
}

class GloomwidowsFeastEffect extends OneShotEffect {

    boolean applied = false;

    public GloomwidowsFeastEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with flying. If that creature was blue or black, create a 1/2 green Spider creature token with reach";
    }

    public GloomwidowsFeastEffect(final GloomwidowsFeastEffect effect) {
        super(effect);
    }

    @Override
    public GloomwidowsFeastEffect copy() {
        return new GloomwidowsFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            targetCreature.destroy(source.getSourceId(), game, false);
            Permanent destroyedCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
            if (destroyedCreature.getColor(game).isBlue()
                    || destroyedCreature.getColor(game).isBlack()) {
                SpiderToken token = new SpiderToken();
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
