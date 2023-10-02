
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Derpthemeus
 */
public final class Disarm extends CardImpl {

    public Disarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Unattach all Equipment from target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DisarmEffect());
    }

    private Disarm(final Disarm card) {
        super(card);
    }

    @Override
    public Disarm copy() {
        return new Disarm(this);
    }

    class DisarmEffect extends OneShotEffect {

        public DisarmEffect() {
            super(Outcome.UnboostCreature);
            this.staticText = "Unattach all Equipment from target creature";
        }

        private DisarmEffect(final DisarmEffect effect) {
            super(effect);
        }

        @Override
        public DisarmEffect copy() {
            return new DisarmEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
            if (creature != null) {
                FilterPermanent creatureFilter = new FilterPermanent();
                creatureFilter.add(new PermanentIdPredicate(creature.getId()));

                FilterPermanent equipmentFilter = new FilterPermanent();
                equipmentFilter.add(new AttachedToPredicate(creatureFilter));
                equipmentFilter.add(SubType.EQUIPMENT.getPredicate());

                for (Permanent equipment : game.getBattlefield().getAllActivePermanents(equipmentFilter, game)) {
                    creature.removeAttachment(equipment.getId(), source, game);
                }
                return true;
            }
            return false;
        }
    }
}
