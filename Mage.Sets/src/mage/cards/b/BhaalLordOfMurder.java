package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BhaalLordOfMurder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public BhaalLordOfMurder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as your life total is less than or equal to half your starting life total, Bhaal, Lord of Murder has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), BhaalLordOfMurderCondition.instance,
                "as long as your life total is less than or equal to half your starting life total, {this} has indestructible"
        )));

        // Whenever another nontoken creature you control dies, put a +1/+1 counter on target creature and goad it.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, filter
        );
        ability.addEffect(new GoadTargetEffect().setText("and goad it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BhaalLordOfMurder(final BhaalLordOfMurder card) {
        super(card);
    }

    @Override
    public BhaalLordOfMurder copy() {
        return new BhaalLordOfMurder(this);
    }
}

enum BhaalLordOfMurderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.of(game.getPlayer(source.getControllerId()))
                .filter(Objects::nonNull)
                .map(Player::getLife)
                .map(x -> 2 * x >= game.getStartingLife())
                .orElse(false);
    }
}