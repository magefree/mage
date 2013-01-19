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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CipherEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class StolenIdentity extends CardImpl<StolenIdentity> {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
    }
    
    public StolenIdentity(UUID ownerId) {
        super(ownerId, 53, "Stolen Identity", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);

        // Put a token onto the battlefield that's a copy of target artifact or creature.
        this.getSpellAbility().addEffect(new StolenIdentityEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    public StolenIdentity(final StolenIdentity card) {
        super(card);
    }

    @Override
    public StolenIdentity copy() {
        return new StolenIdentity(this);
    }
}


class StolenIdentityEffect extends OneShotEffect<StolenIdentityEffect> {
    

    
    public StolenIdentityEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "Put a token onto the battlefield that's a copy of target artifact or creature";
    }

    public StolenIdentityEffect(final StolenIdentityEffect effect) {
        super(effect);
    }

    @Override
    public StolenIdentityEffect copy() {
        return new StolenIdentityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);

            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            
            return true;
        }

        return false;
    }
}
