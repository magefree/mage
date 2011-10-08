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
package mage.sets.ravnika;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class Phytohydra extends CardImpl<Phytohydra> {

    public Phytohydra(UUID ownerId) {
        super(ownerId, 218, "Phytohydra", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{W}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Plant");
        this.subtype.add("Hydra");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If damage would be dealt to Phytohydra, put that many +1/+1 counters on it instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PhytohydraEffect()));
    }

    public Phytohydra(final Phytohydra card) {
        super(card);
    }

    @Override
    public Phytohydra copy() {
        return new Phytohydra(this);
    }
}

class PhytohydraEffect extends ReplacementEffectImpl<PhytohydraEffect> {
    PhytohydraEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.BoostCreature);
        staticText = "If damage would be dealt to {this}, put that many +1/+1 counters on it instead";
    }

    PhytohydraEffect(final PhytohydraEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageCreatureEvent damageEvent = (DamageCreatureEvent) event;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.addCounters(CounterType.P1P1.createInstance(damageEvent.getAmount()), game);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_CREATURE && event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PhytohydraEffect copy() {
        return new PhytohydraEffect(this);
    }
}
