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
package mage.sets.venservskoth;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author FenrisulfrX
 */
public class SawtoothLoon extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("white or blue creature you control");

    static {
        Predicates.or(
            new ColorPredicate(ObjectColor.WHITE),
            new ColorPredicate(ObjectColor.BLUE));
    }

    public SawtoothLoon(UUID ownerId) {
        super(ownerId, 17, "Sawtooth Loon", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "DDI";
        this.subtype.add("Bird");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sawtooth Loon enters the battlefield, return a white or blue creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));

        // When Sawtooth Loon enters the battlefield, draw two cards, then put two cards from your hand on the bottom of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SawtoothLoonEffect()));
    }

    public SawtoothLoon(final SawtoothLoon card) {
        super(card);
    }

    @Override
    public SawtoothLoon copy() {
        return new SawtoothLoon(this);
    }
}

class SawtoothLoonEffect extends OneShotEffect {

    public SawtoothLoonEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw two cards, then put two cards from your hand on the bottom of your library";
    }

    public SawtoothLoonEffect(final SawtoothLoonEffect effect) {
        super(effect);
    }

    @Override
    public SawtoothLoonEffect copy() {
        return new SawtoothLoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, game);
            putOnLibrary(controller, source, game);
            putOnLibrary(controller, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        player.chooseTarget(Outcome.ReturnToHand, target, source, game);
        Card card = player.getHand().get(target.getFirstTarget(), game);
        if (card != null) {
            player.getHand().remove(card);
            player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, false, false);
        }
        return true;
    }
}
