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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author TheElk801
 */
public class MavrenFeinDuskApostle extends CardImpl {

    public MavrenFeinDuskApostle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more nontoken Vampires you control attack, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new MavrenFeinDuskApostleTriggeredAbility());
    }

    public MavrenFeinDuskApostle(final MavrenFeinDuskApostle card) {
        super(card);
    }

    @Override
    public MavrenFeinDuskApostle copy() {
        return new MavrenFeinDuskApostle(this);
    }
}

class MavrenFeinDuskApostleTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken Vampires you control");

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MavrenFeinDuskApostleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new IxalanVampireToken()), false);
    }

    public MavrenFeinDuskApostleTriggeredAbility(final MavrenFeinDuskApostleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MavrenFeinDuskApostleTriggeredAbility copy() {
        return new MavrenFeinDuskApostleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID creatureId : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null && filter.match(creature, game) && creature.getControllerId().equals(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more nontoken Vampires you control attack, " + super.getRule();
    }
}
