
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 *
 */
public final class CrosisThePurger extends CardImpl {

    public CrosisThePurger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Crosis, the Purger deals combat damage to a player, you may pay {2}{B}. If you do, choose a color, then that player reveals their hand and discards all cards of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new CrosisThePurgerEffect(),
                new ManaCostsImpl("{2}{B}")), false, true));
    }

    public CrosisThePurger(final CrosisThePurger card) {
        super(card);
    }

    @Override
    public CrosisThePurger copy() {
        return new CrosisThePurger(this);
    }
}

class CrosisThePurgerEffect extends OneShotEffect {

    CrosisThePurgerEffect() {
        super(Outcome.Discard);
        this.staticText = "choose a color, then that player reveals their hand and discards all cards of that color.";
    }

    CrosisThePurgerEffect(final CrosisThePurgerEffect effect) {
        super(effect);
    }

    @Override
    public CrosisThePurgerEffect copy() {
        return new CrosisThePurgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ChoiceColor choice = new ChoiceColor();
            player.choose(outcome, choice, game);
            if (choice.isChosen()) {
                game.informPlayers(new StringBuilder(player.getLogName()).append(" chooses ").append(choice.getColor()).toString());
                Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                damagedPlayer.revealCards("hand of " + damagedPlayer.getName(), damagedPlayer.getHand(), game);
                FilterCard filter = new FilterCard();
                filter.add(new ColorPredicate(choice.getColor()));
                List<Card> toDiscard = new ArrayList<>();
                for (UUID cardId : damagedPlayer.getHand()) {
                    Card card = game.getCard(cardId);
                    if (filter.match(card, game)) {
                        toDiscard.add(card);
                    }
                }
                for (Card card : toDiscard) {
                    damagedPlayer.discard(card, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
