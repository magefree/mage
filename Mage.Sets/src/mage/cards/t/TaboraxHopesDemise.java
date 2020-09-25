package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaboraxHopesDemise extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, 5);
    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(TokenPredicate.instance));
    }

    public TaboraxHopesDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Taborax, Hope's Demise has lifelink as long as it has five or more +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()), condition,
                "{this} has lifelink as long as it has five or more +1/+1 counters on it"
        )));

        // Whenever another nontoken creature you control dies, put a +1/+1 counter on Taborax. If that creature was a Cleric, you may draw a card. If you do, you lose 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, filter, true
        );
        ability.addEffect(new TaboraxHopesDemiseEffect());
        this.addAbility(ability);
    }

    private TaboraxHopesDemise(final TaboraxHopesDemise card) {
        super(card);
    }

    @Override
    public TaboraxHopesDemise copy() {
        return new TaboraxHopesDemise(this);
    }
}

class TaboraxHopesDemiseEffect extends OneShotEffect {

    TaboraxHopesDemiseEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature was a Cleric, you may draw a card. If you do, you lose 1 life.";
    }

    private TaboraxHopesDemiseEffect(final TaboraxHopesDemiseEffect effect) {
        super(effect);
    }

    @Override
    public TaboraxHopesDemiseEffect copy() {
        return new TaboraxHopesDemiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.hasSubtype(SubType.CLERIC, game)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.chooseUse(
                outcome, "Draw a card and lose 1 life?", source, game
        ) && player.drawCards(1, source.getSourceId(), game) > 0) {
            player.loseLife(1, game, false);
            return true;
        }
        return false;
    }
}
