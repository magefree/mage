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
package mage.sets.legions;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class ScionOfDarkness extends CardImpl<ScionOfDarkness> {

    public ScionOfDarkness(UUID ownerId) {
        super(ownerId, 79, "Scion of Darkness", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Avatar");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Scion of Darkness deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ScionOfDarknessEffect(), true, true);
        this.addAbility(ability);

        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{3}")));
    }

    public ScionOfDarkness(final ScionOfDarkness card) {
        super(card);
    }

    @Override
    public ScionOfDarkness copy() {
        return new ScionOfDarkness(this);
    }
}

class ScionOfDarknessEffect extends OneShotEffect<ScionOfDarknessEffect> {

    public ScionOfDarknessEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "you may put target creature card from that player's graveyard onto the battlefield under your control";
    }

    public ScionOfDarknessEffect(final ScionOfDarknessEffect effect) {
        super(effect);
    }

    @Override
    public ScionOfDarknessEffect copy() {
        return new ScionOfDarknessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player you = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard("creature in that player's graveyard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        if (target.canChoose(source.getSourceId(), you.getId(), game)) {
            if (you.chooseTarget(Outcome.PutCreatureInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    card.putOntoBattlefield(game, Constants.Zone.GRAVEYARD, id, you.getId());
                    return true;
                }
            }
        }
        return false;
    }
}
