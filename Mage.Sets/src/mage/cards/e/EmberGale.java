
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class EmberGale extends CardImpl {

    public EmberGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Creatures target player controls can't block this turn. Ember Gale deals 1 damage to each white and/or blue creature that player controls.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new EmberGaleEffect());

    }

    private EmberGale(final EmberGale card) {
        super(card);
    }

    @Override
    public EmberGale copy() {
        return new EmberGale(this);
    }
}

class EmberGaleEffect extends OneShotEffect {

    public EmberGaleEffect() {
        super(Outcome.Detriment);
        staticText = "Creatures target player controls can't block this turn. {this} deals 1 damage to each white and/or blue creature that player controls";
    }

    private EmberGaleEffect(final EmberGaleEffect effect) {
        super(effect);
    }

    @Override
    public EmberGaleEffect copy() {
        return new EmberGaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(targetPlayer.getId()));
            RestrictionEffect effect = new CantBlockAllEffect(filter, Duration.EndOfTurn);
            game.addEffect(effect, source);
            FilterPermanent filter2 = new FilterPermanent();
            filter2.add(new ControllerIdPredicate(targetPlayer.getId()));
            filter2.add(Predicates.or(new ColorPredicate(ObjectColor.WHITE),
                    new ColorPredicate(ObjectColor.BLUE)));
            filter2.add(CardType.CREATURE.getPredicate());
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter2, targetPlayer.getId(), game)) {
                creature.damage(1, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
