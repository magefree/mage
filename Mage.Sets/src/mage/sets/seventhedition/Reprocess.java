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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox

 */
public class Reprocess extends CardImpl {

    public Reprocess(UUID ownerId) {
        super(ownerId, 159, "Reprocess", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "7ED";

        // Sacrifice any number of artifacts, creatures, and/or lands. Draw a card for each permanent sacrificed this way.
        this.getSpellAbility().addEffect(new ReprocessEffect());
    }

    public Reprocess(final Reprocess card) {
        super(card);
    }

    @Override
    public Reprocess copy() {
        return new Reprocess(this);
    }
}

class ReprocessEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, creatures, and/or lands");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE),
            new CardTypePredicate(CardType.LAND)));
    }

    public ReprocessEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of artifacts, creatures, and/or lands. Draw a card for each permanent sacrificed this way.";
    }

    public ReprocessEffect(final ReprocessEffect effect) {
        super(effect);
    }

    @Override
    public ReprocessEffect copy() {
        return new ReprocessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        int amount = 0;
        TargetControlledPermanent toSacrifice = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
        if(player.chooseTarget(Outcome.Sacrifice, toSacrifice, source, game)) {
            for(Object uuid : toSacrifice.getTargets()){
                Permanent permanent = game.getPermanent((UUID)uuid);
                if(permanent != null){
                    permanent.sacrifice(source.getSourceId(), game);
                    amount++;
                }
            }
            player.drawCards(amount, game);
        }
        return true;
    }
}
