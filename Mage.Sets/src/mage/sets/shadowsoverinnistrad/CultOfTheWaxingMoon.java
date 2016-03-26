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
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author LevelX2
 */
public class CultOfTheWaxingMoon extends CardImpl {

    public CultOfTheWaxingMoon(UUID ownerId) {
        super(ownerId, 201, "Cult of the Waxing Moon", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever a permanent you control transforms into a non-Human creature, put a 2/2 green Wolf creature token onto the battlefield.
        this.addAbility(new CultOfTheWaxingMoonAbility());
    }

    public CultOfTheWaxingMoon(final CultOfTheWaxingMoon card) {
        super(card);
    }

    @Override
    public CultOfTheWaxingMoon copy() {
        return new CultOfTheWaxingMoon(this);
    }
}

class CultOfTheWaxingMoonAbility extends TriggeredAbilityImpl {

    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.not(new SubtypePredicate("Human")));
    }

    public CultOfTheWaxingMoonAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()), false);
    }

    public CultOfTheWaxingMoonAbility(final CultOfTheWaxingMoonAbility ability) {
        super(ability);
    }

    @Override
    public CultOfTheWaxingMoonAbility copy() {
        return new CultOfTheWaxingMoonAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent you control transforms into a non-Human creature, " + super.getRule();
    }
}
