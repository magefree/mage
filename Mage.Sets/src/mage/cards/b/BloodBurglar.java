package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodBurglar extends CardImpl {

    public BloodBurglar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as it's your turn, Blood Burglar has lifelink.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                LifelinkAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance,
                        "As long as it's your turn, "
                                + "{this} has lifelink."
                )
        ).addHint(MyTurnHint.instance));
    }

    private BloodBurglar(final BloodBurglar card) {
        super(card);
    }

    @Override
    public BloodBurglar copy() {
        return new BloodBurglar(this);
    }
}
