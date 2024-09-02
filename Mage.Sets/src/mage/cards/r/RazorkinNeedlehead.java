package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazorkinNeedlehead extends CardImpl {

    public RazorkinNeedlehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Razorkin Needlehead has first strike during your turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                MyTurnCondition.instance, "{this} has first strike during your turn"
        )));

        // Whenever an opponent draws a card, Razorkin Needlehead deals 1 damage to them.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new DamageTargetEffect(
                1, true, "them"
        ), false, true));
    }

    private RazorkinNeedlehead(final RazorkinNeedlehead card) {
        super(card);
    }

    @Override
    public RazorkinNeedlehead copy() {
        return new RazorkinNeedlehead(this);
    }
}
