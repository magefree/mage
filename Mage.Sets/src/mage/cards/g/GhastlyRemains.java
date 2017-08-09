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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class GhastlyRemains extends CardImpl {

    public GhastlyRemains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");

        this.subtype.add("Zombie");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));

        // At the beginning of your upkeep, if Ghastly Remains is in your graveyard, you may pay {B}{B}{B}. If you do, return Ghastly Remains to your hand.
        this.addAbility(new GhastlyRemainsTriggeredAbility());

    }

    public GhastlyRemains(final GhastlyRemains card) {
        super(card);
    }

    @Override
    public GhastlyRemains copy() {
        return new GhastlyRemains(this);
    }
}

class GhastlyRemainsTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    public GhastlyRemainsTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl("{B}{B}{B}")), TargetController.YOU, false);
    }

    public GhastlyRemainsTriggeredAbility(GhastlyRemainsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new GhastlyRemainsTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null && controller.getGraveyard().contains(sourceId)) {
            return super.checkInterveningIfClause(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if {source} is in your graveyard, you may pay {B}{B}{B}. If you do, return {source} to your hand";
    }

}
