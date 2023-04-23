package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScourgeOfTheSkyclaves extends CardImpl {

    public ScourgeOfTheSkyclaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kicker {4}{B}
        this.addAbility(new KickerAbility("{4}{B}"));

        // When you cast this spell, if it was kicked, each player loses half their life, rounded up.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new ScourgeOfTheSkyclavesEffect()), ScourgeOfTheSkyclavesCondition.instance,
                "When you cast this spell, if it was kicked, each player loses half their life, rounded up."
        ));

        // Scourge of the Skyclaves's power and toughness are each equal to 20 minus the highest life total among players.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(
                ScourgeOfTheSkyclavesValue.instance
        ).setText("{this}'s power and toughness are each equal to 20 minus the highest life total among players.")));
    }

    private ScourgeOfTheSkyclaves(final ScourgeOfTheSkyclaves card) {
        super(card);
    }

    @Override
    public ScourgeOfTheSkyclaves copy() {
        return new ScourgeOfTheSkyclaves(this);
    }
}

enum ScourgeOfTheSkyclavesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return KickerAbility.getSpellKickedCount(game, source.getSourceId()) > 0;
    }
}

enum ScourgeOfTheSkyclavesValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 20 - game
                .getState()
                .getPlayersInRange(sourceAbility.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .max()
                .orElse(0);
    }

    @Override
    public ScourgeOfTheSkyclavesValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "20 minus the highest life total among players";
    }
}

class ScourgeOfTheSkyclavesEffect extends OneShotEffect {

    ScourgeOfTheSkyclavesEffect() {
        super(Outcome.Benefit);
    }

    private ScourgeOfTheSkyclavesEffect(final ScourgeOfTheSkyclavesEffect effect) {
        super(effect);
    }

    @Override
    public ScourgeOfTheSkyclavesEffect copy() {
        return new ScourgeOfTheSkyclavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int lifeToLose = (int) Math.ceil(player.getLife() / 2.0);
            player.loseLife(lifeToLose, game, source, false);
        }
        return true;
    }
}
