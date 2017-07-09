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
package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;


/**
 * @author LevelX2
 */
public class PatronOfTheOrochi extends CardImpl {

    public PatronOfTheOrochi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Spirit");

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Snake offering (You may cast this card any time you could cast an instant by sacrificing a Snake and paying the difference in mana costs between this and the sacrificed Snake. Mana cost includes color.)
        this.addAbility(new OfferingAbility(SubType.SNAKE));

        // {T}: Untap all Forests and all green creatures. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new PatronOfTheOrochiEffect(), new TapSourceCost()));
        
    }

    public PatronOfTheOrochi(final PatronOfTheOrochi card) {
        super(card);
    }

    @Override
    public PatronOfTheOrochi copy() {
        return new PatronOfTheOrochi(this);
    }
}

class PatronOfTheOrochiEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.or( new SubtypePredicate(SubType.FOREST),
                                  Predicates.and(new CardTypePredicate(CardType.CREATURE),
                                                 new ColorPredicate(ObjectColor.GREEN))
                ));
    }

    public PatronOfTheOrochiEffect() {
        super(Outcome.Untap);
        staticText = "Untap all Forests and all green creatures";
    }

    public PatronOfTheOrochiEffect(final PatronOfTheOrochiEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PatronOfTheOrochiEffect copy() {
        return new PatronOfTheOrochiEffect(this);
    }

}