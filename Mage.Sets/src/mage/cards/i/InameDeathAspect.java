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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public class InameDeathAspect extends CardImpl {

    public InameDeathAspect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Spirit");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InameDeathAspectEffect(), true));
    }

    public InameDeathAspect(final InameDeathAspect card) {
        super(card);
    }

    @Override
    public InameDeathAspect copy() {
        return new InameDeathAspect(this);
    }
}

class InameDeathAspectEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public InameDeathAspectEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        staticText = "search your library for any number of Spirit cards and put them into your graveyard. If you do, shuffle your library";
    }

    public InameDeathAspectEffect(final InameDeathAspectEffect effect) {
        super(effect);
    }

    @Override
    public InameDeathAspectEffect copy() {
        return new InameDeathAspectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                player.moveCards(new CardsImpl(target.getTargets()), Zone.GRAVEYARD, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}