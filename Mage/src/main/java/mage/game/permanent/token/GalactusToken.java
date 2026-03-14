package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetLandPermanent;

/**
 * @author TheElk801
 */
public final class GalactusToken extends TokenImpl {

    public GalactusToken() {
        super("Galactus", "Galactus, a legendary 16/16 black Elder Alien creature token with flying, trample, and \"Whenever Galactus attacks, destroy target land.\"");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDER);
        subtype.add(SubType.ALIEN);

        color.setBlack(true);
        power = new MageInt(16);
        toughness = new MageInt(16);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        Ability ability = new AttacksTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GalactusToken(final GalactusToken token) {
        super(token);
    }

    public GalactusToken copy() {
        return new GalactusToken(this);
    }
}
