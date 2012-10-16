
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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SkymarkRoc extends CardImpl<SkymarkRoc> {

    public SkymarkRoc(UUID ownerId) {
        super(ownerId, 196, "Skymark Roc", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Bird");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skymark Roc attacks, you may return target creature defending player controls with toughness 2 or less to its owner's hand.
        this.addAbility(new SkymarkRocAbility());
    }

    public SkymarkRoc(final SkymarkRoc card) {
        super(card);
    }

    @Override
    public SkymarkRoc copy() {
        return new SkymarkRoc(this);
    }
}

class SkymarkRocAbility extends TriggeredAbilityImpl<SkymarkRocAbility> {

    public SkymarkRocAbility() {
        super(Constants.Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
    }

    public SkymarkRocAbility(final SkymarkRocAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls with toughness 2 or less");
            UUID defenderId = game.getCombat().getDefendingPlayer(sourceId);
            filter.add(new ControllerIdPredicate(defenderId));
            filter.add(new ToughnessPredicate(Filter.ComparisonType.LessThan, 3));

            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            target.setRequired(true);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, you may return target creature defending player controls with toughness 2 or less to its owner's hand.";
    }

    @Override
    public SkymarkRocAbility copy() {
        return new SkymarkRocAbility(this);
    }
}