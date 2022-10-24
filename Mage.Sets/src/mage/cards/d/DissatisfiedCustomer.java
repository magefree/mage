package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DissatisfiedCustomer extends CardImpl {

    public DissatisfiedCustomer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.GUEST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Dissatisfied Customer enters the battlefield, roll a six-sided die. If the result is 3 or less, you lose that much life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DissatisfiedCustomerEffect()));
    }

    private DissatisfiedCustomer(final DissatisfiedCustomer card) {
        super(card);
    }

    @Override
    public DissatisfiedCustomer copy() {
        return new DissatisfiedCustomer(this);
    }
}

class DissatisfiedCustomerEffect extends OneShotEffect {

    DissatisfiedCustomerEffect() {
        super(Outcome.Benefit);
        staticText = "roll a six-sided die. If the result is 3 or less, you lose that much life";
    }

    private DissatisfiedCustomerEffect(final DissatisfiedCustomerEffect effect) {
        super(effect);
    }

    @Override
    public DissatisfiedCustomerEffect copy() {
        return new DissatisfiedCustomerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        if (result <= 3) {
            player.loseLife(result, game, source, false);
        }
        return true;
    }
}
