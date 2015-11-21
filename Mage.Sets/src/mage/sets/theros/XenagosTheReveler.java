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
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
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
public class XenagosTheReveler extends CardImpl {

    public XenagosTheReveler(UUID ownerId) {
        super(ownerId, 209, "Xenagos, the Reveler", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Xenagos");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

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

class XenagosManaEffect extends OneShotEffect {

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
        if (player != null) {
            int x = game.getBattlefield().count(new FilterControlledCreaturePermanent(), source.getSourceId(), source.getControllerId(), game);
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Red");
            choices.add("Green");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            for (int i = 0; i < x; i++) {
                Mana mana = new Mana();
                while (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    if (!player.canRespond()) {
                        return false;
                    }
                }
                if (manaChoice.getChoice() == null) {  // can happen if player leaves game
                    return false;
                }
                switch (manaChoice.getChoice()) {
                    case "Green":
                        mana.increaseGreen();
                        break;
                    case "Red":
                        mana.increaseRed();
                        break;
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

class XenagosExileEffect extends OneShotEffect {

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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards exiledCards = new CardsImpl();
            exiledCards.addAll(controller.getLibrary().getTopCards(game, 7));
            controller.moveCards(exiledCards, Zone.EXILED, source, game);
            FilterCard filter = new FilterCard("creature and/or land cards to put onto the battlefield");
            filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                    new CardTypePredicate(CardType.LAND)));
            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.EXILED, filter);
            if (exiledCards.size() > 0
                    && target1.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.choose(Outcome.PutCardInPlay, exiledCards, target1, game)) {
                controller.moveCards(new CardsImpl(target1.getTargets()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
