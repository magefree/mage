
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author cbt33
 */
public final class GhastlyDemise extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature if its tougness is less than the number of cards in your graveyard");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public GhastlyDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Destroy target nonblack creature if its toughness is less than or equal to the number of cards in your graveyard.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new GhastlyDemiseEffect(false));
    }

    public GhastlyDemise(final GhastlyDemise card) {
        super(card);
    }

    @Override
    public GhastlyDemise copy() {
        return new GhastlyDemise(this);
    }
}

class GhastlyDemiseEffect extends OneShotEffect {

    protected boolean noRegen;

    public GhastlyDemiseEffect(boolean noRegen) {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target nonblack creature if its toughness is less than or equal to the number of cards in your graveyard";
        this.noRegen = noRegen;
    }

    public GhastlyDemiseEffect(final GhastlyDemiseEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
    }

    @Override
    public GhastlyDemiseEffect copy() {
        return new GhastlyDemiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (source.getTargets().size() > 1 && targetPointer instanceof FirstTargetPointer) { // for Rain of Thorns
            for (Target target : source.getTargets()) {
                for (UUID permanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null && permanent.getToughness().getValue() <= game.getPlayer(source.getControllerId()).getGraveyard().size()) {
                        permanent.destroy(source.getSourceId(), game, noRegen);
                        affectedTargets++;
                    }
                }
            }
        } else if (!targetPointer.getTargets(game, source).isEmpty()) {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null && permanent.getToughness().getValue() <= game.getPlayer(source.getControllerId()).getGraveyard().size()) {
                    permanent.destroy(source.getSourceId(), game, noRegen);
                    affectedTargets++;
                }
            }
        }
        return affectedTargets > 0;
    }
}
