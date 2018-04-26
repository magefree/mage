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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WelcomeToTheFold extends CardImpl {

    public WelcomeToTheFold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Madness {X}{U}{U} <i>(If you discard this card
        // discard it into exile. When you do
        // cast it for its madness cost or put it into your graveyard.
        Ability ability = new MadnessAbility(this, new ManaCostsImpl("{X}{U}{U}"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Gain control of target creature if its toughness is 2 or less. If Welcome to the Fold's madness cost was paid, instead gain control of that creature if its toughness is X or less.
        this.getSpellAbility().addEffect(new WelcomeToTheFoldEffect(Duration.Custom, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public WelcomeToTheFold(final WelcomeToTheFold card) {
        super(card);
    }

    @Override
    public WelcomeToTheFold copy() {
        return new WelcomeToTheFold(this);
    }
}

class WelcomeToTheFoldEffect extends GainControlTargetEffect {

    public WelcomeToTheFoldEffect(Duration duration, boolean fixedControl) {
        super(duration, fixedControl);
        staticText = "Gain control of target creature if its toughness is 2 or less. If Welcome to the Fold's madness cost was paid, instead gain control of that creature if its toughness is X or less";
    }

    public WelcomeToTheFoldEffect(GainControlTargetEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int maxToughness = 2;
            ManaCosts manaCosts = source.getManaCostsToPay();
            if (!manaCosts.getVariableCosts().isEmpty()) {
                maxToughness = source.getManaCostsToPay().getX();
            }
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null && permanent.getToughness().getValue() > maxToughness) {
                this.discard();
                return;
            }
        }
        super.init(source, game);
    }

    @Override
    public GainControlTargetEffect copy() {
        return new WelcomeToTheFoldEffect(this);
    }

}
