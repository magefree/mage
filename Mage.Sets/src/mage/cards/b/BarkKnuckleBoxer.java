package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ExpendTriggeredAbility;
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
public final class BarkKnuckleBoxer extends CardImpl {

    public BarkKnuckleBoxer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you expend 4, Bark-Knuckle Boxer gains indestructible until end of turn.
        this.addAbility(new ExpendTriggeredAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), ExpendTriggeredAbility.Expend.FOUR));
    }

    private BarkKnuckleBoxer(final BarkKnuckleBoxer card) {
        super(card);
    }

    @Override
    public BarkKnuckleBoxer copy() {
        return new BarkKnuckleBoxer(this);
    }
}
