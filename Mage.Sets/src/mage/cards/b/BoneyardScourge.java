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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author TheElk801
 */
public class BoneyardScourge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Dragon you control");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public BoneyardScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add("Zombie");
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control dies while Boneyard Scourge is in your graveyard, you may pay 1B. If you do, return Boneyard Scourge from your graveyard to the battlefield.
        TriggeredAbility ability = new DiesWhileInGraveyardTriggeredAbility(
                new DoIfCostPaid(new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl("{1}{B}")),
                filter);
        this.addAbility(ability);
    }

    public BoneyardScourge(final BoneyardScourge card) {
        super(card);
    }

    @Override
    public BoneyardScourge copy() {
        return new BoneyardScourge(this);
    }
}

class DiesWhileInGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

    public DiesWhileInGraveyardTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        super(Zone.GRAVEYARD, effect, false);
        this.filter = filter;
    }

    public DiesWhileInGraveyardTriggeredAbility(final DiesWhileInGraveyardTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesWhileInGraveyardTriggeredAbility copy() {
        return new DiesWhileInGraveyardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        for (Zone z : Zone.values()) {
            if (game.getShortLivingLKI(sourceId, z) && !z.equals(Zone.GRAVEYARD)) {
                return false;
            }
        }
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
            if (filter.match(zEvent.getTarget(), sourceId, controllerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " dies while {this} is in your graveyard, " + super.getRule();
    }

}
