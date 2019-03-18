
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MoonlightHunt extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public MoonlightHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Choose target creature you don't control. Each creature you control that's a Wolf or Werewolf deals damage equal to its power to that creature.
        this.getSpellAbility().addEffect(new MoonlightHuntEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public MoonlightHunt(final MoonlightHunt card) {
        super(card);
    }

    @Override
    public MoonlightHunt copy() {
        return new MoonlightHunt(this);
    }
}

class MoonlightHuntEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.WOLF), new SubtypePredicate(SubType.WEREWOLF)));
    }

    public MoonlightHuntEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose target creature you don't control. Each creature you control that's a Wolf or Werewolf deals damage equal to its power to that creature";
    }

    public MoonlightHuntEffect(final MoonlightHuntEffect effect) {
        super(effect);
    }

    @Override
    public MoonlightHuntEffect copy() {
        return new MoonlightHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && targetCreature != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                targetCreature.damage(permanent.getPower().getValue(), permanent.getId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
