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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SetessanTactics extends CardImpl<SetessanTactics> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public SetessanTactics(UUID ownerId) {
        super(ownerId, 140, "Setessan Tactics", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "JOU";

        this.color.setGreen(true);

        // Strive - Setessan Tactics costs G more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{G}"));
        // Until end of turn, any number of target creatures each get +1/+1 and gain "T: This creature fights another target creature."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        Effect effect = new BoostTargetEffect(1,1, Duration.EndOfTurn);
        effect.setText("Until end of turn, any number of target creatures each get +1/+1");
        this.getSpellAbility().addEffect(effect);
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FightTargetSourceEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter, true));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.EndOfTurn,
                "and gain \"T: This creature fights another target creature"));
    }

    public SetessanTactics(final SetessanTactics card) {
        super(card);
    }

    @Override
    public SetessanTactics copy() {
        return new SetessanTactics(this);
    }
}
