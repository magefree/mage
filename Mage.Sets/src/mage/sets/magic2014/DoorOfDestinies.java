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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author Plopman
 */
public class DoorOfDestinies extends CardImpl {

    public DoorOfDestinies(UUID ownerId) {
        super(ownerId, 208, "Door of Destinies", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "M14";

        // As Door of Destinies enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Whenever you cast a spell of the chosen type, put a charge counter on Door of Destinies.
        this.addAbility(new AddCounterAbility());
        // Creatures you control of the chosen type get +1/+1 for each charge counter on Door of Destinies.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostCreatureEffectEffect()));
    }

    public DoorOfDestinies(final DoorOfDestinies card) {
        super(card);
    }

    @Override
    public DoorOfDestinies copy() {
        return new DoorOfDestinies(this);
    }
}

class AddCounterAbility extends TriggeredAbilityImpl {

    public AddCounterAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), false);
    }

    public AddCounterAbility(final AddCounterAbility ability) {
        super(ability);
    }

    @Override
    public AddCounterAbility copy() {
        return new AddCounterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent doorOfDestinies = game.getPermanent(getSourceId());
        if (doorOfDestinies != null) {
            String subtype = (String) game.getState().getValue(doorOfDestinies.getId() + "_type");
            if (subtype != null) {
                FilterSpell filter = new FilterSpell();
                filter.add(new ControllerPredicate(TargetController.YOU));
                filter.add(new SubtypePredicate(subtype));
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && filter.match(spell, getSourceId(), getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell of the chosen type, put a charge counter on {source}";
    }
}

class BoostCreatureEffectEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public BoostCreatureEffectEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen type get +1/+1 for each charge counter on {this}";
    }

    public BoostCreatureEffectEffect(final BoostCreatureEffectEffect effect) {
        super(effect);
    }

    @Override
    public BoostCreatureEffectEffect copy() {
        return new BoostCreatureEffectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
            if (subtype != null) {
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (perm.hasSubtype(subtype, game)) {
                        int boost = permanent.getCounters(game).getCount(CounterType.CHARGE);
                        perm.addPower(boost);
                        perm.addToughness(boost);
                    }
                }
            }
        }
        return true;
    }

}
