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
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SubterraneanTremorsLizardToken;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SubterraneanTremors extends CardImpl {

    public SubterraneanTremors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Subterranean Tremors deals X damage to each creature without flying. 
        // If X is 4 or more, destroy all artifacts. 
        // If X is 8 or more, create an 8/8 red Lizard creature token.
        this.getSpellAbility().addEffect(new SubterraneanTremorsEffect());
    }

    public SubterraneanTremors(final SubterraneanTremors card) {
        super(card);
    }

    @Override
    public SubterraneanTremors copy() {
        return new SubterraneanTremors(this);
    }
}

class SubterraneanTremorsEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("creature without flying");
    private static final FilterArtifactPermanent filterArtifacts = new FilterArtifactPermanent("all artifacts");

    static {
        filterCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SubterraneanTremorsEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to each creature without flying. If X is 4 or more, destroy all artifacts. If X is 8 or more, create an 8/8 red Lizard creature token.";
    }

    public SubterraneanTremorsEffect(final SubterraneanTremorsEffect effect) {
        super(effect);
    }

    @Override
    public SubterraneanTremorsEffect copy() {
        return new SubterraneanTremorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        int damage = source.getManaCostsToPay().getX();
        UUID sourceId = source.getSourceId();
        UUID controllerId = source.getControllerId();

        // X damage to each creature without flying
        List<Permanent> creaturePermanents = game.getBattlefield().getActivePermanents(filterCreatures, controllerId, game);
        for (Permanent permanent : creaturePermanents) {
            permanent.damage(damage, sourceId, game, false, true);
        }

        // X 4 or more: destroy all artifacts
        if (damage >= 4) {
            List<Permanent> artifactPermanents = game.getBattlefield().getActivePermanents(filterArtifacts, controllerId, game);
            for (Permanent permanent : artifactPermanents) {
                permanent.destroy(permanent.getId(), game, false);
            }
        }
        // X 8 or more: create an 8/8 red lizard creature token on the battlefield
        if (damage >= 8) {
            SubterraneanTremorsLizardToken lizardToken = new SubterraneanTremorsLizardToken();
            lizardToken.putOntoBattlefield(1, game, sourceId, controllerId);
        }

        return true;
    }
}
