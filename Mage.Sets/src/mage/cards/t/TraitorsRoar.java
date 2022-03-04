package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConspireAbility;
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
 * @author jeffwadsworth
 */
public final class TraitorsRoar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public TraitorsRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B/R}");

        // Tap target untapped creature. It deals damage equal to its power to its controller.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new TraitorsRoarEffect());

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));

    }

    private TraitorsRoar(final TraitorsRoar card) {
        super(card);
    }

    @Override
    public TraitorsRoar copy() {
        return new TraitorsRoar(this);
    }
}

class TraitorsRoarEffect extends OneShotEffect {

    public TraitorsRoarEffect() {
        super(Outcome.Detriment);
        this.staticText = "Tap target untapped creature. It deals damage equal to its power to its controller";
    }

    public TraitorsRoarEffect(final TraitorsRoarEffect effect) {
        super(effect);
    }

    @Override
    public TraitorsRoarEffect copy() {
        return new TraitorsRoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {
            applied = targetCreature.tap(source, game);
            Player controller = game.getPlayer(targetCreature.getControllerId());
            if (controller != null) {
                controller.damage(targetCreature.getPower().getValue(), targetCreature.getId(), source, game);
                applied = true;
            }
        }
        return applied;
    }
}
