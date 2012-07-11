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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class MikaeusTheUnhallowed extends CardImpl<MikaeusTheUnhallowed> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Human")));
    }

    public MikaeusTheUnhallowed(UUID ownerId) {
        super(ownerId, 70, "Mikaeus, the Unhallowed", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.expansionSetCode = "DKA";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Cleric");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(IntimidateAbility.getInstance());
        // Whenever a Human deals damage to you, destroy it.
        this.addAbility(new MikaeusTheUnhallowedAbility());
        // Other non-Human creatures you control get +1/+1 and have undying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new UndyingAbility(), Duration.WhileOnBattlefield, filter, true)));
    }

    public MikaeusTheUnhallowed(final MikaeusTheUnhallowed card) {
        super(card);
    }

    @Override
    public MikaeusTheUnhallowed copy() {
        return new MikaeusTheUnhallowed(this);
    }
}

class MikaeusTheUnhallowedAbility extends TriggeredAbilityImpl<MikaeusTheUnhallowedAbility> {

    public MikaeusTheUnhallowedAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    public MikaeusTheUnhallowedAbility(final MikaeusTheUnhallowedAbility ability) {
        super(ability);
    }

    @Override
    public MikaeusTheUnhallowedAbility copy() {
        return new MikaeusTheUnhallowedAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.controllerId)) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.hasSubtype("Human")) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Human deals damage to you, destroy it.";
    }
}
