package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CrewSaddleIncreasedPowerAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DynamiteDiver extends CardImpl {

    public DynamiteDiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature saddles Mounts and crews Vehicles as though its power were 2 greater.
        this.addAbility(new CrewSaddleIncreasedPowerAbility());

        // When this creature dies, it deals 1 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DynamiteDiver(final DynamiteDiver card) {
        super(card);
    }

    @Override
    public DynamiteDiver copy() {
        return new DynamiteDiver(this);
    }
}
