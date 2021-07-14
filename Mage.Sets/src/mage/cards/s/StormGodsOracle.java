package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormGodsOracle extends CardImpl {

    public StormGodsOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}: Storm God's Oracle gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, -1, Duration.EndOfTurn), new GenericManaCost(1)
        ));

        // When Storm God's Oracle dies, it deals 3 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private StormGodsOracle(final StormGodsOracle card) {
        super(card);
    }

    @Override
    public StormGodsOracle copy() {
        return new StormGodsOracle(this);
    }
}
