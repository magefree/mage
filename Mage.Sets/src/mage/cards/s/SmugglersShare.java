package mage.cards.s;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 * One way or another, everyone gets paid what they're owed.
 * Based on Gadrak, the Crown Scourge, Hoard Hauler, Lavaball Trap, Walker Of The Grove, Widespread Thieving, and Wound Reflection
 *
 * @author Slanman3755
 */
public final class SmugglersShare extends CardImpl {

    public SmugglersShare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of each end step, draw a card for each opponent who drew two or more cards this turn, then
        // create a Treasure token for each opponent who had two or more lands enter the battlefield under their control
        // this turn.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(SmugglersShareDrawValue.instance), TargetController.EACH_PLAYER, false);
        ability.addEffect((new CreateTokenEffect(new TreasureToken(), SmugglersShareTreasureValue.instance)).concatBy(", then"));
        ability.addWatcher(new CardsAmountDrawnThisTurnWatcher());
        ability.addWatcher(new PermanentsEnteredBattlefieldWatcher());
        this.addAbility(ability);
    }

    private SmugglersShare(final SmugglersShare card) {
        super(card);
    }

    @Override
    public SmugglersShare copy() {
        return new SmugglersShare(this);
    }
}

enum SmugglersShareDrawValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        if (watcher == null || controller == null) {
            return 0;
        }
        int drawCount = 0;
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && watcher.getAmountCardsDrawn(opponentId) >= 2) {
                drawCount++;
            }
        }
        return drawCount;
    }

    @Override
    public SmugglersShareDrawValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "opponent who drew two or more cards this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum SmugglersShareTreasureValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Player controller = game.getPlayer(source.getControllerId());
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher == null || controller == null) {
            return 0;
        }
        int treasureCount = 0;
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            List<Permanent> enteredPermanents = watcher.getThisTurnEnteringPermanents(opponentId);
            if (enteredPermanents == null) {
                continue;
            }

            int enteredLandCount = 0;
            for (Permanent permanent : enteredPermanents) {
                if (permanent.isLand(game)) enteredLandCount++;
            }
            if (enteredLandCount >= 2) {
                treasureCount++;
            }
        }
        return treasureCount;
    }

    @Override
    public SmugglersShareTreasureValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "opponent who had two or more lands enter the battlefield under their control this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
