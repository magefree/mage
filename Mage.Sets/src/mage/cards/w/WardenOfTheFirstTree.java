package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WardenOfTheFirstTree extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIRIT, "");
    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public WardenOfTheFirstTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.HUMAN, SubType.WARRIOR
        ).setText("{this} becomes a Human Warrior"), new ManaCostsImpl<>("{1}{W/B}"));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                3, 3, Duration.Custom, SubLayer.SetPT_7b
        ).setText("with base power and toughness 3/3"));
        this.addAbility(ability);

        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        this.addAbility(new SimpleActivatedAbility(
                new WardenOfTheFirstTreeEffect(), new ManaCostsImpl<>("{2}{W/B}{W/B}")
        ));

        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        this.addAbility(new SimpleActivatedAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                        condition, "If {this} is a Spirit, put five +1/+1 counters on it"
                ), new ManaCostsImpl<>("{3}{W/B}{W/B}{W/B}")
        ));
    }

    private WardenOfTheFirstTree(final WardenOfTheFirstTree card) {
        super(card);
    }

    @Override
    public WardenOfTheFirstTree copy() {
        return new WardenOfTheFirstTree(this);
    }
}

class WardenOfTheFirstTreeEffect extends OneShotEffect {

    WardenOfTheFirstTreeEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink";
    }

    private WardenOfTheFirstTreeEffect(final WardenOfTheFirstTreeEffect effect) {
        super(effect);
    }

    @Override
    public WardenOfTheFirstTreeEffect copy() {
        return new WardenOfTheFirstTreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.WARRIOR, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.HUMAN, SubType.SPIRIT, SubType.WARRIOR
        ), source);
        game.addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.Custom
        ), source);
        game.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.Custom
        ), source);
        return true;
    }
}
