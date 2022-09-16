package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.PlaguebearerOfNurgleToken;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatUncleanOne extends CardImpl {

    public GreatUncleanOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reverberating Summons -- At the beginning of your end step, each opponent loses 2 life. Then for each opponent who has less life than you, create a 1/3 black Demon creature token named Plaguebearer of Nurgle.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(2), TargetController.YOU, false
        );
        ability.addEffect(new CreateTokenEffect(
                new PlaguebearerOfNurgleToken(), GreatUncleanOneValue.instance
        ).setText("then for each opponent who has less life than you, " +
                "create a 1/3 black Demon creature token named Plaguebearer of Nurgle"));
        this.addAbility(ability.withFlavorWord("Reverberating Summons"));
    }

    private GreatUncleanOne(final GreatUncleanOne card) {
        super(card);
    }

    @Override
    public GreatUncleanOne copy() {
        return new GreatUncleanOne(this);
    }
}

enum GreatUncleanOneValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        int life = player.getLife();
        return game
                .getOpponents(player.getId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .map(l -> l < life ? 1 : 0)
                .sum();
    }

    @Override
    public GreatUncleanOneValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
