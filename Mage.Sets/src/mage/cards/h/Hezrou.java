package mage.cards.h;

import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedThisTurnPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.watchers.common.BlockedThisTurnWatcher;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Hezrou extends AdventureCard {

    private static final FilterCreaturePermanent filterBlocked = new FilterCreaturePermanent("each creature that blocked this turn");
    private static final FilterCreaturePermanent filterBlocking = new FilterCreaturePermanent("each blocking creature");
    static {
        filterBlocked.add(BlockedThisTurnPredicate.instance);
        filterBlocking.add(BlockingPredicate.instance);
    }


    public Hezrou(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FROG, SubType.DEMON}, "{5}{B}{B}",
                "Demonic Stench",
                new CardType[]{CardType.INSTANT}, "{B}");

        // Hezrou
        this.getLeftHalfCard().setPT(6, 6);

        // Whenever one or more creatures you control become blocked, each blocking creature gets -1/-1 until end of turn.
        this.getLeftHalfCard().addAbility(new HezrouTriggeredAbility(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filterBlocking, false)));

        // Demonic Stench
        // Each creature that blocked this turn gets -1/-1 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filterBlocked, false));
        this.getRightHalfCard().getSpellAbility().addWatcher(new BlockedThisTurnWatcher());

        finalizeCard();
    }

    private Hezrou(final Hezrou card) {
        super(card);
    }

    @Override
    public Hezrou copy() {
        return new Hezrou(this);
    }
}

class HezrouTriggeredAbility extends TriggeredAbilityImpl {

    HezrouTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever one or more creatures you control become blocked, ");
    }

    private HezrouTriggeredAbility(final HezrouTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP
                || event.getType() == GameEvent.EventType.BATCH_BLOCK_NONCOMBAT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case DECLARE_BLOCKERS_STEP:
                return game.getCombat()
                        .getGroups()
                        .stream()
                        .filter(CombatGroup::getBlocked)
                        .map(CombatGroup::getAttackers)
                        .flatMap(Collection::stream)
                        .map(game::getControllerId)
                        .anyMatch(this.getControllerId()::equals);
            case BATCH_BLOCK_NONCOMBAT:
                Object value = game.getState().getValue("becameBlocked_" + event.getData());
                if (!(value instanceof Set)) {
                    return false;
                }
                Set<MageObjectReference> permanents = (Set<MageObjectReference>) value;
                return permanents
                        .stream()
                        .map(mor -> mor.getPermanentOrLKIBattlefield(game))
                        .filter(Objects::nonNull)
                        .map(Controllable::getControllerId)
                        .anyMatch(this.getControllerId()::equals);
            default:
                return false;
        }
    }

    @Override
    public HezrouTriggeredAbility copy() {
        return new HezrouTriggeredAbility(this);
    }

}
