package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author anonymous
 */
public final class FlailingDrake extends CardImpl {

    public FlailingDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Flailing Drake blocks or becomes blocked by a creature, that creature gets +1/+1 until end of turn.
        Effect effect = new BoostTargetEffect(+1, +1, Duration.EndOfTurn);
        effect.setText("that creature gets +1/+1 until end of turn");
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(effect));
    }

    private FlailingDrake(final FlailingDrake card) {
        super(card);
    }

    @Override
    public FlailingDrake copy() {
        return new FlailingDrake(this);
    }
}
