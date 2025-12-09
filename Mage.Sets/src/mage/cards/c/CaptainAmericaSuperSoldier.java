package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptainAmericaSuperSoldier extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.SHIELD);
    private static final FilterPermanent filter = new FilterPermanent(SubType.HERO, "Heroes");

    public CaptainAmericaSuperSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Captain America enters with a shield counter on him.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance()),
                "with a shield counter on him"
        ));

        // As long as Captain America has a shield counter on him, you and other Heroes you control have hexproof.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControllerEffect(HexproofAbility.getInstance()),
                condition, "as long as {this} has a shield counter on him, you"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ), condition, null).concatBy("and"));
        this.addAbility(ability);
    }

    private CaptainAmericaSuperSoldier(final CaptainAmericaSuperSoldier card) {
        super(card);
    }

    @Override
    public CaptainAmericaSuperSoldier copy() {
        return new CaptainAmericaSuperSoldier(this);
    }
}
