
package mage.cards.g;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedLastTurnWatcher;

/**
 *
 * @author L_J
 */
public final class GoblinRockSled extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public GoblinRockSled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Goblin Rock Sled doesn't untap during your untap step if it attacked during your last turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapIfAttackedLastTurnSourceEffect()), new AttackedLastTurnWatcher());

        // Goblin Rock Sled can't attack unless defending player controls a Mountain.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(filter)));
    }

    private GoblinRockSled(final GoblinRockSled card) {
        super(card);
    }

    @Override
    public GoblinRockSled copy() {
        return new GoblinRockSled(this);
    }
}

class DontUntapIfAttackedLastTurnSourceEffect extends ContinuousRuleModifyingEffectImpl {

    public DontUntapIfAttackedLastTurnSourceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, true);
        staticText = "{this} doesn't untap during your untap step if it attacked during your last turn";
    }

    public DontUntapIfAttackedLastTurnSourceEffect(final DontUntapIfAttackedLastTurnSourceEffect effect) {
        super(effect);
    }

    @Override
    public DontUntapIfAttackedLastTurnSourceEffect copy() {
        return new DontUntapIfAttackedLastTurnSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.UNTAP
                && event.getTargetId().equals(source.getSourceId())) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && permanent.isControlledBy(game.getActivePlayerId())) {
                AttackedLastTurnWatcher watcher = game.getState().getWatcher(AttackedLastTurnWatcher.class);
                if (watcher != null) {
                    Set<MageObjectReference> attackingCreatures = watcher.getAttackedLastTurnCreatures(permanent.getControllerId());
                    MageObjectReference mor = new MageObjectReference(permanent, game);
                    if (attackingCreatures.contains(mor)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
