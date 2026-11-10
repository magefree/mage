package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author muz
 */
public final class SevenOfNineUnassimilated extends CardImpl {

    static final DifferentlyNamedPermanentCount landCount =
        new DifferentlyNamedPermanentCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    public SevenOfNineUnassimilated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // At the beginning of your first main phase, draw a card. Then discard a card unless you control five or more differently named lands.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(new DiscardControllerEffect(1),
            new InvertCondition(SevenOfNineUnassimilatedCondition.FIVE_LANDS),
            "Then discard a card unless you control five or more differently named lands"
        ));
        this.addAbility(ability.addHint(landCount.getHint()));

        // {6}{U}: Seven of Nine's base power and toughness become 7/9. Activate only if you control seven or more differently named lands.
        this.addAbility(new SimpleActivatedAbility(new SetBasePowerToughnessSourceEffect(7, 9, Duration.Custom)
            .setText("{this}'s base power and toughness become 7/9. Activate only if you control seven or more differently named lands"),
            new ManaCostsImpl<>("{6}{U}")
        ).setCondition(SevenOfNineUnassimilatedCondition.SEVEN_LANDS));
    }

    private SevenOfNineUnassimilated(final SevenOfNineUnassimilated card) {
        super(card);
    }

    @Override
    public SevenOfNineUnassimilated copy() {
        return new SevenOfNineUnassimilated(this);
    }
}

enum SevenOfNineUnassimilatedCondition implements Condition {
    FIVE_LANDS(5),
    SEVEN_LANDS(7);

    private final int minimum;

    SevenOfNineUnassimilatedCondition(int minimum) {
        this.minimum = minimum;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SevenOfNineUnassimilated.landCount.calculate(game, source, null) >= minimum;
    }
}
