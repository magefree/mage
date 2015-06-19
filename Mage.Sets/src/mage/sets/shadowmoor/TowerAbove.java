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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class TowerAbove extends CardImpl {

    public TowerAbove(UUID ownerId) {
        super(ownerId, 131, "Tower Above", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2/G}{2/G}{2/G}");
        this.expansionSetCode = "SHM";


        // <i>({2G} can be paid with any two mana or with {G}. This card's converted mana cost is 6.)</i>
        // Until end of turn, target creature gets +4/+4 and gains trample, wither, and "When this creature attacks, target creature blocks it this turn if able."
        this.getSpellAbility().addEffect(new TowerAboveEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public TowerAbove(final TowerAbove card) {
        super(card);
    }

    @Override
    public TowerAbove copy() {
        return new TowerAbove(this);
    }
}

class TowerAboveEffect extends OneShotEffect {

    public TowerAboveEffect() {
        super(Outcome.BoostCreature);
        staticText = "Until end of turn, target creature gets +4/+4 and gains trample, wither, and \"When this creature attacks, target creature blocks it this turn if able.";
    }

    public TowerAboveEffect(final TowerAboveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }

        ContinuousEffect effect = new BoostTargetEffect(4, 4, Duration.EndOfTurn);
        ContinuousEffect effect2 = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        ContinuousEffect effect3 = new GainAbilityTargetEffect(WitherAbility.getInstance(), Duration.EndOfTurn);
        ContinuousEffect effect4 = new GainAbilityTargetEffect(new TowerAboveTriggeredAbility(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getId()));
        effect2.setTargetPointer(new FixedTarget(target.getId()));
        effect3.setTargetPointer(new FixedTarget(target.getId()));
        effect4.setTargetPointer(new FixedTarget(target.getId()));
        effect4.setText("");
        game.addEffect(effect, source);
        game.addEffect(effect2, source);
        game.addEffect(effect3, source);
        game.addEffect(effect4, source);
        return true;
    }

    @Override
    public TowerAboveEffect copy() {
        return new TowerAboveEffect(this);
    }
}

class TowerAboveTriggeredAbility extends TriggeredAbilityImpl {

    public TowerAboveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn), false);
    }

    public TowerAboveTriggeredAbility(final TowerAboveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent();
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature attacks, target creature blocks it this turn if able.";
    }

    @Override
    public TowerAboveTriggeredAbility copy() {
        return new TowerAboveTriggeredAbility(this);
    }
}
