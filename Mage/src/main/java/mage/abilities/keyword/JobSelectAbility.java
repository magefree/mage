package mage.abilities.keyword;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.game.permanent.token.HeroToken;

/**
 * @author balazskristof
 */
public class JobSelectAbility extends EntersBattlefieldTriggeredAbility {

    public JobSelectAbility() {
        super(new CreateTokenAttachSourceEffect(new HeroToken()));
    }

    protected JobSelectAbility(final JobSelectAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Job select <i>(When this Equipment enters, " +
                "create a 1/1 colorless Hero creature token, then attach this to it.)</i>";
    }

    @Override
    public JobSelectAbility copy() {
        return new JobSelectAbility(this);
    }
}
