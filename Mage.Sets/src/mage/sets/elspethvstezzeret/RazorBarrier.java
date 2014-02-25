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
package mage.sets.elspethvstezzeret;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColorOrArtifact;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class RazorBarrier extends CardImpl<RazorBarrier> {

    public RazorBarrier(UUID ownerId) {
        super(ownerId, 26, "Razor Barrier", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "DDF";

        this.color.setWhite(true);

        // Target permanent you control gains protection from artifacts or from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new RazorBarrierEffect(Duration.EndOfTurn));
        Target target = new TargetControlledPermanent();
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
    }

    public RazorBarrier(final RazorBarrier card) {
        super(card);
    }

    @Override
    public RazorBarrier copy() {
        return new RazorBarrier(this);
    }
}

class RazorBarrierEffect extends GainAbilityTargetEffect {

    public RazorBarrierEffect(Duration duration) {
        super(new ProtectionAbility(new FilterCard()), duration);
        staticText = "Target permanent you control gains protection from artifacts or from the color of your choice until end of turn";
    }

    public RazorBarrierEffect(final RazorBarrierEffect effect) {
        super(effect);
    }

    @Override
    public RazorBarrierEffect copy() {
        return new RazorBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard protectionFilter = new FilterCard();
            ChoiceColorOrArtifact choice = new ChoiceColorOrArtifact();
            if (controller.choose(outcome, choice, game)) {
                if (choice.isArtifactSelected()) {
                    protectionFilter.add(new CardTypePredicate(CardType.ARTIFACT));
                } else {
                    protectionFilter.add(new ColorPredicate(choice.getColor()));
                }

                protectionFilter.setMessage(choice.getChoice());
                ((ProtectionAbility) ability).setFilter(protectionFilter);
                Permanent creature = game.getPermanent(source.getFirstTarget());
                if (creature != null) {
                    creature.addAbility(ability, source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }

}
