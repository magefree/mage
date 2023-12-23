package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.RenownedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class AragornHornburgHero extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a renowned creature you control");

    static {
        filter.add(RenownedPredicate.instance);
    }
    public AragornHornburgHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Attacking creatures you control have first strike and renown 1.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new RenownAbility(1), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("and renown 1"));
        this.addAbility(ability);
        // Whenever a renowned creature you control deals combat damage to a player, double the number of +1/+1 counters on it.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AragornDoubleCountersTargetEffect(), filter,
                false, SetTargetPointer.PERMANENT, true
        ));

    }

    private AragornHornburgHero(final AragornHornburgHero card) {
        super(card);
    }

    @Override
    public AragornHornburgHero copy() {
        return new AragornHornburgHero(this);
    }
}
//Copied from Elvish Vatkeeper
class AragornDoubleCountersTargetEffect extends OneShotEffect {

    AragornDoubleCountersTargetEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on it";
    }

    private AragornDoubleCountersTargetEffect(final AragornDoubleCountersTargetEffect effect) {
        super(effect);
    }

    @Override
    public AragornDoubleCountersTargetEffect copy() {
        return new AragornDoubleCountersTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(
                permanent.getCounters(game).getCount(CounterType.P1P1)
        ), source.getControllerId(), source, game);
    }
}
