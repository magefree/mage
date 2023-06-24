
package mage.cards.s;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.DragonToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author JRHerlehy
 */
public final class SarkhanUnbroken extends CardImpl {

    private static final FilterCard dragonFilter = new FilterCard("Dragon creature cards");

    static {
        dragonFilter.add(SubType.DRAGON.getPredicate());
    }

    public SarkhanUnbroken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);

        this.setStartingLoyalty(4);

        // +1: Draw a card, then add one mana of any color.
        this.addAbility(new LoyaltyAbility(new SarkhanUnbrokenAbility1(), 1));
        // -2: Create a 4/4 red Dragon creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DragonToken(), 1), -2));
        // -8: Search your library for any number of Dragon creature cards and put them onto the battlefield. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, Integer.MAX_VALUE, dragonFilter)), -8));
    }

    private SarkhanUnbroken(final SarkhanUnbroken card) {
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
        this.staticText = "Draw a card, then add one mana of any color.";
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
            controller.drawCards(1, source, game);

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
            if (!controller.choose(Outcome.Benefit, manaChoice, game)) {
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
