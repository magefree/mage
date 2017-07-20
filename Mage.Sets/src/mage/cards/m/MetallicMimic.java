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
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class MetallicMimic extends CardImpl {

    public MetallicMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add("Shapeshifter");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As Metallic Mimic enters the battlefield, choose a creature type.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature), null, EnterEventType.SELF);
        // Metallic Mimic is the chosen type in addition to its other types.
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);

        // Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MetallicMimicReplacementEffect()));

    }

    public MetallicMimic(final MetallicMimic card) {
        super(card);
    }

    @Override
    public MetallicMimic copy() {
        return new MetallicMimic(this);
    }

}

class MetallicMimicReplacementEffect extends ReplacementEffectImpl {

    MetallicMimicReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it";
        setCharacterDefining(true);
    }

    MetallicMimicReplacementEffect(MetallicMimicReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent enteringCreature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (enteringCreature != null && sourcePermanent != null
                && enteringCreature.getControllerId().equals(source.getControllerId())
                && enteringCreature.isCreature()
                && !event.getTargetId().equals(source.getSourceId())) {
            String subtype = (String) game.getState().getValue(sourcePermanent.getId() + "_type");
            return subtype != null && enteringCreature.hasSubtype(subtype, game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public MetallicMimicReplacementEffect copy() {
        return new MetallicMimicReplacementEffect(this);
    }
}
