package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlowerfootSwordmaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.MOUSE, "Mice");

    public FlowerfootSwordmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // Valiant -- Whenever this creature becomes the target of a spell or ability you control for the first time each turn, Mice you control get +1/+0 until end of turn.
        this.addAbility(new ValiantTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter
        )));
    }

    private FlowerfootSwordmaster(final FlowerfootSwordmaster card) {
        super(card);
    }

    @Override
    public FlowerfootSwordmaster copy() {
        return new FlowerfootSwordmaster(this);
    }
}
