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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class ChoiceOfDamnations extends CardImpl<ChoiceOfDamnations> {

    public ChoiceOfDamnations(UUID ownerId) {
        super(ownerId, 62, "Choice of Damnations", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{B}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Arcane");

        this.color.setBlack(true);

        // Target opponent chooses a number. You may have that player lose that much life. If you don't, that player sacrifices all but that many permanents.
        this.getSpellAbility().addEffect(new ChoiceOfDamnationsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));
    }

    public ChoiceOfDamnations(final ChoiceOfDamnations card) {
        super(card);
    }

    @Override
    public ChoiceOfDamnations copy() {
        return new ChoiceOfDamnations(this);
    }
}

class ChoiceOfDamnationsEffect extends OneShotEffect<ChoiceOfDamnationsEffect> {

    public ChoiceOfDamnationsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent chooses a number. You may have that player lose that much life. If you don't, that player sacrifices all but that many permanents";
    }

    public ChoiceOfDamnationsEffect(final ChoiceOfDamnationsEffect effect) {
        super(effect);
    }

    @Override
    public ChoiceOfDamnationsEffect copy() {
        return new ChoiceOfDamnationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int amount = targetPlayer.getAmount(0, Integer.MAX_VALUE, "Chooses a number", game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                StringBuilder sb = new StringBuilder("Shall ").append(targetPlayer.getName()).append(" lose ").append(amount).append(" life?");
                if (controller.chooseUse(outcome, sb.toString(), game)) {
                    targetPlayer.loseLife(amount, game);
                } else {
                    int numberPermanents = game.getState().getBattlefield().countAll(new FilterPermanent(), targetPlayer.getId(), game);
                    if (numberPermanents > amount) {
                        int numberToSacrifice = numberPermanents - amount;
                        Target target = new TargetControlledPermanent(numberToSacrifice, numberToSacrifice, new FilterPermanent(), false);
                        target.setRequired(true);
                        targetPlayer.chooseTarget(Outcome.Sacrifice, target, source, game);
                        for (UUID uuid : target.getTargets()) {
                            Permanent permanent = game.getPermanent(uuid);
                            if (permanent != null) {
                                permanent.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
