package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class RighteousValkyrie extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Angel or Cleric");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(SubType.ANGEL.getPredicate(), SubType.CLERIC.getPredicate()));
    }

    public RighteousValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another Angel or Cleric enters the battlefield under your control, you gain life equal to that creature's toughness.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new RighteousValkyrieEffect(), filter));

        // As long as you have at least 7 life more than your starting life total, creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield), RighteousValkyrieCondition.instance,
                "As long as you have at least 7 life more than your starting life total, creatures you control get +2/+2"
        )));
    }

    private RighteousValkyrie(final RighteousValkyrie card) {
        super(card);
    }

    @Override
    public RighteousValkyrie copy() {
        return new RighteousValkyrie(this);
    }
}

class RighteousValkyrieEffect extends OneShotEffect {

    public RighteousValkyrieEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that creature's toughness";
    }

    private RighteousValkyrieEffect(final RighteousValkyrieEffect effect) {
        super(effect);
    }

    @Override
    public RighteousValkyrieEffect copy() {
        return new RighteousValkyrieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) this.getValue("permanentEnteringBattlefield");
        if (player == null || permanent == null) {
            return false;
        }
        player.gainLife(permanent.getToughness().getValue(), game, source);
        return true;
    }
}

enum RighteousValkyrieCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() >= game.getStartingLife() + 7;
    }
}
