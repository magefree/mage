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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author lunaskyrise
 */
public class IsperiaTheInscrutable extends CardImpl {

    public IsperiaTheInscrutable(UUID ownerId) {
        super(ownerId, 114, "Isperia the Inscrutable", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{U}{U}");
        this.expansionSetCode = "DIS";
        this.supertype.add("Legendary");
        this.subtype.add("Sphinx");
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Isperia the Inscrutable deals combat damage to a player, name a card. That player reveals his or her hand. If he or she reveals the named card, search your library for a creature card with flying, reveal it, put it into your hand, then shuffle your library.
        Effect effect1 = new NameACardEffect(NameACardEffect.TypeOfName.ALL);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect1, true, true);
        Effect effect2 = new IsperiaTheInscrutableEffect();
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public IsperiaTheInscrutable(final IsperiaTheInscrutable card) {
        super(card);
    }

    @Override
    public IsperiaTheInscrutable copy() {
        return new IsperiaTheInscrutable(this);
    }
}

class IsperiaTheInscrutableEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature card with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public IsperiaTheInscrutableEffect() {
        super(Outcome.Neutral);
        staticText = "That player reveals his or her hand. If he or she reveals the named card, search your library for a creature card with flying, reveal it, put it into your hand, then shuffle your library";
    }

    public IsperiaTheInscrutableEffect(final IsperiaTheInscrutableEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Object object = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (player != null && object instanceof String) {
            String namedCard = (String) object;
            for (Card card : player.getHand().getCards(game)) {
                if (card != null && card.getName().equals(namedCard)) {
                    return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new IsperiaTheInscrutableEffect(this);
    }
}
