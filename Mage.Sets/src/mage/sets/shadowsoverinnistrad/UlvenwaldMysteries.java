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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public class UlvenwaldMysteries extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");
    private static final FilterControlledPermanent filterClue = new FilterControlledPermanent("a Clue");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
        filterClue.add(new SubtypePredicate("Clue"));
    }

    public UlvenwaldMysteries(UUID ownerId) {
        super(ownerId, 236, "Ulvenwald Mysteries", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "SOI";

        // Whenever a nontoken creature you control dies, investigate. <i>(Put a colorless Clue artifact token onto the battlefield with "{2}, Sacrifice this artifact: Draw a card.")</i>
        this.addAbility(new DiesCreatureTriggeredAbility(new InvestigateEffect(), false, filter));

        // Whenever you sacrifice a Clue, put a 1/1 white Human Soldier creature token onto the battlefield.
        this.addAbility(new UlvenwaldMysteriesTriggeredAbility());
    }

    public UlvenwaldMysteries(final UlvenwaldMysteries card) {
        super(card);
    }

    @Override
    public UlvenwaldMysteries copy() {
        return new UlvenwaldMysteries(this);
    }
}

class UlvenwaldMysteriesTriggeredAbility extends TriggeredAbilityImpl {

    public UlvenwaldMysteriesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanSoldierToken()));
        setLeavesTheBattlefieldTrigger(true);
    }

    public UlvenwaldMysteriesTriggeredAbility(final UlvenwaldMysteriesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UlvenwaldMysteriesTriggeredAbility copy() {
        return new UlvenwaldMysteriesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).getSubtype(game).contains("Clue");
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice a Clue, " + super.getRule();
    }
}