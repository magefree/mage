package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * @author Loki
 */
public class PutIntoGraveFromAnywhereSourceTriggeredAbility extends ZoneChangeTriggeredAbility {

    public PutIntoGraveFromAnywhereSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.GRAVEYARD, effect, "When {this} is put into a graveyard from anywhere, ", optional);
    }

    public PutIntoGraveFromAnywhereSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    protected PutIntoGraveFromAnywhereSourceTriggeredAbility(final PutIntoGraveFromAnywhereSourceTriggeredAbility ability) {
        super(ability);
    }
//
//    /**
//     *
//     * There are two types of triggers that involve the graveyard: dies triggers
//     * (which are a subset of leave-the-battlefield triggers) and put into the
//     * graveyard from anywhere triggers.
//     *
//     * The former triggers trigger based on the game state prior to the move
//     * where the Kozilek permanent is face down and has no abilities. The latter
//     * triggers trigger from the game state after the move where the Kozilek
//     * card is itself and has the ability.
//     *
//     * The trigger on Kozilek will trigger and he and the graveyard will be
//     * shuffled into the library.
//     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/537065-ixidron-and-kozilek
//     *
//     * @param game
//     * @param source
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
//        if (game.getState().getZone(source.getId()).equals(Zone.GRAVEYARD)) {
//            return this.hasSourceObjectAbility(game, source, event);
//        }
//        return false;
//    }

    @Override
    public PutIntoGraveFromAnywhereSourceTriggeredAbility copy() {
        return new PutIntoGraveFromAnywhereSourceTriggeredAbility(this);
    }
}
