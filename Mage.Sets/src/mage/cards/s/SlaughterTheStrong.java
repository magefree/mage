/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
public class SlaughterTheStrong extends CardImpl {

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
