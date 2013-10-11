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
package mage.sets.heroesvsmonsters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class DeusOfCalamity extends CardImpl<DeusOfCalamity> {

    public DeusOfCalamity(UUID ownerId) {
        super(ownerId, 54, "Deus of Calamity", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R/G}{R/G}{R/G}{R/G}{R/G}");
        this.expansionSetCode = "DDL";
        this.subtype.add("Spirit");
        this.subtype.add("Avatar");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Deus of Calamity deals 6 or more damage to an opponent, destroy target land that player controls.
        this.addAbility(new DeusOfCalamityTriggeredAbility());
    }

    public DeusOfCalamity(final DeusOfCalamity card) {
        super(card);
    }

    @Override
    public DeusOfCalamity copy() {
        return new DeusOfCalamity(this);
    }
}

class DeusOfCalamityTriggeredAbility extends TriggeredAbilityImpl<DeusOfCalamityTriggeredAbility> {

    public DeusOfCalamityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    public DeusOfCalamityTriggeredAbility(final DeusOfCalamityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeusOfCalamityTriggeredAbility copy() {
        return new DeusOfCalamityTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.DAMAGED_PLAYER)
                && event.getSourceId().equals(this.getSourceId())
                && event.getAmount() > 5
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            FilterLandPermanent filter = new FilterLandPermanent("land of the damaged player");
            filter.add(new ControllerIdPredicate(event.getTargetId()));
            Target target = new TargetLandPermanent(filter);
            this.getTargets().clear();
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Deus of Calamity deals 6 or more damage to an opponent, destroy target land that player controls.";
    }
}
