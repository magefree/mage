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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AdarkarValkyrie extends CardImpl<AdarkarValkyrie> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    static {
        filter.add(new AnotherPredicate());
    }

    public AdarkarValkyrie(UUID ownerId) {
        super(ownerId, 1, "Adarkar Valkyrie", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "CSP";
        this.supertype.add("Snow");
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {tap}: When target creature other than Adarkar Valkyrie dies this turn, return that card to the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AdarkarValkyrieEffect(), new TapSourceCost());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public AdarkarValkyrie(final AdarkarValkyrie card) {
        super(card);
    }

    @Override
    public AdarkarValkyrie copy() {
        return new AdarkarValkyrie(this);
    }
}

class AdarkarValkyrieEffect extends OneShotEffect<AdarkarValkyrieEffect> {

    public AdarkarValkyrieEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When target creature other than Adarkar Valkyrie dies this turn, return that card to the battlefield under your control";
    }

    public AdarkarValkyrieEffect(final AdarkarValkyrieEffect effect) {
        super(effect);
    }

    @Override
    public AdarkarValkyrieEffect copy() {
        return new AdarkarValkyrieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new AdarkarValkyrieDelayedTriggeredAbility(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return false;
    }
}


class AdarkarValkyrieDelayedTriggeredAbility extends DelayedTriggeredAbility<AdarkarValkyrieDelayedTriggeredAbility> {

    protected FixedTarget fixedTarget;

    public AdarkarValkyrieDelayedTriggeredAbility(FixedTarget fixedTarget) {
        super(new ReturnToBattlefieldUnderYourControlTargetEffect(), Duration.EndOfTurn);
        this.getEffects().get(0).setTargetPointer(fixedTarget);
        this.fixedTarget = fixedTarget;
    }

    public AdarkarValkyrieDelayedTriggeredAbility(final AdarkarValkyrieDelayedTriggeredAbility ability) {
        super(ability);
        this.fixedTarget = ability.fixedTarget;
    }

    @Override
    public AdarkarValkyrieDelayedTriggeredAbility copy() {
        return new AdarkarValkyrieDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            if (fixedTarget.getFirst(game, this).equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When target creature other than Adarkar Valkyrie dies this turn, " + super.getRule();
    }
}

