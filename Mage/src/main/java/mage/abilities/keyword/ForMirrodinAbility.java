package mage.abilities.keyword;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.game.permanent.token.PhyrexianGermToken;
import mage.game.permanent.token.RebelRedToken;

/**
 * @author TheElk801
 */
public class ForMirrodinAbility extends EntersBattlefieldTriggeredAbility {

    public ForMirrodinAbility() {
        super(new CreateTokenAttachSourceEffect(new RebelRedToken()));
    }

    public ForMirrodinAbility(final ForMirrodinAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "For Mirrodin! <i>(When this Equipment enters the battlefield, " +
                "create a 2/2 red Rebel creature token, then attach this to it.)</i>";
    }

    @Override
    public ForMirrodinAbility copy() {
        return new ForMirrodinAbility(this);
    }
}
