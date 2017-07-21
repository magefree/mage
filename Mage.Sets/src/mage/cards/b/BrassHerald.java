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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public class BrassHerald extends CardImpl {

    public BrassHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Brass Herald enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // When Brass Herald enters the battlefield, reveal the top four cards of your library. Put all creature cards of the chosen type revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BrassHeraldEntersEffect()));

        // Creatures of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, false)));
    }

    public BrassHerald(final BrassHerald card) {
        super(card);
    }

    @Override
    public BrassHerald copy() {
        return new BrassHerald(this);
    }
}

class BrassHeraldEntersEffect extends OneShotEffect {

    public BrassHeraldEntersEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top four cards of your library. Put all creature cards of the chosen type revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public BrassHeraldEntersEffect(final BrassHeraldEntersEffect effect) {
        super(effect);
    }

    @Override
    public BrassHeraldEntersEffect copy() {
        return new BrassHeraldEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("creature cards of the chosen type");
        filter.add(new ChosenSubtypePredicate(source.getSourceId()));
        return new RevealLibraryPutIntoHandEffect(4, filter, Zone.LIBRARY).apply(game, source);
    }
}
