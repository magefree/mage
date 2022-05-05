
package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetObject;



/**
 *
 * @author jeffwadsworth
 */
public final class Outwit extends CardImpl {

    private static FilterSpell filter = new FilterSpell("spell that targets a player");

    public Outwit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Counter target spell that targets a player.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new CustomTargetSpell(filter));
    }

    private Outwit(final Outwit card) {
        super(card);
    }

    @Override
    public Outwit copy() {
        return new Outwit(this);
    }

    private static class CustomTargetSpell extends TargetObject {

        protected FilterSpell filter;

        public CustomTargetSpell() {
            this(1, 1, StaticFilters.FILTER_SPELL);
        }

        public CustomTargetSpell(FilterSpell filter) {
            this(1, 1, filter);
        }

        public CustomTargetSpell(int numTargets, FilterSpell filter) {
            this(numTargets, numTargets, filter);
        }

        public CustomTargetSpell(int minNumTargets, int maxNumTargets, FilterSpell filter) {
            this.minNumberOfTargets = minNumTargets;
            this.maxNumberOfTargets = maxNumTargets;
            this.zone = Zone.STACK;
            this.filter = filter;
            this.targetName = filter.getMessage();
        }

        public CustomTargetSpell(final CustomTargetSpell target) {
            super(target);
            this.filter = target.filter.copy();
        }

        @Override
        public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
            return canChoose(sourceControllerId, game);
        }

        @Override
        public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
            return possibleTargets(sourceControllerId, game);
        }

        @Override
        public boolean canTarget(UUID id, Ability source, Game game) {
            if (super.canTarget(id, source, game)) {
                if (targetsPlayer(id, game)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canChoose(UUID sourceControllerId, Game game) {
            int count = 0;
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell && filter.match(stackObject, game)) {
                    if (targetsPlayer(stackObject.getId(), game)) {
                        count++;
                        if (count >= this.minNumberOfTargets) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
            Set<UUID> possibleTargets = new HashSet<>();
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell && filter.match(stackObject, game)) {
                    if (targetsPlayer(stackObject.getId(), game)) {
                        possibleTargets.add(stackObject.getId());
                    }
                }
            }
            return possibleTargets;
        }

        @Override
        public Filter getFilter() {
                return filter;
        }

        private boolean targetsPlayer(UUID id, Game game) {
            StackObject spell = game.getStack().getStackObject(id);
            if (spell != null) {
                Ability ability = spell.getStackAbility();
                if (ability != null && !ability.getTargets().isEmpty()) {
                    for (Target target : ability.getTargets()) {
                        for (UUID playerId : target.getTargets()) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public CustomTargetSpell copy() {
            return new CustomTargetSpell(this);
        }
    }
}
