package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RemnantOfTheRisingStar extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4);
    private static final Hint hint
            = new ValueHint("Modified creatures you control", new PermanentsOnBattlefieldCount(filter));

    public RemnantOfTheRisingStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature enters the battlefield under your control, you may pay {X}. When you do, put X +1/+1 counters on that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new RemnantOfTheRisingStarEffect(), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));

        // As long as you control five or more modified creatures, Remnant of the Rising Star gets +5/+5 and has trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(5, 5, Duration.WhileOnBattlefield),
                condition, "as long as you control five or more modified creatures, {this} gets +5/+5"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()), condition, "and has trample"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private RemnantOfTheRisingStar(final RemnantOfTheRisingStar card) {
        super(card);
    }

    @Override
    public RemnantOfTheRisingStar copy() {
        return new RemnantOfTheRisingStar(this);
    }
}

class RemnantOfTheRisingStarEffect extends OneShotEffect {

    RemnantOfTheRisingStarEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, put X +1/+1 counters on that creature";
    }

    private RemnantOfTheRisingStarEffect(final RemnantOfTheRisingStarEffect effect) {
        super(effect);
    }

    @Override
    public RemnantOfTheRisingStarEffect copy() {
        return new RemnantOfTheRisingStarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        if (player == null || !player.chooseUse(
                Outcome.BoostCreature, "Pay " + cost.getText() + "?", source, game
        )) {
            return false;
        }
        int xValue = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(xValue));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(xValue))
                        .setTargetPointer(new FixedTarget(permanent, game)), false
        ), source);
        return true;
    }
}
