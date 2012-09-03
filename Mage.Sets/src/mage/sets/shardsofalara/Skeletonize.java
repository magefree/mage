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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author North
 */
public class Skeletonize extends CardImpl<Skeletonize> {

    public Skeletonize(UUID ownerId) {
        super(ownerId, 114, "Skeletonize", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);

        // Skeletonize deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // When a creature dealt damage this way dies this turn, put a 1/1 black Skeleton creature token onto the battlefield with "{B}: Regenerate this creature."
        this.getSpellAbility().addEffect(new SkeletonizeEffect());
        this.addWatcher(new DamagedByWatcher());
    }

    public Skeletonize(final Skeletonize card) {
        super(card);
    }

    @Override
    public Skeletonize copy() {
        return new Skeletonize(this);
    }
}

class SkeletonizeEffect extends OneShotEffect<SkeletonizeEffect> {

    public SkeletonizeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When a creature dealt damage this way dies this turn, put a 1/1 black Skeleton creature token onto the battlefield with \"{B}: Regenerate this creature\"";
    }

    public SkeletonizeEffect(final SkeletonizeEffect effect) {
        super(effect);
    }

    @Override
    public SkeletonizeEffect copy() {
        return new SkeletonizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SkeletonizeDelayedTriggeredAbility();
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}

class SkeletonizeDelayedTriggeredAbility extends DelayedTriggeredAbility<SkeletonizeDelayedTriggeredAbility> {

    public SkeletonizeDelayedTriggeredAbility() {
        super(new CreateTokenEffect(new SkeletonToken()), Duration.EndOfTurn);
    }

    public SkeletonizeDelayedTriggeredAbility(final SkeletonizeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkeletonizeDelayedTriggeredAbility copy() {
        return new SkeletonizeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", this.getSourceId());
            if (watcher != null) {
                return watcher.damagedCreatures.contains(event.getTargetId());
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a creature dealt damage this way dies this turn, " + super.getRule();
    }
}

class SkeletonToken extends Token {

    SkeletonToken() {
        super("Skeleton", "1/1 black Skeleton creature token with \"{B}: Regenerate this creature.\"");
        this.cardType.add(CardType.CREATURE);
        this.color = ObjectColor.BLACK;
        this.subtype.add("Bat");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}: Regenerate this creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}")));
    }
}
