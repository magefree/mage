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
package mage.sets.darkascension;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public class SuddenDisappearance extends CardImpl<SuddenDisappearance> {

    public SuddenDisappearance(UUID ownerId) {
        super(ownerId, 23, "Sudden Disappearance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{W}");
        this.expansionSetCode = "DKA";

        this.color.setWhite(true);

        // Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new SuddenDisappearanceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
    }

    public SuddenDisappearance(final SuddenDisappearance card) {
        super(card);
    }

    @Override
    public SuddenDisappearance copy() {
        return new SuddenDisappearance(this);
    }
}

class SuddenDisappearanceEffect extends OneShotEffect<SuddenDisappearanceEffect> {

    private static FilterNonlandPermanent filter = new FilterNonlandPermanent();
    
    public SuddenDisappearanceEffect() {
        super(Outcome.Exile);
    }
    
    public SuddenDisappearanceEffect(final SuddenDisappearanceEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> perms = game.getBattlefield().getAllActivePermanents(filter, source.getFirstTarget());
        if (perms.size() > 0) {
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filter, source.getFirstTarget())) {
                permanent.moveToExile(source.getSourceId(), "Sudden Disappearance", source.getSourceId(), game);
            }
            AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Constants.Zone.BATTLEFIELD));
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }

    @Override
    public SuddenDisappearanceEffect copy() {
        return new SuddenDisappearanceEffect(this);
    }
    
}