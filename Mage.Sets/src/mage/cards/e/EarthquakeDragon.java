package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.TotalPermanentsManaValue;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthquakeDragon extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON, "Dragons you control");

    private static final TotalPermanentsManaValue xValue = new TotalPermanentsManaValue(filter);

    public EarthquakeDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{14}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // This spell costs {X} less to cast, where X is the total mana value of Dragons you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(xValue)
            ).addHint(xValue.getHint()).setRuleAtTheTop(true)
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {2}{G}, Sacrifice a land: Return Earthquake Dragon from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{G}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT));
        this.addAbility(ability);
    }

    private EarthquakeDragon(final EarthquakeDragon card) {
        super(card);
    }

    @Override
    public EarthquakeDragon copy() {
        return new EarthquakeDragon(this);
    }
}
