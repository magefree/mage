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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DarkDabbling extends CardImpl {

    public DarkDabbling(UUID ownerId) {
        super(ownerId, 89, "Dark Dabbling", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "ORI";

        // Regenerate target creature. Draw a card.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // <i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, also regenerate each other creature you control.
        this.getSpellAbility().addEffect(new DarkDabblingEffect());
    }

    public DarkDabbling(final DarkDabbling card) {
        super(card);
    }

    @Override
    public DarkDabbling copy() {
        return new DarkDabbling(this);
    }
}

class DarkDabblingEffect extends OneShotEffect {

    public DarkDabblingEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, also regenerate each other creature you control";
    }

    public DarkDabblingEffect(final DarkDabblingEffect effect) {
        super(effect);
    }

    @Override
    public DarkDabblingEffect copy() {
        return new DarkDabblingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (SpellMasteryCondition.getInstance().apply(game, source)) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                if (!permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
                    permanent.regenerate(source.getSourceId(), game);
                }
            }
        }
        return true;
    }
}
