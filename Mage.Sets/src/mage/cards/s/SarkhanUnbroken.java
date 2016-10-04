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
package mage.sets.dragonsoftarkir;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.DragonToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author JRHerlehy
 */
public class SarkhanUnbroken extends CardImpl {

    private static final FilterCard dragonFilter = new FilterCard("Dragon creature card");

    static {
        dragonFilter.add(new SubtypePredicate("Dragon"));
    }

    public SarkhanUnbroken(UUID ownerId) {
        super(ownerId, 230, "Sarkhan Unbroken", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}{R}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Sarkhan");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Draw a card, then add one mana of any color to your mana pool.
        this.addAbility(new LoyaltyAbility(new SarkhanUnbrokenAbility1(), 1));
        // -2: Put a 4/4 red Dragon creature token with flying onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DragonToken(), 1), -2));
        // -8: Search your library for any number of Dragon creature cards and put them onto the battlefield. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, Integer.MAX_VALUE, dragonFilter)), -8));
    }

    public SarkhanUnbroken(final SarkhanUnbroken card) {
        super(card);
    }

    @Override
    public SarkhanUnbroken copy() {
        return new SarkhanUnbroken(this);
    }
}

class SarkhanUnbrokenAbility1 extends OneShotEffect {

    public SarkhanUnbrokenAbility1() {
        super(Outcome.Benefit);
        this.staticText = "Draw a card, then add one mana of any color to your mana pool.";
    }

    public SarkhanUnbrokenAbility1(final SarkhanUnbrokenAbility1 effect) {
        super(effect);
    }

    @Override
    public SarkhanUnbrokenAbility1 copy() {
        return new SarkhanUnbrokenAbility1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, game);

            game.fireUpdatePlayersEvent();

            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("White");
            choices.add("Blue");
            choices.add("Black");
            choices.add("Red");
            choices.add("Green");

            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            Mana mana = new Mana();

            controller.choose(Outcome.Benefit, manaChoice, game);

            if (manaChoice.getChoice() == null) {
                return false;
            }

            switch (manaChoice.getChoice()) {
                case "White":
                    mana.increaseWhite();
                    break;
                case "Blue":
                    mana.increaseBlue();
                    break;
                case "Black":
                    mana.increaseBlack();
                    break;
                case "Red":
                    mana.increaseRed();
                    break;
                case "Green":
                    mana.increaseGreen();
                    break;
            }

            controller.getManaPool().addMana(mana, game, source);

            return true;
        }
        return false;
    }
}
