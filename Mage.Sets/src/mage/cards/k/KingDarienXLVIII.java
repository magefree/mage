package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author weirddan455
 */
public final class KingDarienXLVIII extends CardImpl {

    public KingDarienXLVIII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)));

        // {3}{G}{W}: Put a +1/+1 counter on King Darien and create a 1/1 white Soldier creature token.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{3}{G}{W}")
        );
        ability.addEffect(new CreateTokenEffect(new SoldierToken()).concatBy("and"));
        this.addAbility(ability);

        // Sacrifice King Darien: Creature tokens you control gain hexproof and indestructible until end of turn.
        ability = new SimpleActivatedAbility(
                new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_TOKENS)
                        .setText("Creature tokens you control gain hexproof"),
                new SacrificeSourceCost()
        );
        ability.addEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_TOKENS)
                .setText("and indestructible until end of turn"));
        this.addAbility(ability);
    }

    private KingDarienXLVIII(final KingDarienXLVIII card) {
        super(card);
    }

    @Override
    public KingDarienXLVIII copy() {
        return new KingDarienXLVIII(this);
    }
}
