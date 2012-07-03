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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class AshesToAshes extends CardImpl<AshesToAshes> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");

    static {
        filter.getNotCardType().add(CardType.ARTIFACT);
    }

    public AshesToAshes(UUID ownerId) {
        super(ownerId, 3, "Ashes to Ashes", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "5ED";

        this.color.setBlack(true);

        // Exile two target nonartifact creatures. Ashes to Ashes deals 5 damage to you.
        this.getSpellAbility().addEffect(new AshesToAshesEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter));
        this.getSpellAbility().addEffect(new DamageControllerEffect(5));
    }

    public AshesToAshes(final AshesToAshes card) {
        super(card);
    }

    @Override
    public AshesToAshes copy() {
        return new AshesToAshes(this);
    }
}

class AshesToAshesEffect extends OneShotEffect<AshesToAshesEffect> {

    public AshesToAshesEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Exile two target nonartifact creatures";
    }

    public AshesToAshesEffect(final AshesToAshesEffect effect) {
        super(effect);
    }

    @Override
    public AshesToAshesEffect copy() {
        return new AshesToAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(permanentId);
            if (target != null) {
                target.moveToExile(exileId, "Ashes to Ashes", source.getId(), game);
            }
        }
        return true;
    }
}
