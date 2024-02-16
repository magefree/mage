package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LoneFox
 */
public final class CrosisThePurger extends CardImpl {

    public CrosisThePurger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Crosis, the Purger deals combat damage to a player, you may pay {2}{B}. If you do, choose a color, then that player reveals their hand and discards all cards of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new CrosisThePurgerEffect(),
                new ManaCostsImpl<>("{2}{B}")), false, true));
    }

    private CrosisThePurger(final CrosisThePurger card) {
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

    private CrosisThePurgerEffect(final CrosisThePurgerEffect effect) {
        super(effect);
    }

    @Override
    public CrosisThePurgerEffect copy() {
        return new CrosisThePurgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        player.choose(outcome, choice, game);
        if (!choice.isChosen()) {
            return false;
        }
        game.informPlayers(player.getLogName() + " chooses " + choice.getColor());
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (damagedPlayer == null) {
            return false;
        }
        damagedPlayer.revealCards("hand of " + damagedPlayer.getName(), damagedPlayer.getHand(), game);
        Cards cards = new CardsImpl(
                damagedPlayer.getHand()
                        .getCards(game)
                        .stream()
                        .filter(card -> card.getColor(game).shares(choice.getColor()))
                        .collect(Collectors.toSet())
        );
        damagedPlayer.discard(cards, false, source, game);
        return true;
    }
}
