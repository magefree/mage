package mage.game.permanent;

import mage.abilities.Ability;
import mage.abilities.keyword.PhasingAbility;
import mage.constants.CardType;
import mage.constants.RangeOfInfluence;
import mage.filter.FilterPermanent;
import mage.game.Game;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Battlefield implements Serializable {

    private final Map<UUID, Permanent> field = new LinkedHashMap<>();

    public Battlefield() {
    }

    public Battlefield(final Battlefield battlefield) {
        for (Entry<UUID, Permanent> entry : battlefield.field.entrySet()) {
            field.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public Battlefield copy() {
        return new Battlefield(this);
    }

    public void reset(Game game) {
        for (Permanent perm : field.values()) {
            perm.reset(game);
        }
    }

    public void clear() {
        field.clear();
    }

    /**
     * Returns a count of all {@link Permanent} that match the filter and are
     * controlled by controllerId.
     * <p>
     * Some filter predicates do not work here (e.g. AnotherPredicate() because
     * filter.match() is called without controllerId. To use this predicates you
     * can use count() instead of countAll()
     *
     * @param filter
     * @param controllerId
     * @param game
     * @return count
     */
    public int countAll(FilterPermanent filter, UUID controllerId, Game game) {
        return (int) field.values()
                .stream()
                .filter(permanent -> permanent.isControlledBy(controllerId)
                        && filter.match(permanent, game)
                        && permanent.isPhasedIn())
                .count();

    }

    /**
     * Returns a count of all {@link Permanent} that are within the range of
     * influence of the specified player id and that match the supplied filter.
     *
     * @param filter
     * @param sourcePlayerId
     * @param source
     * @param game
     * @return count
     */
    public int count(FilterPermanent filter, UUID sourcePlayerId, Ability source, Game game) {
        if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
            return (int) field.values()
                    .stream()
                    .filter(permanent -> filter.match(permanent, sourcePlayerId, source, game)
                            && permanent.isPhasedIn())
                    .count();
        } else {
            List<UUID> range = game.getState().getPlayersInRange(sourcePlayerId, game);
            return (int) field.values()
                    .stream()
                    .filter(permanent -> range.contains(permanent.getControllerId())
                            && filter.match(permanent, sourcePlayerId, source, game)
                            && permanent.isPhasedIn()).count();
        }
    }

    public boolean containsControlled(FilterPermanent filter, Ability source, Game game, int num) {
        return containsControlled(filter, source.getSourceId(), source.getControllerId(), source, game, num);
    }

    /**
     * Returns true if the battlefield contains num or more {@link Permanent}
     * that matches the filter and is controlled by controllerId. This method
     * ignores the range of influence.
     *
     * @param filter
     * @param sourceId
     * @param controllerId controller and source can be different (from different players)
     * @param source
     * @param game
     * @param num
     * @return boolean
     */
    public boolean containsControlled(FilterPermanent filter, UUID sourceId, UUID controllerId, Ability source, Game game, int num) {
        return field.values()
                .stream()
                .filter(permanent -> permanent.isControlledBy(controllerId)
                        && filter.match(permanent, controllerId, source, game)
                        && permanent.isPhasedIn())
                .count() >= num;

    }

    public boolean contains(FilterPermanent filter, Ability source, Game game, int num) {
        return contains(filter, source.getSourceId(), source.getControllerId(), source, game, num);
    }

    /**
     * Returns true if the battlefield contains num or more {@link Permanent}
     * that is within the range of influence of the specified player id and that
     * matches the supplied filter.
     *
     * @param filter
     * @param sourceId       can be null for default SBA checks like legendary rule
     * @param sourcePlayerId
     * @param source
     * @param game
     * @param num
     * @return boolean
     */
    public boolean contains(FilterPermanent filter, UUID sourceId, UUID sourcePlayerId, Ability source, Game game, int num) {
        if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
            return field.values().stream()
                    .filter(permanent -> filter.match(permanent, sourcePlayerId, source, game)
                            && permanent.isPhasedIn()).count() >= num;

        } else {
            List<UUID> range = game.getState().getPlayersInRange(sourcePlayerId, game);
            return field.values().stream()
                    .filter(permanent -> range.contains(permanent.getControllerId())
                            && filter.match(permanent, sourcePlayerId, source, game)
                            && permanent.isPhasedIn())
                    .count() >= num;
        }
    }

    public void addPermanent(Permanent permanent) {
        field.put(permanent.getId(), permanent);
    }

    /**
     * Find a permanent on the battlefield by its ID.
     * If you are working with cards and want to know if it is on the battlefield then use game.getState().getZone() instead.
     * Note that the card ID and permanant ID may be different (e.g. MDFC puts a half card on the battlefield, not the main card).
     *
     * @param key   the UUID of a permanent to be retrieved
     * @return      the permanent matching the passed in UUID
     */
    public Permanent getPermanent(UUID key) {
        return field.get(key);
    }

    public void removePermanent(UUID key) {
        field.remove(key);
    }

    /**
     * Check whether the battlefield contains a permanent with the passed in UUID.
     *
     * @param key   the UUID whose existence we're checking for among permanents on the battlefield
     * @return      whether the passed in UUID matches a permanent on the battlefield
     */
    public boolean containsPermanent(UUID key) {
        return field.containsKey(key);
    }

    public void beginningOfTurn(Game game) {
        for (Permanent perm : field.values()) {
            perm.beginningOfTurn(game);
        }
    }

    public void endOfTurn(UUID controllerId, Game game) {
        for (Permanent perm : field.values()) {
            perm.endOfTurn(game);
        }
    }

    public Collection<Permanent> getAllPermanents() {
        return field.values();
    }

    public Set<UUID> getAllPermanentIds() {
        return field.keySet();
    }

    public List<Permanent> getAllActivePermanents() {
        return field.values()
                .stream()
                .filter(Permanent::isPhasedIn)
                .collect(Collectors.toList());
    }

    /**
     * Returns all {@link Permanent} on the battlefield that are controlled by
     * the specified player id. The method ignores the range of influence.
     *
     * @param controllerId
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getAllActivePermanents(UUID controllerId) {
        return field.values()
                .stream()
                .filter(perm -> perm.isPhasedIn()
                        && perm.isControlledBy(controllerId))
                .collect(Collectors.toList());
    }

    /**
     * Returns all {@link Permanent} on the battlefield that match the specified
     * {@link CardType}. This method ignores the range of influence.
     *
     * @param type
     * @param game
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getAllActivePermanents(CardType type, Game game) {
        return field.values()
                .stream()
                .filter(perm -> perm.isPhasedIn() && perm.getCardType(game).contains(type))
                .collect(Collectors.toList());
    }

    /**
     * Returns all {@link Permanent} on the battlefield that match the supplied
     * filter. This method ignores the range of influence and ignores
     * ObjectSourcePlayer predicates in the filter (e.g. controller predicates)
     *
     * @param filter
     * @param game
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getAllActivePermanents(FilterPermanent filter, Game game) {
        return field.values()
                .stream()
                .filter(perm -> perm.isPhasedIn() && filter.match(perm, game))
                .collect(Collectors.toList());
    }

    /**
     * Returns all {@link Permanent} that match the filter and are controlled by
     * controllerId. This method ignores the range of influence and ignores
     * ObjectSourcePlayer predicates in the filter
     *
     * @param filter
     * @param controllerId
     * @param game
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getAllActivePermanents(FilterPermanent filter, UUID controllerId, Game game) {
        return field.values()
                .stream()
                .filter(perm -> perm.isPhasedIn() && perm.isControlledBy(controllerId) && filter.match(perm, game))
                .collect(Collectors.toList());
    }

    /**
     * Returns all {@link Permanent} that are within the range of influence of
     * the specified player id and that match the supplied filter.
     *
     * @param filter
     * @param sourcePlayerId
     * @param game
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getActivePermanents(FilterPermanent filter, UUID sourcePlayerId, Game game) {
        return getActivePermanents(filter, sourcePlayerId, null, game);
    }

    /**
     * Returns all {@link Permanent} that are within the range of influence of
     * the specified player id and that match the supplied filter.
     *
     * @param filter
     * @param sourcePlayerId
     * @param source
     * @param game
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getActivePermanents(FilterPermanent filter, UUID sourcePlayerId, Ability source, Game game) {
        if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
            return field.values()
                    .stream()
                    .filter(perm -> perm.isPhasedIn() && filter.match(perm, sourcePlayerId, source, game))
                    .collect(Collectors.toList());
        } else {
            List<UUID> range = game.getState().getPlayersInRange(sourcePlayerId, game);
            return field.values()
                    .stream()
                    .filter(perm -> perm.isPhasedIn() && range.contains(perm.getControllerId())
                            && filter.match(perm, sourcePlayerId, source, game)).collect(Collectors.toList());
        }
    }

    /**
     * Returns all {@link Permanent} that are within the range of influence of
     * the specified player id.
     *
     * @param sourcePlayerId
     * @param game
     * @return a list of {@link Permanent}
     * @see Permanent
     */
    public List<Permanent> getActivePermanents(UUID sourcePlayerId, Game game) {
        if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
            return getAllActivePermanents();
        } else {
            List<UUID> range = game.getState().getPlayersInRange(sourcePlayerId, game);
            return field.values()
                    .stream()
                    .filter(perm -> perm.isPhasedIn()
                            && range.contains(perm.getControllerId()))
                    .collect(Collectors.toList());

        }
    }

    public List<Permanent> getPhasedIn(Game game, UUID controllerId) {
        return field.values()
                .stream()
                .filter(perm -> perm.hasAbility(PhasingAbility.getInstance(), game)
                        && perm.isPhasedIn()
                        && perm.isControlledBy(controllerId))
                .collect(Collectors.toList());
    }

    public List<Permanent> getPhasedOut(Game game, UUID controllerId) {
        return field.values()
                .stream()
                .filter(perm -> !perm.isPhasedIn() && perm.isControlledBy(controllerId))
                .collect(Collectors.toList());
    }

    public void resetPermanentsControl() {
        for (Permanent perm : field.values()) {
            if (perm.isPhasedIn()) {
                perm.resetControl();
            }
        }
    }

    /**
     * since control could change several times during applyEvents we only want
     * to fire control changed events after all control change effects have been
     * applied
     *
     * @param game
     * @return
     */
    public boolean fireControlChangeEvents(Game game) {
        boolean controlChanged = false;
        for (Permanent perm : field.values()) {
            if (perm.isPhasedIn()) {
                controlChanged |= perm.checkControlChanged(game);
            }
        }
        return controlChanged;
    }

    public int countTokens(UUID controllerId) {
        return field
                .values()
                .stream()
                .filter(Objects::nonNull)
                .filter(PermanentToken.class::isInstance)
                .map(permanent -> permanent.isControlledBy(controllerId))
                .mapToInt(x -> x ? 1 : 0)
                .sum();
    }

    @Override
    public String toString() {
        return "Permanents: " + field.size();
    }
}
