package mage.cards.i;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernoHellion extends CardImpl {

    public InfernoHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each end step, if Inferno Hellion attacked or blocked this turn, its owner shuffles it into their library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new ShuffleIntoLibrarySourceEffect(),
                false, InfernoHellionCondition.instance
        ), new BlockedThisTurnWatcher());
    }

    private InfernoHellion(final InfernoHellion card) {
        super(card);
    }

    @Override
    public InfernoHellion copy() {
        return new InfernoHellion(this);
    }
}

enum InfernoHellionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcherAttacked = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        BlockedThisTurnWatcher watcherBlocked = game.getState().getWatcher(BlockedThisTurnWatcher.class);
        MageObjectReference mor = new MageObjectReference(source.getSourceId(), game);
        if (watcherAttacked == null || watcherBlocked == null) {
            return false;
        }
        return watcherAttacked.getAttackedThisTurnCreatures().contains(mor)
                || watcherBlocked.getBlockedThisTurnCreatures().contains(mor);
    }

    @Override
    public String toString() {
        return "{this} attacked or blocked this turn";
    }
}
