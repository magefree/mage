
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class GrislySpectacle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public GrislySpectacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");

        // Destroy target nonartifact creature. Its controller puts a number of cards equal to that creature's power from the top of their library into their graveyard.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GrislySpectacleEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public GrislySpectacle(final GrislySpectacle card) {
        super(card);
    }

    @Override
    public GrislySpectacle copy() {
        return new GrislySpectacle(this);
    }
}

class GrislySpectacleEffect extends OneShotEffect {

    public GrislySpectacleEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Its controller puts a number of cards equal to that creature's power from the top of their library into their graveyard";
    }

    public GrislySpectacleEffect(final GrislySpectacleEffect effect) {
        super(effect);
    }

    @Override
    public GrislySpectacleEffect copy() {
        return new GrislySpectacleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                int power = creature.getPower().getValue();
                Effect effect = new PutLibraryIntoGraveTargetEffect(power);
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
