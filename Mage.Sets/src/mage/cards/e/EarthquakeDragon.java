package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
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
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthquakeDragon extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Total mana value of Dragons you control", EarthquakeDragonValue.instance
    );

    public EarthquakeDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{14}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // This spell costs {X} less to cast, where X is the total mana value of Dragons you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(EarthquakeDragonValue.instance)
        ).addHint(DevotionCount.W.getHint()).setRuleAtTheTop(true));

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

enum EarthquakeDragonValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum();
    }

    @Override
    public EarthquakeDragonValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the total mana value of Dragons you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
