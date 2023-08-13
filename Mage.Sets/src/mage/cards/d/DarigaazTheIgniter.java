package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
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

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class DarigaazTheIgniter extends CardImpl {

    public DarigaazTheIgniter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Darigaaz, the Igniter deals combat damage to a player, you may pay {2}{R}. If you do, choose a color, then that player reveals their hand and Darigaaz deals damage to the player equal to the number of cards of that color revealed this way.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new DarigaazTheIgniterEffect(), new ManaCostsImpl<>("{2}{R}")), false, true));
    }

    private DarigaazTheIgniter(final DarigaazTheIgniter card) {
        super(card);
    }

    @Override
    public DarigaazTheIgniter copy() {
        return new DarigaazTheIgniter(this);
    }
}

class DarigaazTheIgniterEffect extends OneShotEffect {

    public DarigaazTheIgniterEffect() {
        super(Outcome.Damage);
        staticText = "choose a color, then that player reveals their hand and {this} deals damage"
                + " to the player equal to the number of cards of that color revealed this way";
    }

    public DarigaazTheIgniterEffect(final DarigaazTheIgniterEffect effect) {
        super(effect);
    }

    @Override
    public DarigaazTheIgniterEffect copy() {
        return new DarigaazTheIgniterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor(true);
        if (controller != null && controller.choose(outcome, choice, game)) {
            game.informPlayers(controller.getLogName() + " chooses " + choice.getColor());
            Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (damagedPlayer != null) {
                damagedPlayer.revealCards("hand of " + damagedPlayer.getName(), damagedPlayer.getHand(), game);
                FilterCard filter = new FilterCard();
                filter.add(new ColorPredicate(choice.getColor()));
                int damage = damagedPlayer.getHand().count(filter, source.getControllerId(), source, game);
                if (damage > 0) {
                    damagedPlayer.damage(damage, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
