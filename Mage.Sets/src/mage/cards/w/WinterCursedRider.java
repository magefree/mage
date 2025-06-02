package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.util.CardUtil;

/**
 *
 * @author Jmlundeen
 */
public final class WinterCursedRider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each other nonartifact creature");
    private static final DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public WinterCursedRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2)));

        // Artifacts you control have "Ward--Pay 2 life."
        WardAbility wardAbility = new WardAbility(new PayLifeCost(2));
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAllEffect(wardAbility, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS)
                        .setText("Artifacts you control have " + "\"" + CardUtil.getTextWithFirstCharUpperCase(wardAbility.getRuleWithoutHint()) + "\"")
        ));
        // Exhaust -- {2}{U}{B}, {T}, Exile X artifact cards from your graveyard: Each other nonartifact creature gets -X/-X until end of turn.
        Ability ability = new ExhaustAbility(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn, filter, true),
                new ManaCostsImpl<>("{2}{U}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARD_ARTIFACTS)
                .setText("Exile X artifact cards from your graveyard"));
        this.addAbility(ability);
    }

    private WinterCursedRider(final WinterCursedRider card) {
        super(card);
    }

    @Override
    public WinterCursedRider copy() {
        return new WinterCursedRider(this);
    }
}
