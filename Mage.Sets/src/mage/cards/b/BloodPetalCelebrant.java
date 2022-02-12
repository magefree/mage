package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodPetalCelebrant extends CardImpl {

    public BloodPetalCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Blood Petal Celebrant has first strike as long as it's attacking.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                SourceAttackingCondition.instance, "{this} has first strike as long as it's attacking"
        )));

        // When Blood Petal Celebrant dies, create a Blood token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BloodToken())));
    }

    private BloodPetalCelebrant(final BloodPetalCelebrant card) {
        super(card);
    }

    @Override
    public BloodPetalCelebrant copy() {
        return new BloodPetalCelebrant(this);
    }
}
