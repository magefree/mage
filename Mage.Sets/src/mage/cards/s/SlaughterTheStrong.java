package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SlaughterTheStrong extends CardImpl {

    public SlaughterTheStrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Each player chooses any number of creatures they control with total power 4 or less, then sacrifices all other creatures they control.
        this.getSpellAbility().addEffect(new SlaughterTheStrongEffect());
    }

    private SlaughterTheStrong(final SlaughterTheStrong card) {
        super(card);
    }

    @Override
    public SlaughterTheStrong copy() {
        return new SlaughterTheStrong(this);
    }
}

class SlaughterTheStrongEffect extends OneShotEffect {

    public SlaughterTheStrongEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player chooses any number of creatures they control with total power 4 or less, then sacrifices all other creatures they control";
    }

    private SlaughterTheStrongEffect(final SlaughterTheStrongEffect effect) {
        super(effect);
    }

    @Override
    public SlaughterTheStrongEffect copy() {
        return new SlaughterTheStrongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    boolean selectionDone = false;
                    Set<UUID> selectedCreatures = new HashSet<>();
                    while (player.canRespond() && selectionDone == false) {
                        int powerSum = 0;
                        for (UUID creatureId : selectedCreatures) {
                            Permanent creature = game.getPermanent(creatureId);
                            if (creature != null) {
                                powerSum += creature.getPower().getValue();
                            }
                        }
                        FilterControlledCreaturePermanent currentFilter
                                = new FilterControlledCreaturePermanent("any number of creatures you control with total power 4 or less (already selected total power " + powerSum + ")");
                        Set<PermanentIdPredicate> alreadySelectedCreatures = new HashSet<>();
                        if (!selectedCreatures.isEmpty()) {
                            for (UUID creatureId : selectedCreatures) {
                                alreadySelectedCreatures.add(new PermanentIdPredicate(creatureId));
                            }
                            currentFilter.add(Predicates.or(new PowerPredicate(ComparisonType.FEWER_THAN, 5 - powerSum),
                                    Predicates.or(alreadySelectedCreatures)));
                        } else {
                            currentFilter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 5 - powerSum));
                        }

                        // human can de-select targets, but AI must choose only one time
                        Target target;
                        if (player.isComputer()) {
                            // AI settings
                            FilterControlledCreaturePermanent strictFilter = currentFilter.copy();
                            selectedCreatures.stream().forEach(id -> {
                                strictFilter.add(Predicates.not(new PermanentIdPredicate(id)));
                            });
                            target = new TargetPermanent(0, 1, strictFilter, true);
                        } else {
                            // Human settings
                            target = new TargetPermanent(0, 1, currentFilter, true);
                        }

                        player.chooseTarget(Outcome.BoostCreature, target, source, game);
                        if (target.getFirstTarget() != null) {
                            if (selectedCreatures.contains(target.getFirstTarget())) {
                                selectedCreatures.remove(target.getFirstTarget());
                            } else {
                                selectedCreatures.add(target.getFirstTarget());
                            }
                        } else {
                            if (player.isComputer()) {
                                // AI stops
                                selectionDone = true;
                            } else {
                                // Human can continue
                                String selected = "Selected: ";
                                for (UUID creatureId : selectedCreatures) {
                                    Permanent creature = game.getPermanent(creatureId);
                                    if (creature != null) {
                                        selected += creature.getLogName() + " ";
                                    }
                                }
                                selectionDone = player.chooseUse(Outcome.Detriment,
                                        "Creature selection",
                                        selected,
                                        "End the selection",
                                        "Continue the selection", source, game);
                            }
                        }
                    }
                    for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game)) {
                        if (!selectedCreatures.contains(creature.getId())) {
                            creature.sacrifice(source, game);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }
}
