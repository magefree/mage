package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChevillBaneOfMonsters extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent();
    private static final FilterPermanent filter2
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");
    private static final FilterPermanent filter3
            = new FilterPermanent("a permanent an opponent controls with a bounty counter on it");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(CounterType.BOUNTY.getPredicate());

        filter2.add(TargetController.OPPONENT.getControllerPredicate());

        filter3.add(TargetController.OPPONENT.getControllerPredicate());
        filter3.add(CounterType.BOUNTY.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0, false);

    public ChevillBaneOfMonsters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your upkeep, if your opponents control no permanents with bounty counters on them, put a bounty counter on target creature or planeswalker an opponent controls.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()),
                        TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, " +
                "if your opponents control no permanents with bounty counters on them, " +
                "put a bounty counter on target creature or planeswalker an opponent controls."
        );
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);

        // Whenever a permanent an opponent controls with a bounty counter on it dies, you gain 3 life and draw card.
        ability = new DiesCreatureTriggeredAbility(new GainLifeEffect(3), false, filter3);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ChevillBaneOfMonsters(final ChevillBaneOfMonsters card) {
        super(card);
    }

    @Override
    public ChevillBaneOfMonsters copy() {
        return new ChevillBaneOfMonsters(this);
    }
}
