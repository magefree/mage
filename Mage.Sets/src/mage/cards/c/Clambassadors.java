
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class Clambassadors extends CardImpl {

    public Clambassadors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.CLAMFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Clambassadors deals damage to a player, choose an artifact, creature, or land you control. That player gains control of that permanent.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new ClambassadorsEffect(), false, true));
    }

    public Clambassadors(final Clambassadors card) {
        super(card);
    }

    @Override
    public Clambassadors copy() {
        return new Clambassadors(this);
    }
}


class ClambassadorsEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land you control");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }

    public ClambassadorsEffect() {
        super(Outcome.Detriment);
        this.staticText = "choose an artifact, creature, or land you control. That player gains control of that permanent";
    }

    public ClambassadorsEffect(final ClambassadorsEffect effect) {
        super(effect);
    }

    @Override
    public ClambassadorsEffect copy() {
        return new ClambassadorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledPermanent(1, 1, filter, true);
            if (target.hasPossibleTargets(source.getSourceId(), controller.getId(), game)) {
                while (!target.isChosen() && target.hasPossibleChoices(controller.getId(), game) && controller.canRespond()) {
                    controller.chooseTarget(outcome, target, source, game);
                }
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (permanent != null && opponent != null) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, opponent.getId());
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
                game.informPlayers(opponent.getLogName() + " has gained control of " + permanent.getLogName());
                return true;
            }
        }
        return false;
    }
}
