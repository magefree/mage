package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PackTacticsAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author weirddan455
 */
public final class BattleCryGoblin extends CardImpl {

    public BattleCryGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}: Goblins you control get +1/+0 and gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS)
                    .setText("Goblins you control get +1/+0"),
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS)
                    .setText("and gain haste until end of turn")
        );
        this.addAbility(ability);

        // Pack tactics — Whenever Battle Cry Goblin attacks, if you attacked with creatures with total power 6 or greater this combat,
        // create a 1/1 red Goblin creature token that's tapped and attacking.
        this.addAbility(new PackTacticsAbility(new CreateTokenEffect(
                new GoblinToken(), 1, true, true
        )));
    }

    private BattleCryGoblin(final BattleCryGoblin card) {
        super(card);
    }

    @Override
    public BattleCryGoblin copy() {
        return new BattleCryGoblin(this);
    }
}
