package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author michael.napoleon@gmail.com
 */
public final class Backlash extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public Backlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");

        // Tap target untapped creature. That creature deals damage equal to its power to its controller.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new BacklashEffect());
    }

    private Backlash(final Backlash card) {
        super(card);
    }

    @Override
    public Backlash copy() {
        return new Backlash(this);
    }
}

class BacklashEffect extends OneShotEffect {

    public BacklashEffect() {
        super(Outcome.Detriment);
        this.staticText = "Tap target untapped creature. That creature deals damage equal to its power to its controller.";
    }

    private BacklashEffect(final BacklashEffect effect) {
        super(effect);
    }

    @Override
    public BacklashEffect copy() {
        return new BacklashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {
            targetCreature.tap(source, game);
            Player controller = game.getPlayer(targetCreature.getControllerId());
            if (controller != null) {
                controller.damage(targetCreature.getPower().getValue(), targetCreature.getId(), source, game);
                return true;
            }
        }
        return false;
    }
}
