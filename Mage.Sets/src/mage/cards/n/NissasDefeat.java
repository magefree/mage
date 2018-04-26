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
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public class NissasDefeat extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("Forest, green enchantment, or green planeswalker");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.FOREST),
            (Predicates.and(new ColorPredicate(ObjectColor.GREEN), new CardTypePredicate(CardType.ENCHANTMENT))),
            (Predicates.and(new ColorPredicate(ObjectColor.GREEN), new CardTypePredicate(CardType.PLANESWALKER)))));
    }

    public NissasDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Destroy target Forest, green enchantment, or green planeswalker. If that permanent was a Nissa planeswalker, draw a card.
        this.getSpellAbility().addEffect(new NissasDefeatEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public NissasDefeat(final NissasDefeat card) {
        super(card);
    }

    @Override
    public NissasDefeat copy() {
        return new NissasDefeat(this);
    }
}

class NissasDefeatEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.NISSA));
    }

    public NissasDefeatEffect() {
        super(Outcome.Damage);
        this.staticText = "Destroy target Forest, green enchantment, or green planeswalker. If that permanent was a Nissa planeswalker, draw a card.";
    }

    public NissasDefeatEffect(final NissasDefeatEffect effect) {
        super(effect);
    }

    @Override
    public NissasDefeatEffect copy() {
        return new NissasDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        if (permanent != null) {
            permanent.destroy(source.getSourceId(), game, false);

            // If it was a Nissa planeswalker, draw a card
            if (filter.match(permanent, game) && controller != null) {
                controller.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
