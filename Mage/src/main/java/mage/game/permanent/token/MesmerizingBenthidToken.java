package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class MesmerizingBenthidToken extends TokenImpl {

    public MesmerizingBenthidToken() {
        super("Illusion", "0/2 blue Illusion creature token with \"Whenever this creature blocks a creature, that creature doesn't untap during its controller's next untap step.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        setOriginalExpansionSetCode("RNA");

        subtype.add(SubType.ILLUSION);
        power = new MageInt(0);
        toughness = new MageInt(2);
        this.addAbility(new BlocksTriggeredAbility(
                new DontUntapInControllersNextUntapStepTargetEffect("that creature"),
                false, true
        ));
    }

    private MesmerizingBenthidToken(final MesmerizingBenthidToken token) {
        super(token);
    }

    public MesmerizingBenthidToken copy() {
        return new MesmerizingBenthidToken(this);
    }
}
