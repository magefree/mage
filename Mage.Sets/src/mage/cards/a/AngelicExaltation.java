package mage.cards.a;

import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicExaltation extends CardImpl {

    public AngelicExaltation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of creatures you control.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new BoostTargetEffect(
                CreaturesYouControlCount.instance,
                CreaturesYouControlCount.instance,
                Duration.EndOfTurn, true
        ).setText("it gets +X/+X until end of turn, where X is the number of creatures you control"), true, false).addHint(CreaturesYouControlHint.instance));
    }

    private AngelicExaltation(final AngelicExaltation card) {
        super(card);
    }

    @Override
    public AngelicExaltation copy() {
        return new AngelicExaltation(this);
    }
}
