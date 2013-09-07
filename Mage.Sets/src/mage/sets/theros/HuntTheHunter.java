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
package mage.sets.theros;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HuntTheHunter extends CardImpl<HuntTheHunter> {

    private static final FilterControlledCreaturePermanent filterControlledGreen = new FilterControlledCreaturePermanent("green creature you control");
    private static final FilterCreaturePermanent filterOpponentGreen = new FilterCreaturePermanent("green creature an opponent controls");
    static {
        filterControlledGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterOpponentGreen.add(new ControllerPredicate(TargetController.OPPONENT));
        filterOpponentGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public HuntTheHunter(UUID ownerId) {
        super(ownerId, 157, "Hunt the Hunter", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "THS";

        this.color.setGreen(true);

        // Target green creature you control gets +2/+2 until end of turn. It fights target green creature an opponent controls.
        Effect effect = new BoostTargetEffect(2,2, Duration.EndOfTurn);
        effect.setApplyEffectsAfter();
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(1,1,filterControlledGreen, false, true));

        effect = new FightTargetsEffect();
        effect.setText("It fights target green creature an opponent controls");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));
        Target target = new TargetCreaturePermanent(filterOpponentGreen);
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
    }

    public HuntTheHunter(final HuntTheHunter card) {
        super(card);
    }

    @Override
    public HuntTheHunter copy() {
        return new HuntTheHunter(this);
    }
}
