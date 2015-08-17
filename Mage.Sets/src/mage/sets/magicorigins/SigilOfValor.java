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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import static mage.filter.predicate.permanent.ControllerControlsIslandPredicate.filter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SigilOfValor extends CardImpl {

    public SigilOfValor(UUID ownerId) {
        super(ownerId, 239, "Sigil of Valor", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ORI";
        this.subtype.add("Equipment");

        // Whenever equipped creature attacks alone, it gets +1/+1 until end of turn for each other creature you control.
        this.addAbility(new SigilOfValorTriggeredAbility(new SigilOfValorCount()));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    public SigilOfValor(final SigilOfValor card) {
        super(card);
    }

    @Override
    public SigilOfValor copy() {
        return new SigilOfValor(this);
    }
}

class SigilOfValorTriggeredAbility extends TriggeredAbilityImpl {

    public SigilOfValorTriggeredAbility(DynamicValue boostValue) {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(boostValue, boostValue, Duration.EndOfTurn));
    }

    public SigilOfValorTriggeredAbility(final SigilOfValorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SigilOfValorTriggeredAbility copy() {
        return new SigilOfValorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getActivePlayerId().equals(getControllerId())) {
            if (game.getCombat().attacksAlone()) {
                Permanent equipment = game.getPermanent(getSourceId());
                UUID attackerId = game.getCombat().getAttackers().get(0);
                if (equipment != null && equipment.getAttachedTo() == attackerId) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(attackerId));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks alone, it gets +1/+1 until end of turn for each other creature you control.";
    }

}

class SigilOfValorCount implements DynamicValue {

    public SigilOfValorCount() {
    }

    public SigilOfValorCount(final SigilOfValorCount dynamicValue) {
        super();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            FilterPermanent filterPermanent = new FilterControlledCreaturePermanent();
            filterPermanent.add(Predicates.not(new CardIdPredicate(equipment.getAttachedTo())));
            return game.getBattlefield().count(filterPermanent, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new SigilOfValorCount(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
