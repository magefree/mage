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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class ConsumingFerocity extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.WALL)));
    }

    public ConsumingFerocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant non-Wall creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 0, Duration.WhileOnBattlefield)));

        // At the beginning of your upkeep, put a +1/+0 counter on enchanted creature. If that creature has three or more +1/+0 counters on it, it deals damage equal to its power to its controller, then destroy that creature and it can't be regenerated.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConsumingFerocityEffect(), TargetController.YOU, false));
    }

    public ConsumingFerocity(final ConsumingFerocity card) {
        super(card);
    }

    @Override
    public ConsumingFerocity copy() {
        return new ConsumingFerocity(this);
    }
}

class ConsumingFerocityEffect extends OneShotEffect {

    public ConsumingFerocityEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+0 counter on enchanted creature. If that creature has three or more +1/+0 counters on it, it deals damage equal to its power to its controller, then destroy that creature and it can't be regenerated";
    }

    public ConsumingFerocityEffect(final ConsumingFerocityEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingFerocityEffect copy() {
        return new ConsumingFerocityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            Effect effect = new AddCountersAttachedEffect(CounterType.P1P0.createInstance(), "enchanted creature");
            effect.apply(game, source);
            if (creature.getCounters(game).getCount(CounterType.P1P0) > 2) {
                Player player = game.getPlayer(creature.getControllerId());
                if (player != null) {
                    player.damage(creature.getPower().getValue(), creature.getId(), game, false, true);
                }
                effect = new DestroyTargetEffect(true);
                effect.setTargetPointer(new FixedTarget(creature, game));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
