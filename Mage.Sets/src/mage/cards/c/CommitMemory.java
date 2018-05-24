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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author fireshoes
 */
public class CommitMemory extends SplitCard {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or nonland permanent");

    static {
        filter.setPermanentFilter(new FilterNonlandPermanent());
    }

    public CommitMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{3}{U}", "{4}{U}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Commit
        // Put target spell or nonland permanent into its owner's library second from the top.
        getLeftHalfCard().getSpellAbility().addEffect(new CommitEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpellOrPermanent(1, 1, filter, false));

        // Memory
        // Aftermath
        // Each player shuffles their hand and graveyard into their library, then draws seven cards.
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        getRightHalfCard().getSpellAbility().addEffect(effect);
    }

    public CommitMemory(final CommitMemory card) {
        super(card);
    }

    @Override
    public CommitMemory copy() {
        return new CommitMemory(this);
    }
}

class CommitEffect extends OneShotEffect {

    public CommitEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target spell or nonland permanent into its owner's library second from the top";
    }

    public CommitEffect(final CommitEffect effect) {
        super(effect);
    }

    @Override
    public CommitEffect copy() {
        return new CommitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                return controller.putCardOnTopXOfLibrary(permanent, game, source, 2);
            }
            Spell spell = game.getStack().getSpell(source.getFirstTarget());
            if (spell != null) {
                return controller.putCardOnTopXOfLibrary(spell, game, source, 2);
            }
        }
        return false;
    }
}
