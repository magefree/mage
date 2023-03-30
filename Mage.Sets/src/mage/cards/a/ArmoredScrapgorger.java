package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCardInGraveyard;

/**
 * @author TheElk801
 */
public final class ArmoredScrapgorger extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.OIL, 3);

    public ArmoredScrapgorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Armored Scrapgorger gets +3/+0 as long as it has three or more oil counters on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield), condition,
                "{this} gets +3/+0 as long as it has three or more oil counters on it"
        )));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // When Armored Scrapgorger becomes tapped, exile target card from a graveyard and put an oil counter on Armored Scrapgorger.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard());
        ability.addEffect(new AddCountersSourceEffect(CounterType.OIL.createInstance()).concatBy("and"));
        this.addAbility(ability);
    }

    private ArmoredScrapgorger(final ArmoredScrapgorger card) {
        super(card);
    }

    @Override
    public ArmoredScrapgorger copy() {
        return new ArmoredScrapgorger(this);
    }
}
