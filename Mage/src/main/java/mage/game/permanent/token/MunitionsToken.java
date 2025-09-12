package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class MunitionsToken extends TokenImpl {

    public MunitionsToken() {
        super("Munitions", "colorless artifact token named Munitions with \"When this token leaves the battlefield, it deals 2 damage to any target.\"");
        cardType.add(CardType.ARTIFACT);

        Ability ability = new LeavesBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MunitionsToken(final MunitionsToken token) {
        super(token);
    }

    public MunitionsToken copy() {
        return new MunitionsToken(this);
    }
}
