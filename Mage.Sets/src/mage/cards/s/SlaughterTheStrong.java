
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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

/**
 *
 * @author LevelX2
 */
public final class SlaughterTheStrong extends CardImpl {

    public SlaughterTheStrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Each player chooses any number of creatures he or she controls with total power 4 or less, then sacrifices all other creatures he or she controls.
        this.getSpellAbility().addEffect(new SlaughterTheStrongEffect());
    }

    public SlaughterTheStrong(final SlaughterTheStrong card) {
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
        this.staticText = "Each player chooses any number of creatures he or she controls with total power 4 or less, then sacrifices all other creatures he or she controls";
    }

    public SlaughterTheStrongEffect(final SlaughterTheStrongEffect effect) {
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
                    while (selectionDone == false && player.isInGame()) {
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
                        Target target = new TargetPermanent(0, 1, currentFilter, true);
                        player.chooseTarget(Outcome.BoostCreature, target, source, game);
                        if (target.getFirstTarget() != null) {
                            if (selectedCreatures.contains(target.getFirstTarget())) {
                                selectedCreatures.remove(target.getFirstTarget());
                            } else {
                                selectedCreatures.add(target.getFirstTarget());
                            }
                        } else {
                            if (player.isHuman()) {
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
                            } else {
                                selectionDone = true;
                            }
                        }
                    }
                    for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game)) {
                        if (!selectedCreatures.contains(creature.getId())) {
                            creature.sacrifice(source.getSourceId(), game);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }
}
