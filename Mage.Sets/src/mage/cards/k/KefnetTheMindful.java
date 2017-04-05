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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class KefnetTheMindful extends CardImpl {

    public KefnetTheMindful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("God");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Kefnet the Mindful can't attack or block unless you have seven or more cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KefnetTheMindfulRestrictionEffect()));

        // {3}{U}: Draw a card, then you may return a land you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KefnetTheMindfulEffect(), new ManaCostsImpl("{3}{U}"));
        this.addAbility(ability);

    }

    public KefnetTheMindful(final KefnetTheMindful card) {
        super(card);
    }

    @Override
    public KefnetTheMindful copy() {
        return new KefnetTheMindful(this);
    }
}

class KefnetTheMindfulRestrictionEffect extends RestrictionEffect {

    public KefnetTheMindfulRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you have seven or more cards in your hand";
    }

    public KefnetTheMindfulRestrictionEffect(final KefnetTheMindfulRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public KefnetTheMindfulRestrictionEffect copy() {
        return new KefnetTheMindfulRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                return (controller.getHand().size() < 7);
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}

class KefnetTheMindfulEffect extends OneShotEffect {

    KefnetTheMindfulEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card, then you may return a land you control to its owner's hand";
    }

    KefnetTheMindfulEffect(final KefnetTheMindfulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledPermanent filterControlledLand = new FilterControlledPermanent("land you control");
        filterControlledLand.add(new CardTypePredicate(CardType.LAND));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, game);
            if (controller.chooseUse(Outcome.AIDontUseIt, "Do you want to return a land you control to its owner's hand?", null, "Yes", "No", source, game)) {
                Effect effect = new ReturnToHandChosenControlledPermanentEffect(filterControlledLand);
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public KefnetTheMindfulEffect copy() {
        return new KefnetTheMindfulEffect(this);
    }
}
