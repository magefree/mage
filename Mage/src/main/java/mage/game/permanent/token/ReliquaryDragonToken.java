package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class ReliquaryDragonToken extends TokenImpl {

    public ReliquaryDragonToken() {
        super("Reliquary Dragon", "4/4 Dragon creature token named Reliquary Dragon that's all colors. It has flying, lifelink, and \"When this token enters, it deals 3 damage to any target.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlue(true);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
        addAbility(LifelinkAbility.getInstance());

        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        addAbility(ability);
    }

    private ReliquaryDragonToken(final ReliquaryDragonToken token) {
        super(token);
    }

    public ReliquaryDragonToken copy() {
        return new ReliquaryDragonToken(this);
    }
}
