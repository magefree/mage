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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public class HollowWarrior extends CardImpl {

    public HollowWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hollow Warrior can't attack or block unless you tap an untapped creature you control not declared as an attacking or blocking creature this combat. (This cost is paid as attackers are declared.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HollowWarriorCostToAttackBlockEffect()));

    }
        
    public HollowWarrior(final HollowWarrior card) {
        super(card);
    }

    @Override
    public HollowWarrior copy() {
        return new HollowWarrior(this);
    }
}

class HollowWarriorCostToAttackBlockEffect extends PayCostToAttackBlockEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped creature you control not declared as an attacking or blocking creature");
    static {
        filter.add(Predicates.not(new AttackingPredicate()));
        filter.add(Predicates.not(new BlockingPredicate()));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    HollowWarriorCostToAttackBlockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK_AND_BLOCK,
                new TapTargetCost(new TargetControlledPermanent(filter)));
        staticText = "{this} can't attack or block unless you tap an untapped creature you control not declared as an attacking or blocking creature this combat <i>(This cost is paid as attackers are declared.)</i>";
    }

    HollowWarriorCostToAttackBlockEffect(HollowWarriorCostToAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public HollowWarriorCostToAttackBlockEffect copy() {
        return new HollowWarriorCostToAttackBlockEffect(this);
    }

}
