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
package mage.sets.theros;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class XenagosTheReveler extends CardImpl<XenagosTheReveler> {

    public XenagosTheReveler(UUID ownerId) {
        super(ownerId, 209, "Xenagos, the Reveler", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Xenagos");

        this.color.setRed(true);
        this.color.setGreen(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +1: Add X mana in any combination of {R} and/or {G} to your mana pool, where X is the number of creatures you control.
        this.addAbility(new LoyaltyAbility(new XenagosManaEffect(), +1));
        
        // 0: Put a 2/2 red and green Satyr creature token with haste onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new XenagosSatyrToken()), 0));

        // -6: Exile the top seven cards of your library. You may put any number of creature and/or land cards from among them onto the battlefield.
        this.addAbility(new LoyaltyAbility(new XenagosExileEffect(), -6));



    }

    public XenagosTheReveler(final XenagosTheReveler card) {
        super(card);
    }

    @Override
    public XenagosTheReveler copy() {
        return new XenagosTheReveler(this);
    }
}

class XenagosManaEffect extends OneShotEffect <XenagosManaEffect> {

    public XenagosManaEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add X mana in any combination of {R} and/or {G} to your mana pool, where X is the number of creatures you control";
    }

    public XenagosManaEffect(final XenagosManaEffect effect) {
        super(effect);
    }

    @Override
    public XenagosManaEffect copy() {
        return new XenagosManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            int x = game.getBattlefield().count(new FilterControlledCreaturePermanent(), source.getSourceId(), source.getControllerId(), game);
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<String>();
            choices.add("Red");
            choices.add("Green");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            for(int i = 0; i < x; i++){
                Mana mana = new Mana();
                while (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    if (!player.isInGame()) {
                        return false;
                    }
                }
                if (manaChoice.getChoice().equals("Green")) {
                    mana.addGreen();
                } else if (manaChoice.getChoice().equals("Red")) {
                    mana.addRed();
                }
                player.getManaPool().addMana(mana, game, source);
            }
            return true;
        }
        return false;
    }
}


class XenagosSatyrToken extends Token {

    public XenagosSatyrToken() {
        super("Satyr", "2/2 red and green Satyr creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add("Satyr");
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
    }

}


class XenagosExileEffect extends OneShotEffect<XenagosExileEffect> {

    public XenagosExileEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exile the top seven cards of your library. You may put any number of creature and/or land cards from among them onto the battlefield";
    }

    public XenagosExileEffect(final XenagosExileEffect effect) {
        super(effect);
    }

    @Override
    public XenagosExileEffect copy() {
        return new XenagosExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.EXILED);
            int count = Math.min(player.getLibrary().size(), 7);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().getFromTop(game);
                cards.add(card);
                card.moveToExile(null, null, source.getSourceId(), game);
            }
            FilterCard filter = new FilterCard("creature and/or land cards to put onto the battlefield");
            filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                                     new CardTypePredicate(CardType.LAND)));

            TargetCard target1 = new TargetCard(Zone.EXILED, filter);
            while (cards.size() > 0
                    && target1.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && player.choose(Outcome.PutCardInPlay, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), source.getControllerId());
                }
                target1.clearChosen();
            }
            return true;
        }
        return false;
    }
}
