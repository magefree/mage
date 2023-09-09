
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class InquisitorsSnare extends CardImpl {

    public InquisitorsSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Prevent all damage target attacking or blocking creature would deal this turn. If that creature is black or red, destroy it.
        this.getSpellAbility().addEffect(new InquisitorsSnareEffect());
        Target target = new TargetAttackingOrBlockingCreature();
        this.getSpellAbility().addTarget(target);

    }

    private InquisitorsSnare(final InquisitorsSnare card) {
        super(card);
    }

    @Override
    public InquisitorsSnare copy() {
        return new InquisitorsSnare(this);
    }
}

class InquisitorsSnareEffect extends OneShotEffect {

    public InquisitorsSnareEffect() {
        super(Outcome.Detriment);
        this.staticText = "Prevent all damage target attacking or blocking creature would deal this turn. If that creature is black or red, destroy it";
    }

    private InquisitorsSnareEffect(final InquisitorsSnareEffect effect) {
        super(effect);
    }

    @Override
    public InquisitorsSnareEffect copy() {
        return new InquisitorsSnareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new PermanentIdPredicate(targetCreature.getId()));
            game.addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, false), source);
            if (targetCreature.getColor(game).isBlack() || targetCreature.getColor(game).isRed()) {
                return targetCreature.destroy(source, game, false);
            }
        }
        return false;
    }
}