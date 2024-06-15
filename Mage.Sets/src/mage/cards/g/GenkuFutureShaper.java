package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Fox22VigilanceToken;
import mage.game.permanent.token.Moonfolk12FlyingToken;
import mage.game.permanent.token.Rat11LifelinkToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GenkuFutureShaper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another nontoken permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public GenkuFutureShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever another nontoken permanent you control leaves the battlefield, choose one that hasn't been chosen this turn. Create a creature token with those characteristics.
        // * 2/2 white Fox with vigilance.
        Ability ability = new LeavesBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new Fox22VigilanceToken())
                        .setText("2/2 white Fox with vigilance"),
                filter, false
        );
        ability.setModeTag("2/2 white Fox with vigilance");
        ability.getModes().setLimitUsageByOnce(true);

        // * 1/2 blue Moonfolk with flying.
        ability.addMode(
                new Mode(
                        new CreateTokenEffect(new Moonfolk12FlyingToken())
                                .setText("1/2 blue Moonfolk with flying")
                ).setModeTag("1/2 blue Moonfolk with flying")
        );

        // * 1/1 black Rat with lifelink.
        ability.addMode(
                new Mode(
                        new CreateTokenEffect(new Rat11LifelinkToken())
                                .setText("1/1 black Rat with lifelink")
                ).setModeTag("1/1 black Rat with lifelink")
        );
        ability.getModes().setChooseText("choose one that hasn't been chosen this turn. Create a creature token with those characteristics.");
        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);

        // {3}{W}{U}: Put a +1/+1 counter on each creature you control.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
                new ManaCostsImpl<>("{3}{W}{U}")
        ));
    }

    private GenkuFutureShaper(final GenkuFutureShaper card) {
        super(card);
    }

    @Override
    public GenkuFutureShaper copy() {
        return new GenkuFutureShaper(this);
    }
}
