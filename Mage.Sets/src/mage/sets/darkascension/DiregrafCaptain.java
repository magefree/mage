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
package mage.sets.darkascension;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class DiregrafCaptain extends CardImpl<DiregrafCaptain> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie");

    static {
        filter.getSubtype().add("Zombie");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public DiregrafCaptain(UUID ownerId) {
        super(ownerId, 135, "Diregraf Captain", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Zombie");
        this.subtype.add("Soldier");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(DeathtouchAbility.getInstance());
        // Other Zombie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Constants.Duration.WhileOnBattlefield, filter, true)));
        // Whenever another Zombie you control dies, target opponent loses 1 life.
        this.addAbility(new DiregrafCaptainTriggeredAbility());
    }

    public DiregrafCaptain(final DiregrafCaptain card) {
        super(card);
    }

    @Override
    public DiregrafCaptain copy() {
        return new DiregrafCaptain(this);
    }
}

class DiregrafCaptainTriggeredAbility extends TriggeredAbilityImpl<DiregrafCaptainTriggeredAbility> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie");

    static {
        filter.getSubtype().add("Zombie");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }
    
    public DiregrafCaptainTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
        this.addTarget(new TargetOpponent());
    }
    
    public DiregrafCaptainTriggeredAbility(final DiregrafCaptainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && !event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Constants.Zone.BATTLEFIELD && zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
                Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (p != null && p.getControllerId().equals(this.controllerId) && filter.match(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DiregrafCaptainTriggeredAbility copy() {
        return new DiregrafCaptainTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another Zombie you control dies, target opponent loses 1 life.";
    }
}