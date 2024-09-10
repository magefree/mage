package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PropagatorDrone extends CardImpl {

    public PropagatorDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Creature tokens you control have evolve.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new EvolveAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // {3}{G}: Create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new EldraziSpawnToken()), new ManaCostsImpl<>("{3}{G}")));
    }

    private PropagatorDrone(final PropagatorDrone card) {
        super(card);
    }

    @Override
    public PropagatorDrone copy() {
        return new PropagatorDrone(this);
    }
}
