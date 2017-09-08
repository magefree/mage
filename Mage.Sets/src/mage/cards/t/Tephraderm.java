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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public class Tephraderm extends CardImpl {

    public Tephraderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever a creature deals damage to Tephraderm, Tephraderm deals that much damage to that creature.
        this.addAbility(new TephradermCreatureDamageTriggeredAbility());

        // Whenever a spell deals damage to Tephraderm, Tephraderm deals that much damage to that spell's controller.
        this.addAbility(new TephradermSpellDamageTriggeredAbility());
    }

    public Tephraderm(final Tephraderm card) {
        super(card);
    }

    @Override
    public Tephraderm copy() {
        return new Tephraderm(this);
    }
}

class TephradermCreatureDamageTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent FILTER_CREATURE = new FilterCreaturePermanent();

    public TephradermCreatureDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(0));
    }

    public TephradermCreatureDamageTriggeredAbility(final TephradermCreatureDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }

        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (sourcePermanent != null && FILTER_CREATURE.match(sourcePermanent, getSourceId(), getControllerId(), game)) {
            for (Effect effect : getEffects()) {
                if (effect instanceof DamageTargetEffect) {
                    effect.setTargetPointer(new FixedTarget(sourcePermanent.getId()));
                    ((DamageTargetEffect) effect).setAmount(new StaticValue(event.getAmount()));
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public TriggeredAbility copy() {
        return new TephradermCreatureDamageTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals damage to {this}, {this} deals that much damage to that creature.";
    }
}

class TephradermSpellDamageTriggeredAbility extends TriggeredAbilityImpl {

    public TephradermSpellDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(0));
    }

    public TephradermSpellDamageTriggeredAbility(final TephradermSpellDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }

        StackObject sourceSpell = game.getStack().getStackObject(event.getSourceId());
        if (sourceSpell != null && StaticFilters.FILTER_SPELL.match(sourceSpell, getSourceId(), getControllerId(), game)) {
            for (Effect effect : getEffects()) {
                if (effect instanceof DamageTargetEffect) {
                    effect.setTargetPointer(new FixedTarget(sourceSpell.getControllerId()));
                    ((DamageTargetEffect) effect).setAmount(new StaticValue(event.getAmount()));
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public TriggeredAbility copy() {
        return new TephradermSpellDamageTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a spell deals damage to {this}, {this} deals that much damage to that spell's controller.";
    }
}
