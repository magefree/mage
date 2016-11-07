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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.d.DarthVader;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class AnakinSkywalker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public AnakinSkywalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{B}{R}");
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Sith");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.transformable = true;
        this.secondSideCardClazz = DarthVader.class;

        // Whenever another creature dies, put a +1/+1 counter on Anakin Skywalker.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));

        // Sacrifice another creature: Target creature gets -1/-1 until end of turn. Activate this ability only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn), new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // If Anakin Skywalker would die, regenerate and transform him instead.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnakinSkywalkerEffect()));

    }

    public AnakinSkywalker(final AnakinSkywalker card) {
        super(card);
    }

    @Override
    public AnakinSkywalker copy() {
        return new AnakinSkywalker(this);
    }
}

class AnakinSkywalkerEffect extends ReplacementEffectImpl {

    AnakinSkywalkerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Transform);
        staticText = "If {this} would die, regenerate and transform him instead";
    }

    AnakinSkywalkerEffect(final AnakinSkywalkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            permanent.regenerate(source.getSourceId(), game);
            return new TransformSourceEffect(true).apply(game, source);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) && ((ZoneChangeEvent) event).isDiesEvent();
    }

    @Override
    public AnakinSkywalkerEffect copy() {
        return new AnakinSkywalkerEffect(this);
    }
}
