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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class JaceTheLivingGuildpact extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another target nonland permanent");

    static {
        filter.add(new AnotherPredicate());
    }

    public JaceTheLivingGuildpact(UUID ownerId) {
        super(ownerId, 62, "Jace, the Living Guildpact", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.expansionSetCode = "M15";
        this.subtype.add("Jace");


        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), false));

        // +1: Look at the top two cards of your library. Put one of them into your graveyard.
        Effect effect = new LookLibraryAndPickControllerEffect(
                new StaticValue(2), false, new StaticValue(1), new FilterCard(), Zone.LIBRARY, true, false, false, Zone.GRAVEYARD, false);
        effect.setText("Look at the top two cards of your library. Put one of them into your graveyard");
        this.addAbility(new LoyaltyAbility(effect, 1));

        // -3: Return another target nonland permanent to its owner's hand. 
        LoyaltyAbility ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -8: Each player shuffles his or her hand and graveyard into his or her library. You draw seven cards.
        this.addAbility(new LoyaltyAbility(new JaceTheLivingGuildpactEffect(), -8));

    }

    public JaceTheLivingGuildpact(final JaceTheLivingGuildpact card) {
        super(card);
    }

    @Override
    public JaceTheLivingGuildpact copy() {
        return new JaceTheLivingGuildpact(this);
    }
}

class JaceTheLivingGuildpactEffect extends OneShotEffect {

    public JaceTheLivingGuildpactEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles his or her hand and graveyard into his or her library. You draw seven cards";
    }

    public JaceTheLivingGuildpactEffect(final JaceTheLivingGuildpactEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getHand().getCards(game)) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }                    
                    for (Card card: player.getGraveyard().getCards(game)) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }                       
                    player.shuffleLibrary(game);
                }
            }
            controller.drawCards(7, game);
            return true;

        }
        return false;

    }

    @Override
    public JaceTheLivingGuildpactEffect copy() {
        return new JaceTheLivingGuildpactEffect(this);
    }

}
