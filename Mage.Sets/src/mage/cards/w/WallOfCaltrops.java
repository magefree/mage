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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class WallOfCaltrops extends CardImpl {

    public WallOfCaltrops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Caltrops blocks a creature, if at least one other Wall creature is blocking that creature and no non-Wall creatures are blocking that creature, Wall of Caltrops gains banding until end of turn.
        this.addAbility(new WallOfCaltropsAbility());
    }

    public WallOfCaltrops(final WallOfCaltrops card) {
        super(card);
    }

    @Override
    public WallOfCaltrops copy() {
        return new WallOfCaltrops(this);
    }
}

class WallOfCaltropsAbility extends BlocksTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creature");

    static {
        filter.add(new SubtypePredicate(SubType.WALL));
    }

    public WallOfCaltropsAbility() {
        super(new GainAbilitySourceEffect(BandingAbility.getInstance(), Duration.EndOfTurn), false, false);
    }

    public WallOfCaltropsAbility(WallOfCaltropsAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null
                    && targetPermanent.isCreature()) {
                CombatGroup group = game.getCombat().findGroup(targetPermanent.getId());
                if (group != null) {
                    for (UUID blockerId : group.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null) {
                            if (!filter.match(blocker, game)) {
                                return false;
                            }
                        }
                    }
                    return group.getBlockers().size() > 1 && group.getBlockers().contains(sourceId);
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks a creature, if at least one other Wall creature is blocking that creature and no non-Wall creatures are blocking that creature, {this} gains banding until end of turn.";
    }

    @Override
    public WallOfCaltropsAbility copy() {
        return new WallOfCaltropsAbility(this);
    }
}
