package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientSilverDragon extends CardImpl {

    public AncientSilverDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");

        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ancient Silver Dragon deals combat damage to a player, roll a d20. Draw cards equal to the result. You have no maximum hand size for the rest of the game.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AncientSilverDragonEffect(), false);
        ability.addEffect(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.EndOfGame, MaximumHandSizeControllerEffect.HandSizeModification.SET
        ));
    }

    private AncientSilverDragon(final AncientSilverDragon card) {
        super(card);
    }

    @Override
    public AncientSilverDragon copy() {
        return new AncientSilverDragon(this);
    }
}

class AncientSilverDragonEffect extends OneShotEffect {

    AncientSilverDragonEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d20. Draw cards equal to the result.";
    }

    private AncientSilverDragonEffect(final AncientSilverDragonEffect effect) {
        super(effect);
    }

    @Override
    public AncientSilverDragonEffect copy() {
        return new AncientSilverDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.rollDice(Outcome.Benefit, source, game, 20);
        if (amount > 0) {
            player.drawCards(amount, source, game);
        }
        return true;
    }
}
