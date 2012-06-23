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
package mage.sets.newphyrexia;

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public class MeliraSylvokOutcast extends CardImpl<MeliraSylvokOutcast> {

    public MeliraSylvokOutcast(UUID ownerId) {
        super(ownerId, 115, "Melira, Sylvok Outcast", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "NPH";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Scout");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You can't get poison counters.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraSylvokOutcastEffect()));

        // Creatures you control can't have -1/-1 counters placed on them.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraSylvokOutcastEffect2()));

        // Creatures your opponents control lose infect.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraSylvokOutcastEffect3()));

    }

    public MeliraSylvokOutcast(final MeliraSylvokOutcast card) {
        super(card);
    }

    @Override
    public MeliraSylvokOutcast copy() {
        return new MeliraSylvokOutcast(this);
    }
}

class MeliraSylvokOutcastEffect extends ReplacementEffectImpl<MeliraSylvokOutcastEffect> {

    public MeliraSylvokOutcastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "You can't get poison counters";
    }

    public MeliraSylvokOutcastEffect(final MeliraSylvokOutcastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MeliraSylvokOutcastEffect copy() {
        return new MeliraSylvokOutcastEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ADD_COUNTER && event.getData().equals(CounterType.POISON.getName()) && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }

}

class MeliraSylvokOutcastEffect2 extends ReplacementEffectImpl<MeliraSylvokOutcastEffect2> {

    public MeliraSylvokOutcastEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "Creatures you control can't have -1/-1 counters placed on them";
    }

    public MeliraSylvokOutcastEffect2(final MeliraSylvokOutcastEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MeliraSylvokOutcastEffect2 copy() {
        return new MeliraSylvokOutcastEffect2(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ADD_COUNTER && event.getData().equals(CounterType.M1M1.getName())) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm != null && perm.getCardType().contains(CardType.CREATURE) && perm.getControllerId().equals(source.getControllerId()))
                return true;
        }
        return false;
    }

}

class MeliraSylvokOutcastEffect3 extends ContinuousEffectImpl<MeliraSylvokOutcastEffect3> {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public MeliraSylvokOutcastEffect3() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        staticText = "Creatures your opponents control lose infect";
    }

    public MeliraSylvokOutcastEffect3(final MeliraSylvokOutcastEffect3 effect) {
        super(effect);
    }

    @Override
    public MeliraSylvokOutcastEffect3 copy() {
        return new MeliraSylvokOutcastEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (opponents.contains(perm.getControllerId())) {
                perm.getAbilities().remove(InfectAbility.getInstance());
            }
        }
        return true;
    }

}
