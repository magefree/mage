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
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
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
public class PolymorphousRush extends CardImpl<PolymorphousRush> {

    public PolymorphousRush(UUID ownerId) {
        super(ownerId, 46, "Polymorphous Rush", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "JOU";

        this.color.setBlue(true);

        // Strive - Polymorphous Rush costs {1}{U} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{U}"));
        // Choose a creature on the battlefield. Any number of target creatures you control each become a copy of that creature until end of turn.
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent(""));
        target.setNotTarget(true);
        target.setTargetName("a creature on the battlefield (creature to copy)");
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
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

class PolymorphousRushCopyEffect extends OneShotEffect<PolymorphousRushCopyEffect> {
    
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
            Permanent copyFromCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
            if (copyFromCreature != null) {
                for (UUID copyToId: source.getTargets().get(1).getTargets()) {
                    Permanent copyToCreature = game.getPermanent(copyToId);
                    if (copyToCreature != null) {
                        game.copyPermanent(Duration.EndOfTurn, copyFromCreature, copyToCreature, source, new EmptyApplyToPermanent());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
