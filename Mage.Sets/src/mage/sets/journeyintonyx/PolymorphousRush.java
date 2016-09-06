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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class PolymorphousRush extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PolymorphousRush(UUID ownerId) {
        super(ownerId, 46, "Polymorphous Rush", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "JOU";

        // Strive - Polymorphous Rush costs {1}{U} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{U}"));

        // Choose a creature on the battlefield. Any number of target creatures you control each become a copy of that creature until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, false));
        this.getSpellAbility().addEffect(new PolymorphousRushCopyEffect());

    }

    public PolymorphousRush(final PolymorphousRush card) {
        super(card);
    }

    @Override
    public PolymorphousRush copy() {
        return new PolymorphousRush(this);
    }
}

class PolymorphousRushCopyEffect extends OneShotEffect {

    public PolymorphousRushCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "Choose a creature on the battlefield. Any number of target creatures you control each become a copy of that creature until end of turn";
    }

    public PolymorphousRushCopyEffect(final PolymorphousRushCopyEffect effect) {
        super(effect);
    }

    @Override
    public PolymorphousRushCopyEffect copy() {
        return new PolymorphousRushCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCreaturePermanent(new FilterCreaturePermanent(""));
            target.setNotTarget(true);
            target.setTargetName("a creature on the battlefield (creature to copy)");
            if (target.canChoose(source.getId(), controller.getId(), game) && controller.chooseTarget(outcome, target, source, game)) {
                Permanent copyFromCreature = game.getPermanent(target.getFirstTarget());
                if (copyFromCreature != null) {
                    for (UUID copyToId : getTargetPointer().getTargets(game, source)) {
                        Permanent copyToCreature = game.getPermanent(copyToId);
                        if (copyToCreature != null) {
                            game.copyPermanent(Duration.EndOfTurn, copyFromCreature, copyToId, source, new EmptyApplyToPermanent());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
