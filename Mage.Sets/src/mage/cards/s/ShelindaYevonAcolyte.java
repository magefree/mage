package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShelindaYevonAcolyte extends CardImpl {

    public ShelindaYevonAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another creature you control enters, put a +1/+1 counter on that creature if its power is less than Shelinda's power. Otherwise, put a +1/+1 counter on Shelinda.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new ConditionalOneShotEffect(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        ShelindaYevonAcolyteCondition.instance, "put a +1/+1 counter on that creature " +
                        "if its power is less than {this}'s power. Otherwise, put a +1/+1 counter on {this}"
                ), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, false, SetTargetPointer.PERMANENT
        ));
    }

    private ShelindaYevonAcolyte(final ShelindaYevonAcolyte card) {
        super(card);
    }

    @Override
    public ShelindaYevonAcolyte copy() {
        return new ShelindaYevonAcolyte(this);
    }
}

enum ShelindaYevonAcolyteCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null
                && CardUtil
                .getEffectValueFromAbility(source, "permanentEnteringBattlefield", Permanent.class)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(x -> x < permanent.getPower().getValue())
                .orElse(false);
    }
}
