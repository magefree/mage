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
package mage.cards.t;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TriumphOfGerrard extends CardImpl {

    public TriumphOfGerrard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);
        // I, II — Put a +1/+1 counter on target creature you control with the greatest power.
        Ability ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TriumphOfGerrardTargetCreature());
        // III — Target creature you control with the greatest power gains flying, first strike, and lifelink until end of turn.
        ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("Target creature you control with the greatest power gains flying"));
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn).setText(", first strike"));
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn).setText(", and lifelink until end of turn"));
        ability.addTarget(new TriumphOfGerrardTargetCreature());
        this.addAbility(sagaAbility);

    }

    public TriumphOfGerrard(final TriumphOfGerrard card) {
        super(card);
    }

    @Override
    public TriumphOfGerrard copy() {
        return new TriumphOfGerrard(this);
    }
}

class TriumphOfGerrardTargetCreature extends TargetControlledCreaturePermanent {

    public TriumphOfGerrardTargetCreature() {
        super();
        setTargetName("creature you control with the greatest power");
    }

    public TriumphOfGerrardTargetCreature(final TriumphOfGerrardTargetCreature target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            int maxPower = 0;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (permanent.getPower().getValue() > maxPower) {
                    maxPower = permanent.getPower().getValue();
                }
            }
            Permanent targetPermanent = game.getPermanent(id);
            if (targetPermanent != null) {
                return targetPermanent.getPower().getValue() == maxPower;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        int maxPower = 0;
        List<Permanent> activePermanents = game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : activePermanents) {
            if (permanent.getPower().getValue() > maxPower) {
                maxPower = permanent.getPower().getValue();
            }
        }
        for (Permanent permanent : activePermanents) {
            if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                if (permanent.getPower().getValue() == maxPower) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return !possibleTargets(sourceId, sourceControllerId, game).isEmpty();
    }

    @Override
    public TriumphOfGerrardTargetCreature copy() {
        return new TriumphOfGerrardTargetCreature(this);
    }
}
