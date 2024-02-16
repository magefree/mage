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
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientCopperDragon extends CardImpl {

    public AncientCopperDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ancient Copper Dragon deals combat damage to a player, roll a d20. You create a number of Treasure tokens equal to the result.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AncientCopperDragonEffect(), false));
    }

    private AncientCopperDragon(final AncientCopperDragon card) {
        super(card);
    }

    @Override
    public AncientCopperDragon copy() {
        return new AncientCopperDragon(this);
    }
}

class AncientCopperDragonEffect extends OneShotEffect {

    AncientCopperDragonEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d20. You create a number of Treasure tokens equal to the result";
    }

    private AncientCopperDragonEffect(final AncientCopperDragonEffect effect) {
        super(effect);
    }

    @Override
    public AncientCopperDragonEffect copy() {
        return new AncientCopperDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.rollDice(Outcome.Benefit, source, game, 20);
        if (amount > 0) {
            new TreasureToken().putOntoBattlefield(amount, game, source);
        }
        return true;
    }
}
