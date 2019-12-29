package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrowthCycle extends CardImpl {

    public GrowthCycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 until end of turn. It gets an additional +2/+2 until end of turn for each card named Growth Cycle in your graveyard.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                        GrowthCycleValue.instance, GrowthCycleValue.instance,
                        Duration.EndOfTurn, true
                ).setText("Target creature gets +3/+3 until end of turn. " +
                        "It gets an additional +2/+2 until end of turn " +
                        "for each card named Growth Cycle in your graveyard.")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GrowthCycle(final GrowthCycle card) {
        super(card);
    }

    @Override
    public GrowthCycle copy() {
        return new GrowthCycle(this);
    }
}

enum GrowthCycleValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 3;
        }
        return 3 + player
                .getGraveyard()
                .getCards(game)
                .stream()
                .mapToInt(card -> "Growth Cycle".equals(card.getName()) ? 2 : 0)
                .sum();
    }

    @Override
    public GrowthCycleValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}