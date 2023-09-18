package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EstwaldShieldbasher extends CardImpl {

    public EstwaldShieldbasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Estwald Shieldbasher attacks, you may pay {1}. If you do, it gains indestructible until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains indestructible until end of turn"), new GenericManaCost(1))));
    }

    private EstwaldShieldbasher(final EstwaldShieldbasher card) {
        super(card);
    }

    @Override
    public EstwaldShieldbasher copy() {
        return new EstwaldShieldbasher(this);
    }
}
