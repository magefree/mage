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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class Grozoth extends CardImpl {

    public Grozoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}{U}");
        this.subtype.add("Leviathan");
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
                
        // When Grozoth enters the battlefield, you may search your library for any number of cards that have converted mana cost 9, reveal them, and put them into your hand. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GrozothEffect(), true));
        
        // {4}: Grozoth loses defender until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn), 
                new ManaCostsImpl("{4}")));
        
        // Transmute {1}{U}{U}
        this.addAbility(new TransmuteAbility("{1}{U}{U}"));        
    }

    public Grozoth(final Grozoth card) {
        super(card);
    }

    @Override
    public Grozoth copy() {
        return new Grozoth(this);
    }
}

class GrozothEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, 9));
    }

    public GrozothEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.DrawCard);
        staticText = "you may search your library for any number of cards that have converted mana cost 9, reveal them, and put them into your hand. If you do, shuffle your library";
    }

    public GrozothEffect(final GrozothEffect effect) {
        super(effect);
    }

    @Override
    public GrozothEffect copy() {
        return new GrozothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null && player != null && player.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                player.revealCards(sourceCard.getIdName(), cards, game);
                player.moveCards(cards, Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}