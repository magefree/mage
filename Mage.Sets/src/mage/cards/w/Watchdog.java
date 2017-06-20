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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class Watchdog extends CardImpl {

    public Watchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add("Hound");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Watchdog blocks each turn if able.
        this.getAbilities().add(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield)));
        // As long as Watchdog is untapped, all creatures attacking you get -1/-0.
         this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostAllEffect(-1, 0, Duration.WhileOnBattlefield, new WatchdogFilter(), false), new InvertCondition(SourceTappedCondition.instance),"As long as {this} is untapped, all creatures attacking you get -1/-0")));
    }

    public Watchdog(final Watchdog card) {
        super(card);
    }

    @Override
    public Watchdog copy() {
        return new Watchdog(this);
    }
}


class WatchdogFilter extends FilterAttackingCreature {

    public WatchdogFilter() {
        super("creatures attacking you");
    }


    public WatchdogFilter(final WatchdogFilter filter) {
        super(filter);
    }

    @Override
    public WatchdogFilter copy() {
        return new WatchdogFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if(!super.match(permanent, sourceId, playerId, game)) {
            return false;
        }

        for(CombatGroup group : game.getCombat().getGroups()) {
            for(UUID attacker : group.getAttackers()) {
                if(attacker.equals(permanent.getId())) {
                    UUID defenderId = group.getDefenderId();
                    if(defenderId.equals(playerId)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
