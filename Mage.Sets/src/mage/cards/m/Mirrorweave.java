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
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyApplyToPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class Mirrorweave extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public Mirrorweave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W/U}{W/U}");

        // Each other creature becomes a copy of target nonlegendary creature until end of turn.
        this.getSpellAbility().addEffect(new MirrorWeaveEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    public Mirrorweave(final Mirrorweave card) {
        super(card);
    }

    @Override
    public Mirrorweave copy() {
        return new Mirrorweave(this);
    }
}

class MirrorWeaveEffect extends OneShotEffect {

    public MirrorWeaveEffect() {
        super(Outcome.Copy);
        this.staticText = "Each other creature becomes a copy of target nonlegendary creature until end of turn";
    }

    public MirrorWeaveEffect(final MirrorWeaveEffect effect) {
        super(effect);
    }

    @Override
    public MirrorWeaveEffect copy() {
        return new MirrorWeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();

        if (controller != null) {
            Permanent copyFromCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
            if (copyFromCreature != null) {
                filter.add(Predicates.not(new PermanentIdPredicate(copyFromCreature.getId())));
                for (Permanent copyToCreature : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (copyToCreature != null) {
                        game.copyPermanent(Duration.EndOfTurn, copyFromCreature, copyToCreature.getId(), source, new EmptyApplyToPermanent());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
