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
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class CryptChampion extends CardImpl {

    public CryptChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add("Zombie");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Crypt Champion enters the battlefield, each player puts a creature card with converted mana cost 3 or less from his or her graveyard onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CryptChampionEffect()));

        // When Crypt Champion enters the battlefield, sacrifice it unless {R} was spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(new ManaWasSpentCondition(ColoredManaSymbol.R)), false), new ManaSpentToCastWatcher());
    }

    public CryptChampion(final CryptChampion card) {
        super(card);
    }

    @Override
    public CryptChampion copy() {
        return new CryptChampion(this);
    }
}

class CryptChampionEffect extends OneShotEffect {

    CryptChampionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "each player puts a creature card with converted mana cost 3 or less from his or her graveyard onto the battlefield";
    }

    CryptChampionEffect(final CryptChampionEffect effect) {
        super(effect);
    }

    @Override
    public CryptChampionEffect copy() {
        return new CryptChampionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterCard filter = new FilterCreatureCard("creature card with converted mana cost 3 or less from your graveyard");
                    filter.add(new OwnerIdPredicate(playerId));
                    filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
                    Target target = new TargetCardInGraveyard(filter);
                    if (target.canChoose(playerId, game) && player.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            player.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
