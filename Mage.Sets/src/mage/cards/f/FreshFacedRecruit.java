package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class FreshFacedRecruit extends CardImpl {

    public FreshFacedRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as it's your turn, Fresh-Faced Recruit has first strike.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FirstStrikeAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance,
                        "As long as it's your turn, "
                        + "{this} has first strike."
                )
        ));
    }

    public FreshFacedRecruit(final FreshFacedRecruit card) {
        super(card);
    }

    @Override
    public FreshFacedRecruit copy() {
        return new FreshFacedRecruit(this);
    }
}
