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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class GuardianOfTazeem extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GuardianOfTazeem(UUID ownerId) {
        super(ownerId, 78, "Guardian of Tazeem", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Sphinx");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Landfall</i> - Whenever a land enters the battlefield under you control, tap target creature an opponent controls. If that land is an Island, that creature doesn't untap during its controller's next untap step.
        Ability ability = new GuardianOfTazeemTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public GuardianOfTazeem(final GuardianOfTazeem card) {
        super(card);
    }

    @Override
    public GuardianOfTazeem copy() {
        return new GuardianOfTazeem(this);
    }
}

class GuardianOfTazeemTriggeredAbility extends TriggeredAbilityImpl {

    public GuardianOfTazeemTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), false);
        addEffect(new GuardianOfTazeemEffect());
    }

    public GuardianOfTazeemTriggeredAbility(final GuardianOfTazeemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GuardianOfTazeemTriggeredAbility copy() {
        return new GuardianOfTazeemTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.getCardType().contains(CardType.LAND)
                && permanent.getControllerId().equals(getControllerId())) {
            for (Effect effect : getEffects()) {
                if (effect instanceof GuardianOfTazeemEffect) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "<i>Landfall</i> - Whenever a land enters the battlefield under your control, " + super.getRule();
    }
}

class GuardianOfTazeemEffect extends OneShotEffect {

    public GuardianOfTazeemEffect() {
        super(Outcome.Benefit);
        this.staticText = "If that land is an Island, that creature doesn't untap during its controller's next untap step";
    }

    public GuardianOfTazeemEffect(final GuardianOfTazeemEffect effect) {
        super(effect);
    }

    @Override
    public GuardianOfTazeemEffect copy() {
        return new GuardianOfTazeemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (land != null && targetCreature != null) {
            if (land.hasSubtype("Island")) {
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("that creature");
                effect.setTargetPointer(new FixedTarget(targetCreature, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
