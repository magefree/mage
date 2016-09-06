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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class AvenMimeomancer extends CardImpl {

    public AvenMimeomancer(UUID ownerId) {
        super(ownerId, 2, "Aven Mimeomancer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Bird");
        this.subtype.add("Wizard");



        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you may put a feather counter on target creature. If you do, that creature has base power and toughness 3/1 and has flying for as long as it has a feather counter on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.FEATHER.createInstance()), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new AvenEffect());
        ability.addEffect(new AvenEffect2());
        this.addAbility(ability);
    }

    public AvenMimeomancer(final AvenMimeomancer card) {
        super(card);
    }

    @Override
    public AvenMimeomancer copy() {
        return new AvenMimeomancer(this);
    }
}

class AvenEffect extends ContinuousEffectImpl {

    public AvenEffect() {
        super(Duration.Custom, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
    }

    public AvenEffect(final AvenEffect effect) {
        super(effect);
    }

    @Override
    public AvenEffect copy() {
        return new AvenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            target.getPower().setValue(3);
            target.getToughness().setValue(1);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent creature = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (creature != null && creature.getCounters(game).getCount(CounterType.FEATHER) < 1) {
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("If you do, that creature has base power and toughness 3/1 and has flying for as long as it has a feather counter on it");
        return sb.toString();
    }
}

class AvenEffect2 extends ContinuousEffectImpl {

    public AvenEffect2() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.BoostCreature);
    }

    public AvenEffect2(final AvenEffect2 effect) {
        super(effect);
    }

    @Override
    public AvenEffect2 copy() {
        return new AvenEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            if (!target.getAbilities().contains(FlyingAbility.getInstance())) {
                target.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent creature = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (creature != null && creature.getCounters(game).getCount(CounterType.FEATHER) < 1) {
            return true;
        }
        return false;
    }
}