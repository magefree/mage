
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public final class PathOfBravery extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    static final String rule = "As long as your life total is greater than or equal to your starting life total, creatures you control get +1/+1";

    public PathOfBravery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // As long as your life total is greater than or equal to your starting life total, creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true), LifeCondition.instance, rule)));

        // Whenever one or more creatures you control attack, you gain life equal to the number of attacking creatures.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new PathOfBraveryEffect(), 1));

    }

    public PathOfBravery(final PathOfBravery card) {
        super(card);
    }

    @Override
    public PathOfBravery copy() {
        return new PathOfBravery(this);
    }
}

enum LifeCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            return you.getLife() >= game.getLife();
        }
        return false;
    }
}

class PathOfBraveryEffect extends OneShotEffect {

    private int attackers;

    public PathOfBraveryEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to the number of attacking creatures";
    }

    public PathOfBraveryEffect(final PathOfBraveryEffect effect) {
        super(effect);
    }

    @Override
    public PathOfBraveryEffect copy() {
        return new PathOfBraveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        attackers = game.getCombat().getAttackers().size();
        if (you != null) {
            you.gainLife(attackers, game, source);
            attackers = 0;
            return true;
        }
        return false;
    }
}
