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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AuntiesSnitch extends CardImpl<AuntiesSnitch> {

    public AuntiesSnitch(UUID ownerId) {
        super(ownerId, 72, "Auntie's Snitch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Auntie's Snitch can't block.
        this.addAbility(new CantBlockAbility());
        // Prowl {1}{B}
        this.addAbility(new ProwlAbility(this, "{1}{B}"));
        // Whenever a Goblin or Rogue you control deals combat damage to a player, if Auntie's Snitch is in your graveyard, you may return Auntie's Snitch to your hand.
        this.addAbility(new AuntiesSnitchTriggeredAbility());
    }

    public AuntiesSnitch(final AuntiesSnitch card) {
        super(card);
    }

    @Override
    public AuntiesSnitch copy() {
        return new AuntiesSnitch(this);
    }
}

class AuntiesSnitchTriggeredAbility extends TriggeredAbilityImpl<AuntiesSnitchTriggeredAbility> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Goblin or Rogue you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.or(new SubtypePredicate("Goblin"), new SubtypePredicate("Rogue")));
    }

    public AuntiesSnitchTriggeredAbility() {
        super(Zone.GRAVEYARD, new ReturnToHandSourceEffect(), true);
    }

    public AuntiesSnitchTriggeredAbility(final AuntiesSnitchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AuntiesSnitchTriggeredAbility copy() {
        return new AuntiesSnitchTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && filter.match(p, getSourceId(), getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Goblin or Rogue you control deals combat damage to a player, if {this} is in your graveyard, you may return {this} to your hand.";
    }
}
