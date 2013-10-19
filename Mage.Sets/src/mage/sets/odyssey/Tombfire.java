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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33, Cloudthresher (LevelX2), Cranial Extraction (BetaSteward)
 */
public class Tombfire extends CardImpl<Tombfire> {

    public Tombfire(UUID ownerId) {
        super(ownerId, 165, "Tombfire", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "ODY";

        this.color.setBlack(true);

        // Target player exiles all cards with flashback from his or her graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        this.getSpellAbility().addEffect(new TombfireEffect());

    }

    public Tombfire(final Tombfire card) {
        super(card);
    }

    @Override
    public Tombfire copy() {
        return new Tombfire(this);
    }
}

class TombfireEffect extends OneShotEffect<TombfireEffect> {

    private static final FilterCard filter = new FilterCard("cards with flashback");

    static {
        filter.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public TombfireEffect() {
        super(Outcome.Exile);
        staticText = "Target player exiles all cards with flashback from his or her graveyard";
    }

    public TombfireEffect(final TombfireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            for (Card card : targetPlayer.getGraveyard().getCards(filter, game)) {
                card.moveToExile(null, "", source.getSourceId(), game);
            }
            return true;
        } 
        return false;

    }

    @Override
    public TombfireEffect copy() {
        return new TombfireEffect(this);
    }
}
