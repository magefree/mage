package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LasydProwler extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LANDS, null);
    private static final Hint hint = new ValueHint("Land cards in your graveyard", xValue);

    public LasydProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters, you may mill cards equal to the number of lands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(LandsYouControlCount.instance)
                .setText("mill cards equal to the number of lands you control"), true));

        // Renew -- {1}{G}, Exile this card from your graveyard: Put X +1/+1 counters on target creature, where X is the number of land cards in your graveyard. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), xValue),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.RENEW).addHint(hint));
    }

    private LasydProwler(final LasydProwler card) {
        super(card);
    }

    @Override
    public LasydProwler copy() {
        return new LasydProwler(this);
    }
}
