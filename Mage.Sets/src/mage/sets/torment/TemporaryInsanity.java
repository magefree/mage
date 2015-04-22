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
package mage.sets.torment;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public class TemporaryInsanity extends CardImpl {

    public TemporaryInsanity(UUID ownerId) {
        super(ownerId, 116, "Temporary Insanity", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.expansionSetCode = "TOR";

        // Untap target creature with power less than the number of cards in your graveyard
        this.getSpellAbility().addTarget(new TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard());
        this.getSpellAbility().addEffect(new UntapTargetEffect());

        // and gain control of it until end of turn.
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it until end of the turn. ");
        this.getSpellAbility().addEffect(effect);

        // That creature gains haste until end of turn.
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("That creature gains haste until end of the turn.");
        this.getSpellAbility().addEffect(effect);
    }

    public TemporaryInsanity(final TemporaryInsanity card) {
        super(card);
    }

    @Override
    public TemporaryInsanity copy() {
        return new TemporaryInsanity(this);
    }
}

class TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard extends TargetCreaturePermanent {

    public TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard() {
        super();
        targetName = "creature with power less than the number of cards in your graveyard";
    }

    public TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard(final TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent target = game.getPermanent(id);
            if (target != null) {
                return target.getPower().getValue() < game.getPlayer(source.getControllerId()).getGraveyard().size();
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                if(permanent.getPower().getValue() < game.getPlayer(sourceControllerId).getGraveyard().size()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard copy() {
        return new TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard(this);
    }
}
