package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class OutlawsMerrimentRogueToken extends TokenImpl {

    public OutlawsMerrimentRogueToken() {
        super("Human Rogue Token", "1/2 Human Rogue with haste and \"When this creature enters the battlefield, it deals 1 damage to any target.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.ROGUE);
        color.setWhite(true);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private OutlawsMerrimentRogueToken(final OutlawsMerrimentRogueToken token) {
        super(token);
    }

    public OutlawsMerrimentRogueToken copy() {
        return new OutlawsMerrimentRogueToken(this);
    }
}
