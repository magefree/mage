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
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox
 */
public class YavimayaDryad extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("a Forest card");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public YavimayaDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add("Dryad");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
        // When Yavimaya Dryad enters the battlefield, you may search your library for a Forest card and put it onto the battlefield tapped under target player's control. If you do, shuffle your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new YavimayaDryadEffect(new TargetCardInLibrary(filter)), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public YavimayaDryad(final YavimayaDryad card) {
        super(card);
    }

    @Override
    public YavimayaDryad copy() {
        return new YavimayaDryad(this);
    }
}

class YavimayaDryadEffect extends SearchEffect {

    public YavimayaDryadEffect(TargetCardInLibrary target) {
        super(target, Outcome.PutLandInPlay);
        staticText = "you may search your library for a Forest card and put it onto the battlefield tapped under target player's control. If you do, shuffle your library";
    }

    public YavimayaDryadEffect(final YavimayaDryadEffect effect) {
        super(effect);
    }

    @Override
    public YavimayaDryadEffect copy() {
        return new YavimayaDryadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        if (controller.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                targetPlayer.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                        Zone.BATTLEFIELD, source, game, true, false, false, null);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
