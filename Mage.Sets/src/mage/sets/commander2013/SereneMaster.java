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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SereneMaster extends CardImpl<SereneMaster> {

    public SereneMaster(UUID ownerId) {
        super(ownerId, 20, "Serene Master", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "C13";
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever Serene Master blocks, exchange its power and the power of target creature it's blocking until end of combat.
        this.addAbility(new BlocksTriggeredAbility(new SereneMasterEffect(), false));

    }

    public SereneMaster(final SereneMaster card) {
        super(card);
    }

    @Override
    public SereneMaster copy() {
        return new SereneMaster(this);
    }
}

class SereneMasterEffect extends OneShotEffect<SereneMasterEffect> {

    public SereneMasterEffect() {
        super(Outcome.Benefit);
        this.staticText = "exchange its power and the power of target creature it's blocking until end of combat";
    }

    public SereneMasterEffect(final SereneMasterEffect effect) {
        super(effect);
    }

    @Override
    public SereneMasterEffect copy() {
        return new SereneMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (controller != null && sourceCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature it's blocking");
            filter.add(new BlockedByIdPredicate((source.getSourceId())));
            Target target = new TargetCreaturePermanent(filter, true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Permanent attackingCreature = game.getPermanent(target.getFirstTarget());
                    if (attackingCreature != null) {
                        int newSourcePower = attackingCreature.getPower().getValue();
                        int newAttackerPower = sourceCreature.getPower().getValue();
                        ContinuousEffect effect = new SetPowerToughnessTargetEffect(newSourcePower, sourceCreature.getToughness().getValue(), Duration.EndOfCombat);
                        effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                        game.addEffect(effect, source);
                        effect = new SetPowerToughnessTargetEffect(newAttackerPower, attackingCreature.getToughness().getValue(), Duration.EndOfCombat);
                        effect.setTargetPointer(new FixedTarget(attackingCreature.getId()));
                        game.addEffect(effect, source);
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
