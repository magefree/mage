package mage.cards.u;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnchainedBerserker extends CardImpl {

    public UnchainedBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // Unchained Berserker gets +2/+0 as long as it's attacking.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                SourceAttackingCondition.instance, "{this} gets +2/+0 as long as it's attacking"
        )));
    }

    private UnchainedBerserker(final UnchainedBerserker card) {
        super(card);
    }

    @Override
    public UnchainedBerserker copy() {
        return new UnchainedBerserker(this);
    }
}
