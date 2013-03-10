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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth and magenoxx_at_gmail.com
 */
public class Spelltwine extends CardImpl<Spelltwine> {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from your graveyard");
    private static final FilterCard filter2 = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));

        filter2.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public Spelltwine(UUID ownerId) {
        super(ownerId, 68, "Spelltwine", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{U}");
        this.expansionSetCode = "M13";

        this.color.setBlue(true);

        // Exile target instant or sorcery card from your graveyard and target instant or sorcery card from an opponent's graveyard. Copy those cards. Cast the copies if able without paying their mana costs. Exile Spelltwine.
        this.getSpellAbility().addEffect(new SpelltwineEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter2));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

    }

    public Spelltwine(final Spelltwine card) {
        super(card);
    }

    @Override
    public Spelltwine copy() {
        return new Spelltwine(this);
    }
}

class SpelltwineEffect extends OneShotEffect<SpelltwineEffect> {

    public SpelltwineEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile target instant or sorcery card from your graveyard and target instant or sorcery card from an opponent's graveyard. Copy those cards. Cast the copies if able without paying their mana costs";
    }

    public SpelltwineEffect(final SpelltwineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Card cardOne = game.getCard(source.getTargets().get(0).getFirstTarget());
        Card cardTwo = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (you != null) {
            if (cardOne != null) {
                cardOne.moveToExile(null, null, source.getId(), game);
            }
            if (cardTwo != null) {
                cardTwo.moveToExile(null, null, source.getId(), game);
            }

            boolean castCardOne = true;
            if (cardOne != null && you.chooseUse(Outcome.Neutral, "Cast the copy of " + cardOne.getName() + " first?", game)) {
                Card copyOne = game.copyCard(cardOne, source, you.getId());
                you.cast(copyOne.getSpellAbility(), game, true);
                castCardOne = false;
            }
            if (cardTwo != null) {
                Card copyTwo = game.copyCard(cardTwo, source, you.getId());
                you.cast(copyTwo.getSpellAbility(), game, true);
            }
            if (cardOne != null && castCardOne) {
                Card copyOne = game.copyCard(cardOne, source, you.getId());
                you.cast(copyOne.getSpellAbility(), game, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public SpelltwineEffect copy() {
        return new SpelltwineEffect(this);
    }
}
