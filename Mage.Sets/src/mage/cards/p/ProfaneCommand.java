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
package mage.cards.p;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ProfaneCommand extends CardImpl {

    public ProfaneCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");


        DynamicValue xValue = new ManacostVariableValue();
        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // * Target player loses X life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(xValue));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Return target creature card with converted mana cost X or less from your graveyard to the battlefield.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card with converted mana cost X or less from your graveyard")));
        this.getSpellAbility().addMode(mode);

        // * Target creature gets -X/-X until end of turn.
        DynamicValue minusValue = new SignInversionDynamicValue(xValue);
        mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(minusValue, minusValue, Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // * Up to X target creatures gain fear until end of turn.
        mode = new Mode();
        Effect effect = new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Up to X target creatures gain fear until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addMode(mode);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        // adjust targets is called for every selected mode
        Mode mode = ability.getModes().getMode();
        for (Effect effect : mode.getEffects()) {
            if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                mode.getTargets().clear();
                int xValue = ability.getManaCostsToPay().getX();
                FilterCard filter = new FilterCreatureCard("creature card with converted mana cost " + xValue + " or less from your graveyard");
                filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
                mode.getTargets().add(new TargetCardInYourGraveyard(filter));
            }
            if (effect instanceof GainAbilityTargetEffect) {
                mode.getTargets().clear();
                int xValue = ability.getManaCostsToPay().getX();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures gain fear until end of turn");
                mode.getTargets().add(new TargetCreaturePermanent(0, xValue, filter, false));
            }
        }
    }

    public ProfaneCommand(final ProfaneCommand card) {
        super(card);
    }

    @Override
    public ProfaneCommand copy() {
        return new ProfaneCommand(this);
    }
}
