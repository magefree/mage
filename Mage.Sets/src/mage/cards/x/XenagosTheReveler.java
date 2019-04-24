
package mage.cards.x;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.XenagosSatyrToken;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class XenagosTheReveler extends CardImpl {

    public XenagosTheReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.XENAGOS);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // +1: Add X mana in any combination of {R} and/or {G}, where X is the number of creatures you control.
        this.addAbility(new LoyaltyAbility(new XenagosManaEffect(), +1));

        // 0: Create a 2/2 red and green Satyr creature token with haste.
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
        this.staticText = "Add X mana in any combination of {R} and/or {G}, where X is the number of creatures you control";
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
                if (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    return false;
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
            if (!exiledCards.isEmpty()
                    && target1.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.choose(Outcome.PutCardInPlay, exiledCards, target1, game)) {
                controller.moveCards(new CardsImpl(target1.getTargets()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
