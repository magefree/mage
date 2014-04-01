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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattelfieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MyrServitor extends CardImpl<MyrServitor> {

    public MyrServitor(UUID ownerId) {
        super(ownerId, 139, "Myr Servitor", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Myr");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if Myr Servitor is on the battlefield, each player returns all cards named Myr Servitor from his or her graveyard to the battlefield.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new MyrServitorReturnEffect(), TargetController.YOU, false),
                SourceOnBattelfieldCondition.getInstance(),
                "At the beginning of your upkeep, if {this} is on the battlefield, each player returns all cards named Myr Servitor from his or her graveyard to the battlefield"
        ));
        
    }

    public MyrServitor(final MyrServitor card) {
        super(card);
    }

    @Override
    public MyrServitor copy() {
        return new MyrServitor(this);
    }
}

class MyrServitorReturnEffect extends OneShotEffect<MyrServitorReturnEffect> {
    
    private static final FilterCard filter = new FilterCard("cards named Myr Servitor");
    
    static {
        filter.add(new NamePredicate("Myr Servitor"));
    }
    
    public MyrServitorReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "if {this} is on the battlefield, each player returns all cards named Myr Servitor from his or her graveyard to the battlefield";
    }
    
    public MyrServitorReturnEffect(final MyrServitorReturnEffect effect) {
        super(effect);
    }
    
    @Override
    public MyrServitorReturnEffect copy() {
        return new MyrServitorReturnEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getGraveyard().getCards(filter, game)) {
                        player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                    }
                }
            }
        }
        return false;
    }
}
