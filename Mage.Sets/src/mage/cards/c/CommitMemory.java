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
import mage.abilities.keyword.AftermathAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
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
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public CommitMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{3}{U}", "{4}{U}{U}", false);

        // Commit
        // Put target spell or nonland permanent into its owner's library second from the top.
        getLeftHalfCard().getSpellAbility().addEffect(new CommitEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpellOrPermanent(1, 1, filter, false));

        // Memory
        // Aftermath
        // Each player shuffles his or her hand and graveyard into his or her library, then draws seven cards.
        ((CardImpl)(getRightHalfCard())).addAbility(new AftermathAbility());
        getRightHalfCard().getSpellAbility().addEffect(new MemoryEffect());
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
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            Player controller = game.getPlayer(permanent.getControllerId());
            if (owner == null || controller == null) {
                return false;
            }

            Card card = null;
            if (owner.getLibrary().hasCards()) {
                card = owner.getLibrary().removeFromTop(game);
            }

            permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            if (card != null) {
                owner.getLibrary().putOnTop(card, game);
            }
            return true;
        }
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player owner = game.getPlayer(spell.getOwnerId());
            Player controller = game.getPlayer(spell.getControllerId());
            if (owner == null || controller == null) {
                return false;
            }

            Card card = null;
            if (owner.getLibrary().hasCards()) {
                card = owner.getLibrary().removeFromTop(game);
            }

            spell.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            if (card != null) {
                owner.getLibrary().putOnTop(card, game);
            }
            return true;
        }
        return false;
    }
}

class MemoryEffect extends OneShotEffect {

    public MemoryEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles his or her hand and graveyard into his or her library";
    }

    public MemoryEffect(final MemoryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                player.moveCards(player.getGraveyard(), Zone.LIBRARY, source, game);
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }

    @Override
    public MemoryEffect copy() {
        return new MemoryEffect(this);
    }

}
