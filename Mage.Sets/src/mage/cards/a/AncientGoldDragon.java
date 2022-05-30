package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.FaerieDragonToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientGoldDragon extends CardImpl {

    public AncientGoldDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(10);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ancient Gold Dragon deals combat damage to a player, roll a d20. You create a number of 1/1 blue Faerie Dragon creature tokens with flying equal to the result.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AncientGoldDragonEffect(), false));
    }

    private AncientGoldDragon(final AncientGoldDragon card) {
        super(card);
    }

    @Override
    public AncientGoldDragon copy() {
        return new AncientGoldDragon(this);
    }
}

class AncientGoldDragonEffect extends OneShotEffect {

    AncientGoldDragonEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d20. You create a number of 1/1 blue " +
                "Faerie Dragon creature tokens with flying equal to the result";
    }

    private AncientGoldDragonEffect(final AncientGoldDragonEffect effect) {
        super(effect);
    }

    @Override
    public AncientGoldDragonEffect copy() {
        return new AncientGoldDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.rollDice(Outcome.Benefit, source, game, 20);
        return new FaerieDragonToken().putOntoBattlefield(amount, game, source);
    }
}
