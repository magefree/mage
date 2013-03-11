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
package mage.sets.morningtide;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Deglamer extends CardImpl<Deglamer> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                                 new CardTypePredicate(CardType.ENCHANTMENT)));
    }
    public Deglamer(UUID ownerId) {
        super(ownerId, 118, "Deglamer", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "MOR";

        this.color.setGreen(true);

        // Choose target artifact or enchantment. Its owner shuffles it into his or her library.
        this.getSpellAbility().addEffect(new DeglamerShuffleIntoLibraryEffect());
        Target target = new TargetPermanent(1,1,filter,true);
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
    }

    public Deglamer(final Deglamer card) {
        super(card);
    }

    @Override
    public Deglamer copy() {
        return new Deglamer(this);
    }
}

class DeglamerShuffleIntoLibraryEffect extends OneShotEffect<DeglamerShuffleIntoLibraryEffect> {

    public DeglamerShuffleIntoLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose target artifact or enchantment. Its owner shuffles it into his or her library";
    }

    public DeglamerShuffleIntoLibraryEffect(final DeglamerShuffleIntoLibraryEffect effect) {
        super(effect);
    }

    @Override
    public DeglamerShuffleIntoLibraryEffect copy() {
        return new DeglamerShuffleIntoLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            if (permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true) ) {
                game.getState().getPlayer(permanent.getOwnerId()).getLibrary().shuffle();
                return true;
            }
        }
        return false;
    }
}
