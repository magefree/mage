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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author LevelX2
 */
public class KuonOgreAscendant extends CardImpl {

    public KuonOgreAscendant(UUID ownerId) {
        super(ownerId, 78, "Kuon, Ogre Ascendant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Ogre");
        this.subtype.add("Monk");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.flipCard = true;
        this.flipCardName = "Kuon's Essence";

        // At the beginning of the end step, if three or more creatures died this turn, flip Kuon, Ogre Ascendant.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new FlipSourceEffect(new KuonsEssenceToken()),
                TargetController.ANY,
                new KuonOgreAscendantCondition(), false), 
                new CreaturesDiedWatcher());
    }

    public KuonOgreAscendant(final KuonOgreAscendant card) {
        super(card);
    }

    @Override
    public KuonOgreAscendant copy() {
        return new KuonOgreAscendant(this);
    }
}

class KuonsEssenceToken extends Token {


    KuonsEssenceToken() {
        super("Kuon's Essence", "");
        supertype.add("Legendary");
        cardType.add(CardType.ENCHANTMENT);

        color.setBlack(true);

        // At the beginning of each player's upkeep, that player sacrifices a creature..
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new SacrificeEffect(new FilterCreaturePermanent(),1,"that player"),
                TargetController.ANY, false, true));
    }
}

class KuonOgreAscendantCondition implements Condition {

    private static final KuonOgreAscendantCondition fInstance = new KuonOgreAscendantCondition();

    public static KuonOgreAscendantCondition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreaturesDiedWatcher watcher = (CreaturesDiedWatcher) game.getState().getWatchers().get("CreaturesDiedWatcher");
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiesThisTurn() > 2;
        }
        return false;
    }

    @Override
    public String toString() {
        return "if three or more creatures died this turn";
    }

}
