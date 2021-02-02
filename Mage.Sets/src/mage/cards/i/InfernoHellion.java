package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 *
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
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new ShuffleIntoLibrarySourceEffect(),
                        TargetController.ANY, false
                ),
                InfernoHellionCondition.instance,
                "At the beginning of each end step, "
                + "if {this} attacked or blocked this turn, "
                + "its owner shuffles it into their library."
        );
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new BlockedThisTurnWatcher());
        this.addAbility(ability);
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
}
