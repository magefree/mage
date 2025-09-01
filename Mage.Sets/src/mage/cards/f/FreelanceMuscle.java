package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FreelanceMuscle extends CardImpl {

    public FreelanceMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Freelance Muscle attacks or blocks, it gets +X/+X until end of turn, where X is the greatest power and/or toughness among other creatures you control.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BoostSourceEffect(
                GreatestAmongPermanentsValue.POWER_OR_TOUGHNESS_OTHER_CONTROLLED_CREATURES,
                GreatestAmongPermanentsValue.POWER_OR_TOUGHNESS_OTHER_CONTROLLED_CREATURES,
                Duration.EndOfTurn, "it"
        ), false).addHint(GreatestAmongPermanentsValue.POWER_OR_TOUGHNESS_OTHER_CONTROLLED_CREATURES.getHint()));
    }

    private FreelanceMuscle(final FreelanceMuscle card) {
        super(card);
    }

    @Override
    public FreelanceMuscle copy() {
        return new FreelanceMuscle(this);
    }
}