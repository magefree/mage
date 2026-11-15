package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BeornTheFierce extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterPermanent(SubType.BEAR, ""), ComparisonType.OR_GREATER, 3
    );

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.BEAR, "Bears");

    public BeornTheFierce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other Bears you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of combat on your turn, put a trample counter on up to one target creature you control. It becomes a Bear in addition to its other types. Then if you control three or more Bears, draw two cards.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.TRAMPLE.createInstance()));
        ability.addEffect(new AddCardSubTypeTargetEffect(SubType.BEAR, Duration.WhileOnBattlefield).setText("It becomes a Bear in addition to its other types"));
        ability.addEffect(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(2), condition,
            "Then if you control three or more Bears, draw two cards"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private BeornTheFierce(final BeornTheFierce card) {
        super(card);
    }

    @Override
    public BeornTheFierce copy() {
        return new BeornTheFierce(this);
    }
}
